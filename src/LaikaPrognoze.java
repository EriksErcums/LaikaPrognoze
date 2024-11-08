import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LaikaPrognoze {

    public String pilseta = "";
    public String lat = "0";
    public String lon = "0";
    public String temp = "0";
    public String mitrums = "0";
    public String vejaAtrums = "0";
    public String kluda = "";
    public Boolean lietus = false;
    public Boolean sniegs = false;
    public Boolean makonains = false;

    //Bez  lietotaja pisletas pieprasijuma, default Liepaja
    public void iegutDatus(){
        this.kluda = ""; //Nodzest kludu

        if(this.pilseta.isEmpty())
            iegutPilsetasKoordinatas("Liepaja");
        else
            iegutPilsetasKoordinatas(this.pilseta);

        pieprasitDatus();
    }

    public void iegutDatus(String pilseta){
        this.kluda = ""; //Nodzest kludu

        iegutPilsetasKoordinatas(pilseta);

        pieprasitDatus();
    }

    //Pieprasa laikapstaklus no API
    private void pieprasitDatus(){
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + this.lat  + "&longitude=" + this.lon + "&current=temperature_2m,relative_humidity_2m,rain,snowfall,cloud_cover,wind_speed_10m";

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
            System.out.println("\n PIEPRASIT DATUS ERROR: " + e.getMessage());
            this.kluda = "COULD NOT CONNECT TO WEATHER FORECAST API!";
        }
    }

    //No API pieprasijuma izvel laikapstaklus
    private void iegutLaikapstaklus(String atbilde){
        try{
            JSONObject jsonAtbilde = new JSONObject(atbilde);
            jsonAtbilde = jsonAtbilde.getJSONObject("current");
            System.out.println((jsonAtbilde.toString()));

            this.temp = Double.toString(jsonAtbilde.getDouble("temperature_2m"));
            this.mitrums = Double.toString(jsonAtbilde.getDouble("relative_humidity_2m"));
            this.vejaAtrums = Double.toString(jsonAtbilde.getDouble("wind_speed_10m"));
            
            Double rain = jsonAtbilde.getDouble("rain");
            this.lietus = (rain == 0) ? false : true;

            Double sniegs = jsonAtbilde.getDouble("snowfall");
            this.sniegs = (sniegs == 0) ? false : true;

            Double makonains = jsonAtbilde.getDouble("cloud_cover");
            this.makonains = (makonains <= 25) ? false : true;
            

            //Debug
            System.out.println("Temp: " + this.temp
                + " Mitrums: " + this.mitrums
                + " Veja atrums: " + this.vejaAtrums
                + " Lietus: " + this.lietus
                + " Sniegs: " + this.sniegs
                + " Makoni: " + this.makonains);
        
        }
        catch(JSONException e){
            System.out.println("\n IEGUT PILSETAS LAIKAPSTAKLUS ERROR: " + e.getMessage());
            this.kluda = "COULD NOT READ THE WEATHER FORECAST!";
        }
    }

    //Atrod lietotaja dotas pilsetas koordinatas no API
    private void iegutPilsetasKoordinatas(String pilseta){
        pilseta = pilseta.replaceAll(" ", "+");

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
            System.out.println("\n IEGUT PILSETAS KOORDINATAS ERROR: " + e.getMessage());
            this.kluda = "COULD NOT CONNECT TO GEOCODING API!";
        }
    }

    //No API pieprasijuma izvelk lat un lon
    private void pilsetasKoordinatas(String atbilde){
        //Debug
        System.out.print("\nDEBUG:");
        System.out.println(atbilde);
        try{
            JSONObject jsonAtbilde = new JSONObject(atbilde);
            JSONArray resultsArray = jsonAtbilde.getJSONArray("results");

            jsonAtbilde = resultsArray.getJSONObject(0);

            this.lat = Double.toString(jsonAtbilde.getDouble("latitude"));
            this.lon = Double.toString(jsonAtbilde.getDouble("longitude"));
            this.pilseta = jsonAtbilde.getString("name");

            //Debug
            System.out.println("Latitude = " + this.lat + ", Longitude = " + this.lon);
            
        }
        catch(JSONException e){
            System.out.println("\n PILSETAS KOORDINATAS ERROR: " + e.getMessage());
            this.kluda = "CITY WAS NOT FOUND!";
        }
    }
}
