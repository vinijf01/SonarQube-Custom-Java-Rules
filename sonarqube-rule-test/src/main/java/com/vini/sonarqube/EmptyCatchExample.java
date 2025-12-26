package com.vini.sonarqube;

public class EmptyCatchExample {
    public void doSomething(){
        try {
            int x = 10 / 1;
        } catch (Exception e) {
            // nothing here
        }
    }
}
