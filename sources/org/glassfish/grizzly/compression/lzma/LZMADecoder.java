package org.glassfish.grizzly.compression.lzma;

import java.io.IOException;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.glassfish.grizzly.compression.lzma.impl.Decoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class LZMADecoder extends AbstractTransformer<Buffer, Buffer> {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<LZMAInputState> CACHE_IDX = ThreadCache.obtainIndex(LZMAInputState.class, 2);

    public String getName() {
        return "lzma-decoder";
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage storage, Buffer input) {
        MemoryManager memoryManager = obtainMemoryManager(storage);
        LZMAInputState state = (LZMAInputState) obtainStateObject(storage);
        state.setMemoryManager(memoryManager);
        Buffer decodedBuffer = null;
        Decoder.State decState = null;
        if (input.hasRemaining()) {
            decState = decodeBuffer(input, state);
            decodedBuffer = state.getDst();
        }
        boolean hasRemainder = input.hasRemaining();
        Buffer buffer = null;
        if (decState == Decoder.State.NEED_MORE_DATA || decodedBuffer == null) {
            if (hasRemainder) {
                buffer = input;
            }
            return TransformationResult.createIncompletedResult(buffer);
        }
        Buffer flip = decodedBuffer.flip();
        if (hasRemainder) {
            buffer = input;
        }
        return TransformationResult.createCompletedResult(flip, buffer);
    }

    /* access modifiers changed from: protected */
    public AbstractTransformer.LastResultAwareState<Buffer, Buffer> createStateObject() {
        return create();
    }

    public static LZMAInputState create() {
        LZMAInputState state = (LZMAInputState) ThreadCache.takeFromCache(CACHE_IDX);
        if (state != null) {
            return state;
        }
        return new LZMAInputState();
    }

    public void finish(AttributeStorage storage) {
        ((LZMAInputState) obtainStateObject(storage)).recycle();
    }

    private Decoder.State decodeBuffer(Buffer buffer, LZMAInputState state) {
        state.setSrc(buffer);
        try {
            Decoder.State decState = state.getDecoder().code(state, -1);
            if (decState != Decoder.State.ERR) {
                return decState;
            }
            disposeDstBuffer(state);
            throw new IllegalStateException("Invalid decoder state.");
        } catch (IOException e) {
            disposeDstBuffer(state);
            throw new IllegalStateException(e);
        }
    }

    private static void disposeDstBuffer(LZMAInputState state) {
        Buffer dstBuffer = state.getDst();
        if (dstBuffer != null) {
            dstBuffer.dispose();
            state.setDst((Buffer) null);
        }
    }

    public static class LZMAInputState extends AbstractTransformer.LastResultAwareState<Buffer, Buffer> implements Cacheable {
        public boolean decInitialized;
        private final Decoder decoder = new Decoder();
        public Decoder.LiteralDecoder.Decoder2 decoder2;
        private final byte[] decoderConfigBits = new byte[5];
        private Buffer dst;
        private boolean initialized;
        public int inner1State;
        public int inner2State;
        public int lastMethodResult;
        private MemoryManager mm;
        public long nowPos64;
        public int posState;
        public byte prevByte;
        public int rep0;
        public int rep1;
        public int rep2;
        public int rep3;
        private Buffer src;
        public int state;
        public int state31;
        public int state311;
        public int state311Distance;
        public int state32;
        public int state321;
        public int state321NumDirectBits;
        public int state32PosSlot;
        public int state3Len;
        public int staticBitIndex;
        public int staticM;
        public int staticReverseDecodeMethodState;
        public int staticSymbol;

        public boolean initialize(Buffer buffer) {
            buffer.get(this.decoderConfigBits);
            this.initialized = this.decoder.setDecoderProperties(this.decoderConfigBits);
            this.state = Base.stateInit();
            return this.initialized;
        }

        public boolean isInitialized() {
            return this.initialized;
        }

        public Decoder getDecoder() {
            return this.decoder;
        }

        public Buffer getSrc() {
            return this.src;
        }

        public void setSrc(Buffer src2) {
            this.src = src2;
        }

        public Buffer getDst() {
            return this.dst;
        }

        public void setDst(Buffer dst2) {
            this.dst = dst2;
        }

        public MemoryManager getMemoryManager() {
            return this.mm;
        }

        public void setMemoryManager(MemoryManager mm2) {
            this.mm = mm2;
        }

        public void recycle() {
            this.state = 0;
            this.rep0 = 0;
            this.rep1 = 0;
            this.rep2 = 0;
            this.rep3 = 0;
            this.nowPos64 = 0;
            this.prevByte = 0;
            this.src = null;
            this.dst = null;
            this.lastResult = null;
            this.initialized = false;
            this.decInitialized = false;
            this.mm = null;
            this.posState = 0;
            this.lastMethodResult = 0;
            this.inner1State = 0;
            this.inner2State = 0;
            this.decoder2 = null;
            this.staticReverseDecodeMethodState = 0;
            this.state31 = 0;
            this.state311 = 0;
            this.state32 = 0;
            this.state321 = 0;
            ThreadCache.putToCache(LZMADecoder.CACHE_IDX, this);
        }
    }
}
