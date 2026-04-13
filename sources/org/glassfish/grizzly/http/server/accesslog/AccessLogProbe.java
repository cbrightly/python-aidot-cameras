package org.glassfish.grizzly.http.server.accesslog;

import com.amazonaws.kinesisvideo.producer.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class AccessLogProbe extends HttpServerProbe.Adapter {
    private static final String ATTRIBUTE_TIME_STAMP = (AccessLogProbe.class.getName() + ".timeStamp");
    public static final int DEFAULT_STATUS_THRESHOLD = Integer.MIN_VALUE;
    private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
    private final AccessLogAppender appender;
    private final AccessLogFormat format;
    private final int statusThreshold;

    public AccessLogProbe(AccessLogAppender appender2, AccessLogFormat format2) {
        this(appender2, format2, Integer.MIN_VALUE);
    }

    public AccessLogProbe(AccessLogAppender appender2, AccessLogFormat format2, int statusThreshold2) {
        if (appender2 == null) {
            throw new NullPointerException("Null access log appender");
        } else if (format2 != null) {
            this.appender = appender2;
            this.format = format2;
            this.statusThreshold = statusThreshold2;
        } else {
            throw new NullPointerException("Null format");
        }
    }

    public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
        request.setAttribute(ATTRIBUTE_TIME_STAMP, Long.valueOf(System.nanoTime()));
        connection.getLocalAddress();
        connection.getPeerAddress();
    }

    public void onRequestCompleteEvent(HttpServerFilter filter, Connection connection, Response response) {
        if (response.getStatus() >= this.statusThreshold) {
            Long requestNanos = (Long) response.getRequest().getAttribute(ATTRIBUTE_TIME_STAMP);
            long timeStamp = System.currentTimeMillis();
            long responseNanos = requestNanos == null ? -1 : System.nanoTime() - requestNanos.longValue();
            try {
                this.appender.append(this.format.format(response, new Date(timeStamp - (responseNanos / Time.NANOS_IN_A_MILLISECOND)), responseNanos));
            } catch (Throwable throwable) {
                LOGGER.log(Level.WARNING, "Exception caught appending to access log", throwable);
            }
        }
    }
}
