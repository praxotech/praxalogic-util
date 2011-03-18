/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fyang
 *
 */
public abstract class Operator<T, E> implements Operand<T> {
  private List<Operand<E>> operand = new ArrayList<Operand<E>>();
  
  public List<Operand<E>> getOperandList() {
    return operand;
  }
  
  public abstract String getOperatorString();
  
  public abstract void validate();

  /**
   * @param index
   * @param element
   * @see java.util.List#add(int, java.lang.Object)
   */
  public void addOperandAt(int index, Operand<E> element) {
    operand.add(index, element);
  }

  /**
   * @param o
   * @return
   * @see java.util.List#add(java.lang.Object)
   */
  public boolean addOperand(Operand<E> o) {
    return operand.add(o);
  }

  /**
   * @param c
   * @return
   * @see java.util.List#addAll(java.util.Collection)
   */
  public boolean addAllOperands(Collection<? extends Operand<E>> c) {
    return operand.addAll(c);
  }

  /**
   * @param index
   * @param c
   * @return
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  public boolean addAllOperandsAt(int index, Collection<? extends Operand<E>> c) {
    return operand.addAll(index, c);
  }

  /**
   * 
   * @see java.util.List#clear()
   */
  public void clearOperandList() {
    operand.clear();
  }

  /**
   * @param o
   * @return
   * @see java.util.List#contains(java.lang.Object)
   */
  public boolean containsOperand(Object o) {
    return operand.contains(o);
  }

  /**
   * @param c
   * @return
   * @see java.util.List#containsAll(java.util.Collection)
   */
  public boolean containsAllOperands(Collection<?> c) {
    return operand.containsAll(c);
  }

  /**
   * @param index
   * @return
   * @see java.util.List#get(int)
   */
  public Operand<E> getOperand(int index) {
    return operand.get(index);
  }

  /**
   * @param o
   * @return
   * @see java.util.List#indexOf(java.lang.Object)
   */
  public int indexOfOperand(Object o) {
    return operand.indexOf(o);
  }

  /**
   * @return
   * @see java.util.List#isEmpty()
   */
  public boolean isOperandListEmpty() {
    return operand.isEmpty();
  }

  /**
   * @param o
   * @return
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  public int lastIndexOfOperand(Object o) {
    return operand.lastIndexOf(o);
  }

  /**
   * @param index
   * @return
   * @see java.util.List#remove(int)
   */
  public Operand<E> removeOperandAt(int index) {
    return operand.remove(index);
  }

  /**
   * @param o
   * @return
   * @see java.util.List#remove(java.lang.Object)
   */
  public boolean removeOperand(Object o) {
    return operand.remove(o);
  }

  /**
   * @param c
   * @return
   * @see java.util.List#removeAll(java.util.Collection)
   */
  public boolean removeAllOperands(Collection<?> c) {
    return operand.removeAll(c);
  }

  /**
   * @param c
   * @return
   * @see java.util.List#retainAll(java.util.Collection)
   */
  public boolean retainAllOperands(Collection<?> c) {
    return operand.retainAll(c);
  }

  /**
   * @param index
   * @param element
   * @return
   * @see java.util.List#set(int, java.lang.Object)
   */
  public Operand<E> setOperandAt(int index, Operand<E> element) {
    return operand.set(index, element);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    if (operand.size() > 0) {
      sb.append("("); // opening parenthesis
    }
    for (Operand<E> op : operand) {
      if (sb.length() > 0) {
        sb.append(") ").append(getOperatorString()).append(" (");
      }
      sb.append(op.toString());
    }
    if (operand.size() > 0) {
      sb.append(")"); // closing parenthesis
    }
    
    return sb.toString();
  }
  
}
