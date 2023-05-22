package com.example.starbucksworker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StarbucksDrinkRepository extends CrudRepository<StarbucksDrink, Integer> {

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
    // https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#repositories.query-methods.query-creation

    List<StarbucksDrink> findByOrderNumber(String orderNumber) ;

}