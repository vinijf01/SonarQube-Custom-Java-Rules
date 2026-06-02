package com.vini.sonarqube;

public class SystemOutExample {
    public void process() {
        System.out.println("Processing started");
        System.err.println("Something went wrong");
        System.out.printf("Current step: %s%n", "validation");
    }
}
