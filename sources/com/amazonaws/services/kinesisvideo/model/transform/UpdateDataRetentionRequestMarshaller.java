package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.UpdateDataRetentionRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class UpdateDataRetentionRequestMarshaller implements Marshaller<Request<UpdateDataRetentionRequest>, UpdateDataRetentionRequest> {
    public Request<UpdateDataRetentionRequest> marshall(UpdateDataRetentionRequest updateDataRetentionRequest) {
        if (updateDataRetentionRequest != null) {
            Request<UpdateDataRetentionRequest> request = new DefaultRequest<>(updateDataRetentionRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/updateDataRetention");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (updateDataRetentionRequest.getStreamName() != null) {
                    String streamName = updateDataRetentionRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (updateDataRetentionRequest.getStreamARN() != null) {
                    String streamARN = updateDataRetentionRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (updateDataRetentionRequest.getCurrentVersion() != null) {
                    String currentVersion = updateDataRetentionRequest.getCurrentVersion();
                    jsonWriter.name("CurrentVersion");
                    jsonWriter.value(currentVersion);
                }
                if (updateDataRetentionRequest.getOperation() != null) {
                    String operation = updateDataRetentionRequest.getOperation();
                    jsonWriter.name("Operation");
                    jsonWriter.value(operation);
                }
                if (updateDataRetentionRequest.getDataRetentionChangeInHours() != null) {
                    Integer dataRetentionChangeInHours = updateDataRetentionRequest.getDataRetentionChangeInHours();
                    jsonWriter.name("DataRetentionChangeInHours");
                    jsonWriter.value((Number) dataRetentionChangeInHours);
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
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateDataRetentionRequest)");
        }
    }
}
