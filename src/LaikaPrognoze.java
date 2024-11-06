import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LaikaPrognoze {
    public void pieprasitDatus(){
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude=56.946&longitude=24.1059&hourly=temperature_2m&forecast_days=1";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> dataLaikaProg(response))
            .join();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void dataPilseta(String atbilde){
        try{
            JSONArray jsonArray = new JSONArray(atbilde);
            JSONObject obj = jsonArray.getJSONObject(0);
            System.out.println(obj.toString(2));

            double latitude = obj.getDouble("latitude");
            System.out.println(latitude);
        }
        catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }

    public void dataLaikaProg(String atbilde){
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

    public void vieta(String pilseta){
        try{
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + pilseta + "&count=1&language=en&format=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> dataPilseta(response))
            .join();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
