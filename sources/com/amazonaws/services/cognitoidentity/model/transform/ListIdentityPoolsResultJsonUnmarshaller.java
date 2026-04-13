package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListIdentityPoolsResultJsonUnmarshaller implements Unmarshaller<ListIdentityPoolsResult, JsonUnmarshallerContext> {
    private static ListIdentityPoolsResultJsonUnmarshaller instance;

    public ListIdentityPoolsResult unmarshall(JsonUnmarshallerContext context) {
        ListIdentityPoolsResult listIdentityPoolsResult = new ListIdentityPoolsResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPools")) {
                listIdentityPoolsResult.setIdentityPools(new ListUnmarshaller(IdentityPoolShortDescriptionJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("NextToken")) {
                listIdentityPoolsResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listIdentityPoolsResult;
    }

    public static ListIdentityPoolsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListIdentityPoolsResultJsonUnmarshaller();
        }
        return instance;
    }
}
