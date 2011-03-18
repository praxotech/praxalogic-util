/**
 * Copyright (c), 2008 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.praxotech.util.graph.SimpleDirectedGraph;

/**
 * @author fyang
 *
 */
public class SimpleDirectedGraphTest {
  private SimpleDirectedGraph<String> tg;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    tg = new SimpleDirectedGraph<String>();
    tg.addNode("0");
    tg.addNode("1");
    tg.addNode("2");
    tg.addNode("3");
    tg.addNode("4");
    tg.addNode("5");
    tg.addNode("6");
    tg.addNode("7");
    tg.addNode("8");
    tg.addNode("9");
    tg.addEdge("0", "6");
    tg.addEdge("0", "8");
    tg.addEdge("1", "5");
    tg.addEdge("1", "8");
    tg.addEdge("2", "7");
    tg.addEdge("3", "4");
    tg.addEdge("3", "9");
    tg.addEdge("4", "5");
    tg.addEdge("6", "3");
    tg.addEdge("8", "5");
  }

  /**
   * Test method for {@link org.praxotech.util.graph.SimpleDirectedGraph#isAcyclic()}.
   */
  @Test
  public void testIsAcyclic() {
    assertTrue(tg.isAcyclic());
    tg.addEdge("3", "0");
    assertFalse(tg.isAcyclic());
    tg.removeEdge("3", "0");
    assertTrue(tg.isAcyclic());
    tg.addEdge("5", "3");
    assertFalse(tg.isAcyclic());
  }

}
