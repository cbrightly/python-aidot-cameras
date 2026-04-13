package com.downloader;

import java.io.Serializable;

/* compiled from: Progress */
public class j implements Serializable {
    public long currentBytes;
    public long totalBytes;

    public j(long currentBytes2, long totalBytes2) {
        this.currentBytes = currentBytes2;
        this.totalBytes = totalBytes2;
    }

    public String toString() {
        return "Progress{currentBytes=" + this.currentBytes + ", totalBytes=" + this.totalBytes + '}';
    }
}
