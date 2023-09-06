package com.softwareminds.shoppinglistservice.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ShoppingListEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;
  @ElementCollection private List<String> elements;
}
