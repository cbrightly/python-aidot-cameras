package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.DecodeAuthorizationMessageRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class DecodeAuthorizationMessageRequestMarshaller implements Marshaller<Request<DecodeAuthorizationMessageRequest>, DecodeAuthorizationMessageRequest> {
    public Request<DecodeAuthorizationMessageRequest> marshall(DecodeAuthorizationMessageRequest decodeAuthorizationMessageRequest) {
        if (decodeAuthorizationMessageRequest != null) {
            Request<DecodeAuthorizationMessageRequest> request = new DefaultRequest<>(decodeAuthorizationMessageRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "DecodeAuthorizationMessage");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (decodeAuthorizationMessageRequest.getEncodedMessage() != null) {
                request.addParameter("EncodedMessage", StringUtils.fromString(decodeAuthorizationMessageRequest.getEncodedMessage()));
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(DecodeAuthorizationMessageRequest)");
    }
}
