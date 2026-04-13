package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class AssumeRoleWithWebIdentityRequestMarshaller implements Marshaller<Request<AssumeRoleWithWebIdentityRequest>, AssumeRoleWithWebIdentityRequest> {
    public Request<AssumeRoleWithWebIdentityRequest> marshall(AssumeRoleWithWebIdentityRequest assumeRoleWithWebIdentityRequest) {
        if (assumeRoleWithWebIdentityRequest != null) {
            Request<AssumeRoleWithWebIdentityRequest> request = new DefaultRequest<>(assumeRoleWithWebIdentityRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "AssumeRoleWithWebIdentity");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (assumeRoleWithWebIdentityRequest.getRoleArn() != null) {
                request.addParameter("RoleArn", StringUtils.fromString(assumeRoleWithWebIdentityRequest.getRoleArn()));
            }
            if (assumeRoleWithWebIdentityRequest.getRoleSessionName() != null) {
                request.addParameter("RoleSessionName", StringUtils.fromString(assumeRoleWithWebIdentityRequest.getRoleSessionName()));
            }
            if (assumeRoleWithWebIdentityRequest.getWebIdentityToken() != null) {
                request.addParameter("WebIdentityToken", StringUtils.fromString(assumeRoleWithWebIdentityRequest.getWebIdentityToken()));
            }
            if (assumeRoleWithWebIdentityRequest.getProviderId() != null) {
                request.addParameter("ProviderId", StringUtils.fromString(assumeRoleWithWebIdentityRequest.getProviderId()));
            }
            if (assumeRoleWithWebIdentityRequest.getPolicyArns() != null) {
                int policyArnsIndex = 1;
                String policyArnsPrefix = "PolicyArns";
                for (PolicyDescriptorType policyArnsItem : assumeRoleWithWebIdentityRequest.getPolicyArns()) {
                    String prefix = policyArnsPrefix + ".member." + policyArnsIndex;
                    if (policyArnsItem != null) {
                        PolicyDescriptorTypeStaxMarshaller.getInstance().marshall(policyArnsItem, request, prefix + ".");
                    }
                    policyArnsIndex++;
                }
            }
            if (assumeRoleWithWebIdentityRequest.getPolicy() != null) {
                request.addParameter("Policy", StringUtils.fromString(assumeRoleWithWebIdentityRequest.getPolicy()));
            }
            if (assumeRoleWithWebIdentityRequest.getDurationSeconds() != null) {
                request.addParameter("DurationSeconds", StringUtils.fromInteger(assumeRoleWithWebIdentityRequest.getDurationSeconds()));
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(AssumeRoleWithWebIdentityRequest)");
    }
}
