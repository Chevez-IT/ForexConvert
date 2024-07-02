package com.forexconvert.services;

import com.forexconvert.models.ExChangeRatePair;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRate {
    private static final String API_ENDPOINT = "https://v6.exchangerate-api.com/v6/";
    private static final String API_KEY = "0ece03d64290c19dbef14c5d";

    private final HttpClient httpClient;
    private final Gson gson;

    public ExchangeRate() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String getAllCurrencies() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + API_KEY + "/codes"))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() == 200) {
            return httpResponse.body();
        } else {
            throw new RuntimeException("Error getting currency codes: " + httpResponse.statusCode() + " - " + httpResponse.body());
        }
    }

    public ExChangeRatePair getExchangeRateDetails(String sourceCurrency, String targetCurrency, Double amountCurrency) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + API_KEY + "/pair/" + sourceCurrency + "/" + targetCurrency + "/" + amountCurrency))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (httpResponse.statusCode() != 200) {
            throw new RuntimeException("Error getting exchange rate details: " + httpResponse.statusCode() + " - " + httpResponse.body());
        }

        return gson.fromJson(httpResponse.body(), ExChangeRatePair.class);
    }
}
