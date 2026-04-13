package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import com.amazonaws.util.json.AwsJsonWriter;

public class IdentityPoolShortDescriptionJsonMarshaller {
    private static IdentityPoolShortDescriptionJsonMarshaller instance;

    IdentityPoolShortDescriptionJsonMarshaller() {
    }

    public void marshall(IdentityPoolShortDescription identityPoolShortDescription, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (identityPoolShortDescription.getIdentityPoolId() != null) {
            String identityPoolId = identityPoolShortDescription.getIdentityPoolId();
            jsonWriter.name("IdentityPoolId");
            jsonWriter.value(identityPoolId);
        }
        if (identityPoolShortDescription.getIdentityPoolName() != null) {
            String identityPoolName = identityPoolShortDescription.getIdentityPoolName();
            jsonWriter.name("IdentityPoolName");
            jsonWriter.value(identityPoolName);
        }
        jsonWriter.endObject();
    }

    public static IdentityPoolShortDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new IdentityPoolShortDescriptionJsonMarshaller();
        }
        return instance;
    }
}
