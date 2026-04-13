package org.glassfish.grizzly.ssl;

import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferWrapper;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.ssl.SSLConnectionContext;

public final class SSLUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean ANDROID_WORKAROUND_NEEDED;
    private static final byte APPLICATION_DATA_CONTENT_TYPE = 23;
    private static final byte CHANGE_CIPHER_SPECT_CONTENT_TYPE = 20;
    private static final SSLConnectionContext.Allocator HS_UNWRAP_ALLOCATOR = new SSLConnectionContext.Allocator() {
        public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            return SSLUtils.allocateOutputBuffer(newSize);
        }
    };
    private static final SSLConnectionContext.Allocator HS_WRAP_ALLOCATOR = new SSLConnectionContext.Allocator() {
        public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            return SSLUtils.allocateOutputBuffer(newSize);
        }
    };
    private static final int LOLLIPOP_VER = 21;
    private static final int MAX_MAJOR_VERSION = 3;
    private static final int MIN_VERSION = 768;
    private static final int SSL20_HELLO_VERSION = 2;
    private static final int SSLV3_RECORD_HEADER_SIZE = 5;
    private static final String SSL_CONNECTION_CTX_ATTR_NAME;
    static final Attribute<SSLConnectionContext> SSL_CTX_ATTR;
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<Buffer> SSL_OUTPUT_BUFFER_IDX = ThreadCache.obtainIndex(SSLBaseFilter.class.getName() + ".output-buffer-cache", Buffer.class, 4);

    static {
        boolean isNeedWorkAround = false;
        if ("android runtime".equalsIgnoreCase(System.getProperty("java.runtime.name"))) {
            try {
                isNeedWorkAround = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt((Object) null) >= 21;
            } catch (Throwable th) {
            }
        }
        ANDROID_WORKAROUND_NEEDED = isNeedWorkAround;
        String str = SSLUtils.class + ".ssl-connection-context";
        SSL_CONNECTION_CTX_ATTR_NAME = str;
        SSL_CTX_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(str);
    }

    public static SSLConnectionContext getSslConnectionContext(Connection connection) {
        return SSL_CTX_ATTR.get((AttributeStorage) connection);
    }

    public static SSLEngine getSSLEngine(Connection connection) {
        SSLConnectionContext sslCtx = getSslConnectionContext(connection);
        if (sslCtx == null) {
            return null;
        }
        return sslCtx.getSslEngine();
    }

    public static void setSSLEngine(Connection connection, SSLEngine sslEngine) {
        SSLConnectionContext ctx = getSslConnectionContext(connection);
        if (ctx == null) {
            ctx = new SSLConnectionContext(connection);
            SSL_CTX_ATTR.set((AttributeStorage) connection, ctx);
        }
        ctx.configure(sslEngine);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r5v4, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getSSLPacketSize(org.glassfish.grizzly.Buffer r14) {
        /*
            int r0 = r14.remaining()
            r1 = 5
            if (r0 >= r1) goto L_0x0009
            r0 = -1
            return r0
        L_0x0009:
            boolean r0 = r14.hasArray()
            if (r0 == 0) goto L_0x002f
            byte[] r0 = r14.array()
            int r2 = r14.arrayOffset()
            int r3 = r14.position()
            int r2 = r2 + r3
            int r3 = r2 + 1
            byte r2 = r0[r2]
            int r4 = r3 + 1
            byte r3 = r0[r3]
            int r5 = r4 + 1
            byte r4 = r0[r4]
            int r6 = r5 + 1
            byte r5 = r0[r5]
            byte r0 = r0[r6]
            goto L_0x0054
        L_0x002f:
            int r0 = r14.position()
            int r2 = r0 + 1
            byte r0 = r14.get((int) r0)
            int r3 = r2 + 1
            byte r2 = r14.get((int) r2)
            int r4 = r3 + 1
            byte r3 = r14.get((int) r3)
            int r5 = r4 + 1
            byte r4 = r14.get((int) r4)
            byte r6 = r14.get((int) r5)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r0
            r0 = r6
        L_0x0054:
            r6 = 20
            r7 = 3
            java.lang.String r8 = " minor="
            java.lang.String r9 = "Unsupported record version major="
            r10 = 768(0x300, float:1.076E-42)
            if (r2 < r6) goto L_0x0092
            r6 = 23
            if (r2 > r6) goto L_0x0092
            r6 = r3
            r11 = r4
            int r12 = r6 << 8
            r13 = r11 & 255(0xff, float:3.57E-43)
            r12 = r12 | r13
            if (r12 < r10) goto L_0x0077
            if (r6 > r7) goto L_0x0077
            r7 = r5 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r8 = r0 & 255(0xff, float:3.57E-43)
            int r7 = r7 + r8
            int r7 = r7 + r1
            goto L_0x00bb
        L_0x0077:
            javax.net.ssl.SSLException r1 = new javax.net.ssl.SSLException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r9)
            r7.append(r6)
            r7.append(r8)
            r7.append(r11)
            java.lang.String r7 = r7.toString()
            r1.<init>(r7)
            throw r1
        L_0x0092:
            r1 = r2 & 128(0x80, float:1.794E-43)
            r6 = 1
            if (r1 == 0) goto L_0x0099
            r1 = r6
            goto L_0x009a
        L_0x0099:
            r1 = 0
        L_0x009a:
            if (r1 == 0) goto L_0x00d7
            if (r4 == r6) goto L_0x00a1
            r6 = 4
            if (r4 != r6) goto L_0x00d7
        L_0x00a1:
            r6 = r5
            r11 = r0
            int r12 = r6 << 8
            r13 = r11 & 255(0xff, float:3.57E-43)
            r12 = r12 | r13
            r13 = 2
            if (r12 < r10) goto L_0x00ad
            if (r6 <= r7) goto L_0x00af
        L_0x00ad:
            if (r12 != r13) goto L_0x00bc
        L_0x00af:
            r7 = 127(0x7f, float:1.78E-43)
            r8 = r2 & r7
            int r8 = r8 << 8
            r9 = r3 & 255(0xff, float:3.57E-43)
            int r8 = r8 + r9
            int r7 = r8 + 2
        L_0x00bb:
            return r7
        L_0x00bc:
            javax.net.ssl.SSLException r7 = new javax.net.ssl.SSLException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            r10.append(r6)
            r10.append(r8)
            r10.append(r11)
            java.lang.String r8 = r10.toString()
            r7.<init>(r8)
            throw r7
        L_0x00d7:
            javax.net.ssl.SSLException r6 = new javax.net.ssl.SSLException
            java.lang.String r7 = "Unrecognized SSL message, plaintext connection?"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.ssl.SSLUtils.getSSLPacketSize(org.glassfish.grizzly.Buffer):int");
    }

    public static void executeDelegatedTask(SSLEngine sslEngine) {
        while (true) {
            Runnable delegatedTask = sslEngine.getDelegatedTask();
            Runnable runnable = delegatedTask;
            if (delegatedTask != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    public static boolean isHandshaking(SSLEngine sslEngine) {
        SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();
        return (handshakeStatus == SSLEngineResult.HandshakeStatus.FINISHED || handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) ? false : true;
    }

    public static SSLEngineResult handshakeUnwrap(int length, SSLConnectionContext sslCtx, Buffer inputBuffer, Buffer tmpOutputBuffer) {
        SSLConnectionContext.SslResult result = sslCtx.unwrap(length, inputBuffer, tmpOutputBuffer, HS_UNWRAP_ALLOCATOR);
        Buffer output = result.getOutput();
        if (!output.isComposite()) {
            if (output != tmpOutputBuffer) {
                output.dispose();
            }
            if (!result.isError()) {
                return result.getSslEngineResult();
            }
            throw result.getError();
        }
        throw new AssertionError();
    }

    public static Buffer handshakeWrap(Connection connection, SSLConnectionContext sslCtx, Buffer netBuffer) {
        Buffer buffer;
        int packetBufferSize = sslCtx.getNetBufferSize();
        if (netBuffer == null || netBuffer.isComposite() || netBuffer.capacity() - netBuffer.limit() < packetBufferSize) {
            buffer = allocateOutputBuffer(packetBufferSize * 2);
        } else {
            netBuffer.position(netBuffer.limit());
            netBuffer.limit(netBuffer.capacity());
            buffer = netBuffer;
        }
        SSLConnectionContext.SslResult result = sslCtx.wrap(Buffers.EMPTY_BUFFER, buffer, HS_WRAP_ALLOCATOR);
        Buffer output = result.getOutput();
        output.flip();
        if (!(buffer == output || netBuffer == null || buffer != netBuffer)) {
            netBuffer.flip();
        }
        if (result.isError()) {
            if (output != netBuffer) {
                output.dispose();
            }
            throw result.getError();
        } else if (output != netBuffer) {
            return allowDispose(Buffers.appendBuffers(connection.getMemoryManager(), netBuffer, output));
        } else {
            return output;
        }
    }

    static Buffer allocateOutputBuffer(int size) {
        Buffer buffer = (Buffer) ThreadCache.takeFromCache(SSL_OUTPUT_BUFFER_IDX);
        if (!(buffer != null) || buffer.remaining() < size) {
            return new ByteBufferWrapper(ByteBuffer.allocate(size)) {
                public void dispose() {
                    clear();
                    ThreadCache.putToCache(SSLUtils.SSL_OUTPUT_BUFFER_IDX, this);
                }
            };
        }
        return buffer;
    }

    public static Buffer allocateInputBuffer(SSLConnectionContext sslCtx) {
        if (sslCtx.getSslEngine() == null) {
            return null;
        }
        return allocateOutputBuffer(sslCtx.getNetBufferSize() * 2);
    }

    static Buffer makeInputRemainder(SSLConnectionContext sslCtx, FilterChainContext context, Buffer buffer) {
        if (buffer == null) {
            return null;
        }
        if (!buffer.hasRemaining()) {
            buffer.tryDispose();
            return null;
        } else if (sslCtx.resetLastInputBuffer() != null) {
            return move(context.getMemoryManager(), buffer);
        } else {
            Buffer remainder = buffer.split(buffer.position());
            buffer.tryDispose();
            return remainder;
        }
    }

    static Buffer copy(MemoryManager memoryManager, Buffer buffer) {
        Buffer tmpBuf = memoryManager.allocate(buffer.remaining());
        tmpBuf.put(buffer);
        return tmpBuf.flip();
    }

    static Buffer move(MemoryManager memoryManager, Buffer buffer) {
        Buffer tmpBuf = copy(memoryManager, buffer);
        buffer.tryDispose();
        return tmpBuf;
    }

    public static Buffer allowDispose(Buffer buffer) {
        if (buffer == null) {
            return null;
        }
        buffer.allowBufferDispose(true);
        if (buffer.isComposite()) {
            ((CompositeBuffer) buffer).allowInternalBuffersDispose(true);
        }
        return buffer;
    }

    static SSLEngineResult sslEngineWrap(SSLEngine engine, ByteBuffer in, ByteBuffer out) {
        return engine.wrap(in, out);
    }

    static SSLEngineResult sslEngineWrap(SSLEngine engine, ByteBuffer[] in, int inOffs, int inLen, ByteBuffer out) {
        return ANDROID_WORKAROUND_NEEDED ? AndroidWorkAround.wrapArray(engine, in, inOffs, inLen, out) : engine.wrap(in, inOffs, inLen, out);
    }

    static SSLEngineResult sslEngineUnwrap(SSLEngine engine, ByteBuffer in, ByteBuffer out) {
        return engine.unwrap(in, out);
    }

    static SSLEngineResult sslEngineUnwrap(SSLEngine engine, ByteBuffer in, ByteBuffer[] out, int outOffs, int outLen) {
        return ANDROID_WORKAROUND_NEEDED ? AndroidWorkAround.unwrapArray(engine, in, out, outOffs, outLen) : engine.unwrap(in, out, outOffs, outLen);
    }

    public static class AndroidWorkAround {
        private AndroidWorkAround() {
        }

        public static SSLEngineResult wrapArray(SSLEngine engine, ByteBuffer[] in, int inOffs, int inLen, ByteBuffer out) {
            if (inOffs == 0 && inLen == in.length) {
                return engine.wrap(in, out);
            }
            ByteBuffer[] tmp = new ByteBuffer[inLen];
            System.arraycopy(in, inOffs, tmp, 0, inLen);
            return engine.wrap(tmp, out);
        }

        public static SSLEngineResult unwrapArray(SSLEngine engine, ByteBuffer in, ByteBuffer[] out, int outOffs, int outLen) {
            if (outOffs == 0 && outLen == out.length) {
                return engine.unwrap(in, out);
            }
            ByteBuffer[] tmp = new ByteBuffer[outLen];
            System.arraycopy(out, outOffs, tmp, 0, outLen);
            return engine.unwrap(in, tmp);
        }
    }
}
