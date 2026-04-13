package com.didichuxing.doraemonkit.kit.network.okhttp;

import com.didichuxing.doraemonkit.kit.network.core.ResourceType;
import com.didichuxing.doraemonkit.kit.network.core.ResourceTypeHelper;

public class InterceptorUtil {
    public static boolean isImg(String contentType) {
        if ((contentType != null ? new ResourceTypeHelper().determineResourceType(contentType) : null) == ResourceType.IMAGE) {
            return true;
        }
        return false;
    }
}
