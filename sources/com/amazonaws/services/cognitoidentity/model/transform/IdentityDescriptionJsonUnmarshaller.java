package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.IdentityDescription;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class IdentityDescriptionJsonUnmarshaller implements Unmarshaller<IdentityDescription, JsonUnmarshallerContext> {
    private static IdentityDescriptionJsonUnmarshaller instance;

    IdentityDescriptionJsonUnmarshaller() {
    }

    public IdentityDescription unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        IdentityDescription identityDescription = new IdentityDescription();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityId")) {
                identityDescription.setIdentityId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Logins")) {
                identityDescription.setLogins(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("CreationDate")) {
                identityDescription.setCreationDate(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("LastModifiedDate")) {
                identityDescription.setLastModifiedDate(SimpleTypeJsonUnmarshallers.DateJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return identityDescription;
    }

    public static IdentityDescriptionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new IdentityDescriptionJsonUnmarshaller();
        }
        return instance;
    }
}
