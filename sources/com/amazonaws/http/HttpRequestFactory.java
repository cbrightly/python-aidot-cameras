package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.grizzly.http.GZipContentEncoding;

public class HttpRequestFactory {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public HttpRequest createHttpRequest(Request<?> request, ClientConfiguration clientConfiguration, ExecutionContext context) {
        boolean putParamsInUri = true;
        String uri = HttpUtils.appendUri(request.getEndpoint().toString(), request.getResourcePath(), true);
        String encodedParams = HttpUtils.encodeParameters(request);
        HttpMethodName method = request.getHttpMethod();
        boolean requestAlreadyHasPayload = request.getContent() != null;
        HttpMethodName httpMethodName = HttpMethodName.POST;
        if ((method == httpMethodName) && !requestAlreadyHasPayload) {
            putParamsInUri = false;
        }
        if (encodedParams != null && putParamsInUri) {
            uri = uri + "?" + encodedParams;
        }
        Map<String, String> headers = new HashMap<>();
        configureHeaders(headers, request, context, clientConfiguration);
        InputStream is = request.getContent();
        HttpMethodName httpMethodName2 = HttpMethodName.PATCH;
        if (method == httpMethodName2) {
            method = HttpMethodName.POST;
            headers.put("X-HTTP-Method-Override", httpMethodName2.toString());
        }
        if (method == httpMethodName && request.getContent() == null && encodedParams != null) {
            byte[] contentBytes = encodedParams.getBytes(StringUtils.UTF8);
            is = new ByteArrayInputStream(contentBytes);
            headers.put("Content-Length", String.valueOf(contentBytes.length));
        }
        if (!clientConfiguration.isEnableGzip() || headers.get(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING) != null) {
            headers.put(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, "identity");
        } else {
            headers.put(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, GZipContentEncoding.NAME);
        }
        HttpRequest httpRequest = new HttpRequest(method.toString(), URI.create(uri), headers, is);
        httpRequest.setStreaming(request.isStreaming());
        return httpRequest;
    }

    private void configureHeaders(Map<String, String> headers, Request<?> request, ExecutionContext context, ClientConfiguration clientConfiguration) {
        URI endpoint = request.getEndpoint();
        String hostHeader = endpoint.getHost();
        if (HttpUtils.isUsingNonDefaultPort(endpoint)) {
            hostHeader = hostHeader + ":" + endpoint.getPort();
        }
        headers.put("Host", hostHeader);
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        if (headers.get("Content-Type") == null || headers.get("Content-Type").isEmpty()) {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=" + StringUtils.lowerCase("UTF-8"));
        }
        if (context != null && context.getContextUserAgent() != null) {
            headers.put("User-Agent", createUserAgentString(clientConfiguration, context.getContextUserAgent()));
        }
    }

    private String createUserAgentString(ClientConfiguration clientConfiguration, String contextUserAgent) {
        if (clientConfiguration.getUserAgent().contains(contextUserAgent)) {
            return clientConfiguration.getUserAgent();
        }
        return clientConfiguration.getUserAgent() + " " + contextUserAgent;
    }
}
