package com.ama.ist.jmx;

import java.util.concurrent.atomic.AtomicInteger;

import com.ama.ist.concurrency.threadsextra2.DeadLockExample;

public class Status implements StatusMBean{

  public static final AtomicInteger tcount = new AtomicInteger();
  
  @Override
  public int getTrancastionCount() {
    return tcount.get();
  }
  
  
  public void wiredTransfer(Integer from, Integer to, Integer amount) {
    DeadLockExample.transferMoney(from, to, amount);
  }
  
  public int retrieveBalance(Integer account) {
    return DeadLockExample.accounts.get(account).getBalance();
  }
}