package com.ama.ist.concurrency.producer_consumer;

public class Consumer {

  public void consume() {
    try {
      Mediator.LOCK.lock();
      if (Mediator.QUEUE.size() > 0) {
        Mediator.QUEUE.poll();
        Mediator.LOCK.notifyAll();
      } else {

        try {
          Mediator.LOCK.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }finally {
      Mediator.LOCK.unlock();
    }

  }

}