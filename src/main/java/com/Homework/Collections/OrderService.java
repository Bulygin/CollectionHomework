package com.Homework.Collections;

import com.Homework.Collections.Main.Order;
import com.Homework.Collections.Main.OrderStatus;

public class OrderService extends Thread {

  private boolean isActive;

  public OrderService(String name) {
    super(name);
  }

  OrderService() {
    isActive = true;
  }

  void disable() {
    isActive = false;
  }

  @Override
  public void run() {
    while (isActive) {
      if (!Main.incomingQueue.isEmpty()) {
        Order order = Main.incomingQueue.peekFirst();
        Main.processedOrders.addLast(new Order(OrderStatus.PROCESSING, order.getOrderItems()));
        Main.incomingQueue.remove(order);
      }
    }
  }
}
