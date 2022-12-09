package com.example.bikeservice.controller;

import com.example.bikeservice.entity.Bike;
import com.example.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bike")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping("list")
    public ResponseEntity<List<Bike>> listar(){
        List<Bike> bikes =bikeService.getAll();
        if(bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Bike> obtenerBike(@PathVariable int id){
        Bike bike = bikeService.getBikeById(id);
        if(bike == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bike);
    }

    @PostMapping("save")
    public ResponseEntity<Bike> guardarBike(@RequestBody Bike bike){
        Bike findBike = bikeService.getBikeById(bike.getId());

        if(findBike == null){
            Bike newBike = bikeService.save(bike);
            return ResponseEntity.ok(newBike);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("update")
    public ResponseEntity<Bike> updateBike(@RequestBody Bike bike){
        Bike findBike = bikeService.getBikeById(bike.getId());

        if(findBike != null){
            Bike newBike = new Bike();
            newBike.setBrand(bike.getBrand());
            newBike.setModel(bike.getModel());
            newBike.setId(bike.getId());
            return ResponseEntity.ok(bikeService.save(newBike));
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("listBikeUser/{userId}")
    public ResponseEntity<List<Bike>> getListBikeUser(@PathVariable("userId") int userId){
        List<Bike> listBike=bikeService.bikeListUser(userId);
        if(listBike.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listBike);
        }
    }


}
