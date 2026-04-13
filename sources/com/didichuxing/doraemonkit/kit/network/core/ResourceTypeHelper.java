package com.didichuxing.doraemonkit.kit.network.core;

import com.leedarson.serviceinterface.utils.IntentUtils;
import org.slf4j.e;

public class ResourceTypeHelper {
    private final MimeMatcher<ResourceType> mMimeMatcher;

    public ResourceTypeHelper() {
        MimeMatcher<ResourceType> mimeMatcher = new MimeMatcher<>();
        this.mMimeMatcher = mimeMatcher;
        mimeMatcher.addRule("text/css", ResourceType.STYLESHEET);
        mimeMatcher.addRule(IntentUtils.TYPE_IMAGE, ResourceType.IMAGE);
        mimeMatcher.addRule("application/x-javascript", ResourceType.SCRIPT);
        ResourceType resourceType = ResourceType.XHR;
        mimeMatcher.addRule("text/javascript", resourceType);
        mimeMatcher.addRule("application/json", resourceType);
        mimeMatcher.addRule("text/*", ResourceType.DOCUMENT);
        mimeMatcher.addRule(e.ANY_MARKER, ResourceType.OTHER);
    }

    public ResourceType determineResourceType(String contentType) {
        return this.mMimeMatcher.match(stripContentExtras(contentType));
    }

    public String stripContentExtras(String contentType) {
        int index = contentType.indexOf(59);
        return index >= 0 ? contentType.substring(0, index) : contentType;
    }
}
