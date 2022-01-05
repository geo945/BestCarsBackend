package com.example.bestcarsbackend.Controller;

import com.example.bestcarsbackend.Exception.InvalidCredentialsException;
import com.example.bestcarsbackend.Exception.InvalidEmailException;
import com.example.bestcarsbackend.Exception.InvalidUsernameException;
import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.Person;
import com.example.bestcarsbackend.Service.EmailSenderService;
import com.example.bestcarsbackend.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public PersonController(PersonService personService, EmailSenderService emailSenderService) {
        this.personService = personService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Person>> getAllUsers(){
        List<Person> users = personService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws InvalidEmailException, InvalidUsernameException {
        Person p = personService.addPerson(person);

        //send email:
        String body = "Hello " + person.getUsername() + ",\n\nWelcome to bestcars!";
        String subject = "Bestcars registration";
        emailSenderService.sendSimpleEmail(person.getEmail(), body, subject);

        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){
        Person p = personService.updatePerson(person);
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @PutMapping(path = "/changePassword")
    public ResponseEntity<Person> changePersonPassword(@RequestBody Person person){
        Person p = personService.changePassword(person);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long id) throws UserDoesNotExistException {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Person> loginPerson(@RequestBody Person person) throws InvalidCredentialsException {
        Person p = personService.personLogin(person.getUsername(), person.getPassword());
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
