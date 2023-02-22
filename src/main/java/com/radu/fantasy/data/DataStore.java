package com.radu.fantasy.data;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataStore {
    private List<Entry> drivers = new ArrayList<>();
    private List<Entry> teams = new ArrayList<>();

    public void readData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/radu/fantasy/data/RawData.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] components = line.split(",");
                String name = components[0];
                Double price = Double.valueOf(components[1]);
                if (line.contains("Driver")) {
                    this.drivers.add(new Entry(name.split(": ")[1], price));
                } else {
                    this.teams.add(new Entry(name, price));
                }

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Entry> getDrivers() {
        return drivers;
    }
    public List<Entry> getTeams() {
        return teams;
    }
    public List<Entry> getEntries() {
        List<Entry> entries = new ArrayList<>(drivers.size() + teams.size());
        entries.addAll(drivers);
        entries.addAll(teams);
        return entries;
    }

    public Entry getEntry(String name) {
        for(Entry entry: drivers) {
            if (entry.getName().contains(name)) {
                return entry;
            }
        }
        for(Entry entry: teams) {
            if (entry.getName().contains(name)) {
                return entry;
            }
        }
        return null;
    }
}
