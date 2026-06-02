package com.vini.sonarqube.rules;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

class GenericCatchExceptionRuleTest {

    @Test
    void detectsGenericCatchTypes() {
        CheckVerifier.newVerifier()
                .onFile("src/test/files/GenericCatchExceptionRuleSample.java")
                .withCheck(new GenericCatchExceptionRule())
                .verifyIssues();
    }
}
