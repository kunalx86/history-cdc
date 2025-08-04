package local.cdc.consumer.pojo;

import lombok.Data;

@Data
public class DebeziumChangeEvent<T extends AbstractData> {
    public Schema schema;
    public Payload<T> payload;
}
