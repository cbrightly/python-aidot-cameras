package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.GetFederationTokenRequest;
import com.amazonaws.services.securitytoken.model.PolicyDescriptorType;
import com.amazonaws.services.securitytoken.model.Tag;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class GetFederationTokenRequestMarshaller implements Marshaller<Request<GetFederationTokenRequest>, GetFederationTokenRequest> {
    public Request<GetFederationTokenRequest> marshall(GetFederationTokenRequest getFederationTokenRequest) {
        if (getFederationTokenRequest != null) {
            Request<GetFederationTokenRequest> request = new DefaultRequest<>(getFederationTokenRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "GetFederationToken");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (getFederationTokenRequest.getName() != null) {
                request.addParameter("Name", StringUtils.fromString(getFederationTokenRequest.getName()));
            }
            if (getFederationTokenRequest.getPolicy() != null) {
                request.addParameter("Policy", StringUtils.fromString(getFederationTokenRequest.getPolicy()));
            }
            if (getFederationTokenRequest.getPolicyArns() != null) {
                int policyArnsIndex = 1;
                String policyArnsPrefix = "PolicyArns";
                for (PolicyDescriptorType policyArnsItem : getFederationTokenRequest.getPolicyArns()) {
                    String prefix = policyArnsPrefix + ".member." + policyArnsIndex;
                    if (policyArnsItem != null) {
                        PolicyDescriptorTypeStaxMarshaller.getInstance().marshall(policyArnsItem, request, prefix + ".");
                    }
                    policyArnsIndex++;
                }
            }
            if (getFederationTokenRequest.getDurationSeconds() != null) {
                request.addParameter("DurationSeconds", StringUtils.fromInteger(getFederationTokenRequest.getDurationSeconds()));
            }
            if (getFederationTokenRequest.getTags() != null) {
                int tagsIndex = 1;
                String tagsPrefix = "Tags";
                for (Tag tagsItem : getFederationTokenRequest.getTags()) {
                    String prefix2 = tagsPrefix + ".member." + tagsIndex;
                    if (tagsItem != null) {
                        TagStaxMarshaller.getInstance().marshall(tagsItem, request, prefix2 + ".");
                    }
                    tagsIndex++;
                }
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(GetFederationTokenRequest)");
    }
}
