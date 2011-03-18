/**
 * Copyright (c) 2005 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.praxotech.util.RangeUtil;

/**
 * @author fyang
 *
 */
public class RangeUtilTest {
  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#between(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, boolean, boolean)}.
   */
  @Test
  public void testBetweenTTTBooleanBoolean() {
    assertTrue(RangeUtil.between(Integer.valueOf(0), Integer.valueOf(-1), Integer.valueOf(3), true, true));
    assertFalse(RangeUtil.between(Integer.valueOf(3), Integer.valueOf(-1), Integer.valueOf(3), false, true));
  }

  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#between(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable)}.
   */
  @Test
  public void testBetweenTTT() {
    assertTrue(RangeUtil.between(Integer.valueOf(3), Integer.valueOf(-1), Integer.valueOf(3)));
    assertFalse(RangeUtil.between(Integer.valueOf(-2), Integer.valueOf(-1), Integer.valueOf(3)));
  }

  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#isEmpty(java.lang.Comparable, java.lang.Comparable, boolean, boolean)}.
   */
  @Test
  public void testIsEmpty() {
    assertTrue(RangeUtil.isEmpty(Integer.valueOf(1), Integer.valueOf(1), true, false));
    assertTrue(RangeUtil.isEmpty(Integer.valueOf(2), Integer.valueOf(1), false, false));
    assertFalse(RangeUtil.isEmpty(Integer.valueOf(1), Integer.valueOf(1), false, false));
  }

  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#overlapped(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, boolean, boolean, boolean, boolean)}.
   */
  @Test
  public void testOverlappedTTTTBooleanBooleanBooleanBoolean() {
    assertTrue(RangeUtil.overlapped(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(7), false, false, false, true));
    assertFalse(RangeUtil.overlapped(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(7), false, true, false, true));
  }

  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#overlapped(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, java.lang.Comparable)}.
   */
  @Test
  public void testOverlappedTTTT() {
    assertTrue(RangeUtil.overlapped(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6), Integer.valueOf(7)));
    assertFalse(RangeUtil.overlapped(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(-2), Integer.valueOf(0)));
  }

  /**
   * Test method for {@link com.aptusgrc.util.RangeUtil#covers(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, java.lang.Comparable, boolean, boolean, boolean, boolean)}.
   */
  @Test
  public void testCovers() {
    assertTrue(RangeUtil.covers(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(2), true, true, true, true));
    assertFalse(RangeUtil.covers(Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(2), true, true, false, true));
  }
}
