package com.vini.sonarqube;

import org.sonar.api.rule.RuleStatus;
import org.sonar.api.rule.Severity;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;

public class JavaRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "custom-java-rules";
    public static final String REPOSITORY_NAME = "Custom Java Rules";

    @Override
    public void define(Context context) {
        NewRepository repo = context
                .createRepository(REPOSITORY_KEY, "java")
                .setName(REPOSITORY_NAME);

        // Rule : DetectClassExtendThread
        repo.createRule("extend-thread")
                .setName("Detect extending Thread")
                .setHtmlDescription(
                        "This rule reports classes that extend <code>Thread</code>. " +
                                "In many projects, concurrency is managed using <code>Runnable</code> " +
                                "or executor-based frameworks. This rule helps teams identify " +
                                "direct <code>Thread</code> usage for review."
                )
                .setSeverity(Severity.INFO)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.CODE_SMELL);

        // Rule : EmptyCatchBlockRule
        repo.createRule("empty-catch-block")
                .setName("Empty Catch Block")
                .setHtmlDescription(
                        "This rule detects empty <code>catch</code> blocks. " + "Ignoring exceptions can hide errors and make debugging difficult."
                )
                .setSeverity(Severity.MAJOR)
                .setStatus(RuleStatus.READY)
                .setTags("bug", "error-handling");

        // Rule : HardCodeCredentialRule
        repo.createRule("hardcoded-credential")
                .setName("Hard-coded credentials should not be used")
                .setHtmlDescription
                        (
                                "<p>Hard-coded credentials such as passwords, tokens, or API keys " +
                                        "pose a security risk and should not be embedded directly in source code.</p>" +
                                        "<p>Use environment variables, configuration files, or secret managers instead.</p>"
                        )
                .setSeverity(Severity.CRITICAL)
                .setStatus(RuleStatus.READY)
                .setTags("security", "credential");

        // Rule : LongMethodRule
        repo.createRule("long-method")
                .setName("Method should not be too long")
                .setHtmlDescription(
                                "<p>Long methods are difficult to read, understand, and maintain.</p>" +
                                        "<p>Consider refactoring the method by extracting smaller methods " +
                                        "to improve readability and maintainability.</p>"
                        )
                .setSeverity(Severity.MAJOR)
                .setStatus(RuleStatus.READY)
                .setTags("code-smell", "maintainability");

        // Rule : NoSystemOutRule
        repo.createRule("no-system-out")
                .setName("System.out and System.err should not be used")
                .setHtmlDescription(
                        "<p>Using <code>System.out</code> or <code>System.err</code> " +
                                "for logging is not recommended in production environments.</p>" +
                                "<p>Use a proper logging framework such as SLF4J or Log4j " +
                                "to enable log level management and centralized logging.</p>"
                )
                .setSeverity(Severity.MINOR)
                .setStatus(RuleStatus.READY)
                .setTags("bad-practice", "logging");

        repo.done();
    }
}
