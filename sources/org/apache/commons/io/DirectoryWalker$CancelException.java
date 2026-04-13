package org.apache.commons.io;

import java.io.File;
import java.io.IOException;

public class DirectoryWalker$CancelException extends IOException {
    private static final long serialVersionUID = 1347339620135041008L;
    private final int depth;
    private final File file;

    public DirectoryWalker$CancelException(File file2, int depth2) {
        this("Operation Cancelled", file2, depth2);
    }

    public DirectoryWalker$CancelException(String message, File file2, int depth2) {
        super(message);
        this.file = file2;
        this.depth = depth2;
    }

    public File getFile() {
        return this.file;
    }

    public int getDepth() {
        return this.depth;
    }
}
