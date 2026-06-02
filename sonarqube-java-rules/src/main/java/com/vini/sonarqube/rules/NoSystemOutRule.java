package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.Set;

@Rule(key = "no-system-out")
public class NoSystemOutRule extends IssuableSubscriptionVisitor {

    private static final Set<String> PRINT_METHODS = Set.of("print", "println", "printf", "format");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;

        ExpressionTree select = methodInvocationTree.methodSelect();

        if (!(select instanceof MemberSelectExpressionTree)) {
            return;
        }

        MemberSelectExpressionTree memberSelect = (MemberSelectExpressionTree) select;

        String methodName = memberSelect.identifier().name();

        if (!PRINT_METHODS.contains(methodName)) {
            return;
        }

        ExpressionTree expression = memberSelect.expression();

        if (!(expression instanceof MemberSelectExpressionTree owner)) {
            return;
        }

        boolean isSystemStream =
                "System".equals(owner.expression().toString()) &&
                        ("out".equals(owner.identifier().name()) || "err".equals(owner.identifier().name()));

        if (isSystemStream) {
            reportIssue(
                    memberSelect,
                    "Avoid using System.out/System.err for application logging. Use a logger instead."
            );
        }
    }

}
