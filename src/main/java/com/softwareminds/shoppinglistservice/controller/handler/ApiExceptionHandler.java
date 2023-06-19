package com.softwareminds.shoppinglistservice.controller.handler;

import com.softwareminds.shoppinglistservice.exceptions.ShoppingListNotFound;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler
  public ProblemDetail shoppingListNotFoundHandler(ShoppingListNotFound exception) {
    var pb = ProblemDetail.forStatus(404);
    pb.setTitle("SHOPPING_LIST_NOT_FOUND");
    pb.setDetail(exception.getMessage());
    return pb;
  }
}
