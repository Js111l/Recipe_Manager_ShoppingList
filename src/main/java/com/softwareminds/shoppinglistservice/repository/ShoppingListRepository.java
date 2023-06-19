package com.softwareminds.shoppinglistservice.repository;

import com.softwareminds.shoppinglistservice.entities.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity,Integer> {}
