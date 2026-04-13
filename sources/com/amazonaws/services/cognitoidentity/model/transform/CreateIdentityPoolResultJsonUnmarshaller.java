package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.CreateIdentityPoolResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class CreateIdentityPoolResultJsonUnmarshaller implements Unmarshaller<CreateIdentityPoolResult, JsonUnmarshallerContext> {
    private static CreateIdentityPoolResultJsonUnmarshaller instance;

    public CreateIdentityPoolResult unmarshall(JsonUnmarshallerContext context) {
        CreateIdentityPoolResult createIdentityPoolResult = new CreateIdentityPoolResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPoolId")) {
                createIdentityPoolResult.setIdentityPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IdentityPoolName")) {
                createIdentityPoolResult.setIdentityPoolName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowUnauthenticatedIdentities")) {
                createIdentityPoolResult.setAllowUnauthenticatedIdentities(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowClassicFlow")) {
                createIdentityPoolResult.setAllowClassicFlow(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SupportedLoginProviders")) {
                createIdentityPoolResult.setSupportedLoginProviders(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("DeveloperProviderName")) {
                createIdentityPoolResult.setDeveloperProviderName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("OpenIdConnectProviderARNs")) {
                createIdentityPoolResult.setOpenIdConnectProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("CognitoIdentityProviders")) {
                createIdentityPoolResult.setCognitoIdentityProviders(new ListUnmarshaller(CognitoIdentityProviderJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("SamlProviderARNs")) {
                createIdentityPoolResult.setSamlProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("IdentityPoolTags")) {
                createIdentityPoolResult.setIdentityPoolTags(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return createIdentityPoolResult;
    }

    public static CreateIdentityPoolResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateIdentityPoolResultJsonUnmarshaller();
        }
        return instance;
    }
}
