package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideo.model.GetDataEndpointRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class GetDataEndpointRequestMarshaller implements Marshaller<Request<GetDataEndpointRequest>, GetDataEndpointRequest> {
    public Request<GetDataEndpointRequest> marshall(GetDataEndpointRequest getDataEndpointRequest) {
        if (getDataEndpointRequest != null) {
            Request<GetDataEndpointRequest> request = new DefaultRequest<>(getDataEndpointRequest, "AWSKinesisVideo");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/getDataEndpoint");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (getDataEndpointRequest.getStreamName() != null) {
                    String streamName = getDataEndpointRequest.getStreamName();
                    jsonWriter.name("StreamName");
                    jsonWriter.value(streamName);
                }
                if (getDataEndpointRequest.getStreamARN() != null) {
                    String streamARN = getDataEndpointRequest.getStreamARN();
                    jsonWriter.name("StreamARN");
                    jsonWriter.value(streamARN);
                }
                if (getDataEndpointRequest.getAPIName() != null) {
                    String aPIName = getDataEndpointRequest.getAPIName();
                    jsonWriter.name("APIName");
                    jsonWriter.value(aPIName);
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
            throw new AmazonClientException("Invalid argument passed to marshall(GetDataEndpointRequest)");
        }
    }
}
