package com.example.springstarbucksapi.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


/*
	https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#mapping.fundamentals
	https://www.baeldung.com/jpa-indexes
 */

@Entity
@Table(name = "STARBUCKS_DRINKS", indexes=@Index(name = "altIndex", columnList = "orderNumber", unique = true))
@Data
@RequiredArgsConstructor
public class StarbucksDrink {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false)
    private String orderNumber ;
    private double total ;
    private String status ;

}
