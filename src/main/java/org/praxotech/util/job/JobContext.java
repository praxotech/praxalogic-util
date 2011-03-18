/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author fyang
 *
 */
public class JobContext {
  private Map<Task, List<Throwable>> exceptionMap = Collections.synchronizedMap(new HashMap<Task, List<Throwable>>());
  private Map<String, Object> ctx = Collections.synchronizedMap(new HashMap<String, Object>());

  /**
   * 
   * @see java.util.Map#clear()
   */
  public void clear() {
    ctx.clear();
  }

  /**
   * @param key
   * @return
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  public boolean containsKey(Object key) {
    return ctx.containsKey(key);
  }

  /**
   * @param value
   * @return
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  public boolean containsValue(Object value) {
    return ctx.containsValue(value);
  }

  /**
   * @return
   * @see java.util.Map#entrySet()
   */
  public Set<Entry<String, Object>> entrySet() {
    return ctx.entrySet();
  }

  /**
   * @param o
   * @return
   * @see java.util.Map#equals(java.lang.Object)
   */
  public boolean equals(Object o) {
    if (!this.getClass().isInstance(o)) return false;
    
    return ctx.equals(this.getClass().cast(o).ctx);
  }

  /**
   * @param key
   * @return
   * @see java.util.Map#get(java.lang.Object)
   */
  public Object get(Object key) {
    return ctx.get(key);
  }

  /**
   * @return
   * @see java.util.Map#hashCode()
   */
  public int hashCode() {
    return ctx.hashCode();
  }

  /**
   * @return
   * @see java.util.Map#isEmpty()
   */
  public boolean isEmpty() {
    return ctx.isEmpty();
  }

  /**
   * @return
   * @see java.util.Map#keySet()
   */
  public Set<String> keySet() {
    return ctx.keySet();
  }

  /**
   * @param key
   * @param value
   * @return
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  public Object put(String key, Object value) {
    return ctx.put(key, value);
  }

  /**
   * @param t
   * @see java.util.Map#putAll(java.util.Map)
   */
  public void putAll(Map<? extends String, ? extends Object> t) {
    ctx.putAll(t);
  }

  /**
   * @param key
   * @return
   * @see java.util.Map#remove(java.lang.Object)
   */
  public Object remove(Object key) {
    return ctx.remove(key);
  }

  /**
   * @return
   * @see java.util.Map#size()
   */
  public int size() {
    return ctx.size();
  }

  /**
   * @return
   * @see java.util.Map#values()
   */
  public Collection<Object> values() {
    return ctx.values();
  }
  
  public List<Throwable> getThrowables(Task t) {
    return exceptionMap.get(t);
  }
  
  public boolean addThrowable(Task t, Throwable e) {
    if (exceptionMap.get(t) == null) {
      exceptionMap.put(t, Collections.synchronizedList(new ArrayList<Throwable>()));
    }
    return exceptionMap.get(t).add(e);
  }
}
