package local.cdc.consumer.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OwnerData extends AbstractData {
    public String name;
}

