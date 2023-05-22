package com.example.springstarbucksapi.repository;

/* https://docs.spring.io/spring-data/jpa/docs/2.4.6/reference/html/#repositories */

import com.example.springstarbucksapi.model.StarbucksDrink;
import com.example.springstarbucksapi.model.StarbucksOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder, Long> {

    @Query("SELECT starOrd FROM StarbucksOrder starOrd WHERE starOrd.register = :register")
    StarbucksOrder findByRegisterId(@Param("register")String regid) ;

    @Modifying
    @Query("DELETE FROM StarbucksOrder starOrd WHERE starOrd.register = :register")
    void deleteByRegId(@Param("register")String regid) ;
}


