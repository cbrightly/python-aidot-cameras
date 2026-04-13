package org.glassfish.grizzly.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class StringEncoder extends AbstractTransformer<String, Buffer> {
    protected Charset charset;
    protected String stringTerminator;

    public StringEncoder() {
        this((String) null);
    }

    public StringEncoder(String stringTerminator2) {
        this((Charset) null, stringTerminator2);
    }

    public StringEncoder(Charset charset2) {
        this(charset2, (String) null);
    }

    public StringEncoder(Charset charset2, String stringTerminator2) {
        this.charset = charset2 != null ? charset2 : Charset.defaultCharset();
        this.stringTerminator = stringTerminator2;
    }

    public String getName() {
        return "StringEncoder";
    }

    /* access modifiers changed from: protected */
    public TransformationResult<String, Buffer> transformImpl(AttributeStorage storage, String input) {
        if (input != null) {
            try {
                if (this.stringTerminator != null) {
                    input = input + this.stringTerminator;
                }
                byte[] byteRepresentation = input.getBytes(this.charset.name());
                Buffer output = obtainMemoryManager(storage).allocate(byteRepresentation.length + 4);
                if (this.stringTerminator == null) {
                    output.putInt(byteRepresentation.length);
                }
                output.put(byteRepresentation);
                output.flip();
                output.allowBufferDispose(true);
                return TransformationResult.createCompletedResult(output, null);
            } catch (UnsupportedEncodingException e) {
                throw new TransformationException("Charset " + this.charset.name() + " is not supported", e);
            }
        } else {
            throw new TransformationException("Input could not be null");
        }
    }

    public boolean hasInputRemaining(AttributeStorage storage, String input) {
        return input != null;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset2) {
        this.charset = charset2;
    }
}
