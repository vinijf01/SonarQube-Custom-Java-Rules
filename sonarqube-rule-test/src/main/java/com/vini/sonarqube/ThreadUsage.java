package com.vini.sonarqube;

import  java.lang.Thread;

public class ThreadUsage extends Thread{

    @Override
    public void run(){
        System.out.println("Hello");
    }
}
