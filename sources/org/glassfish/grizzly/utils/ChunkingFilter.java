package org.glassfish.grizzly.utils;

import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.AbstractCodecFilter;
import org.glassfish.grizzly.memory.Buffers;

public class ChunkingFilter extends AbstractCodecFilter<Buffer, Buffer> {
    private static final Logger LOGGER = Grizzly.logger(ChunkingFilter.class);
    private final int chunkSize;

    public ChunkingFilter(int chunkSize2) {
        super(new ChunkingDecoder(chunkSize2), new ChunkingEncoder(chunkSize2));
        this.chunkSize = chunkSize2;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    public static final class ChunkingDecoder extends ChunkingTransformer {
        public ChunkingDecoder(int chunk) {
            super(chunk);
        }
    }

    public static final class ChunkingEncoder extends ChunkingTransformer {
        public ChunkingEncoder(int chunk) {
            super(chunk);
        }
    }

    public static abstract class ChunkingTransformer extends AbstractTransformer<Buffer, Buffer> {
        private final int chunk;

        public ChunkingTransformer(int chunk2) {
            this.chunk = chunk2;
        }

        public String getName() {
            return "ChunkingTransformer";
        }

        /* access modifiers changed from: protected */
        public TransformationResult<Buffer, Buffer> transformImpl(AttributeStorage storage, Buffer input) {
            if (!input.hasRemaining()) {
                return TransformationResult.createIncompletedResult(input);
            }
            int chunkSize = Math.min(this.chunk, input.remaining());
            int oldInputPos = input.position();
            int oldInputLimit = input.limit();
            Buffers.setPositionLimit(input, oldInputPos, oldInputPos + chunkSize);
            Buffer output = obtainMemoryManager(storage).allocate(chunkSize);
            output.put(input).flip();
            Buffers.setPositionLimit(input, oldInputPos + chunkSize, oldInputLimit);
            return TransformationResult.createCompletedResult(output, input);
        }

        public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
            return input != null && input.hasRemaining();
        }
    }
}
