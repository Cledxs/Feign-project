package com.example.carservice.controller;

import com.example.carservice.entity.Car;
import com.example.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("list")
    public ResponseEntity<List<Car>> listar(){
        List<Car> cars =carService.getAll();
        if(cars.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Car> obtenerCar(@PathVariable int id){
        Car car = carService.getCarById(id);
        if(car == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car);
    }

    @PostMapping("save")
    public ResponseEntity<Car> guardarCar(@RequestBody Car car){
        Car findCar = carService.getCarById(car.getId());

        if(findCar == null){
            Car newCar = carService.save(car);
            return ResponseEntity.ok(newCar);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("update")
    public ResponseEntity<Car> updateCar(@RequestBody Car car){
        Car findCar = carService.getCarById(car.getId());

        if(findCar != null){
            Car newCar = new Car();
            newCar.setBrand(car.getBrand());
            newCar.setModel(car.getModel());
            newCar.setId(car.getId());
            return ResponseEntity.ok(carService.save(newCar));
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("listCarUser/{userId}")
    public ResponseEntity<List<Car>> getListCarUser(@PathVariable("userId") int userId){
        List<Car> listCar=carService.carListUser(userId);
        if(listCar.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(listCar);
        }
    }


}
