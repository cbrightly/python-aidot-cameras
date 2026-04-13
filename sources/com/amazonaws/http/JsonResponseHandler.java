package com.amazonaws.http;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.transform.VoidJsonUnmarshaller;

public class JsonResponseHandler<T> implements HttpResponseHandler<AmazonWebServiceResponse<T>> {
    private static final Log log = LogFactory.getLog("com.amazonaws.request");
    public boolean needsConnectionLeftOpen = false;
    private Unmarshaller<T, JsonUnmarshallerContext> responseUnmarshaller;

    public JsonResponseHandler(Unmarshaller<T, JsonUnmarshallerContext> responseUnmarshaller2) {
        this.responseUnmarshaller = responseUnmarshaller2;
        if (responseUnmarshaller2 == null) {
            this.responseUnmarshaller = new VoidJsonUnmarshaller();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x0101 A[SYNTHETIC, Splitter:B:40:0x0101] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.amazonaws.AmazonWebServiceResponse<T> handle(com.amazonaws.http.HttpResponse r18) {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r2 = "Error closing json parser"
            com.amazonaws.logging.Log r0 = log
            java.lang.String r3 = "Parsing service response JSON"
            r0.trace(r3)
            java.util.Map r3 = r18.getHeaders()
            java.lang.String r4 = "x-amz-crc32"
            java.lang.Object r3 = r3.get(r4)
            java.lang.String r3 = (java.lang.String) r3
            java.io.InputStream r4 = r18.getRawContent()
            if (r4 != 0) goto L_0x002d
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream
            java.nio.charset.Charset r6 = com.amazonaws.util.StringUtils.UTF8
            java.lang.String r7 = "{}"
            byte[] r6 = r7.getBytes(r6)
            r5.<init>(r6)
            r4 = r5
        L_0x002d:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "CRC32Checksum = "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            r0.debug(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "content encoding = "
            r5.append(r6)
            java.util.Map r6 = r18.getHeaders()
            java.lang.String r7 = "Content-Encoding"
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.debug(r5)
            java.util.Map r5 = r18.getHeaders()
            java.lang.Object r5 = r5.get(r7)
            java.lang.String r6 = "gzip"
            boolean r5 = r6.equals(r5)
            r6 = 0
            if (r3 == 0) goto L_0x0079
            com.amazonaws.util.CRC32ChecksumCalculatingInputStream r7 = new com.amazonaws.util.CRC32ChecksumCalculatingInputStream
            r7.<init>(r4)
            r6 = r7
            r4 = r6
        L_0x0079:
            if (r5 == 0) goto L_0x0081
            java.util.zip.GZIPInputStream r7 = new java.util.zip.GZIPInputStream
            r7.<init>(r4)
            r4 = r7
        L_0x0081:
            java.io.InputStreamReader r7 = new java.io.InputStreamReader
            java.nio.charset.Charset r8 = com.amazonaws.util.StringUtils.UTF8
            r7.<init>(r4, r8)
            com.amazonaws.util.json.AwsJsonReader r7 = com.amazonaws.util.json.JsonUtils.getJsonReader(r7)
            com.amazonaws.AmazonWebServiceResponse r8 = new com.amazonaws.AmazonWebServiceResponse     // Catch:{ all -> 0x00f7 }
            r8.<init>()     // Catch:{ all -> 0x00f7 }
            com.amazonaws.transform.JsonUnmarshallerContext r9 = new com.amazonaws.transform.JsonUnmarshallerContext     // Catch:{ all -> 0x00f7 }
            r10 = r18
            r9.<init>(r7, r10)     // Catch:{ all -> 0x00f5 }
            com.amazonaws.transform.Unmarshaller<T, com.amazonaws.transform.JsonUnmarshallerContext> r11 = r1.responseUnmarshaller     // Catch:{ all -> 0x00f5 }
            java.lang.Object r11 = r11.unmarshall(r9)     // Catch:{ all -> 0x00f5 }
            if (r6 == 0) goto L_0x00b9
            long r12 = java.lang.Long.parseLong(r3)     // Catch:{ all -> 0x00f5 }
            long r14 = r6.getCRC32Checksum()     // Catch:{ all -> 0x00f5 }
            int r16 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r16 != 0) goto L_0x00af
            r16 = r3
            goto L_0x00bb
        L_0x00af:
            com.amazonaws.internal.CRC32MismatchException r0 = new com.amazonaws.internal.CRC32MismatchException     // Catch:{ all -> 0x00f5 }
            r16 = r3
            java.lang.String r3 = "Client calculated crc32 checksum didn't match that calculated by server side"
            r0.<init>(r3)     // Catch:{ all -> 0x00f2 }
            throw r0     // Catch:{ all -> 0x00f2 }
        L_0x00b9:
            r16 = r3
        L_0x00bb:
            r8.setResult(r11)     // Catch:{ all -> 0x00f2 }
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ all -> 0x00f2 }
            r3.<init>()     // Catch:{ all -> 0x00f2 }
            java.lang.String r12 = "AWS_REQUEST_ID"
            java.util.Map r13 = r18.getHeaders()     // Catch:{ all -> 0x00f2 }
            java.lang.String r14 = "x-amzn-RequestId"
            java.lang.Object r13 = r13.get(r14)     // Catch:{ all -> 0x00f2 }
            r3.put(r12, r13)     // Catch:{ all -> 0x00f2 }
            com.amazonaws.ResponseMetadata r12 = new com.amazonaws.ResponseMetadata     // Catch:{ all -> 0x00f2 }
            r12.<init>((java.util.Map<java.lang.String, java.lang.String>) r3)     // Catch:{ all -> 0x00f2 }
            r8.setResponseMetadata(r12)     // Catch:{ all -> 0x00f2 }
            java.lang.String r12 = "Done parsing service response"
            r0.trace(r12)     // Catch:{ all -> 0x00f2 }
            boolean r0 = r1.needsConnectionLeftOpen
            if (r0 != 0) goto L_0x00f1
            r7.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00f1
        L_0x00e9:
            r0 = move-exception
            r12 = r0
            r0 = r12
            com.amazonaws.logging.Log r12 = log
            r12.warn(r2, r0)
        L_0x00f1:
            return r8
        L_0x00f2:
            r0 = move-exception
            r3 = r0
            goto L_0x00fd
        L_0x00f5:
            r0 = move-exception
            goto L_0x00fa
        L_0x00f7:
            r0 = move-exception
            r10 = r18
        L_0x00fa:
            r16 = r3
            r3 = r0
        L_0x00fd:
            boolean r0 = r1.needsConnectionLeftOpen
            if (r0 != 0) goto L_0x010d
            r7.close()     // Catch:{ IOException -> 0x0105 }
            goto L_0x010d
        L_0x0105:
            r0 = move-exception
            r8 = r0
            r0 = r8
            com.amazonaws.logging.Log r8 = log
            r8.warn(r2, r0)
        L_0x010d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.http.JsonResponseHandler.handle(com.amazonaws.http.HttpResponse):com.amazonaws.AmazonWebServiceResponse");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void registerAdditionalMetadataExpressions(JsonUnmarshallerContext unmarshallerContext) {
    }

    public boolean needsConnectionLeftOpen() {
        return this.needsConnectionLeftOpen;
    }
}
