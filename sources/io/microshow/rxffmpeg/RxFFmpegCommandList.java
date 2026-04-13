package io.microshow.rxffmpeg;

import android.util.Log;
import java.util.ArrayList;

public class RxFFmpegCommandList extends ArrayList<String> {
    public RxFFmpegCommandList() {
        add("ffmpeg");
        add("-y");
    }

    public RxFFmpegCommandList clearCommands() {
        clear();
        return this;
    }

    public RxFFmpegCommandList append(String s) {
        add(s);
        return this;
    }

    public String[] build() {
        String[] command = new String[size()];
        for (int i = 0; i < size(); i++) {
            command[i] = (String) get(i);
        }
        return command;
    }

    public String[] build(boolean isLog) {
        String[] cmds = build();
        if (isLog) {
            StringBuilder cmdLogStr = new StringBuilder();
            for (int i = 0; i < cmds.length; i++) {
                cmdLogStr.append(cmds[i]);
                if (i < cmds.length - 1) {
                    cmdLogStr.append(" ");
                }
            }
            Log.e("TAG_FFMPEG", "cmd: " + cmdLogStr.toString());
        }
        return cmds;
    }
}
