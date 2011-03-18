/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public class LogicalNot extends LogicalOperator {

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.LogicalOperator#validate()
   */
  @Override
  public void validate() {
    super.validate();
    
    if (getOperandList().size() > 1) throw new RuntimeException("Too many operands for operator " + getOperatorString() + ".");
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operator#getOperatorString()
   */
  @Override
  public String getOperatorString() {
    return "NOT";
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operand#evaluate()
   */
  public Boolean evaluate() {
    // Validate the operand list. throw a runtime exception if it is empty.
    validate();
    
    return !getOperandList().get(0).evaluate();
  }

}
