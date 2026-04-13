package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.GetIdentityPoolRolesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class GetIdentityPoolRolesRequestMarshaller implements Marshaller<Request<GetIdentityPoolRolesRequest>, GetIdentityPoolRolesRequest> {
    public Request<GetIdentityPoolRolesRequest> marshall(GetIdentityPoolRolesRequest getIdentityPoolRolesRequest) {
        if (getIdentityPoolRolesRequest != null) {
            Request<GetIdentityPoolRolesRequest> request = new DefaultRequest<>(getIdentityPoolRolesRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetIdentityPoolRoles");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (getIdentityPoolRolesRequest.getIdentityPoolId() != null) {
                    String identityPoolId = getIdentityPoolRolesRequest.getIdentityPoolId();
                    jsonWriter.name("IdentityPoolId");
                    jsonWriter.value(identityPoolId);
                }
                jsonWriter.endObject();
                jsonWriter.close();
                String snippet = stringWriter.toString();
                byte[] content = snippet.getBytes(StringUtils.UTF8);
                request.setContent(new StringInputStream(snippet));
                request.addHeader("Content-Length", Integer.toString(content.length));
                if (!request.getHeaders().containsKey("Content-Type")) {
                    request.addHeader("Content-Type", "application/x-amz-json-1.1");
                }
                return request;
            } catch (Throwable t) {
                throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
            }
        } else {
            throw new AmazonClientException("Invalid argument passed to marshall(GetIdentityPoolRolesRequest)");
        }
    }
}
