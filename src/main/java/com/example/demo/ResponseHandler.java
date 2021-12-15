package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> buildTokenResponse(String publicKey, String token,Long id, HttpStatus status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("publicKey", publicKey);
        map.put("token", token);
        map.put("userId", id);
        map.put("status", status.value());

        return new ResponseEntity<Object>(map, status);
    }
    public static ResponseEntity<Object> buildAuthorizationResponse(String role, String id, HttpStatus status)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userRole", role);
        map.put("userId", id);
        map.put("status",status.value());
        return new ResponseEntity<Object>(map, status);
    }
}
