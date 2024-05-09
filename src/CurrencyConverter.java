
import javax.net.ssl.HttpsURLConnection;
import java.util.Scanner;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CurrencyConverter {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/e0d69557171d30d8d68c934e/latest/USD";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

                     System.out.println( "Escolha uma opção:\n"   +
                                   " 1. Converter USD para EUR\n" +
                                   " 2. Converter EUR para USD\n" +
                                   " 3. Converter USD para GBP\n" +
                                   " 4. Converter GBP para USD\n" +
                                   " 5. Converter EUR para GBP\n" +
                                   " 6. Converter GBP para EUR\n" +
                                   " 7. Sair\n");

            System.out.print("Digite o número da opção desejada: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    convertCurrency("USD", "EUR");
                    break;
                case 2:
                    convertCurrency("EUR", "USD");
                    break;
                case 3:
                    convertCurrency("USD", "GBP");
                    break;
                case 4:
                    convertCurrency("GBP", "USD");
                    break;
                case 5:
                    convertCurrency("EUR", "GBP");
                    break;
                case 6:
                    convertCurrency("GBP", "EUR");
                    break;
                case 7:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void convertCurrency(String from, String to) {
        try {
            URL url = new URL(API_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsing JSON response
            String jsonResponse = response.toString();
            double exchangeRate = Double.parseDouble(jsonResponse.split("\"" + to + "\":")
                    [1].split(",")[0]);
            System.out.println("Taxa de câmbio atual de " + from + " para " + to + ":" +
                    " " + exchangeRate);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o valor em " + from + " que deseja converter para " +
                    " " + to + ": ");
            double amount = scanner.nextDouble();

            double convertedAmount = amount * exchangeRate;
            System.out.println(amount + " " + from + " equivale a " +
                    convertedAmount + " " + to);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao converter a moeda: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

