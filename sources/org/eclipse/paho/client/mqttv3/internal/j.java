package org.eclipse.paho.client.mqttv3.internal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: FileLock */
public class j {
    private File a;
    private RandomAccessFile b;
    private Object c;

    public j(File clientDir, String lockFilename) {
        this.a = new File(clientDir, lockFilename);
        if (i.c("java.nio.channels.FileLock")) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.a, "rw");
                this.b = randomAccessFile;
                Object channel = randomAccessFile.getClass().getMethod("getChannel", new Class[0]).invoke(this.b, new Object[0]);
                this.c = channel.getClass().getMethod("tryLock", new Class[0]).invoke(channel, new Object[0]);
            } catch (NoSuchMethodException e) {
                this.c = null;
            } catch (IllegalArgumentException e2) {
                this.c = null;
            } catch (IllegalAccessException e3) {
                this.c = null;
            }
            if (this.c == null) {
                a();
                throw new Exception("Problem obtaining file lock");
            }
        }
    }

    public void a() {
        try {
            Object obj = this.c;
            if (obj != null) {
                obj.getClass().getMethod("release", new Class[0]).invoke(this.c, new Object[0]);
                this.c = null;
            }
        } catch (Exception e) {
        }
        RandomAccessFile randomAccessFile = this.b;
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException e2) {
            }
            this.b = null;
        }
        File file = this.a;
        if (file != null && file.exists()) {
            this.a.delete();
        }
        this.a = null;
    }
}
