package com.didichuxing.doraemonkit.kit.loginfo;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.loginfo.reader.ScrubberUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogLine {
    private static final int TIMESTAMP_LENGTH = 19;
    private static final String filterPattern = "ResourceType|memtrack|android.os.Debug|BufferItemConsumer|DPM.*|MDM.*|ChimeraUtils|BatteryExternalStats.*|chatty.*|DisplayPowerController|WidgetHelper|WearableService|DigitalWidget.*|^ANDR-PERF-.*";
    public static boolean isScrubberEnabled = false;
    private static Pattern logPattern = Pattern.compile("(\\w)/([^(]+)\\(\\s*(\\d+)(?:\\*\\s*\\d+)?\\): ");
    private boolean expanded = false;
    private boolean highlighted = false;
    private int logLevel;
    private String logOutput;
    private int processId = -1;
    private String tag;
    private String timestamp;

    public static LogLine newLogLine(String originalLine, boolean expanded2) {
        LogLine logLine = new LogLine();
        logLine.setExpanded(expanded2);
        int startIdx = 0;
        if (!TextUtils.isEmpty(originalLine) && Character.isDigit(originalLine.charAt(0)) && originalLine.length() >= 19) {
            logLine.setTimestamp(originalLine.substring(0, 18));
            startIdx = 19;
        }
        Matcher matcher = logPattern.matcher(originalLine);
        if (matcher.find(startIdx)) {
            char logLevelChar = matcher.group(1).charAt(0);
            String logText = originalLine.substring(matcher.end());
            if (logText.matches("^maxLineHeight.*|Failed to read.*")) {
                logLine.setLogLevel(convertCharToLogLevel('V'));
            } else {
                logLine.setLogLevel(convertCharToLogLevel(logLevelChar));
            }
            String tagText = matcher.group(2);
            if (tagText.matches(filterPattern)) {
                logLine.setLogLevel(convertCharToLogLevel('V'));
            }
            logLine.setTag(tagText);
            logLine.setProcessId(Integer.parseInt(matcher.group(3)));
            logLine.setLogOutput(logText);
        } else {
            logLine.setLogOutput(originalLine);
            logLine.setLogLevel(-1);
        }
        return logLine;
    }

    private static int convertCharToLogLevel(char logLevelChar) {
        switch (logLevelChar) {
            case 'D':
                return 3;
            case 'E':
                return 6;
            case 'F':
                return 2;
            case 'I':
                return 4;
            case 'V':
                return 2;
            case 'W':
                return 5;
            default:
                return -1;
        }
    }

    private static char convertLogLevelToChar(int logLevel2) {
        switch (logLevel2) {
            case 2:
                return 'V';
            case 3:
                return 'D';
            case 4:
                return 'I';
            case 5:
                return 'W';
            case 6:
                return 'E';
            default:
                return ' ';
        }
    }

    public String getOriginalLine() {
        if (this.logLevel == -1) {
            return this.logOutput;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String str = this.timestamp;
        if (str != null) {
            stringBuilder.append(str);
            stringBuilder.append(' ');
        }
        stringBuilder.append(convertLogLevelToChar(this.logLevel));
        stringBuilder.append('/');
        stringBuilder.append(this.tag);
        stringBuilder.append('(');
        stringBuilder.append(this.processId);
        stringBuilder.append("): ");
        stringBuilder.append(this.logOutput);
        return stringBuilder.toString();
    }

    public String getLogLevelText() {
        return Character.toString(convertLogLevelToChar(this.logLevel));
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel2) {
        this.logLevel = logLevel2;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag2) {
        this.tag = tag2;
    }

    public String getLogOutput() {
        return this.logOutput;
    }

    public void setLogOutput(String logOutput2) {
        if (isScrubberEnabled) {
            this.logOutput = ScrubberUtils.scrubLine(logOutput2);
        } else {
            this.logOutput = logOutput2;
        }
    }

    public int getProcessId() {
        return this.processId;
    }

    public void setProcessId(int processId2) {
        this.processId = processId2;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp2) {
        this.timestamp = timestamp2;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded2) {
        this.expanded = expanded2;
    }

    public boolean isHighlighted() {
        return this.highlighted;
    }

    public void setHighlighted(boolean highlighted2) {
        this.highlighted = highlighted2;
    }
}
