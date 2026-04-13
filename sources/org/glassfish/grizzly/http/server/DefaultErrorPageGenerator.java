package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;

public class DefaultErrorPageGenerator implements ErrorPageGenerator {
    public String generate(Request request, int status, String reasonPhrase, String description, Throwable exception) {
        if (status != 404) {
            return HtmlHelper.getExceptionErrorPage(reasonPhrase, description, request.getServerFilter().getFullServerName(), exception);
        }
        String reasonPhrase2 = HttpStatus.NOT_FOUND_404.getReasonPhrase();
        return HtmlHelper.getErrorPage(reasonPhrase2, "Resource identified by path '" + HttpUtils.filter(request.getRequestURI()) + "', does not exist.", request.getServerFilter().getFullServerName());
    }
}
