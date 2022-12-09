package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.feignclients.BikeFeignClient;
import com.example.userservice.feignclients.CarFeignClient;
import com.example.userservice.model.Bike;
import com.example.userservice.model.Car;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;
    @Autowired
    BikeFeignClient bikeFeignClient;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        User userNew = userRepository.save(user);
        return userNew;
    }

    public List<Car>  getCars(int userId){
        List<Car> cars =  restTemplate.getForObject("http://localhost:8002/car/listCarUser/"+userId,List.class);
        return cars;
    }

    public List<Bike>  getBikes(int userId){
        List<Bike> bikes =  restTemplate.getForObject("http://localhost:8003/bike/listBikeUser/"+userId,List.class);
        return bikes;
    }

    public Car saveCar(Car car){
        Car newCar = carFeignClient.guardarCar(car);
        return newCar;
    }

    public Bike saveBike(Bike bike){
        Bike newBike = bikeFeignClient.guardarBike(bike);
        return newBike;
    }

    public List<Bike> bikeListUser(int userId){
        List<Bike> bikes = bikeFeignClient.getListBikeUser(userId);
        return bikes;
    }

    public List<Car> carListUser(int userId){
        List<Car> cars = carFeignClient.getListCarUser(userId);
        return cars;
    }

    public Map<String, Object> getUserVehicles(int userId){
        Map<String, Object> result = new HashMap<>();
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            result.put("Mensaje","no existe el usuario");
            return result;
        }else{
            result.put("User", user.get().getName());
            List<Car> cars = carFeignClient.getListCarUser(userId);
            if(cars == null){
                result.put("Cars","Ese user no tiene coches");
            }else{
                result.put("Cars",cars);
            }

            List<Bike> bikes = bikeFeignClient.getListBikeUser(userId);
            if(bikes == null){
                result.put("Bikes","Ese user no tiene motos");
            }else{
                result.put("Bikes",bikes);
            }
            return result;
        }

    }

}
