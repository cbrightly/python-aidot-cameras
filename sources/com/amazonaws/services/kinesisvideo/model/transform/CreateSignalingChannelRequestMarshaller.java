package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.CreateSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.SingleMasterConfiguration;
import com.amazonaws.services.kinesisvideo.model.Tag;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;

public class CreateSignalingChannelRequestMarshaller implements Marshaller<Request<CreateSignalingChannelRequest>, CreateSignalingChannelRequest> {
    public Request<CreateSignalingChannelRequest> marshall(CreateSignalingChannelRequest createSignalingChannelRequest) {
        if (createSignalingChannelRequest != null) {
            Request<CreateSignalingChannelRequest> request = new DefaultRequest<>(createSignalingChannelRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/createSignalingChannel");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (createSignalingChannelRequest.getChannelName() != null) {
                    String channelName = createSignalingChannelRequest.getChannelName();
                    jsonWriter.name("ChannelName");
                    jsonWriter.value(channelName);
                }
                if (createSignalingChannelRequest.getChannelType() != null) {
                    String channelType = createSignalingChannelRequest.getChannelType();
                    jsonWriter.name("ChannelType");
                    jsonWriter.value(channelType);
                }
                if (createSignalingChannelRequest.getSingleMasterConfiguration() != null) {
                    SingleMasterConfiguration singleMasterConfiguration = createSignalingChannelRequest.getSingleMasterConfiguration();
                    jsonWriter.name("SingleMasterConfiguration");
                    SingleMasterConfigurationJsonMarshaller.getInstance().marshall(singleMasterConfiguration, jsonWriter);
                }
                if (createSignalingChannelRequest.getTags() != null) {
                    List<Tag> tags = createSignalingChannelRequest.getTags();
                    jsonWriter.name("Tags");
                    jsonWriter.beginArray();
                    for (Tag tagsItem : tags) {
                        if (tagsItem != null) {
                            TagJsonMarshaller.getInstance().marshall(tagsItem, jsonWriter);
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
            throw new AmazonClientException("Invalid argument passed to marshall(CreateSignalingChannelRequest)");
        }
    }
}
