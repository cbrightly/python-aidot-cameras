package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class TagStreamResult implements Serializable {
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
        if (obj == null || !(obj instanceof TagStreamResult)) {
            return false;
        }
        TagStreamResult tagStreamResult = (TagStreamResult) obj;
        return true;
    }
}
