package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.UpdateIdentityPoolResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class UpdateIdentityPoolResultJsonUnmarshaller implements Unmarshaller<UpdateIdentityPoolResult, JsonUnmarshallerContext> {
    private static UpdateIdentityPoolResultJsonUnmarshaller instance;

    public UpdateIdentityPoolResult unmarshall(JsonUnmarshallerContext context) {
        UpdateIdentityPoolResult updateIdentityPoolResult = new UpdateIdentityPoolResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPoolId")) {
                updateIdentityPoolResult.setIdentityPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IdentityPoolName")) {
                updateIdentityPoolResult.setIdentityPoolName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowUnauthenticatedIdentities")) {
                updateIdentityPoolResult.setAllowUnauthenticatedIdentities(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowClassicFlow")) {
                updateIdentityPoolResult.setAllowClassicFlow(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SupportedLoginProviders")) {
                updateIdentityPoolResult.setSupportedLoginProviders(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("DeveloperProviderName")) {
                updateIdentityPoolResult.setDeveloperProviderName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("OpenIdConnectProviderARNs")) {
                updateIdentityPoolResult.setOpenIdConnectProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("CognitoIdentityProviders")) {
                updateIdentityPoolResult.setCognitoIdentityProviders(new ListUnmarshaller(CognitoIdentityProviderJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("SamlProviderARNs")) {
                updateIdentityPoolResult.setSamlProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("IdentityPoolTags")) {
                updateIdentityPoolResult.setIdentityPoolTags(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return updateIdentityPoolResult;
    }

    public static UpdateIdentityPoolResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateIdentityPoolResultJsonUnmarshaller();
        }
        return instance;
    }
}
