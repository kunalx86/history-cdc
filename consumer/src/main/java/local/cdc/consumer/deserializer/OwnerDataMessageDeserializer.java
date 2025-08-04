package local.cdc.consumer.deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import local.cdc.consumer.pojo.DebeziumChangeEvent;
import local.cdc.consumer.pojo.OwnerData;

import java.util.Map;

public class OwnerDataMessageDeserializer implements Deserializer<DebeziumChangeEvent<OwnerData>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DebeziumChangeEvent<OwnerData> deserialize(String topic, byte[] data) {
        if (data == null) return null;
        try {
            return objectMapper.readValue(
                data,
                new TypeReference<DebeziumChangeEvent<OwnerData>>() {}
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

