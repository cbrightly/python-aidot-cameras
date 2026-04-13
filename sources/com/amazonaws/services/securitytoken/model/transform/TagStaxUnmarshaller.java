package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.Tag;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class TagStaxUnmarshaller implements Unmarshaller<Tag, StaxUnmarshallerContext> {
    private static TagStaxUnmarshaller instance;

    TagStaxUnmarshaller() {
    }

    public Tag unmarshall(StaxUnmarshallerContext context) {
        Tag tag = new Tag();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;
        if (context.isStartOfDocument()) {
            targetDepth += 2;
        }
        while (true) {
            int xmlEvent = context.nextEvent();
            if (xmlEvent != 1) {
                if (xmlEvent != 2) {
                    if (xmlEvent == 3 && context.getCurrentDepth() < originalDepth) {
                        break;
                    }
                } else if (context.testExpression("Key", targetDepth)) {
                    tag.setKey(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                } else if (context.testExpression("Value", targetDepth)) {
                    tag.setValue(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return tag;
    }

    public static TagStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new TagStaxUnmarshaller();
        }
        return instance;
    }
}
