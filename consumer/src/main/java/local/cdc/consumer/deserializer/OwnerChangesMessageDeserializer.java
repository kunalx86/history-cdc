package local.cdc.consumer.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OwnerChangesMessageDeserializer implements Deserializer<String> {

    @Override
    public String deserialize(String topic, byte[] data) {
        String message = new String(data, java.nio.charset.StandardCharsets.UTF_8);
        log.info("Deserializing message from topic {}: {}", topic, message);
        return message;
    }

}
