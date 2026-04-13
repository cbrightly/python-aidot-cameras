package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.ListTagsForStreamRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class ListTagsForStreamRequestMarshaller implements Marshaller<Request<ListTagsForStreamRequest>, ListTagsForStreamRequest> {
    public Request<ListTagsForStreamRequest> marshall(ListTagsForStreamRequest listTagsForStreamRequest) {
        if (listTagsForStreamRequest != null) {
            Request<ListTagsForStreamRequest> request = new DefaultRequest<>(listTagsForStreamRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/listTagsForStream");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (listTagsForStreamRequest.getNextToken() != null) {
                    String nextToken = listTagsForStreamRequest.getNextToken();
                    jsonWriter.name("NextToken");
                    jsonWriter.value(nextToken);
                }
                if (listTagsForStreamRequest.getStreamARN() != null) {
                    String streamARN = listTagsForStreamRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (listTagsForStreamRequest.getStreamName() != null) {
                    String streamName = listTagsForStreamRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
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
            throw new AmazonClientException("Invalid argument passed to marshall(ListTagsForStreamRequest)");
        }
    }
}
