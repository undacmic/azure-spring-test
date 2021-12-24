package com.example.demo.person;

import com.example.demo.*;
import com.example.demo.role.Role;
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

    @GetMapping("/{username}")
    public Person getUser(@PathVariable("username") String username) { return personRepository.getByCredentials(username); }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm)
    {

        try {
            String password = personRepository.findByUsername(loginForm.getUsername());
            if (password == null) {
                return ResponseHandler.buildErrorResponse("CREDENTIALS_ERROR","Contul cu numele de utlizator cautat nu exista sau a fost sters!",HttpStatus.NOT_FOUND);
            }


            if (Utils.verifyHash(loginForm.getPassword(), password)) {
                SecurityHandler sc = new SecurityHandler();
                Person requestedUser = personRepository.getByCredentials(loginForm.getUsername());
                return SecurityHandler.signInformation(requestedUser.getRole().getRoleName(), requestedUser.getID());

            } else {
                return ResponseHandler.buildErrorResponse("VERIFICATION_ERROR","Verificarea asupra integritatii parolei nu s-a putut realiza cu succes!",HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e) {
            return ResponseHandler.buildErrorResponse(e.getMessage(),"A aparut o exceptie in timpul procesului de autentificare pe serviciul remote!",HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm loginForm)
    {
        try {
            String password = personRepository.findByUsername(loginForm.getUsername());
            if (password != null) {
                return ResponseHandler.buildErrorResponse("CREDENTIALS_ERROR", "Contul cu numele de utlizator introdus este deja existent in baza de date a utilizatorilor!", HttpStatus.FOUND);
            }

            byte[] salt = Utils.getRandomBytes(64);
            String storePassword = Utils.getHash(loginForm.getPassword(), salt);

            Person p = new Person(loginForm.getFirstName(), loginForm.getLastName(), loginForm.getBirthDate(), loginForm.getPersonAddress(), loginForm.getPhoneNumber(), loginForm.getEmail(), loginForm.getUsername(), storePassword);
            Role role = new Role();
            role.setID(loginForm.getRole());
            p.setRole(role);

            Person newUser = personRepository.save(p);

            KeyPair keyPair = Utils.generateKeyPair();
            byte[] publicKey = keyPair.getPublic().getEncoded();
            byte[] privateKey = keyPair.getPrivate().getEncoded();

            personRepository.setUserKey(Base64.getEncoder().encodeToString(publicKey), newUser.getID());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("secretKey.prv", "secretKey.prv");

            return ResponseHandler.buildRegisterResponse(Base64.getEncoder().encodeToString(privateKey),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return ResponseHandler.buildErrorResponse(e.getMessage(),"A aparut o exceptie in timpul procesului de inregistrare pe serviciul remote!",HttpStatus.UNAUTHORIZED);
        }

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
    public void deleteUser(@PathVariable("id") Long id) //@RequestBody AuthorizeForm authorizeForm - authorization parameter
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // authorizeForm.setEncodedPublic(personRepository.getPublicKey(authorizeForm.getUserId()));
        // String role = Utils.verifyToken(authorizeForm);
        //if(role.equals("admin") || role.equals("librarian"))
        //{
        personRepository.deleteById(id);
        //}
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody RegisterForm personForm, @PathVariable("id") Long id) //Request param for authorization
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //request.getAuthorizeForm().setEncodedPublic(personRepository.getPublicKey(request.getAuthorizeForm().getUserId()));
        //String role = Utils.verifyToken(request.getAuthorizeForm());
        //if(role.equals("admin") || role.equals("librarian")) {

        Person user = personRepository.getById(id);

        //Person person = gson.fromJson(request.getJsonObject(), Person.class);


        user.setFirstName(personForm.getFirstName());
        user.setLastName(personForm.getLastName());
        user.setBirthDate(personForm.getBirthDate());
        user.setPersonAddress(personForm.getPersonAddress());
        user.setPhoneNumber(personForm.getPhoneNumber());
        user.setEmail(personForm.getEmail());
        user.setPersonPassword(personForm.getPassword());

        personRepository.save(user);

        return ResponseHandler.buildBorrowRequest(personForm.getUsername(),HttpStatus.OK);

        //}

    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestBody AuthorizeForm authorizeForm)
            throws Exception
    {
        return SecurityHandler.verifyToken(authorizeForm);
    }

}
