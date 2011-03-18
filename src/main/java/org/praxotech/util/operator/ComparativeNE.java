/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public class ComparativeNE<T extends Comparable<T>> extends
    ComparativeOperator<T> {

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operator#getOperatorString()
   */
  @Override
  public String getOperatorString() {
    return "<>";
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operand#evaluate()
   */
  public Boolean evaluate() {
    // Validate operand list for its size and throw runtime exception if it's not 2
    validate();
    
    return getOperand(0).evaluate().compareTo(getOperand(1).evaluate()) != 0;
  }

}
