package com.example.demo;

import com.example.demo.person.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RequestHandler {

    private String url = "https://dragosrest-spring.azurewebsites.net/api/v1/users/";

    public void makeRequest()
            throws MalformedURLException, IOException
    {
        // create a client
        HttpClient client = HttpClient.newHttpClient();

        // create a request
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(this.url))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        //HttpResponse<Iterable<Person>> response = client.send(request,);

        // the response:
        //System.out.println(response.body().get().title);



    }


    public static JSONObject makeLoginRequest(String username, String password)
    {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("username", username);
            map.put("password", password);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);

            HttpRequest request = HttpRequest.newBuilder(URI.create("https://libraryproject.azurewebsites.net/api/v1/users/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response =  HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200)
            {
                return new JSONObject(response.body());
            }
            else
            {
                return null;
            }
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
            return null;
        }

    }




}
