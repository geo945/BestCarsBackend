package com.example.bestcarsbackend.Controller;

import com.example.bestcarsbackend.Exception.InvalidEmailException;
import com.example.bestcarsbackend.Exception.InvalidUsernameException;
import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.Person;
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

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Person>> getAllUsers(){
        List<Person> users = personService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws InvalidEmailException, InvalidUsernameException {
        Person p = personService.addPerson(person);
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){
        Person p = personService.updatePerson(person);
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id) throws UserDoesNotExistException {
        personService.deletePerson(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
