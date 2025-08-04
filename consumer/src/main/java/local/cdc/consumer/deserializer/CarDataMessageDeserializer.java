package local.cdc.consumer.deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import local.cdc.consumer.pojo.CarData;
import local.cdc.consumer.pojo.DebeziumChangeEvent;

import java.util.Map;

public class CarDataMessageDeserializer implements Deserializer<DebeziumChangeEvent<CarData>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DebeziumChangeEvent<CarData> deserialize(String topic, byte[] data) {
        if (data == null) return null;
        try {
            return objectMapper.readValue(
                data,
                new TypeReference<DebeziumChangeEvent<CarData>>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize DebeziumChangeEvent<OwnerData>", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}
}

