package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class TransformerOutput extends BufferedOutput {
    protected final AttributeStorage attributeStorage;
    protected final MemoryManager memoryManager;
    private final Attribute<CompositeBuffer> outputBufferAttr;
    protected final Transformer<Buffer, Buffer> transformer;
    protected final Output underlyingOutput;

    public TransformerOutput(Transformer<Buffer, Buffer> transformer2, Output underlyingOutput2, Connection connection) {
        this(transformer2, underlyingOutput2, connection.getMemoryManager(), connection);
    }

    public TransformerOutput(Transformer<Buffer, Buffer> transformer2, Output underlyingOutput2, MemoryManager memoryManager2, AttributeStorage attributeStorage2) {
        this.transformer = transformer2;
        this.underlyingOutput = underlyingOutput2;
        this.memoryManager = memoryManager2;
        this.attributeStorage = attributeStorage2;
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.outputBufferAttr = attributeBuilder.createAttribute("TransformerOutput-" + transformer2.getName());
    }

    /* access modifiers changed from: protected */
    public GrizzlyFuture<Integer> flush0(Buffer buffer, CompletionHandler<Integer> completionHandler) {
        if (buffer == null) {
            return BufferedOutput.ZERO_READY_FUTURE;
        }
        CompositeBuffer savedBuffer = this.outputBufferAttr.get(this.attributeStorage);
        if (savedBuffer != null) {
            savedBuffer.append(buffer);
            buffer = savedBuffer;
        }
        do {
            TransformationResult<Buffer, Buffer> result = this.transformer.transform(this.attributeStorage, buffer);
            TransformationResult.Status status = result.getStatus();
            if (status == TransformationResult.Status.COMPLETE) {
                this.underlyingOutput.write(result.getMessage());
                this.transformer.release(this.attributeStorage);
            } else if (status == TransformationResult.Status.INCOMPLETE) {
                buffer.compact();
                if (!buffer.isComposite()) {
                    buffer = CompositeBuffer.newBuffer(this.memoryManager, buffer);
                }
                this.outputBufferAttr.set(this.attributeStorage, (CompositeBuffer) buffer);
                return ReadyFutureImpl.create((Throwable) new IllegalStateException("Can not flush data: Insufficient input data for transformer"));
            } else if (status == TransformationResult.Status.ERROR) {
                this.transformer.release(this.attributeStorage);
                throw new IOException("Transformation exception: " + result.getErrorDescription());
            }
        } while (buffer.hasRemaining());
        return this.underlyingOutput.flush(completionHandler);
    }

    /* access modifiers changed from: protected */
    public Buffer newBuffer(int size) {
        return this.memoryManager.allocate(size);
    }

    /* access modifiers changed from: protected */
    public Buffer reallocateBuffer(Buffer oldBuffer, int size) {
        return this.memoryManager.reallocate(oldBuffer, size);
    }

    /* access modifiers changed from: protected */
    public void onClosed() {
    }
}
