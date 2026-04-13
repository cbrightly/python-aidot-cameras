package com.didichuxing.doraemonkit.kit.network.core;

import com.didichuxing.doraemonkit.kit.network.stream.GunzippingOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.InflaterOutputStream;

public class RequestBodyHelper {
    private static final String DEFLATE_ENCODING = "deflate";
    private static final String GZIP_ENCODING = "gzip";
    private ByteArrayOutputStream mDeflatedOutput;

    public OutputStream createBodySink(String contentEncoding) {
        OutputStream deflatingOutput;
        ByteArrayOutputStream deflatedOutput = new ByteArrayOutputStream();
        if ("gzip".equals(contentEncoding)) {
            deflatingOutput = GunzippingOutputStream.create(deflatedOutput);
        } else if (DEFLATE_ENCODING.equals(contentEncoding)) {
            deflatingOutput = new InflaterOutputStream(deflatedOutput);
        } else {
            deflatingOutput = deflatedOutput;
        }
        this.mDeflatedOutput = deflatedOutput;
        return deflatingOutput;
    }

    public byte[] getDisplayBody() {
        throwIfNoBody();
        return this.mDeflatedOutput.toByteArray();
    }

    public boolean hasBody() {
        return this.mDeflatedOutput != null;
    }

    private void throwIfNoBody() {
        if (!hasBody()) {
            throw new IllegalStateException("No body found; has createBodySink been called?");
        }
    }
}
