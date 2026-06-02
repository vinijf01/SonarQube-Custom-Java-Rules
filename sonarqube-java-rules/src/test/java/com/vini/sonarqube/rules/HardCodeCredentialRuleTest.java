package com.vini.sonarqube.rules;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class HardCodeCredentialRuleTest {

    @Test
    void detectsHardcodedCredentials() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/HardCodeCredentialRuleSample.java")
                .withCheck(new HardCodeCredentialRule())
                .verifyIssues();
    }
}
