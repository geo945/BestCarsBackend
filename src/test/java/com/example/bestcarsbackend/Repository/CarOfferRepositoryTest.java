package com.example.bestcarsbackend.Repository;

import com.example.bestcarsbackend.Model.CarOffer;
import com.example.bestcarsbackend.Model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarOfferRepositoryTest {

    private final CarOfferRepository carOfferRepository;

    @Autowired
    public CarOfferRepositoryTest(CarOfferRepository carOfferRepository) {
        this.carOfferRepository = carOfferRepository;
    }

    @Test
    public void savecarOfferWithPerson(){
        Person p = Person.builder()
                .username("Geo")
                .firstName("Mere")
                .lastName("Pere")
                .email("Geo@yahoo.com")
                .password("parola")
                .personCode("sdfsad")
                .build();

        CarOffer carOffer = CarOffer.builder()
                .title("Enunt")
                .person(p)
                .fabricationYear(12)
                .mileage(12.2)
                .fuelType("Diesel")
                .price(1000)
                .city("TM")
                .build();


        carOfferRepository.save(carOffer);

    }
}