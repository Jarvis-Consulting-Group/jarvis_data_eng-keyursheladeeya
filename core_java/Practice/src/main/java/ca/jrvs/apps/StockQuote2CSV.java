package ca.jrvs.apps;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class StockQuote2CSV {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        //REST API URL
        String url = "https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=MSFT&datatype=json";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", "5bac1a4454msh30257ed0cdb1e3dp1934ddjsn562f8329ddd0")
                .addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                String csvFileName = "stock_quotes_data.csv";

                try (FileWriter writer = new FileWriter(csvFileName)) {
                    // Write CSV header
                    writer.write("01. symbol,02. open,03. high,04. low,05. price,06. Volume,07. latest trading day," +
                            "08. previous close,09. change, 10. change percent\n");

                    // Parse JSON response into a Java object
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONObject globalQuote = jsonObject.getJSONObject("Global Quote");

                    // Extract fields from the JSON object to put into CSV
                    String symbolValue = globalQuote.getString("01. symbol");
                    String openValue = globalQuote.getString("02. open");
                    String highValue = globalQuote.getString("03. high");
                    String lowValue = globalQuote.getString("04. low");
                    String priceValue = globalQuote.getString("05. price");
                    String volumeValue = globalQuote.getString("06. volume");
                    String latestTradingDay = globalQuote.getString("07. latest trading day");
                    String previousCloseValue = globalQuote.getString("08. previous close");
                    String changeValue = globalQuote.getString("09. change");
                    String changePercentValue = globalQuote.getString("10. change percent");

                    // Write data into CSV
                    writer.write(symbolValue + "," + openValue + "," + highValue + "," +
                            lowValue + "," + priceValue + "," + volumeValue + "," +
                            latestTradingDay + "," + previousCloseValue + "," +
                            changeValue + "," + changePercentValue + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed Request: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
