package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.ListIdentitiesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListIdentitiesResultJsonUnmarshaller implements Unmarshaller<ListIdentitiesResult, JsonUnmarshallerContext> {
    private static ListIdentitiesResultJsonUnmarshaller instance;

    public ListIdentitiesResult unmarshall(JsonUnmarshallerContext context) {
        ListIdentitiesResult listIdentitiesResult = new ListIdentitiesResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPoolId")) {
                listIdentitiesResult.setIdentityPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Identities")) {
                listIdentitiesResult.setIdentities(new ListUnmarshaller(IdentityDescriptionJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("NextToken")) {
                listIdentitiesResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listIdentitiesResult;
    }

    public static ListIdentitiesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListIdentitiesResultJsonUnmarshaller();
        }
        return instance;
    }
}
