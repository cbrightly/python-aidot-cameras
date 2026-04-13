package org.glassfish.grizzly.http.server.util;

import com.yanzhenjie.andserver.util.h;
import java.io.Writer;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.HttpUtils;

public class HtmlHelper {
    private static final String CSS = "div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}";
    private static final int MAX_STACK_ELEMENTS = 10;

    public static void sendErrorPage(Request request, Response response, ErrorPageGenerator generator, int status, String reasonPhrase, String description, Throwable exception) {
        if (generator != null && !response.isCommitted() && response.getOutputBuffer().getBufferedDataSize() == 0) {
            String errorPage = generator.generate(request, status, reasonPhrase, description, exception);
            Writer writer = response.getWriter();
            if (errorPage != null) {
                if (!response.getResponse().isContentTypeSet()) {
                    response.setContentType(h.TEXT_HTML_VALUE);
                }
                writer.write(errorPage);
            }
            writer.close();
        }
    }

    public static void setErrorAndSendErrorPage(Request request, Response response, ErrorPageGenerator generator, int status, String reasonPhrase, String description, Throwable exception) {
        response.setStatus(status, reasonPhrase);
        if (generator != null && !response.isCommitted() && response.getOutputBuffer().getBufferedDataSize() == 0) {
            String errorPage = generator.generate(request, status, reasonPhrase, description, exception);
            Writer writer = response.getWriter();
            if (errorPage != null) {
                if (!response.getResponse().isContentTypeSet()) {
                    response.setContentType(h.TEXT_HTML_VALUE);
                }
                writer.write(errorPage);
            }
            writer.close();
        }
    }

    public static void writeTraceMessage(Request request, Response response) {
        response.setStatus(HttpStatus.OK_200);
        response.setContentType("message/http");
        Writer writer = response.getWriter();
        writer.append(request.getMethod().toString()).append(' ').append(request.getRequest().getRequestURIRef().getOriginalRequestURIBC().toString()).append(' ').append(request.getProtocol().getProtocolString()).append("\r\n");
        for (String headerName : request.getHeaderNames()) {
            for (String headerValue : request.getHeaders(headerName)) {
                writer.append(headerName).append(": ").append(headerValue).append("\r\n");
            }
        }
    }

    public static String getErrorPage(String headerMessage, String message, String serverName) {
        return prepareBody(headerMessage, message, serverName);
    }

    public static String getExceptionErrorPage(String headerMessage, String message, String serverName, Throwable t) {
        return prepareExceptionBody(headerMessage, message, serverName, t);
    }

    private static String prepareBody(String headerMessage, String message, String serverName) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>");
        sb.append(serverName);
        sb.append("</title>");
        sb.append("<style><!--");
        sb.append(CSS);
        sb.append("--></style> ");
        sb.append("</head><body>");
        sb.append("<div class=\"header\">");
        sb.append(headerMessage);
        sb.append("</div>");
        sb.append("<div class=\"body\">");
        sb.append(message != null ? message : "<HR size=\"1\" noshade>");
        sb.append("</div>");
        sb.append("<div class=\"footer\">");
        sb.append(serverName);
        sb.append("</div>");
        sb.append("</body></html>");
        return sb.toString();
    }

    private static String prepareExceptionBody(String headerMessage, String message, String serverName, Throwable t) {
        if (t == null) {
            return prepareBody(headerMessage, message, serverName);
        }
        Throwable rootCause = getRootCause(t);
        StackTraceElement[] elements = t.getStackTrace();
        StackTraceElement[] rootCauseElements = null;
        if (rootCause != null) {
            rootCauseElements = rootCause.getStackTrace();
        }
        StringBuilder tBuilder = new StringBuilder();
        formatStackElements(elements, tBuilder);
        StringBuilder rootBuilder = new StringBuilder();
        if (rootCause != null) {
            formatStackElements(rootCauseElements, rootBuilder);
        }
        String exMessage = HttpUtils.filter(t.getMessage() != null ? t.getMessage() : t.toString());
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>");
        sb.append(serverName);
        sb.append("</title>");
        sb.append("<style><!--");
        sb.append(CSS);
        sb.append("--></style> ");
        sb.append("</head><body>");
        sb.append("<div class=\"header\">");
        sb.append(headerMessage);
        sb.append("</div>");
        sb.append("<div class=\"body\">");
        sb.append("<b>");
        sb.append(exMessage);
        sb.append("</b>");
        sb.append("<pre>");
        sb.append(tBuilder.toString());
        sb.append("</pre>");
        if (rootCause != null) {
            sb.append("<b>Root Cause: ");
            sb.append(rootCause.toString());
            sb.append("</b>");
            sb.append("<pre>");
            sb.append(rootBuilder.toString());
            sb.append("</pre>");
        }
        sb.append("Please see the log for more detail.");
        sb.append("</div>");
        sb.append("<div class=\"footer\">");
        sb.append(serverName);
        sb.append("</div>");
        sb.append("</body></html>");
        return sb.toString();
    }

    private static Throwable getRootCause(Throwable t) {
        Throwable rootCause = null;
        if (t.getCause() != null) {
            rootCause = t.getCause();
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
        }
        return rootCause;
    }

    private static void formatStackElements(StackTraceElement[] elements, StringBuilder builder) {
        int maxLines = getMaxStackElementsToDisplay(elements);
        for (int i = 0; i < maxLines; i++) {
            builder.append(i + 1 > 9 ? "    " : "     ");
            builder.append(i + 1);
            builder.append(": ");
            builder.append(elements[i].toString());
            builder.append(10);
        }
        if (elements.length > 10) {
            builder.append("        ... ");
            builder.append(elements.length - 10);
            builder.append(" more");
        }
    }

    private static int getMaxStackElementsToDisplay(StackTraceElement[] elements) {
        if (elements.length > 10) {
            return 10;
        }
        return elements.length;
    }
}
