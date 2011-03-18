/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.operator;

/**
 * @author fyang
 *
 */
public class VariableOperand<T> extends SimpleOperand<T> {
  private final String name;
  private final Class<T> type;
  
  public VariableOperand(String name, Class<T> type) {
    this.name = name;
    this.type = type;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!this.getClass().isAssignableFrom(obj.getClass())) return false;
    return name.equals(this.getClass().cast(obj).getName());
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    T v = evaluate();
    return "${" + getName() + (v == null ? "" : " = " + v.toString()) + "}";
  }

  /* (non-Javadoc)
   * @see com.aptusgrc.util.operator.SimpleOperand#evaluate()
   */
  @Override
  public T evaluate() {
    T val = super.evaluate();
    
    if (val == null) throw new RuntimeException("Value is not set for variable " + getName() + ".");
    
    return val;
  }

  public Class<T> getType() {
    return type;
  }
}
