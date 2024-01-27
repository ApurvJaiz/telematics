package com.example.telematics.rule;

import com.example.telematics.model.TelematicsData;

public interface MonitoringRule {

    // Method to check if the rule is triggered based on telematics data
    boolean isRuleTriggered(TelematicsData telematicsData);

    // Method to execute the action associated with the rule
    void executeAction(TelematicsData telematicsData);
}
