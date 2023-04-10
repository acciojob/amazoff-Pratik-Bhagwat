package com.driver;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    HashMap<String,Order> orderDB = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryPartnerDB = new HashMap<>();
    HashMap<String, List<String>> orderPartnerDB = new HashMap<>();
//    HashMap<String,Order> unassignedOrderDB = new HashMap<>();

    public void addOrder(Order order) {
        orderDB.put(order.getId(), order);
    }
    public void addDeliveryPartner(DeliveryPartner partner) {
        deliveryPartnerDB.put(partner.getId(), partner);
    }
    public void assignOrderByPartner(String orderId,String partnerId) {
        orderPartnerDB.putIfAbsent(partnerId,new ArrayList<>());
//        Order order = orderDB.get(orderId);
        orderPartnerDB.get(partnerId).add(orderId);
    }
    public List<String> getAllOrders() {
        return new ArrayList<>(orderDB.keySet());
    }
    public List<DeliveryPartner> getAllPartners() {
        return new ArrayList<>(deliveryPartnerDB.values());
    }
    public Integer getOrderCountByPartner(String partnerId) {
        int orderCount = 0;
        if (orderPartnerDB.containsKey(partnerId)) {
            List<String > orderList = orderPartnerDB.get(partnerId);
            orderCount =  orderList.size();
        }
        return orderCount;
    }
    public List<String> getOrderByPartnerId(String partnerId) {
        return new ArrayList<>(orderPartnerDB.get(partnerId));
    }
    public Integer getUnAssignedOrders() {
        int count = 0;
        for (Order o : orderDB.values()) {
            if(!orderPartnerDB.containsKey(o.getId())) count++;
        }
        return count;
    }
    public Integer getOrdersLeftAfterGivenTime(int time, String partnerId) {
        int count = 0;
        List<String> orderList = orderPartnerDB.get(partnerId);
        for (String s : orderList) {
            Order order = new Order(s);
            if(order.getDeliveryTime() > time) count++;
        }
        return count;
    }
    public String getLastDeliveryTime(String partnerId) {
        int maxTime = 0;
        List<String> orderList = orderPartnerDB.get(partnerId);
        for (String s : orderList) {
            Order order = new Order(s);
            maxTime = Math.max(maxTime,order.getDeliveryTime());
        }
        return maxTime+"";
    }
    public void deleteDeliveryPartner(String partnerId) {
        orderPartnerDB.remove(partnerId);
    }
    public void deleteOrderById(String orderId) {
        orderDB.remove(orderId);
        for (String s : orderPartnerDB.keySet()) {
            List<String> orderList = orderPartnerDB.get(s);
            orderList.removeIf(order -> order.equals(orderId));
        }
    }
}
