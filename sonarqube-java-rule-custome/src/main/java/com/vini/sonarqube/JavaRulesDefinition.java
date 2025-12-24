package com.vini.sonarqube;

import org.sonar.api.rule.Severity;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;

public class JavaRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "custom-java-rules";

    @Override
    public void define(Context context) {
        NewRepository repo = context
                .createRepository(REPOSITORY_KEY, "java")
                .setName("Custom Java Rules");

        // Rule 1 - Always issue (test)
        repo.createRule("always-issue")
                .setName("Always raise issue (test)")
                .setHtmlDescription("This rule always raises an issue for testing.");

        // Rule 2 - extend Thread
        repo.createRule("extend-thread")
                .setName("Avoid extending Thread")
                .setHtmlDescription(
                        "Extending <code>Thread</code> directly is discouraged. " +
                                "Prefer implementing <code>Runnable</code> instead."
                )
                .setSeverity(Severity.MAJOR)
                .setType(RuleType.CODE_SMELL);

        repo.done();
    }
}
