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
    public ResponseEntity<byte[]> register(@RequestBody LoginForm loginForm)
        throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        String password = personRepository.findByUsername(loginForm.getUsername());
        if(password != null)
        {
            return null;
            //return "I'm sorry, but the entered credentials are already taken!";
        }

        byte[] salt = Utils.getRandomBytes(64);
        String storePassword = Utils.getHash(loginForm.getPassword(),salt);

        Person p = new Person(loginForm.getFirstName(),loginForm.getLastName(),loginForm.getBirthDate(),loginForm.getPersonAddress(),loginForm.getPhoneNumber(),loginForm.getEmail(),loginForm.getUsername(),storePassword);
        Role role=new Role();
        role.setID(3L);
        p.setRole(role);

        Person newUser = personRepository.save(p);

        KeyPair keyPair = Utils.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        personRepository.setUserKey(Base64.getEncoder().encodeToString(publicKey), newUser.getID());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("secretKey.prv","secretKey.prv");

        ResponseEntity<byte[]> response = new ResponseEntity<>(Base64.getEncoder().encode(privateKey),headers,HttpStatus.OK);
        return response;

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

    @PostMapping("/validate")
    public String validateToken(@RequestBody AuthorizeForm authorizeForm)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String encodedPublic = personRepository.getPublicKey(authorizeForm.getUsername());
        return Utils.verifyToken(authorizeForm.getToken(), encodedPublic);
    }

}
