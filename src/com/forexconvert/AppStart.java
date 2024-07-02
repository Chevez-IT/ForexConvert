package com.forexconvert;

import com.forexconvert.models.ConversionHistory;
import com.forexconvert.models.Currency;
import com.forexconvert.models.ExChangeRatePair;
import com.forexconvert.services.ExchangeRate;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppStart {
    private final Scanner scanner;
    private final List<ConversionHistory> conversionHistories;


    public AppStart() {
        this.scanner = new Scanner(System.in);
        this.conversionHistories = new ArrayList<>();

        loadConversionHistoriesFromFile();
    }

    private void loadConversionHistoriesFromFile() {
        try (FileReader fileReader = new FileReader("conversion_histories.json")) {
            JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();

            Gson gson = new GsonBuilder().create();
            Type conversionHistoryListType = new TypeToken<List<ConversionHistory>>() {}.getType();
            List<ConversionHistory> loadedHistories = gson.fromJson(jsonArray, conversionHistoryListType);

            if (loadedHistories != null) {
                conversionHistories.addAll(loadedHistories);
                System.out.println("Conversion histories loaded successfully from file.");
            }
        } catch (IOException e) {
            System.out.println("No existing conversion history file found. Starting with an empty history.");
        }
    }


    public void start() throws Exception {
        String choice;
        System.out.println("*****************************************************************************");
        System.out.println("*                       WELCOME TO CURRENCY CONVERTER                       *");
        System.out.println("*****************************************************************************");

        do {
            System.out.println("-------------------------------------MENU------------------------------------");
            System.out.println("                           [1] Convert currency                              ");
            System.out.println("                      [2] Show available currencies                          ");
            System.out.println("                        [3] Show conversion history                          ");
            System.out.println("                                  [x] Exit                                   ");
            System.out.println("-----------------------------------------------------------------------------");
            System.out.print("Option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    appConverter();
                    break;
                case "2":
                    appCurrency();
                    break;
                case "3":
                    appHistory();
                    break;
                case "x":
                    appClose();
                    break;
                default:
                    System.out.println("Invalid option, please try again---------------------------------------------");
                    break;
            }
        } while (!choice.equals("x"));
        scanner.close();
    }

    private void appConverter() throws IOException, InterruptedException {
        String converterMenu;
        ExchangeRate exchangeRate = new ExchangeRate();
        List<Currency> currencyList = parseCurrencyResponse(exchangeRate.getAllCurrencies());

        do {
            System.out.println("----------------------------------CONVERTER----------------------------------");

            System.out.print("Enter the currency code to convert from: ");
            String fromCurrency = scanner.nextLine().toUpperCase();

            while (!isValidCurrencyCode(fromCurrency, currencyList)) {
                System.out.print("Invalid currency code. Please enter a valid currency code: ");
                fromCurrency = scanner.nextLine().toUpperCase();
            }

            System.out.print("Enter the currency code to convert to: ");
            String toCurrency = scanner.nextLine().toUpperCase();

            while (!isValidCurrencyCode(toCurrency, currencyList)) {
                System.out.print("Invalid currency code. Please enter a valid currency code: ");
                toCurrency = scanner.nextLine().toUpperCase();
            }

            System.out.print("Enter the amount: ");
            double amount = getValidAmount(scanner);

            ExChangeRatePair response = exchangeRate.getExchangeRateDetails(fromCurrency, toCurrency, amount);

            conversionHistories.add(new ConversionHistory(fromCurrency, toCurrency, amount, response.getConversionRate(), response.getConversionResult()));

            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("Conversion Rate: " + response.getConversionRate());
            System.out.println("Conversion Result: " + response.getConversionResult());

            do{
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("[1] Convert another amount  |  [2] Back to main menu  |  [x] Exit application");
                System.out.println("-----------------------------------------------------------------------------");
                System.out.print("Option: ");
                converterMenu = scanner.nextLine();

                switch (converterMenu){
                    case "1", "2":
                        break;
                    case "x":
                        appClose();
                    default:
                        System.out.println("Invalid option, please try again---------------------------------------------");
                }
            }while(!converterMenu.equals("1") && !converterMenu.equals("2") && !converterMenu.equals("x") );


        } while (!converterMenu.equals("2"));
    }

    private void appCurrency() throws Exception {
        String currencyMenu;
        ExchangeRate exchangeRate = new ExchangeRate();
        String currencies = exchangeRate.getAllCurrencies();
        List<Currency> currencyList = parseCurrencyResponse(currencies);

        int pageSize = 10;
        int currentPage = 0;
        do {

            System.out.println("-----------------------------AVAILABLE CURRENCIES----------------------------");
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, currencyList.size());

            for (int i = start; i < end; i++) {
                System.out.println(currencyList.get(i).toString());
            }
            do{
                System.out.println("-----------------------------------------------------------------------------");

                if (start == 0 && end >= currencyList.size()) {
                    System.out.println("[b] Back to main menu  |  [x] Exit");
                } else if (start == 0) {
                    System.out.println("[n] Next page  |  [b] Back to main menu  |  [x] Exit");
                } else if (end >= currencyList.size()) {
                    System.out.println("[p] Previous page  |  [b] Back to main menu  |  [x] Exit");
                } else {
                    System.out.println("[n] Next page  |  [p] Previous page  |  [b] Back to main menu  |  [x] Exit");
                }

                System.out.println("-----------------------------------------------------------------------------");
                System.out.print("Option: ");
                currencyMenu = scanner.nextLine();

                switch (currencyMenu){
                    case "n":
                        if (end < currencyList.size()) {
                            currentPage++;
                        }
                        break;
                    case "p":
                        if (currentPage > 0) {
                        currentPage--;
                        }
                        break;
                    case "b":
                        break;
                    case "x":
                        appClose();
                        break;
                    default:
                        System.out.println("Invalid option, please try again---------------------------------------------");
                        break;
                }
            }while(!currencyMenu.equals("n") && !currencyMenu.equals("p") && !currencyMenu.equals("b") && !currencyMenu.equals("x"));

        }while(!currencyMenu.equals("b"));
    }


    private boolean isValidCurrencyCode(String code, List<Currency> currencyList) {
        return currencyList.stream().anyMatch(currency -> currency.code().equalsIgnoreCase(code));
    }

    private double getValidAmount(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount. Please enter a valid number: ");
            }
        }
    }

    private void appHistory() {
        String historyMenu;
        do {
            if (conversionHistories.isEmpty()) {
                System.out.println("---------------------------CONVERSION HISTORY IS EMPTY------------------------");
            } else {
                System.out.println("-----------------------------CONVERSION HISTORY------------------------------");
                for (ConversionHistory history : conversionHistories) {
                    System.out.println(history.toString());
                }
            }

            do{
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("[r] Repeat  |[b] Back to main menu  |  [x] Exit");
                System.out.println("-----------------------------------------------------------------------------");
                System.out.print("Option: ");
                historyMenu = scanner.nextLine();
                switch (historyMenu){
                    case "b", "r":
                        break;
                    case "x":
                        appClose();
                        break;
                    default:
                        System.out.println("Invalid option, please try again---------------------------------------------");
                        break;
                }
            }while(!historyMenu.equals("b") && !historyMenu.equals("x") && !historyMenu.equals("r") );
        }while (!historyMenu.equals("b"));
    }

    private List<Currency> parseCurrencyResponse(String currenciesResponse) {
        List<Currency> currencies = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(currenciesResponse).getAsJsonObject();

        if (jsonObject.has("supported_codes")) {
            JsonArray supportedCodesArray = jsonObject.getAsJsonArray("supported_codes");

            for (JsonElement jsonElement : supportedCodesArray) {
                JsonArray currencyArray = jsonElement.getAsJsonArray();
                if (currencyArray.size() >= 2) {
                    String code = currencyArray.get(0).getAsString();
                    String name = currencyArray.get(1).getAsString();
                    Currency currency = new Currency(code, name);

                    currencies.add(currency);
                }
            }
        }

        return currencies;
    }

    private void appClose() {
        saveConversionHistories();
        System.out.println("Exiting----------------------------------------------------------------------");
        System.exit(0);
    }

    private void saveConversionHistories() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();

        for (ConversionHistory history : conversionHistories) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id", history.getId());
            jsonObject.addProperty("fromCurrency", history.getFromCurrency());
            jsonObject.addProperty("toCurrency", history.getToCurrency());
            jsonObject.addProperty("amount", history.getAmount());
            jsonObject.addProperty("conversionRate", history.getConversionRate());
            jsonObject.addProperty("conversionResult", history.getConversionResult());
            jsonObject.addProperty("dateTime", history.getDateTime().toString());

            jsonArray.add(jsonObject);
        }

        try (FileWriter fileWriter = new FileWriter("conversion_histories.json")) {
            gson.toJson(jsonArray, fileWriter);
            System.out.println("Conversion histories saved to conversion_histories.json");
        } catch (IOException e) {
            System.out.println("Error saving conversion histories to file: " + e.getMessage());
        }
    }

}
