package com.example.starbucksworker;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


/*
	https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#mapping.fundamentals
	https://www.baeldung.com/jpa-indexes
 */

@Entity
@Table(name = "STARBUCKS_DRINKS", indexes=@Index(name = "altIndex", columnList = "orderNumber", unique = true))
@Data
@RequiredArgsConstructor
class StarbucksDrink {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable=false)
    private String orderNumber ;
    private String status ;

}