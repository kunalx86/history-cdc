package local.cdc.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import local.cdc.consumer.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
