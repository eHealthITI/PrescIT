package com.example.Drugbank;

public class Product {
    private String name;
    private String labeller;
    private String dosageForm;
    private String strength;
    private String route;
    private String fdaAppNum;
    private String startedOn;
    private String endedOn;
    private String approved;
    private String country;
    private String source;

    public Product(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getApproved() {
        return approved;
    }

    public String getCountry() {
        return country;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public String getEndedOn() {
        return endedOn;
    }

    public String getFdaAppNum() {
        return fdaAppNum;
    }

    public String getLabeller() {
        return labeller;
    }

    public String getRoute() {
        return route;
    }

    public String getSource() {
        return source;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public String getStrength() {
        return strength;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public void setEndedOn(String endedOn) {
        this.endedOn = endedOn;
    }

    public void setFdaAppNum(String fdaAppNum) {
        this.fdaAppNum = fdaAppNum;
    }

    public void setLabeller(String labeller) {
        this.labeller = labeller;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
