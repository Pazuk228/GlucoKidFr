package com.example.glucokidfr.data.dto;

import com.example.glucokidfr.domain.entities.Parent;

public class ParentDTO {
    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String phone;

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
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

    public Parent toDomain() {
        return new Parent(
                id,
                firstName,
                secondName,
                lastName,
                phone
        );
    }
}