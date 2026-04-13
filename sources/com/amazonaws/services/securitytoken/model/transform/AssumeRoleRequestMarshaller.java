package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.services.securitytoken.model.Tag;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class AssumeRoleRequestMarshaller implements Marshaller<Request<AssumeRoleRequest>, AssumeRoleRequest> {
    public Request<AssumeRoleRequest> marshall(AssumeRoleRequest assumeRoleRequest) {
        if (assumeRoleRequest != null) {
            Request<AssumeRoleRequest> request = new DefaultRequest<>(assumeRoleRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "AssumeRole");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (assumeRoleRequest.getRoleArn() != null) {
                request.addParameter("RoleArn", StringUtils.fromString(assumeRoleRequest.getRoleArn()));
            }
            if (assumeRoleRequest.getRoleSessionName() != null) {
                request.addParameter("RoleSessionName", StringUtils.fromString(assumeRoleRequest.getRoleSessionName()));
            }
            if (assumeRoleRequest.getPolicyArns() != null) {
                int policyArnsIndex = 1;
                String policyArnsPrefix = "PolicyArns";
                for (PolicyDescriptorType policyArnsItem : assumeRoleRequest.getPolicyArns()) {
                    String prefix = policyArnsPrefix + ".member." + policyArnsIndex;
                    if (policyArnsItem != null) {
                        PolicyDescriptorTypeStaxMarshaller.getInstance().marshall(policyArnsItem, request, prefix + ".");
                    }
                    policyArnsIndex++;
                }
            }
            if (assumeRoleRequest.getPolicy() != null) {
                request.addParameter("Policy", StringUtils.fromString(assumeRoleRequest.getPolicy()));
            }
            if (assumeRoleRequest.getDurationSeconds() != null) {
                request.addParameter("DurationSeconds", StringUtils.fromInteger(assumeRoleRequest.getDurationSeconds()));
            }
            if (assumeRoleRequest.getTags() != null) {
                int tagsIndex = 1;
                String tagsPrefix = "Tags";
                for (Tag tagsItem : assumeRoleRequest.getTags()) {
                    String prefix2 = tagsPrefix + ".member." + tagsIndex;
                    if (tagsItem != null) {
                        TagStaxMarshaller.getInstance().marshall(tagsItem, request, prefix2 + ".");
                    }
                    tagsIndex++;
                }
            }
            if (assumeRoleRequest.getTransitiveTagKeys() != null) {
                int transitiveTagKeysIndex = 1;
                String transitiveTagKeysPrefix = "TransitiveTagKeys";
                for (String transitiveTagKeysItem : assumeRoleRequest.getTransitiveTagKeys()) {
                    String prefix3 = transitiveTagKeysPrefix + ".member." + transitiveTagKeysIndex;
                    if (transitiveTagKeysItem != null) {
                        request.addParameter(prefix3, StringUtils.fromString(transitiveTagKeysItem));
                    }
                    transitiveTagKeysIndex++;
                }
            }
            if (assumeRoleRequest.getExternalId() != null) {
                request.addParameter("ExternalId", StringUtils.fromString(assumeRoleRequest.getExternalId()));
            }
            if (assumeRoleRequest.getSerialNumber() != null) {
                request.addParameter("SerialNumber", StringUtils.fromString(assumeRoleRequest.getSerialNumber()));
            }
            if (assumeRoleRequest.getTokenCode() != null) {
                request.addParameter("TokenCode", StringUtils.fromString(assumeRoleRequest.getTokenCode()));
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(AssumeRoleRequest)");
    }
}
