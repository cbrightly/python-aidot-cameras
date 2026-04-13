package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.SingleMasterConfiguration;
import com.amazonaws.services.kinesisvideo.model.UpdateSignalingChannelRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class UpdateSignalingChannelRequestMarshaller implements Marshaller<Request<UpdateSignalingChannelRequest>, UpdateSignalingChannelRequest> {
    public Request<UpdateSignalingChannelRequest> marshall(UpdateSignalingChannelRequest updateSignalingChannelRequest) {
        if (updateSignalingChannelRequest != null) {
            Request<UpdateSignalingChannelRequest> request = new DefaultRequest<>(updateSignalingChannelRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/updateSignalingChannel");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (updateSignalingChannelRequest.getChannelARN() != null) {
                    String channelARN = updateSignalingChannelRequest.getChannelARN();
                    jsonWriter.name("ChannelARN");
                    jsonWriter.value(channelARN);
                }
                if (updateSignalingChannelRequest.getCurrentVersion() != null) {
                    String currentVersion = updateSignalingChannelRequest.getCurrentVersion();
                    jsonWriter.name("CurrentVersion");
                    jsonWriter.value(currentVersion);
                }
                if (updateSignalingChannelRequest.getSingleMasterConfiguration() != null) {
                    SingleMasterConfiguration singleMasterConfiguration = updateSignalingChannelRequest.getSingleMasterConfiguration();
                    jsonWriter.name("SingleMasterConfiguration");
                    SingleMasterConfigurationJsonMarshaller.getInstance().marshall(singleMasterConfiguration, jsonWriter);
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
            throw new AmazonClientException("Invalid argument passed to marshall(UpdateSignalingChannelRequest)");
        }
    }
}
