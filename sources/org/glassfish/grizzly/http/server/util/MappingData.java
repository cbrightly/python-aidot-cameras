package org.glassfish.grizzly.http.server.util;

import org.glassfish.grizzly.http.util.DataChunk;

public class MappingData {
    private static final String CONTEXT_DESC = "context";
    public static final byte CONTEXT_ROOT = 1;
    public static final byte DEFAULT = 2;
    private static final String DEFAULT_DESC = "default";
    public static final byte EXACT = 4;
    private static final String EXACT_DESC = "exact";
    public static final byte EXTENSION = 8;
    private static final String EXTENSION_DESC = "extension";
    public static final byte PATH = 16;
    private static final String PATH_DESC = "path";
    public static final byte UNKNOWN = 32;
    private static final String UNKNOWN_DESC = "unknown";
    public Object context = null;
    public final DataChunk contextPath = DataChunk.newInstance();
    public String descriptorPath = null;
    public Object host = null;
    public boolean isDefaultContext = false;
    public boolean jspWildCard = false;
    public byte mappingType = 32;
    public String matchedPath = null;
    public final DataChunk pathInfo = DataChunk.newInstance();
    public final DataChunk redirectPath = DataChunk.newInstance();
    public final DataChunk requestPath = DataChunk.newInstance();
    public String servletName = null;
    public final DataChunk tmpMapperDC = DataChunk.newInstance();
    public Object wrapper = null;
    public final DataChunk wrapperPath = DataChunk.newInstance();

    public void recycle() {
        this.mappingType = 32;
        this.host = null;
        this.context = null;
        this.wrapper = null;
        this.servletName = null;
        this.pathInfo.recycle();
        this.requestPath.recycle();
        this.wrapperPath.recycle();
        this.contextPath.recycle();
        this.redirectPath.recycle();
        this.jspWildCard = false;
        this.isDefaultContext = false;
        this.descriptorPath = null;
        this.matchedPath = null;
    }

    public String toString() {
        return "host: " + this.host + "\ncontext: " + this.context + "\nwrapper: " + this.wrapper + "\nservletName: " + this.servletName + "\ncontextPath: " + this.contextPath + "\nrequestPath: " + this.requestPath + "\nwrapperPath: " + this.wrapperPath + "\npathInfo: " + this.pathInfo + "\nredirectPath: " + this.redirectPath + "\nmappingType: " + getMappingDescription() + "\ndescriptorPath: " + this.descriptorPath + "\nmatchedPath: " + this.matchedPath;
    }

    private String getMappingDescription() {
        switch (this.mappingType) {
            case 1:
                return CONTEXT_DESC;
            case 2:
                return DEFAULT_DESC;
            case 4:
                return EXACT_DESC;
            case 8:
                return EXTENSION_DESC;
            case 16:
                return PATH_DESC;
            default:
                return "unknown";
        }
    }
}
