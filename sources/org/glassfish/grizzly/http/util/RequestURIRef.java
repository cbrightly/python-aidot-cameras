package org.glassfish.grizzly.http.util;

import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.utils.Charsets;

public class RequestURIRef {
    private final DataChunk decodedRequestURIDC = DataChunk.newInstance();
    private Charset decodedURIEncoding;
    private Charset defaultURIEncoding = Charsets.UTF8_CHARSET;
    private boolean isDecoded;
    private final DataChunk originalRequestURIDC = DataChunk.newInstance();
    private byte[] preallocatedDecodedURIBuffer;
    private final DataChunk requestURIDC = new DataChunk() {
        public void notifyDirectUpdate() {
            if (this.type == DataChunk.Type.Buffer) {
                int start = getStart();
                int end = getEnd();
                byte[] bytes = new byte[(end - start)];
                Buffer currentBuffer = getBufferChunk().getBuffer();
                int pos = currentBuffer.position();
                int lim = currentBuffer.limit();
                Buffers.setPositionLimit(currentBuffer, start, end);
                currentBuffer.get(bytes);
                Buffers.setPositionLimit(currentBuffer, pos, lim);
                setBytes(bytes);
            }
        }
    };
    private boolean wasSlashAllowed = true;

    public void init(Buffer input, int start, int end) {
        this.originalRequestURIDC.setBuffer(input, start, end);
        this.requestURIDC.setBuffer(input, start, end);
    }

    public void init(byte[] input, int start, int end) {
        this.originalRequestURIDC.setBytes(input, start, end);
        this.requestURIDC.setBytes(input, start, end);
    }

    public void init(String requestUri) {
        this.originalRequestURIDC.setString(requestUri);
        this.requestURIDC.setString(requestUri);
    }

    public final DataChunk getOriginalRequestURIBC() {
        return this.originalRequestURIDC;
    }

    public final DataChunk getRequestURIBC() {
        return this.requestURIDC;
    }

    public final DataChunk getDecodedRequestURIBC() {
        return getDecodedRequestURIBC(this.wasSlashAllowed, this.defaultURIEncoding);
    }

    public DataChunk getDecodedRequestURIBC(boolean isSlashAllowed) {
        return getDecodedRequestURIBC(isSlashAllowed, this.defaultURIEncoding);
    }

    public DataChunk getDecodedRequestURIBC(boolean isSlashAllowed, Charset charset) {
        if (this.isDecoded && isSlashAllowed == this.wasSlashAllowed && charset == this.decodedURIEncoding) {
            return this.decodedRequestURIDC;
        }
        checkDecodedURICapacity(this.requestURIDC.getLength());
        this.decodedRequestURIDC.setBytes(this.preallocatedDecodedURIBuffer);
        HttpRequestURIDecoder.decode(this.requestURIDC, this.decodedRequestURIDC, isSlashAllowed, charset);
        this.isDecoded = true;
        this.wasSlashAllowed = isSlashAllowed;
        this.decodedURIEncoding = charset;
        return this.decodedRequestURIDC;
    }

    public String getURI() {
        return getURI((Charset) null);
    }

    public String getURI(Charset encoding) {
        return getRequestURIBC().toString(encoding);
    }

    public void setURI(String uri) {
        getRequestURIBC().setString(uri);
    }

    public final String getDecodedURI() {
        return getDecodedURI(this.wasSlashAllowed);
    }

    public final String getDecodedURI(boolean isSlashAllowed) {
        return getDecodedURI(isSlashAllowed, this.defaultURIEncoding);
    }

    public String getDecodedURI(boolean isSlashAllowed, Charset encoding) {
        getDecodedRequestURIBC(isSlashAllowed, encoding);
        return this.decodedRequestURIDC.toString();
    }

    public void setDecodedURI(String uri) {
        this.decodedRequestURIDC.setString(uri);
        this.isDecoded = true;
    }

    public boolean isDecoded() {
        return this.isDecoded;
    }

    public Charset getDefaultURIEncoding() {
        return this.defaultURIEncoding;
    }

    public void setDefaultURIEncoding(Charset defaultURIEncoding2) {
        this.defaultURIEncoding = defaultURIEncoding2;
    }

    public void recycle() {
        this.originalRequestURIDC.recycle();
        this.decodedRequestURIDC.recycle();
        this.requestURIDC.recycle();
        this.isDecoded = false;
        this.wasSlashAllowed = true;
        this.decodedURIEncoding = null;
        this.defaultURIEncoding = Charsets.UTF8_CHARSET;
    }

    private void checkDecodedURICapacity(int size) {
        byte[] bArr = this.preallocatedDecodedURIBuffer;
        if (bArr == null || bArr.length < size) {
            this.preallocatedDecodedURIBuffer = new byte[size];
        }
    }
}
