package com.Homework.Collections;

import com.Homework.Collections.Main.Order;
import com.Homework.Collections.Main.OrderStatus;

public class OrdersThread extends Thread {

  public OrdersThread() {
    super();
  }

  @Override
  public void run() {
    for (int i = 0; i < 50; i++) {
      synchronized (Main.incomingQueue) {
        Order anOrder = new Order(OrderStatus.NOT_STARTED, (int) Math.floor(Math.random() * 10));
        Main.incomingQueue.addLast(anOrder);
      }
    }
  }
}
