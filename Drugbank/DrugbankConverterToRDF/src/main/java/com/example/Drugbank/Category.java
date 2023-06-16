package com.example.Drugbank;


public class Category {
    private String categoryName;
    private String meshID;

    public Category(String categoryName, String meshID){
        this.categoryName = categoryName;
        this.meshID = meshID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getMeshID() {
        return meshID;
    }
}
