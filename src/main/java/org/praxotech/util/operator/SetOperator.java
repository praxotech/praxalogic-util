/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

import java.util.Set;

public abstract class SetOperator<T> extends Operator<Set<T>, Set<T>> {

  @Override
  public void validate() {
    if (getOperandList().isEmpty()) throw new RuntimeException("At least one operand is required for the operator.");
  }

}
