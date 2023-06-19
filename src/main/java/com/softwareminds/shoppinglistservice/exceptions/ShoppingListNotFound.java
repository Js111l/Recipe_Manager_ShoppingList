package com.softwareminds.shoppinglistservice.exceptions;

public class ShoppingListNotFound extends RuntimeException {
  public ShoppingListNotFound(String msg) {
    super(msg);
  }
}
