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

        // Rule : EmptyCatchBlockRule
        repo.createRule("empty-catch-block")
                .setName("Empty Catch Block")
                .setHtmlDescription(
                        "This rule detects empty <code>catch</code> blocks, including blocks that only contain comments. " +
                                "Ignoring exceptions can hide errors and make debugging difficult."
                )
                .setSeverity(Severity.MAJOR)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.BUG)
                .setTags("bug", "error-handling");

        // Rule : GenericCatchExceptionRule
        repo.createRule("generic-catch-exception")
                .setName("Generic exceptions should not be caught")
                .setHtmlDescription(
                        "This rule reports <code>catch</code> blocks that handle generic exception types such as " +
                                "<code>Exception</code>, <code>RuntimeException</code>, or <code>Throwable</code>. " +
                                "Prefer narrower exception types so error handling stays intentional and predictable."
                )
                .setSeverity(Severity.MAJOR)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.CODE_SMELL)
                .setTags("error-handling", "design");

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
                .setType(RuleType.VULNERABILITY)
                .setTags("security", "credential");

        // Rule : LoggedOnlyCatchBlockRule
        repo.createRule("logged-only-catch")
                .setName("Catch blocks should not only log and continue")
                .setHtmlDescription(
                        "This rule detects <code>catch</code> blocks that only log an exception or call " +
                                "<code>printStackTrace()</code> without recovery or rethrowing. " +
                                "That pattern can hide failures and leave the application in an inconsistent state."
                )
                .setSeverity(Severity.MAJOR)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.BUG)
                .setTags("error-handling", "logging");

        // Rule : NoSystemOutRule
        repo.createRule("no-system-out")
                .setName("System.out and System.err should not be used")
                .setHtmlDescription(
                        "<p>Using <code>System.out</code> or <code>System.err</code> " +
                                "for logging is not recommended in production environments, including <code>print</code>, <code>println</code>, <code>printf</code>, and <code>format</code>.</p>" +
                                "<p>Use a proper logging framework such as SLF4J or Log4j " +
                                "to enable log level management and centralized logging.</p>"
                )
                .setSeverity(Severity.MINOR)
                .setStatus(RuleStatus.READY)
                .setType(RuleType.CODE_SMELL)
                .setTags("bad-practice", "logging");

        repo.done();
    }
}
