/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * This operator takes the first operand as the base, and then
 * exclude all items from all other operands.
 * 
 * @author fyang
 *
 */
public class SetExclusion<T> extends SetOperator<T> {

  /* (non-Javadoc)
   * @see org.praxotech.util.operator.Operand#evaluate()
   */
  @Override
  public Set<T> evaluate() {
    validate();

    Set<T> result = null;
    List<Operand<Set<T>>> ol = getOperandList();
    for (int i = 0; i < ol.size(); i++) {
      Set<T> opSet = ol.get(i).evaluate();
      if (result == null) {
        result = new HashSet<T>(opSet);
      }
      else {
        result.removeAll(opSet);
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
    return "EXCLUSION";
  }

}
