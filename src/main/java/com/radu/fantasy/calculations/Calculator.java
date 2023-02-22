package com.radu.fantasy.calculations;

import com.radu.fantasy.data.DataStore;
import com.radu.fantasy.data.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Calculator {
    private List<Entry> bestTeam = new ArrayList<>();

    public List<Entry> calculateBestTeam(DataStore data, Double budget) {
        double bestBudget = 0;
        List<Entry> combination = new ArrayList<>();
        double combinationBudget;
        System.out.println("Calculating best team...");

        for (int a = 0; a < data.getDrivers().size(); a++) {
            for (int b = 1; b < data.getDrivers().size(); b++) {
                for (int c = 2; c < data.getDrivers().size(); c++) {
                    for (int d = 3; d < data.getDrivers().size(); d++) {
                        for (int e = 4; e < data.getDrivers().size(); e++) {
                            for (int f = 0; f < data.getTeams().size(); f++) {
                                for (int g = 1; g < data.getTeams().size(); g++) {
                                    combination.add(data.getDrivers().get(a));
                                    combination.add(data.getDrivers().get(b));
                                    combination.add(data.getDrivers().get(c));
                                    combination.add(data.getDrivers().get(d));
                                    combination.add(data.getDrivers().get(e));
                                    combination.add(data.getTeams().get(f));
                                    combination.add(data.getTeams().get(g));

                                    combinationBudget = combinationBudget(combination);

                                    if (combinationBudget > bestBudget && combinationBudget <= budget) {
                                        bestTeam = new ArrayList<>(combination);
                                        bestBudget = combinationBudget;
                                    }

                                    combination.clear();
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Finished calculating best team.");

        return bestTeam;
    }

    public List<Entry> calculateBestTeam(DataStore data, Double budget, List<Entry> drivers) {
        double bestBudget = 0;
        List<Entry> combination = new ArrayList<>();
        double combinationBudget;
        System.out.println("Calculating best team...");

        for (int a = 0; a < data.getDrivers().size(); a++) {
            for (int b = 1; b < data.getDrivers().size(); b++) {
                for (int c = 2; c < data.getDrivers().size(); c++) {
                    for (int d = 3; d < data.getDrivers().size(); d++) {
                        for (int e = 4; e < data.getDrivers().size(); e++) {
                            for (int f = 0; f < data.getTeams().size(); f++) {
                                for (int g = 1; g < data.getTeams().size(); g++) {
                                    combination.add(data.getDrivers().get(a));
                                    combination.add(data.getDrivers().get(b));
                                    combination.add(data.getDrivers().get(c));
                                    combination.add(data.getDrivers().get(d));
                                    combination.add(data.getDrivers().get(e));
                                    combination.add(data.getTeams().get(f));
                                    combination.add(data.getTeams().get(g));

                                    boolean driversInTheCombination = true;

                                    for (Entry entry : drivers) {
                                        if (!combination.contains(entry)) {
                                            driversInTheCombination = false;
                                            break;
                                        }
                                    }

                                    if (driversInTheCombination) {
                                        combinationBudget = combinationBudget(combination);

                                        if (combinationBudget > bestBudget && combinationBudget <= budget) {
                                            bestTeam = new ArrayList<>(combination);
                                            bestBudget = combinationBudget;
                                        }
                                    }
                                    combination.clear();
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Finished calculating best team.");

        return bestTeam;
    }

    public Double combinationBudget(List<Entry> combination) {
        double sum = 0.0;
        for (Entry entry: combination) {
            sum = sum + entry.getPrice();
        }
        return sum;
    }

}
