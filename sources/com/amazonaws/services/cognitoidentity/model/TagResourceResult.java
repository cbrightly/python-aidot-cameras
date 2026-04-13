package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class TagResourceResult implements Serializable {
    public String toString() {
        return "{" + "}";
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TagResourceResult)) {
            return false;
        }
        TagResourceResult tagResourceResult = (TagResourceResult) obj;
        return true;
    }
}
