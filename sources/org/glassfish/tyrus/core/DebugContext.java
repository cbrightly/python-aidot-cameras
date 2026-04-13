package org.glassfish.tyrus.core;

import com.amazonaws.kinesisvideo.producer.Time;
import com.google.android.material.timepicker.TimeModel;
import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class DebugContext {
    private List<LogRecord> logRecords;
    private String sessionId;
    private final long startTimestamp;
    private Map<String, List<String>> tracingHeaders;
    private final Level tracingLevel;

    public enum TracingThreshold {
        SUMMARY,
        TRACE
    }

    public enum TracingType {
        OFF,
        ON_DEMAND,
        ALL
    }

    public enum Type {
        MESSAGE_IN,
        MESSAGE_OUT,
        OTHER
    }

    public DebugContext(TracingThreshold tracingThreshold) {
        this.logRecords = new ArrayList();
        this.tracingHeaders = new HashMap();
        this.sessionId = null;
        this.startTimestamp = System.nanoTime();
        if (TracingThreshold.SUMMARY == tracingThreshold) {
            this.tracingLevel = Level.FINE;
        } else {
            this.tracingLevel = Level.FINER;
        }
    }

    public DebugContext() {
        this.logRecords = new ArrayList();
        this.tracingHeaders = new HashMap();
        this.sessionId = null;
        this.startTimestamp = System.nanoTime();
        this.tracingLevel = Level.OFF;
    }

    public void appendLogMessage(Logger logger, Level loggingLevel, Type type, Object... messageParts) {
        appendLogMessageWithThrowable(logger, loggingLevel, type, (Throwable) null, messageParts);
    }

    public void appendTraceMessage(Logger logger, Level loggingLevel, Type type, Object... messageParts) {
        appendTraceMessageWithThrowable(logger, loggingLevel, type, (Throwable) null, messageParts);
    }

    public void appendLogMessageWithThrowable(Logger logger, Level loggingLevel, Type type, Throwable t, Object... messageParts) {
        if (logger.isLoggable(loggingLevel)) {
            String message = stringifyMessageParts(messageParts);
            if (this.sessionId == null) {
                this.logRecords.add(new LogRecord(logger, loggingLevel, type, message, t, false));
            } else if (t != null) {
                logger.log(loggingLevel, formatLogMessage(message, type, System.nanoTime()), t);
            } else {
                logger.log(loggingLevel, formatLogMessage(message, type, System.nanoTime()));
            }
        }
    }

    public void appendTraceMessageWithThrowable(Logger logger, Level loggingLevel, Type type, Throwable t, Object... messageParts) {
        if (this.tracingLevel.intValue() <= loggingLevel.intValue()) {
            appendTracingHeader(stringifyMessageParts(messageParts));
        }
        appendLogMessageWithThrowable(logger, loggingLevel, type, t, messageParts);
    }

    public void appendStandardOutputMessage(Type type, String message) {
        if (this.sessionId == null) {
            this.logRecords.add(new LogRecord((Logger) null, Level.OFF, type, message, (Throwable) null, true));
        } else {
            System.out.println(formatLogMessage(message, type, System.nanoTime()));
        }
    }

    public void setSessionId(String sessionId2) {
        this.sessionId = sessionId2;
        flush();
    }

    public void flush() {
        if (this.sessionId == null) {
            this.sessionId = UUID.randomUUID().toString();
        }
        for (LogRecord logRecord : this.logRecords) {
            if (logRecord.printToSout) {
                System.out.println(formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp));
            } else if (logRecord.logger.isLoggable(logRecord.loggingLevel)) {
                if (logRecord.t != null) {
                    logRecord.logger.log(logRecord.loggingLevel, formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp), logRecord.t);
                } else {
                    logRecord.logger.log(logRecord.loggingLevel, formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp));
                }
            }
        }
        this.logRecords.clear();
    }

    public Map<String, List<String>> getTracingHeaders() {
        return this.tracingHeaders;
    }

    private void appendTracingHeader(String message) {
        Map<String, List<String>> map = this.tracingHeaders;
        map.put(UpgradeResponse.TRACING_HEADER_PREFIX + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(this.tracingHeaders.size())}), Arrays.asList(new String[]{Constants.ARRAY_TYPE + ((System.nanoTime() - this.startTimestamp) / Time.NANOS_IN_A_MILLISECOND) + " ms] " + message}));
    }

    private String formatLogMessage(String message, Type type, long timestamp) {
        String prefix;
        Type type2 = type;
        StringBuilder formattedMessage = new StringBuilder();
        List<String> messageLines = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(message, "\n", true);
        while (tokenizer.hasMoreTokens()) {
            messageLines.add(tokenizer.nextToken());
        }
        if (type2 == Type.MESSAGE_IN) {
            prefix = "< ";
        } else if (type2 == Type.MESSAGE_OUT) {
            prefix = "> ";
        } else {
            prefix = "* ";
        }
        boolean isFirst = true;
        for (String line : messageLines) {
            if (isFirst) {
                formattedMessage.append(prefix);
                formattedMessage.append("Session ");
                formattedMessage.append(this.sessionId);
                formattedMessage.append(" ");
                formattedMessage.append(Constants.ARRAY_TYPE);
                formattedMessage.append((timestamp - this.startTimestamp) / Time.NANOS_IN_A_MILLISECOND);
                formattedMessage.append(" ms]: ");
                formattedMessage.append(line);
                isFirst = false;
            } else {
                if (!"\n".equals(line)) {
                    formattedMessage.append(prefix);
                }
                formattedMessage.append(line);
            }
        }
        return formattedMessage.toString();
    }

    private String stringifyMessageParts(Object... messageParts) {
        StringBuilder sb = new StringBuilder();
        for (Object messagePart : messageParts) {
            sb.append(messagePart);
        }
        return sb.toString();
    }

    public static class LogRecord {
        /* access modifiers changed from: private */
        public Logger logger;
        /* access modifiers changed from: private */
        public Level loggingLevel;
        /* access modifiers changed from: private */
        public String message;
        /* access modifiers changed from: private */
        public boolean printToSout;
        /* access modifiers changed from: private */
        public Throwable t;
        /* access modifiers changed from: private */
        public long timestamp = System.nanoTime();
        /* access modifiers changed from: private */
        public Type type;

        LogRecord(Logger logger2, Level loggingLevel2, Type Type, String message2, Throwable t2, boolean printToSout2) {
            this.logger = logger2;
            this.loggingLevel = loggingLevel2;
            this.type = Type;
            this.message = message2;
            this.t = t2;
            this.printToSout = printToSout2;
        }
    }
}
