package local.cdc.consumer.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;

import local.cdc.consumer.pojo.AbstractData;
import local.cdc.consumer.pojo.DebeziumChangeEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMessageListener<T extends AbstractData> extends AbstractConsumerSeekAware {

    protected void processRecords(
        List<ConsumerRecord<String, DebeziumChangeEvent<T>>> records,
        Acknowledgment acknowledgment
    ) {
        try {
            for (ConsumerRecord<String, DebeziumChangeEvent<T>> record : records) {
                String key = record.key();
                DebeziumChangeEvent<T> value = record.value();
                T updatedValues = value.getPayload().getAfter();
                int partition = record.partition();
                log.info("Received message with key: {}, updated value: {} from topic: {} on partition: {}",
                         key, updatedValues, record.topic(), partition);
                if (updatedValues != null) {
                    handleUpdate(updatedValues);
                } else {
                    log.warn("No updated values found in the record with key: {}", key);
                }
            }
        } catch (Exception e) {
            log.error("Error processing records: {}", e.getMessage(), e);
        }
        acknowledgment.acknowledge();
    }

    protected abstract void handleUpdate(T value);
}
