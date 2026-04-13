package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.UntagStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;

public class UntagStreamRequestMarshaller implements Marshaller<Request<UntagStreamRequest>, UntagStreamRequest> {
    public Request<UntagStreamRequest> marshall(UntagStreamRequest untagStreamRequest) {
        if (untagStreamRequest != null) {
            Request<UntagStreamRequest> request = new DefaultRequest<>(untagStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/untagStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (untagStreamRequest.getStreamARN() != null) {
                    String streamARN = untagStreamRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (untagStreamRequest.getStreamName() != null) {
                    String streamName = untagStreamRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (untagStreamRequest.getTagKeyList() != null) {
                    List<String> tagKeyList = untagStreamRequest.getTagKeyList();
                    jsonWriter.name("TagKeyList");
                    jsonWriter.beginArray();
                    for (String tagKeyListItem : tagKeyList) {
                        if (tagKeyListItem != null) {
                            jsonWriter.value(tagKeyListItem);
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
                    request.addHeader("Content-Type", "application/x-amz-json-1.0");
                }
                return request;
            } catch (Throwable t) {
                throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
            }
        } else {
            throw new AmazonClientException("Invalid argument passed to marshall(UntagStreamRequest)");
        }
    }
}
