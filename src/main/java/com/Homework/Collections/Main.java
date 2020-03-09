package com.Homework.Collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Vector;


public class Main {

  public static LinkedList<Order> incomingQueue = new LinkedList<Order>();
  public static LinkedList<Order> processedOrders = new LinkedList<Order>();

  public static void main(String[] args) {
    runThreads();
    System.out.println(processedOrders.size());
    removeDuplicate();
    System.out.println(processedOrders.size());
    arraylistVSvector();
  }

  public static void arraylistVSvector() {
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

  public static void runThreads() {
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

  public static void removeDuplicate() {
    ListIterator iterator = processedOrders.listIterator();
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

  public static class Order {

    public final OrderStatus status;
    private int orderItems;

    public Order(OrderStatus status, int orderItems) {
      this.status = status;
      this.orderItems = orderItems;
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

    public OrderStatus getStatus() {
      return status;
    }

    public int getOrderItems() {
      return orderItems;
    }

  }
}