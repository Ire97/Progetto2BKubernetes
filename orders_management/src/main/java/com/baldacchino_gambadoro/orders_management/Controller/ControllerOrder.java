package com.baldacchino_gambadoro.orders_management.Controller;

import com.baldacchino_gambadoro.orders_management.ClassSerializable.OrderCompleted;
import com.baldacchino_gambadoro.orders_management.DataModel.StatusMicroservice;
import com.baldacchino_gambadoro.orders_management.DataModel.TotalOrder;
import com.baldacchino_gambadoro.orders_management.Exceptions.OrderNotFoundException;
import com.baldacchino_gambadoro.orders_management.Repository.TotalOrderRepository;
import com.baldacchino_gambadoro.orders_management.Services.PaginationService;
import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class ControllerOrder{

    @Autowired
    private TotalOrderRepository repository;


    @Autowired
    private PaginationService paginationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${KAFKA_TOPIC_ORDERS}")
    private String topicNameOrder;

    @Value("${KAFKA_TOPIC_NOTIFICATION}")
    private String topicNameNotification;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping(path="/orders")
    public @ResponseBody String addOrder(@RequestBody TotalOrder totalOrder,
                                         @RequestHeader("X-User-ID") String userId){

            totalOrder.setUserId(userId);
            double amount = 0.0;
            for (int i = 0; i < totalOrder.getOrders().size(); i++) {
                amount = amount + totalOrder.getOrders().get(i).getPrice();
            }
            totalOrder.setAmount(amount);
            repository.save(totalOrder);
            OrderCompleted orderCompleted = new OrderCompleted(totalOrder.get_id(),
                    totalOrder.getOrders(), amount, totalOrder.getAddressShipment(),
                    totalOrder.getAddressBilling(), userId);
            kafkaTemplate.send(topicNameOrder, "order_completed", new Gson().toJson(orderCompleted));
            kafkaTemplate.send(topicNameNotification, "order_completed", new Gson().toJson(orderCompleted));

        return "Order created.";
    }

    @GetMapping(path="/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getOrderById(@PathVariable("id") ObjectId id,
                                        @RequestHeader("X-User-ID") String userId){

            TotalOrder order = repository.findBy_id(id);
            if (order != null && (userId.equals("0") || order.getUserId().equals(userId))) {
                return new ResponseEntity<Object>(order, HttpStatus.OK);
            }else{
                throw new OrderNotFoundException();
            }
    }

    @GetMapping(path="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getOrdersbyIdUser(@RequestHeader("X-User-ID") String userId,
                                                                  @RequestParam(required = false) Integer per_page,
                                                                  @RequestParam(required = false) Integer page){
        List<TotalOrder> orders = repository.findByUserId(userId);
        if(orders.size() != 0) {

            switch (userId) {
                case "0":
                    if (page != null && per_page != null) {
                        orders = paginationService.getAllOrders(page, per_page);
                    } else {
                        orders = repository.findAll();
                    }
                    break;
                default:
                    if (page != null && per_page != null) {
                        orders = paginationService.getOrdersByUserID(page, per_page, userId);
                    } else {
                        //nel caso id != 0 e senza pagination
                        orders = repository.findByUserId(userId);
                    }
                    break;
            }

            return new ResponseEntity<Object>(orders, HttpStatus.OK);

        }else{
            throw new OrderNotFoundException();
        }

    }

    @GetMapping(path="/ping")
    public @ResponseBody
    ResponseEntity<Object> ping(){
            try {
                Document answer = mongoTemplate.getDb().runCommand(new BasicDBObject("ping", "1"));
                return new ResponseEntity<Object>(new StatusMicroservice("up", "up"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<Object>(new StatusMicroservice("up", "down"), HttpStatus.OK);
            }
    }

    @GetMapping(path="/ping1")
    public @ResponseBody String pingKub(){
        return "Pong";
    }

}
