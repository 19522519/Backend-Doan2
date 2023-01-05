package com.example.demo.service.implement;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDto;
import com.example.demo.dto.CheckoutDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.UserOrderDto;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShippingService;
import com.example.demo.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    AddressService addressService;

    @Autowired
    ShippingService shippingService;

    @Autowired
    PaymentService paymentService;

    @Override
    public OrderEntity createOrderBasedOnUser(AppUser appUser) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setOrderStatus("Not delivery");
        orderEntity.setShippingDate(LocalDate.now().plusDays(3));
        orderEntity.setOrderTotal(shoppingCartService.calculateTotalMoney(appUser).toString());
        orderEntity.setAppUser(appUser);
        orderEntity.setIsDeleted(false);

        orderRepository.save(orderEntity);

        List<CartItemDto> cartItemDtos = shoppingCartService.showListCartItems(appUser);
        for (CartItemDto cartItemDto : cartItemDtos) {
            CartItemEntity cartItemEntity = cartItemRepository.findByIdAndIsDeletedIsFalse(cartItemDto.getCartItemId());
            cartItemEntity.setOrder(orderEntity);
            cartItemEntity.setIsDeleted(true);
            cartItemRepository.save(cartItemEntity);

            ProductEntity productEntity = cartItemEntity.getProduct();
            productEntity.setInventory(productEntity.getInventory() - cartItemEntity.getQuantity());
            productRepository.save(productEntity);
        }

        return orderEntity;
    }

    @Override
    public CheckoutDto showCustomerInfo(AppUser appUser) {
        CheckoutDto checkoutDto = new CheckoutDto();

        checkoutDto.setReceiverName(appUser.getLastName() + " " + appUser.getFirstName());
        checkoutDto.setReceiverPhone(appUser.getPhone());
        checkoutDto.setEmail(appUser.getEmail());

        return checkoutDto;
    }

    @Override
    public List<UserOrderDto> showUserOrderPage(AppUser appUser) {
        List<UserOrderDto> userOrderDtos = new ArrayList<>();

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByAppUserAndIsDeletedIsTrue(appUser);
        for (CartItemEntity cartItemEntity : cartItemEntities) {
            UserOrderDto userOrderDto = new UserOrderDto();

            userOrderDto.setThumbnail(cartItemEntity.getProduct().getThumbnail());
            userOrderDto.setProductName(cartItemEntity.getProduct().getName());
            userOrderDto.setQuantity(cartItemEntity.getQuantity());
            userOrderDto.setPrice(Integer.parseInt(cartItemEntity.getProduct().getPrice()));
            userOrderDto.setShippingDate(cartItemEntity.getOrder().getShippingDate());
            userOrderDto.setOrderDate(cartItemEntity.getOrder().getOrderDate());
            userOrderDto.setStatus(cartItemEntity.getOrder().getOrderStatus());

            userOrderDtos.add(userOrderDto);

        }
        return userOrderDtos;
    }

    @Override
    public List<UserOrderDto> showUserOrderPage() {
        List<UserOrderDto> userOrderDtos = new ArrayList<>();

        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();
        for (OrderEntity orderEntity : orderEntities) {
            UserOrderDto userOrderDto = new UserOrderDto();

            userOrderDto.setOrderId(orderEntity.getId());
            userOrderDto.setReceiver(orderEntity.getShipping().getReceiver());
            userOrderDto.setPhone(orderEntity.getShipping().getPhone());
            userOrderDto.setPrice(Integer.parseInt(orderEntity.getOrderTotal()));
            userOrderDto.setPaymentMethod(orderEntity.getPayment().getType());
            userOrderDto.setShippingDate(orderEntity.getShippingDate());
            userOrderDto.setStatus(orderEntity.getOrderStatus());

            userOrderDtos.add(userOrderDto);

        }
        return userOrderDtos;
    }

    @Override
    public List<UserOrderDto> displayEightRecentOrders() {
        List<UserOrderDto> userOrderDtos = new ArrayList<>();
        Pageable topEight = PageRequest.of(0, 8);

        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalseOrderByOrderDateDesc(topEight);
        for (OrderEntity orderEntity : orderEntities) {
            UserOrderDto userOrderDto = new UserOrderDto();

            userOrderDto.setOrderId(orderEntity.getId());
            userOrderDto.setReceiver(orderEntity.getShipping().getReceiver());
            userOrderDto.setQuantity(orderEntity.getCartItems().size());
            userOrderDto.setPrice(Integer.parseInt(orderEntity.getOrderTotal()));

            userOrderDtos.add(userOrderDto);
        }

        return userOrderDtos;
    }

    @Override
    public Integer countOrders() {
        return orderRepository.findByIsDeletedIsFalse().size();
    }

    @Override
    public Integer calculateRevenue() {
        Integer sum = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();
        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getOrderDate().getYear() == LocalDate.now().getYear())
                sum += Integer.parseInt(orderEntity.getOrderTotal());
        }
        return sum;
    }

    @Override
    public void deleteOrder(Integer orderId) {
        OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(orderId);
        orderRepository.delete(orderEntity);
    }

    @Override
    public Integer getRevenueByLastMonth() {
        Integer sum = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();
        LocalDate now = LocalDate.now(); // 2015-11-24
        LocalDate earlier = now.minusMonths(1); // 2015-10-24

        Integer month =earlier.getMonth().getValue(); // 
     
       
        Integer currentMonth = LocalDate.now().getMonthValue();
        Integer currentYear = LocalDate.now().getYear();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")) {
                if (orderEntity.getShippingDate().getMonthValue() == 1) {
                    if (orderEntity.getShippingDate().getMonthValue() == 12 && orderEntity.getShippingDate()
                            .getYear() == currentYear - 1)
                        sum += Integer.parseInt(orderEntity.getOrderTotal());
                } else {
                    if (orderEntity.getShippingDate().getMonthValue() == currentMonth - 1){
                        sum += Integer.parseInt(orderEntity.getOrderTotal());
                    }
                }
            }
        }

        return sum;
    }

    @Override
    public Integer getRevenueByLastWeek() {
        Integer sum = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Integer currentWeek = LocalDate.now().get(weekFields.weekOfWeekBasedYear());

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getOrderDate().getYear() == LocalDate.now().getYear()) {
                if (orderEntity.getOrderDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek - 1)
                    sum += Integer.parseInt(orderEntity.getOrderTotal());
            }
        }

        return sum;
    }

    @Override
    public Integer getRevenueByToday() {
        Integer sum = 0;
        List<OrderEntity> orderEntities = orderRepository.findByIsDeletedIsFalse();

        for (OrderEntity orderEntity : orderEntities) {
            if (orderEntity.getOrderStatus().equals("Delivered")
                    && orderEntity.getOrderDate().isEqual(LocalDate.now()))
                sum += Integer.parseInt(orderEntity.getOrderTotal());
        }

        return sum;
    }

    @Override
    public void saveOrder(List<UserOrderDto> orderDtos) {
        for(UserOrderDto orderDto : orderDtos) { 
            OrderEntity orderEntity = orderRepository.findByIdAndIsDeletedIsFalse(orderDto.getOrderId());
            orderEntity.setOrderStatus(orderDto.getStatus());
            orderRepository.save(orderEntity);}
    
    }
}
