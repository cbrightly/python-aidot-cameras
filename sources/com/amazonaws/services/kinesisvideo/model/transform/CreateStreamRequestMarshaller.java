package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.CreateStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;

public class CreateStreamRequestMarshaller implements Marshaller<Request<CreateStreamRequest>, CreateStreamRequest> {
    public Request<CreateStreamRequest> marshall(CreateStreamRequest createStreamRequest) {
        if (createStreamRequest != null) {
            Request<CreateStreamRequest> request = new DefaultRequest<>(createStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/createStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (createStreamRequest.getDeviceName() != null) {
                    String deviceName = createStreamRequest.getDeviceName();
                    jsonWriter.name("DeviceName");
                    jsonWriter.value(deviceName);
                }
                if (createStreamRequest.getStreamName() != null) {
                    String streamName = createStreamRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (createStreamRequest.getMediaType() != null) {
                    String mediaType = createStreamRequest.getMediaType();
                    jsonWriter.name("MediaType");
                    jsonWriter.value(mediaType);
                }
                if (createStreamRequest.getKmsKeyId() != null) {
                    String kmsKeyId = createStreamRequest.getKmsKeyId();
                    jsonWriter.name("KmsKeyId");
                    jsonWriter.value(kmsKeyId);
                }
                if (createStreamRequest.getDataRetentionInHours() != null) {
                    Integer dataRetentionInHours = createStreamRequest.getDataRetentionInHours();
                    jsonWriter.name("DataRetentionInHours");
                    jsonWriter.value((Number) dataRetentionInHours);
                }
                if (createStreamRequest.getTags() != null) {
                    Map<String, String> tags = createStreamRequest.getTags();
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
            throw new AmazonClientException("Invalid argument passed to marshall(CreateStreamRequest)");
        }
    }
}
