/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.job;

import java.rmi.server.UID;
import java.util.Observable;

/**
 * @author fyang
 *
 */
public abstract class Task extends Observable implements Runnable {
  public enum TaskStatus {
    NEW,
    SUBMITTED,
    IN_PREPROC,
    READY,
    NOT_READY,
    RUNNING,
    EXECUTED,
    EXEC_ERR,
    IN_POSTPROC,
    POSTPROC_ERR,
    POSTPROC_FAILED,
    SUCCESSFUL,
    FAILED,
    COMPLETED_WITH_ERR
  }
  
  private final String taksId = new UID().toString();
  private TaskStatus status = TaskStatus.NEW;
  private final JobContext ctx;

  public Task(JobContext ctx) {
    if (ctx == null) {
      this.ctx = new JobContext();
    }
    else {
      this.ctx = ctx;
    }
  }
  
  /**
   * @return the ctx
   */
  public JobContext getCtx() {
    return ctx;
  }

  /**
   * @return the taksId
   */
  public String getTaksId() {
    return taksId;
  }

  /**
   * @return the status
   */
  public TaskStatus getStatus() {
    return status;
  }
  
  public void reset() throws Exception {
    if (getStatus() != TaskStatus.NOT_READY) throw new Exception("Task cannot be reset.");
    
    setStatus(TaskStatus.NEW);
  }

  /**
   * @param status the status to set
   */
  private void setStatus(TaskStatus status) {
    if (this.status != status) {
      this.status = status;
      setChanged();
    }
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public final void run() {
    if (getStatus() != TaskStatus.NEW) {
      try {
        reset();
      }
      catch (Exception e) {
        setStatus(TaskStatus.FAILED);
        ctx.addThrowable(this, e);
        return;
      }
    }
    
    try {
      setStatus(TaskStatus.IN_PREPROC);
      if (preprocess()) {
        setStatus(TaskStatus.READY);
      }
      else {
        setStatus(TaskStatus.NOT_READY);
      }
    }
    catch (Throwable e) {
      setStatus(TaskStatus.FAILED);
      ctx.addThrowable(this, e);
      return;
    }
    
    if (getStatus() == TaskStatus.NOT_READY) return;
    
    try {
      setStatus(TaskStatus.RUNNING);
      if (executeTask()) {
        setStatus(TaskStatus.EXECUTED);
      }
      else {
        setStatus(TaskStatus.EXEC_ERR);
      }
    }
    catch (Throwable e) {
      setStatus(TaskStatus.FAILED);
      ctx.addThrowable(this, e);
      return;
    }
    
    if (getStatus() == TaskStatus.EXEC_ERR) return;
    
    try {
      setStatus(TaskStatus.IN_POSTPROC);
      if (postprocess()) {
        setStatus(TaskStatus.SUCCESSFUL);
      }
      else {
        setStatus(TaskStatus.POSTPROC_ERR);
      }
    }
    catch (Throwable e) {
      setStatus(TaskStatus.POSTPROC_FAILED);
      ctx.addThrowable(this, e);
    }

    notifyObservers();
  }
  
  protected abstract boolean executeTask() throws Throwable;
  
  protected boolean preprocess() throws Throwable {
    return true;
  }
  
  protected boolean postprocess() throws Throwable {
    return true;
  }
}
