package com.example.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.person.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.ECPrivateKey;

import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.*;


public class SecurityHandler {

    SecurityHandler()
    {
        try {
            if (certificateStore == null) {
                certificateStore = KeyStore.getInstance("PKCS12");
                char[] pwdArray = "EchipaDeSoc74".toCharArray();
                FileInputStream fis = new FileInputStream("src/main/java/com/example/demo/springboot.p12");
                certificateStore.load(fis, pwdArray);
            }
        }
        catch(IOException e)
        {
            System.out.print("Fisierul nu exista sau calea acestuia a fost modificata!\n");
            System.out.print(e.toString());
            System.exit(-10);
        }
        catch(KeyStoreException e)
        {
            System.out.print("Instantierea keystore-ului nu s-a putut finaliza cu succes.\n");
            System.out.print(e.toString());
            System.exit(-11);
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.print("Algoritmul folosit nu exista si a produs o exceptie!\n");
            System.out.print(e.toString());
            System.exit(-12);
        }
        catch(CertificateException e)
        {
            System.out.print("Certificatul cu alias-ul cautat nu exista sau parola folosita la accesare este incorecta!\n");
            System.out.print(e.toString());
            System.exit(-13);
        }
    }


    public static ResponseEntity<Object> signInformation(String role, Long id)
            throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
    {


        ECPrivateKey ecSigningKey = (ECPrivateKey) certificateStore.getKey("springboot",pwdArray);
        ECPublicKey ecPublicKey = (ECPublicKey) certificateStore.getCertificate("springboot").getPublicKey();
        Algorithm algorithm = Algorithm.ECDSA384(ecPublicKey,ecSigningKey);
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date expirationDate = calendar.getTime();


        String token = JWT.create()
                .withIssuer("alphav0.1-rest")
                .withSubject(id.toString())
                .withIssuedAt(currentDate)
                .withClaim("role",role)
                .withExpiresAt(expirationDate)
                .sign(algorithm);


        return ResponseHandler.buildTokenResponse(Base64.getEncoder().encodeToString(ecPublicKey.getEncoded()), token,id, HttpStatus.OK);

    }

    public static ResponseEntity<Object> verifyToken(AuthorizeForm authorizeForm)
            throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, InvalidKeyException, UnsupportedEncodingException, SignatureException
    {


        ECPublicKey serverPublicKey = (ECPublicKey) certificateStore.getCertificate("springboot").getPublicKey();


        try {
            Algorithm algorithm = Algorithm.ECDSA256(serverPublicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("alphav0.1-rest")
                    .build();
            DecodedJWT jwt = verifier.verify(authorizeForm.getToken());
            Map<String, Claim> claims = jwt.getClaims();
            authorizeForm.setEncodedPublic(personRepository.getPublicKey(Long.parseLong(jwt.getSubject())));

            byte[] publicString = Base64.getDecoder().decode(authorizeForm.getEncodedPublic());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicString);

            Signature ecdsaVerify = Signature.getInstance(authorizeForm.getAlgorithm());
            ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(publicKeySpec);
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(authorizeForm.getToken().getBytes(StandardCharsets.UTF_8));
            boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(authorizeForm.getSignature()));



            Date currentDate = new Date();
            if(!result || jwt.getExpiresAt().after(currentDate))
            {
                return ResponseHandler.buildAuthorizationResponse(null,null,HttpStatus.UNAUTHORIZED);
            }

            return ResponseHandler.buildAuthorizationResponse(claims.get("role").asString(),jwt.getSubject(),HttpStatus.OK);

        }
        catch(JWTVerificationException exception) {
            return ResponseHandler.buildAuthorizationResponse(null,null,HttpStatus.UNAUTHORIZED);
        }
    }



    private static KeyStore certificateStore = null;
    private static final char[] pwdArray="EchipaDeSoc74".toCharArray();
    private static PersonRepository personRepository;


}
