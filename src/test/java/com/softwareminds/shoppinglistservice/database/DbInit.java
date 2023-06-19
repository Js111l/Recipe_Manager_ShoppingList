package com.softwareminds.shoppinglistservice.database;


import java.util.List;
import java.util.stream.IntStream;

import com.softwareminds.shoppinglistservice.entities.ShoppingListEntity;
import com.softwareminds.shoppinglistservice.service.ShoppingListService;
import org.springframework.stereotype.Component;

@Component
public class DbInit {
  private final ShoppingListService recipeService;

  public DbInit(ShoppingListService recipeService) {
    this.recipeService = recipeService;
  }

  public List<ShoppingListEntity> getShoppingListEntities() {

    return IntStream.rangeClosed(1, 6)
        .mapToObj(
            x -> {
              var shoppingListEntity = new ShoppingListEntity();
              shoppingListEntity.setId(x);
              shoppingListEntity.setTitle("List" + x);
              shoppingListEntity.setElements(List.of("Milk", "Juice", "Eggs", "Sausages"));
              return shoppingListEntity;
            })
        .toList();
  }

  public void initTestData() {
    getShoppingListEntities().forEach(this.recipeService::createShoppingListEntity);
  }
}
