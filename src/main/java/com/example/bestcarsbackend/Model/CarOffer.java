package com.example.bestcarsbackend.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "car_offers")
@Builder
public class CarOffer {

    @Id
    @SequenceGenerator(
            name = "carOffer_sequence",
            sequenceName = "carOffer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "carOffer_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "offer_id")
    private Long id;
    private String title;
    private Integer fabricationYear;
    private Double mileage;
    private String fuelType;
    private String city;
    private Integer price;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    //LOB or Large OBject refers to a variable length datatype for storing large objects.
    //
    //The datatype has two variants:
    //
    //CLOB – Character Large Object will store large text data
    //BLOB – Binary Large Object is for storing binary data like image, audio, or video
    //In this tutorial, we'll show how we can utilize Hibernate ORM for persisting large objects.
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "person_id"
    )
    private Person person;
    private Boolean approved=false;


}
