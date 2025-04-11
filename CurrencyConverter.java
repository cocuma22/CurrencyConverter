import java.util.*;

public class CurrencyConverter {
    public static void main(String[] args) {
        Map<String, Double> rates = Map.of(
                "USD", 0.88,
                "EUR", 1.13,
                "GBP", 0.80);

        Scanner scanner = new Scanner(System.in);

        // starting currency
        System.out.print("Insert the initial currency: ");
        String startingCurrency = scanner.nextLine().toUpperCase();

        // ending currency
        System.out.print("Insert the final currency: ");
        String endingCurrency = scanner.nextLine().toUpperCase();

        // value
        System.out.print("Insert the amount: ");
        double amount = scanner.nextDouble();

        // response
        if (rates.containsKey(startingCurrency) && rates.containsKey(endingCurrency)) {
            double result = amount * rates.get(endingCurrency) / rates.get(startingCurrency);
            System.out.printf("%.2f %s = %.2f %s\n", amount, startingCurrency, result, endingCurrency);
        } else {
            System.out.println("Currency non suppoted.");
        }

    }
}
