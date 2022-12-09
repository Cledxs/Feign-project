package com.example.userservice.feignclients;

import com.example.userservice.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service", url = "http://localhost:8003/bike")
public interface BikeFeignClient {
    @PostMapping("save")
    Bike guardarBike(@RequestBody Bike bike);

    @GetMapping("listBikeUser/{userId}")
    List<Bike> getListBikeUser(@PathVariable("userId") int userId);
}
