package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.Request;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.util.StringUtils;

public class PolicyDescriptorTypeStaxMarshaller {
    private static PolicyDescriptorTypeStaxMarshaller instance;

    PolicyDescriptorTypeStaxMarshaller() {
    }

    public void marshall(PolicyDescriptorType _policyDescriptorType, Request<?> request, String _prefix) {
        if (_policyDescriptorType.getArn() != null) {
            request.addParameter(_prefix + "arn", StringUtils.fromString(_policyDescriptorType.getArn()));
        }
    }

    public static PolicyDescriptorTypeStaxMarshaller getInstance() {
        if (instance == null) {
            instance = new PolicyDescriptorTypeStaxMarshaller();
        }
        return instance;
    }
}
