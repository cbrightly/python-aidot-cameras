package com.didichuxing.doraemonkit.kit.loginfo.reader;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import com.didichuxing.doraemonkit.kit.loginfo.helper.RuntimeHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class SingleLogcatReader extends AbsLogcatReader {
    private static final String TAG = "SingleLogcatReader";
    private BufferedReader bufferedReader;
    private String lastLine;
    private String logBuffer;
    private Process logcatProcess;

    public SingleLogcatReader(boolean recordingMode, String logBuffer2, String lastLine2) {
        super(recordingMode);
        this.logBuffer = logBuffer2;
        this.lastLine = lastLine2;
        init();
    }

    private void init() {
        this.logcatProcess = LogcatHelper.getLogcatProcess(this.logBuffer);
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.logcatProcess.getInputStream()), 8192);
    }

    public String getLogBuffer() {
        return this.logBuffer;
    }

    public void killQuietly() {
        Process process = this.logcatProcess;
        if (process != null) {
            RuntimeHelper.destroy(process);
        }
    }

    public String readLine() {
        String str;
        String line = this.bufferedReader.readLine();
        if (this.recordingMode && (str = this.lastLine) != null && str.equals(line)) {
            this.lastLine = null;
        }
        return line;
    }

    private boolean isAfterLastTime(String line) {
        return isDatedLogLine(this.lastLine) && isDatedLogLine(line) && line.compareTo(this.lastLine) > 0;
    }

    private boolean isDatedLogLine(String line) {
        return !TextUtils.isEmpty(line) && line.length() >= 18 && Character.isDigit(line.charAt(0));
    }

    public boolean readyToRecord() {
        return this.recordingMode && this.lastLine == null;
    }

    public List<Process> getProcesses() {
        return Collections.singletonList(this.logcatProcess);
    }
}
