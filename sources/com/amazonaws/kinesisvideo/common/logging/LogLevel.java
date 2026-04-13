package com.amazonaws.kinesisvideo.common.logging;

public enum LogLevel {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),
    ASSERT(7);
    
    private static final LogLevel[] LOG_LEVELS = null;
    private final int mLogLevel;

    static {
        LogLevel logLevel;
        LogLevel logLevel2;
        LogLevel logLevel3;
        LogLevel logLevel4;
        LogLevel logLevel5;
        LogLevel logLevel6;
        LOG_LEVELS = new LogLevel[]{logLevel, logLevel, logLevel, logLevel2, logLevel3, logLevel4, logLevel5, logLevel6};
    }

    public static LogLevel fromInt(int logLevel) {
        if (logLevel >= 0) {
            LogLevel[] logLevelArr = LOG_LEVELS;
            if (logLevel < logLevelArr.length) {
                return logLevelArr[logLevel];
            }
        }
        return VERBOSE;
    }

    private LogLevel(int logLevel) {
        this.mLogLevel = logLevel;
    }

    public int getLogLevel() {
        return this.mLogLevel;
    }
}
