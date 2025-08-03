package local.cdc.consumer.pojo;

import lombok.Data;

@Data
public class DebeziumChangeEvent {
    public Schema schema;
    public Payload payload;
}
