package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.RoleMapping;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class RoleMappingJsonUnmarshaller implements Unmarshaller<RoleMapping, JsonUnmarshallerContext> {
    private static RoleMappingJsonUnmarshaller instance;

    RoleMappingJsonUnmarshaller() {
    }

    public RoleMapping unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        RoleMapping roleMapping = new RoleMapping();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Type")) {
                roleMapping.setType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AmbiguousRoleResolution")) {
                roleMapping.setAmbiguousRoleResolution(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("RulesConfiguration")) {
                roleMapping.setRulesConfiguration(RulesConfigurationTypeJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return roleMapping;
    }

    public static RoleMappingJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RoleMappingJsonUnmarshaller();
        }
        return instance;
    }
}
