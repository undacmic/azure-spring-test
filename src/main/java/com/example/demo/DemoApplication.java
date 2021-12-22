package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
			throws JSONException, JsonProcessingException {
		//System.out.print(RequestHandler.makeLoginRequest("un_dragos","1234"));
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject credentials = new JSONObject();
		credentials.put("username","un_dragos");
		credentials.put("password","1234");

		map.put("method","https://libraryproject.azurewebsites.net/api/v1/users/login");
		map.put("credentials",credentials);
		map.put("type",0);
		JSONObject json = new JSONObject(map);
		HashMap<String,Object> result = new ObjectMapper().readValue(json.toString(),HashMap.class);
		JSONObject response = Utils.makeGenericRequest((String) json.get("method"),result,(int)json.get("type"));
		SpringApplication.run(DemoApplication.class, args);
	}

}
