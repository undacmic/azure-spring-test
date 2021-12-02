package com.proiect.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.AuthorizeForm;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


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

    public static String generateToken(String encodedSecret, String encodedPublic)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
       byte[] secret =  Base64.getDecoder().decode(encodedSecret);
       byte[] publicString = Base64.getDecoder().decode(encodedPublic);


       KeyFactory keyFactory = KeyFactory.getInstance("EC");

       X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicString);
       ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(publicKeySpec);

       PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(secret);
       ECPrivateKey privateKey = (ECPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        Algorithm algorithm = Algorithm.ECDSA256(publicKey,privateKey);
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date expirationDate = calendar.getTime();

        String token = JWT.create()
                .withIssuer("auth0")
                .withIssuedAt(currentDate)
                .withClaim("role","admin")
                .withExpiresAt(expirationDate)
                .sign(algorithm);

        return token;

    }

    public static String verifyToken(AuthorizeForm authorizeForm)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        byte[] publicString = Base64.getDecoder().decode(authorizeForm.getEncodedPublic());

        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicString);
        ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(publicKeySpec);

        try {
            Algorithm algorithm = Algorithm.ECDSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(authorizeForm.getToken());
            Map<String, Claim> claims = jwt.getClaims();

            return claims.get("role").asString();
        }
        catch(JWTVerificationException exception) {
            return "Unathorized";
        }
    }





}
