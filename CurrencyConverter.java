import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            // starting currency
            System.out.print("Insert the initial currency: ");
            String startingCurrency = scanner.nextLine().toUpperCase();

            // ending currency
            System.out.print("Insert the final currency: ");
            String endingCurrency = scanner.nextLine().toUpperCase();

            // amount to convert
            System.out.print("Insert the amount: ");
            String amountStr = scanner.nextLine().replace(",", ".").trim();
            double amount = 0;

            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    System.out.println("The amount must be greater than 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number, es. 100.00");
                return;
            }

            // get API key
            String apiKey = Config.getApiKey();
            if (apiKey == null || apiKey.isEmpty()) {
                System.out.println("API key not found.");
                return;
            }

            try {
                // forse correct decimal format
                String amountFormatted = String.format(Locale.US, "%.2f", amount);

                // make string request
                String requestStr = String.format(
                        "https://api.exchangerate.host/convert?from=%s&to=%s&amount=%s&access_key=%s",
                        startingCurrency, endingCurrency, amountFormatted, apiKey);

                // make url and send request
                URL requestUrl = new URI(requestStr).toURL();
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("GET");

                // get response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // JSON conversion
                JSONObject responseJson = new JSONObject(response.toString());

                if (responseJson.getBoolean("success")) {
                    double result = responseJson.getDouble("result");
                    System.out.printf("%.2f %s = %.2f %s\n", amount, startingCurrency, result, endingCurrency);
                } else {
                    System.out.println("Error");
                    if (responseJson.has("error")) {
                        JSONObject error = responseJson.getJSONObject("error");
                        System.out.println("Detail: " + error.optString("info", "No additional information available"));
                    }
                }

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
