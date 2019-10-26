package com.ama.ist.concurrency.producer_consumer;

import java.util.Random;

public class Producer {

  public void produce() {
    try {
      Mediator.LOCK.lock();

      Random rm = new Random();
      if (Mediator.QUEUE.size() < 10) {
        Mediator.QUEUE.add(rm.nextInt(10));
        Mediator.LOCK.notifyAll();
      } else {
        try {
          Mediator.LOCK.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } finally {
      Mediator.LOCK.unlock();
    }

    if (Mediator.QUEUE.size() > 10) {
      System.out.println(Mediator.QUEUE.size());
    }
  }
}