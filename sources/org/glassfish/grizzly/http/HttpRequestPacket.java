package org.glassfish.grizzly.http;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.attributes.DefaultAttributeBuilder;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpCodecUtils;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.http.util.RequestURIRef;

public abstract class HttpRequestPacket extends HttpHeader {
    private static final AttributeBuilder ATTR_BUILDER = new DefaultAttributeBuilder();
    public static final String READ_ONLY_ATTR_PREFIX = "@RoA.";
    protected final Map<String, Object> attributes = new HashMap();
    private final DataChunk authTypeC = DataChunk.newInstance();
    private Connection connection;
    private boolean hostHeaderParsed;
    protected final DataChunk localAddressC = DataChunk.newInstance();
    private String localHost;
    protected final DataChunk localNameC = DataChunk.newInstance();
    protected int localPort = -1;
    private final DataChunk methodC = DataChunk.newInstance();
    private final transient AttributeHolder notesHolder = ATTR_BUILDER.createUnsafeAttributeHolder();
    protected Method parsedMethod;
    private final DataChunk queryC = DataChunk.newInstance();
    protected final DataChunk remoteAddressC = DataChunk.newInstance();
    protected final DataChunk remoteHostC = DataChunk.newInstance();
    protected int remotePort = -1;
    private final DataChunk remoteUserC = DataChunk.newInstance();
    private final RequestURIRef requestURIRef = new RequestURIRef();
    private boolean requiresAcknowledgement;
    private HttpResponsePacket response;
    private final DataChunk serverNameC = DataChunk.newInstance();
    private int serverPort = -1;
    protected DataChunk unparsedHostC;

    public static Builder builder() {
        return new Builder();
    }

    protected HttpRequestPacket() {
        setMethod(Method.GET);
    }

    public void setConnection(Connection connection2) {
        this.connection = connection2;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public HttpResponsePacket getResponse() {
        return this.response;
    }

    public DataChunk getMethodDC() {
        this.parsedMethod = null;
        return this.methodC;
    }

    public Method getMethod() {
        Method method = this.parsedMethod;
        if (method != null) {
            return method;
        }
        Method valueOf = Method.valueOf(this.methodC);
        this.parsedMethod = valueOf;
        return valueOf;
    }

    public void setMethod(String method) {
        this.methodC.setString(method);
        this.parsedMethod = null;
    }

    public void setMethod(Method method) {
        this.methodC.setString(method.getMethodString());
        this.parsedMethod = method;
    }

    public RequestURIRef getRequestURIRef() {
        return this.requestURIRef;
    }

    public String getRequestURI() {
        return this.requestURIRef.getURI();
    }

    public void setRequestURI(String requestURI) {
        this.requestURIRef.setURI(requestURI);
    }

    public DataChunk getQueryStringDC() {
        return this.queryC;
    }

    public String getQueryString() {
        if (this.queryC.isNull()) {
            return null;
        }
        return this.queryC.toString();
    }

    public void setQueryString(String query) {
        this.queryC.setString(query);
    }

    /* access modifiers changed from: protected */
    public DataChunk serverNameRaw() {
        return this.serverNameC;
    }

    public DataChunk serverName() {
        parseHostHeader();
        return this.serverNameC;
    }

    public int getServerPort() {
        parseHostHeader();
        return this.serverPort;
    }

    public void setServerPort(int serverPort2) {
        this.serverPort = serverPort2;
    }

    public DataChunk remoteAddr() {
        if (this.remoteAddressC.isNull()) {
            this.remoteAddressC.setString(((InetSocketAddress) this.connection.getPeerAddress()).getAddress().getHostAddress());
        }
        return this.remoteAddressC;
    }

    public String getRemoteAddress() {
        return remoteAddr().toString();
    }

    public DataChunk remoteHost() {
        if (this.remoteHostC.isNull()) {
            String remoteHost = null;
            InetAddress inetAddr = ((InetSocketAddress) this.connection.getPeerAddress()).getAddress();
            if (inetAddr != null) {
                remoteHost = inetAddr.getHostName();
            }
            if (remoteHost == null) {
                if (!this.remoteAddressC.isNull()) {
                    remoteHost = this.remoteAddressC.toString();
                } else {
                    this.remoteHostC.recycle();
                }
            }
            this.remoteHostC.setString(remoteHost);
        }
        return this.remoteHostC;
    }

    public String getRemoteHost() {
        return remoteHost().toString();
    }

    /* access modifiers changed from: protected */
    public void requiresAcknowledgement(boolean requiresAcknowledgement2) {
        this.requiresAcknowledgement = requiresAcknowledgement2;
    }

    public boolean requiresAcknowledgement() {
        return this.requiresAcknowledgement;
    }

    public DataChunk localName() {
        if (this.localNameC.isNull()) {
            this.localNameC.setString(((InetSocketAddress) this.connection.getLocalAddress()).getAddress().getHostName());
        }
        return this.localNameC;
    }

    public String getLocalName() {
        return localName().toString();
    }

    public DataChunk localAddr() {
        if (this.localAddressC.isNull()) {
            this.localAddressC.setString(((InetSocketAddress) this.connection.getLocalAddress()).getAddress().getHostAddress());
        }
        return this.localAddressC;
    }

    public String getLocalAddress() {
        return localAddr().toString();
    }

    public int getRemotePort() {
        if (this.remotePort == -1) {
            this.remotePort = ((InetSocketAddress) this.connection.getPeerAddress()).getPort();
        }
        return this.remotePort;
    }

    public void setRemotePort(int port) {
        this.remotePort = port;
    }

    public int getLocalPort() {
        if (this.localPort == -1) {
            this.localPort = ((InetSocketAddress) this.connection.getLocalAddress()).getPort();
        }
        return this.localPort;
    }

    public void setLocalPort(int port) {
        this.localPort = port;
    }

    public String getLocalHost() {
        return this.localHost;
    }

    public void setLocalHost(String host) {
        this.localHost = host;
    }

    public DataChunk authType() {
        return this.authTypeC;
    }

    public DataChunk remoteUser() {
        return this.remoteUserC;
    }

    public static <E> Note<E> createNote(String name) {
        return new Note<>(ATTR_BUILDER.createAttribute(name));
    }

    public <E> E getNote(Note<E> note) {
        return note.attribute.get(this.notesHolder);
    }

    public Set<String> getNoteNames() {
        return this.notesHolder.getAttributeNames();
    }

    public <E> E removeNote(Note<E> note) {
        return note.attribute.remove(this.notesHolder);
    }

    public <E> void setNote(Note<E> note, E value) {
        note.attribute.set(this.notesHolder, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Set<String> getAttributeNames() {
        Set<String> attrNames = new HashSet<>(this.attributes.size());
        for (String name : this.attributes.keySet()) {
            if (name == null || !name.startsWith(READ_ONLY_ATTR_PREFIX)) {
                attrNames.add(name);
            }
        }
        return Collections.unmodifiableSet(attrNames);
    }

    public void setAttribute(String name, Object value) {
        Object oldValue = this.attributes.put(name, value);
        if (oldValue != null && name != null && name.startsWith(READ_ONLY_ATTR_PREFIX)) {
            this.attributes.put(name, oldValue);
        }
    }

    public void removeAttribute(String name) {
        if (name == null || !name.startsWith(READ_ONLY_ATTR_PREFIX)) {
            this.attributes.remove(name);
        }
    }

    public boolean isHeadRequest() {
        return Method.HEAD.equals(getMethod());
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this.requestURIRef.recycle();
        this.queryC.recycle();
        this.methodC.recycle();
        this.parsedMethod = null;
        this.hostHeaderParsed = false;
        this.unparsedHostC = null;
        this.remoteAddressC.recycle();
        this.remoteHostC.recycle();
        this.localAddressC.recycle();
        this.localNameC.recycle();
        this.serverNameC.recycle();
        this.authTypeC.recycle();
        this.remoteUserC.recycle();
        this.attributes.clear();
        this.requiresAcknowledgement = false;
        this.remotePort = -1;
        this.localPort = -1;
        this.serverPort = -1;
        this.connection = null;
        this.localHost = null;
        this.response = null;
        super.reset();
    }

    public final boolean isRequest() {
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("HttpRequestPacket (\n   method=");
        sb.append(getMethod());
        sb.append("\n   url=");
        sb.append(getRequestURI());
        sb.append("\n   query=");
        sb.append(getQueryString());
        sb.append("\n   protocol=");
        sb.append(getProtocol().getProtocolString());
        sb.append("\n   content-length=");
        sb.append(getContentLength());
        sb.append("\n   headers=[");
        MimeHeaders headersLocal = getHeaders();
        for (String name : headersLocal.names()) {
            for (String value : headersLocal.values(name)) {
                sb.append("\n      ");
                sb.append(name);
                sb.append('=');
                sb.append(value);
            }
        }
        sb.append("]\n)");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void setResponse(HttpResponsePacket response2) {
        this.response = response2;
    }

    private void parseHostHeader() {
        if (!this.hostHeaderParsed) {
            doParseHostHeader();
            this.hostHeaderParsed = true;
        }
    }

    /* access modifiers changed from: protected */
    public void doParseHostHeader() {
        HttpCodecUtils.parseHost(this.unparsedHostC, this.serverNameC, this);
    }

    public static class Builder extends HttpHeader.Builder<Builder> {
        protected String host;
        protected Method method;
        protected String methodString;
        protected String queryString;
        protected String uri;

        public Builder method(Method method2) {
            this.method = method2;
            this.methodString = null;
            return this;
        }

        public Builder method(String method2) {
            this.methodString = method2;
            this.method = null;
            return this;
        }

        public Builder uri(String uri2) {
            this.uri = uri2;
            return this;
        }

        public Builder host(String host2) {
            this.host = host2;
            return this;
        }

        public Builder query(String queryString2) {
            this.queryString = queryString2;
            return this;
        }

        public final HttpRequestPacket build() {
            HttpRequestPacket packet = (HttpRequestPacket) super.build();
            Method method2 = this.method;
            if (method2 != null) {
                packet.setMethod(method2);
            }
            String str = this.methodString;
            if (str != null) {
                packet.setMethod(str);
            }
            String str2 = this.uri;
            if (str2 != null) {
                packet.setRequestURI(str2);
            }
            String str3 = this.queryString;
            if (str3 != null) {
                packet.setQueryString(str3);
            }
            String str4 = this.host;
            if (str4 != null) {
                packet.addHeader(Header.Host, str4);
            }
            return packet;
        }

        public void reset() {
            super.reset();
            this.method = null;
            this.uri = null;
            this.queryString = null;
        }

        /* access modifiers changed from: protected */
        public HttpHeader create() {
            return HttpRequestPacketImpl.create();
        }
    }
}
