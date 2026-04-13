package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class SendAlexaOfferToMasterResultJsonUnmarshaller implements Unmarshaller<SendAlexaOfferToMasterResult, JsonUnmarshallerContext> {
    private static SendAlexaOfferToMasterResultJsonUnmarshaller instance;

    public SendAlexaOfferToMasterResult unmarshall(JsonUnmarshallerContext context) {
        SendAlexaOfferToMasterResult sendAlexaOfferToMasterResult = new SendAlexaOfferToMasterResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Answer")) {
                sendAlexaOfferToMasterResult.setAnswer(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sendAlexaOfferToMasterResult;
    }

    public static SendAlexaOfferToMasterResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SendAlexaOfferToMasterResultJsonUnmarshaller();
        }
        return instance;
    }
}
