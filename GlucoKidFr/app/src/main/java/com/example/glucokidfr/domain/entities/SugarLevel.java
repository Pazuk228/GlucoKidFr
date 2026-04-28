package com.example.glucokidfr.domain.entities;

public class SugarLevel {
    private final String id;
    private final String childId;
    private final double value;
    private final String time;
    private final String extra;


    public SugarLevel(String id, String childId, double value, String time, String extra) {
        this.id = id;
        this.childId = childId;
        this.value = value;
        this.time = time;
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public String getChildId() {
        return childId;
    }

    public double getValue() {
        return value;
    }

    public String getExtra() {
        return extra;
    }

    public String getTime() {
        return time;
    }
}
