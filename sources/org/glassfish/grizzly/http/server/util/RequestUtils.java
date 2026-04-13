package org.glassfish.grizzly.http.server.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import org.glassfish.grizzly.ssl.SSLSupport;
import org.glassfish.grizzly.ssl.SSLSupportImpl;

public class RequestUtils {
    private static final Logger LOGGER = Grizzly.logger(RequestUtils.class);

    public static Object populateCertificateAttribute(Request request) {
        Object certificates = null;
        if (request.getRequest().isSecure()) {
            if (!request.getRequest().isUpgrade()) {
                try {
                    request.getInputBuffer().fillFully(request.getHttpFilter().getConfiguration().getMaxBufferedPostSize());
                } catch (IOException e) {
                    throw new IllegalStateException("Can't complete SSL re-negotation", e);
                }
            }
            try {
                certificates = new SSLBaseFilter.CertificateEvent(true).trigger(request.getContext()).get(30, TimeUnit.SECONDS);
            } catch (Exception e2) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Unable to obtain certificates from peer.", e2);
                }
            }
            request.setAttribute("jakarta.servlet.request.X509Certificate", certificates);
        }
        return certificates;
    }

    public static void populateSSLAttributes(Request request) {
        if (request.isSecure()) {
            try {
                SSLSupport sslSupport = new SSLSupportImpl(request.getContext().getConnection());
                Object sslO = sslSupport.getCipherSuite();
                if (sslO != null) {
                    request.setAttribute("jakarta.servlet.request.cipher_suite", sslO);
                }
                Object sslO2 = sslSupport.getPeerCertificateChain(false);
                if (sslO2 != null) {
                    request.setAttribute("jakarta.servlet.request.X509Certificate", sslO2);
                }
                Object sslO3 = sslSupport.getKeySize();
                if (sslO3 != null) {
                    request.setAttribute("jakarta.servlet.request.key_size", sslO3);
                }
                Object sslO4 = sslSupport.getSessionId();
                if (sslO4 != null) {
                    request.setAttribute(SSLSupport.SESSION_ID_KEY, sslO4);
                }
            } catch (Exception ioe) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Unable to populate SSL attributes", ioe);
                }
            }
        }
    }

    public static void handleSendFile(Request request) {
        Object f = request.getAttribute(Request.SEND_FILE_ATTR);
        if (f != null) {
            Response response = request.getResponse();
            if (response.isCommitted()) {
                Logger logger = LOGGER;
                Level level = Level.WARNING;
                if (logger.isLoggable(level)) {
                    logger.log(level, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_REQUESTUTILS_SENDFILE_FAILED());
                    return;
                }
                return;
            }
            File file = (File) f;
            Long offset = (Long) request.getAttribute(Request.SEND_FILE_START_OFFSET_ATTR);
            Long len = (Long) request.getAttribute(Request.SEND_FILE_WRITE_LEN_ATTR);
            if (offset == null) {
                offset = 0L;
            }
            if (len == null) {
                len = Long.valueOf(file.length());
            }
            response.getOutputBuffer().sendfile(file, offset.longValue(), len.longValue(), (CompletionHandler<WriteResult>) null);
        }
    }
}
