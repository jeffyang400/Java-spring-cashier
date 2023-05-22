package com.example.starbucksworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RabbitListener(queues = "drink")
public class StarbucksDrinkWorker {

    private static final Logger log = LoggerFactory.getLogger(StarbucksDrinkWorker.class);

    @Autowired
    private StarbucksDrinkRepository drinkRepository;

    @RabbitHandler
    public void processStarbucksDrink(String drinkNumber) {
        log.info( "\nReceived  Drink # " + drinkNumber + "\n" ) ;

        // Sleeping to simulate drink making
        try {
            Thread.sleep(120000); // 60 seconds
        } catch (Exception e) {}

        List<StarbucksDrink> list = drinkRepository.findByOrderNumber( drinkNumber ) ;

        if (!list.isEmpty()) {
            StarbucksDrink drink = list.get(0);
            drink.setStatus ( "FULFILLED" ) ;
            drinkRepository.save(drink) ;
            log.info( "\nProcessed Drink # " + drinkNumber  + "\n" );
        } else {
            log.info( "\n>>>>[ERROR] Drink # " + drinkNumber + " Not Found!\n" );
        }

    }
}
