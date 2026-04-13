package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.MappingRule;
import com.amazonaws.util.json.AwsJsonWriter;

public class MappingRuleJsonMarshaller {
    private static MappingRuleJsonMarshaller instance;

    MappingRuleJsonMarshaller() {
    }

    public void marshall(MappingRule mappingRule, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (mappingRule.getClaim() != null) {
            String claim = mappingRule.getClaim();
            jsonWriter.name("Claim");
            jsonWriter.value(claim);
        }
        if (mappingRule.getMatchType() != null) {
            String matchType = mappingRule.getMatchType();
            jsonWriter.name("MatchType");
            jsonWriter.value(matchType);
        }
        if (mappingRule.getValue() != null) {
            String value = mappingRule.getValue();
            jsonWriter.name("Value");
            jsonWriter.value(value);
        }
        if (mappingRule.getRoleARN() != null) {
            String roleARN = mappingRule.getRoleARN();
            jsonWriter.name("RoleARN");
            jsonWriter.value(roleARN);
        }
        jsonWriter.endObject();
    }

    public static MappingRuleJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MappingRuleJsonMarshaller();
        }
        return instance;
    }
}
