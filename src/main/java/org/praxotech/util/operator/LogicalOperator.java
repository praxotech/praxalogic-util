/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public abstract class LogicalOperator extends Operator<Boolean, Boolean> {

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operator#validate()
   */
  @Override
  public void validate() {
    if (getOperandList().isEmpty()) throw new RuntimeException("Missing operand for operator " + getOperatorString() + ".");
  }
  
}
