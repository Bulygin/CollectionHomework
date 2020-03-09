package com.Homework.Collections.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache<K, V> {

  private ConcurrentHashMap<Key, V> globalMap = new ConcurrentHashMap<>();
  private long default_timeout;
  private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
    Thread th = new Thread(r);
    th.setDaemon(true);
    return th;
  });

  public Cache(long default_timeout) throws Exception {
    if (default_timeout < 10) {
      throw new Exception(
          "Too short interval for storage in the cache. Interval should be more than 10 ms");
    }
    this.default_timeout = default_timeout;
    scheduler.scheduleAtFixedRate(() -> {
      long current = System.currentTimeMillis();
      for (Key k : globalMap.keySet()) {
        if (!k.isLive(current)) {
          globalMap.remove(k);
        }
      }
    }, 1, default_timeout / 5, TimeUnit.MILLISECONDS);
  }

  public void setDefault_timeout(long default_timeout) throws Exception {
    if (default_timeout < 100) {
      throw new Exception(
          "Too short interval for storage in the cache. Interval should be more than 10 ms");
    }
    this.default_timeout = default_timeout;
  }

  public void put(K key, V data) {
    globalMap.put(new Key(key, default_timeout), data);
  }

  public void put(K key, V data, long timeout) {
    globalMap.put(new Key(key, timeout), data);
  }

  public V get(K key) {
    return globalMap.get(new Key(key));
  }

  public void remove(K key) {
    globalMap.remove(new Key(key));
  }

  public void removeAll() {
    globalMap.clear();
  }

  public void setAll(Map<K, V> map) {
    ConcurrentHashMap tempmap = new ConcurrentHashMap<Key, V>();
    for (Entry<K, V> entry : map.entrySet()) {
      tempmap.put(new Key(entry.getKey(), default_timeout), entry.getValue());
    }
    globalMap = tempmap;
  }

  public void addAll(Map<K, V> map) {
    for (Entry<K, V> entry : map.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

}
