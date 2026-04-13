package org.glassfish.grizzly.ssl;

import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLEncoderTransformer extends AbstractTransformer<Buffer, Buffer> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int BUFFER_OVERFLOW_ERROR = 3;
    public static final int BUFFER_UNDERFLOW_ERROR = 2;
    private static final TransformationResult<Buffer, Buffer> HANDSHAKE_NOT_EXECUTED_RESULT = TransformationResult.createErrorResult(1, "Handshake was not executed");
    private static final Logger LOGGER = Grizzly.logger(SSLEncoderTransformer.class);
    public static final int NEED_HANDSHAKE_ERROR = 1;
    private final MemoryManager memoryManager;

    public SSLEncoderTransformer() {
        this(MemoryManager.DEFAULT_MEMORY_MANAGER);
    }

    public SSLEncoderTransformer(MemoryManager memoryManager2) {
        this.memoryManager = memoryManager2;
    }

    public String getName() {
        return SSLEncoderTransformer.class.getName();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage state, Buffer originalMessage) {
        TransformationResult<Buffer, Buffer> wrapAll;
        SSLEngine sslEngine = SSLUtils.getSSLEngine((Connection) state);
        if (sslEngine == null) {
            return HANDSHAKE_NOT_EXECUTED_RESULT;
        }
        synchronized (state) {
            wrapAll = wrapAll(sslEngine, originalMessage);
        }
        return wrapAll;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c0, code lost:
        if (r14 != javax.net.ssl.SSLEngineResult.Status.CLOSED) goto L_0x00cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c2, code lost:
        r4 = org.glassfish.grizzly.TransformationResult.createCompletedResult(org.glassfish.grizzly.memory.Buffers.EMPTY_BUFFER, r3);
        r0 = r11;
        r7 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00cf, code lost:
        if (r14 != javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d1, code lost:
        r0 = org.glassfish.grizzly.TransformationResult.createErrorResult(2, "Buffer underflow during wrap operation");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00db, code lost:
        if (r14 != javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00dd, code lost:
        r0 = org.glassfish.grizzly.TransformationResult.createErrorResult(3, "Buffer overflow during wrap operation");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e5, code lost:
        r0 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e7, code lost:
        r4 = r0;
        r0 = r11;
        r7 = r21;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.glassfish.grizzly.TransformationResult<org.glassfish.grizzly.Buffer, org.glassfish.grizzly.Buffer> wrapAll(javax.net.ssl.SSLEngine r24, org.glassfish.grizzly.Buffer r25) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = 0
            r0 = 0
            r5 = 0
            org.glassfish.grizzly.memory.ByteBufferArray r6 = r25.toByteBufferArray()
            r7 = 0
            r8 = 0
            r22 = r5
            r5 = r0
            r0 = r22
        L_0x0014:
            int r9 = r6.size()
            if (r8 >= r9) goto L_0x0105
            int r9 = r25.position()
            java.lang.Object[] r10 = r6.getArray()
            java.nio.ByteBuffer[] r10 = (java.nio.ByteBuffer[]) r10
            r10 = r10[r8]
            org.glassfish.grizzly.memory.MemoryManager r11 = r1.memoryManager
            javax.net.ssl.SSLSession r12 = r24.getSession()
            int r12 = r12.getPacketBufferSize()
            org.glassfish.grizzly.Buffer r11 = r11.allocate(r12)
            org.glassfish.grizzly.Buffer r11 = allowDispose(r11)
            java.nio.ByteBuffer r12 = r11.toByteBuffer()
            java.util.logging.Logger r0 = LOGGER     // Catch:{ SSLException -> 0x00f6 }
            java.util.logging.Level r13 = java.util.logging.Level.FINE     // Catch:{ SSLException -> 0x00f6 }
            boolean r14 = r0.isLoggable(r13)     // Catch:{ SSLException -> 0x00f6 }
            r17 = 1
            r15 = 3
            if (r14 == 0) goto L_0x005d
            java.lang.String r14 = "SSLEncoder engine: {0} input: {1} output: {2}"
            r19 = r4
            java.lang.Object[] r4 = new java.lang.Object[r15]     // Catch:{ SSLException -> 0x00f4 }
            r16 = 0
            r4[r16] = r2     // Catch:{ SSLException -> 0x00f4 }
            r4[r17] = r10     // Catch:{ SSLException -> 0x00f4 }
            r18 = 2
            r4[r18] = r12     // Catch:{ SSLException -> 0x00f4 }
            r0.log(r13, r14, r4)     // Catch:{ SSLException -> 0x00f4 }
            goto L_0x005f
        L_0x005d:
            r19 = r4
        L_0x005f:
            javax.net.ssl.SSLEngineResult r4 = org.glassfish.grizzly.ssl.SSLUtils.sslEngineWrap(r2, r10, r12)     // Catch:{ SSLException -> 0x00f4 }
            int r14 = r25.position()     // Catch:{ SSLException -> 0x00f4 }
            if (r9 != r14) goto L_0x0072
            r7 = 1
            int r14 = r4.bytesConsumed()     // Catch:{ SSLException -> 0x00f4 }
            int r14 = r14 + r9
            r3.position(r14)     // Catch:{ SSLException -> 0x00f4 }
        L_0x0072:
            javax.net.ssl.SSLEngineResult$Status r14 = r4.getStatus()     // Catch:{ SSLException -> 0x00f0 }
            boolean r20 = r0.isLoggable(r13)     // Catch:{ SSLException -> 0x00f0 }
            if (r20 == 0) goto L_0x0095
            java.lang.String r15 = "SSLEncoder done engine: {0} result: {1} input: {2} output: {3}"
            r21 = r7
            r7 = 4
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ SSLException -> 0x00ec }
            r16 = 0
            r7[r16] = r2     // Catch:{ SSLException -> 0x00ec }
            r7[r17] = r4     // Catch:{ SSLException -> 0x00ec }
            r16 = 2
            r7[r16] = r10     // Catch:{ SSLException -> 0x00ec }
            r16 = 3
            r7[r16] = r12     // Catch:{ SSLException -> 0x00ec }
            r0.log(r13, r15, r7)     // Catch:{ SSLException -> 0x00ec }
            goto L_0x0097
        L_0x0095:
            r21 = r7
        L_0x0097:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ SSLException -> 0x00ec }
            if (r14 != r0) goto L_0x00be
            int r0 = r4.bytesProduced()     // Catch:{ SSLException -> 0x00ec }
            r11.position(r0)     // Catch:{ SSLException -> 0x00ec }
            r11.trim()     // Catch:{ SSLException -> 0x00ec }
            org.glassfish.grizzly.memory.MemoryManager r0 = r1.memoryManager     // Catch:{ SSLException -> 0x00ec }
            org.glassfish.grizzly.Buffer r0 = org.glassfish.grizzly.memory.Buffers.appendBuffers(r0, r5, r11)     // Catch:{ SSLException -> 0x00ec }
            r5 = r0
            boolean r0 = r10.hasRemaining()
            if (r0 == 0) goto L_0x00b5
            int r8 = r8 + -1
        L_0x00b5:
            int r8 = r8 + 1
            r0 = r11
            r4 = r19
            r7 = r21
            goto L_0x0014
        L_0x00be:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ SSLException -> 0x00ec }
            if (r14 != r0) goto L_0x00cd
            org.glassfish.grizzly.Buffer r0 = org.glassfish.grizzly.memory.Buffers.EMPTY_BUFFER     // Catch:{ SSLException -> 0x00ec }
            org.glassfish.grizzly.TransformationResult r0 = org.glassfish.grizzly.TransformationResult.createCompletedResult(r0, r3)     // Catch:{ SSLException -> 0x00ec }
            r4 = r0
            r0 = r11
            r7 = r21
            goto L_0x0107
        L_0x00cd:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ SSLException -> 0x00ec }
            if (r14 != r0) goto L_0x00d9
            java.lang.String r0 = "Buffer underflow during wrap operation"
            r7 = 2
            org.glassfish.grizzly.TransformationResult r0 = org.glassfish.grizzly.TransformationResult.createErrorResult(r7, r0)     // Catch:{ SSLException -> 0x00ec }
            goto L_0x00e7
        L_0x00d9:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ SSLException -> 0x00ec }
            if (r14 != r0) goto L_0x00e5
            java.lang.String r0 = "Buffer overflow during wrap operation"
            r7 = 3
            org.glassfish.grizzly.TransformationResult r0 = org.glassfish.grizzly.TransformationResult.createErrorResult(r7, r0)     // Catch:{ SSLException -> 0x00ec }
            goto L_0x00e7
        L_0x00e5:
            r0 = r19
        L_0x00e7:
            r4 = r0
            r0 = r11
            r7 = r21
            goto L_0x0107
        L_0x00ec:
            r0 = move-exception
            r7 = r21
            goto L_0x00f9
        L_0x00f0:
            r0 = move-exception
            r21 = r7
            goto L_0x00f9
        L_0x00f4:
            r0 = move-exception
            goto L_0x00f9
        L_0x00f6:
            r0 = move-exception
            r19 = r4
        L_0x00f9:
            disposeBuffers(r11, r5)
            r6.restore()
            org.glassfish.grizzly.TransformationException r4 = new org.glassfish.grizzly.TransformationException
            r4.<init>((java.lang.Throwable) r0)
            throw r4
        L_0x0105:
            r19 = r4
        L_0x0107:
            boolean r8 = r25.hasRemaining()
            if (r8 != 0) goto L_0x0124
            if (r7 == 0) goto L_0x0112
            r6.restore()
        L_0x0112:
            r6.recycle()
            if (r4 == 0) goto L_0x011b
            disposeBuffers(r0, r5)
            return r4
        L_0x011b:
            org.glassfish.grizzly.Buffer r8 = allowDispose(r5)
            org.glassfish.grizzly.TransformationResult r8 = org.glassfish.grizzly.TransformationResult.createCompletedResult(r8, r3)
            return r8
        L_0x0124:
            java.lang.AssertionError r8 = new java.lang.AssertionError
            r8.<init>()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.ssl.SSLEncoderTransformer.wrapAll(javax.net.ssl.SSLEngine, org.glassfish.grizzly.Buffer):org.glassfish.grizzly.TransformationResult");
    }

    private static void disposeBuffers(Buffer currentBuffer, Buffer bigBuffer) {
        if (currentBuffer != null) {
            currentBuffer.dispose();
        }
        if (bigBuffer != null) {
            bigBuffer.allowBufferDispose(true);
            if (bigBuffer.isComposite()) {
                ((CompositeBuffer) bigBuffer).allowInternalBuffersDispose(true);
            }
            bigBuffer.dispose();
        }
    }

    private static Buffer allowDispose(Buffer buffer) {
        buffer.allowBufferDispose(true);
        if (buffer.isComposite()) {
            ((CompositeBuffer) buffer).allowInternalBuffersDispose(true);
        }
        return buffer;
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input != null && input.hasRemaining();
    }
}
