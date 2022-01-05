package com.example.bestcarsbackend.Controller;

import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.CarOffer;
import com.example.bestcarsbackend.Service.CarOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/car")
public class CarOfferController {
    private CarOfferService carOfferService;

    @Autowired
    public CarOfferController(CarOfferService carOfferService) {
        this.carOfferService = carOfferService;
    }

   @GetMapping("/all")
   public ResponseEntity<List<CarOffer>> getAllOffers(){
        List<CarOffer> offer = carOfferService.getAllOffers();
        return new ResponseEntity<>(offer, HttpStatus.OK);
   }

    @PostMapping(path = "/add")
    public ResponseEntity<CarOffer> addCar(@RequestBody CarOffer carOffer/*, @RequestParam("image") MultipartFile file*/) {
       // System.out.println(file);
        // System.out.println(carOffer.toString());
        // System.out.println(carOffer);
       // carOffer.setImage("");
        ///System.out.println(carOffer.getFile());
        carOfferService.addCar(carOffer);
        return new ResponseEntity<>(carOffer, HttpStatus.OK);
    }

    @PutMapping(path = "/approveAd")
    public ResponseEntity<CarOffer> approveAd(@RequestBody CarOffer carOffer){
        CarOffer response = carOfferService.approveAd(carOffer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteAd/{id}")
    public ResponseEntity<CarOffer> deleteAd(@PathVariable("id") Long id) throws UserDoesNotExistException {
        carOfferService.deleteCarAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
