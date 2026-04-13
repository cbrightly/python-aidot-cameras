package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.Tag;
import com.amazonaws.util.json.AwsJsonWriter;

public class TagJsonMarshaller {
    private static TagJsonMarshaller instance;

    TagJsonMarshaller() {
    }

    public void marshall(Tag tag, AwsJsonWriter jsonWriter) {
        jsonWriter.beginObject();
        if (tag.getKey() != null) {
            String key = tag.getKey();
            jsonWriter.name("Key");
            jsonWriter.value(key);
        }
        if (tag.getValue() != null) {
            String value = tag.getValue();
            jsonWriter.name("Value");
            jsonWriter.value(value);
        }
        jsonWriter.endObject();
    }

    public static TagJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new TagJsonMarshaller();
        }
        return instance;
    }
}
