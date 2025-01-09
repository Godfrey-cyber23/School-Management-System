package com.example.LTS_Plus.contact;

public class ContactData {
    private String name;
    private String contact;

    // Parameterized Constructor
    public ContactData(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
