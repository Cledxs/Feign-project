package com.example.bikeservice.service;

import com.example.bikeservice.entity.Bike;
import com.example.bikeservice.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;

    public List<Bike> getAll(){
        return bikeRepository.findAll();
    }

    public Bike getBikeById(int id){
        return bikeRepository.findById(id).orElse(null);
    }

    public Bike save(Bike bike){
        Bike bikeNew = bikeRepository.save(bike);
        return bikeNew;
    }

    public List<Bike> bikeListUser(int userId){
        List<Bike> bikeList=bikeRepository.findByUserId(userId);
        return bikeList;
    }
}
