package com.meituan.robust;

import android.content.Context;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RobustApkHashUtils {
    private static String robustApkHashValue;

    public static String readRobustApkHash(Context context) {
        if (TextUtils.isEmpty(robustApkHashValue)) {
            robustApkHashValue = readRobustApkHashFile(context);
        }
        return robustApkHashValue;
    }

    private static String readRobustApkHashFile(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return readFirstLine(context, Constants.ROBUST_APK_HASH_FILE_NAME);
        } catch (Throwable th) {
            return "";
        }
    }

    private static String readFirstLine(Context context, String fileName) {
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String readLine = reader2.readLine();
            try {
                reader2.close();
            } catch (IOException e) {
            }
            return readLine;
        } catch (IOException e2) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                }
            }
            return "";
        }
    }
}
