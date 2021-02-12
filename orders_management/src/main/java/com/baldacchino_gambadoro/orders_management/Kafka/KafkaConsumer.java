package com.baldacchino_gambadoro.orders_management.Kafka;

import com.baldacchino_gambadoro.orders_management.ClassSerializable.OrderPaid;
import com.baldacchino_gambadoro.orders_management.ClassSerializable.OrderValidation;
import com.baldacchino_gambadoro.orders_management.DataModel.TotalOrder;
import com.baldacchino_gambadoro.orders_management.Repository.TotalOrderRepository;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    TotalOrderRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${KAFKA_TOPIC_LOGGING}")
    private String kafkaTopicLogging;
    @Value(value = "${KAFKA_TOPIC_NOTIFICATION}")
    private String kafkaTopicNotification;
    @Value(value = "${KAFKA_TOPIC_INVOICING}")
    private String kafkaTopicInvoicing;


    @KafkaListener(topics = "${KAFKA_TOPIC_ORDERS}", groupId = "${KAFKA_GROUP_ID}")
    public void listenOrderValidation(ConsumerRecord<String, String> record) {
        if(record != null){
            if(record.key().equals("order_validation")) {
                System.out.println("Messaggio ricevuto sul topic orders con chiave order_validation");
                OrderValidation orderValidation = new Gson().fromJson(record.value(), OrderValidation.class);
                if (orderValidation.getStatus() != 0) {
                    System.out.println("Lo stato relativo al messaggio è diverso da 0");
                    TotalOrder order = repository.findBy_id(new ObjectId(orderValidation.getOrderId()));
                    order.setStatus("Abort");
                    repository.save(order);
                }
            }else if(record.key().equals("order_paid")){
                System.out.println("Messaggio ricevuto sul topic orders con chiave order_paid");
                OrderPaid orderPaid = new Gson().fromJson(record.value(), OrderPaid.class);
                TotalOrder order = repository.findTotalOrderBy_idAndUserIdAndAmount(new ObjectId(orderPaid.getOrderId()),
                        orderPaid.getUserId(), orderPaid.getAmount());
                if(order != null){
                    System.out.println("L'ordine ricevuto ha tutti i parametri corretti.");
                    order.setStatus("Paid");
                    repository.save(order);
                    kafkaTemplate.send(kafkaTopicNotification, "order_paid", new Gson().toJson(orderPaid));
                    kafkaTemplate.send(kafkaTopicInvoicing, "order_paid", new Gson().toJson(orderPaid));
                    System.out.println("Viene inviato un messaggio sui topic notification e invocing con chiave order_paid");
                }else{
                    System.out.println("L'ordine ha dei parametri non corrispondenti.");
                    TotalOrder order_error = repository.findTotalOrderBy_idAndUserId(new ObjectId(orderPaid.getOrderId()),
                            orderPaid.getUserId());
                    if(order_error != null){
                        //esiste un ordine ma c'è l'amount sbagliato.
                        order_error.setStatus("Abort");
                        repository.save(order_error);
                        orderPaid.getExtraArgs().put("error", "WRONG_AMOUNT_PAID");
                    }else{
                        orderPaid.getExtraArgs().put("error", "ORDER_NOT_FOUND");
                    }
                    kafkaTemplate.send(kafkaTopicLogging, "order_paid_validation_failure", new Gson().toJson(orderPaid));
                    System.out.println("Viene inviato un messaggio sul topic logging con chiave order_paid_validation_failure");
                }
            }

        }
    }

}
