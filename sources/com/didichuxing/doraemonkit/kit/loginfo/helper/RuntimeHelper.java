package com.didichuxing.doraemonkit.kit.loginfo.helper;

import com.didichuxing.doraemonkit.kit.loginfo.util.ArrayUtil;
import java.util.List;

public class RuntimeHelper {
    public static Process exec(List<String> args) {
        return Runtime.getRuntime().exec((String[]) ArrayUtil.toArray(args, String.class));
    }

    public static void destroy(Process process) {
        process.destroy();
    }
}
