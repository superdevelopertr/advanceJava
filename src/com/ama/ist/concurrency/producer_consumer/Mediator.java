package com.ama.ist.concurrency.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mediator {

  public static final Queue<Integer> QUEUE = new LinkedList<>();

  public static final Lock LOCK = new ReentrantLock();

  public static void main(String[] args) throws InterruptedException {
    Producer producer = new Producer();
    Consumer consumer = new Consumer();
    
    for (int i = 0; i < 1; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          while (!Thread.currentThread().isInterrupted())
            producer.produce();
        }
      }, "producer"+i).start();
    }
    
    for (int i = 0; i < 10; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          while (!Thread.currentThread().isInterrupted())
            consumer.consume();
        }
      }, "consumer"+i).start();
    }
    
    
    Thread.sleep(Integer.MAX_VALUE);
  }
}