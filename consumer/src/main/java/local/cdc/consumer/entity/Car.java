package local.cdc.consumer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "cars", schema = "history")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "original_id")
    private Integer originalId;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "model")
    private String model;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "changed_at")
    private OffsetDateTime changedAt;
}
