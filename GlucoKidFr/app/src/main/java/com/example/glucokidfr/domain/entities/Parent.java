package com.example.glucokidfr.domain.entities;

public class Parent {
    private final Long id;
    private final String firstName;
    private final String secondName;
    private final String lastName;
    private final String phone;

    public Parent(Long id, String firstName, String secondName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}

