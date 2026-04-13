package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.DeleteIdentitiesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DeleteIdentitiesResultJsonUnmarshaller implements Unmarshaller<DeleteIdentitiesResult, JsonUnmarshallerContext> {
    private static DeleteIdentitiesResultJsonUnmarshaller instance;

    public DeleteIdentitiesResult unmarshall(JsonUnmarshallerContext context) {
        DeleteIdentitiesResult deleteIdentitiesResult = new DeleteIdentitiesResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("UnprocessedIdentityIds")) {
                deleteIdentitiesResult.setUnprocessedIdentityIds(new ListUnmarshaller(UnprocessedIdentityIdJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return deleteIdentitiesResult;
    }

    public static DeleteIdentitiesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteIdentitiesResultJsonUnmarshaller();
        }
        return instance;
    }
}
