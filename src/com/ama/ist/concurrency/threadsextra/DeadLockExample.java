package com.ama.ist.concurrency.threadsextra;

import java.lang.management.ManagementFactory;
import java.util.stream.IntStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.ama.ist.jmx.Status;

public class DeadLockExample {

  // private static synchronized void transferMoney(Account a, Account b, int i)
  // {
  // try {
  // Thread.sleep(1000);
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  //
  // a.updateBalance(-i);
  // b.updateBalance(+i);
  // }

  // deadlock
//  private static void transferMoney(Account a, Account b, int i) {
//    synchronized (a) {
//      synchronized (b) {
//        a.updateBalance(-i);
//        b.updateBalance(+i);
//      }
//    }
//
//  }
  
  //deadlock solution
  private static void transferMoney(Account a, Account b, int i) {

    Account first = a.getId() > b.getId() ? a : b;
    Account second = a.getId() > b.getId() ? b : a;

    synchronized (first) {
      synchronized (second) {
        first.updateBalance(-i);
        second.updateBalance(+i);
        Status.tcount.incrementAndGet();
      }
    }

  }
  
  
  private static void registerJmx() {
    try {
      MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
      ObjectName name = new ObjectName("BigBankSystem:type=Mbeans, name=StatusMBean");
      mbeanServer.registerMBean(new Status(), name);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    registerJmx();
    Account a = new Account(100, 1);
    Account b = new Account(100, 2);

    Thread transferA = new Thread(() -> IntStream.range(1, 2000).forEach(i -> transferMoney(a, b, i)));
    Thread transferB = new Thread(() -> IntStream.range(1, 2000).forEach(i -> transferMoney(b, a, i)));

    transferA.start();
    transferB.start();
    transferA.join();
    transferB.join();

    System.out.println("Balance for Acount A: " + a.getBalance());
    System.out.println("Balance for Acount B: " + b.getBalance());

  }
}