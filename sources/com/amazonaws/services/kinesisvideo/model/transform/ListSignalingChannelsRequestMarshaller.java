package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.ChannelNameCondition;
import com.amazonaws.services.kinesisvideo.model.ListSignalingChannelsRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class ListSignalingChannelsRequestMarshaller implements Marshaller<Request<ListSignalingChannelsRequest>, ListSignalingChannelsRequest> {
    public Request<ListSignalingChannelsRequest> marshall(ListSignalingChannelsRequest listSignalingChannelsRequest) {
        if (listSignalingChannelsRequest != null) {
            Request<ListSignalingChannelsRequest> request = new DefaultRequest<>(listSignalingChannelsRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/listSignalingChannels");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (listSignalingChannelsRequest.getMaxResults() != null) {
                    Integer maxResults = listSignalingChannelsRequest.getMaxResults();
                    jsonWriter.name("MaxResults");
                    jsonWriter.value((Number) maxResults);
                }
                if (listSignalingChannelsRequest.getNextToken() != null) {
                    String nextToken = listSignalingChannelsRequest.getNextToken();
                    jsonWriter.name("NextToken");
                    jsonWriter.value(nextToken);
                }
                if (listSignalingChannelsRequest.getChannelNameCondition() != null) {
                    ChannelNameCondition channelNameCondition = listSignalingChannelsRequest.getChannelNameCondition();
                    jsonWriter.name("ChannelNameCondition");
                    ChannelNameConditionJsonMarshaller.getInstance().marshall(channelNameCondition, jsonWriter);
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
            throw new AmazonClientException("Invalid argument passed to marshall(ListSignalingChannelsRequest)");
        }
    }
}
