package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.OutputSink;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;

public class HttpContext implements AttributeStorage {
    private static final Attribute<HttpContext> HTTP_CONTEXT_ATTR = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(HttpContext.class.getName());
    private final Closeable closeable;
    private final AttributeStorage contextStorage;
    private final OutputSink outputSink;
    private final HttpRequestPacket request;

    protected HttpContext(AttributeStorage attributeStorage, OutputSink outputSink2, Closeable closeable2, HttpRequestPacket request2) {
        this.contextStorage = attributeStorage;
        this.closeable = closeable2;
        this.outputSink = outputSink2;
        this.request = request2;
    }

    public HttpRequestPacket getRequest() {
        return this.request;
    }

    public HttpContext attach(FilterChainContext ctx) {
        HTTP_CONTEXT_ATTR.set((AttributeStorage) ctx, this);
        return this;
    }

    public final AttributeHolder getAttributes() {
        return this.contextStorage.getAttributes();
    }

    public AttributeStorage getContextStorage() {
        return this.contextStorage;
    }

    public OutputSink getOutputSink() {
        return this.outputSink;
    }

    public Closeable getCloseable() {
        return this.closeable;
    }

    public void close() {
        this.closeable.closeSilently();
    }

    public static HttpContext newInstance(AttributeStorage attributeStorage, OutputSink outputSink2, Closeable closeable2, HttpRequestPacket request2) {
        return new HttpContext(attributeStorage, outputSink2, closeable2, request2);
    }

    public static HttpContext get(FilterChainContext ctx) {
        return HTTP_CONTEXT_ATTR.get((AttributeStorage) ctx);
    }
}
