package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.CognitoIdentityProvider;
import com.amazonaws.util.json.AwsJsonWriter;

public class CognitoIdentityProviderJsonMarshaller {
    private static CognitoIdentityProviderJsonMarshaller instance;

    CognitoIdentityProviderJsonMarshaller() {
    }

    public void marshall(CognitoIdentityProvider cognitoIdentityProvider, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (cognitoIdentityProvider.getProviderName() != null) {
            String providerName = cognitoIdentityProvider.getProviderName();
            jsonWriter.name("ProviderName");
            jsonWriter.value(providerName);
        }
        if (cognitoIdentityProvider.getClientId() != null) {
            String clientId = cognitoIdentityProvider.getClientId();
            jsonWriter.name("ClientId");
            jsonWriter.value(clientId);
        }
        if (cognitoIdentityProvider.getServerSideTokenCheck() != null) {
            Boolean serverSideTokenCheck = cognitoIdentityProvider.getServerSideTokenCheck();
            jsonWriter.name("ServerSideTokenCheck");
            jsonWriter.value(serverSideTokenCheck.booleanValue());
        }
        jsonWriter.endObject();
    }

    public static CognitoIdentityProviderJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CognitoIdentityProviderJsonMarshaller();
        }
        return instance;
    }
}
