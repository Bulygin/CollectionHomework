package com.Homework.Collections.cache;

public class Key {

  private final Object keyObject;
  private long timeLife;

  Key(Object key, long timeout) {
    keyObject = key;
    this.timeLife = System.currentTimeMillis() + timeout;
  }

  public Key(Object key) {
    keyObject = key;
  }

  public Object getKey() {
    return keyObject;
  }

  boolean isLive(long currentTimeMillis) {
    return currentTimeMillis < timeLife;
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
    return this.keyObject == other.keyObject || (this.keyObject != null && this.keyObject.equals(other.keyObject));
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 43 * hash + (this.keyObject != null ? this.keyObject.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return "Key{" + "key=" + keyObject + '}';
  }
}
