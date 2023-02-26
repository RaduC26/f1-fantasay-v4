package com.radu.fantasy.calculations;

import org.apache.commons.lang3.tuple.Pair;
import com.radu.fantasy.data.DataStore;
import com.radu.fantasy.data.Entry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Calculator {
    int noOfCombinations;
    double executionTime;

    public List<Pair<List<Entry>, Double>> calculateBestTeam(DataStore data, Double budget) {
        noOfCombinations = 0;
        long startTime = System.nanoTime();
        List<Pair<List<Entry>, Double>> bestCombinations = new ArrayList<>();
        PriorityQueue<Pair<List<Entry>, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Pair::getRight));

        int combinationSize = 7;

        generateCombinations(data, 0, new ArrayList<>(), combinationSize, budget, queue);

        bestCombinations.addAll(queue);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  // in nanoseconds
        executionTime = (double)duration / 1_000_000_000.0;  // convert to seconds

        return bestCombinations.stream().limit(3).collect(Collectors.toList());
    }

    private void generateCombinations(DataStore data, int startIndex, List<Entry> combination, int combinationSize, Double budget, PriorityQueue<Pair<List<Entry>, Double>> queue) {
        if (combination.size() == combinationSize) {
            if (!combinationIsValid(combination)) return;
            Double combinationBudget = combinationBudget(combination);
            if (combinationBudget <= budget) {
                queue.offer(Pair.of(new ArrayList<>(combination), combinationBudget));
                if (queue.size() > 3) {
                    boolean flag = false;
                    for (Pair<List<Entry>, Double> queueElement : queue) {
                        if (queueElement.getRight() < combinationBudget) {
                            queue.poll();
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) queue.remove(Pair.of(new ArrayList<>(combination), combinationBudget));
                }
            }
            return;
        }

        for (int i = startIndex; i < data.getEntries().size(); i++) {
            combination.add(data.getEntries().get(i));
            generateCombinations(data, i + 1, combination, combinationSize, budget, queue);
            combination.remove(combination.size() - 1);
        }
    }

    public Double combinationBudget(List<Entry> combination) {
        double sum = 0.0;
        for (Entry entry: combination) {
            sum = sum + entry.getPrice();
        }
        return sum;
    }

    public Boolean combinationIsValid(List<Entry> combination) {
        int teamNo = 0;
        for (Entry entry: combination) {
            if (entry.getType().equals("team")) teamNo += 1;
        }
        if (teamNo == 2) {
            noOfCombinations += 1;
            return true;
        }
        return false;
    }

    public int getNoOfCombinations() {
        return noOfCombinations;
    }

    public double getExecutionTime() {
        return executionTime;
    }
}
