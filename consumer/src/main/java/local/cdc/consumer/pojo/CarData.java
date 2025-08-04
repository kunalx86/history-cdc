package local.cdc.consumer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CarData extends AbstractData {
    @JsonProperty("owner_id")
    public int ownerId;

    public String model;

    @JsonProperty("registration_no")
    public String registrationNo;
}
