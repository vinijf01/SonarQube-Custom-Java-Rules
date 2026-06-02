package com.vini.sonarqube;

import com.vini.sonarqube.rules.EmptyCatchBlockRule;
import com.vini.sonarqube.rules.GenericCatchExceptionRule;
import com.vini.sonarqube.rules.HardCodeCredentialRule;
import com.vini.sonarqube.rules.LoggedOnlyCatchBlockRule;
import com.vini.sonarqube.rules.NoSystemOutRule;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

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
        return List.of(
                EmptyCatchBlockRule.class,
                GenericCatchExceptionRule.class,
                HardCodeCredentialRule.class,
                LoggedOnlyCatchBlockRule.class,
                NoSystemOutRule.class
        );
    }

    private static Iterable<Class<? extends JavaCheck>> testCheckClasses() {
        return List.of();
    }
}
