package com.vini.sonarqube.rules;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class EmptyCatchBlockRuleTest {

    @Test
    void detectsEmptyCatchBlocks() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/EmptyCatchBlockRuleSample.java")
                .withCheck(new EmptyCatchBlockRule())
                .verifyIssues();
    }
}
