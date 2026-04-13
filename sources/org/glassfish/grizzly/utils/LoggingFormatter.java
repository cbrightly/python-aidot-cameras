package org.glassfish.grizzly.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingFormatter extends Formatter {
    private static String lineSeparator;
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(LoggingFormatter.class.getName());

    static {
        lineSeparator = "\n";
        try {
            String separator = System.getProperty("line.separator");
            if (separator != null && separator.trim().length() > 0) {
                lineSeparator = separator;
            }
        } catch (SecurityException e) {
        }
    }

    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer(128);
        sb.append('[');
        sb.append(Thread.currentThread().getName());
        sb.append("] ");
        sb.append(new Date(record.getMillis()).toString());
        sb.append(' ');
        sb.append('[');
        sb.append(record.getLevel().getLocalizedName());
        sb.append("] ");
        if (record.getSourceClassName() != null) {
            sb.append(record.getSourceClassName());
        } else {
            sb.append(record.getLoggerName());
        }
        if (record.getSourceMethodName() != null) {
            sb.append(' ');
            sb.append(record.getSourceMethodName());
        }
        sb.append(':');
        sb.append(lineSeparator);
        sb.append(formatMessage(record));
        sb.append(lineSeparator);
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception e) {
            }
        }
        sb.append(lineSeparator);
        return sb.toString();
    }

    public static void main(String[] args) {
        Logger logger = log;
        logger.info("Info Event");
        logger.severe("Severe Event");
        new Thread(new Runnable() {
            public void run() {
                LoggingFormatter.log.info("Info Event in Thread");
            }
        }, "Thread into main").start();
        logger.log(Level.SEVERE, "exception", new Exception());
    }
}
