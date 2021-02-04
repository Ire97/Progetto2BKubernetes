package com.baldacchino_gambadoro.orders_management.Services;

import com.baldacchino_gambadoro.orders_management.DataModel.TotalOrder;
import com.baldacchino_gambadoro.orders_management.Repository.TotalOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationService {
    @Autowired
    TotalOrderRepository repository;

    public List<TotalOrder> getAllOrders(Integer page, Integer per_page)
    {
        Pageable paging = PageRequest.of(page, per_page);

        Slice<TotalOrder> sliceResult = repository.findAll(paging);

        if(sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<TotalOrder>();
        }
    }

    public List<TotalOrder> getOrdersByUserID(Integer page, Integer per_page, String userId)
    {
        Pageable paging = PageRequest.of(page, per_page);

        Slice<TotalOrder> sliceResult = repository.findByUserId(userId, paging);

        if(sliceResult.hasContent()) {
            return sliceResult.getContent();
        } else {
            return new ArrayList<TotalOrder>();
        }
    }
}
