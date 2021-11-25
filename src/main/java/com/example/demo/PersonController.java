package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.proiect.utils.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


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
        throws InvalidKeySpecException, NoSuchAlgorithmException
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

}
