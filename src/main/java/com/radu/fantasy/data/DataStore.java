package com.radu.fantasy.data;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataStore {
    private final List<Entry> entries = new ArrayList<>();

    public void readData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/radu/fantasy/data/RawData.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] components = line.split(",");
                String name = components[0];
                double price = Double.parseDouble(components[1]);
                double points = Double.parseDouble(components[2]);
                if (line.contains("Driver")) {
                    this.entries.add(new Entry(name.split(": ")[1], price, points,"driver"));
                } else {
                    this.entries.add(new Entry(name, price, points,"team"));
                }

                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Entry> getEntries() {
        return entries;
    }

    public Entry getEntry(String name) {
        for(Entry entry: entries) {
            if (entry.getName().contains(name)) {
                return entry;
            }
        }
        return null;
    }

    public List<Entry> getDrivers() {
        List<Entry> drivers = new ArrayList<>();
        for(Entry entry: entries) {
            if (entry.getType().equals("driver")) {
                drivers.add(entry);
            }
        }
        return drivers;
    }

    public List<Entry> getTeams() {
        List<Entry> teams = new ArrayList<>();
        for(Entry entry: entries) {
            if (entry.getType().equals("team")) {
                teams.add(entry);
            }
        }
        return teams;
    }
}
