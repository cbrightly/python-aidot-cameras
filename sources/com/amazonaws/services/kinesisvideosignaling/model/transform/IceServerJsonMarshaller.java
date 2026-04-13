package com.amazonaws.services.kinesisvideosignaling.model.transform;

import com.amazonaws.services.kinesisvideosignaling.model.IceServer;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

public class IceServerJsonMarshaller {
    private static IceServerJsonMarshaller instance;

    IceServerJsonMarshaller() {
    }

    public void marshall(IceServer iceServer, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (iceServer.getUris() != null) {
            List<String> uris = iceServer.getUris();
            jsonWriter.name("Uris");
            jsonWriter.beginArray();
            for (String urisItem : uris) {
                if (urisItem != null) {
                    jsonWriter.value(urisItem);
                }
            }
            jsonWriter.endArray();
        }
        if (iceServer.getUsername() != null) {
            String username = iceServer.getUsername();
            jsonWriter.name("Username");
            jsonWriter.value(username);
        }
        if (iceServer.getPassword() != null) {
            String password = iceServer.getPassword();
            jsonWriter.name("Password");
            jsonWriter.value(password);
        }
        if (iceServer.getTtl() != null) {
            Integer ttl = iceServer.getTtl();
            jsonWriter.name("Ttl");
            jsonWriter.value((Number) ttl);
        }
        jsonWriter.endObject();
    }

    public static IceServerJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new IceServerJsonMarshaller();
        }
        return instance;
    }
}
