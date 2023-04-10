package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderRepository repository = new OrderRepository();
    public void addOrder(Order order) {
        repository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        repository.addDeliveryPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        repository.assignOrderByPartner(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        List<String> orderList = repository.getAllOrders();
        for (String o : orderList) {
            if (o.equals(orderId)) return new Order(orderId);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        List<DeliveryPartner> partnerList = repository.getAllPartners();
        for (DeliveryPartner dp: partnerList) {
            if (dp.getId().equals(partnerId)) return dp;
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return repository.getOrderCountByPartner(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return repository.getOrderByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return repository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return repository.getUnAssignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int deliveryTime = Integer.parseInt(time);
        return repository.getOrdersLeftAfterGivenTime(deliveryTime,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return repository.getLastDeliveryTime(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        repository.deleteDeliveryPartner(partnerId);
    }

    public void deleteOrderById(String orderId) {
        repository.deleteOrderById(orderId);
    }
}
