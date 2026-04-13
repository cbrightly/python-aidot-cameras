package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.TagStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;

public class TagStreamRequestMarshaller implements Marshaller<Request<TagStreamRequest>, TagStreamRequest> {
    public Request<TagStreamRequest> marshall(TagStreamRequest tagStreamRequest) {
        if (tagStreamRequest != null) {
            Request<TagStreamRequest> request = new DefaultRequest<>(tagStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/tagStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (tagStreamRequest.getStreamARN() != null) {
                    String streamARN = tagStreamRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (tagStreamRequest.getStreamName() != null) {
                    String streamName = tagStreamRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (tagStreamRequest.getTags() != null) {
                    Map<String, String> tags = tagStreamRequest.getTags();
                    jsonWriter.name("Tags");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, String> tagsEntry : tags.entrySet()) {
                        String tagsValue = tagsEntry.getValue();
                        if (tagsValue != null) {
                            jsonWriter.name(tagsEntry.getKey());
                            jsonWriter.value(tagsValue);
                        }
                    }
                    jsonWriter.endObject();
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
            throw new AmazonClientException("Invalid argument passed to marshall(TagStreamRequest)");
        }
    }
}
