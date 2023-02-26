package com.radu.fantasy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontEndController {
    @Autowired
    ApiController apiController;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/bestTeam")
    public ModelAndView displayBestTeam() {
        ModelAndView mav = new ModelAndView("bestTeam");
        mav.addObject("tableOfEntries", apiController.getBestTeam().getBody());
        mav.addObject("numberOfCombinations", apiController.getNumberOfCombinations().getBody());
        mav.addObject("executionTime", String.format("%.3f", apiController.getExecutionTime().getBody()));
        return mav;
    }
}
