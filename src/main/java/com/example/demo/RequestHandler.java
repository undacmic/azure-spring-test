package com.example.demo;

import com.example.demo.person.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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


    public static CompletableFuture<Void> makeLoginRequest()
            throws JsonProcessingException
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username","un_dragos");
        map.put("password","1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/v1/users/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(System.out::println);

    }




}
