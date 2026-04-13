package org.glassfish.grizzly.compression.lzma;

import java.io.IOException;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.compression.lzma.impl.Encoder;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class LZMAEncoder extends AbstractTransformer<Buffer, Buffer> {
    /* access modifiers changed from: private */
    public static final ThreadCache.CachedTypeIndex<LZMAOutputState> CACHE_IDX = ThreadCache.obtainIndex(LZMAOutputState.class, 2);
    private final LZMAProperties lzmaProperties;

    public LZMAEncoder() {
        this(new LZMAProperties());
    }

    public LZMAEncoder(LZMAProperties lzmaProperties2) {
        this.lzmaProperties = lzmaProperties2;
    }

    public String getName() {
        return "lzma-encoder";
    }

    public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
        return input.hasRemaining();
    }

    /* access modifiers changed from: protected */
    public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage storage, Buffer input) {
        MemoryManager memoryManager = obtainMemoryManager(storage);
        LZMAOutputState state = (LZMAOutputState) obtainStateObject(storage);
        if (!state.isInitialized()) {
            initializeOutput(state);
        }
        Buffer encodedBuffer = null;
        if (input != null && input.hasRemaining()) {
            try {
                state.setMemoryManager(memoryManager);
                encodedBuffer = encodeBuffer(input, state);
            } catch (IOException ioe) {
                throw new TransformationException((Throwable) ioe);
            }
        }
        if (encodedBuffer == null) {
            return TransformationResult.createIncompletedResult(null);
        }
        return TransformationResult.createCompletedResult(encodedBuffer, null);
    }

    /* access modifiers changed from: protected */
    public AbstractTransformer.LastResultAwareState<Buffer, Buffer> createStateObject() {
        return create();
    }

    public static LZMAOutputState create() {
        LZMAOutputState state = (LZMAOutputState) ThreadCache.takeFromCache(CACHE_IDX);
        if (state != null) {
            return state;
        }
        return new LZMAOutputState();
    }

    public void finish(AttributeStorage storage) {
        ((LZMAOutputState) obtainStateObject(storage)).recycle();
    }

    private void initializeOutput(LZMAOutputState state) {
        Encoder encoder = state.getEncoder();
        encoder.setAlgorithm(this.lzmaProperties.getAlgorithm());
        encoder.setDictionarySize(this.lzmaProperties.getDictionarySize());
        encoder.setNumFastBytes(this.lzmaProperties.getNumFastBytes());
        encoder.setMatchFinder(this.lzmaProperties.getMatchFinder());
        encoder.setLcLpPb(this.lzmaProperties.getLc(), this.lzmaProperties.getLp(), this.lzmaProperties.getPb());
        encoder.setEndMarkerMode(true);
        state.setInitialized(true);
    }

    private Buffer encodeBuffer(Buffer input, LZMAOutputState state) {
        Buffer resultBuffer = null;
        state.setSrc(input);
        Buffer encoded = encode(state);
        if (encoded != null) {
            resultBuffer = Buffers.appendBuffers(state.getMemoryManager(), (Buffer) null, encoded);
        }
        input.position(input.limit());
        return resultBuffer;
    }

    private Buffer encode(LZMAOutputState outputState) {
        Encoder encoder = outputState.getEncoder();
        outputState.setDst(outputState.getMemoryManager().allocate(512));
        if (!outputState.isHeaderWritten()) {
            encoder.writeCoderProperties(outputState.getDst());
            outputState.setHeaderWritten(true);
        }
        encoder.code(outputState, -1, -1);
        Buffer dst = outputState.getDst();
        if (dst.position() <= 0) {
            dst.dispose();
            return null;
        }
        dst.trim();
        return dst;
    }

    public static class LZMAOutputState extends AbstractTransformer.LastResultAwareState<Buffer, Buffer> implements Cacheable {
        private Buffer dst;
        private final Encoder encoder = new Encoder();
        private boolean headerWritten = false;
        private boolean initialized;
        private MemoryManager mm;
        private Buffer src;

        public boolean isInitialized() {
            return this.initialized;
        }

        public void setInitialized(boolean initialized2) {
            this.initialized = initialized2;
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

        public Encoder getEncoder() {
            return this.encoder;
        }

        public boolean isHeaderWritten() {
            return this.headerWritten;
        }

        public void setHeaderWritten(boolean headerWritten2) {
            this.headerWritten = headerWritten2;
        }

        public MemoryManager getMemoryManager() {
            return this.mm;
        }

        public void setMemoryManager(MemoryManager mm2) {
            this.mm = mm2;
        }

        public void recycle() {
            this.lastResult = null;
            this.initialized = false;
            this.headerWritten = false;
            this.mm = null;
            this.dst = null;
            this.src = null;
            ThreadCache.putToCache(LZMAEncoder.CACHE_IDX, this);
        }
    }

    public static class LZMAProperties {
        private int algorithm = 2;
        private int dictionarySize = 65536;
        private int lc = 3;
        private int lp = 0;
        private int matchFinder = 1;
        private int numFastBytes = 128;
        private int pb = 2;

        public LZMAProperties() {
            loadProperties(this);
        }

        public LZMAProperties(int algorithm2, int dictionarySize2, int numFastBytes2, int matchFinder2, int lc2, int lp2, int pb2) {
            this.algorithm = algorithm2;
            this.dictionarySize = dictionarySize2;
            this.numFastBytes = numFastBytes2;
            this.matchFinder = matchFinder2;
            this.lc = lc2;
            this.lp = lp2;
            this.pb = pb2;
        }

        public int getLc() {
            return this.lc;
        }

        public void setLc(int Lc) {
            this.lc = Lc;
        }

        public int getLp() {
            return this.lp;
        }

        public void setLp(int Lp) {
            this.lp = Lp;
        }

        public int getPb() {
            return this.pb;
        }

        public void setPb(int Pb) {
            this.pb = Pb;
        }

        public int getAlgorithm() {
            return this.algorithm;
        }

        public void setAlgorithm(int algorithm2) {
            this.algorithm = algorithm2;
        }

        public int getDictionarySize() {
            return this.dictionarySize;
        }

        public void setDictionarySize(int dictionarySize2) {
            this.dictionarySize = dictionarySize2;
        }

        public int getMatchFinder() {
            return this.matchFinder;
        }

        public void setMatchFinder(int matchFinder2) {
            this.matchFinder = matchFinder2;
        }

        public int getNumFastBytes() {
            return this.numFastBytes;
        }

        public void setNumFastBytes(int numFastBytes2) {
            this.numFastBytes = numFastBytes2;
        }

        public static void loadProperties(LZMAProperties properties) {
            properties.algorithm = Integer.getInteger("lzma-filter.algorithm", 2).intValue();
            properties.dictionarySize = 1 << Integer.getInteger("lzma-filter.dictionary-size", 16).intValue();
            properties.numFastBytes = Integer.getInteger("lzma-filter.num-fast-bytes", 128).intValue();
            properties.matchFinder = Integer.getInteger("lzma-filter.match-finder", 1).intValue();
            properties.lc = Integer.getInteger("lzma-filter.lc", 3).intValue();
            properties.lp = Integer.getInteger("lzma-filter.lp", 0).intValue();
            properties.pb = Integer.getInteger("lzma-filter.pb", 2).intValue();
        }
    }
}
