package com.didichuxing.doraemonkit.kit.loginfo.reader;

import java.util.List;

public interface LogcatReader {
    List<Process> getProcesses();

    void killQuietly();

    String readLine();

    boolean readyToRecord();
}
