package local.cdc.consumer.mapper;

import java.time.OffsetDateTime;

import local.cdc.consumer.entity.Owner;

public class OwnerDataToEntity {

    public static Owner mapToEntity(local.cdc.consumer.pojo.OwnerData ownerData) {
        Owner owner = new Owner();
        owner.setOriginalId(ownerData.getId());
        owner.setName(ownerData.getName());
        owner.setCreatedAt(OffsetDateTime.parse(ownerData.getCreatedAt()));
        owner.setUpdatedAt(OffsetDateTime.parse(ownerData.getUpdatedAt()));
        return owner;
    }

}
