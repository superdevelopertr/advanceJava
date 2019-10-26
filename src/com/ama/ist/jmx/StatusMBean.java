package com.ama.ist.jmx;

public interface StatusMBean {

  int getTrancastionCount();
  
  void wiredTransfer(Integer from, Integer to, Integer amount);
  
  int retrieveBalance(Integer account);
}