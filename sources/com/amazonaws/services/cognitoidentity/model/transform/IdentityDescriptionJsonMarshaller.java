package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.IdentityDescription;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Date;
import java.util.List;

public class IdentityDescriptionJsonMarshaller {
    private static IdentityDescriptionJsonMarshaller instance;

    IdentityDescriptionJsonMarshaller() {
    }

    public void marshall(IdentityDescription identityDescription, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (identityDescription.getIdentityId() != null) {
            String identityId = identityDescription.getIdentityId();
            jsonWriter.name("IdentityId");
            jsonWriter.value(identityId);
        }
        if (identityDescription.getLogins() != null) {
            List<String> logins = identityDescription.getLogins();
            jsonWriter.name("Logins");
            jsonWriter.beginArray();
            for (String loginsItem : logins) {
                if (loginsItem != null) {
                    jsonWriter.value(loginsItem);
                }
            }
            jsonWriter.endArray();
        }
        if (identityDescription.getCreationDate() != null) {
            Date creationDate = identityDescription.getCreationDate();
            jsonWriter.name("CreationDate");
            jsonWriter.value(creationDate);
        }
        if (identityDescription.getLastModifiedDate() != null) {
            Date lastModifiedDate = identityDescription.getLastModifiedDate();
            jsonWriter.name("LastModifiedDate");
            jsonWriter.value(lastModifiedDate);
        }
        jsonWriter.endObject();
    }

    public static IdentityDescriptionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new IdentityDescriptionJsonMarshaller();
        }
        return instance;
    }
}
