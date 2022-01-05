package com.example.bestcarsbackend.Repository;

import com.example.bestcarsbackend.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person getPersonByUsername(String username);
}
