package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.Tag;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class TagJsonUnmarshaller implements Unmarshaller<Tag, JsonUnmarshallerContext> {
    private static TagJsonUnmarshaller instance;

    TagJsonUnmarshaller() {
    }

    public Tag unmarshall(JsonUnmarshallerContext context) {
        AwsJsonReader reader = context.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        Tag tag = new Tag();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Key")) {
                tag.setKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Value")) {
                tag.setValue(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return tag;
    }

    public static TagJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TagJsonUnmarshaller();
        }
        return instance;
    }
}
