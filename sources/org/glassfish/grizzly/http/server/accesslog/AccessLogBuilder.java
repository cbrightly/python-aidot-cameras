package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;
import org.glassfish.grizzly.http.server.ServerConfiguration;

public class AccessLogBuilder {
    private final File file;
    private AccessLogFormat format = ApacheLogFormat.COMBINED;
    private String rotationPattern;
    private int statusThreshold = Integer.MIN_VALUE;
    private boolean synchronous;

    public AccessLogBuilder(String file2) {
        if (file2 != null) {
            this.file = new File(file2).getAbsoluteFile();
            return;
        }
        throw new NullPointerException("Null file");
    }

    public AccessLogBuilder(File file2) {
        if (file2 != null) {
            this.file = file2;
            return;
        }
        throw new NullPointerException("Null file");
    }

    public AccessLogProbe build() {
        AccessLogAppender appender;
        String extension;
        String base;
        try {
            if (this.rotationPattern == null) {
                appender = new FileAppender(this.file.getCanonicalFile());
            } else {
                File directory = this.file.getCanonicalFile().getParentFile();
                String name = this.file.getName();
                int position = name.lastIndexOf(".");
                if (position < 0) {
                    base = name.replace("'", "''");
                    extension = "";
                } else {
                    String base2 = name.substring(0, position).replace("'", "''");
                    extension = name.substring(position).replace("'", "''");
                    base = base2;
                }
                appender = new RotatingFileAppender(directory, name, '\'' + base + "'-" + this.rotationPattern + '\'' + extension + '\'');
            }
            if (!this.synchronous) {
                appender = new QueueingAppender(appender);
            }
            return new AccessLogProbe(appender, this.format, this.statusThreshold);
        } catch (IOException exception) {
            throw new IllegalStateException("I/O error creating acces log", exception);
        }
    }

    public ServerConfiguration instrument(ServerConfiguration serverConfiguration) {
        serverConfiguration.getMonitoringConfig().getWebServerConfig().addProbes(build());
        return serverConfiguration;
    }

    public AccessLogBuilder format(AccessLogFormat format2) {
        if (format2 != null) {
            this.format = format2;
            return this;
        }
        throw new NullPointerException("Null format");
    }

    public AccessLogBuilder format(String format2) {
        if (format2 != null) {
            return format((AccessLogFormat) new ApacheLogFormat(format2));
        }
        throw new NullPointerException("Null format");
    }

    public AccessLogBuilder timeZone(TimeZone timeZone) {
        if (timeZone != null) {
            AccessLogFormat accessLogFormat = this.format;
            if (accessLogFormat instanceof ApacheLogFormat) {
                this.format = new ApacheLogFormat(timeZone, ((ApacheLogFormat) accessLogFormat).getFormat());
                return this;
            }
            throw new IllegalStateException("TimeZone can not be set for " + this.format.getClass().getName());
        }
        throw new NullPointerException("Null time zone");
    }

    public AccessLogBuilder timeZone(String timeZone) {
        if (timeZone != null) {
            return timeZone(TimeZone.getTimeZone(timeZone));
        }
        throw new NullPointerException("Null time zone");
    }

    public AccessLogBuilder statusThreshold(int statusThreshold2) {
        this.statusThreshold = statusThreshold2;
        return this;
    }

    public AccessLogBuilder rotatedHourly() {
        return rotationPattern("yyyyMMDDhh");
    }

    public AccessLogBuilder rotatedDaily() {
        return rotationPattern("yyyyMMDD");
    }

    public AccessLogBuilder rotationPattern(String rotationPattern2) {
        if (rotationPattern2 != null) {
            this.rotationPattern = rotationPattern2;
            return this;
        }
        throw new NullPointerException("Null rotation pattern");
    }

    public AccessLogBuilder synchronous(boolean synchronous2) {
        this.synchronous = synchronous2;
        return this;
    }
}
