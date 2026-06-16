package com.example.glucokidfr.domain.entities;

public class Child {
    private final Long id;
    private final String parentId;
    private final String firstName;
    private final String secondName;
    private final String lastName;
    private final String phone;
    private final String password;

    public Child(Long id, String parentId, String firstName, String secondName, String lastName, String phone, String password) {
        this.id = id;
        this.parentId = parentId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() { return password; }

    public String getParentId() {
        return parentId;
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
