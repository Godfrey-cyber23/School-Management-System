package com.example.LTS_Plus.student_list;

public class StudentListData {
    private String name;
    private String phone;
    private final String address;
    private String image;

    public StudentListData(String name, String phone, String address, String image) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
