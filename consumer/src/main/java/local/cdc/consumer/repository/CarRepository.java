package local.cdc.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import local.cdc.consumer.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
