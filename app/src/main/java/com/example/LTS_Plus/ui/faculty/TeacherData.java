package com.example.LTS_Plus.ui.faculty;

public class TeacherData {
    private String name;
    private final String jon;
    private String phone;
    private final String post;
    private String image;

    public TeacherData(String name, String jon, String phone, String post, String image) {
        this.name = name;
        this.jon = jon;
        this.phone = phone;
        this.post = post;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJon() {
        return jon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
