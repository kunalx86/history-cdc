package local.cdc.consumer.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OwnerChangesMessageListener extends AbstractConsumerSeekAware {

    @KafkaListener(
        topics = "${application.topic-owner}",
        groupId = "${spring.kafka.consumer.group-id}",
        batch = "true"
    )
    public void onMessage(List<ConsumerRecord<String, String>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> record : records) {
            String key = record.key();
            String value = record.value();
            int partition = record.partition();
            log.info("Received message with key: {}, value: {} from topic: {} on partition: {}", key, value, record.topic(), partition);
        }
        acknowledgment.acknowledge();
    }

}
