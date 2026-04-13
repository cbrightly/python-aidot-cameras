package com.amazonaws.services.securitytoken.model.transform;

import com.amazonaws.Request;
import com.amazonaws.services.securitytoken.model.Tag;
import com.amazonaws.util.StringUtils;

public class TagStaxMarshaller {
    private static TagStaxMarshaller instance;

    TagStaxMarshaller() {
    }

    public void marshall(Tag _tag, Request<?> request, String _prefix) {
        if (_tag.getKey() != null) {
            request.addParameter(_prefix + "Key", StringUtils.fromString(_tag.getKey()));
        }
        if (_tag.getValue() != null) {
            request.addParameter(_prefix + "Value", StringUtils.fromString(_tag.getValue()));
        }
    }

    public static TagStaxMarshaller getInstance() {
        if (instance == null) {
            instance = new TagStaxMarshaller();
        }
        return instance;
    }
}
