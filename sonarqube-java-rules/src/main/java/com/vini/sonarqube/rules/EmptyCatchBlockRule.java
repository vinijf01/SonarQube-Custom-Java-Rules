package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

@Rule(key = "empty-catch-block")
public class EmptyCatchBlockRule extends IssuableSubscriptionVisitor {
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.CATCH);
    }

    @Override
    public void visitNode(Tree tree) {
        CatchTree catchTree = (CatchTree) tree;

        if (catchTree.block().body().isEmpty()) {
            reportIssue(
                    catchTree.catchKeyword(),
                    "Empty catch blocks silently discard exceptions. Handle, rethrow, or document why ignoring is safe."
            );
        }
    }

}
