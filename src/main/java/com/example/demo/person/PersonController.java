package com.example.demo.person;

import com.example.demo.Request;
import com.example.demo.role.Role;
import com.example.demo.AuthorizeForm;
import com.example.demo.LoginForm;
import com.example.demo.Utils;
import com.google.gson.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Type;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Base64;


@RestController
@RequestMapping("/api/v1/users")
public class PersonController {

    private final PersonRepository personRepository;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();

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

    @GetMapping("/keys/{id}")
    public ResponseEntity<byte[]> generateKeypair(@PathVariable("id") Long id)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        KeyPair keyPair = Utils.generateKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();

        personRepository.setUserKey(Base64.getEncoder().encodeToString(publicKey), id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("secretKey.prv","secretKey.prv");

        ResponseEntity<byte[]> response = new ResponseEntity<>(Base64.getEncoder().encode(privateKey),headers,HttpStatus.OK);
        return response;
    }


    @DeleteMapping("/delete/{id}")
    public void deleteUser(@RequestBody AuthorizeForm authorizeForm, @PathVariable("id") Long id)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        authorizeForm.setEncodedPublic(personRepository.getPublicKey(authorizeForm.getUserId()));
        String role = Utils.verifyToken(authorizeForm);
        if(role.equals("admin") || role.equals("librarian"))
        {
            personRepository.deleteById(id);
        }
    }

    @PutMapping("/update/{id}")
    public void updateUser(@RequestBody Request request, @PathVariable("id") Long id)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        request.getAuthorizeForm().setEncodedPublic(personRepository.getPublicKey(request.getAuthorizeForm().getUserId()));
        String role = Utils.verifyToken(request.getAuthorizeForm());
        if(role.equals("admin") || role.equals("librarian")) {

            Person user = personRepository.getById(id);

            Person person = gson.fromJson(request.getJsonObject(), Person.class);


            user.setFirstName(person.getFirstName());
            user.setLastName(person.getLastName());
            user.setBirthDate(person.getBirthDate());
            user.setPersonAddress(person.getPersonAddress());
            user.setPhoneNumber(person.getPhoneNumber());
            user.setEmail(person.getEmail());
            user.setPersonPassword(person.getPersonPassword());

            personRepository.save(user);

        }

    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public String getToken(@RequestBody AuthorizeForm authorizeForm)
           throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String encodedPublic = personRepository.getPublicKey(authorizeForm.getUserId());
        String token = Utils.generateToken(authorizeForm.getToken(),encodedPublic);
        return token;
   }

    @PostMapping("/validate")
    public String validateToken(@RequestBody AuthorizeForm authorizeForm)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        authorizeForm.setEncodedPublic(personRepository.getPublicKey(authorizeForm.getUserId()));
        return Utils.verifyToken(authorizeForm);
    }

}
