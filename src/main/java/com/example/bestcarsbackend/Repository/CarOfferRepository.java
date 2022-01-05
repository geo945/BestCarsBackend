package com.example.bestcarsbackend.Repository;

import com.example.bestcarsbackend.Model.CarOffer;
import com.example.bestcarsbackend.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarOfferRepository extends JpaRepository<CarOffer, Long> {

    @Query(value = "select c.id from CarOffer c where c.person.id=:personId")
    List<Long> findAllByPersonId(@Param("personId") Long personId);

}
