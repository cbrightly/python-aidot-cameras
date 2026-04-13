package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.RoleMapping;
import com.amazonaws.services.cognitoidentity.model.RulesConfigurationType;
import com.amazonaws.util.json.AwsJsonWriter;

public class RoleMappingJsonMarshaller {
    private static RoleMappingJsonMarshaller instance;

    RoleMappingJsonMarshaller() {
    }

    public void marshall(RoleMapping roleMapping, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (roleMapping.getType() != null) {
            String type = roleMapping.getType();
            jsonWriter.name("Type");
            jsonWriter.value(type);
        }
        if (roleMapping.getAmbiguousRoleResolution() != null) {
            String ambiguousRoleResolution = roleMapping.getAmbiguousRoleResolution();
            jsonWriter.name("AmbiguousRoleResolution");
            jsonWriter.value(ambiguousRoleResolution);
        }
        if (roleMapping.getRulesConfiguration() != null) {
            RulesConfigurationType rulesConfiguration = roleMapping.getRulesConfiguration();
            jsonWriter.name("RulesConfiguration");
            RulesConfigurationTypeJsonMarshaller.getInstance().marshall(rulesConfiguration, jsonWriter);
        }
        jsonWriter.endObject();
    }

    public static RoleMappingJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new RoleMappingJsonMarshaller();
        }
        return instance;
    }
}
