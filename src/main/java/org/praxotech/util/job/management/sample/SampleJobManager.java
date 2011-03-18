/**
 * Copyright (c), 2008 - 2011, PraxoTech Org.
 */
package org.praxotech.util.job.management.sample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.praxotech.util.job.Job;
import org.praxotech.util.job.JobContext;
import org.praxotech.util.job.PassThruTask;
import org.praxotech.util.job.Task;
import org.praxotech.util.job.management.JobManager;
import org.praxotech.util.job.management.JobManager.ThreadCommand;

/**
 * @author fyang
 *
 */
public class SampleJobManager {
  /**
   * @param args
   */
  public static void main(String[] args) {
    ThreadPoolExecutor workerPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
    JobManager manager = new JobManager(workerPool);
    // create a job
    Job job = new Job();
    JobContext ctx = manager.getCtx();
    
    // create tasks
    Task t0 = new SampleTask("0", 1000, ctx);
    Task t1 = new SampleTask("1", 2000, ctx);
    Task t2 = new SampleTask("2", 1500, ctx);
    Task t3 = new SampleTask("3", 1800, ctx);
    Task t4 = new SampleTask("4", 1650, ctx);
    Task t5 = new SampleTask("5", 2380, ctx);
    Task t6 = new SampleDynamicTaskGeneratorTask("6", 1300, ctx);
    Task t7 = new SampleTask("7", 2100, ctx);
    Task t8 = new PassThruTask(ctx);
    Task t9 = new SampleTask("9", 2100, ctx);
    
    // set context
    ctx.put("NumberOfCopies", 8);
    ctx.put("dependency", t8);
    ctx.put("JobManager", manager);
    ctx.put("Output", new StringBuffer());

    // add tasks to the job
    job.addNode(t0);
    job.addNode(t1);
    job.addNode(t2);
    job.addNode(t3);
    job.addNode(t4);
    job.addNode(t5);
    job.addNode(t6);
    job.addNode(t7);
    job.addNode(t8);
    job.addNode(t9);
    
    // create task dependencies
    job.addEdge(t1, t5);
    job.addEdge(t1, t0);
    job.addEdge(t7, t3);
    job.addEdge(t7, t0);
    job.addEdge(t5, t4);
    job.addEdge(t5, t9);
    job.addEdge(t3, t4);
    job.addEdge(t0, t9);
    job.addEdge(t4, t2);
    job.addEdge(t4, t6);
    job.addEdge(t9, t2);
    job.addEdge(t9, t8); // this is very important.  t9 is guarded by t8
    job.addEdge(t6, t8); // this is very important.  t6 is guarded by t8
    
    long s = System.currentTimeMillis();
    // submit job to manager
    manager.acceptJob(job);
    
    manager.startJob();
    manager.callThread(ThreadCommand.SLEEP);
    workerPool.shutdown();
    System.out.println("Time ellapsed: " + (System.currentTimeMillis() - s) + " milliseconds");
    // show the result on console
    System.out.println(StringBuffer.class.cast(manager.getCtx().get("Output")));
  }

}

class SampleTask extends Task {
  private String name;
  private long duration;
  
  SampleTask(String name, long d, JobContext ctx) {
    super(ctx);
    this.name = name;
    this.duration = d;
  }

  protected boolean executeTask() {
    long s = System.currentTimeMillis();
    StringBuffer output = StringBuffer.class.cast(getCtx().get("Output"));

    while (System.currentTimeMillis() - s < duration) {
      String temp = name;
      temp.equals(name);
    }
    output.append("["+name+"]");
//    setStatus(TaskStatus.SUCCESSFUL);
    // trigger the notification for its observers
    setChanged();
    notifyObservers();
    return true;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the duration
   */
  public long getDuration() {
    return duration;
  }
  
}

class SampleDynamicTaskGeneratorTask extends SampleTask {

  public SampleDynamicTaskGeneratorTask(String name, long d, JobContext ctx) {
    super(name, d, ctx);
  }

  @Override
  protected boolean executeTask() {
    JobContext ctx = getCtx();
    int n = Integer.class.cast(ctx.get("NumberOfCopies"));
    Task dep = Task.class.cast(ctx.get("dependency"));
    JobManager mgr = JobManager.class.cast(ctx.get("JobManager"));
    
    for (int i = 0; i < n; i++) {
      SampleTask dt = new SampleTask(getName() + ":" + (i+1), 1000, ctx);
      // add the task
      Job job = mgr.getJob(); 
      job.addNode(dt);
      // add dependencies
      job.addEdge(dep, dt);
      // register mgr as an observer to dt
      dt.addObserver(mgr);
    }
    
    return super.executeTask();
  }
  
}
