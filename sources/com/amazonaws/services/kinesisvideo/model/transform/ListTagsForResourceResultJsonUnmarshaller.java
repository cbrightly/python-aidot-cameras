package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ListTagsForResourceResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListTagsForResourceResultJsonUnmarshaller implements Unmarshaller<ListTagsForResourceResult, JsonUnmarshallerContext> {
    private static ListTagsForResourceResultJsonUnmarshaller instance;

    public ListTagsForResourceResult unmarshall(JsonUnmarshallerContext context) {
        ListTagsForResourceResult listTagsForResourceResult = new ListTagsForResourceResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("NextToken")) {
                listTagsForResourceResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Tags")) {
                listTagsForResourceResult.setTags(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listTagsForResourceResult;
    }

    public static ListTagsForResourceResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListTagsForResourceResultJsonUnmarshaller();
        }
        return instance;
    }
}
