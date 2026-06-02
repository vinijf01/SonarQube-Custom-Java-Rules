package com.vini.sonarqube;

public class CredentialExample {
    private String password = "admin123";
    private String apiKey;

    public CredentialExample() {
        this.apiKey = "ABC-SECRET-KEY";
    }

    public String currentApiKey() {
        return apiKey;
    }
}
