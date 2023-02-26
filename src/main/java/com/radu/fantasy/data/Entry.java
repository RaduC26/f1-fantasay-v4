package com.radu.fantasy.data;

public class Entry {
    private final String name;
    private double price;
    private double points = 0.0;
    private String type;

    private double pricePerPoint = 0.0;

    public Entry(String name, double price, double points, String type) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getPoints() {
        return points;
    }

    public String getType() {
        return type;
    }

    public double getPricePerPoint() {
        return (price / points);
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
