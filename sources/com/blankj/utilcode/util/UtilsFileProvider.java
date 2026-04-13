package com.blankj.utilcode.util;

import android.app.Application;
import androidx.core.content.FileProvider;

public class UtilsFileProvider extends FileProvider {
    public boolean onCreate() {
        f0.b((Application) getContext().getApplicationContext());
        return true;
    }
}
