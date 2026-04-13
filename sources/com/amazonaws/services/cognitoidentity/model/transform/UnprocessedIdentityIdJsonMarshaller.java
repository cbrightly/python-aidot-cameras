package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.UnprocessedIdentityId;
import com.amazonaws.util.json.AwsJsonWriter;

public class UnprocessedIdentityIdJsonMarshaller {
    private static UnprocessedIdentityIdJsonMarshaller instance;

    UnprocessedIdentityIdJsonMarshaller() {
    }

    public void marshall(UnprocessedIdentityId unprocessedIdentityId, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (unprocessedIdentityId.getIdentityId() != null) {
            String identityId = unprocessedIdentityId.getIdentityId();
            jsonWriter.name("IdentityId");
            jsonWriter.value(identityId);
        }
        if (unprocessedIdentityId.getErrorCode() != null) {
            String errorCode = unprocessedIdentityId.getErrorCode();
            jsonWriter.name("ErrorCode");
            jsonWriter.value(errorCode);
        }
        jsonWriter.endObject();
    }

    public static UnprocessedIdentityIdJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new UnprocessedIdentityIdJsonMarshaller();
        }
        return instance;
    }
}
