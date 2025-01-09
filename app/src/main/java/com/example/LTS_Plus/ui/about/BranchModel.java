package com.example.LTS_Plus.ui.about;

public class BranchModel extends BranchAdapter {

    private final int img;
    private String title;
    private final String description;

    public BranchModel(int img, String title, String description) {
        this.img = img;
        this.title = title;
        this.description = description;
    }


    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

}
