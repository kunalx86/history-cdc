package local.cdc.consumer.mapper;

import java.time.OffsetDateTime;

import local.cdc.consumer.entity.Car;

public class CarDataToEntity {

    public static Car mapToEntity(local.cdc.consumer.pojo.CarData carData) {
        Car car = new Car();
        car.setOriginalId(carData.getId());
        car.setOwnerId(carData.getOwnerId());
        car.setModel(carData.getModel());
        car.setRegistrationNo(carData.getRegistrationNo());
        car.setCreatedAt(OffsetDateTime.parse(carData.getCreatedAt()));
        car.setUpdatedAt(OffsetDateTime.parse(carData.getUpdatedAt()));
        return car;
    }

}
