package com.radu.fantasy.controllers;

import com.radu.fantasy.calculations.Calculator;
import com.radu.fantasy.data.DataStore;
import com.radu.fantasy.data.Entry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SimpleController {
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

    @GetMapping("/")
    public String homePage(Model model) {
        DataStore dataStore = new DataStore();
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/entries")
    public ResponseEntity<String> getEntries() {
        return ResponseEntity.ok()
                .body(printData(dataStore.getEntries()));
    }

    @GetMapping("/driversRanked")
    public ResponseEntity<String> getRankedDrivers() {
        List<Entry> driversSorted = dataStore.getDrivers();
        driversSorted.sort(Comparator.comparing(Entry::getPricePerPoint));
        return ResponseEntity.ok()
                .body(printData(driversSorted));
    }

    @GetMapping("/bestTeam")
    public ResponseEntity<String> getBestTeam() {
        return ResponseEntity.ok()
                .body(printData(calculator.calculateBestTeam(dataStore, 100.0)));
    }

    @PostMapping("/bestTeamPartial")
    public ResponseEntity<String> getBestPartialTeam(@RequestBody HashMap<String, Object> requestBody) {
        List<Entry> drivers = new ArrayList<>();

        List<String> requestedDrivers = (List<String>) requestBody.get("drivers");
        requestedDrivers.forEach(requestedDriver -> drivers.add(dataStore.getEntry(requestedDriver)));

        return ResponseEntity.ok()
                .body(printData(calculator.calculateBestTeam(dataStore, 100.0, drivers)));
    }

    public String printData(List<Entry> data) {
        StringBuilder result = new StringBuilder();
        for (Entry entry: data) {
            result.append(entry.getName() + " Price: ").append(entry.getPrice() + " Points: ").append(entry.getPoints() + " \n");
        }
        result.append("Total budget for this team combination: " + calculator.combinationBudget(data));
        return result.toString();
    }
}
