package com.vini.sonarqube.rules;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class NoSystemOutRuleTest {

    @Test
    void detectsSystemOutAndErrUsage() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/NoSystemOutRuleSample.java")
                .withCheck(new NoSystemOutRule())
                .verifyIssues();
    }
}
