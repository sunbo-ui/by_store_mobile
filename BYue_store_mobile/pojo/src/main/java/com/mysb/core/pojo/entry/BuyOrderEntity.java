package com.mysb.core.pojo.entry;

import com.mysb.core.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

public class BuyOrderEntity implements Serializable {
    private String sellerId;
    private List<BuyCartEntity> orderItems;

    @Override
    public String toString() {
        return "BuyOrderEntity{" +
                "sellerId='" + sellerId + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<BuyCartEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<BuyCartEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
