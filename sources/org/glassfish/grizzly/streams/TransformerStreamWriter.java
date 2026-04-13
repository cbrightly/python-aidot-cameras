package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Transformer;

public class TransformerStreamWriter extends AbstractStreamWriter {
    public TransformerStreamWriter(StreamWriter underlyingStream, Transformer<Buffer, Buffer> transformer) {
        super(underlyingStream.getConnection(), new TransformerOutput(transformer, new StreamOutput(underlyingStream), underlyingStream.getConnection()));
    }
}
