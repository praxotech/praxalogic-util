/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public abstract class ComparativeOperator<T extends Comparable<T>> extends Operator<Boolean, T> {

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operator#validate()
   */
  @Override
  public void validate() {
    if (getOperandList().size() != 2) throw new RuntimeException("Two operands are required for the operator.");
  }

}
