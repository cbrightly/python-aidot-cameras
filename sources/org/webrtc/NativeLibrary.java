package org.webrtc;

public class NativeLibrary {
    /* access modifiers changed from: private */
    public static String TAG = "NativeLibrary";
    private static boolean libraryLoaded;
    private static Object lock = new Object();

    NativeLibrary() {
    }

    public static class DefaultLoader implements NativeLibraryLoader {
        DefaultLoader() {
        }

        public boolean load(String name) {
            String access$000 = NativeLibrary.TAG;
            Logging.d(access$000, "Loading library: " + name);
            try {
                System.loadLibrary(name);
                return true;
            } catch (UnsatisfiedLinkError e) {
                String access$0002 = NativeLibrary.TAG;
                Logging.e(access$0002, "Failed to load native library: " + name, e);
                return false;
            }
        }
    }

    static void initialize(NativeLibraryLoader loader, String libraryName) {
        synchronized (lock) {
            if (libraryLoaded) {
                Logging.d(TAG, "Native library has already been loaded.");
                return;
            }
            String str = TAG;
            Logging.d(str, "Loading native library: " + libraryName);
            libraryLoaded = loader.load(libraryName);
        }
    }

    static boolean isLoaded() {
        boolean z;
        synchronized (lock) {
            z = libraryLoaded;
        }
        return z;
    }
}
