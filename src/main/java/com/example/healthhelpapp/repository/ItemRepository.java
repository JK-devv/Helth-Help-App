package com.example.healthhelpapp.repository;

import com.example.healthhelpapp.dto.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCategoryName(String categoryName);
}
