package com.Homework.Collections;

import com.Homework.Collections.Main.Order;
import com.Homework.Collections.Main.OrderStatus;

public interface OrderFactory {

  default Order createNotStarted(int orderItems) {
    return new Order(OrderStatus.NOT_STARTED, orderItems);
  }

  default Order createProcessing(int orderItems) {
    return new Order(OrderStatus.PROCESSING, orderItems);
  }

  default Order createCompleted(int orderItems) {
    return new Order(OrderStatus.COMPLETED, orderItems);
  }
}
