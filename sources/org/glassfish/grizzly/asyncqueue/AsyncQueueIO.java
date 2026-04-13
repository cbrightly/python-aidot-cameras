package org.glassfish.grizzly.asyncqueue;

public interface AsyncQueueIO<L> {
    AsyncQueueReader<L> getReader();

    AsyncQueueWriter<L> getWriter();

    public static class Factory {
        public static <L> AsyncQueueIO<L> createImmutable(AsyncQueueReader<L> reader, AsyncQueueWriter<L> writer) {
            return new ImmutableAsyncQueueIO(reader, writer);
        }

        public static <L> MutableAsyncQueueIO<L> createMutable(AsyncQueueReader<L> reader, AsyncQueueWriter<L> writer) {
            return new MutableAsyncQueueIO<>(reader, writer);
        }
    }

    public static final class ImmutableAsyncQueueIO<L> implements AsyncQueueIO<L> {
        private final AsyncQueueReader<L> reader;
        private final AsyncQueueWriter<L> writer;

        private ImmutableAsyncQueueIO(AsyncQueueReader<L> reader2, AsyncQueueWriter<L> writer2) {
            this.reader = reader2;
            this.writer = writer2;
        }

        public AsyncQueueReader<L> getReader() {
            return this.reader;
        }

        public AsyncQueueWriter<L> getWriter() {
            return this.writer;
        }
    }

    public static final class MutableAsyncQueueIO<L> implements AsyncQueueIO<L> {
        private volatile AsyncQueueReader<L> reader;
        private volatile AsyncQueueWriter<L> writer;

        private MutableAsyncQueueIO(AsyncQueueReader<L> reader2, AsyncQueueWriter<L> writer2) {
            this.reader = reader2;
            this.writer = writer2;
        }

        public AsyncQueueReader<L> getReader() {
            return this.reader;
        }

        public void setReader(AsyncQueueReader<L> reader2) {
            this.reader = reader2;
        }

        public AsyncQueueWriter<L> getWriter() {
            return this.writer;
        }

        public void setWriter(AsyncQueueWriter<L> writer2) {
            this.writer = writer2;
        }
    }
}
