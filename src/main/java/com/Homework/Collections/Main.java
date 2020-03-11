package com.Homework.Collections;

import com.Homework.Collections.cache.Cache;
import com.Homework.Collections.cache.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Main {

  static ConcurrentLinkedDeque<Order> incomingQueue = new ConcurrentLinkedDeque<>();
  static ConcurrentLinkedDeque<Order> processedOrders = new ConcurrentLinkedDeque<>();

  public static void main(String[] args) throws Exception {
    Cache<Key, Order> cache = new Cache(1000);
    Key key1 = new Key(new Object());
    Key key2 = new Key(new Object());
    cache.put(key1, new Order(OrderStatus.COMPLETED, 25));
    cache.put(key2, new Order(OrderStatus.PROCESSING, 70), 1000000);

    runThreads();
    System.out.println(processedOrders.size());
    removeDuplicate();
    System.out.println(processedOrders.size());
    ArrayListOrVector();

    Optional<Order> optionalOrder1 = cache.get(key2);
    if (optionalOrder1.isPresent()) {
      System.out.println(optionalOrder1.get());
    }

    Optional<Order> optionalOrder2 = cache.get(key1);
    if (optionalOrder2.isPresent()) {
      System.out.println(optionalOrder2.get());
    }
    OrderFactory orderFactory = new OrderFactory() {
    };

    List<Order> orderList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      orderList.add(orderFactory.createCompleted((int) Math.floor(Math.random() * 100)));
      orderList.add(orderFactory.createNotStarted((int) Math.floor(Math.random() * 100)));
      orderList.add(orderFactory.createProcessing((int) Math.floor(Math.random() * 100)));
    }

    orderList.stream().filter(order -> order.filterItems(order, 70))
        .filter(order -> order.filterStatus(order, OrderStatus.COMPLETED))
        .forEach(order -> System.out.println("From stream: " + order));
  }

  private static void ArrayListOrVector() {
    Vector<Object> vector = new Vector();
    ArrayList<Object> arrayList = new ArrayList();
    long arrayListTime = 0;
    long vectorTime = 0;
    for (int i = 0; i < 100; i++) {
      arrayListTime += Benchmarks.arrayListBenchmark(arrayList);
      vectorTime += Benchmarks.vectorBenchmark(vector);
    }
    String result = arrayListTime > vectorTime ? "ArrayList" : "Vector";
    System.out.println(result + " is faster");
  }

  private static void runThreads() {
    OrdersThread ordersThread1 = new OrdersThread();
    OrdersThread ordersThread2 = new OrdersThread();
    OrdersThread ordersThread3 = new OrdersThread();
    OrderService orderService = new OrderService();
    ordersThread1.start();
    ordersThread2.start();
    ordersThread3.start();
    orderService.start();

    while (true) {
      if (incomingQueue.isEmpty() && !ordersThread1.isAlive() && !ordersThread2.isAlive()
          && !ordersThread3.isAlive()) {
        orderService.disable();
        break;
      }
    }
  }

  private static void removeDuplicate() {
    Iterator iterator = processedOrders.iterator();
    HashSet tempSet = new HashSet();

    while (iterator.hasNext()) {
      Object obj = iterator.next();
      if (tempSet.contains(obj)) {
        iterator.remove();
      } else {
        tempSet.add(obj);
      }
    }
  }

  public enum OrderStatus {
    NOT_STARTED, PROCESSING, COMPLETED
  }

  public static class Order implements OrderFactory {

    final OrderStatus status;
    private int orderItems;

    Order(OrderStatus status, int orderItems) {
      this.status = status;
      this.orderItems = orderItems;
    }

    boolean filterItems(Order order, int orderItems) {
      return order.getOrderItems() > orderItems;
    }

    boolean filterStatus(Order order, OrderStatus orderStatus) {
      return order.getStatus() == orderStatus;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Order)) {
        return false;
      }
      Order order = (Order) o;
      return getOrderItems() == order.getOrderItems() && getStatus() == order.getStatus();
    }

    @Override
    public int hashCode() {
      return Objects.hash(getStatus(), getOrderItems());
    }

    @Override
    public String toString() {
      return "Order{" + "orderItems=" + orderItems + ", status=" + status + '}';
    }

    OrderStatus getStatus() {
      return status;
    }

    int getOrderItems() {
      return orderItems;
    }

  }
}