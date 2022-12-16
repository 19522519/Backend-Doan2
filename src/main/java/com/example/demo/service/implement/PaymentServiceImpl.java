package com.example.demo.service.implement;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CheckoutDto;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.PaymentEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void createPaymentBasedOnOrder(OrderEntity orderEntity, CheckoutDto checkoutDto) {
        PaymentEntity paymentEntity = new PaymentEntity();

        if (orderEntity != null) {
            paymentEntity.setOrder(orderEntity);
            paymentEntity.setType(checkoutDto.getPaymentMethod());
            paymentEntity.setIsDeleted(false);
            paymentRepository.save(paymentEntity);
        }
    }

    @Override
    public void deletePaymentBasedOnOrder(Integer orderId) {
        OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(orderId);
        PaymentEntity paymentEntity = paymentRepository.findByOrder(orderEntity);
        paymentRepository.delete(paymentEntity);
    }
}
