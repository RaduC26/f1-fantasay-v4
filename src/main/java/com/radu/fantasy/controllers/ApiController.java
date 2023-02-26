package com.radu.fantasy.controllers;

import com.radu.fantasy.calculations.Calculator;
import com.radu.fantasy.data.DataStore;
import com.radu.fantasy.data.Entry;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class ApiController {
    @Autowired
    DataStore dataStore;

    @PostConstruct
    public void initialize() {
       dataStore.readData();
    }

    @Autowired
    Calculator calculator;

    @Value("${spring.application.name}")
    String appName;



    @GetMapping("/api/entries")
    public ResponseEntity<String> getEntries() {
        return ResponseEntity.ok()
                .body(printData(dataStore.getEntries()));
    }

    @GetMapping("/api/driversRanked")
    public ResponseEntity<String> getRankedDrivers() {
        List<Entry> driversSorted = dataStore.getDrivers();
        driversSorted.sort(Comparator.comparing(Entry::getPricePerPoint));
        return ResponseEntity.ok()
                .body(printData(driversSorted));
    }

    @GetMapping("/api/bestTeam")
    @Cacheable("myCache")
    public ResponseEntity<List<Pair<List<Entry>, Double>>> getBestTeam() {
        return ResponseEntity.ok()
                .body(calculator.calculateBestTeam(dataStore, 100.0));
    }

    @GetMapping("/api/numberOfCombinations")
    public ResponseEntity<Integer> getNumberOfCombinations() {
        return ResponseEntity.ok().body(calculator.getNoOfCombinations());
    }

    @GetMapping("/api/executionTime")
    public ResponseEntity<Double> getExecutionTime() {
        return ResponseEntity.ok().body(calculator.getExecutionTime());
    }

//    @PostMapping("/api/bestTeamPartial")
//    public ResponseEntity<List<Entry>> getBestPartialTeam(@RequestBody HashMap<String, Object> requestBody) {
//        List<Entry> drivers = new ArrayList<>();
//
//        List<String> requestedDrivers = (List<String>) requestBody.get("entries");
//        requestedDrivers.forEach(requestedDriver -> drivers.add(dataStore.getEntry(requestedDriver)));
//
//        return ResponseEntity.ok()
//                .body(calculator.calculateBestTeam(dataStore, 100.0, drivers));
//    }

    public String printData(List<Entry> data) {
        StringBuilder result = new StringBuilder();
        for (Entry entry: data) {
            result.append(entry.getName() + " Price: ").append(entry.getPrice() + " Points: ").append(entry.getPoints() + " \n");
        }
        result.append("Total budget for this team combination: " + calculator.combinationBudget(data));
        return result.toString();
    }
}
