package com.ama.ist.concurrency.threadsextra;

public class Account {
  
  private int id;
  private int balance;

  public Account(int balance,int id) {
    this.balance = balance;
    this.id = id;
  }
  
  public int getBalance() {
    return balance;
  }
  
  //atomicity problem
//  public synchronized void updateBalance(int amount) {
//    try {
//      Thread.sleep(100);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    balance+=amount;
//  }
  
  public  void updateBalance(int amount) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    balance+=amount;
  }
  
  public int getId() {
    return id;
  }
}