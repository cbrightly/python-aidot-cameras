package org.glassfish.grizzly.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;

public class StringDecoder extends AbstractTransformer<Buffer, String> {
    private static final Logger logger = Grizzly.logger(StringDecoder.class);
    protected Charset charset;
    protected final Attribute<Integer> lengthAttribute;
    protected byte[] stringTerminateBytes;

    public StringDecoder() {
        this((Charset) null, (String) null);
    }

    public StringDecoder(String stringTerminator) {
        this(Charset.forName("UTF-8"), stringTerminator);
    }

    public StringDecoder(Charset charset2) {
        this(charset2, (String) null);
    }

    public StringDecoder(Charset charset2, String stringTerminator) {
        Charset defaultCharset = charset2 != null ? charset2 : Charset.defaultCharset();
        this.charset = defaultCharset;
        if (stringTerminator != null) {
            try {
                this.stringTerminateBytes = stringTerminator.getBytes(defaultCharset.name());
            } catch (UnsupportedEncodingException e) {
            }
        }
        this.lengthAttribute = this.attributeBuilder.createAttribute("StringDecoder.StringSize");
    }

    public String getName() {
        return "StringDecoder";
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, String> transformImpl(AttributeStorage storage, Buffer input) {
        if (input != null) {
            return this.stringTerminateBytes == null ? parseWithLengthPrefix(storage, input) : parseWithTerminatingSeq(storage, input);
        }
        throw new TransformationException("Input could not be null");
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, String> parseWithLengthPrefix(AttributeStorage storage, Buffer input) {
        Integer stringSize = this.lengthAttribute.get(storage);
        Logger logger2 = logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            logger2.log(level, "StringDecoder decode stringSize={0} buffer={1} content={2}", new Object[]{stringSize, input, input.toStringContent()});
        }
        if (stringSize == null) {
            if (input.remaining() < 4) {
                return TransformationResult.createIncompletedResult(input);
            }
            stringSize = Integer.valueOf(input.getInt());
            this.lengthAttribute.set(storage, stringSize);
        }
        if (input.remaining() < stringSize.intValue()) {
            return TransformationResult.createIncompletedResult(input);
        }
        int tmpLimit = input.limit();
        input.limit(input.position() + stringSize.intValue());
        String stringMessage = input.toStringContent(this.charset);
        input.position(input.limit());
        input.limit(tmpLimit);
        return TransformationResult.createCompletedResult(stringMessage, input);
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, String> parseWithTerminatingSeq(AttributeStorage storage, Buffer input) {
        int terminationBytesLength = this.stringTerminateBytes.length;
        int checkIndex = 0;
        int termIndex = -1;
        Integer offsetInt = this.lengthAttribute.get(storage);
        int offset = 0;
        if (offsetInt != null) {
            offset = offsetInt.intValue();
        }
        int i = input.position() + offset;
        int lim = input.limit();
        while (true) {
            if (i < lim) {
                if (input.get(i) == this.stringTerminateBytes[checkIndex] && (checkIndex = checkIndex + 1) >= terminationBytesLength) {
                    termIndex = (i - terminationBytesLength) + 1;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        if (termIndex >= 0) {
            int tmpLimit = input.limit();
            input.limit(termIndex);
            String stringMessage = input.toStringContent(this.charset);
            input.limit(tmpLimit);
            input.position(termIndex + terminationBytesLength);
            return TransformationResult.createCompletedResult(stringMessage, input);
        }
        int offset2 = input.remaining() - terminationBytesLength;
        if (offset2 < 0) {
            offset2 = 0;
        }
        this.lengthAttribute.set(storage, Integer.valueOf(offset2));
        return TransformationResult.createIncompletedResult(input);
    }

    public void release(AttributeStorage storage) {
        this.lengthAttribute.remove(storage);
        super.release(storage);
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input != null && input.hasRemaining();
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset2) {
        this.charset = charset2;
    }
}
