package com.example.Drugbank;

public class AdverseEffect {
    private String description;
    private String proteinName;
    private String geneSymbol;
    private String uniportID;
    private String rsID;
    private String pubmedID;

    public AdverseEffect(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public String getProteinName() {
        return proteinName;
    }

    public String getPubmedID() {
        return pubmedID;
    }

    public String getRsID() {
        return rsID;
    }

    public String getUniportID() {
        return uniportID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public void setPubmedID(String pubmedID) {
        this.pubmedID = pubmedID;
    }

    public void setRsID(String rsID) {
        this.rsID = rsID;
    }

    public void setUniportID(String uniportID) {
        this.uniportID = uniportID;
    }
}
