package com.ama.ist.concurrency.exhandler;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadHandler {

  public static void main(String[] args) {
    
    Thread t = new Thread(()-> {});
    
    t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread arg0, Throwable arg1) {
        System.out.println("Thread is failed");
      }
    });
  }
}