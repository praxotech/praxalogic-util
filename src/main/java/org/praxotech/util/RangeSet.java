/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fyang
 *
 */
public class RangeSet<T extends Comparable<? super T>> extends
    HashSet<Range<T>> {

  /**
   * 
   */
  private static final long serialVersionUID = -4471077542120159426L;

  public boolean include(T point) {
    for (Range<T> range : this) {
      if (range.contains(point)) return true;
    }
    
    return false;
  }

  /* (non-Javadoc)
   * @see java.util.HashSet#add(java.lang.Object)
   */
  @Override
  public boolean add(Range<T> range) {
    if (range.isEmpty()) return false;
    
    Set<Range<T>> ols = getOverlaps(range);
    if (ols.isEmpty()) return super.add(range);
    
    super.removeAll(ols);
    
    Range<T> tempR = range;
    for (Range<T> r : ols) {
      tempR = merge(r, tempR);
    }
    
    return super.add(tempR);
  }
  
  private Range<T> merge(Range<T> r1, Range<T> r2) {
    if (r1.isEmpty()) return r2;
    if (r2.isEmpty()) return r1;
    
    T lower, upper;
    boolean openLower, openUpper;

    // set lower bound and its openness
    if (r1.getLowerBound().compareTo(r2.getLowerBound()) < 0) {
      lower = r1.getLowerBound();
      openLower = r1.isLowerOpen();
    }
    else if (r1.getLowerBound().compareTo(r2.getLowerBound()) > 0) {
      lower = r2.getLowerBound();
      openLower = r2.isLowerOpen();
    }
    else {
      lower = r1.getLowerBound();
      openLower = r1.isLowerOpen() || r2.isLowerOpen();
    }

    // set upper bound and its openness
    if (r1.getUpperBound().compareTo(r2.getUpperBound()) < 0) {
      upper = r2.getUpperBound();
      openUpper = r2.isUpperOpen();
    }
    else if (r1.getUpperBound().compareTo(r2.getUpperBound()) > 0) {
      upper = r1.getUpperBound();
      openUpper = r1.isUpperOpen();
    }
    else {
      upper = r1.getUpperBound();
      openUpper = r1.isUpperOpen() || r2.isUpperOpen();
    }
    
    return Range.getInstance(lower, upper, openLower, openUpper);
  }
  
  private Set<Range<T>> getOverlaps(Range<T> range) {
    Set<Range<T>> ols = new HashSet<Range<T>>();
    
    for (Range<T> r : this) {
      if (range.isOverlapped(r)) {
        ols.add(r);
      }
    }
    
    return ols;
  }
}
