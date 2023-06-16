package com.example.Drugbank;

public class Synonym {
    private String synonymName;
    private String language;
    private String coder;

    public Synonym(String language, String coder, String synonymName){
        this.language = language;
        this.coder = coder;
        this.synonymName = synonymName;
    }

    public String getSynonymName() { return synonymName; }
    public String getCoder() { return coder; }
    public String getLanguage() { return language; }
}
