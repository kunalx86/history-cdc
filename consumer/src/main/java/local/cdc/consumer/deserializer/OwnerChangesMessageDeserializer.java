package local.cdc.consumer.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import local.cdc.consumer.pojo.DebeziumChangeEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OwnerChangesMessageDeserializer implements Deserializer<DebeziumChangeEvent> {

    @Override
    public DebeziumChangeEvent deserialize(String topic, byte[] data) {
        String message = new String(data, java.nio.charset.StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        DebeziumChangeEvent event = null;
        try {
            event = objectMapper.readValue(message, DebeziumChangeEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Deserializing message from topic {}: {}", topic, event);
        return event;
    }

}
