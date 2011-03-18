/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fyang
 *
 */
public class SetIntersection<T> extends SetOperator<T> {

/* (non-Javadoc)
 * @see org.praxotech.util.operator.Operand#evaluate()
 */
  @Override
  public Set<T> evaluate() {
    validate();

    Set<T> result = null;
    for (Operand<Set<T>> op : getOperandList()) {
      if (result == null) {
        result = new HashSet<T>(op.evaluate());
      }
      else {
        result.retainAll(op.evaluate());
      }
      
      if (result.isEmpty()) break;
    }

    return result;
  }

  /* (non-Javadoc)
   * @see org.praxotech.util.operator.Operator#getOperatorString()
   */
  @Override
  public String getOperatorString() {
    return "INTERSECT";
  }

}
