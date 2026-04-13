package com.amazonaws.services.kinesisvideo.model.transform;

import com.amazonaws.services.kinesisvideo.model.ListTagsForStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class ListTagsForStreamResultJsonUnmarshaller implements Unmarshaller<ListTagsForStreamResult, JsonUnmarshallerContext> {
    private static ListTagsForStreamResultJsonUnmarshaller instance;

    public ListTagsForStreamResult unmarshall(JsonUnmarshallerContext context) {
        ListTagsForStreamResult listTagsForStreamResult = new ListTagsForStreamResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("NextToken")) {
                listTagsForStreamResult.setNextToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Tags")) {
                listTagsForStreamResult.setTags(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listTagsForStreamResult;
    }

    public static ListTagsForStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ListTagsForStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
