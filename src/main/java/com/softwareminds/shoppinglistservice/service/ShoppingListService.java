package com.softwareminds.shoppinglistservice.service;

import com.softwareminds.shoppinglistservice.entities.ShoppingListEntity;
import com.softwareminds.shoppinglistservice.exceptions.ShoppingListNotFound;
import com.softwareminds.shoppinglistservice.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListService {
  private final ShoppingListRepository shoppingListRepository;

  public ShoppingListService(ShoppingListRepository shoppingListRepository) {
    this.shoppingListRepository = shoppingListRepository;
  }

  public List<ShoppingListEntity> getShoppingListEntities() {
    return this.shoppingListRepository.findAll();
  }

  public ShoppingListEntity getShoppingListEntityById(int id) {
    return this.shoppingListRepository
        .findById(id)
        .orElseThrow(() -> new ShoppingListNotFound("List with given id has not been found!"));
  }

  public ShoppingListEntity updateShoppingListEntity(ShoppingListEntity shoppingListEntity) {
    var fetchedEntity =
        this.shoppingListRepository
            .findById(shoppingListEntity.getId())
            .orElseThrow(() -> new ShoppingListNotFound("List with given id has not been found!"));
    fetchedEntity.setElements(shoppingListEntity.getElements());
    fetchedEntity.setTitle(shoppingListEntity.getTitle());
    this.shoppingListRepository.save(fetchedEntity);
    return fetchedEntity;
  }

  public ShoppingListEntity createShoppingListEntity(ShoppingListEntity shoppingListEntity) {
    this.shoppingListRepository.save(shoppingListEntity);
    return shoppingListEntity;
  }

  public void deleteShoppingList(int id) {
    this.shoppingListRepository
        .findById(id)
        .orElseThrow(() -> new ShoppingListNotFound("List with given id has not been found!"));
    this.shoppingListRepository.deleteById(id);
  }
}
