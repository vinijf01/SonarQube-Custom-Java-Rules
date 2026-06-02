package com.vini.sonarqube.rules;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class LoggedOnlyCatchBlockRuleTest {

    @Test
    void detectsCatchBlocksThatOnlyLog() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/LoggedOnlyCatchBlockRuleSample.java")
                .withCheck(new LoggedOnlyCatchBlockRule())
                .verifyIssues();
    }
}
