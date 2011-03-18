/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public class ConstantOperand<T> extends SimpleOperand<T> {
  public ConstantOperand(T value) {
    super.setValue(value);
  }

  /* (non-Javadoc)
   * This method is not supported due to its "Constant" nature. Its
   * value will be set by the constructor.
   */
  @Override
  public void setValue(T value) {
    throw new RuntimeException("Method setValue(T) is not supported for class ConstantOperand.");
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return evaluate().toString();
  }
  
}
