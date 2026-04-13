package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;

public class SendAlexaOfferToMasterRequestMarshaller implements Marshaller<Request<SendAlexaOfferToMasterRequest>, SendAlexaOfferToMasterRequest> {
    public Request<SendAlexaOfferToMasterRequest> marshall(SendAlexaOfferToMasterRequest sendAlexaOfferToMasterRequest) {
        if (sendAlexaOfferToMasterRequest != null) {
            Request<SendAlexaOfferToMasterRequest> request = new DefaultRequest<>(sendAlexaOfferToMasterRequest, "AWSKinesisVideoSignaling");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/v1/send-alexa-offer-to-master");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (sendAlexaOfferToMasterRequest.getChannelARN() != null) {
                    String channelARN = sendAlexaOfferToMasterRequest.getChannelARN();
                    jsonWriter.name("ChannelARN");
                    jsonWriter.value(channelARN);
                }
                if (sendAlexaOfferToMasterRequest.getSenderClientId() != null) {
                    String senderClientId = sendAlexaOfferToMasterRequest.getSenderClientId();
                    jsonWriter.name("SenderClientId");
                    jsonWriter.value(senderClientId);
                }
                if (sendAlexaOfferToMasterRequest.getMessagePayload() != null) {
                    String messagePayload = sendAlexaOfferToMasterRequest.getMessagePayload();
                    jsonWriter.name("MessagePayload");
                    jsonWriter.value(messagePayload);
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
            throw new AmazonClientException("Invalid argument passed to marshall(SendAlexaOfferToMasterRequest)");
        }
    }
}
