package com.didichuxing.doraemonkit.okgo.callback;

import com.didichuxing.doraemonkit.okgo.convert.FileConvert;
import java.io.File;
import okhttp3.d0;

public abstract class FileCallback extends AbsCallback<File> {
    private FileConvert convert;

    public FileCallback() {
        this((String) null);
    }

    public FileCallback(String destFileName) {
        this((String) null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        FileConvert fileConvert = new FileConvert(destFileDir, destFileName);
        this.convert = fileConvert;
        fileConvert.setCallback(this);
    }

    public File convertResponse(d0 response) {
        File file = this.convert.convertResponse(response);
        response.close();
        return file;
    }
}
