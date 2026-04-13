package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.DeleteSignalingChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class DeleteSignalingChannelRequestMarshaller implements Marshaller<Request<DeleteSignalingChannelRequest>, DeleteSignalingChannelRequest> {
    public Request<DeleteSignalingChannelRequest> marshall(DeleteSignalingChannelRequest deleteSignalingChannelRequest) {
        if (deleteSignalingChannelRequest != null) {
            Request<DeleteSignalingChannelRequest> request = new DefaultRequest<>(deleteSignalingChannelRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/deleteSignalingChannel");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (deleteSignalingChannelRequest.getChannelARN() != null) {
                    String channelARN = deleteSignalingChannelRequest.getChannelARN();
                    jsonWriter.name("ChannelARN");
                    jsonWriter.value(channelARN);
                }
                if (deleteSignalingChannelRequest.getCurrentVersion() != null) {
                    String currentVersion = deleteSignalingChannelRequest.getCurrentVersion();
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
            throw new AmazonClientException("Invalid argument passed to marshall(DeleteSignalingChannelRequest)");
        }
    }
}
