package com.vini.sonarqube;

import com.vini.sonarqube.rules.AlwaysRaiseIssueRule;
import com.vini.sonarqube.rules.DetectClassExtendThread;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Arrays;
import java.util.List;

public class JavaCheckRegistrar implements CheckRegistrar {

    @Override
    public void register(RegistrarContext context) {
        context.registerClassesForRepository(
                JavaRulesDefinition.REPOSITORY_KEY,
                checkClasses(),
                testCheckClasses()
        );
    }

    private static Iterable<Class<? extends JavaCheck>> checkClasses() {
        return Arrays.asList(
                AlwaysRaiseIssueRule.class,
                DetectClassExtendThread.class
        );
    }

    private static Iterable<Class<? extends JavaCheck>> testCheckClasses() {
        return List.of();
    }
}
