/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.job;

import org.praxotech.util.graph.SimpleDirectedGraph;

/**
 * @author fyang
 *
 * This class implements a DAG.
 */
public class Job extends SimpleDirectedGraph<Task> {
  public enum JobStatus {
    PENDING,
    READY_TO_RUN,
    RUNNING,
    COMPLETE_SUCCESSFUL,
    COMPLETE_WITH_ERROR
  };
  
  private JobStatus status = JobStatus.PENDING;
  private final JobContext context = new JobContext();
  
  /**
   * @return the context
   */
  public JobContext getContext() {
    return context;
  }

  public JobStatus getStatus() {
    return status;
  }
  
  /**
   * @param status the status to set
   */
  public void setStatus(JobStatus status) {
    this.status = status;
  }

  public boolean isRunning() {
    return status == JobStatus.RUNNING;
  }
  
  public boolean isComplete() {
    return status == JobStatus.COMPLETE_SUCCESSFUL || status == JobStatus.COMPLETE_WITH_ERROR;
  }

  /* (non-Javadoc)
   * @see com.foxt.util.graph.SimpleDirectedGraph#addEdge(java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean addEdge(Task from, Task to) {
    SimpleDirectedGraph<Task> test = clone();
    test.addEdge(from, to);
    if (test.isAcyclic()) {
      return super.addEdge(from, to);
    }
    else {
      return false;
    }
  }
}
