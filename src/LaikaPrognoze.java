import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LaikaPrognoze {

    public String pilseta;
    public String lat;
    public String lon;
    public String temp;
    public String mitrums;
    public String vejaAtrums;

    //Bez  lietotaja pisletas pieprasijuma, default Liepaja
    public void iegutDatus(){
        iegutPilsetasKoordinatas("Liepaja");
        pieprasitDatus();
    }

    //Pieprasa laikapstaklus no API
    private void pieprasitDatus(){
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + this.lat + "&longitude=" + this.lon + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> iegutLaikapstaklus(response))
            .join();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //No API pieprasijuma izvel laikapstaklus
    private void iegutLaikapstaklus(String atbilde){
        try{
            JSONObject obj = new JSONObject(atbilde);
        
            System.out.println(obj.toString());
        
        }
        catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }

    //Atrod lietotaja dotas pilsetas koordinatas no API
    private void iegutPilsetasKoordinatas(String pilseta){
        this.pilseta = pilseta;
        try{
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + pilseta + "&count=1&language=en&format=json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> pilsetasKoordinatas(response))
            .join();
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //No API pieprasijuma izvelk lat un lon
    private void pilsetasKoordinatas(String atbilde){
        try{
            JSONObject jsonAtbilde = new JSONObject(atbilde);
            JSONArray resultsArray = jsonAtbilde.getJSONArray("results");

            jsonAtbilde = resultsArray.getJSONObject(0);

            this.lat = jsonAtbilde.getString("latitude");
            this.lon = jsonAtbilde.getString("longitude");

            //Debug
            System.out.println(": Latitude = " + this.lat + ", Longitude = " + this.lon);
            
        }
        catch(JSONException e){
            System.out.println(e.getMessage());
        }
    }
}
