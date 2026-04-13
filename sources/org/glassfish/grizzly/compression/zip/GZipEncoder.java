package org.glassfish.grizzly.compression.zip;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.MemoryManager;

public class GZipEncoder extends AbstractTransformer<Buffer, Buffer> {
    private static final int GZIP_MAGIC = 35615;
    private static final int TRAILER_SIZE = 8;
    private static final Buffer header;
    private final int bufferSize;

    static {
        Buffer allocate = MemoryManager.DEFAULT_MEMORY_MANAGER.allocate(10);
        header = allocate;
        allocate.put((byte) 31);
        allocate.put((byte) -117);
        allocate.put((byte) 8);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.put((byte) 0);
        allocate.flip();
    }

    public GZipEncoder() {
        this(512);
    }

    public GZipEncoder(int bufferSize2) {
        this.bufferSize = bufferSize2;
    }

    public String getName() {
        return "gzip-encoder";
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public GZipOutputState createStateObject() {
        return new GZipOutputState();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage storage, Buffer input) {
        MemoryManager memoryManager = obtainMemoryManager(storage);
        GZipOutputState state = (GZipOutputState) obtainStateObject(storage);
        if (!state.isInitialized) {
            state.initialize();
        }
        Buffer encodedBuffer = null;
        if (input != null && input.hasRemaining()) {
            encodedBuffer = encodeBuffer(input, state, memoryManager);
        }
        if (encodedBuffer == null) {
            return TransformationResult.createIncompletedResult(null);
        }
        if (!state.isHeaderWritten) {
            boolean unused = state.isHeaderWritten = true;
            encodedBuffer = Buffers.appendBuffers(memoryManager, getHeader(), encodedBuffer);
        }
        return TransformationResult.createCompletedResult(encodedBuffer, null);
    }

    public Buffer finish(AttributeStorage storage) {
        MemoryManager memoryManager = obtainMemoryManager(storage);
        GZipOutputState state = (GZipOutputState) obtainStateObject(storage);
        Buffer resultBuffer = null;
        if (state.isInitialized) {
            Deflater deflater = state.deflater;
            if (!deflater.finished()) {
                deflater.finish();
                while (!deflater.finished()) {
                    resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, deflate(deflater, memoryManager));
                }
                if (!state.isHeaderWritten) {
                    boolean unused = state.isHeaderWritten = true;
                    resultBuffer = Buffers.appendBuffers(memoryManager, getHeader(), resultBuffer);
                }
                Buffer trailer = memoryManager.allocate(8);
                putUInt(trailer, (int) state.crc32.getValue());
                putUInt(trailer, deflater.getTotalIn());
                trailer.flip();
                resultBuffer = Buffers.appendBuffers(memoryManager, resultBuffer, trailer);
            }
            state.reset();
        }
        return resultBuffer;
    }

    private Buffer getHeader() {
        Buffer headerToWrite = header.duplicate();
        headerToWrite.allowBufferDispose(false);
        return headerToWrite;
    }

    private Buffer encodeBuffer(Buffer buffer, GZipOutputState state, MemoryManager memoryManager) {
        int off;
        byte[] buf;
        MemoryManager memoryManager2 = memoryManager;
        CRC32 crc32 = state.crc32;
        Deflater deflater = state.deflater;
        if (!deflater.finished()) {
            int stride = this.bufferSize;
            Buffer resultBuffer = null;
            ByteBufferArray byteBufferArray = buffer.toByteBufferArray();
            ByteBuffer[] buffers = (ByteBuffer[]) byteBufferArray.getArray();
            int size = byteBufferArray.size();
            for (int i = 0; i < size; i++) {
                ByteBuffer byteBuffer = buffers[i];
                int len = byteBuffer.remaining();
                if (len > 0) {
                    if (byteBuffer.hasArray()) {
                        buf = byteBuffer.array();
                        off = byteBuffer.arrayOffset() + byteBuffer.position();
                    } else {
                        buf = new byte[len];
                        off = 0;
                        byteBuffer.get(buf);
                        byteBuffer.position(byteBuffer.position() - len);
                    }
                    for (int j = 0; j < len; j += stride) {
                        deflater.setInput(buf, off + j, Math.min(stride, len - j));
                        resultBuffer = resultBuffer;
                        while (!deflater.needsInput()) {
                            Buffer deflated = deflate(deflater, memoryManager2);
                            if (deflated != null) {
                                resultBuffer = Buffers.appendBuffers(memoryManager2, resultBuffer, deflated);
                            }
                        }
                    }
                    Buffer buffer2 = resultBuffer;
                    crc32.update(buf, off, len);
                }
            }
            byteBufferArray.restore();
            byteBufferArray.recycle();
            buffer.position(buffer.limit());
            return resultBuffer;
        }
        Buffer buffer3 = buffer;
        throw new IllegalStateException("write beyond end of stream");
    }

    /* access modifiers changed from: protected */
    public Buffer deflate(Deflater deflater, MemoryManager memoryManager) {
        Buffer buffer = memoryManager.allocate(this.bufferSize);
        ByteBuffer byteBuffer = buffer.toByteBuffer();
        int len = deflater.deflate(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), this.bufferSize);
        if (len <= 0) {
            buffer.dispose();
            return null;
        }
        buffer.position(len);
        buffer.trim();
        return buffer;
    }

    private static void putUInt(Buffer buffer, int value) {
        putUShort(buffer, value & 65535);
        putUShort(buffer, 65535 & (value >> 16));
    }

    private static void putUShort(Buffer buffer, int value) {
        buffer.put((byte) (value & 255));
        buffer.put((byte) ((value >> 8) & 255));
    }

    public static final class GZipOutputState extends AbstractTransformer.LastResultAwareState<Buffer, Buffer> {
        /* access modifiers changed from: private */
        public CRC32 crc32;
        /* access modifiers changed from: private */
        public Deflater deflater;
        /* access modifiers changed from: private */
        public boolean isHeaderWritten;
        /* access modifiers changed from: private */
        public boolean isInitialized;

        protected GZipOutputState() {
        }

        /* access modifiers changed from: private */
        public void initialize() {
            Deflater newDeflater = new Deflater(-1, true);
            CRC32 newCrc32 = new CRC32();
            newCrc32.reset();
            this.deflater = newDeflater;
            this.crc32 = newCrc32;
            this.isInitialized = true;
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.isInitialized = false;
            this.isHeaderWritten = false;
            this.deflater.end();
            this.crc32 = null;
            this.deflater = null;
        }
    }
}
