/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util;

/**
 * @author fyang
 *
 */
public class Range<T extends Comparable<? super T>> extends Pair<T> {
  private final boolean lowerOpen;
  private final boolean upperOpen;
  private final boolean blank;

  private Range(T lower, T upper, boolean lowerOpen, boolean upperOpen) {
    super(lower, upper);
    
    if (lower != null && upper != null && lower.compareTo(upper) > 0)
      throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
    
    this.lowerOpen = lowerOpen;
    this.upperOpen = upperOpen;
    this.blank = false;
  }
  
  private Range() {
    super(null, null);
    
    this.blank = true;
    this.lowerOpen = false;
    this.upperOpen = false;
  }

  /**
   * To detect if this range covers the other one, i.e., this contains all elements
   * in the other one.
   * 
   * @param other the other range
   * @return true if this range contains all elements in the other one, false otherwise.
   */
  public boolean contains(Range<? extends T> other) {
    if (other == null || isEmpty()) return false;
    
    if (other.isEmpty()) return true;
    
    return RangeUtil.covers(getLowerBound(), getUpperBound(), other.getLowerBound(),
        other.getUpperBound(), isLowerOpen(), isUpperOpen(), other.isLowerOpen(), other.isUpperOpen());
  }

  /**
   * Detects if this and the other ranges are disjoint, i.e., no overlapping
   * 
   * @param other the other range
   * @return true if no overlapping, false otherwise.
   */
  public boolean isDisjoint(Range<? extends T> other) {
    return !isOverlapped(other);
  }

  /**
   * Return the lower boundary of the range. Null represents an open end.
   * 
   * @return the lower boundary
   */
  public T getLowerBound() {
    return getFirst();
  }

  /**
   * Return the upper boundary of the range. Null represents an open end.
   * 
   * @return the upper boundary
   */
  public T getUpperBound() {
    return getSecond();
  }

  /**
   * To detect if the parameter value lies in the range. A null value will
   * always return false.
   * 
   * @param <V> the parameter type has to be the same as or derived from type T
   * @param value the value to test. Null represents infinity
   * @return true if value is in the range, false otherwise
   */
  public boolean contains(T value) {
    if (isEmpty() || value == null) return false;

    return RangeUtil.between(value, getLowerBound(), getUpperBound(), lowerOpen, upperOpen);
  }

  /**
   * Detects if this range overlaps with the other one, i.e., this contains
   * some of the elements in the other one.
   * 
   * @param other the other range
   * @return true if this range contains some of the elements in the other one, false
   * otherwise
   */
  public boolean isOverlapped(Range<? extends T> other) {
    if (other == null || isEmpty() || other.isEmpty()) return false;
    
    return RangeUtil.overlapped(getLowerBound(), getUpperBound(),
        other.getLowerBound(), other.getUpperBound(), lowerOpen, upperOpen,
        other.isLowerOpen(), other.isUpperOpen());
  }

  /**
   * Returns the intersection of this range and the parameter other. The result
   * is a new range object with the larger of the two lower bounds and the smaller
   * of the two upper bounds as its boundaries.
   * 
   * @param other the other range that will be intersected with this to generate
   *        the new ranges.
   * @return a new range objects that is the intersection of this and the parameter
   *         other.
   */
  public Range<T> intersects(Range<? extends T> other) {
    if (other == null) return null;
    if (isEmpty() || other.isEmpty()) return new Range<T>(); // return a blank range
    
    T lb, ub;
    boolean exclLower, exclUpper;
    
    // lower bound and inclusiveness
    if (getLowerBound() == null ||
        (other.getLowerBound() != null && getLowerBound().compareTo(other.getLowerBound()) < 0)) {
      lb = other.getLowerBound();
      exclLower = other.isLowerOpen();
    }
    else if (other.getLowerBound() == null ||
        getLowerBound().compareTo(other.getLowerBound()) > 0) {
      lb = getLowerBound();
      exclLower = lowerOpen;
    }
    else {
      lb = getLowerBound();
      exclLower = lowerOpen || other.isLowerOpen();
    }

    // upper bound and inclusiveness
    if (getUpperBound() == null ||
        (other.getUpperBound() != null && getUpperBound().compareTo(other.getUpperBound()) > 0)) {
      ub = other.getUpperBound();
      exclUpper = other.isUpperOpen();
    }
    else if (other.getUpperBound() == null ||
        getUpperBound().compareTo(other.getUpperBound()) < 0) {
      ub = getUpperBound();
      exclUpper = upperOpen;
    }
    else {
      ub = getUpperBound();
      exclUpper = upperOpen || other.isUpperOpen();
    }
    
    return new Range<T>(lb, ub, exclLower, exclUpper);
  }
  
  public Range<T> conjoins(Range<? extends T> other) {
    if (other == null || !(isEmpty() || other.isEmpty() || isOverlapped(other))) return null; // Not conjoinable as a range since no overlapping
    
    if (other.isEmpty()) {
      return isEmpty()? new Range<T>() : new Range<T>(getLowerBound(), getUpperBound(), lowerOpen, upperOpen);
    }
    
    T lb, ub;
    boolean exclLower, exclUpper;
    
    // lower bound and inclusiveness
    if (getLowerBound() == null ||
        (other.getLowerBound() != null && getLowerBound().compareTo(other.getLowerBound()) < 0)) {
      lb = getLowerBound();
      exclLower = isLowerOpen();
    }
    else if (other.getLowerBound() == null ||
        getLowerBound().compareTo(other.getLowerBound()) > 0) {
      lb = other.getLowerBound();
      exclLower = other.lowerOpen;
    }
    else {
      lb = getLowerBound();
      exclLower = lowerOpen && other.isLowerOpen();
    }

    // upper bound and inclusiveness
    if (getUpperBound() == null ||
        (other.getUpperBound() != null && getUpperBound().compareTo(other.getUpperBound()) > 0)) {
      ub = getUpperBound();
      exclUpper = isUpperOpen();
    }
    else if (other.getUpperBound() == null ||
        getUpperBound().compareTo(other.getUpperBound()) < 0) {
      ub = other.getUpperBound();
      exclUpper = other.upperOpen;
    }
    else {
      ub = getUpperBound();
      exclUpper = upperOpen && other.isUpperOpen();
    }
    
    return new Range<T>(lb, ub, exclLower, exclUpper);
  }
  
  public RangeSet<T> excludes(Range<T> other) {
    RangeSet<T> result = new RangeSet<T>();
    
    if (other == null || other.isEmpty() || !isOverlapped(other)) {
      result.add(this);
    }
    else if (isEmpty() || equals(other) || other.contains(this)) {
      // do nothing;
    }
    else if (contains(other)) {
      result.add(new Range<T>(getLowerBound(), other.getLowerBound(), lowerOpen, !other.lowerOpen));
      result.add(new Range<T>(other.getUpperBound(), getUpperBound(), !other.upperOpen, upperOpen));
    }
    else {
      T lb, ub;
      boolean exclLower, exclUpper;
      if (other.getLowerBound() == null || !contains(other.getLowerBound())) {
        lb = other.getUpperBound();
        exclLower = !other.upperOpen;
        ub = getUpperBound();
        exclUpper = upperOpen;
      }
      else {
        lb = getLowerBound();
        exclLower = lowerOpen;
        ub = other.getLowerBound();
        exclUpper = !other.lowerOpen;
      }
      
      result.add(new Range<T>(lb, ub, exclLower, exclUpper));
    }
    
    return result;
  }
  
  public boolean isEmpty() {
    return blank || RangeUtil.isEmpty(getLowerBound(), getUpperBound(), isLowerOpen(), isUpperOpen());
  }
  
  /**
   * Returns a range object for any comparable type.
   * 
   * @param <V> The type for which the returned range object is created. It must be
   *            assignment compatible with type Comparable.
   * @param lower The lower bound of the range. Null represents an open lower bound.
   * @param upper The upper bound of the range. Null represents an open upper bound.
   * @return a range object with lower and upper as its boundaries. The comparator is
   *         the native one defined for type V or one of its super classes.
   */
  public static <V extends Comparable<? super V>> Range<V> getInstance(V lower, V upper,
      boolean lowerOpen, boolean upperOpen) {
    return new Range<V>(lower, upper, lowerOpen, upperOpen);
  }

  /**
   * @return the lowerOpen
   */
  public boolean isLowerOpen() {
    return lowerOpen;
  }

  /**
   * @return the upperOpen
   */
  public boolean isUpperOpen() {
    return upperOpen;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    if (isEmpty()) return 0;
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (lowerOpen ? 1231 : 1237);
    result = prime * result + (upperOpen ? 1231 : 1237);
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Range)) {
      return false;
    }

    if (!Range.class.isInstance(obj)) {
      return false;
    }

    Range<T> other = Range.class.cast(obj);
    if (other == null) return false;
    
    if (isEmpty() && other.isEmpty()) return true;

    if (!super.equals(obj)) {
      return false;
    }

    return lowerOpen == other.lowerOpen && upperOpen == other.upperOpen;
  }
}
