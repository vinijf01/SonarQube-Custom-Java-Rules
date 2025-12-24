package com.vini.sonarqube.rules;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.util.Collections;
import java.util.List;

@Rule(key = "extend-thread")
public class DetectClassExtendThread extends IssuableSubscriptionVisitor {
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        System.out.println("VISIT: " + classTree.simpleName());
        if (classTree.superClass() == null) {
            return;
        }

        String superClass = classTree.superClass().toString();

        if ("Thread".equals(superClass) || "java.lang.Thread".equals(superClass)) {
            reportIssue(
                    classTree.simpleName(),
                    "Avoid extending Thread directly. Prefer implementing Runnable."
            );
        }
    }
}
