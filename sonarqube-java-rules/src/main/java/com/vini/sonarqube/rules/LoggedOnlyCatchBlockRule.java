package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.Set;

@Rule(key = "logged-only-catch")
public class LoggedOnlyCatchBlockRule extends IssuableSubscriptionVisitor {

    private static final Set<String> LOGGER_METHODS = Set.of(
            "trace",
            "debug",
            "info",
            "warn",
            "error",
            "fatal",
            "log"
    );

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.CATCH);
    }

    @Override
    public void visitNode(Tree tree) {
        CatchTree catchTree = (CatchTree) tree;
        List<StatementTree> statements = catchTree.block().body();

        if (statements.size() != 1) {
            return;
        }

        StatementTree statement = statements.get(0);
        if (!(statement instanceof ExpressionStatementTree expressionStatement)) {
            return;
        }

        ExpressionTree expression = expressionStatement.expression();
        if (!(expression instanceof MethodInvocationTree methodInvocation)) {
            return;
        }

        if (isPrintStackTrace(methodInvocation) || isLoggerOnlyCall(methodInvocation)) {
            reportIssue(
                    catchTree.catchKeyword(),
                    "Logging an exception without recovery or rethrowing can hide failures."
            );
        }
    }

    private static boolean isPrintStackTrace(MethodInvocationTree methodInvocation) {
        if (!(methodInvocation.methodSelect() instanceof MemberSelectExpressionTree methodSelect)) {
            return false;
        }

        return "printStackTrace".equals(methodSelect.identifier().name());
    }

    private static boolean isLoggerOnlyCall(MethodInvocationTree methodInvocation) {
        if (!(methodInvocation.methodSelect() instanceof MemberSelectExpressionTree methodSelect)) {
            return false;
        }

        String methodName = methodSelect.identifier().name();
        if (!LOGGER_METHODS.contains(methodName)) {
            return false;
        }

        String ownerText = methodSelect.expression().toString().toLowerCase();
        return ownerText.contains("log");
    }
}
