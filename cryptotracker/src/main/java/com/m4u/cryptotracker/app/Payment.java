package com.m4u.cryptotracker.app;

public class Payment implements Comparable<Payment> {

    private final String description;
    private double amountValue;
    private String currency;
    private final String service;

    public Payment(String description, double amount, String currency, String service) {
        this.description = description;
        this.amountValue = amount;
        this.currency = currency;
        this.service = service;
    }

    public String getDescription() {
        return description;
    }


    public double getAmountValue() {
        return amountValue;
    }

    public String getCurrency() {
        return currency;
    }

    public String getService() {
        return service;
    }

    @Override
    public int compareTo(Payment otherPayment) {
//        int valueComparison = Double.compare(this.amount.getAmountValue(), otherPayment.getAmountValue());
//
//        // Если числовые значения равны, сравниваем валюты
//        if (valueComparison == 0) {
//            return this.currency.compareTo(otherPayment.getCurrency());
//        }


        if (this.description == null && otherPayment.description == null) {
            return 0;
        } else if (this.description == null) {
            return -1;
        } else if (otherPayment.description == null) {
            return 1;
        } else {
            return this.description.compareTo(otherPayment.description);
        }
    }
}

