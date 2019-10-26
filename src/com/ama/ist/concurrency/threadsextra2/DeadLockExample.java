package com.ama.ist.concurrency.threadsextra2;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.ama.ist.jmx.Status;

public class DeadLockExample {

  public static Map<Integer,Account> accounts = new HashMap<>();
  
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
  public static void transferMoney(Integer from, Integer to, int i) {

    Account first = accounts.get(Integer.max(from, to));
    Account second =accounts.get(Integer.min(from, to));

   
    synchronized (first) {
      synchronized (second) {
        first.updateBalance(-i);
        second.updateBalance(+i);
        
        System.out.println("Balance from "+first.getBalance());
        System.out.println("Balance to "+second.getBalance());
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

    accounts.put(1,new Account(100, 1));
    accounts.put(2,new Account(100, 2));
    accounts.put(3,new Account(100, 3));
    accounts.put(4,new Account(100, 4));
    accounts.put(5,new Account(100, 5));
    
    Thread transferA = new Thread(() -> IntStream.range(1, 2000).forEach(i -> transferMoney(1, 2, i)));
    Thread transferB = new Thread(() -> IntStream.range(1, 2000).forEach(i -> transferMoney(2, 1, i)));

    transferA.start();
    transferB.start();
    transferA.join();
    transferB.join();

    System.out.println("Balance for Acount A: " + a.getBalance());
    System.out.println("Balance for Acount B: " + b.getBalance());

  }
}