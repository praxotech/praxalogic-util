/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

import java.util.List;

/**
 * @author fyang
 *
 */
public class LogicalAnd extends LogicalOperator {

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operator#getOperatorString()
   */
  public String getOperatorString() {
    return "AND";
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.Operand#evaluate()
   */
  public Boolean evaluate() {
    // Validate the operand list. throw a runtime exception if it is empty.
    validate();

    List<Operand<Boolean>> ol = getOperandList();
    
    for (Operand<Boolean> op : ol) {
      if (!op.evaluate()) return false;
    }
    
    return true;
  }

}
