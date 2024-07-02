package com.forexconvert.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionHistory {
    private static int nextId = 1;

    private final int id;
    private final String fromCurrency;
    private final String toCurrency;
    private final double amount;
    private final double conversionRate;
    private final double conversionResult;
    private final String dateTime;

    public ConversionHistory(String fromCurrency, String toCurrency, double amount, double conversionRate, double conversionResult) {
        this.id = nextId++;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.conversionRate = conversionRate;
        this.conversionResult = conversionResult;

        this.dateTime = LocalDateTime.now().toString();
    }


    public int getId() {
        return id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double getConversionResult() {
        return conversionResult;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + dateTime + "] " +
                amount + " " + fromCurrency + " -> " +
                conversionResult + " " + toCurrency +
                " (Rate: " + conversionRate + ")";
    }
}
