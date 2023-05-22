package com.example.springstarbucksapi.rest;

import com.example.springstarbucksapi.model.StarbucksCard;
import com.example.springstarbucksapi.model.StarbucksOrder;
import com.example.springstarbucksapi.model.StarbucksDrink;
import com.example.springstarbucksapi.service.StarbucksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
    https://spring.io/guides/tutorials/rest/
    https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration
*/

@RestController
public class StarbucksDrinkController {

    // REF: https://www.moreofless.co.uk/spring-mvc-java-autowired-component-null-repository-service
    @Autowired private StarbucksService service ;

    static class Message {
        private String status;
        public String getStatus() {
            return status;
        }
        public void setStatus(String msg) {
            status = msg;
        }
    }

    /* Get Details of a Starbucks Order */
    @GetMapping("/drink/{orderNumber}")
    StarbucksDrink getActiveDrink(@PathVariable String orderNumber, HttpServletResponse response) {
        StarbucksDrink active = service.getDrink(orderNumber) ;
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drink Not Found!");
        }
    }

}
