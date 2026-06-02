package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.Set;

@Rule(key = "generic-catch-exception")
public class GenericCatchExceptionRule extends IssuableSubscriptionVisitor {

    private static final Set<String> GENERIC_EXCEPTION_TYPES = Set.of(
            "Exception",
            "RuntimeException",
            "Throwable",
            "java.lang.Exception",
            "java.lang.RuntimeException",
            "java.lang.Throwable"
    );

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.CATCH);
    }

    @Override
    public void visitNode(Tree tree) {
        CatchTree catchTree = (CatchTree) tree;
        String caughtType = catchTree.parameter().type().toString();

        for (String typeName : caughtType.split("\\|")) {
            if (GENERIC_EXCEPTION_TYPES.contains(typeName.trim())) {
                reportIssue(
                        catchTree.parameter().type(),
                        "Catch a more specific exception type so failures are handled intentionally."
                );
                return;
            }
        }
    }
}
