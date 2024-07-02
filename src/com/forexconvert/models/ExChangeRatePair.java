package com.forexconvert.models;

import com.google.gson.annotations.SerializedName;

public class ExChangeRatePair {

    private String result;
    @SerializedName("conversion_result")
    private double conversionResult;
    @SerializedName("time_next_update_unix")
    private String timeNextUpdateUnix;
    @SerializedName("target_code")
    private String targetCode;
    @SerializedName("time_last_update_utc")
    private String timeNextUpdateutc;
    private String documentation;
    @SerializedName("time_last_update_unix")
    private String timeLastUpdateUnix;
    @SerializedName("base_code")
    private String baseCode;
    private String timeLastUpdateutc;
    @SerializedName("terms_of_use")
    private String termsOfUse;
    @SerializedName("conversion_rate")
    private double conversionRate;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getConversionResult() {
        return conversionResult;
    }

    public void setConversionResult(double conversionResult) {
        this.conversionResult = conversionResult;
    }

    public String getTimeNextUpdateUnix() {
        return timeNextUpdateUnix;
    }

    public void setTimeNextUpdateUnix(String timeNextUpdateUnix) {
        this.timeNextUpdateUnix = timeNextUpdateUnix;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTimeNextUpdateutc() {
        return timeNextUpdateutc;
    }

    public void setTimeNextUpdateutc(String timeNextUpdateutc) {
        this.timeNextUpdateutc = timeNextUpdateutc;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public String getTimeLastUpdateUnix() {
        return timeLastUpdateUnix;
    }

    public void setTimeLastUpdateUnix(String timeLastUpdateUnix) {
        this.timeLastUpdateUnix = timeLastUpdateUnix;
    }

    public String getTimeLastUpdateutc() {
        return timeLastUpdateutc;
    }

    public void setTimeLastUpdateutc(String timeLastUpdateutc) {
        this.timeLastUpdateutc = timeLastUpdateutc;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
