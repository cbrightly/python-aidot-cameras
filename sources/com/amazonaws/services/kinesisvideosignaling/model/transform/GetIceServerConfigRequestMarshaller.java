package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class GetIceServerConfigRequestMarshaller implements Marshaller<Request<GetIceServerConfigRequest>, GetIceServerConfigRequest> {
    public Request<GetIceServerConfigRequest> marshall(GetIceServerConfigRequest getIceServerConfigRequest) {
        if (getIceServerConfigRequest != null) {
            Request<GetIceServerConfigRequest> request = new DefaultRequest<>(getIceServerConfigRequest, "AWSKinesisVideoSignaling");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/v1/get-ice-server-config");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (getIceServerConfigRequest.getChannelARN() != null) {
                    String channelARN = getIceServerConfigRequest.getChannelARN();
                    jsonWriter.name("ChannelARN");
                    jsonWriter.value(channelARN);
                }
                if (getIceServerConfigRequest.getClientId() != null) {
                    String clientId = getIceServerConfigRequest.getClientId();
                    jsonWriter.name("ClientId");
                    jsonWriter.value(clientId);
                }
                if (getIceServerConfigRequest.getService() != null) {
                    String service = getIceServerConfigRequest.getService();
                    jsonWriter.name("Service");
                    jsonWriter.value(service);
                }
                if (getIceServerConfigRequest.getUsername() != null) {
                    String username = getIceServerConfigRequest.getUsername();
                    jsonWriter.name("Username");
                    jsonWriter.value(username);
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
            throw new AmazonClientException("Invalid argument passed to marshall(GetIceServerConfigRequest)");
        }
    }
}
