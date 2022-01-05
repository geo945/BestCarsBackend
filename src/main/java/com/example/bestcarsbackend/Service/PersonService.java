package com.example.bestcarsbackend.Service;

import com.example.bestcarsbackend.Exception.InvalidCredentialsException;
import com.example.bestcarsbackend.Exception.InvalidEmailException;
import com.example.bestcarsbackend.Exception.InvalidUsernameException;
import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.Person;
import com.example.bestcarsbackend.Repository.CarOfferRepository;
import com.example.bestcarsbackend.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CarOfferRepository carOfferRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, CarOfferRepository carOfferRepository) {
        this.personRepository = personRepository;
        this.carOfferRepository = carOfferRepository;
    }

    public Person addPerson(Person person) throws InvalidEmailException, InvalidUsernameException {
        checkEmail(person.getEmail());
        checkUsername(person.getUsername());
        person.setPersonCode(UUID.randomUUID().toString());
        person.setPassword(encodePassword(person.getPersonCode(),person.getPassword()));
        return personRepository.save(person);
    }

    @Transactional
    public void deletePerson(Long personId) throws UserDoesNotExistException {
        if(personRepository.existsById(personId)){
            personRepository.deleteById(personId);
            List<Long> offer_ids = new ArrayList<>();
            offer_ids = carOfferRepository.findAllByPersonId(personId);
            for(Long i: offer_ids){
                carOfferRepository.deleteById(i);
            }
        }else{
            throw new UserDoesNotExistException("User with id " + personId + " was not found!");
        }
    }

    @Transactional
    public Person updatePerson(Person person){
        return personRepository.save(person);
    }

    @Transactional
    public Person changePassword(Person person){
        person.setPassword(encodePassword(person.getPersonCode(), person.getPassword()));
        return personRepository.save(person);
    }

    public List<Person> getAllUsers(){
        return personRepository.findAll();
    }

    public void checkUsername(String username) throws InvalidUsernameException {
        if(username.length()<4 || username==null)
            throw new InvalidUsernameException("Invalid username !");
    }

    public void checkEmail(String email) throws InvalidEmailException {
        if(email==null)
            throw new InvalidEmailException("Email " + email + " is invalid");
        String regex = "^[a-zA-Z*_\\-.]*@[a-z][a-z.]*[^.]$";
        Pattern pattern = Pattern.compile(regex);
        if(!pattern.matcher(email).matches())
            throw new InvalidEmailException("Email " + email + " is invalid");
    }

    public Person personLogin(String username, String password) throws InvalidCredentialsException {
        Person p = findPersonByUsername(username).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials !"));
        if(!Objects.equals(encodePassword(p.getPersonCode(),password), p.getPassword())){
               throw new InvalidCredentialsException("Invalid credentials !");
        }
        return p;
    }

    public Optional<Person> findPersonByUsername(String username){
        Person person = personRepository.getPersonByUsername(username);
        return Optional.ofNullable(person);
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8).replace("\"", "");
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}
