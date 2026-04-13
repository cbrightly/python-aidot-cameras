package com.amazonaws.kinesisvideo.common.logging;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.google.maps.android.BuildConfig;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final String BASE_TAG = "KinesisVideoStreams";
    private static final int DEFAULT_MESSAGE_BUFFER = 1024;
    private static final String MESSAGE_DELIMITER = ": ";
    public static final OutputChannel SYSTEM_OUT = new OutputChannel() {
        public void print(int level, String tag, String message) {
            System.out.println(String.format("%s%s%s\t%s", new Object[]{LogLevel.fromInt(level).toString(), Log.MESSAGE_DELIMITER, tag, message}));
        }

        public String toString() {
            return "standard output console";
        }
    };
    private static final String TAG_DELIMITER = ".";
    private LogLevel mCurrentLogLevel;
    private final OutputChannel mOutputChannel;
    private final StringBuilder mStringBuilder;
    private String mTag;

    public Log(@NonNull OutputChannel outputChannel) {
        this(outputChannel, LogLevel.INFO, BASE_TAG);
    }

    public Log(@NonNull OutputChannel outputChannel, LogLevel currentLogLevel, @NonNull String tag) {
        this.mOutputChannel = (OutputChannel) Preconditions.checkNotNull(outputChannel);
        this.mTag = (String) Preconditions.checkNotNull(tag);
        this.mCurrentLogLevel = currentLogLevel;
        this.mStringBuilder = new StringBuilder(1024);
    }

    public void setCurrentLogLevel(LogLevel logLevel) {
        this.mCurrentLogLevel = logLevel;
    }

    public void setPackagePrefix() {
        String[] parts = new Throwable().getStackTrace()[1].getClassName().split("\\.", 0);
        this.mTag = String.format("%s%s%s", new Object[]{BASE_TAG, TAG_DELIMITER, parts[parts.length - 1]});
    }

    public void log(LogLevel logLevel, String message) {
        if (logLevel.getLogLevel() >= this.mCurrentLogLevel.getLogLevel()) {
            this.mOutputChannel.print(logLevel.getLogLevel(), this.mTag, message);
        }
    }

    public void log(LogLevel logLevel, String template, Object... args) {
        log(logLevel, String.format(template, args));
    }

    public void verbose(String message) {
        log(LogLevel.VERBOSE, message);
    }

    public void verbose(String template, Object... args) {
        log(LogLevel.VERBOSE, template, args);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        log(LogLevel.DEBUG, template, args);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void info(String template, Object... args) {
        log(LogLevel.INFO, template, args);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void warn(String template, Object... args) {
        log(LogLevel.WARN, template, args);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void error(String template, Object... args) {
        log(LogLevel.ERROR, template, args);
    }

    public void assrt(String message) {
        log(LogLevel.ASSERT, message);
    }

    public void assrt(String template, Object... args) {
        log(LogLevel.ASSERT, template, args);
    }

    public void exception(Throwable e) {
        log(LogLevel.ERROR, createMessage("EXCEPTION: ", e.getClass().getSimpleName(), MESSAGE_DELIMITER, e.getMessage()));
    }

    public void exception(Throwable e, String template, Object... args) {
        log(LogLevel.ERROR, createMessage("EXCEPTION: ", e.getClass().getSimpleName(), MESSAGE_DELIMITER, String.format(template, args), MESSAGE_DELIMITER, e.getMessage()));
    }

    private String createMessage(Object... args) {
        if (args.length == 1 && (args[0] instanceof String)) {
            return args[0];
        }
        this.mStringBuilder.setLength(0);
        this.mStringBuilder.append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z' ").format(new Date()));
        StringBuilder sb = this.mStringBuilder;
        sb.append(ExifInterface.GPS_DIRECTION_TRUE);
        sb.append(Thread.currentThread().getId());
        sb.append(MESSAGE_DELIMITER);
        addFlattenedArray(args);
        return this.mStringBuilder.toString();
    }

    private void addFlattenedArray(Object[] args) {
        for (byte[] bArr : args) {
            if (bArr == null) {
                this.mStringBuilder.append(BuildConfig.TRAVIS);
            } else if (bArr instanceof byte[]) {
                addHexString(bArr);
            } else if (bArr instanceof Object[]) {
                addFlattenedArray((Object[]) bArr);
            } else {
                this.mStringBuilder.append(bArr.toString());
            }
        }
    }

    private void addHexString(byte[] bytes) {
        for (byte b : bytes) {
            int unsignedByte = b & 255;
            if (unsignedByte < 16) {
                this.mStringBuilder.append('0');
            }
            this.mStringBuilder.append(Integer.toHexString(unsignedByte));
        }
    }
}
