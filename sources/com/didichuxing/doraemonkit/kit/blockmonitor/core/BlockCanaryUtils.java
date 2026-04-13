package com.didichuxing.doraemonkit.kit.blockmonitor.core;

import android.content.Context;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.blockmonitor.bean.BlockInfo;
import com.didichuxing.doraemonkit.util.SystemUtil;
import java.util.Iterator;

public final class BlockCanaryUtils {
    private static final String CURRENT_PACKAGE = "com.didichuxing.doraemonkit";
    private static String sProcessName;
    private static boolean sProcessNameFirstGetFlag = false;

    public static String concernStackString(Context context, BlockInfo blockInfo) {
        Iterator<String> it = blockInfo.threadStackEntries.iterator();
        while (it.hasNext()) {
            String stackEntry = it.next();
            if (!TextUtils.isEmpty(stackEntry)) {
                String[] lines = stackEntry.split("\r\n");
                for (String line : lines) {
                    String keyStackString = concernStackString(context, line);
                    if (keyStackString != null) {
                        return keyStackString;
                    }
                }
                return classSimpleName(lines[0]);
            }
        }
        return "";
    }

    public static boolean isBlockInfoValid(BlockInfo blockInfo) {
        boolean isValid = true;
        if (!(!TextUtils.isEmpty(blockInfo.timeStart)) || blockInfo.timeCost < 0) {
            isValid = false;
        }
        return isValid;
    }

    private static String concernStackString(Context context, String line) {
        if (!sProcessNameFirstGetFlag) {
            sProcessNameFirstGetFlag = true;
            sProcessName = SystemUtil.obtainProcessName(context);
        }
        if (line.startsWith(sProcessName) || line.startsWith("com.didichuxing.doraemonkit")) {
            return classSimpleName(line);
        }
        return null;
    }

    private static String classSimpleName(String stackLine) {
        int index1 = stackLine.indexOf(40);
        int index2 = stackLine.indexOf(41);
        if (index1 < 0 || index2 < 0) {
            return stackLine;
        }
        return stackLine.substring(index1 + 1, index2);
    }
}
