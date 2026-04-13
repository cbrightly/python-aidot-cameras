package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.MappingRule;
import com.amazonaws.services.cognitoidentity.model.RulesConfigurationType;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

public class RulesConfigurationTypeJsonMarshaller {
    private static RulesConfigurationTypeJsonMarshaller instance;

    RulesConfigurationTypeJsonMarshaller() {
    }

    public void marshall(RulesConfigurationType rulesConfigurationType, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (rulesConfigurationType.getRules() != null) {
            List<MappingRule> rules = rulesConfigurationType.getRules();
            jsonWriter.name("Rules");
            jsonWriter.beginArray();
            for (MappingRule rulesItem : rules) {
                if (rulesItem != null) {
                    MappingRuleJsonMarshaller.getInstance().marshall(rulesItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    public static RulesConfigurationTypeJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new RulesConfigurationTypeJsonMarshaller();
        }
        return instance;
    }
}
