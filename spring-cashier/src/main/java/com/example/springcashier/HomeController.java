package com.example.springcashier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    Controller to redirect from the root to the starbucks directory
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getAction(Model model) {
        return "redirect:" + "/user/starbucks";
    }

}
