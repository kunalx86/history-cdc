package local.cdc.consumer.service;

import org.springframework.stereotype.Service;

import local.cdc.consumer.entity.Owner;
import local.cdc.consumer.mapper.OwnerDataToEntity;
import local.cdc.consumer.pojo.OwnerData;
import local.cdc.consumer.repository.CarRepository;
import local.cdc.consumer.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoryRecorder {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    public HistoryRecorder(OwnerRepository ownerRepository, CarRepository carRepository) {
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }

    public void recordChange(OwnerData ownerData) {
        Owner owner = OwnerDataToEntity.mapToEntity(ownerData);
        log.info("Recording change for owner: {}", owner);
        Owner insertedOwner = ownerRepository.save(owner);
        log.info("Recorded history for owner with ID: {} as {}", insertedOwner.getOriginalId(), insertedOwner.getEventId());
    }
}
