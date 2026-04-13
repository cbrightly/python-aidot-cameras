package io.microshow.rxffmpeg;

import android.text.TextUtils;

public class RxFFmpegCommandSupport {
    public static String[] getBoxblur(String inputPath, String outputPath, String boxblur, boolean isLog) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-vf");
        StringBuilder sb = new StringBuilder();
        sb.append("boxblur=");
        sb.append(TextUtils.isEmpty(boxblur) ? "5:1" : boxblur);
        cmdlist.append(sb.toString());
        cmdlist.append("-preset");
        cmdlist.append("superfast");
        cmdlist.append(outputPath);
        return cmdlist.build(isLog);
    }
}
