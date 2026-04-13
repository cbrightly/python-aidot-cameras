package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithSAMLRequest;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class AssumeRoleWithSAMLRequestMarshaller implements Marshaller<Request<AssumeRoleWithSAMLRequest>, AssumeRoleWithSAMLRequest> {
    public Request<AssumeRoleWithSAMLRequest> marshall(AssumeRoleWithSAMLRequest assumeRoleWithSAMLRequest) {
        if (assumeRoleWithSAMLRequest != null) {
            Request<AssumeRoleWithSAMLRequest> request = new DefaultRequest<>(assumeRoleWithSAMLRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "AssumeRoleWithSAML");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (assumeRoleWithSAMLRequest.getRoleArn() != null) {
                request.addParameter("RoleArn", StringUtils.fromString(assumeRoleWithSAMLRequest.getRoleArn()));
            }
            if (assumeRoleWithSAMLRequest.getPrincipalArn() != null) {
                request.addParameter("PrincipalArn", StringUtils.fromString(assumeRoleWithSAMLRequest.getPrincipalArn()));
            }
            if (assumeRoleWithSAMLRequest.getSAMLAssertion() != null) {
                request.addParameter("SAMLAssertion", StringUtils.fromString(assumeRoleWithSAMLRequest.getSAMLAssertion()));
            }
            if (assumeRoleWithSAMLRequest.getPolicyArns() != null) {
                int policyArnsIndex = 1;
                String policyArnsPrefix = "PolicyArns";
                for (PolicyDescriptorType policyArnsItem : assumeRoleWithSAMLRequest.getPolicyArns()) {
                    String prefix = policyArnsPrefix + ".member." + policyArnsIndex;
                    if (policyArnsItem != null) {
                        PolicyDescriptorTypeStaxMarshaller.getInstance().marshall(policyArnsItem, request, prefix + ".");
                    }
                    policyArnsIndex++;
                }
            }
            if (assumeRoleWithSAMLRequest.getPolicy() != null) {
                request.addParameter("Policy", StringUtils.fromString(assumeRoleWithSAMLRequest.getPolicy()));
            }
            if (assumeRoleWithSAMLRequest.getDurationSeconds() != null) {
                request.addParameter("DurationSeconds", StringUtils.fromInteger(assumeRoleWithSAMLRequest.getDurationSeconds()));
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(AssumeRoleWithSAMLRequest)");
    }
}
