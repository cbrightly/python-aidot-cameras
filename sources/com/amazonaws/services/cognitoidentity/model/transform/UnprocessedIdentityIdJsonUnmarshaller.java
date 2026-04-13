package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.UnprocessedIdentityId;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class UnprocessedIdentityIdJsonUnmarshaller implements Unmarshaller<UnprocessedIdentityId, JsonUnmarshallerContext> {
    private static UnprocessedIdentityIdJsonUnmarshaller instance;

    UnprocessedIdentityIdJsonUnmarshaller() {
    }

    public UnprocessedIdentityId unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        UnprocessedIdentityId unprocessedIdentityId = new UnprocessedIdentityId();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                unprocessedIdentityId.setIdentityId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ErrorCode")) {
                unprocessedIdentityId.setErrorCode(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return unprocessedIdentityId;
    }

    public static UnprocessedIdentityIdJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UnprocessedIdentityIdJsonUnmarshaller();
        }
        return instance;
    }
}
