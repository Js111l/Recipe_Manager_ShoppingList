package com.softwareminds.shoppinglistservice.controller;

import com.softwareminds.shoppinglistservice.database.DbInit;
import com.softwareminds.shoppinglistservice.entities.ShoppingListEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ShoppingListControllerTest {
  @Container
  public static PostgreSQLContainer postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("test")
          .withUsername("root")
          .withPassword("qwerty");

  static final String SHOPPING_LISTS_URL = "shoppinglists";
  List<ShoppingListEntity> SHOPPING_LIST_ENTITIES;
  @Autowired WebTestClient webTestClient;
  @Autowired DbInit dbInit;

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @BeforeEach
  void setUp() {
    postgreSQLContainer.start();
    dbInit.initTestData();
    this.SHOPPING_LIST_ENTITIES = dbInit.getShoppingListEntities();
  }

  @AfterAll
  static void tearDown() {
    postgreSQLContainer.start();
  }

  @Test
  void getShoppingListEntities() {
    var responseBody =
        this.webTestClient
            .get()
            .uri(SHOPPING_LISTS_URL)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(ShoppingListEntity.class)
            .returnResult()
            .getResponseBody();

    assertThat(responseBody).hasSameElementsAs(SHOPPING_LIST_ENTITIES);
  }

  @Test
  void getShoppingListEntitiesById_validId_properShoppingListEntity() {
    var responseBody =
        this.webTestClient
            .get()
            .uri(SHOPPING_LISTS_URL + "/3")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(ShoppingListEntity.class)
            .returnResult()
            .getResponseBody();

    assertThat(responseBody)
        .isEqualTo(
            SHOPPING_LIST_ENTITIES.stream().filter(x -> x.getId() == 3).findFirst().orElseThrow());
  }

  @Test
  void getShoppingListEntitiesById_invalidId_statusNotFound() {
    var responseBody =
        this.webTestClient
            .get()
            .uri(SHOPPING_LISTS_URL + "/99")
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody(ProblemDetail.class)
            .returnResult()
            .getResponseBody();

    assertThat(responseBody.getTitle()).isEqualTo("SHOPPING_LIST_NOT_FOUND");
  }

  @Test
  void deleteShoppingList_validId_properlyDeletedResource() {
    this.webTestClient.delete().uri(SHOPPING_LISTS_URL + "/3").exchange().expectStatus().isOk();
    this.webTestClient.get().uri(SHOPPING_LISTS_URL + "/3").exchange().expectStatus().isNotFound();
  }

  @Test
  void deleteShoppingList_inValidId_statusNotFound() {
    this.webTestClient
        .delete()
        .uri(SHOPPING_LISTS_URL + "/99")
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void updateShoppingList_properEntityWithValidId_properlyUpdatedResource() {
    // Given
    var shoppingListEntity = new ShoppingListEntity();
    shoppingListEntity.setId(3);
    shoppingListEntity.setTitle("Updated List!");
    shoppingListEntity.setElements(List.of("Only Milk"));

    // When
    this.webTestClient
        .put()
        .uri(SHOPPING_LISTS_URL)
        .bodyValue(shoppingListEntity)
        .exchange()
        .expectStatus()
        .isOk();

    // Then
    var result =
        this.webTestClient
            .get()
            .uri(SHOPPING_LISTS_URL + "/3")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(ShoppingListEntity.class)
            .returnResult()
            .getResponseBody();

    assertThat(result).isEqualTo(shoppingListEntity);
  }

  @Test
  void updateShoppingList_properEntityWithInvalidId_statusNotFound() {
    var shoppingListEntity = new ShoppingListEntity();
    shoppingListEntity.setId(999);
    shoppingListEntity.setTitle("Updated List!");
    shoppingListEntity.setElements(List.of("Only Milk"));

    this.webTestClient
        .put()
        .uri(SHOPPING_LISTS_URL)
        .bodyValue(shoppingListEntity)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void createShoppingList_properEntity_properlySavedResource() {
    // Given
    var shoppingListEntity = new ShoppingListEntity();
    shoppingListEntity.setTitle("New List!");
    shoppingListEntity.setElements(List.of("Cheese"));

    // When
    this.webTestClient
        .post()
        .uri(SHOPPING_LISTS_URL)
        .bodyValue(shoppingListEntity)
        .exchange()
        .expectStatus()
        .isOk();

    // Then
    this.webTestClient
        .get()
        .uri(SHOPPING_LISTS_URL + "/7")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.title")
        .value(equalTo(shoppingListEntity.getTitle()))
        .jsonPath("$.elements[0]")
        .value(equalTo(shoppingListEntity.getElements().get(0)));
  }
}
