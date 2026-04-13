package com.amazonaws.kinesisvideo.internal.producer.jni;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;

public class NativeLibraryLoader {
    private final Log mLog;

    public NativeLibraryLoader(@NonNull Log log) {
        this.mLog = (Log) Preconditions.checkNotNull(log);
    }

    public boolean loadNativeLibrary(String fullPath, String libraryName) {
        Preconditions.checkState((fullPath != null && !fullPath.isEmpty()) || (libraryName != null && !libraryName.isEmpty()), "Both the full path and library name can't be null at the same time");
        String soLibraryFileName = "lib" + libraryName + ".so";
        String dylibLibraryFileName = "lib" + libraryName + ".dylib";
        String dllLibraryFileName = "lib" + libraryName + ".dll";
        if (loadNativeLibraryDirect(fullPath)) {
            return true;
        }
        if (loadNativeLibraryDirect(fullPath + ".so")) {
            return true;
        }
        if (loadNativeLibraryDirect(fullPath + ".dylib")) {
            return true;
        }
        if (loadNativeLibraryDirect(fullPath + ".dll") || loadNativeLibraryDirect(soLibraryFileName) || loadNativeLibraryDirect(dylibLibraryFileName) || loadNativeLibraryDirect(dllLibraryFileName) || loadNativeLibraryIndirect(libraryName)) {
            return true;
        }
        return false;
    }

    private boolean loadNativeLibraryDirect(@NonNull String libraryFullPath) {
        if (libraryFullPath != null && !libraryFullPath.isEmpty()) {
            try {
                System.load(libraryFullPath);
                this.mLog.verbose("Success! Directly loaded native library %s.", libraryFullPath);
                return true;
            } catch (UnsatisfiedLinkError e) {
                this.mLog.warn("Unsatisfied link error. Directly loading native library %s.", libraryFullPath);
            } catch (SecurityException e2) {
                this.mLog.warn("Security exception. Directly loading native library %s.", libraryFullPath);
            }
        }
        return false;
    }

    private boolean loadNativeLibraryIndirect(@NonNull String libraryName) {
        try {
            System.loadLibrary(libraryName);
            this.mLog.verbose("Success! Indirectly loaded native library %s.", libraryName);
            return true;
        } catch (UnsatisfiedLinkError e) {
            this.mLog.exception(e, "Unsatisfied link error. Loading native library %s failed with %s", libraryName, e.toString());
            return false;
        } catch (SecurityException e2) {
            this.mLog.exception(e2, "Security exception. Loading native library %s failed with %s", libraryName, e2.toString());
            return false;
        }
    }
}
