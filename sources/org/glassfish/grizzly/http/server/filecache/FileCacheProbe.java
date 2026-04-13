package org.glassfish.grizzly.http.server.filecache;

public interface FileCacheProbe {
    void onEntryAddedEvent(FileCache fileCache, FileCacheEntry fileCacheEntry);

    void onEntryHitEvent(FileCache fileCache, FileCacheEntry fileCacheEntry);

    void onEntryMissedEvent(FileCache fileCache, String str, String str2);

    void onEntryRemovedEvent(FileCache fileCache, FileCacheEntry fileCacheEntry);

    void onErrorEvent(FileCache fileCache, Throwable th);

    public static class Adapter implements FileCacheProbe {
        public void onEntryAddedEvent(FileCache fileCache, FileCacheEntry entry) {
        }

        public void onEntryRemovedEvent(FileCache fileCache, FileCacheEntry entry) {
        }

        public void onEntryHitEvent(FileCache fileCache, FileCacheEntry entry) {
        }

        public void onEntryMissedEvent(FileCache fileCache, String host, String requestURI) {
        }

        public void onErrorEvent(FileCache fileCache, Throwable error) {
        }
    }
}
