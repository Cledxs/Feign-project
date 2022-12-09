package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.model.Bike;
import com.example.userservice.model.Car;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("list")
    public ResponseEntity<List<User>> listar(){
        List<User> users =userService.getAll();
        if(users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<User> obtenerUsuario(@PathVariable int id){
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("save")
    public ResponseEntity<User> guardarUsuario(@RequestBody User user){
        User findUser = userService.getUserById(user.getId());

        if(findUser == null){
            User newUser = userService.save(user);
            return ResponseEntity.ok(newUser);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User findUser = userService.getUserById(user.getId());

        if(findUser != null){
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setId(user.getId());
            return ResponseEntity.ok(userService.save(newUser));
        }else {
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable int userId){
        User findUser=userService.getUserById(userId);
        if(findUser == null){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(userService.getCars(userId));
        }
    }

    @GetMapping("bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable int userId){
        User findUser=userService.getUserById(userId);
        if(findUser == null){
            return ResponseEntity.noContent().build();
        }else {
            List<Bike> listBikes=userService.getBikes(userId);
            if(listBikes==null){
                return ResponseEntity.noContent().build();
            }else {
                return ResponseEntity.ok(listBikes);
            }
        }
    }

    @PostMapping("saveCar")
    public ResponseEntity<Car> saveCar(@RequestBody Car car){
        Car newCar=userService.saveCar(car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("saveBike")
    public ResponseEntity<Bike> saveBike(@RequestBody Bike bike){
        Bike newBike=userService.saveBike(bike);
        return ResponseEntity.ok(newBike);
    }

    @GetMapping("getBikesUser")
    public ResponseEntity<List<Bike>> getBikesUser(@PathVariable int userId){
        List<Bike> bikeList = userService.bikeListUser(userId);
        return ResponseEntity.ok(bikeList);
    }

    @GetMapping("getCarsUser")
    public ResponseEntity<List<Car>> getCarsUser(@PathVariable int userId){
        List<Car> carList = userService.carListUser(userId);
        return ResponseEntity.ok(carList);
    }

    @GetMapping("vehicles/{userId}")
    public ResponseEntity<Map<String, Object>> getUserAndVehicles(@PathVariable int userId){
        Map<String,Object> listVehicles = userService.getUserVehicles(userId);
        return ResponseEntity.ok(listVehicles);
    }


}
