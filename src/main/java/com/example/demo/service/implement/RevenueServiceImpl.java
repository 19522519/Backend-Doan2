package com.example.demo.service.implement;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.RevenueService;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Integer getRevenueByJan() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 1)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByFeb() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 2)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByMar() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 3)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByApr() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 4)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByMay() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 5)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByJun() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 6)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByJul() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 7)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByAug() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 8)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueBySep() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 9)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByOct() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 10)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByNov() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 11)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }

    @Override
    public Integer getRevenueByDec() {
        Integer revenue = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getShippingDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getShippingDate().getMonthValue() == 12)
                    revenue += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return revenue;
    }
}
