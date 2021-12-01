package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proiect.utils.Utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @GetMapping(
            value = "/get-secret"
    )
    public ResponseEntity<byte[]> getSecret()
        throws InvalidAlgorithmParameterException, NoSuchAlgorithmException
    {
        KeyPair keyPair = Utils.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("secret1.prv","secret1.prv");

        ResponseEntity<byte[]> response = new ResponseEntity<>(Base64.getEncoder().encode(privateKey),headers,HttpStatus.OK);
        return response;
    }
}
