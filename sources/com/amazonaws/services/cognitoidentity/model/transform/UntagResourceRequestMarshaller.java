package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.UntagResourceRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;

public class UntagResourceRequestMarshaller implements Marshaller<Request<UntagResourceRequest>, UntagResourceRequest> {
    public Request<UntagResourceRequest> marshall(UntagResourceRequest untagResourceRequest) {
        if (untagResourceRequest != null) {
            Request<UntagResourceRequest> request = new DefaultRequest<>(untagResourceRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.UntagResource");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (untagResourceRequest.getResourceArn() != null) {
                    String resourceArn = untagResourceRequest.getResourceArn();
                    jsonWriter.name("ResourceArn");
                    jsonWriter.value(resourceArn);
                }
                if (untagResourceRequest.getTagKeys() != null) {
                    List<String> tagKeys = untagResourceRequest.getTagKeys();
                    jsonWriter.name("TagKeys");
                    jsonWriter.beginArray();
                    for (String tagKeysItem : tagKeys) {
                        if (tagKeysItem != null) {
                            jsonWriter.value(tagKeysItem);
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
            throw new AmazonClientException("Invalid argument passed to marshall(UntagResourceRequest)");
        }
    }
}
