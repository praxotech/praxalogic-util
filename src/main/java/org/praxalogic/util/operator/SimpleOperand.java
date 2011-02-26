/**
 * Copyright (c) 2005 - 2011, Praxalogic, Org. All rights reserved.
 */
package org.praxalogic.util.operator;

/**
 * @author fyang
 *
 */
public abstract class SimpleOperand<T> implements Operand<T> {
  private T value = null;

  /**
   * @param value the value to set
   */
  public void setValue(T value) {
    this.value = value;
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operand#evaluate()
   */
  public T evaluate() {
    return value;
  }
}
