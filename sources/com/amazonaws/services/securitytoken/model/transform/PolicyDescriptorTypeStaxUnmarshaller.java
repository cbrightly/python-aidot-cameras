package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class PolicyDescriptorTypeStaxUnmarshaller implements Unmarshaller<PolicyDescriptorType, StaxUnmarshallerContext> {
    private static PolicyDescriptorTypeStaxUnmarshaller instance;

    PolicyDescriptorTypeStaxUnmarshaller() {
    }

    public PolicyDescriptorType unmarshall(StaxUnmarshallerContext context) {
        PolicyDescriptorType policyDescriptorType = new PolicyDescriptorType();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;
        if (context.isStartOfDocument()) {
            targetDepth += 2;
        }
        while (true) {
            int xmlEvent = context.nextEvent();
            if (xmlEvent != 1) {
                if (xmlEvent != 2) {
                    if (xmlEvent == 3 && context.getCurrentDepth() < originalDepth) {
                        break;
                    }
                } else if (context.testExpression("arn", targetDepth)) {
                    policyDescriptorType.setArn(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return policyDescriptorType;
    }

    public static PolicyDescriptorTypeStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PolicyDescriptorTypeStaxUnmarshaller();
        }
        return instance;
    }
}
