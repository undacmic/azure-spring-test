package com.example.demo;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
		throws JSONException
	{
		System.out.print(RequestHandler.makeLoginRequest("un_dragos","1234"));
		SpringApplication.run(DemoApplication.class, args);
	}

}
