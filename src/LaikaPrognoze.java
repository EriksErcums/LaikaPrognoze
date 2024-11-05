import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LaikaPrognoze {
    public void pieprasitDatus(){
        try{
            String pilseta = "Riga";
            // String url = "http://api.openweathermap.org/data/2.5/weather?q=" + pilseta + "&appid=" + apiKey;

            String url = "https://api.open-meteo.com/v1/forecast?latitude=56.946&longitude=24.1059&hourly=temperature_2m&forecast_days=1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> data(response))
            .join();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void data(String atbilde){
        try{
            JSONObject obj = new JSONObject(atbilde);
        
        //Debug 
        System.out.println(obj.toString(2));



        // String weatherDescription = obj.getJSONArray("weather").getJSONObject(0).getString("description");

        // double temperature = obj.getJSONObject("main").getDouble("temp");

        // System.out.println("Weather: " + weatherDescription);
        // System.out.printf("Temperature: %.2f °C%n", temperature);
        }
        catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }
}
