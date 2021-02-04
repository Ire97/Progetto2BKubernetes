package com.baldacchino_gambadoro.orders_management.Repository;

import com.baldacchino_gambadoro.orders_management.DataModel.TotalOrder;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TotalOrderRepository extends MongoRepository<TotalOrder, ObjectId>{

    public TotalOrder findBy_id(ObjectId _id);
    public List<TotalOrder> findByUserId(String userId);
    public Slice<TotalOrder> findByUserId(String userId, Pageable paging);

    public TotalOrder findTotalOrderBy_idAndUserIdAndAmount(ObjectId orderId, String userId, double amount);
    public TotalOrder findTotalOrderBy_idAndUserId(ObjectId orderId, String userId);

}
