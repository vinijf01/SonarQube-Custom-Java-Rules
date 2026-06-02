package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;
import java.util.Set;

@Rule(key = "hardcoded-credential")
public class HardCodeCredentialRule extends IssuableSubscriptionVisitor {

    private static final Set<String> CREDENTIAL_KEYWORDS = Set.of(
            "password", "passwd", "pwd",
            "secret", "token", "tkn",
            "apikey", "api_key",
            "accesskey", "access_key",
            "clientsecret", "client_secret",
            "privatekey", "private_key"
    );

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.VARIABLE, Tree.Kind.ASSIGNMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.VARIABLE)) {
            checkVariable((VariableTree) tree);
            return;
        }

        checkAssignment((AssignmentExpressionTree) tree);
    }

    private void checkVariable(VariableTree variableTree) {
        if (!isSensitiveName(variableTree.simpleName().name())) {
            return;
        }

        ExpressionTree initializer = variableTree.initializer();
        if (!isStringLiteral(initializer)) {
            return;
        }

        reportIssue(
                variableTree.simpleName(),
                "Hard-coded credential detected. Load secrets from configuration or a secret manager instead."
        );
    }

    private void checkAssignment(AssignmentExpressionTree assignmentTree) {
        if (!isStringLiteral(assignmentTree.expression())) {
            return;
        }

        String targetName = extractTargetName(assignmentTree.variable());
        if (targetName == null || !isSensitiveName(targetName)) {
            return;
        }

        reportIssue(
                assignmentTree.variable(),
                "Hard-coded credential detected. Load secrets from configuration or a secret manager instead."
        );
    }

    private boolean isStringLiteral(ExpressionTree expressionTree) {
        if (expressionTree == null) {
            return false;
        }

        return expressionTree.is(Tree.Kind.STRING_LITERAL) || expressionTree.is(Tree.Kind.TEXT_BLOCK);
    }

    private boolean isSensitiveName(String variableName) {
        String normalizedName = variableName.toLowerCase();
        return CREDENTIAL_KEYWORDS.stream().anyMatch(normalizedName::contains);
    }

    private String extractTargetName(ExpressionTree expressionTree) {
        if (expressionTree instanceof IdentifierTree identifierTree) {
            return identifierTree.name();
        }

        if (expressionTree instanceof MemberSelectExpressionTree memberSelectExpressionTree) {
            return memberSelectExpressionTree.identifier().name();
        }

        return null;
    }

}
