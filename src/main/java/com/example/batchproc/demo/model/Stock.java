package com.example.batchproc.demo.model;

public class Stock {

    private int securityCode;
    private String issuerName;
    private String securityId;
    private String securityName;
    private String status;
    private String group;
    private double faceValue;
    private String ISIN;
    private String industry;
    private String instrument;

    public Stock() {

    }
    public Stock(int securityCode, String issuerName, String securityId, String securityName, String status,
                 String group, double faceValue, String ISIN, String industry, String instrument) {
        this.securityCode = securityCode;
        this.issuerName = issuerName;
        this.securityId = securityId;
        this.securityName = securityName;
        this.status = status;
        this.group = group;
        this.faceValue = faceValue;
        this.ISIN = ISIN;
        this.industry = industry;
        this.instrument = instrument;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public String getISIN() {
        return ISIN;
    }

    public void setISIN(String ISIN) {
        this.ISIN = ISIN;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
