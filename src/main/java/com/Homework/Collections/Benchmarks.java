package com.Homework.Collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Benchmarks {

  public static long vectorBenchmark(Vector<Object> vector) {
    long start = System.nanoTime();
    //Добавление
    for (int i = 0; i < 1000; i++) {
      vector.add(Math.random() * 10);
    }
    //добавление по индексу
    for (int i = 0; i < 100; i++) {
      int tempValueAndIndex = (int) Math.floor(Math.random() * vector.size());
      vector.add(tempValueAndIndex ,tempValueAndIndex);
    }
    //удаление
    for (int i = 0; i < 600; i++) {
      int tempIndex = (int) Math.floor(Math.random() * vector.size());
      vector.remove(tempIndex);
    }
    //сортировка
    vector.sort(Comparator.comparing(Object::toString));
    long finish = System.nanoTime();
    return finish - start;
  }

  public static long arrayListBenchmark(ArrayList<Object> arrayList) {
    long start = System.nanoTime();
    //добавление
    for (int i = 0; i < 1000; i++) {
      arrayList.add(Math.random() * 10);
    }
    //добавление по индексу
    for (int i = 0; i < 100; i++) {
      int temp = (int) Math.floor(Math.random() * arrayList.size());
      arrayList.add(temp ,temp);
    }
    //удаление
    for (int i = 0; i < 600; i++) {
      int tempIndex = (int) Math.floor(Math.random() * arrayList.size());
      arrayList.remove(tempIndex);
    }
    //сортировка
    arrayList.sort(Comparator.comparing(Object::toString));
    long finish = System.nanoTime();
    return finish - start;
  }

}
