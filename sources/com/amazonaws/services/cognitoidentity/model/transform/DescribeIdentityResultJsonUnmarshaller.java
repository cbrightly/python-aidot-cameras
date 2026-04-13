package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.DescribeIdentityResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeIdentityResultJsonUnmarshaller implements Unmarshaller<DescribeIdentityResult, JsonUnmarshallerContext> {
    private static DescribeIdentityResultJsonUnmarshaller instance;

    public DescribeIdentityResult unmarshall(JsonUnmarshallerContext context) {
        DescribeIdentityResult describeIdentityResult = new DescribeIdentityResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                describeIdentityResult.setIdentityId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Logins")) {
                describeIdentityResult.setLogins(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("CreationDate")) {
                describeIdentityResult.setCreationDate(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LastModifiedDate")) {
                describeIdentityResult.setLastModifiedDate(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeIdentityResult;
    }

    public static DescribeIdentityResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeIdentityResultJsonUnmarshaller();
        }
        return instance;
    }
}
