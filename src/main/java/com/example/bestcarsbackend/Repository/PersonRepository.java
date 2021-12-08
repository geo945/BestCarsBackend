package com.example.bestcarsbackend.Repository;

import com.example.bestcarsbackend.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
