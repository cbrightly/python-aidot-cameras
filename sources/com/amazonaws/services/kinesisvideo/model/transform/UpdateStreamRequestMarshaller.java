package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.UpdateStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class UpdateStreamRequestMarshaller implements Marshaller<Request<UpdateStreamRequest>, UpdateStreamRequest> {
    public Request<UpdateStreamRequest> marshall(UpdateStreamRequest updateStreamRequest) {
        if (updateStreamRequest != null) {
            Request<UpdateStreamRequest> request = new DefaultRequest<>(updateStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/updateStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (updateStreamRequest.getStreamName() != null) {
                    String streamName = updateStreamRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (updateStreamRequest.getStreamARN() != null) {
                    String streamARN = updateStreamRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (updateStreamRequest.getCurrentVersion() != null) {
                    String currentVersion = updateStreamRequest.getCurrentVersion();
                    jsonWriter.name("CurrentVersion");
                    jsonWriter.value(currentVersion);
                }
                if (updateStreamRequest.getDeviceName() != null) {
                    String deviceName = updateStreamRequest.getDeviceName();
                    jsonWriter.name("DeviceName");
                    jsonWriter.value(deviceName);
                }
                if (updateStreamRequest.getMediaType() != null) {
                    String mediaType = updateStreamRequest.getMediaType();
                    jsonWriter.name("MediaType");
                    jsonWriter.value(mediaType);
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
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateStreamRequest)");
        }
    }
}
