package com.example.bestcarsbackend.Service;

import com.example.bestcarsbackend.Exception.InvalidEmailException;
import com.example.bestcarsbackend.Exception.InvalidUsernameException;
import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.Person;
import com.example.bestcarsbackend.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) throws InvalidEmailException, InvalidUsernameException {
        checkEmail(person.getEmail());
        checkUsername(person.getUsername());
        person.setPersonCode(UUID.randomUUID().toString());
        person.setPassword(encodePassword(person.getUsername(),person.getPassword()));
        return personRepository.save(person);
    }

    public void deletePerson(Long id) throws UserDoesNotExistException {
        if(personRepository.existsById(id)){
            personRepository.deleteById(id);
        }else{
            throw new UserDoesNotExistException("User with id " + id + " was not found!");
        }

    }

    @Transactional
    public Person updatePerson(Person person){
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
