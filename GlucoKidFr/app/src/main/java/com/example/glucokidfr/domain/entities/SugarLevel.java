package com.example.glucokidfr.domain.entities;

public class SugarLevel {
    private final Long id;
    private final String childId;
    private final double value;
    private final String time;
    private final String extra;


    public SugarLevel(Long id, String childId, double value, String time, String extra) {
        this.id = id;
        this.childId = childId;
        this.value = value;
        this.time = time;
        this.extra = extra;
    }

    public Long getId() {
        return id;
    }

    public String getChildId() {
        return childId;
    }

    public double getValue() {
        return value;
    }

    public String getTime() {
        return time;
    }

    public String getExtra() {
        return extra;
    }
}
