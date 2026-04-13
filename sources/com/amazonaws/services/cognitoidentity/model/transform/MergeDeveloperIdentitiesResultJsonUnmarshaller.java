package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.MergeDeveloperIdentitiesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class MergeDeveloperIdentitiesResultJsonUnmarshaller implements Unmarshaller<MergeDeveloperIdentitiesResult, JsonUnmarshallerContext> {
    private static MergeDeveloperIdentitiesResultJsonUnmarshaller instance;

    public MergeDeveloperIdentitiesResult unmarshall(JsonUnmarshallerContext context) {
        MergeDeveloperIdentitiesResult mergeDeveloperIdentitiesResult = new MergeDeveloperIdentitiesResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("IdentityId")) {
                mergeDeveloperIdentitiesResult.setIdentityId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return mergeDeveloperIdentitiesResult;
    }

    public static MergeDeveloperIdentitiesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MergeDeveloperIdentitiesResultJsonUnmarshaller();
        }
        return instance;
    }
}
