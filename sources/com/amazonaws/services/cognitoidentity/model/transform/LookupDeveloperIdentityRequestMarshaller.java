package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.LookupDeveloperIdentityRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class LookupDeveloperIdentityRequestMarshaller implements Marshaller<Request<LookupDeveloperIdentityRequest>, LookupDeveloperIdentityRequest> {
    public Request<LookupDeveloperIdentityRequest> marshall(LookupDeveloperIdentityRequest lookupDeveloperIdentityRequest) {
        if (lookupDeveloperIdentityRequest != null) {
            Request<LookupDeveloperIdentityRequest> request = new DefaultRequest<>(lookupDeveloperIdentityRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.LookupDeveloperIdentity");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (lookupDeveloperIdentityRequest.getIdentityPoolId() != null) {
                    String identityPoolId = lookupDeveloperIdentityRequest.getIdentityPoolId();
                    jsonWriter.name("IdentityPoolId");
                    jsonWriter.value(identityPoolId);
                }
                if (lookupDeveloperIdentityRequest.getIdentityId() != null) {
                    String identityId = lookupDeveloperIdentityRequest.getIdentityId();
                    jsonWriter.name("IdentityId");
                    jsonWriter.value(identityId);
                }
                if (lookupDeveloperIdentityRequest.getDeveloperUserIdentifier() != null) {
                    String developerUserIdentifier = lookupDeveloperIdentityRequest.getDeveloperUserIdentifier();
                    jsonWriter.name("DeveloperUserIdentifier");
                    jsonWriter.value(developerUserIdentifier);
                }
                if (lookupDeveloperIdentityRequest.getMaxResults() != null) {
                    Integer maxResults = lookupDeveloperIdentityRequest.getMaxResults();
                    jsonWriter.name("MaxResults");
                    jsonWriter.value((Number) maxResults);
                }
                if (lookupDeveloperIdentityRequest.getNextToken() != null) {
                    String nextToken = lookupDeveloperIdentityRequest.getNextToken();
                    jsonWriter.name("NextToken");
                    jsonWriter.value(nextToken);
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
            throw new AmazonClientException("Invalid argument passed to marshall(LookupDeveloperIdentityRequest)");
        }
    }
}
