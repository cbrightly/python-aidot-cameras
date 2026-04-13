package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;

public class GetOpenIdTokenForDeveloperIdentityRequestMarshaller implements Marshaller<Request<GetOpenIdTokenForDeveloperIdentityRequest>, GetOpenIdTokenForDeveloperIdentityRequest> {
    public Request<GetOpenIdTokenForDeveloperIdentityRequest> marshall(GetOpenIdTokenForDeveloperIdentityRequest getOpenIdTokenForDeveloperIdentityRequest) {
        if (getOpenIdTokenForDeveloperIdentityRequest != null) {
            Request<GetOpenIdTokenForDeveloperIdentityRequest> request = new DefaultRequest<>(getOpenIdTokenForDeveloperIdentityRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.GetOpenIdTokenForDeveloperIdentity");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (getOpenIdTokenForDeveloperIdentityRequest.getIdentityPoolId() != null) {
                    String identityPoolId = getOpenIdTokenForDeveloperIdentityRequest.getIdentityPoolId();
                    jsonWriter.name("IdentityPoolId");
                    jsonWriter.value(identityPoolId);
                }
                if (getOpenIdTokenForDeveloperIdentityRequest.getIdentityId() != null) {
                    String identityId = getOpenIdTokenForDeveloperIdentityRequest.getIdentityId();
                    jsonWriter.name("IdentityId");
                    jsonWriter.value(identityId);
                }
                if (getOpenIdTokenForDeveloperIdentityRequest.getLogins() != null) {
                    Map<String, String> logins = getOpenIdTokenForDeveloperIdentityRequest.getLogins();
                    jsonWriter.name("Logins");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, String> loginsEntry : logins.entrySet()) {
                        String loginsValue = loginsEntry.getValue();
                        if (loginsValue != null) {
                            jsonWriter.name(loginsEntry.getKey());
                            jsonWriter.value(loginsValue);
                        }
                    }
                    jsonWriter.endObject();
                }
                if (getOpenIdTokenForDeveloperIdentityRequest.getTokenDuration() != null) {
                    Long tokenDuration = getOpenIdTokenForDeveloperIdentityRequest.getTokenDuration();
                    jsonWriter.name("TokenDuration");
                    jsonWriter.value((Number) tokenDuration);
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
            throw new AmazonClientException("Invalid argument passed to marshall(GetOpenIdTokenForDeveloperIdentityRequest)");
        }
    }
}
