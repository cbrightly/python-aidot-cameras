package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.utils.conditions.Condition;

public final class TransformerInput extends BufferedInput {
    protected final AttributeStorage attributeStorage;
    /* access modifiers changed from: private */
    public final Attribute<CompositeBuffer> inputBufferAttr;
    protected final MemoryManager memoryManager;
    protected final Transformer<Buffer, Buffer> transformer;
    protected final Input underlyingInput;

    public TransformerInput(Transformer<Buffer, Buffer> transformer2, Input underlyingInput2, Connection connection) {
        this(transformer2, underlyingInput2, connection.getMemoryManager(), connection);
    }

    public TransformerInput(Transformer<Buffer, Buffer> transformer2, Input underlyingInput2, MemoryManager memoryManager2, AttributeStorage attributeStorage2) {
        this.transformer = transformer2;
        this.underlyingInput = underlyingInput2;
        this.memoryManager = memoryManager2;
        this.attributeStorage = attributeStorage2;
        AttributeBuilder attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
        this.inputBufferAttr = attributeBuilder.createAttribute("TransformerInput-" + transformer2.getName());
    }

    /* access modifiers changed from: protected */
    public void onOpenInputSource() {
        this.underlyingInput.notifyCondition(new TransformerCondition(), new TransformerCompletionHandler());
    }

    /* access modifiers changed from: protected */
    public void onCloseInputSource() {
    }

    public final class TransformerCompletionHandler extends EmptyCompletionHandler<Integer> {
        public TransformerCompletionHandler() {
        }

        public void failed(Throwable throwable) {
            TransformerInput transformerInput = TransformerInput.this;
            transformerInput.notifyFailure(transformerInput.completionHandler, throwable);
            TransformerInput.this.future.failure(throwable);
        }
    }

    public final class TransformerCondition implements Condition {
        public TransformerCondition() {
        }

        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean check() {
            /*
                r11 = this;
                org.glassfish.grizzly.streams.TransformerInput r0 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.Attribute r0 = r0.inputBufferAttr     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r1 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.AttributeStorage r1 = r1.attributeStorage     // Catch:{ IOException -> 0x00f6 }
                java.lang.Object r0 = r0.get((org.glassfish.grizzly.attributes.AttributeStorage) r1)     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.memory.CompositeBuffer r0 = (org.glassfish.grizzly.memory.CompositeBuffer) r0     // Catch:{ IOException -> 0x00f6 }
                r1 = r0
                r2 = 1
                r3 = 0
                if (r0 == 0) goto L_0x0017
                r4 = r2
                goto L_0x0018
            L_0x0017:
                r4 = r3
            L_0x0018:
                org.glassfish.grizzly.streams.TransformerInput r5 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.Input r5 = r5.underlyingInput     // Catch:{ IOException -> 0x00f6 }
                boolean r5 = r5.isBuffered()     // Catch:{ IOException -> 0x00f6 }
                if (r5 == 0) goto L_0x002b
                org.glassfish.grizzly.streams.TransformerInput r5 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.Input r5 = r5.underlyingInput     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.Buffer r5 = r5.takeBuffer()     // Catch:{ IOException -> 0x00f6 }
                goto L_0x0050
            L_0x002b:
                org.glassfish.grizzly.streams.TransformerInput r5 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.Input r5 = r5.underlyingInput     // Catch:{ IOException -> 0x00f6 }
                int r5 = r5.size()     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r6 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.memory.MemoryManager r6 = r6.memoryManager     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.Buffer r6 = r6.allocate(r5)     // Catch:{ IOException -> 0x00f6 }
            L_0x003b:
                int r7 = r5 + -1
                if (r5 < 0) goto L_0x004c
                org.glassfish.grizzly.streams.TransformerInput r5 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.Input r5 = r5.underlyingInput     // Catch:{ IOException -> 0x00f6 }
                byte r5 = r5.read()     // Catch:{ IOException -> 0x00f6 }
                r6.put((byte) r5)     // Catch:{ IOException -> 0x00f6 }
                r5 = r7
                goto L_0x003b
            L_0x004c:
                r6.flip()     // Catch:{ IOException -> 0x00f6 }
                r5 = r6
            L_0x0050:
                if (r4 == 0) goto L_0x0056
                r0.append(r5)     // Catch:{ IOException -> 0x00f6 }
                goto L_0x0057
            L_0x0056:
                r1 = r5
            L_0x0057:
                boolean r6 = r1.hasRemaining()     // Catch:{ IOException -> 0x00f6 }
                if (r6 == 0) goto L_0x00f5
                org.glassfish.grizzly.streams.TransformerInput r6 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.Transformer<org.glassfish.grizzly.Buffer, org.glassfish.grizzly.Buffer> r7 = r6.transformer     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.AttributeStorage r6 = r6.attributeStorage     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.TransformationResult r6 = r7.transform(r6, r1)     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.TransformationResult$Status r7 = r6.getStatus()     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.TransformationResult$Status r8 = org.glassfish.grizzly.TransformationResult.Status.COMPLETE     // Catch:{ IOException -> 0x00f6 }
                if (r7 != r8) goto L_0x00ae
                java.lang.Object r8 = r6.getMessage()     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.Buffer r8 = (org.glassfish.grizzly.Buffer) r8     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r9 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock r9 = r9.lock     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r9 = r9.writeLock()     // Catch:{ IOException -> 0x00f6 }
                r9.lock()     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r9 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ all -> 0x00a1 }
                r9.append(r8)     // Catch:{ all -> 0x00a1 }
                org.glassfish.grizzly.streams.TransformerInput r9 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ all -> 0x00a1 }
                boolean r10 = r9.isCompletionHandlerRegistered     // Catch:{ all -> 0x00a1 }
                if (r10 != 0) goto L_0x0096
                java.util.concurrent.locks.ReentrantReadWriteLock r3 = r9.lock     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r3.writeLock()     // Catch:{ IOException -> 0x00f6 }
                r3.unlock()     // Catch:{ IOException -> 0x00f6 }
                return r2
            L_0x0096:
                java.util.concurrent.locks.ReentrantReadWriteLock r9 = r9.lock     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r9 = r9.writeLock()     // Catch:{ IOException -> 0x00f6 }
                r9.unlock()     // Catch:{ IOException -> 0x00f6 }
                goto L_0x00e9
            L_0x00a1:
                r2 = move-exception
                org.glassfish.grizzly.streams.TransformerInput r3 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock r3 = r3.lock     // Catch:{ IOException -> 0x00f6 }
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r3.writeLock()     // Catch:{ IOException -> 0x00f6 }
                r3.unlock()     // Catch:{ IOException -> 0x00f6 }
                throw r2     // Catch:{ IOException -> 0x00f6 }
            L_0x00ae:
                org.glassfish.grizzly.TransformationResult$Status r8 = org.glassfish.grizzly.TransformationResult.Status.INCOMPLETE     // Catch:{ IOException -> 0x00f6 }
                if (r7 != r8) goto L_0x00e5
                if (r4 != 0) goto L_0x00e4
                boolean r2 = r1.isComposite()     // Catch:{ IOException -> 0x00f6 }
                if (r2 == 0) goto L_0x00cb
                org.glassfish.grizzly.streams.TransformerInput r2 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.Attribute r2 = r2.inputBufferAttr     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r8 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.AttributeStorage r8 = r8.attributeStorage     // Catch:{ IOException -> 0x00f6 }
                r9 = r1
                org.glassfish.grizzly.memory.CompositeBuffer r9 = (org.glassfish.grizzly.memory.CompositeBuffer) r9     // Catch:{ IOException -> 0x00f6 }
                r2.set((org.glassfish.grizzly.attributes.AttributeStorage) r8, r9)     // Catch:{ IOException -> 0x00f6 }
                goto L_0x00e4
            L_0x00cb:
                org.glassfish.grizzly.streams.TransformerInput r2 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.memory.MemoryManager r2 = r2.memoryManager     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.memory.CompositeBuffer r2 = org.glassfish.grizzly.memory.CompositeBuffer.newBuffer(r2)     // Catch:{ IOException -> 0x00f6 }
                r0 = r2
                r0.append(r1)     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r2 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.Attribute r2 = r2.inputBufferAttr     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.streams.TransformerInput r8 = org.glassfish.grizzly.streams.TransformerInput.this     // Catch:{ IOException -> 0x00f6 }
                org.glassfish.grizzly.attributes.AttributeStorage r8 = r8.attributeStorage     // Catch:{ IOException -> 0x00f6 }
                r2.set((org.glassfish.grizzly.attributes.AttributeStorage) r8, r0)     // Catch:{ IOException -> 0x00f6 }
            L_0x00e4:
                return r3
            L_0x00e5:
                org.glassfish.grizzly.TransformationResult$Status r8 = org.glassfish.grizzly.TransformationResult.Status.ERROR     // Catch:{ IOException -> 0x00f6 }
                if (r7 == r8) goto L_0x00eb
            L_0x00e9:
                goto L_0x0057
            L_0x00eb:
                org.glassfish.grizzly.TransformationException r2 = new org.glassfish.grizzly.TransformationException     // Catch:{ IOException -> 0x00f6 }
                java.lang.String r3 = r6.getErrorDescription()     // Catch:{ IOException -> 0x00f6 }
                r2.<init>((java.lang.String) r3)     // Catch:{ IOException -> 0x00f6 }
                throw r2     // Catch:{ IOException -> 0x00f6 }
            L_0x00f5:
                return r3
            L_0x00f6:
                r0 = move-exception
                org.glassfish.grizzly.TransformationException r1 = new org.glassfish.grizzly.TransformationException
                r1.<init>((java.lang.Throwable) r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.streams.TransformerInput.TransformerCondition.check():boolean");
        }
    }
}
