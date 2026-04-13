package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.DeleteStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class DeleteStreamRequestMarshaller implements Marshaller<Request<DeleteStreamRequest>, DeleteStreamRequest> {
    public Request<DeleteStreamRequest> marshall(DeleteStreamRequest deleteStreamRequest) {
        if (deleteStreamRequest != null) {
            Request<DeleteStreamRequest> request = new DefaultRequest<>(deleteStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/deleteStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (deleteStreamRequest.getStreamARN() != null) {
                    String streamARN = deleteStreamRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (deleteStreamRequest.getCurrentVersion() != null) {
                    String currentVersion = deleteStreamRequest.getCurrentVersion();
                    jsonWriter.name("CurrentVersion");
                    jsonWriter.value(currentVersion);
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
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteStreamRequest)");
        }
    }
}
