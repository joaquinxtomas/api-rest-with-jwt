package com.jtp.security_jwt.service;

import com.jtp.security_jwt.domain.Car;
import com.jtp.security_jwt.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{

    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        log.info("Executing find all cars");
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        log.info("Executing find by id");
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        log.info("Executing save car");
        return carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Executing delete by id");
        carRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("Executing delete all cars");
        carRepository.deleteAll();
    }

    @Override
    public void deleteAll(List<Car> cars) {
        log.info("Executing delete a list of cars");
        carRepository.deleteAll(cars);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        log.info("Executing delete a list o cars by id");
        carRepository.deleteAllById(ids);
    }
}
