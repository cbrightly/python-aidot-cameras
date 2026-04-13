package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.MergeDeveloperIdentitiesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class MergeDeveloperIdentitiesRequestMarshaller implements Marshaller<Request<MergeDeveloperIdentitiesRequest>, MergeDeveloperIdentitiesRequest> {
    public Request<MergeDeveloperIdentitiesRequest> marshall(MergeDeveloperIdentitiesRequest mergeDeveloperIdentitiesRequest) {
        if (mergeDeveloperIdentitiesRequest != null) {
            Request<MergeDeveloperIdentitiesRequest> request = new DefaultRequest<>(mergeDeveloperIdentitiesRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.MergeDeveloperIdentities");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (mergeDeveloperIdentitiesRequest.getSourceUserIdentifier() != null) {
                    String sourceUserIdentifier = mergeDeveloperIdentitiesRequest.getSourceUserIdentifier();
                    jsonWriter.name("SourceUserIdentifier");
                    jsonWriter.value(sourceUserIdentifier);
                }
                if (mergeDeveloperIdentitiesRequest.getDestinationUserIdentifier() != null) {
                    String destinationUserIdentifier = mergeDeveloperIdentitiesRequest.getDestinationUserIdentifier();
                    jsonWriter.name("DestinationUserIdentifier");
                    jsonWriter.value(destinationUserIdentifier);
                }
                if (mergeDeveloperIdentitiesRequest.getDeveloperProviderName() != null) {
                    String developerProviderName = mergeDeveloperIdentitiesRequest.getDeveloperProviderName();
                    jsonWriter.name("DeveloperProviderName");
                    jsonWriter.value(developerProviderName);
                }
                if (mergeDeveloperIdentitiesRequest.getIdentityPoolId() != null) {
                    String identityPoolId = mergeDeveloperIdentitiesRequest.getIdentityPoolId();
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
            throw new AmazonClientException("Invalid argument passed to marshall(MergeDeveloperIdentitiesRequest)");
        }
    }
}
