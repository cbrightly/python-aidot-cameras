package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller implements Unmarshaller<GetOpenIdTokenForDeveloperIdentityResult, JsonUnmarshallerContext> {
    private static GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller instance;

    public GetOpenIdTokenForDeveloperIdentityResult unmarshall(JsonUnmarshallerContext context) {
        GetOpenIdTokenForDeveloperIdentityResult getOpenIdTokenForDeveloperIdentityResult = new GetOpenIdTokenForDeveloperIdentityResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                getOpenIdTokenForDeveloperIdentityResult.setIdentityId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Token")) {
                getOpenIdTokenForDeveloperIdentityResult.setToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getOpenIdTokenForDeveloperIdentityResult;
    }

    public static GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller();
        }
        return instance;
    }
}
