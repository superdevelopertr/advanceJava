package com.ama.ist.concurrency;

class Waiter implements Runnable {

  volatile boolean shouldFinish;

  public void finish() {
    shouldFinish = true;
  }
  
  @Override
  public void run() {
    long iteration = 0;
    while (!shouldFinish) {
      iteration++;
    }
    System.out.println("finished at it " + iteration);
  }

}

public class VisibilityProblem {

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 1000; i++) {
      Waiter waiter = new Waiter();
      Thread waiterThread = new Thread(waiter);
      
      waiterThread.start();
      
      Thread.sleep(500);
      
      waiter.finish();
      waiterThread.join();
    }
  }
}
