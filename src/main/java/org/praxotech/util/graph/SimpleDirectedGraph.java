/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.praxotech.util.Pair;

/**
 * @author fyang
 *
 */
public class SimpleDirectedGraph<T> {
  private Set<T> nodes = Collections.synchronizedSet(new HashSet<T>());
  private Set<Pair<T>> edges = Collections.synchronizedSet(new HashSet<Pair<T>>());
  
  public boolean addNode(T node) {
    return nodes.add(node);
  }
  
  public boolean addAllNodes(Collection<? extends T> nodes) {
    return this.nodes.addAll(nodes);
  }
  
  public boolean removeNode(T node) {
    if (!nodes.contains(node)) return true;
    
    // remove edges connected to removed node
    if (nodes.remove(node)) {
      Iterator<Pair<T>> iter = edges.iterator();
      while (iter.hasNext()) {
        Pair<T> e = iter.next();
        if (e.getFirst().equals(node) || e.getSecond().equals(node)) iter.remove();
      }
      return true;
    }
    return false;
  }
  
  public boolean removeAllNodes(Collection<? extends T> nodes) {
    // remove edges with removed nodes
    for (T node : nodes) {
      if (!removeNode(node)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean addEdge(T from, T to) {
    if (!(nodes.contains(from) && nodes.contains(to))) return false;
    
    return edges.add(new Pair<T>(from, to));
  }
  
  public boolean removeEdge(T from, T to) {
    return edges.remove(new Pair<T>(from, to));
  }
  
  /**
   * @return the nodes
   */
  public Set<T> getNodes() {
    return Collections.unmodifiableSet(nodes);
  }

  /**
   * @param nodes the nodes to set
   */
  public void setNodes(Set<T> nodes) {
    this.nodes = nodes;
  }

  public boolean isAcyclic() {
    SimpleDirectedGraph<T> clone = clone();
    Set<T> leaves;
    
    while (true) {
      leaves = clone.getLeafNodes();
      if (leaves.isEmpty()) break;
      clone.removeAllNodes(leaves);
    }
    
    return clone.getNodes().isEmpty();
  }
  
  public Set<T> getLeafNodes() {
    Set<T> leaves = new HashSet<T>();
    
    for (T n : nodes) {
      if (getSuccessors(n).isEmpty()) leaves.add(n);
    }
    
    return leaves;
  }
  
  public Set<T> getRootNodes() {
    Set<T> roots = new HashSet<T>();
    
    for (T n : nodes) {
      if (getPredecessors(n).isEmpty()) roots.add(n);
    }
    
    return roots;
  }
  
  public SimpleDirectedGraph<T> clone() {
    SimpleDirectedGraph<T> clone = new SimpleDirectedGraph<T>();
    
    clone.addAllNodes(nodes);
    clone.edges.addAll(this.edges);
    
    return clone;
  }
  
  public Set<T> getPredecessors(T node) {
    if (!nodes.contains(node)) return Collections.<T>emptySet();
    
    Set<T> from = new HashSet<T>();
    for (Pair<T> e : edges) {
      if (e.getSecond().equals(node) && nodes.contains(e.getFirst())) {
        from.add(e.getFirst());
      }
    }
    return from;
  }
  
  public Set<T> getSuccessors(T node) {
    if (!nodes.contains(node)) return Collections.<T>emptySet();
    
    Set<T> to = new HashSet<T>();
    for (Pair<T> e : edges) {
      if (e.getFirst().equals(node) && nodes.contains(e.getSecond())) {
        to.add(e.getSecond());
      }
    }
    return to;
  }
}
