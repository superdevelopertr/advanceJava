package com.ama.ist.concurrency.thread_local;

import java.util.Random;

public class ThreadLocalRunner implements Runnable {

  private ThreadLocal<Integer> maxValue= new ThreadLocal<Integer>();
  
  private Random random = new Random();
  
  public ThreadLocalRunner() {
    maxValue.set(0);
  }
  
  @Override
  public void run() {
    while(!Thread.currentThread().isInterrupted()) {
      Integer current = random.nextInt();
      if(maxValue.get()==null || current>maxValue.get()) {
        maxValue.set(current);
        System.out.println("New max number found: "+current);

        try {
          Thread.sleep(223);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    ThreadLocalRunner localRunner = new ThreadLocalRunner();
    for (int i = 0; i < 10; i++) {
      new Thread(localRunner,"threadLocalEx").start();
    }
    
    Thread.sleep(Integer.MAX_VALUE);
  }
}