package com.jtp.security_jwt.service;

import com.jtp.security_jwt.domain.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();
    Optional<Car> findById(Long id);
    Car save(Car car);
    void deleteById(Long id);
    void deleteAll();
    void deleteAll(List<Car> cars);
    void deleteAllById(List<Long> ids);


}
