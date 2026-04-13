package org.glassfish.grizzly.memory;

public interface MemoryProbe {
    void onBufferAllocateEvent(int i);

    void onBufferAllocateFromPoolEvent(int i);

    void onBufferReleaseToPoolEvent(int i);

    public static class Adapter implements MemoryProbe {
        public void onBufferAllocateEvent(int size) {
        }

        public void onBufferAllocateFromPoolEvent(int size) {
        }

        public void onBufferReleaseToPoolEvent(int size) {
        }
    }
}
