package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.AssumeRoleWithSAMLResult;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class AssumeRoleWithSAMLResultStaxUnmarshaller implements Unmarshaller<AssumeRoleWithSAMLResult, StaxUnmarshallerContext> {
    private static AssumeRoleWithSAMLResultStaxUnmarshaller instance;

    public AssumeRoleWithSAMLResult unmarshall(StaxUnmarshallerContext context) {
        AssumeRoleWithSAMLResult assumeRoleWithSAMLResult = new AssumeRoleWithSAMLResult();
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
                } else if (context.testExpression("Credentials", targetDepth)) {
                    assumeRoleWithSAMLResult.setCredentials(CredentialsStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("AssumedRoleUser", targetDepth)) {
                    assumeRoleWithSAMLResult.setAssumedRoleUser(AssumedRoleUserStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("PackedPolicySize", targetDepth)) {
                    assumeRoleWithSAMLResult.setPackedPolicySize(SimpleTypeStaxUnmarshallers.IntegerStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Subject", targetDepth)) {
                    assumeRoleWithSAMLResult.setSubject(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("SubjectType", targetDepth)) {
                    assumeRoleWithSAMLResult.setSubjectType(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Issuer", targetDepth)) {
                    assumeRoleWithSAMLResult.setIssuer(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Audience", targetDepth)) {
                    assumeRoleWithSAMLResult.setAudience(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("NameQualifier", targetDepth)) {
                    assumeRoleWithSAMLResult.setNameQualifier(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return assumeRoleWithSAMLResult;
    }

    public static AssumeRoleWithSAMLResultStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new AssumeRoleWithSAMLResultStaxUnmarshaller();
        }
        return instance;
    }
}
