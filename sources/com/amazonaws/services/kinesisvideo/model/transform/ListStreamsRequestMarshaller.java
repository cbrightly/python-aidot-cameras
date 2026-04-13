package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.ListStreamsRequest;
import com.amazonaws.services.kinesisvideo.model.StreamNameCondition;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class ListStreamsRequestMarshaller implements Marshaller<Request<ListStreamsRequest>, ListStreamsRequest> {
    public Request<ListStreamsRequest> marshall(ListStreamsRequest listStreamsRequest) {
        if (listStreamsRequest != null) {
            Request<ListStreamsRequest> request = new DefaultRequest<>(listStreamsRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/listStreams");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (listStreamsRequest.getMaxResults() != null) {
                    Integer maxResults = listStreamsRequest.getMaxResults();
                    jsonWriter.name("MaxResults");
                    jsonWriter.value((Number) maxResults);
                }
                if (listStreamsRequest.getNextToken() != null) {
                    String nextToken = listStreamsRequest.getNextToken();
                    jsonWriter.name("NextToken");
                    jsonWriter.value(nextToken);
                }
                if (listStreamsRequest.getStreamNameCondition() != null) {
                    StreamNameCondition streamNameCondition = listStreamsRequest.getStreamNameCondition();
                    jsonWriter.name("StreamNameCondition");
                    StreamNameConditionJsonMarshaller.getInstance().marshall(streamNameCondition, jsonWriter);
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
            throw new AmazonClientException("Invalid argument passed to marshall(ListStreamsRequest)");
        }
    }
}
