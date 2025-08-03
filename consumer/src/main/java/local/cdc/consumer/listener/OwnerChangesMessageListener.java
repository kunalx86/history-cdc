package local.cdc.consumer.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import local.cdc.consumer.pojo.DebeziumChangeEvent;
import local.cdc.consumer.service.HistoryRecorder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OwnerChangesMessageListener extends AbstractConsumerSeekAware {

    private final HistoryRecorder historyRecorder;

    public OwnerChangesMessageListener(HistoryRecorder historyRecorder) {
        this.historyRecorder = historyRecorder;
    }

    @KafkaListener(
        topics = "${application.topic-owner}",
        groupId = "${spring.kafka.consumer.group-id}",
        batch = "true"
    )
    public void onMessage(List<ConsumerRecord<String, DebeziumChangeEvent>> records, Acknowledgment acknowledgment) {
        try {
            for (ConsumerRecord<String, DebeziumChangeEvent> record : records) {
                String key = record.key();
                DebeziumChangeEvent value = record.value();
                var updatedValues = value.getPayload().getAfter();
                int partition = record.partition();
                log.info("Received message with key: {}, updated value: {} from topic: {} on partition: {}", key, updatedValues, record.topic(), partition);
                if (updatedValues != null) {
                    historyRecorder.recordChange(updatedValues);
                } else {
                    log.warn("No updated values found in the record with key: {}", key);
                }
            }    
        } catch (Exception e) {
            log.error("Error processing records: {}", e.getMessage(), e);
        }
        acknowledgment.acknowledge();
    }

}
