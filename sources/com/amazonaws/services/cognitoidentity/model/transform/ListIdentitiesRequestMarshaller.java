package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.ListIdentitiesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class ListIdentitiesRequestMarshaller implements Marshaller<Request<ListIdentitiesRequest>, ListIdentitiesRequest> {
    public Request<ListIdentitiesRequest> marshall(ListIdentitiesRequest listIdentitiesRequest) {
        if (listIdentitiesRequest != null) {
            Request<ListIdentitiesRequest> request = new DefaultRequest<>(listIdentitiesRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.ListIdentities");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (listIdentitiesRequest.getIdentityPoolId() != null) {
                    String identityPoolId = listIdentitiesRequest.getIdentityPoolId();
                    jsonWriter.name("IdentityPoolId");
                    jsonWriter.value(identityPoolId);
                }
                if (listIdentitiesRequest.getMaxResults() != null) {
                    Integer maxResults = listIdentitiesRequest.getMaxResults();
                    jsonWriter.name("MaxResults");
                    jsonWriter.value((Number) maxResults);
                }
                if (listIdentitiesRequest.getNextToken() != null) {
                    String nextToken = listIdentitiesRequest.getNextToken();
                    jsonWriter.name("NextToken");
                    jsonWriter.value(nextToken);
                }
                if (listIdentitiesRequest.getHideDisabled() != null) {
                    Boolean hideDisabled = listIdentitiesRequest.getHideDisabled();
                    jsonWriter.name("HideDisabled");
                    jsonWriter.value(hideDisabled.booleanValue());
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
            throw new AmazonClientException("Invalid argument passed to marshall(ListIdentitiesRequest)");
        }
    }
}
