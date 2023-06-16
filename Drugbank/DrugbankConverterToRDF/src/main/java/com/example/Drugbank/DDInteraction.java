package com.example.Drugbank;

public class DDInteraction {
    private String drugbankID;
    private String name;
    private String description;

    public DDInteraction(String name, String drugbankID, String description){
        this.description = description;
        this.drugbankID = drugbankID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDrugbankID() {
        return drugbankID;
    }
}
