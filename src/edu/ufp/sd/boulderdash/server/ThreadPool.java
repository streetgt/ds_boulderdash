package edu.ufp.sd.boulderdash.server;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * When task creation frequency is high and mean task duration is low
 * use thread pool instead of creating a new thread for each request.
 * 
 * The thread pool size depends on the number of available processors (N)
 * and the nature of the tasks.
 * i) For compute-bounded tasks:
 *      => Thread pool size = N or N+1 threads.
 * ii) For tasks that wait for I/O to complete:
 *      => Thread pool size = N*(1+WT/ST).
 * Use profiling to determine WT (mean waiting time) and ST (mean service time).
 */
public class ThreadPool {

    /** Size of the array/pool of threads */
    private final int poolsize;
    //
    /** Array of threads that compete for running Runnable Sessions */
    private final PoolThread[] poolAvailableThreads;
    /** List of Runnable Sessions that will be picked (one-by-one) in FIFO 
     * manner by the existing threads */
    private final LinkedList<Runnable> listRunnableThreads;

    /**
     * Constructor of the pool of threads.
     * @param nt - number of Threads
     */
    public ThreadPool(int nt) {
        this.poolsize = nt;
        listRunnableThreads = new LinkedList();
        poolAvailableThreads = new PoolThread[this.poolsize];

        this.startThreadPoll();
    }
    
    /**
     * Creates and starts the pool of threads.
     */
    private void startThreadPoll(){
        for (int i = 0; i < this.poolsize; i++) {
            poolAvailableThreads[i] = new PoolThread();
            poolAvailableThreads[i].start();
        }
    }

    /**
     * Adds a Runnable Session to the end of the list and notifies/awakes 
     * one of the threads waiting to run.
     * @param r - Runnable instance to be executed inside a thread
     */
    public void execute(Runnable r) {
        synchronized (listRunnableThreads) {
            listRunnableThreads.addLast(r);
            listRunnableThreads.notify();
        }
    }

    /**
     * Each instance of PoolThread waits on the list of Runnable Sessions to 
     * pick a Runnable and run it.
     */
    private class PoolThread extends Thread {

        /**
         * Waits on the monitor of the list of Runnable Sessions until it is
         * able to pick a Runnable and execute/run it. 
         */
        public void run() {
            Runnable r;
            while (true) {
                //Wait on the monitor associated with the list of runnables
                synchronized (listRunnableThreads) {
                    while (listRunnableThreads.isEmpty()) {
                        try {
                            listRunnableThreads.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    r = (Runnable) listRunnableThreads.removeFirst();
                }

                // Catch RuntimeException to avoid pool leaks
                try {
                    r.run();
                } catch (RuntimeException e) {
                    Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }
}
