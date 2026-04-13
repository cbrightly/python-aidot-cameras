package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.UnlinkIdentityRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class UnlinkIdentityRequestMarshaller implements Marshaller<Request<UnlinkIdentityRequest>, UnlinkIdentityRequest> {
    public Request<UnlinkIdentityRequest> marshall(UnlinkIdentityRequest unlinkIdentityRequest) {
        if (unlinkIdentityRequest != null) {
            Request<UnlinkIdentityRequest> request = new DefaultRequest<>(unlinkIdentityRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.UnlinkIdentity");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (unlinkIdentityRequest.getIdentityId() != null) {
                    String identityId = unlinkIdentityRequest.getIdentityId();
                    jsonWriter.name("IdentityId");
                    jsonWriter.value(identityId);
                }
                if (unlinkIdentityRequest.getLogins() != null) {
                    Map<String, String> logins = unlinkIdentityRequest.getLogins();
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
                if (unlinkIdentityRequest.getLoginsToRemove() != null) {
                    List<String> loginsToRemove = unlinkIdentityRequest.getLoginsToRemove();
                    jsonWriter.name("LoginsToRemove");
                    jsonWriter.beginArray();
                    for (String loginsToRemoveItem : loginsToRemove) {
                        if (loginsToRemoveItem != null) {
                            jsonWriter.value(loginsToRemoveItem);
                        }
                    }
                    jsonWriter.endArray();
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
            throw new AmazonClientException("Invalid argument passed to marshall(UnlinkIdentityRequest)");
        }
    }
}
