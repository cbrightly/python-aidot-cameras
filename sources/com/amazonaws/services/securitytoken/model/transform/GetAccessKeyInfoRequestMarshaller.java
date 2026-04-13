package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.securitytoken.model.GetAccessKeyInfoRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringUtils;

public class GetAccessKeyInfoRequestMarshaller implements Marshaller<Request<GetAccessKeyInfoRequest>, GetAccessKeyInfoRequest> {
    public Request<GetAccessKeyInfoRequest> marshall(GetAccessKeyInfoRequest getAccessKeyInfoRequest) {
        if (getAccessKeyInfoRequest != null) {
            Request<GetAccessKeyInfoRequest> request = new DefaultRequest<>(getAccessKeyInfoRequest, "AWSSecurityTokenService");
            request.addParameter(JsonDocumentFields.ACTION, "GetAccessKeyInfo");
            request.addParameter(JsonDocumentFields.VERSION, "2011-06-15");
            if (getAccessKeyInfoRequest.getAccessKeyId() != null) {
                request.addParameter("AccessKeyId", StringUtils.fromString(getAccessKeyInfoRequest.getAccessKeyId()));
            }
            return request;
        }
        throw new AmazonClientException("Invalid argument passed to marshall(GetAccessKeyInfoRequest)");
    }
}
