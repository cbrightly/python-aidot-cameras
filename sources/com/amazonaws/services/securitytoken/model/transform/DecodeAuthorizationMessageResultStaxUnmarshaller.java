package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.services.securitytoken.model.DecodeAuthorizationMessageResult;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

public class DecodeAuthorizationMessageResultStaxUnmarshaller implements Unmarshaller<DecodeAuthorizationMessageResult, StaxUnmarshallerContext> {
    private static DecodeAuthorizationMessageResultStaxUnmarshaller instance;

    public DecodeAuthorizationMessageResult unmarshall(StaxUnmarshallerContext context) {
        DecodeAuthorizationMessageResult decodeAuthorizationMessageResult = new DecodeAuthorizationMessageResult();
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
                } else if (context.testExpression("DecodedMessage", targetDepth)) {
                    decodeAuthorizationMessageResult.setDecodedMessage(SimpleTypeStaxUnmarshallers.StringStaxUnmarshaller.getInstance().unmarshall(context));
                }
            } else {
                break;
            }
        }
        return decodeAuthorizationMessageResult;
    }

    public static DecodeAuthorizationMessageResultStaxUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DecodeAuthorizationMessageResultStaxUnmarshaller();
        }
        return instance;
    }
}
