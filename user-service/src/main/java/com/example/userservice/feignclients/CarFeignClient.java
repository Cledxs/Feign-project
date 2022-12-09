package com.example.userservice.feignclients;

import com.example.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service",url = "http://localhost:8002/car")
public interface CarFeignClient {

    @PostMapping("save")
    Car guardarCar(@RequestBody Car car);

    @GetMapping("listCarUser/{userId}")
    List<Car> getListCarUser(@PathVariable("userId") int userId);
}
