package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.securitytoken.model.InvalidAuthorizationMessageException;
import com.amazonaws.transform.StandardErrorUnmarshaller;
import org.w3c.dom.Node;

public class InvalidAuthorizationMessageExceptionUnmarshaller extends StandardErrorUnmarshaller {
    public InvalidAuthorizationMessageExceptionUnmarshaller() {
        super(InvalidAuthorizationMessageException.class);
    }

    public AmazonServiceException unmarshall(Node node) {
        String errorCode = parseErrorCode(node);
        if (errorCode == null || !errorCode.equals("InvalidAuthorizationMessageException")) {
            return null;
        }
        return (InvalidAuthorizationMessageException) super.unmarshall(node);
    }
}
