/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util;

/**
 * @author fyang
 *
 */
public class DualObject<F, S> {
  private final F first;
  private final S second;
  
  public DualObject(F first, S second) {
    this.first = first;
    this.second = second;
  }

  /**
   * @return the first
   */
  public F getFirst() {
    return first;
  }

  /**
   * @return the second
   */
  public S getSecond() {
    return second;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!this.getClass().isAssignableFrom(obj.getClass())) return false;

    Object of = DualObject.class.cast(obj).first;
    Object os = DualObject.class.cast(obj).second;
    
    return ((first == null && of == null) || first.equals(of)) &&
           ((second == null && os == null) || second.equals(os));
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return (first == null? 0 : first.hashCode()) ^
           (second == null? 0 : second.hashCode());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "(" + (first == null? "<NULL>" : first.toString()) + ", " +
                 (second == null? "<NULL>" : second.toString()) + ")";
  }
}
