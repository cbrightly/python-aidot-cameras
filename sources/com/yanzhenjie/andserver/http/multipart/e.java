package com.yanzhenjie.andserver.http.multipart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.util.h;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.a;

/* compiled from: StandardMultipartFile */
public class e implements b, Serializable {
    private final a fileItem;
    private final long size;

    public e(a fileItem2) {
        this.fileItem = fileItem2;
        this.size = fileItem2.getSize();
    }

    public final a getFileItem() {
        return this.fileItem;
    }

    @NonNull
    public String getName() {
        return this.fileItem.b();
    }

    @Nullable
    public String getFilename() {
        String filename = this.fileItem.getName();
        if (filename == null) {
            return "";
        }
        int unixSep = filename.lastIndexOf("/");
        int winSep = filename.lastIndexOf("\\");
        int pos = winSep > unixSep ? winSep : unixSep;
        if (pos != -1) {
            return filename.substring(pos + 1);
        }
        return filename;
    }

    @NonNull
    public h getContentType() {
        try {
            return h.parseMediaType(this.fileItem.getContentType());
        } catch (Exception e) {
            return h.APPLICATION_OCTET_STREAM;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public long getSize() {
        return this.size;
    }

    public byte[] getBytes() {
        if (isAvailable()) {
            byte[] bytes = this.fileItem.get();
            return bytes != null ? bytes : new byte[0];
        }
        throw new IllegalStateException("File has been moved - cannot be read again.");
    }

    @NonNull
    public InputStream getStream() {
        if (isAvailable()) {
            InputStream inputStream = this.fileItem.getInputStream();
            return inputStream != null ? inputStream : com.yanzhenjie.andserver.util.e.b();
        }
        throw new IllegalStateException("File has been moved - cannot be read again.");
    }

    public void transferTo(@NonNull File dest) {
        if (!isAvailable()) {
            throw new IllegalStateException("File has already been moved - cannot be transferred again.");
        } else if (!dest.exists() || dest.delete()) {
            try {
                this.fileItem.d(dest);
            } catch (FileUploadException ex) {
                throw new IllegalStateException(ex.getMessage(), ex);
            } catch (IllegalStateException ex2) {
                throw ex2;
            } catch (IOException ex3) {
                throw ex3;
            } catch (Exception ex4) {
                throw new IOException("File transfer failed", ex4);
            }
        } else {
            throw new IOException("Destination file [" + dest.getAbsolutePath() + "] already exists and could not be deleted.");
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAvailable() {
        if (this.fileItem.g()) {
            return true;
        }
        a aVar = this.fileItem;
        if (aVar instanceof org.apache.commons.fileupload.disk.a) {
            return ((org.apache.commons.fileupload.disk.a) aVar).i().exists();
        }
        if (aVar.getSize() == this.size) {
            return true;
        }
        return false;
    }
}
