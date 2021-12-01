package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proiect.utils.Utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;


@RestController
@RequestMapping("/api/v1/users")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) { this.personRepository = personRepository;}

    @GetMapping("/")
    public Iterable<Person> getUsers() { return personRepository.findAll(); }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody LoginForm loginForm)
        throws InvalidKeySpecException, NoSuchAlgorithmException
    {

        String password = personRepository.findByUsername(loginForm.getUsername());
        if(password == null) {
            return "I'm sorry, but the entered credentials are invalid!";
        }

        if(Utils.verifyHash(loginForm.getPassword(),password))
        {
            return "OK";
        }
        else {
            return "I'm sorry, but the entered credentials are invalid!";
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Person register(@RequestBody LoginForm registerForm)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        String password = personRepository.findByUsername(registerForm.getUsername());
        if(password != null)
        {
            return null;
            //return "I'm sorry, but the entered credentials are already taken!";
        }



        byte[] salt = Utils.getRandomBytes(64);
        String storePassword = Utils.getHash(registerForm.getPassword(),salt);

        Person p = new Person(registerForm.getUsername(),storePassword);
        Role role=new Role();
        role.setID(1L);
        p.setRole(role);

        return personRepository.save(p);

        //return "User created!\nPlease enter your personal details to validate your account creation request!";
    }


    @GetMapping("/authorize/{id}")
    public ResponseEntity<byte[]> getSecret(@PathVariable("id") Long id)
        throws InvalidAlgorithmParameterException, NoSuchAlgorithmException
    {
        KeyPair keyPair = Utils.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        personRepository.setUserKey(Base64.getEncoder().encodeToString(publicKey),id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("secretKey.prv","secretKey.prv");

        ResponseEntity<byte[]> response = new ResponseEntity<>(Base64.getEncoder().encode(privateKey),headers,HttpStatus.OK);
        return response;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public String getToken(@RequestBody String secret)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String encodedPublic = personRepository.getPublicKey("amalia_popa");
        String token = Utils.generateToken(secret,encodedPublic);
        return token;
    }

    @GetMapping("/validate")
    public String validateToken()
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJpc3MiOiJhdXRoMCIsImV4cCI6MTYzODM5MjQ2MywiaWF0IjoxNjM4Mzg4ODYzfQ.KypqnowUvIsHravL5T507VeH3eAvG3QfC4GrYmxdF_-T1Ti07C96MzPlXL6LshIeXo65JpfSB0J7RXM9g7JuNQ";
        String encodedPublic = personRepository.getPublicKey("amalia_popa");
        return Utils.verifyToken(token, encodedPublic);
    }

}
