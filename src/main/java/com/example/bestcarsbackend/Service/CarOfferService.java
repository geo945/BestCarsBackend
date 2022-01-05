package com.example.bestcarsbackend.Service;

import com.example.bestcarsbackend.Exception.UserDoesNotExistException;
import com.example.bestcarsbackend.Model.CarOffer;
import com.example.bestcarsbackend.Repository.CarOfferRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CarOfferService {

    private final CarOfferRepository carOfferRepository;

    @Autowired
    public CarOfferService(CarOfferRepository carOfferRepository) {
        this.carOfferRepository = carOfferRepository;
    }

    public void addCar(CarOffer carOffer)  {
        //MultipartFile mf = (MultipartFile) carOffer.getFile();
       // try {
     //      carOffer.setImage(Base64.getEncoder().encodeToString(mf.getBytes()));
       // }catch (IOException e){
       //     System.out.println(e);
      //=  }
        carOfferRepository.save(carOffer);
    }

    public List<CarOffer> getAllOffers(){
        return carOfferRepository.findAll();
    }

    @Transactional
    public CarOffer approveAd(CarOffer carOffer){
        carOffer.setApproved(true);
        carOfferRepository.save(carOffer);
        return carOffer;
    }

    @Transactional
    public void deleteCarAd(Long id) throws UserDoesNotExistException {
        if(carOfferRepository.existsById(id)){
            carOfferRepository.deleteById(id);
        }else{
            throw new UserDoesNotExistException("User with id " + id + " does not exists!");
        }
    }

}
