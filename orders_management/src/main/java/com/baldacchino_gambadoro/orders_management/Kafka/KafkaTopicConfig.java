package com.baldacchino_gambadoro.orders_management.Kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${KAFKA_ADDRESS}")
    private String bootstrapAddress;
    @Value(value = "${KAFKA_TOPIC_LOGGING}")
    private String kafkaTopicLogging;
    @Value(value = "${KAFKA_TOPIC_NOTIFICATION}")
    private String kafkaTopicNotification;
    @Value(value = "${KAFKA_TOPIC_ORDERS}")
    private String kafkaTopicOrders;
    @Value(value = "${KAFKA_TOPIC_INVOICING}")
    private String kafkaTopicInvoicing;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(kafkaTopicLogging, 100, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(kafkaTopicNotification, 100, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(kafkaTopicOrders, 100, (short) 1);
    }
    @Bean
    public NewTopic topic4() {
        return new NewTopic(kafkaTopicInvoicing, 100, (short) 1);
    }
}
