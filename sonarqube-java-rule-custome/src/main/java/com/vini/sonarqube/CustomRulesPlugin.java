package com.vini.sonarqube;

import com.vini.sonarqube.rules.AlwaysRaiseIssueRule;
import com.vini.sonarqube.rules.DetectClassExtendThread;
import org.sonar.api.Plugin;

public class CustomRulesPlugin implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtension(JavaRulesDefinition.class);
        context.addExtension(JavaCheckRegistrar.class);
    }
}
