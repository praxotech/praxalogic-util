/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.job.management;

import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.praxotech.util.job.Job;
import org.praxotech.util.job.JobContext;
import org.praxotech.util.job.Task;
import org.praxotech.util.job.Job.JobStatus;
import org.praxotech.util.job.Task.TaskStatus;


/**
 * @author fyang
 *
 */
public class JobManager implements Observer {
  public enum ThreadCommand {
    WAKE_UP,
    SLEEP
  }
  
  // TODO read core pool size, max pool size and queue size from system configuration
  private final ThreadPoolExecutor workerPool;
  private Job job;
  private final JobContext ctx = new JobContext();
  private Set<Task> completedTasks = Collections.synchronizedSet(new HashSet<Task>());
  
  /**
   * The parameter thread pool is assigned to the manager.
   * 
   * @param pool
   */
  public JobManager(ThreadPoolExecutor pool) {
    workerPool = pool;
  }
  
  public boolean acceptJob(Job job) {
    // Check if the job graph is acyclic
    if (!job.isAcyclic()) return false;
    
    // TODO there may be other conditions for turning down the job
    if ((this.job != null && this.job.isRunning())
        || job == null || job.getStatus() != JobStatus.PENDING) return false;
    // TODO do we need to do something for job completed,
    // such as returning the status?

    this.job = job;
    prepare();
    return true;
  }
  
  public Job getJob() {
    return job;
  }
  
  public ThreadPoolExecutor getWorkerPool() {
    return workerPool;
  }
  
  public void startJob() {
    if (job == null || job.getStatus() != JobStatus.READY_TO_RUN) return;
    
    // clear completed task set for a fresh run
    completedTasks.clear();

    job.setStatus(JobStatus.RUNNING);
    Set<Task> starters = job.getRootNodes();
    for (Task t : starters) {
      workerPool.submit(t);
    }
  }
  
  private void prepare() {
    if (job == null || job.getStatus() != JobStatus.PENDING) return;

    // register this manager to the tasks in the job
    for (Task t : job.getNodes()) {
      t.addObserver(this);
    }
    
    job.setStatus(JobStatus.READY_TO_RUN);
  }

  public void update(Observable arg0, Object arg1) {
    if (Task.class.isInstance(arg0)) {
      Task t = Task.class.cast(arg0);
      if (t.getStatus() == TaskStatus.SUCCESSFUL) {
        // submit the task and return. do not take more time for update method.
        workerPool.submit(new TaskPostProcessor(t, this));
      }
    }
    else {
      // return when observable is not a task object
      return;
    }
    
  }
  
  public synchronized void callThread(ThreadCommand c) {
    if (c == ThreadCommand.SLEEP) {
      try {
        wait();
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    else if (c == ThreadCommand.WAKE_UP) {
      notifyAll();
    }
  }

  /**
   * @return the ctx
   */
  public JobContext getCtx() {
    return ctx;
  }
  
  class TaskPostProcessor implements Runnable {
    private Task task;
    private JobManager manager;
    
    TaskPostProcessor(Task t, JobManager manager) {
      task = t;
      this.manager = manager;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
      // add the task to complete list
      completedTasks.add(task);
      
      // get its successors
      Set<Task> successors = job.getSuccessors(task);
      
      if (successors.isEmpty()) {  // this is an end task, check for job completion.
        if (completedTasks.containsAll(manager.getJob().getLeafNodes())) {
          job.setStatus(JobStatus.COMPLETE_SUCCESSFUL);
          manager.callThread(ThreadCommand.WAKE_UP);
        }
      }
      else { // otherwise, check for depencies completion for each t
        for (Task t : successors) {
          if (completedTasks.containsAll(manager.getJob().getPredecessors(t))) {
            manager.workerPool.submit(t);
          }
        }
      }
    }
    
  }
}
