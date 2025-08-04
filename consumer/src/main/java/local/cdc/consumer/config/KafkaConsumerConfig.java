package local.cdc.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

import local.cdc.consumer.deserializer.CarDataMessageDeserializer;
import local.cdc.consumer.deserializer.OwnerDataMessageDeserializer;
import local.cdc.consumer.pojo.CarData;
import local.cdc.consumer.pojo.DebeziumChangeEvent;
import local.cdc.consumer.pojo.OwnerData;

@Configuration
public class KafkaConsumerConfig {

    private String ownerGroupId;
    private String carsGroupId;

    public KafkaConsumerConfig(
        @Value("${application.owner-group-id}") String ownerGroupId,
        @Value("${application.cars-group-id}") String carsGroupId
    ) {
        this.ownerGroupId = ownerGroupId;
        this.carsGroupId = carsGroupId;
    }

    @Bean
    ConsumerFactory<String, DebeziumChangeEvent<OwnerData>> ownerConsumerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ownerGroupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new OwnerDataMessageDeserializer());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DebeziumChangeEvent<OwnerData>> ownerKafkaListenerContainerFactory(
        ConsumerFactory<String, DebeziumChangeEvent<OwnerData>> ownerConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, DebeziumChangeEvent<OwnerData>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ownerConsumerFactory);
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    ConsumerFactory<String, DebeziumChangeEvent<CarData>> carsConsumerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, carsGroupId);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new CarDataMessageDeserializer());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DebeziumChangeEvent<CarData>> carsKafkaListenerContainerFactory(
        ConsumerFactory<String, DebeziumChangeEvent<CarData>> carsConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, DebeziumChangeEvent<CarData>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(carsConsumerFactory);
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        factory.setBatchListener(true);
        return factory;
    }

}
