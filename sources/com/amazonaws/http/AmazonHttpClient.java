package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.RequestClientOptions;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.handlers.CredentialsRequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.CRC32MismatchException;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.TimingInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AmazonHttpClient {
    private static final String HEADER_SDK_RETRY_INFO = "aws-sdk-retry";
    private static final String HEADER_SDK_TRANSACTION_ID = "aws-sdk-invocation-id";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final int HTTP_STATUS_MULTIPLE_CHOICES = 300;
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_REQ_TOO_LONG = 413;
    private static final int HTTP_STATUS_SERVICE_UNAVAILABLE = 503;
    private static final int HTTP_STATUS_TEMP_REDIRECT = 307;
    private static final Log REQUEST_LOG = LogFactory.getLog("com.amazonaws.request");
    private static final int TIME_MILLISEC = 1000;
    static final Log log = LogFactory.getLog(AmazonHttpClient.class);
    final ClientConfiguration config;
    final HttpClient httpClient;
    private final HttpRequestFactory requestFactory;
    private final RequestMetricCollector requestMetricCollector;

    public AmazonHttpClient(ClientConfiguration config2) {
        this(config2, (HttpClient) new UrlHttpClient(config2));
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, RequestMetricCollector requestMetricCollector2) {
        this(config2, new UrlHttpClient(config2), requestMetricCollector2);
    }

    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = null;
    }

    @Deprecated
    public AmazonHttpClient(ClientConfiguration config2, HttpClient httpClient2, RequestMetricCollector requestMetricCollector2) {
        this.requestFactory = new HttpRequestFactory();
        this.config = config2;
        this.httpClient = httpClient2;
        this.requestMetricCollector = requestMetricCollector2;
    }

    @Deprecated
    public ResponseMetadata getResponseMetadataForRequest(AmazonWebServiceRequest request) {
        return null;
    }

    public <T> Response<T> execute(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponseHandler<AmazonServiceException> errorResponseHandler, ExecutionContext executionContext) {
        if (executionContext != null) {
            List<RequestHandler2> requestHandler2s = requestHandler2s(request, executionContext);
            AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
            Response<T> response = null;
            try {
                response = executeHelper(request, responseHandler, errorResponseHandler, executionContext);
                afterResponse(request, requestHandler2s, response, awsRequestMetrics.getTimingInfo().endTiming());
                return response;
            } catch (AmazonClientException e) {
                afterError(request, response, requestHandler2s, e);
                throw e;
            }
        } else {
            throw new AmazonClientException("Internal SDK Error: No execution context parameter specified.");
        }
    }

    /* access modifiers changed from: package-private */
    public void afterError(Request<?> request, Response<?> response, List<RequestHandler2> requestHandler2s, AmazonClientException e) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterError(request, response, e);
        }
    }

    /* access modifiers changed from: package-private */
    public <T> void afterResponse(Request<?> request, List<RequestHandler2> requestHandler2s, Response<T> response, TimingInfo timingInfo) {
        for (RequestHandler2 handler2 : requestHandler2s) {
            handler2.afterResponse(request, response);
        }
    }

    /* access modifiers changed from: package-private */
    public List<RequestHandler2> requestHandler2s(Request<?> request, ExecutionContext executionContext) {
        List<RequestHandler2> requestHandler2s = executionContext.getRequestHandler2s();
        if (requestHandler2s == null) {
            return Collections.emptyList();
        }
        for (RequestHandler2 requestHandler2 : requestHandler2s) {
            if (requestHandler2 instanceof CredentialsRequestHandler) {
                ((CredentialsRequestHandler) requestHandler2).setCredentials(executionContext.getCredentials());
            }
            requestHandler2.beforeRequest(request);
        }
        return requestHandler2s;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x0781 A[SYNTHETIC, Splitter:B:297:0x0781] */
    /* JADX WARNING: Removed duplicated region for block: B:301:0x07d9  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x0814 A[EDGE_INSN: B:316:0x0814->B:317:? ?: BREAK  , SYNTHETIC, Splitter:B:316:0x0814] */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x0824 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> com.amazonaws.Response<T> executeHelper(com.amazonaws.Request<?> r29, com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonWebServiceResponse<T>> r30, com.amazonaws.http.HttpResponseHandler<com.amazonaws.AmazonServiceException> r31, com.amazonaws.http.ExecutionContext r32) {
        /*
            r28 = this;
            r7 = r28
            r8 = r29
            r9 = r32
            r0 = 0
            com.amazonaws.util.AWSRequestMetrics r10 = r32.getAwsRequestMetrics()
            com.amazonaws.util.AWSRequestMetrics$Field r1 = com.amazonaws.util.AWSRequestMetrics.Field.ServiceName
            java.lang.String r2 = r29.getServiceName()
            r10.addProperty((com.amazonaws.metrics.MetricType) r1, (java.lang.Object) r2)
            com.amazonaws.util.AWSRequestMetrics$Field r1 = com.amazonaws.util.AWSRequestMetrics.Field.ServiceEndpoint
            java.net.URI r2 = r29.getEndpoint()
            r10.addProperty((com.amazonaws.metrics.MetricType) r1, (java.lang.Object) r2)
            r28.setUserAgent(r29)
            java.util.UUID r1 = java.util.UUID.randomUUID()
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "aws-sdk-invocation-id"
            r8.addHeader(r2, r1)
            r1 = 0
            r2 = 0
            r4 = 0
            r5 = 0
            java.util.LinkedHashMap r6 = new java.util.LinkedHashMap
            java.util.Map r11 = r29.getParameters()
            r6.<init>(r11)
            r11 = r6
            java.util.HashMap r6 = new java.util.HashMap
            java.util.Map r12 = r29.getHeaders()
            r6.<init>(r12)
            r12 = r6
            java.io.InputStream r13 = r29.getContent()
            if (r13 == 0) goto L_0x0056
            boolean r6 = r13.markSupported()
            if (r6 == 0) goto L_0x0056
            r6 = -1
            r13.mark(r6)
        L_0x0056:
            com.amazonaws.auth.AWSCredentials r14 = r32.getCredentials()
            r6 = 0
            r15 = 0
            r16 = 0
            r26 = r1
            r1 = r0
            r0 = r26
            r27 = r15
            r15 = r4
            r4 = r27
        L_0x0068:
            r17 = r1
            r1 = 1
            r18 = r4
            int r4 = r0 + 1
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.RequestCount
            r19 = r2
            long r1 = (long) r4
            r10.setCounter((com.amazonaws.metrics.MetricType) r0, (long) r1)
            r0 = 1
            if (r4 <= r0) goto L_0x0083
            r8.setParameters(r11)
            r8.setHeaders(r12)
            r8.setContent(r13)
        L_0x0083:
            if (r15 == 0) goto L_0x00bb
            java.net.URI r0 = r29.getEndpoint()
            if (r0 != 0) goto L_0x00bb
            java.lang.String r0 = r29.getResourcePath()
            if (r0 != 0) goto L_0x00bb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r15.getScheme()
            r0.append(r1)
            java.lang.String r1 = "://"
            r0.append(r1)
            java.lang.String r1 = r15.getAuthority()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.net.URI r0 = java.net.URI.create(r0)
            r8.setEndpoint(r0)
            java.lang.String r0 = r15.getPath()
            r8.setResourcePath(r0)
        L_0x00bb:
            java.lang.String r1 = "Cannot close the response content."
            r0 = 1
            if (r4 <= r0) goto L_0x015e
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.RetryPauseTime     // Catch:{ IOException -> 0x014f, RuntimeException -> 0x0141, Error -> 0x0133, all -> 0x0124 }
            r10.startEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x014f, RuntimeException -> 0x0141, Error -> 0x0133, all -> 0x0124 }
            com.amazonaws.AmazonWebServiceRequest r3 = r29.getOriginalRequest()     // Catch:{ all -> 0x011c }
            com.amazonaws.ClientConfiguration r2 = r7.config     // Catch:{ all -> 0x011c }
            com.amazonaws.retry.RetryPolicy r2 = r2.getRetryPolicy()     // Catch:{ all -> 0x011c }
            long r2 = r7.pauseBeforeNextRetry(r3, r5, r4, r2)     // Catch:{ all -> 0x011c }
            r10.endEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x010d, RuntimeException -> 0x0101, Error -> 0x00f5, all -> 0x00e8 }
            java.io.InputStream r0 = r29.getContent()     // Catch:{ IOException -> 0x010d, RuntimeException -> 0x0101, Error -> 0x00f5, all -> 0x00e8 }
            if (r0 == 0) goto L_0x0160
            boolean r19 = r0.markSupported()     // Catch:{ IOException -> 0x010d, RuntimeException -> 0x0101, Error -> 0x00f5, all -> 0x00e8 }
            if (r19 == 0) goto L_0x0160
            r0.reset()     // Catch:{ IOException -> 0x010d, RuntimeException -> 0x0101, Error -> 0x00f5, all -> 0x00e8 }
            goto L_0x0160
        L_0x00e8:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r20 = r11
            r1 = r17
            r4 = r18
            r11 = r0
            goto L_0x0822
        L_0x00f5:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r20 = r11
            r1 = r17
            r4 = r18
            goto L_0x0746
        L_0x0101:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r20 = r11
            r1 = r17
            r4 = r18
            goto L_0x075b
        L_0x010d:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r20 = r11
            r19 = r16
            r16 = r5
            r11 = r6
            goto L_0x0777
        L_0x011c:
            r0 = move-exception
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.RetryPauseTime     // Catch:{ IOException -> 0x014f, RuntimeException -> 0x0141, Error -> 0x0133, all -> 0x0124 }
            r10.endEvent((com.amazonaws.metrics.MetricType) r2)     // Catch:{ IOException -> 0x014f, RuntimeException -> 0x0141, Error -> 0x0133, all -> 0x0124 }
            throw r0     // Catch:{ IOException -> 0x014f, RuntimeException -> 0x0141, Error -> 0x0133, all -> 0x0124 }
        L_0x0124:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r2 = r19
            r20 = r11
            r11 = r0
            goto L_0x0822
        L_0x0133:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r2 = r19
            r20 = r11
            goto L_0x0746
        L_0x0141:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r2 = r19
            r20 = r11
            goto L_0x075b
        L_0x014f:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r21 = r19
            r20 = r11
            r19 = r16
            r16 = r5
            r11 = r6
            goto L_0x0777
        L_0x015e:
            r2 = r19
        L_0x0160:
            java.lang.String r0 = "aws-sdk-retry"
            r19 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0717, RuntimeException -> 0x0708, Error -> 0x06f9, all -> 0x06e8 }
            r5.<init>()     // Catch:{ IOException -> 0x0717, RuntimeException -> 0x0708, Error -> 0x06f9, all -> 0x06e8 }
            r20 = r11
            int r11 = r4 + -1
            r5.append(r11)     // Catch:{ IOException -> 0x06d9, RuntimeException -> 0x06cb, Error -> 0x06bd, all -> 0x06ae }
            java.lang.String r11 = "/"
            r5.append(r11)     // Catch:{ IOException -> 0x06d9, RuntimeException -> 0x06cb, Error -> 0x06bd, all -> 0x06ae }
            r5.append(r2)     // Catch:{ IOException -> 0x06d9, RuntimeException -> 0x06cb, Error -> 0x06bd, all -> 0x06ae }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x06d9, RuntimeException -> 0x06cb, Error -> 0x06bd, all -> 0x06ae }
            r8.addHeader(r0, r5)     // Catch:{ IOException -> 0x06d9, RuntimeException -> 0x06cb, Error -> 0x06bd, all -> 0x06ae }
            if (r6 != 0) goto L_0x01bf
            java.net.URI r0 = r29.getEndpoint()     // Catch:{ IOException -> 0x01b0, RuntimeException -> 0x01a4, Error -> 0x0198, all -> 0x018b }
            com.amazonaws.auth.Signer r0 = r9.getSignerByURI(r0)     // Catch:{ IOException -> 0x01b0, RuntimeException -> 0x01a4, Error -> 0x0198, all -> 0x018b }
            r11 = r0
            goto L_0x01c0
        L_0x018b:
            r0 = move-exception
            r11 = r0
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0822
        L_0x0198:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0746
        L_0x01a4:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x075b
        L_0x01b0:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r11 = r6
            r26 = r19
            r19 = r16
            r16 = r26
            goto L_0x0777
        L_0x01bf:
            r11 = r6
        L_0x01c0:
            if (r11 == 0) goto L_0x020f
            if (r14 == 0) goto L_0x020f
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.RequestSigningTime     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r10.startEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r11.sign(r8, r14)     // Catch:{ all -> 0x01d0 }
            r10.endEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            goto L_0x020f
        L_0x01d0:
            r0 = move-exception
            r5 = r0
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.RequestSigningTime     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r10.endEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            throw r5     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
        L_0x01d9:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r6 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            r11 = r0
            goto L_0x0822
        L_0x01e7:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r6 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0746
        L_0x01f4:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r6 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x075b
        L_0x0201:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r26 = r19
            r19 = r16
            r16 = r26
            goto L_0x0777
        L_0x020f:
            com.amazonaws.logging.Log r0 = REQUEST_LOG     // Catch:{ IOException -> 0x069e, RuntimeException -> 0x068c, Error -> 0x067a, all -> 0x0667 }
            boolean r5 = r0.isDebugEnabled()     // Catch:{ IOException -> 0x069e, RuntimeException -> 0x068c, Error -> 0x067a, all -> 0x0667 }
            if (r5 == 0) goto L_0x022f
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r5.<init>()     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            java.lang.String r6 = "Sending Request: "
            r5.append(r6)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            java.lang.String r6 = r29.toString()     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r5.append(r6)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
            r0.debug(r5)     // Catch:{ IOException -> 0x0201, RuntimeException -> 0x01f4, Error -> 0x01e7, all -> 0x01d9 }
        L_0x022f:
            com.amazonaws.http.HttpRequestFactory r0 = r7.requestFactory     // Catch:{ IOException -> 0x069e, RuntimeException -> 0x068c, Error -> 0x067a, all -> 0x0667 }
            com.amazonaws.ClientConfiguration r5 = r7.config     // Catch:{ IOException -> 0x069e, RuntimeException -> 0x068c, Error -> 0x067a, all -> 0x0667 }
            com.amazonaws.http.HttpRequest r0 = r0.createHttpRequest(r8, r5, r9)     // Catch:{ IOException -> 0x069e, RuntimeException -> 0x068c, Error -> 0x067a, all -> 0x0667 }
            r6 = r0
            r16 = 0
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.HttpRequestTime     // Catch:{ IOException -> 0x065b, RuntimeException -> 0x0645, Error -> 0x062f, all -> 0x0618 }
            r10.startEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x065b, RuntimeException -> 0x0645, Error -> 0x062f, all -> 0x0618 }
            com.amazonaws.http.HttpClient r5 = r7.httpClient     // Catch:{ all -> 0x05d4 }
            com.amazonaws.http.HttpResponse r5 = r5.execute(r6)     // Catch:{ all -> 0x05d4 }
            r10.endEvent((com.amazonaws.metrics.MetricType) r0)     // Catch:{ IOException -> 0x05c3, RuntimeException -> 0x05ad, Error -> 0x0597, all -> 0x0580 }
            boolean r0 = r7.isRequestSuccessful(r5)     // Catch:{ IOException -> 0x05c3, RuntimeException -> 0x05ad, Error -> 0x0597, all -> 0x0580 }
            if (r0 == 0) goto L_0x0390
            com.amazonaws.util.AWSRequestMetrics$Field r0 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x0382, RuntimeException -> 0x0372, Error -> 0x0362, all -> 0x0351 }
            int r18 = r5.getStatusCode()     // Catch:{ IOException -> 0x0382, RuntimeException -> 0x0372, Error -> 0x0362, all -> 0x0351 }
            r22 = r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r18)     // Catch:{ IOException -> 0x0345, RuntimeException -> 0x0335, Error -> 0x0325, all -> 0x0314 }
            r10.addProperty((com.amazonaws.metrics.MetricType) r0, (java.lang.Object) r2)     // Catch:{ IOException -> 0x0345, RuntimeException -> 0x0335, Error -> 0x0325, all -> 0x0314 }
            boolean r0 = r30.needsConnectionLeftOpen()     // Catch:{ IOException -> 0x0345, RuntimeException -> 0x0335, Error -> 0x0325, all -> 0x0314 }
            r2 = r0
            r3 = r30
            java.lang.Object r0 = r7.handleResponse(r8, r3, r5, r9)     // Catch:{ IOException -> 0x0306, RuntimeException -> 0x02f4, Error -> 0x02e2, all -> 0x02cf }
            r17 = r0
            com.amazonaws.Response r3 = new com.amazonaws.Response     // Catch:{ IOException -> 0x0306, RuntimeException -> 0x02f4, Error -> 0x02e2, all -> 0x02cf }
            r24 = r4
            r4 = r17
            r3.<init>(r4, r5)     // Catch:{ IOException -> 0x02c3, RuntimeException -> 0x02b3, Error -> 0x02a3, all -> 0x0292 }
            if (r2 != 0) goto L_0x028f
            java.io.InputStream r0 = r5.getRawContent()     // Catch:{ IOException -> 0x0286 }
            if (r0 == 0) goto L_0x0283
            java.io.InputStream r0 = r5.getRawContent()     // Catch:{ IOException -> 0x0286 }
            r0.close()     // Catch:{ IOException -> 0x0286 }
        L_0x0283:
            r17 = r2
            goto L_0x0291
        L_0x0286:
            r0 = move-exception
            r17 = r2
            com.amazonaws.logging.Log r2 = log
            r2.warn(r1, r0)
            goto L_0x0291
        L_0x028f:
            r17 = r2
        L_0x0291:
            return r3
        L_0x0292:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            r11 = r0
            goto L_0x0822
        L_0x02a3:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x0746
        L_0x02b3:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x075b
        L_0x02c3:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r18 = r5
            r19 = r6
            r21 = r22
            goto L_0x0777
        L_0x02cf:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            r11 = r0
            goto L_0x0822
        L_0x02e2:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x0746
        L_0x02f4:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x075b
        L_0x0306:
            r0 = move-exception
            r17 = r2
            r9 = r1
            r24 = r4
            r18 = r5
            r19 = r6
            r21 = r22
            goto L_0x0777
        L_0x0314:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            r11 = r0
            goto L_0x0822
        L_0x0325:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x0746
        L_0x0335:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x075b
        L_0x0345:
            r0 = move-exception
            r9 = r1
            r24 = r4
            r18 = r5
            r19 = r6
            r21 = r22
            goto L_0x0777
        L_0x0351:
            r0 = move-exception
            r22 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r16 = r6
            r6 = r11
            r11 = r0
            goto L_0x0822
        L_0x0362:
            r0 = move-exception
            r22 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r16 = r6
            r6 = r11
            goto L_0x0746
        L_0x0372:
            r0 = move-exception
            r22 = r2
            r9 = r1
            r24 = r4
            r4 = r5
            r5 = r16
            r1 = r17
            r16 = r6
            r6 = r11
            goto L_0x075b
        L_0x0382:
            r0 = move-exception
            r22 = r2
            r9 = r1
            r24 = r4
            r18 = r5
            r19 = r6
            r21 = r22
            goto L_0x0777
        L_0x0390:
            r22 = r2
            r24 = r4
            boolean r0 = isTemporaryRedirect(r5)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            if (r0 == 0) goto L_0x0421
            java.util.Map r0 = r5.getHeaders()     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.String r2 = "Location"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            com.amazonaws.logging.Log r2 = log     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r3.<init>()     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.String r4 = "Redirecting to: "
            r3.append(r4)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r3.append(r0)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r2.debug(r3)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.net.URI r2 = java.net.URI.create(r0)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r15 = r2
            r2 = 0
            r8.setEndpoint(r2)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r8.setResourcePath(r2)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            int r3 = r5.getStatusCode()     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.RedirectLocation     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r0)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r3 = 0
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0417, RuntimeException -> 0x0409, Error -> 0x03fb, all -> 0x03ec }
            r9 = r1
            r19 = r6
            r1 = r17
            r21 = r22
            r23 = r11
            r11 = r5
            goto L_0x0489
        L_0x03ec:
            r0 = move-exception
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            r11 = r0
            goto L_0x0822
        L_0x03fb:
            r0 = move-exception
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x0746
        L_0x0409:
            r0 = move-exception
            r9 = r1
            r4 = r5
            r5 = r16
            r1 = r17
            r2 = r22
            r16 = r6
            r6 = r11
            goto L_0x075b
        L_0x0417:
            r0 = move-exception
            r9 = r1
            r18 = r5
            r19 = r6
            r21 = r22
            goto L_0x0777
        L_0x0421:
            r3 = 0
            boolean r0 = r31.needsConnectionLeftOpen()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            r17 = r0
            r4 = r31
            com.amazonaws.AmazonServiceException r0 = r7.handleErrorResponse(r8, r4, r5)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            java.lang.String r3 = r0.getRequestId()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.AWSErrorCode     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            java.lang.String r3 = r0.getErrorCode()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            com.amazonaws.util.AWSRequestMetrics$Field r2 = com.amazonaws.util.AWSRequestMetrics.Field.StatusCode     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            int r3 = r0.getStatusCode()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            r10.addProperty((com.amazonaws.metrics.MetricType) r2, (java.lang.Object) r3)     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            com.amazonaws.AmazonWebServiceRequest r2 = r29.getOriginalRequest()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            java.io.InputStream r3 = r6.getContent()     // Catch:{ IOException -> 0x0571, RuntimeException -> 0x055b, Error -> 0x0545, all -> 0x052e }
            r25 = r1
            com.amazonaws.ClientConfiguration r1 = r7.config     // Catch:{ IOException -> 0x0526, RuntimeException -> 0x051e, Error -> 0x0516, all -> 0x050e }
            com.amazonaws.retry.RetryPolicy r18 = r1.getRetryPolicy()     // Catch:{ IOException -> 0x0526, RuntimeException -> 0x051e, Error -> 0x0516, all -> 0x050e }
            r9 = r25
            r1 = r28
            r21 = r22
            r23 = r11
            r11 = 0
            r4 = r0
            r11 = r5
            r5 = r24
            r19 = r6
            r6 = r18
            boolean r1 = r1.shouldRetry(r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x0507, RuntimeException -> 0x04f9, Error -> 0x04eb, all -> 0x04dc }
            if (r1 == 0) goto L_0x04da
            r5 = r0
            boolean r1 = com.amazonaws.retry.RetryUtils.isClockSkewError(r0)     // Catch:{ IOException -> 0x04d1, RuntimeException -> 0x04c5, Error -> 0x04b9, all -> 0x04ac }
            if (r1 == 0) goto L_0x0482
            int r1 = r7.parseClockSkewOffset(r11, r0)     // Catch:{ IOException -> 0x04d1, RuntimeException -> 0x04c5, Error -> 0x04b9, all -> 0x04ac }
            com.amazonaws.SDKGlobalConfiguration.setGlobalTimeOffset(r1)     // Catch:{ IOException -> 0x04d1, RuntimeException -> 0x04c5, Error -> 0x04b9, all -> 0x04ac }
        L_0x0482:
            r7.resetRequestAfterError(r8, r0)     // Catch:{ IOException -> 0x04d1, RuntimeException -> 0x04c5, Error -> 0x04b9, all -> 0x04ac }
            r16 = r5
            r1 = r17
        L_0x0489:
            if (r1 != 0) goto L_0x04a1
            if (r11 == 0) goto L_0x04a1
            java.io.InputStream r0 = r11.getRawContent()     // Catch:{ IOException -> 0x049b }
            if (r0 == 0) goto L_0x04a1
            java.io.InputStream r0 = r11.getRawContent()     // Catch:{ IOException -> 0x049b }
            r0.close()     // Catch:{ IOException -> 0x049b }
            goto L_0x04a1
        L_0x049b:
            r0 = move-exception
            com.amazonaws.logging.Log r2 = log
            r2.warn(r9, r0)
        L_0x04a1:
            r4 = r11
            r5 = r16
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x07ff
        L_0x04ac:
            r0 = move-exception
            r4 = r11
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            r11 = r0
            goto L_0x0822
        L_0x04b9:
            r0 = move-exception
            r4 = r11
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x0746
        L_0x04c5:
            r0 = move-exception
            r4 = r11
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x075b
        L_0x04d1:
            r0 = move-exception
            r16 = r5
            r18 = r11
            r11 = r23
            goto L_0x0777
        L_0x04da:
            throw r0     // Catch:{ IOException -> 0x0507, RuntimeException -> 0x04f9, Error -> 0x04eb, all -> 0x04dc }
        L_0x04dc:
            r0 = move-exception
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            r11 = r0
            goto L_0x0822
        L_0x04eb:
            r0 = move-exception
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x0746
        L_0x04f9:
            r0 = move-exception
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x075b
        L_0x0507:
            r0 = move-exception
            r18 = r11
            r11 = r23
            goto L_0x0777
        L_0x050e:
            r0 = move-exception
            r19 = r6
            r21 = r22
            r9 = r25
            goto L_0x0534
        L_0x0516:
            r0 = move-exception
            r19 = r6
            r21 = r22
            r9 = r25
            goto L_0x054b
        L_0x051e:
            r0 = move-exception
            r19 = r6
            r21 = r22
            r9 = r25
            goto L_0x0561
        L_0x0526:
            r0 = move-exception
            r19 = r6
            r21 = r22
            r9 = r25
            goto L_0x0577
        L_0x052e:
            r0 = move-exception
            r9 = r1
            r19 = r6
            r21 = r22
        L_0x0534:
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            r11 = r0
            goto L_0x0822
        L_0x0545:
            r0 = move-exception
            r9 = r1
            r19 = r6
            r21 = r22
        L_0x054b:
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x0746
        L_0x055b:
            r0 = move-exception
            r9 = r1
            r19 = r6
            r21 = r22
        L_0x0561:
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x075b
        L_0x0571:
            r0 = move-exception
            r9 = r1
            r19 = r6
            r21 = r22
        L_0x0577:
            r23 = r11
            r11 = r5
            r18 = r11
            r11 = r23
            goto L_0x0777
        L_0x0580:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r6 = r23
            r11 = r0
            goto L_0x0822
        L_0x0597:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r6 = r23
            goto L_0x0746
        L_0x05ad:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r11 = r5
            r4 = r11
            r5 = r16
            r1 = r17
            r16 = r19
            r6 = r23
            goto L_0x075b
        L_0x05c3:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r11 = r5
            r18 = r11
            r11 = r23
            goto L_0x0777
        L_0x05d4:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            com.amazonaws.util.AWSRequestMetrics$Field r1 = com.amazonaws.util.AWSRequestMetrics.Field.HttpRequestTime     // Catch:{ IOException -> 0x0613, RuntimeException -> 0x0604, Error -> 0x05f5, all -> 0x05e5 }
            r10.endEvent((com.amazonaws.metrics.MetricType) r1)     // Catch:{ IOException -> 0x0613, RuntimeException -> 0x0604, Error -> 0x05f5, all -> 0x05e5 }
            throw r0     // Catch:{ IOException -> 0x0613, RuntimeException -> 0x0604, Error -> 0x05f5, all -> 0x05e5 }
        L_0x05e5:
            r0 = move-exception
            r11 = r0
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x0822
        L_0x05f5:
            r0 = move-exception
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x0746
        L_0x0604:
            r0 = move-exception
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
            r6 = r23
            goto L_0x075b
        L_0x0613:
            r0 = move-exception
            r11 = r23
            goto L_0x0777
        L_0x0618:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r11 = r0
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r6 = r23
            goto L_0x0822
        L_0x062f:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r6 = r23
            goto L_0x0746
        L_0x0645:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r6 = r23
            goto L_0x075b
        L_0x065b:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r6
            r23 = r11
            goto L_0x0777
        L_0x0667:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r23 = r11
            r11 = r0
            r1 = r17
            r4 = r18
            r5 = r19
            r6 = r23
            goto L_0x0822
        L_0x067a:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r23 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            r6 = r23
            goto L_0x0746
        L_0x068c:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r23 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            r6 = r23
            goto L_0x075b
        L_0x069e:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r23 = r11
            r26 = r19
            r19 = r16
            r16 = r26
            goto L_0x0777
        L_0x06ae:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r11 = r0
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0822
        L_0x06bd:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0746
        L_0x06cb:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x075b
        L_0x06d9:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r11 = r6
            r26 = r19
            r19 = r16
            r16 = r26
            goto L_0x0777
        L_0x06e8:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r20 = r11
            r11 = r0
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0822
        L_0x06f9:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r20 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x0746
        L_0x0708:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r20 = r11
            r1 = r17
            r4 = r18
            r5 = r19
            goto L_0x075b
        L_0x0717:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r20 = r11
            r11 = r6
            r26 = r19
            r19 = r16
            r16 = r26
            goto L_0x0777
        L_0x0727:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r5
            r20 = r11
            r11 = r0
            r1 = r17
            r4 = r18
            goto L_0x0822
        L_0x0738:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r5
            r20 = r11
            r1 = r17
            r4 = r18
        L_0x0746:
            java.lang.Throwable r11 = r7.handleUnexpectedFailure(r0, r10)     // Catch:{ all -> 0x0762 }
            java.lang.Error r11 = (java.lang.Error) r11     // Catch:{ all -> 0x0762 }
            throw r11     // Catch:{ all -> 0x0762 }
        L_0x074d:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r5
            r20 = r11
            r1 = r17
            r4 = r18
        L_0x075b:
            java.lang.Throwable r11 = r7.handleUnexpectedFailure(r0, r10)     // Catch:{ all -> 0x0762 }
            java.lang.RuntimeException r11 = (java.lang.RuntimeException) r11     // Catch:{ all -> 0x0762 }
            throw r11     // Catch:{ all -> 0x0762 }
        L_0x0762:
            r0 = move-exception
            r11 = r0
            goto L_0x0822
        L_0x0766:
            r0 = move-exception
            r9 = r1
            r21 = r2
            r24 = r4
            r19 = r5
            r20 = r11
            r11 = r6
            r26 = r19
            r19 = r16
            r16 = r26
        L_0x0777:
            com.amazonaws.logging.Log r1 = log     // Catch:{ all -> 0x0815 }
            boolean r2 = r1.isDebugEnabled()     // Catch:{ all -> 0x0815 }
            java.lang.String r3 = "Unable to execute HTTP request: "
            if (r2 == 0) goto L_0x0797
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0815 }
            r2.<init>()     // Catch:{ all -> 0x0815 }
            r2.append(r3)     // Catch:{ all -> 0x0815 }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x0815 }
            r2.append(r4)     // Catch:{ all -> 0x0815 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0815 }
            r1.debug(r2, r0)     // Catch:{ all -> 0x0815 }
        L_0x0797:
            com.amazonaws.util.AWSRequestMetrics$Field r1 = com.amazonaws.util.AWSRequestMetrics.Field.Exception     // Catch:{ all -> 0x0815 }
            r10.incrementCounter((com.amazonaws.metrics.MetricType) r1)     // Catch:{ all -> 0x0815 }
            r10.addProperty((com.amazonaws.metrics.MetricType) r1, (java.lang.Object) r0)     // Catch:{ all -> 0x0815 }
            com.amazonaws.util.AWSRequestMetrics$Field r1 = com.amazonaws.util.AWSRequestMetrics.Field.AWSRequestID     // Catch:{ all -> 0x0815 }
            r2 = 0
            r10.addProperty((com.amazonaws.metrics.MetricType) r1, (java.lang.Object) r2)     // Catch:{ all -> 0x0815 }
            com.amazonaws.AmazonClientException r1 = new com.amazonaws.AmazonClientException     // Catch:{ all -> 0x0815 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0815 }
            r2.<init>()     // Catch:{ all -> 0x0815 }
            r2.append(r3)     // Catch:{ all -> 0x0815 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0815 }
            r2.append(r3)     // Catch:{ all -> 0x0815 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0815 }
            r1.<init>(r2, r0)     // Catch:{ all -> 0x0815 }
            r23 = r1
            com.amazonaws.AmazonWebServiceRequest r2 = r29.getOriginalRequest()     // Catch:{ all -> 0x0815 }
            java.io.InputStream r3 = r19.getContent()     // Catch:{ all -> 0x0815 }
            com.amazonaws.ClientConfiguration r1 = r7.config     // Catch:{ all -> 0x0815 }
            com.amazonaws.retry.RetryPolicy r6 = r1.getRetryPolicy()     // Catch:{ all -> 0x0815 }
            r1 = r28
            r4 = r23
            r5 = r24
            boolean r1 = r1.shouldRetry(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0815 }
            if (r1 == 0) goto L_0x0813
            r5 = r23
            r7.resetRequestAfterError(r8, r0)     // Catch:{ all -> 0x0807 }
            if (r17 != 0) goto L_0x07f6
            if (r18 == 0) goto L_0x07f6
            java.io.InputStream r0 = r18.getRawContent()     // Catch:{ IOException -> 0x07f0 }
            if (r0 == 0) goto L_0x07f6
            java.io.InputStream r0 = r18.getRawContent()     // Catch:{ IOException -> 0x07f0 }
            r0.close()     // Catch:{ IOException -> 0x07f0 }
            goto L_0x07f6
        L_0x07f0:
            r0 = move-exception
            com.amazonaws.logging.Log r1 = log
            r1.warn(r9, r0)
        L_0x07f6:
            r6 = r11
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
        L_0x07ff:
            r9 = r32
            r11 = r20
            r0 = r24
            goto L_0x0068
        L_0x0807:
            r0 = move-exception
            r6 = r11
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
            r11 = r0
            goto L_0x0822
        L_0x0813:
            throw r23     // Catch:{ all -> 0x0815 }
        L_0x0815:
            r0 = move-exception
            r6 = r11
            r5 = r16
            r1 = r17
            r4 = r18
            r16 = r19
            r2 = r21
            r11 = r0
        L_0x0822:
            if (r1 != 0) goto L_0x083f
            if (r4 == 0) goto L_0x083f
            java.io.InputStream r0 = r4.getRawContent()     // Catch:{ IOException -> 0x0836 }
            if (r0 == 0) goto L_0x0833
            java.io.InputStream r0 = r4.getRawContent()     // Catch:{ IOException -> 0x0836 }
            r0.close()     // Catch:{ IOException -> 0x0836 }
        L_0x0833:
            r17 = r1
            goto L_0x0841
        L_0x0836:
            r0 = move-exception
            r17 = r1
            com.amazonaws.logging.Log r1 = log
            r1.warn(r9, r0)
            goto L_0x0841
        L_0x083f:
            r17 = r1
        L_0x0841:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.http.AmazonHttpClient.executeHelper(com.amazonaws.Request, com.amazonaws.http.HttpResponseHandler, com.amazonaws.http.HttpResponseHandler, com.amazonaws.http.ExecutionContext):com.amazonaws.Response");
    }

    private <T extends Throwable> T handleUnexpectedFailure(T t, AWSRequestMetrics awsRequestMetrics) {
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.Exception;
        awsRequestMetrics.incrementCounter((MetricType) field);
        awsRequestMetrics.addProperty((MetricType) field, (Object) t);
        return t;
    }

    /* access modifiers changed from: package-private */
    public void resetRequestAfterError(Request<?> request, Exception cause) {
        if (request.getContent() != null) {
            if (request.getContent().markSupported()) {
                try {
                    request.getContent().reset();
                } catch (IOException e) {
                    throw new AmazonClientException("Encountered an exception and couldn't reset the stream to retry", cause);
                }
            } else {
                throw new AmazonClientException("Encountered an exception and stream is not resettable", cause);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setUserAgent(Request<?> request) {
        RequestClientOptions opts;
        String userAgentMarker;
        String userAgent = ClientConfiguration.DEFAULT_USER_AGENT;
        AmazonWebServiceRequest awsreq = request.getOriginalRequest();
        if (!(awsreq == null || (opts = awsreq.getRequestClientOptions()) == null || (userAgentMarker = opts.getClientMarker(RequestClientOptions.Marker.USER_AGENT)) == null)) {
            userAgent = createUserAgentString(userAgent, userAgentMarker);
        }
        if (!ClientConfiguration.DEFAULT_USER_AGENT.equals(this.config.getUserAgent())) {
            userAgent = createUserAgentString(userAgent, this.config.getUserAgent());
        }
        request.addHeader("User-Agent", userAgent);
    }

    static String createUserAgentString(String existingUserAgentString, String userAgent) {
        if (existingUserAgentString.contains(userAgent)) {
            return existingUserAgentString;
        }
        return existingUserAgentString.trim() + " " + userAgent.trim();
    }

    public void shutdown() {
        this.httpClient.shutdown();
    }

    private boolean shouldRetry(AmazonWebServiceRequest originalRequest, InputStream inputStream, AmazonClientException exception, int requestCount, RetryPolicy retryPolicy) {
        int retries = requestCount - 1;
        int maxErrorRetry = this.config.getMaxErrorRetry();
        if (maxErrorRetry < 0 || !retryPolicy.isMaxErrorRetryInClientConfigHonored()) {
            maxErrorRetry = retryPolicy.getMaxErrorRetry();
        }
        if (retries >= maxErrorRetry) {
            return false;
        }
        if (inputStream == null || inputStream.markSupported()) {
            return retryPolicy.getRetryCondition().shouldRetry(originalRequest, exception, retries);
        }
        Log log2 = log;
        if (log2.isDebugEnabled()) {
            log2.debug("Content not repeatable");
        }
        return false;
    }

    private static boolean isTemporaryRedirect(HttpResponse response) {
        int statusCode = response.getStatusCode();
        String location = response.getHeaders().get("Location");
        return statusCode == 307 && location != null && !location.isEmpty();
    }

    private boolean isRequestSuccessful(HttpResponse response) {
        int statusCode = response.getStatusCode();
        return statusCode >= 200 && statusCode < 300;
    }

    /* access modifiers changed from: package-private */
    public <T> T handleResponse(Request<?> request, HttpResponseHandler<AmazonWebServiceResponse<T>> responseHandler, HttpResponse response, ExecutionContext executionContext) {
        AWSRequestMetrics awsRequestMetrics;
        try {
            awsRequestMetrics = executionContext.getAwsRequestMetrics();
            AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ResponseProcessingTime;
            awsRequestMetrics.startEvent((MetricType) field);
            AmazonWebServiceResponse<? extends T> awsResponse = responseHandler.handle(response);
            awsRequestMetrics.endEvent((MetricType) field);
            if (awsResponse != null) {
                Log log2 = REQUEST_LOG;
                if (log2.isDebugEnabled()) {
                    log2.debug("Received successful response: " + response.getStatusCode() + ", AWS Request ID: " + awsResponse.getRequestId());
                }
                awsRequestMetrics.addProperty((MetricType) AWSRequestMetrics.Field.AWSRequestID, (Object) awsResponse.getRequestId());
                return awsResponse.getResult();
            }
            throw new RuntimeException("Unable to unmarshall response metadata. Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText());
        } catch (CRC32MismatchException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AmazonClientException("Unable to unmarshall response (" + e3.getMessage() + "). Response Code: " + response.getStatusCode() + ", Response Text: " + response.getStatusText(), e3);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ResponseProcessingTime);
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public AmazonServiceException handleErrorResponse(Request<?> request, HttpResponseHandler<AmazonServiceException> errorResponseHandler, HttpResponse response) {
        AmazonServiceException exception;
        int status = response.getStatusCode();
        try {
            exception = errorResponseHandler.handle(response);
            Log log2 = REQUEST_LOG;
            log2.debug("Received error response: " + exception.toString());
        } catch (Exception e) {
            if (status == 413) {
                exception = new AmazonServiceException("Request entity too large");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(413);
                exception.setErrorType(AmazonServiceException.ErrorType.Client);
                exception.setErrorCode("Request entity too large");
            } else if (status == 503 && "Service Unavailable".equalsIgnoreCase(response.getStatusText())) {
                exception = new AmazonServiceException("Service unavailable");
                exception.setServiceName(request.getServiceName());
                exception.setStatusCode(503);
                exception.setErrorType(AmazonServiceException.ErrorType.Service);
                exception.setErrorCode("Service unavailable");
            } else if (e instanceof IOException) {
                throw ((IOException) e);
            } else {
                throw new AmazonClientException("Unable to unmarshall error response (" + e.getMessage() + "). Response Code: " + status + ", Response Text: " + response.getStatusText() + ", Response Headers: " + response.getHeaders(), e);
            }
        }
        exception.setStatusCode(status);
        exception.setServiceName(request.getServiceName());
        exception.fillInStackTrace();
        return exception;
    }

    private long pauseBeforeNextRetry(AmazonWebServiceRequest originalRequest, AmazonClientException previousException, int requestCount, RetryPolicy retryPolicy) {
        int retries = (requestCount - 1) - 1;
        long delay = retryPolicy.getBackoffStrategy().delayBeforeNextRetry(originalRequest, previousException, retries);
        Log log2 = log;
        if (log2.isDebugEnabled()) {
            log2.debug("Retriable error detected, will retry in " + delay + "ms, attempt number: " + retries);
        }
        try {
            Thread.sleep(delay);
            return delay;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AmazonClientException(e.getMessage(), e);
        }
    }

    private String getServerDateFromException(String body) {
        int endPos;
        int startPos = body.indexOf("(");
        if (body.contains(" + 15")) {
            endPos = body.indexOf(" + 15");
        } else {
            endPos = body.indexOf(" - 15");
        }
        return body.substring(startPos + 1, endPos);
    }

    /* access modifiers changed from: package-private */
    public int parseClockSkewOffset(HttpResponse response, AmazonServiceException exception) {
        Date serverDate;
        Date deviceDate = new Date();
        String responseDateHeader = response.getHeaders().get("Date");
        if (responseDateHeader != null) {
            try {
                if (!responseDateHeader.isEmpty()) {
                    serverDate = DateUtils.parseRFC822Date(responseDateHeader);
                    return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
                }
            } catch (RuntimeException e) {
                Log log2 = log;
                log2.warn("Unable to parse clock skew offset from response: " + null, e);
                return 0;
            }
        }
        serverDate = DateUtils.parseCompressedISO8601Date(getServerDateFromException(exception.getMessage()));
        return (int) ((deviceDate.getTime() - serverDate.getTime()) / 1000);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        shutdown();
        super.finalize();
    }

    public RequestMetricCollector getRequestMetricCollector() {
        return this.requestMetricCollector;
    }
}
