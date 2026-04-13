package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class DeleteSignalingChannelResult implements Serializable {
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
        if (obj == null || !(obj instanceof DeleteSignalingChannelResult)) {
            return false;
        }
        DeleteSignalingChannelResult deleteSignalingChannelResult = (DeleteSignalingChannelResult) obj;
        return true;
    }
}
