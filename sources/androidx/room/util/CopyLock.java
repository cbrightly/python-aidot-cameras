package androidx.room.util;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class CopyLock {
    private static final Map<String, Lock> sThreadLocks = new HashMap();
    private final File mCopyLockFile;
    private final boolean mFileLevelLock;
    private FileChannel mLockChannel;
    private final Lock mThreadLock;

    public CopyLock(@NonNull String name, @NonNull File lockDir, boolean processLock) {
        File file = new File(lockDir, name + ".lck");
        this.mCopyLockFile = file;
        this.mThreadLock = getThreadLock(file.getAbsolutePath());
        this.mFileLevelLock = processLock;
    }

    public void lock() {
        this.mThreadLock.lock();
        if (this.mFileLevelLock) {
            try {
                FileChannel channel = new FileOutputStream(this.mCopyLockFile).getChannel();
                this.mLockChannel = channel;
                channel.lock();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to grab copy lock.", e);
            }
        }
    }

    public void unlock() {
        FileChannel fileChannel = this.mLockChannel;
        if (fileChannel != null) {
            try {
                fileChannel.close();
            } catch (IOException e) {
            }
        }
        this.mThreadLock.unlock();
    }

    private static Lock getThreadLock(String key) {
        Lock threadLock;
        Map<String, Lock> map = sThreadLocks;
        synchronized (map) {
            threadLock = map.get(key);
            if (threadLock == null) {
                threadLock = new ReentrantLock();
                map.put(key, threadLock);
            }
        }
        return threadLock;
    }
}
