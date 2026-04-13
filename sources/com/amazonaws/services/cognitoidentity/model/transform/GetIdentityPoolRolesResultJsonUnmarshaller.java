package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.GetIdentityPoolRolesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class GetIdentityPoolRolesResultJsonUnmarshaller implements Unmarshaller<GetIdentityPoolRolesResult, JsonUnmarshallerContext> {
    private static GetIdentityPoolRolesResultJsonUnmarshaller instance;

    public GetIdentityPoolRolesResult unmarshall(JsonUnmarshallerContext context) {
        GetIdentityPoolRolesResult getIdentityPoolRolesResult = new GetIdentityPoolRolesResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPoolId")) {
                getIdentityPoolRolesResult.setIdentityPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Roles")) {
                getIdentityPoolRolesResult.setRoles(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("RoleMappings")) {
                getIdentityPoolRolesResult.setRoleMappings(new MapUnmarshaller(RoleMappingJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return getIdentityPoolRolesResult;
    }

    public static GetIdentityPoolRolesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetIdentityPoolRolesResultJsonUnmarshaller();
        }
        return instance;
    }
}
