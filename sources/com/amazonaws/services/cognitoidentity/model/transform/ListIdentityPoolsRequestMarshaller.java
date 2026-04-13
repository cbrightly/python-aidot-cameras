package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class ListIdentityPoolsRequestMarshaller implements Marshaller<Request<ListIdentityPoolsRequest>, ListIdentityPoolsRequest> {
    public Request<ListIdentityPoolsRequest> marshall(ListIdentityPoolsRequest listIdentityPoolsRequest) {
        if (listIdentityPoolsRequest != null) {
            Request<ListIdentityPoolsRequest> request = new DefaultRequest<>(listIdentityPoolsRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.ListIdentityPools");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (listIdentityPoolsRequest.getMaxResults() != null) {
                    Integer maxResults = listIdentityPoolsRequest.getMaxResults();
                    jsonWriter.name("MaxResults");
                    jsonWriter.value((Number) maxResults);
                }
                if (listIdentityPoolsRequest.getNextToken() != null) {
                    String nextToken = listIdentityPoolsRequest.getNextToken();
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
            throw new AmazonClientException("Invalid argument passed to marshall(ListIdentityPoolsRequest)");
        }
    }
}
