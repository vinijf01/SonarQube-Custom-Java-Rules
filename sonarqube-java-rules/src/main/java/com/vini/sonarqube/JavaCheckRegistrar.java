package com.vini.sonarqube;

import com.vini.sonarqube.rules.*;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Arrays;
import java.util.List;

public class JavaCheckRegistrar implements CheckRegistrar {
    @Override
    public void register(RegistrarContext registrarContext) {
        registrarContext.registerClassesForRepository(
                JavaRulesDefinition.REPOSITORY_KEY,
                checkClasses(),
                testCheckClasses()
        );
    }

    private static Iterable<Class<? extends JavaCheck>> checkClasses() {
        return Arrays.asList(
                DetectClassExtendThread.class,
                EmptyCatchBlockRule.class,
                HardCodeCredentialRule.class,
                LongMethodRule.class,
                NoSystemOutRule.class
        );
    }

    private static Iterable<Class<? extends JavaCheck>> testCheckClasses() {
        return List.of();
    }
}
