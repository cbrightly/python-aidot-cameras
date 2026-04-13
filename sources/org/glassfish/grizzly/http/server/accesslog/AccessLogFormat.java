package org.glassfish.grizzly.http.server.accesslog;

import java.util.Date;
import org.glassfish.grizzly.http.server.Response;

public interface AccessLogFormat {
    String format(Response response, Date date, long j);
}
