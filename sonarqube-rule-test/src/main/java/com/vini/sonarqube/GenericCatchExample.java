package com.vini.sonarqube;

import java.io.IOException;

public class GenericCatchExample {
    public String load() {
        try {
            throw new IOException("Cannot load file");
        } catch (Exception e) {
            return "Fallback result: " + e.getMessage();
        }
    }
}
