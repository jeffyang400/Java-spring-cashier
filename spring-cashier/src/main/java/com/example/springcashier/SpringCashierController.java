package com.example.springcashier;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.example.springcashier.model.Card;
import com.example.springcashier.model.Order;
import com.example.springcashier.model.Ping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Controller
@RequestMapping("/user/starbucks")
public class SpringCashierController {

    private @Value("${cashier.apikey}") String API_KEY ;
    private @Value("${cashier.apihost}") String API_HOST ;

    private Order order;

    @GetMapping
    public String getAction(@ModelAttribute("command") Command command,
                            Model model) {

        String message = "" ;

        command.setRegister( "5012349" ) ;
        message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order"+ "\n" ;

        // Host Information
        String server_ip = "" ;
        String host_name = "" ;
        try {
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") Command command,
                             @RequestParam(value="action", required=true) String action,
                             Errors errors, Model model, HttpServletRequest request) {

        String message = "" ;

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "" ;
        String apiMessage = "" ;

        log.info( "Action: " + action ) ;
        command.setRegister( command.getStores() ) ;
        log.info( "Command: " + command ) ;

        /* Process Post Action */
        if ( action.equals("Get Order") ) {
            try {
                //Get Drink from API
                resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister() +
                        "?apikey=" + API_KEY;

                ResponseEntity<Order> stringResponse = restTemplate.getForEntity(resourceUrl, Order.class);
                order = stringResponse.getBody();
                System.out.println(order.getStatus());

                if ( order.getOrderNumber() != null ) {
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Drink: " + order.getDrink() + "\n" +
                            "Milk:  " + order.getMilk() + "\n" +
                            "Size:  " + order.getSize() + "\n" +
                            "Total: " + order.getTotal() + "\n" +
                            "\n" +
                            "Register: " + order.getRegister() + "\n" +
                            "Status:   " + order.getStatus() + "\n" +
                            "Order Number: " + order.getOrderNumber();
                } else {
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Drink: " + order.getDrink() + "\n" +
                            "Milk:  " + order.getMilk() + "\n" +
                            "Size:  " + order.getSize() + "\n" +
                            "Total: " + order.getTotal() + "\n" +
                            "\n" +
                            "Register: " + order.getRegister() + "\n" +
                            "Status: Active Order Not Yet Paid\n";
                }

            } catch (HttpClientErrorException ex) {
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Currently No Active Orders\n\n" +
                        "Register: " + command.getRegister() + "\n" +
                        "Status:   " + "Ready for New Order"+ "\n" ;
            }

        }
        else if ( action.equals("Place Order") ) {

            resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister() +
                    "?apikey=" + API_KEY;
            try {
                /* Check if any order is active */
                ResponseEntity<Order> stringResponse = restTemplate.getForEntity(resourceUrl, Order.class);
                order = stringResponse.getBody();
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Must Clear Active Order First\n" +
                        "Click 'Get Order' to Refresh Active Order\n\n" +
                        "Register: " + command.getRegister() + "\n" +
                        "Status:   Active Order Currently Up";

            } catch ( HttpClientErrorException e ) {
                if ( command.getType().equals("none") || command.getMilk().equals("none") || command.getSize().equals("none")) {
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Please Make Sure to Select a Drink, Milk, & Size Option\n\n" +
                            "Register: " + command.getRegister() + "\n" +
                            "Status:   " + "Ready for New Order"+ "\n" ;
                } else {
                    order = new Order() ;
                    order.setDrink(command.getType());
                    order.setMilk(command.getMilk());
                    order.setSize(command.getSize());
                    HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(order);
                    ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class);
                    Order newOrder = newOrderResponse.getBody();
                    System.out.println(newOrder);
                    message = "Starbucks Reserved Order" + "\n\n" +
                            "Drink: " + newOrder.getDrink() + "\n" +
                            "Milk:  " + newOrder.getMilk() + "\n" +
                            "Size:  " + newOrder.getSize() + "\n" +
                            "Total: " + newOrder.getTotal() + "\n" +
                            "\n" +
                            "Register: " + newOrder.getRegister() + "\n" +
                            "Status:   " + newOrder.getStatus() + "\n";
                }
            }
        }
        else if ( action.equals("Clear Order") ) {
            try {
                resourceUrl = "http://" + API_HOST + "/order/register/" + command.getRegister() + "?apikey=" + API_KEY;
                restTemplate.delete(resourceUrl);
                message = "Starbucks Reserved Order" + "\n\n" +
                        "Order Cleared" + "\n\n" +
                        "Register: " + command.getRegister() + "\n" +
                        "Status:   " + "Ready for New Order" + "\n";
                command.setType("none");
                command.setMilk("none");
                command.setSize("none");
            } catch ( HttpClientErrorException ex ) {
                message = "Starbucks Reserved Order" + "\n\n" +
                        "All Active Orders are Cleared\n\n" +
                        "Register: " + command.getRegister() + "\n" +
                        "Status:   " + "Ready for New Order"+ "\n" ;
            }
        }
        command.setMessage( message ) ;

        String server_ip = "" ;
        String host_name = "" ;
        try {
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
        } catch (Exception e) { }

        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "user/starbucks" ;

    }


}