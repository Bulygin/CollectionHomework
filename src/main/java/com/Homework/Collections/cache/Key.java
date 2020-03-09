package com.Homework.Collections.cache;

public class Key {

  private final Object key;
  private long timelife;

  Key(Object key, long timeout) {
    this.key = key;
    this.timelife = System.currentTimeMillis() + timeout;
  }

  public Key(Object key) {
    this.key = key;
  }

  public Object getKey() {
    return key;
  }

  boolean isLive(long currentTimeMillis) {
    return currentTimeMillis < timelife;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Key other = (Key) obj;
    return this.key == other.key || (this.key != null && this.key.equals(other.key));
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 43 * hash + (this.key != null ? this.key.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return "Key{" + "key=" + key + '}';
  }
}
