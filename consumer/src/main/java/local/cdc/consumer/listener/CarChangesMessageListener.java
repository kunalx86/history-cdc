package local.cdc.consumer.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import local.cdc.consumer.pojo.CarData;
import local.cdc.consumer.pojo.DebeziumChangeEvent;
import local.cdc.consumer.service.HistoryRecorder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CarChangesMessageListener extends AbstractMessageListener<CarData> {

    private final HistoryRecorder historyRecorder;

    public CarChangesMessageListener(HistoryRecorder historyRecorder) {
        super();
        this.historyRecorder = historyRecorder;
    }

    @KafkaListener(
        topics = "${application.topic-cars}",
        groupId = "${application.cars-group-id}",
        batch = "true",
        containerFactory = "carsKafkaListenerContainerFactory"
    )
    public void processRecords(List<ConsumerRecord<String, DebeziumChangeEvent<CarData>>> records, Acknowledgment acknowledgment) {
        super.processRecords(records, acknowledgment);
    }

    @Override
    protected void handleUpdate(CarData value) {
        historyRecorder.recordChange(value);
    }
}
