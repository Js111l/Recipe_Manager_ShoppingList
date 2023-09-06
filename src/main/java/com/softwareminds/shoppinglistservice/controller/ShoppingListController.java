package com.softwareminds.shoppinglistservice.controller;

import com.softwareminds.shoppinglistservice.entities.ShoppingListEntity;
import com.softwareminds.shoppinglistservice.service.ShoppingListService;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppinglists")
public class ShoppingListController {
  private final ShoppingListService service;

  public ShoppingListController(ShoppingListService service) {
    this.service = service;
  }

  @GetMapping
  @CrossOrigin
  public List<ShoppingListEntity> getShoppingListEntities() {
    return this.service.getShoppingListEntities();
  }

  @GetMapping("/{id}")
  @CrossOrigin
  public ShoppingListEntity getShoppingListEntityById(@PathVariable int id) {
    return this.service.getShoppingListEntityById(id);
  }

  @DeleteMapping("/{id}")
  @CrossOrigin
  public void deleteShoppingList(@PathVariable int id) {
    this.service.deleteShoppingList(id);
  }

  @PutMapping
  @CrossOrigin
  public ShoppingListEntity updateShoppingList(@RequestBody ShoppingListEntity shoppingListEntity) {
    return this.service.updateShoppingListEntity(shoppingListEntity);
  }

  @PostMapping
  @CrossOrigin
  public ShoppingListEntity createShoppingList(@RequestBody ShoppingListEntity shoppingListEntity) {
    return this.service.createShoppingListEntity(shoppingListEntity);
  }
}
