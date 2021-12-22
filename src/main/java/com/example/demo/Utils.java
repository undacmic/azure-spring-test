package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.*;


public class Utils {

    public static byte[] getRandomBytes(int size)
        throws NoSuchAlgorithmException
    {
        byte[] b = new byte[size];
        SecureRandom random = SecureRandom.getInstance("DRBG",
                DrbgParameters.instantiation(256, DrbgParameters.Capability.PR_AND_RESEED, null));
        byte[] seed = random.generateSeed(64);
        random.nextBytes(b);
        return b;
    }
    public static String toHex (byte[] input)
            throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, input);
        String hex = bi.toString(16);

        int paddingLength = (input.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    public static byte[] fromHex(String hex)
            throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    public static String getHash(String password,byte[] salt)
        throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Utils.toHex(salt)+":"+Utils.toHex(hash);
    }
    public static boolean verifyHash(String originalPassword, String storedPassword)
        throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String[] parts = storedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] passwordHash = fromHex(parts[1]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, 65536, 512);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] verificationHash = skf.generateSecret(spec).getEncoded();

        int diff = passwordHash.length ^ verificationHash.length;
        int i;
        for(i=0;i<passwordHash.length;i++) {
            diff |= passwordHash[i] ^ verificationHash[i];
        }

        return diff == 0;


    }

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        kpg.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = kpg.generateKeyPair();
        return keypair;
    }


    public static boolean verifyServerIdentity(JSONObject tokenResponse) {

        try {
            String token = (String) tokenResponse.get("token");
            byte[] decoded = Base64.getDecoder().decode( (String) tokenResponse.get("publicKey"));

            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("EC");

            ECPublicKey serverPublicKey = (ECPublicKey) kf.generatePublic(X509publicKey);

            Algorithm algorithm = Algorithm.ECDSA384(serverPublicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("alphav0.1-rest")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }
        catch(Exception e) {
            return false;
        }

    }

    public static AuthorizeForm signRequestForm(JSONObject tokenResponse, String encodedPrivate)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, JSONException
    {

        String token = (String) tokenResponse.get("token");
        Long userId = (Long) tokenResponse.get("userId");

        byte[] privateString = Base64.getDecoder().decode(encodedPrivate);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateString);
        Signature ecdsaSign = Signature.getInstance("SHA384withECDSA");
        ECPrivateKey privateKey = (ECPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(token.getBytes(StandardCharsets.UTF_8));
        byte[] signature = ecdsaSign.sign();

        String encodedSignature = Base64.getEncoder().encodeToString(signature);

        AuthorizeForm authorizeForm = new AuthorizeForm(encodedSignature, token, null, "SHA384withECDSA");
        return authorizeForm;
    }

    public static JSONObject makeGenericRequest(String url, Map<String,Object> params, int type)
    {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = "";
            if(params != null) {
                requestBody = objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(params);
            }

            HttpRequest request = null;
            switch (type)
            {
                case 0: // GET METHOD
                    request = HttpRequest.newBuilder(URI.create(url))
                            .header("Content-Type","application/json")
                            .GET()
                            .build();
                    break;
                case 1: // POST METHOD
                    request = HttpRequest.newBuilder(URI.create(url))
                            .header("Content-Type","application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    break;
                case 2: // PUT METHOD
                    request = HttpRequest.newBuilder(URI.create(url))
                            .header("Content-Type","application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    break;
                case 3: //DELETE METHOD
                    request = HttpRequest.newBuilder(URI.create(url))
                            .header("Content-Type", "application/json")
                            .DELETE()
                            .build();
                    break;
            }

            HttpResponse<String> response =  HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());

        }
        catch(Exception e)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("message",e.getMessage());
            map.put("status", HttpStatus.UNAUTHORIZED.toString());
            return new JSONObject(map);
        }
    }





}
