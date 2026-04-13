package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.RulesConfigurationType;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class RulesConfigurationTypeJsonUnmarshaller implements Unmarshaller<RulesConfigurationType, JsonUnmarshallerContext> {
    private static RulesConfigurationTypeJsonUnmarshaller instance;

    RulesConfigurationTypeJsonUnmarshaller() {
    }

    public RulesConfigurationType unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        RulesConfigurationType rulesConfigurationType = new RulesConfigurationType();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Rules")) {
                rulesConfigurationType.setRules(new ListUnmarshaller(MappingRuleJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return rulesConfigurationType;
    }

    public static RulesConfigurationTypeJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RulesConfigurationTypeJsonUnmarshaller();
        }
        return instance;
    }
}
