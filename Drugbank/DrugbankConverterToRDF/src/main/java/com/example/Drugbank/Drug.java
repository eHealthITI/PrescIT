package com.example.Drugbank;

import java.util.ArrayList;

public class Drug {
    private ArrayList<Synonym> synonymArrayList;
    private String drugbankID;
    private String mechanismOfAction;
    private String name;
    private ArrayList<DDInteraction> ddInteractionArrayList;
    private String pharmacodynamics;
    private String indication;
    private String toxicity;
    private String state;
    private String description;
    private String CASNumber;
    private String UNII;
    private String halfLife;
    private String drugType;
    private String averageWeight;
    private String foodInteraction;
    private String keggDrug;
    private String pubchemSubstance;

    private ArrayList<String> atcCode;
    private ArrayList<String> groupArrayList;

    private ArrayList<Category> categoryArrayList;
    private ArrayList<Product> productArrayList;
    private ArrayList<AdverseEffect> adverseEffectArrayList;

    public Drug(String drugbankID){
        this.drugbankID = drugbankID;
    }

    public ArrayList<Synonym> getSynonymArrayList() { return synonymArrayList; }
    public void setSynonymArrayList(ArrayList<Synonym> synonymArrayList) {this.synonymArrayList = synonymArrayList;}
    public String getDrugbankID() { return drugbankID; }
    public void setMechanismOfAction(String mechanismOfAction){this.mechanismOfAction = mechanismOfAction;}
    public String getMechanismOfAction() { return mechanismOfAction; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DDInteraction> getDdInteractionArrayList() {
        return ddInteractionArrayList;
    }

    public void setDdInteractionArrayList(ArrayList<DDInteraction> ddInteractionArrayList) {
        this.ddInteractionArrayList = ddInteractionArrayList;
    }

    public String getPharmacodynamics() {
        return pharmacodynamics;
    }

    public void setPharmacodynamics(String pharmacodynamics) {
        this.pharmacodynamics = pharmacodynamics;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCASNumber() {
        return CASNumber;
    }

    public void setCASNumber(String CASNumber) {
        this.CASNumber = CASNumber;
    }

    public String getUNII() {
        return UNII;
    }

    public void setUNII(String UNII) {
        this.UNII = UNII;
    }

    public ArrayList<String> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(ArrayList<String> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    public ArrayList<String> getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(ArrayList<String> atcCode) {
        this.atcCode = atcCode;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public String getHalfLife() {
        return halfLife;
    }

    public void setHalfLife(String halfLife) {
        this.halfLife = halfLife;
    }

    public ArrayList<AdverseEffect> getAdverseEffectArrayList() {
        return adverseEffectArrayList;
    }

    public void setAdverseEffectArrayList(ArrayList<AdverseEffect> adverseEffectArrayList) {
        this.adverseEffectArrayList = adverseEffectArrayList;
    }

    public String getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(String averageWeight) {
        this.averageWeight = averageWeight;
    }

    public String getFoodInteraction() {
        return foodInteraction;
    }

    public void setFoodInteraction(String foodInteraction) {
        this.foodInteraction = foodInteraction;
    }

    public String getDrugType() {
        return drugType;
    }
    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }
    public String getKeggDrug() {
        return keggDrug;
    }
    public String getPubchemSubstance() {
        return pubchemSubstance;
    }

    public void setKeggDrug(String keggDrug) {
        this.keggDrug = keggDrug;
    }

    public void setPubchemSubstance(String pubchemSubstance) {
        this.pubchemSubstance = pubchemSubstance;
    }
}
