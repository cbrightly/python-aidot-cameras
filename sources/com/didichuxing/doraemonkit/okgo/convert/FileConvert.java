package com.didichuxing.doraemonkit.okgo.convert;

import android.os.Environment;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import com.didichuxing.doraemonkit.okgo.utils.IOUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import okhttp3.d0;
import okhttp3.e0;

public class FileConvert implements Converter<File> {
    public static final String DM_TARGET_FOLDER;
    /* access modifiers changed from: private */
    public Callback<File> callback;
    private String fileName;
    private String folder;

    static {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append("download");
        sb.append(str);
        DM_TARGET_FOLDER = sb.toString();
    }

    public FileConvert() {
        this((String) null);
    }

    public FileConvert(String fileName2) {
        this(Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER, fileName2);
    }

    public FileConvert(String folder2, String fileName2) {
        this.folder = folder2;
        this.fileName = fileName2;
    }

    public void setCallback(Callback<File> callback2) {
        this.callback = callback2;
    }

    public File convertResponse(d0 response) {
        String url = response.J().l().toString();
        if (TextUtils.isEmpty(this.folder)) {
            this.folder = Environment.getExternalStorageDirectory() + DM_TARGET_FOLDER;
        }
        if (TextUtils.isEmpty(this.fileName)) {
            this.fileName = HttpUtils.getNetFileName(response, url);
        }
        File dir = new File(this.folder);
        IOUtils.createFolder(dir);
        File file = new File(dir, this.fileName);
        IOUtils.delFileOrFolder(file);
        byte[] buffer = new byte[8192];
        try {
            e0 body = response.a();
            if (body == null) {
                return null;
            }
            InputStream bodyStream = body.byteStream();
            Progress progress = new Progress();
            progress.totalSize = body.contentLength();
            progress.fileName = this.fileName;
            progress.filePath = file.getAbsolutePath();
            progress.status = 2;
            progress.url = url;
            progress.tag = url;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while (true) {
                int read = bodyStream.read(buffer);
                int len = read;
                if (read != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    if (this.callback != null) {
                        Progress.changeProgress(progress, (long) len, new Progress.Action() {
                            public void call(Progress progress) {
                                FileConvert.this.onProgress(progress);
                            }
                        });
                    }
                } else {
                    fileOutputStream.flush();
                    IOUtils.closeQuietly(bodyStream);
                    IOUtils.closeQuietly(fileOutputStream);
                    return file;
                }
            }
        } finally {
            IOUtils.closeQuietly((Closeable) null);
            IOUtils.closeQuietly((Closeable) null);
        }
    }

    /* access modifiers changed from: private */
    public void onProgress(final Progress progress) {
        HttpUtils.runOnUiThread(new Runnable() {
            public void run() {
                FileConvert.this.callback.downloadProgress(progress);
            }
        });
    }
}
