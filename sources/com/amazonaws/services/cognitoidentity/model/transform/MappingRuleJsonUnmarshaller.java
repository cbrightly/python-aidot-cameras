package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.MappingRule;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class MappingRuleJsonUnmarshaller implements Unmarshaller<MappingRule, JsonUnmarshallerContext> {
    private static MappingRuleJsonUnmarshaller instance;

    MappingRuleJsonUnmarshaller() {
    }

    public MappingRule unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        MappingRule mappingRule = new MappingRule();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Claim")) {
                mappingRule.setClaim(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("MatchType")) {
                mappingRule.setMatchType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Value")) {
                mappingRule.setValue(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("RoleARN")) {
                mappingRule.setRoleARN(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return mappingRule;
    }

    public static MappingRuleJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MappingRuleJsonUnmarshaller();
        }
        return instance;
    }
}
