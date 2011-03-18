/**
 * Copyright (c), 2008 - 2011, PraxoTech Org. All rights reserved.
 */
package org.praxotech.util.job.management;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.praxotech.util.job.Job;
import org.praxotech.util.job.JobContext;
import org.praxotech.util.job.Task;
import org.praxotech.util.job.management.JobManager;
import org.praxotech.util.job.management.JobManager.ThreadCommand;


/**
 * @author fyang
 *
 */
public class JobManagerTest {
  class TestTask extends Task {
    private String name;
    private long duration;
    
    TestTask(String name, long d, JobContext ctx) {
      super(ctx);
      this.name = name;
      this.duration = d;
    }

    protected boolean executeTask() throws Throwable {
      long s = System.currentTimeMillis();
      while (System.currentTimeMillis() - s < duration) {
        String temp = name;
        temp.equals(name);
      }
      StringBuffer.class.cast(getCtx().get("Output")).append(name);
      return true;
    }
    
  }
  
  private ThreadPoolExecutor workerPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
  private JobManager manager;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    // create a manager
    manager = new JobManager(workerPool);

    // create a job
    Job job = new Job();
    
    // set job context
    manager.getCtx().put("Output", new StringBuffer());
    
    
    // create tasks
    Task t0 = new TestTask("0", 1000, manager.getCtx());
    Task t1 = new TestTask("1", 2000, manager.getCtx());
    Task t2 = new TestTask("2", 1500, manager.getCtx());
    Task t3 = new TestTask("3", 1800, manager.getCtx());
    Task t4 = new TestTask("4", 1650, manager.getCtx());
    Task t5 = new TestTask("5", 2380, manager.getCtx());
    Task t6 = new TestTask("6", 2630, manager.getCtx());
    Task t7 = new TestTask("7", 1900, manager.getCtx());
    Task t8 = new TestTask("8", 2100, manager.getCtx());
    Task t9 = new TestTask("9", 1300, manager.getCtx());

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
    job.addEdge(t4, t8);
    job.addEdge(t9, t2);
    job.addEdge(t9, t6);
    
    // submit job to manager
    manager.acceptJob(job);
  }

  /**
   * Test method for {@link org.praxotech.util.job.management.JobManager#startJob()}.
   */
  @Test
  public void testStartJob() {
    long s = System.currentTimeMillis();
    manager.startJob();
    manager.callThread(ThreadCommand.SLEEP);
    
    System.out.println("Time ellapsed: " + (System.currentTimeMillis() - s) + " milliseconds");
    StringBuffer output = StringBuffer.class.cast(manager.getCtx().get("Output"));
    
    // show the result on console
    System.out.println(output);
    
    // after waking up by completion of last task, check the result
    assertTrue(Pattern.matches("[0-9]*1[0-9]*5[0-9]*", output) &&
               Pattern.matches("[0-9]*1[0-9]*0[0-9]*", output) &&
               Pattern.matches("[0-9]*7[0-9]*3[0-9]*", output) &&
               Pattern.matches("[0-9]*7[0-9]*0[0-9]*", output) &&
               Pattern.matches("[0-9]*5[0-9]*4[0-9]*", output) &&
               Pattern.matches("[0-9]*5[0-9]*9[0-9]*", output) &&
               Pattern.matches("[0-9]*3[0-9]*4[0-9]*", output) &&
               Pattern.matches("[0-9]*0[0-9]*9[0-9]*", output) &&
               Pattern.matches("[0-9]*4[0-9]*2[0-9]*", output) &&
               Pattern.matches("[0-9]*4[0-9]*6[0-9]*", output) &&
               Pattern.matches("[0-9]*4[0-9]*8[0-9]*", output) &&
               Pattern.matches("[0-9]*9[0-9]*2[0-9]*", output) &&
               Pattern.matches("[0-9]*9[0-9]*6[0-9]*", output));
  }

  /**
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    workerPool.shutdown();
  }
}
