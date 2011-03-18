/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util;

/**
 * @author fyang
 *
 */
public class RangeUtil {
  public static <T extends Comparable<? super T>> boolean between(T subject, T lower, T upper,
      boolean exclLowerEnd, boolean exclUpperEnd) {
    if (subject == null) return false;
    
    boolean validExclEnds = (lower == null? true : lower.compareTo(subject) < 0) &&
                            (upper == null? true : upper.compareTo(subject) > 0);
    
    return validExclEnds || (!exclLowerEnd && subject.equals(lower)) || (!exclUpperEnd && subject.equals(upper));
  }
  
  public static <T extends Comparable<? super T>> boolean between(T subject, T lower, T upper) {
    return between(subject, lower, upper, false, false);
  }
  
  public static <T extends Comparable<? super T>> boolean isEmpty(T lower, T upper,
      boolean exclLowerEnd, boolean exclUpperEnd) {
    
    if (lower == null || upper == null) return false;
    
    return lower.compareTo(upper) > 0 || ((exclLowerEnd || exclUpperEnd) && lower.compareTo(upper) == 0);
  }
  
  public static <T extends Comparable<? super T>> boolean overlapped(T firstLower, T firstUpper,
      T secondLower, T secondUpper, boolean exclFirstLower, boolean exclFirstUpper,
      boolean exclSecondLower, boolean exclSecondUpper) {
    
    if (isEmpty(firstLower, firstUpper, exclFirstLower, exclFirstUpper) ||
        isEmpty(secondLower, secondUpper, exclSecondLower, exclSecondUpper))
      return false;
    
    if (firstLower == null || firstUpper == null || secondLower == null || secondUpper == null)
      return true;
    
    return (between(firstLower, secondLower, secondUpper, exclSecondLower, exclSecondUpper) &&
            !(exclFirstLower && firstLower.equals(secondUpper))) ||
           (between(firstUpper, secondLower, secondUpper, exclSecondLower, exclSecondUpper) &&
            !(exclFirstUpper && firstUpper.equals(secondLower))) ||
           (between(secondLower, firstLower, firstUpper, exclFirstLower, exclFirstUpper) &&
            !(exclSecondLower && secondLower.equals(firstUpper))) ||
           (between(secondUpper, firstLower, firstUpper, exclFirstLower, exclFirstUpper) &&
            !(exclSecondUpper && secondUpper.equals(firstLower)));
  }
  
  public static <T extends Comparable<? super T>> boolean overlapped(T firstLower, T firstUpper,
      T secondLower, T secondUpper) {
    return overlapped(firstLower, firstUpper, secondLower, secondUpper, false, false, false, false);
  }
  
  public static <T extends Comparable<? super T>> boolean covers(T firstLower, T firstUpper,
      T secondLower, T secondUpper, boolean exclFirstLower, boolean exclFirstUpper,
      boolean exclSecondLower, boolean exclSecondUpper) {
    if (isEmpty(firstLower, firstUpper, exclFirstLower, exclFirstUpper)) return false;
    if (isEmpty(secondLower, secondUpper, exclSecondLower, exclSecondUpper)) return true;
    
    // firstly, return false if the second lower end is not included in the first range.
    if (!between(secondLower, firstLower, firstUpper, false, exclFirstUpper) ||
        (exclSecondLower && secondLower.equals(firstUpper)) ||
        (exclFirstLower && !exclSecondLower)) return false;
    
    // secondly, test the second upper bound.
    int cmp = secondUpper.compareTo(firstUpper);
    return !(cmp > 0 || (cmp == 0 && exclFirstUpper && !exclSecondUpper));
  }
}
