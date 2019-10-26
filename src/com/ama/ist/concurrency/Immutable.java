package com.ama.ist.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Immutable {

  private final String name;
  private final Integer age;
  private List<Integer> courses = new ArrayList<>();

  public Immutable(String name, Integer age, List<Integer> courses) {
    this.name = name;
    this.age = age;
    this.courses = courses;
  }
  
  public static void test(int i) {
    synchronized (Immutable.class) {
      System.out.println(Thread.currentThread().getName()+" accessed");
      i--;
      if(i==0) {
        return;
      }
      test(i);
    }
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }
  
  public Immutable addCourse(Integer courseCode) {
    List<Integer> newCourses = new ArrayList<Integer>();
    Collections.copy(newCourses, courses);
    
    return new Immutable(name, courseCode, newCourses);
  }

  public List<Integer> getCourses() {
    return Collections.unmodifiableList(courses);
  }
  
  public static void main(String[] args) throws InterruptedException {
//    List<Integer> courses = new ArrayList<Integer>();
//    courses.add(1);
//    courses.add(2);
//    Immutable im = new Immutable("Abdullah", 20, courses);

    Thread t1 = new Thread(()->test(5),"t1");
    Thread t2 = new Thread(()->test(5),"t2");
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    
  }
}