package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.CognitoIdentityProvider;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class CognitoIdentityProviderJsonUnmarshaller implements Unmarshaller<CognitoIdentityProvider, JsonUnmarshallerContext> {
    private static CognitoIdentityProviderJsonUnmarshaller instance;

    CognitoIdentityProviderJsonUnmarshaller() {
    }

    public CognitoIdentityProvider unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CognitoIdentityProvider cognitoIdentityProvider = new CognitoIdentityProvider();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ProviderName")) {
                cognitoIdentityProvider.setProviderName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ClientId")) {
                cognitoIdentityProvider.setClientId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ServerSideTokenCheck")) {
                cognitoIdentityProvider.setServerSideTokenCheck(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return cognitoIdentityProvider;
    }

    public static CognitoIdentityProviderJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CognitoIdentityProviderJsonUnmarshaller();
        }
        return instance;
    }
}
