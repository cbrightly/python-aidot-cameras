package org.glassfish.grizzly.http.util;

import io.netty.util.internal.StringUtil;
import java.io.CharConversionException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.util.DataChunk;

public final class Parameters {
    public static final Charset DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
    public static final String DEFAULT_ENCODING = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
    public static final int INITIAL_SIZE = 4;
    private static final Logger LOGGER = Grizzly.logger(Parameters.class);
    private Parameters child = null;
    private Parameters currentChild = null;
    final DataChunk decodedQuery = DataChunk.newInstance();
    private boolean didMerge = false;
    private boolean didQueryParameters = false;
    Charset encoding = null;
    MimeHeaders headers;
    private int limit = -1;
    private final BufferChunk origName = new BufferChunk();
    private final BufferChunk origValue = new BufferChunk();
    private final LinkedHashMap<String, ArrayList<String>> paramHashValues = new LinkedHashMap<>();
    private int parameterCount = 0;
    private Parameters parent = null;
    DataChunk queryDC;
    Charset queryStringEncoding = null;
    final BufferChunk tmpName = new BufferChunk();
    final CharChunk tmpNameC = new CharChunk(1024);
    final BufferChunk tmpValue = new BufferChunk();
    final CharChunk tmpValueC = new CharChunk(1024);

    public void setQuery(DataChunk queryBC) {
        this.queryDC = queryBC;
    }

    public void setHeaders(MimeHeaders headers2) {
        this.headers = headers2;
    }

    public void setLimit(int limit2) {
        this.limit = limit2;
    }

    public void setEncoding(Charset encoding2) {
        this.encoding = encoding2;
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "Set encoding to {0}", encoding2);
        }
    }

    public Charset getEncoding() {
        return this.encoding;
    }

    public void setQueryStringEncoding(Charset queryStringEncoding2) {
        this.queryStringEncoding = queryStringEncoding2;
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "Set query string encoding to {0}", queryStringEncoding2);
        }
    }

    public Charset getQueryStringEncoding() {
        return this.queryStringEncoding;
    }

    public void recycle() {
        this.paramHashValues.clear();
        this.didQueryParameters = false;
        this.currentChild = null;
        this.didMerge = false;
        this.encoding = null;
        this.queryStringEncoding = null;
        this.parameterCount = 0;
        this.decodedQuery.recycle();
    }

    public Parameters getCurrentSet() {
        Parameters parameters = this.currentChild;
        if (parameters == null) {
            return this;
        }
        return parameters;
    }

    public void push() {
        Parameters parameters = this.currentChild;
        if (parameters == null) {
            Parameters parameters2 = new Parameters();
            this.currentChild = parameters2;
            parameters2.parent = this;
            return;
        }
        if (parameters.child == null) {
            parameters.child = new Parameters();
            Parameters parameters3 = this.currentChild;
            parameters3.child.parent = parameters3;
        }
        Parameters parameters4 = this.currentChild.child;
        this.currentChild = parameters4;
        parameters4.setEncoding(this.encoding);
    }

    public void pop() {
        Parameters parameters = this.currentChild;
        if (parameters != null) {
            parameters.recycle();
            this.currentChild = this.currentChild.parent;
            return;
        }
        throw new RuntimeException("Attempt to pop without a push");
    }

    public void addParameterValues(String key, String[] newValues) {
        ArrayList<String> values;
        if (key != null) {
            if (this.paramHashValues.containsKey(key)) {
                values = this.paramHashValues.get(key);
            } else {
                values = new ArrayList<>(1);
                this.paramHashValues.put(key, values);
            }
            values.ensureCapacity(values.size() + newValues.length);
            Collections.addAll(values, newValues);
        }
    }

    public String[] getParameterValues(String name) {
        ArrayList<String> values;
        handleQueryParameters();
        Parameters parameters = this.currentChild;
        if (parameters != null) {
            parameters.merge();
            values = this.currentChild.paramHashValues.get(name);
        } else {
            values = this.paramHashValues.get(name);
        }
        if (values != null) {
            return (String[]) values.toArray(new String[values.size()]);
        }
        return null;
    }

    public Set<String> getParameterNames() {
        handleQueryParameters();
        Parameters parameters = this.currentChild;
        if (parameters != null) {
            parameters.merge();
            this.currentChild.paramHashValues.keySet();
        }
        return this.paramHashValues.keySet();
    }

    private void merge() {
        Parameters parameters;
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level)) {
            logger.log(level, "Before merging {0} {1} {2}", new Object[]{this, this.parent, Boolean.valueOf(this.didMerge)});
            logger.log(level, paramsAsString());
        }
        handleQueryParameters();
        if (!this.didMerge && (parameters = this.parent) != null) {
            parameters.merge();
            merge2(this.paramHashValues, this.parent.paramHashValues);
            this.didMerge = true;
            if (logger.isLoggable(level)) {
                logger.log(level, "After {0}", paramsAsString());
            }
        }
    }

    public String getParameter(String name) {
        ArrayList<String> values = this.paramHashValues.get(name);
        if (values == null) {
            return null;
        }
        if (values.isEmpty()) {
            return "";
        }
        return values.get(0);
    }

    public void handleQueryParameters() {
        if (!this.didQueryParameters) {
            this.didQueryParameters = true;
            DataChunk dataChunk = this.queryDC;
            if (dataChunk != null && !dataChunk.isNull()) {
                Logger logger = LOGGER;
                Level level = Level.FINEST;
                if (logger.isLoggable(level)) {
                    logger.log(level, "Decoding query {0} {1}", new Object[]{this.queryDC, this.queryStringEncoding});
                }
                this.decodedQuery.duplicate(this.queryDC);
                processParameters(this.decodedQuery, this.queryStringEncoding);
            }
        }
    }

    private static void merge2(LinkedHashMap<String, ArrayList<String>> one, LinkedHashMap<String, ArrayList<String>> two) {
        ArrayList<String> combinedValue;
        for (String name : two.keySet()) {
            ArrayList<String> oneValue = one.get(name);
            ArrayList<String> twoValue = two.get(name);
            if (twoValue != null) {
                if (oneValue == null) {
                    combinedValue = new ArrayList<>(twoValue);
                } else {
                    combinedValue = new ArrayList<>(oneValue.size() + twoValue.size());
                    combinedValue.addAll(oneValue);
                    combinedValue.addAll(twoValue);
                }
                one.put(name, combinedValue);
            }
        }
    }

    public void addParameter(String key, String value) {
        if (key != null) {
            int i = this.parameterCount + 1;
            this.parameterCount = i;
            int i2 = this.limit;
            if (i2 <= -1 || i <= i2) {
                ArrayList<String> values = this.paramHashValues.get(key);
                if (values == null) {
                    values = new ArrayList<>(1);
                    this.paramHashValues.put(key, values);
                }
                values.add(value);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public void processParameters(Buffer buffer, int start, int len) {
        processParameters(buffer, start, len, this.encoding);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processParameters(org.glassfish.grizzly.Buffer r22, int r23, int r24, java.nio.charset.Charset r25) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r25
            java.util.logging.Logger r0 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINEST
            boolean r6 = r0.isLoggable(r5)
            r7 = 1
            if (r6 == 0) goto L_0x0034
            r6 = 4
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r8 = 0
            r6[r8] = r2
            java.lang.Integer r8 = java.lang.Integer.valueOf(r23)
            r6[r7] = r8
            r8 = 2
            java.lang.Integer r9 = java.lang.Integer.valueOf(r24)
            r6[r8] = r9
            r8 = 3
            int r9 = r3 + r24
            java.lang.String r9 = r2.toStringContent(r4, r3, r9)
            r6[r8] = r9
            java.lang.String r8 = "Process parameters. Buffer: {0} start={1} len={2} content={3}"
            r0.log(r5, r8, r6)
        L_0x0034:
            r0 = 0
            int r5 = r3 + r24
            r6 = r23
            r20 = r6
            r6 = r0
            r0 = r20
        L_0x003e:
            if (r0 >= r5) goto L_0x01d6
            int r8 = r1.limit
            r9 = -1
            if (r8 <= r9) goto L_0x005a
            int r10 = r1.parameterCount
            if (r10 < r8) goto L_0x005a
            java.util.logging.Logger r9 = LOGGER
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.String r8 = org.glassfish.grizzly.localization.LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(r8)
            r9.warning(r8)
            r18 = r5
            goto L_0x01d8
        L_0x005a:
            r8 = r0
            r10 = -1
            r11 = -1
            r12 = -1
            r13 = 1
            r14 = 0
            r15 = 0
            r16 = 0
        L_0x0063:
            byte r17 = r2.get((int) r0)
            switch(r17) {
                case 37: goto L_0x0096;
                case 38: goto L_0x0087;
                case 43: goto L_0x0096;
                case 61: goto L_0x0072;
                default: goto L_0x006a;
            }
        L_0x006a:
            int r0 = r0 + 1
            r20 = r10
            r10 = r0
            r0 = r20
            goto L_0x00a2
        L_0x0072:
            if (r13 == 0) goto L_0x007f
            r10 = r0
            r13 = 0
            int r0 = r0 + 1
            r11 = r0
            r20 = r10
            r10 = r0
            r0 = r20
            goto L_0x00a2
        L_0x007f:
            int r0 = r0 + 1
            r20 = r10
            r10 = r0
            r0 = r20
            goto L_0x00a2
        L_0x0087:
            if (r13 == 0) goto L_0x008b
            r10 = r0
            goto L_0x008c
        L_0x008b:
            r12 = r0
        L_0x008c:
            r16 = 1
            int r0 = r0 + 1
            r20 = r10
            r10 = r0
            r0 = r20
            goto L_0x00a2
        L_0x0096:
            if (r13 == 0) goto L_0x009a
            r14 = 1
            goto L_0x009b
        L_0x009a:
            r15 = 1
        L_0x009b:
            int r0 = r0 + 1
            r20 = r10
            r10 = r0
            r0 = r20
        L_0x00a2:
            if (r16 != 0) goto L_0x00ad
            if (r10 < r5) goto L_0x00a7
            goto L_0x00ad
        L_0x00a7:
            r20 = r10
            r10 = r0
            r0 = r20
            goto L_0x0063
        L_0x00ad:
            if (r10 != r5) goto L_0x00bd
            if (r0 != r9) goto L_0x00b5
            r0 = r10
            r7 = r12
            r12 = r0
            goto L_0x00bf
        L_0x00b5:
            if (r11 <= r9) goto L_0x00bd
            if (r12 != r9) goto L_0x00bd
            r12 = r10
            r7 = r12
            r12 = r0
            goto L_0x00bf
        L_0x00bd:
            r7 = r12
            r12 = r0
        L_0x00bf:
            java.util.logging.Logger r0 = LOGGER
            java.util.logging.Level r9 = java.util.logging.Level.FINEST
            boolean r18 = r0.isLoggable(r9)
            if (r18 == 0) goto L_0x00e7
            r3 = -1
            if (r11 != r3) goto L_0x00e7
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            r18 = r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r12)
            r19 = r10
            java.nio.charset.Charset r10 = DEFAULT_CHARSET
            java.lang.String r10 = r2.toStringContent(r10, r8, r12)
            java.lang.String r3 = org.glassfish.grizzly.localization.LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_NOEQUAL(r3, r5, r10)
            r0.log(r9, r3)
            goto L_0x00eb
        L_0x00e7:
            r18 = r5
            r19 = r10
        L_0x00eb:
            if (r12 > r8) goto L_0x0110
            java.util.logging.Level r3 = java.util.logging.Level.INFO
            boolean r3 = r0.isLoggable(r3)
            if (r3 == 0) goto L_0x0107
            if (r7 >= r8) goto L_0x0107
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r12)
            r9 = 0
            java.lang.String r3 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_INVALID_CHUNK(r3, r5, r9)
            r0.info(r3)
        L_0x0107:
            r3 = r23
            r5 = r18
            r0 = r19
            r7 = 1
            goto L_0x003e
        L_0x0110:
            org.glassfish.grizzly.http.util.BufferChunk r3 = r1.tmpName
            r3.setBufferChunk(r2, r8, r12)
            org.glassfish.grizzly.http.util.BufferChunk r3 = r1.tmpValue
            r3.setBufferChunk(r2, r11, r7)
            boolean r0 = r0.isLoggable(r9)
            if (r0 == 0) goto L_0x012a
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.origName
            r0.setBufferChunk(r2, r8, r12)
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.origValue
            r0.setBufferChunk(r2, r11, r7)
        L_0x012a:
            if (r14 == 0) goto L_0x0133
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.tmpName     // Catch:{ Exception -> 0x015f }
            java.lang.String r0 = r1.urlDecode(r0, r4)     // Catch:{ Exception -> 0x015f }
            goto L_0x0139
        L_0x0133:
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.tmpName     // Catch:{ Exception -> 0x015f }
            java.lang.String r0 = r0.toString(r4)     // Catch:{ Exception -> 0x015f }
        L_0x0139:
            r3 = -1
            if (r11 == r3) goto L_0x014c
            if (r15 == 0) goto L_0x0145
            org.glassfish.grizzly.http.util.BufferChunk r3 = r1.tmpValue     // Catch:{ Exception -> 0x015f }
            java.lang.String r3 = r1.urlDecode(r3, r4)     // Catch:{ Exception -> 0x015f }
            goto L_0x014e
        L_0x0145:
            org.glassfish.grizzly.http.util.BufferChunk r3 = r1.tmpValue     // Catch:{ Exception -> 0x015f }
            java.lang.String r3 = r3.toString(r4)     // Catch:{ Exception -> 0x015f }
            goto L_0x014e
        L_0x014c:
            java.lang.String r3 = ""
        L_0x014e:
            r1.addParameter(r0, r3)     // Catch:{ Exception -> 0x015f }
        L_0x0151:
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.tmpName
            r0.recycle()
            org.glassfish.grizzly.http.util.BufferChunk r0 = r1.tmpValue
            r0.recycle()
            goto L_0x01c0
        L_0x015c:
            r0 = move-exception
            goto L_0x01cb
        L_0x015f:
            r0 = move-exception
            int r6 = r6 + 1
            java.util.logging.Logger r3 = LOGGER     // Catch:{ all -> 0x015c }
            java.util.logging.Level r5 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x015c }
            boolean r9 = r3.isLoggable(r5)     // Catch:{ all -> 0x015c }
            if (r9 == 0) goto L_0x0180
            org.glassfish.grizzly.http.util.BufferChunk r9 = r1.origName     // Catch:{ all -> 0x015c }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x015c }
            org.glassfish.grizzly.http.util.BufferChunk r10 = r1.origValue     // Catch:{ all -> 0x015c }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x015c }
            java.lang.String r9 = org.glassfish.grizzly.localization.LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(r9, r10)     // Catch:{ all -> 0x015c }
            r3.log(r5, r9)     // Catch:{ all -> 0x015c }
            goto L_0x0151
        L_0x0180:
            java.util.logging.Level r5 = java.util.logging.Level.INFO     // Catch:{ all -> 0x015c }
            boolean r5 = r3.isLoggable(r5)     // Catch:{ all -> 0x015c }
            if (r5 == 0) goto L_0x0151
            r5 = 1
            if (r6 != r5) goto L_0x0151
            org.glassfish.grizzly.http.util.BufferChunk r5 = r1.tmpName     // Catch:{ all -> 0x015c }
            int r5 = r5.getLength()     // Catch:{ all -> 0x015c }
            java.lang.String r9 = "unavailable"
            if (r5 <= 0) goto L_0x019c
            org.glassfish.grizzly.http.util.BufferChunk r5 = r1.tmpName     // Catch:{ all -> 0x015c }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x015c }
            goto L_0x019d
        L_0x019c:
            r5 = r9
        L_0x019d:
            org.glassfish.grizzly.http.util.BufferChunk r10 = r1.tmpValue     // Catch:{ all -> 0x015c }
            int r10 = r10.getLength()     // Catch:{ all -> 0x015c }
            if (r10 <= 0) goto L_0x01ab
            org.glassfish.grizzly.http.util.BufferChunk r9 = r1.tmpValue     // Catch:{ all -> 0x015c }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x015c }
        L_0x01ab:
            java.util.logging.Level r10 = java.util.logging.Level.INFO     // Catch:{ all -> 0x015c }
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x015c }
            java.lang.String r2 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(r2, r5, r9)     // Catch:{ all -> 0x015c }
            r3.log(r10, r2)     // Catch:{ all -> 0x015c }
            java.util.logging.Level r2 = java.util.logging.Level.FINE     // Catch:{ all -> 0x015c }
            java.lang.String r10 = "Decoding stacktrace."
            r3.log(r2, r10, r0)     // Catch:{ all -> 0x015c }
            goto L_0x0151
        L_0x01c0:
            r2 = r22
            r3 = r23
            r5 = r18
            r0 = r19
            r7 = 1
            goto L_0x003e
        L_0x01cb:
            org.glassfish.grizzly.http.util.BufferChunk r2 = r1.tmpName
            r2.recycle()
            org.glassfish.grizzly.http.util.BufferChunk r2 = r1.tmpValue
            r2.recycle()
            throw r0
        L_0x01d6:
            r18 = r5
        L_0x01d8:
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINEST
            boolean r3 = r2.isLoggable(r3)
            if (r3 != 0) goto L_0x01f0
            r3 = 1
            if (r6 <= r3) goto L_0x01f0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            java.lang.String r3 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(r3)
            r2.info(r3)
        L_0x01f0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.Parameters.processParameters(org.glassfish.grizzly.Buffer, int, int, java.nio.charset.Charset):void");
    }

    private String urlDecode(BufferChunk bc, Charset enc) {
        URLDecoder.decode(bc, true);
        if (enc == null) {
            CharChunk cc = this.tmpNameC;
            int length = bc.getLength();
            cc.allocate(length, -1);
            Buffer bbuf = bc.getBuffer();
            char[] cbuf = cc.getBuffer();
            int start = bc.getStart();
            for (int i = 0; i < length; i++) {
                cbuf[i] = (char) (bbuf.get(i + start) & 255);
            }
            cc.setChars(cbuf, 0, length);
            String result = cc.toString();
            cc.recycle();
            return result;
        } else if (bc.getStart() == -1 && bc.getEnd() == -1) {
            return "";
        } else {
            return bc.toString(enc);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0151 A[Catch:{ all -> 0x013e }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0165 A[Catch:{ all -> 0x013e }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01cb A[LOOP:0: B:3:0x0038->B:89:0x01cb, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01b1 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processParameters(char[] r22, int r23, int r24) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r24
            int r5 = r3 + r4
            r0 = r23
            r6 = 0
            java.util.logging.Logger r7 = LOGGER
            java.util.logging.Level r8 = java.util.logging.Level.FINEST
            boolean r9 = r7.isLoggable(r8)
            r10 = 3
            r11 = 2
            r12 = 0
            r13 = 1
            if (r9 == 0) goto L_0x0038
            r9 = 4
            java.lang.Object[] r9 = new java.lang.Object[r9]
            r9[r12] = r2
            java.lang.Integer r14 = java.lang.Integer.valueOf(r23)
            r9[r13] = r14
            java.lang.Integer r14 = java.lang.Integer.valueOf(r24)
            r9[r11] = r14
            java.lang.String r14 = new java.lang.String
            r14.<init>(r2, r3, r4)
            r9[r10] = r14
            java.lang.String r14 = "Process parameters. chars: {0} start={1} len={2} content={3}"
            r7.log(r8, r14, r9)
        L_0x0038:
            int r7 = r1.limit
            r8 = -1
            if (r7 <= r8) goto L_0x0050
            int r9 = r1.parameterCount
            if (r9 < r7) goto L_0x0050
            java.util.logging.Logger r8 = LOGGER
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            java.lang.String r7 = org.glassfish.grizzly.localization.LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(r7)
            r8.warning(r7)
            goto L_0x01b2
        L_0x0050:
            r7 = 0
            r9 = r0
            r14 = -1
            r15 = -1
            r11 = 61
            int r11 = org.glassfish.grizzly.http.util.CharChunk.indexOf((char[]) r2, (int) r9, (int) r5, (char) r11)
            r13 = 38
            int r12 = org.glassfish.grizzly.http.util.CharChunk.indexOf((char[]) r2, (int) r9, (int) r5, (char) r13)
            if (r12 == r8) goto L_0x009f
            if (r11 == r8) goto L_0x006a
            if (r11 <= r12) goto L_0x0067
            goto L_0x006a
        L_0x0067:
            r19 = r0
            goto L_0x00a1
        L_0x006a:
            r11 = r12
            r7 = 1
            r14 = r11
            r15 = r11
            java.util.logging.Logger r13 = LOGGER
            java.util.logging.Level r8 = java.util.logging.Level.FINEST
            boolean r19 = r13.isLoggable(r8)
            if (r19 == 0) goto L_0x009c
            r19 = r0
            java.lang.Object[] r0 = new java.lang.Object[r10]
            java.lang.Integer r20 = java.lang.Integer.valueOf(r9)
            r18 = 0
            r0[r18] = r20
            java.lang.Integer r20 = java.lang.Integer.valueOf(r11)
            r17 = 1
            r0[r17] = r20
            java.lang.String r10 = new java.lang.String
            int r3 = r11 - r9
            r10.<init>(r2, r9, r3)
            r3 = 2
            r0[r3] = r10
            java.lang.String r3 = "no equal {0} {1} {2}"
            r13.log(r8, r3, r0)
            goto L_0x00a1
        L_0x009c:
            r19 = r0
            goto L_0x00a1
        L_0x009f:
            r19 = r0
        L_0x00a1:
            r0 = -1
            if (r11 != r0) goto L_0x00a5
            r11 = r5
        L_0x00a5:
            if (r7 != 0) goto L_0x00bd
            if (r11 >= r5) goto L_0x00ac
            int r0 = r11 + 1
            goto L_0x00ad
        L_0x00ac:
            r0 = r5
        L_0x00ad:
            r14 = r0
            r0 = 38
            int r15 = org.glassfish.grizzly.http.util.CharChunk.indexOf((char[]) r2, (int) r14, (int) r5, (char) r0)
            r0 = -1
            if (r15 != r0) goto L_0x00bd
            if (r14 >= r5) goto L_0x00bb
            r0 = r5
            goto L_0x00bc
        L_0x00bb:
            r0 = r14
        L_0x00bc:
            r15 = r0
        L_0x00bd:
            int r3 = r15 + 1
            if (r11 > r9) goto L_0x00c6
            r2 = 2
            r16 = 0
            goto L_0x01af
        L_0x00c6:
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpNameC     // Catch:{ Exception -> 0x0141 }
            int r8 = r11 - r9
            r0.append((char[]) r2, (int) r9, (int) r8)     // Catch:{ Exception -> 0x0141 }
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpValueC     // Catch:{ Exception -> 0x0141 }
            int r8 = r15 - r14
            r0.append((char[]) r2, (int) r14, (int) r8)     // Catch:{ Exception -> 0x0141 }
            java.util.logging.Logger r0 = LOGGER     // Catch:{ Exception -> 0x0141 }
            java.util.logging.Level r8 = java.util.logging.Level.FINEST     // Catch:{ Exception -> 0x0141 }
            boolean r10 = r0.isLoggable(r8)     // Catch:{ Exception -> 0x0141 }
            java.lang.String r13 = "{0}= {1}"
            if (r10 == 0) goto L_0x00f6
            r10 = 2
            java.lang.Object[] r2 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x00f3 }
            org.glassfish.grizzly.http.util.CharChunk r10 = r1.tmpNameC     // Catch:{ Exception -> 0x0141 }
            r18 = 0
            r2[r18] = r10     // Catch:{ Exception -> 0x0141 }
            org.glassfish.grizzly.http.util.CharChunk r10 = r1.tmpValueC     // Catch:{ Exception -> 0x0141 }
            r17 = 1
            r2[r17] = r10     // Catch:{ Exception -> 0x0141 }
            r0.log(r8, r13, r2)     // Catch:{ Exception -> 0x0141 }
            goto L_0x00f6
        L_0x00f3:
            r0 = move-exception
            r2 = r10
            goto L_0x0143
        L_0x00f6:
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpNameC     // Catch:{ Exception -> 0x0141 }
            java.nio.charset.Charset r10 = r1.queryStringEncoding     // Catch:{ Exception -> 0x0141 }
            java.lang.String r10 = r10.name()     // Catch:{ Exception -> 0x0141 }
            r4 = 1
            org.glassfish.grizzly.http.util.URLDecoder.decode((org.glassfish.grizzly.http.util.CharChunk) r2, (org.glassfish.grizzly.http.util.CharChunk) r2, (boolean) r4, (java.lang.String) r10)     // Catch:{ Exception -> 0x0141 }
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpValueC     // Catch:{ Exception -> 0x0141 }
            java.nio.charset.Charset r4 = r1.queryStringEncoding     // Catch:{ Exception -> 0x0141 }
            java.lang.String r4 = r4.name()     // Catch:{ Exception -> 0x0141 }
            r10 = 1
            org.glassfish.grizzly.http.util.URLDecoder.decode((org.glassfish.grizzly.http.util.CharChunk) r2, (org.glassfish.grizzly.http.util.CharChunk) r2, (boolean) r10, (java.lang.String) r4)     // Catch:{ Exception -> 0x0141 }
            boolean r2 = r0.isLoggable(r8)     // Catch:{ Exception -> 0x0141 }
            if (r2 == 0) goto L_0x0129
            r2 = 2
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0127 }
            org.glassfish.grizzly.http.util.CharChunk r10 = r1.tmpNameC     // Catch:{ Exception -> 0x0127 }
            r16 = 0
            r4[r16] = r10     // Catch:{ Exception -> 0x013c }
            org.glassfish.grizzly.http.util.CharChunk r10 = r1.tmpValueC     // Catch:{ Exception -> 0x013c }
            r17 = 1
            r4[r17] = r10     // Catch:{ Exception -> 0x013c }
            r0.log(r8, r13, r4)     // Catch:{ Exception -> 0x013c }
            goto L_0x012c
        L_0x0127:
            r0 = move-exception
            goto L_0x0143
        L_0x0129:
            r2 = 2
            r16 = 0
        L_0x012c:
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpNameC     // Catch:{ Exception -> 0x013c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x013c }
            org.glassfish.grizzly.http.util.CharChunk r4 = r1.tmpValueC     // Catch:{ Exception -> 0x013c }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x013c }
            r1.addParameter(r0, r4)     // Catch:{ Exception -> 0x013c }
            goto L_0x01a4
        L_0x013c:
            r0 = move-exception
            goto L_0x0145
        L_0x013e:
            r0 = move-exception
            goto L_0x01da
        L_0x0141:
            r0 = move-exception
            r2 = 2
        L_0x0143:
            r16 = 0
        L_0x0145:
            int r6 = r6 + 1
            java.util.logging.Logger r4 = LOGGER     // Catch:{ all -> 0x013e }
            java.util.logging.Level r8 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x013e }
            boolean r10 = r4.isLoggable(r8)     // Catch:{ all -> 0x013e }
            if (r10 == 0) goto L_0x0165
            org.glassfish.grizzly.http.util.BufferChunk r10 = r1.origName     // Catch:{ all -> 0x013e }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x013e }
            org.glassfish.grizzly.http.util.BufferChunk r13 = r1.origValue     // Catch:{ all -> 0x013e }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x013e }
            java.lang.String r10 = org.glassfish.grizzly.localization.LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(r10, r13)     // Catch:{ all -> 0x013e }
            r4.log(r8, r10)     // Catch:{ all -> 0x013e }
            goto L_0x01a4
        L_0x0165:
            java.util.logging.Level r8 = java.util.logging.Level.INFO     // Catch:{ all -> 0x013e }
            boolean r8 = r4.isLoggable(r8)     // Catch:{ all -> 0x013e }
            if (r8 == 0) goto L_0x01a4
            r8 = 1
            if (r6 != r8) goto L_0x01a4
            org.glassfish.grizzly.http.util.CharChunk r8 = r1.tmpNameC     // Catch:{ all -> 0x013e }
            int r8 = r8.getLength()     // Catch:{ all -> 0x013e }
            java.lang.String r10 = "unavailable"
            if (r8 <= 0) goto L_0x0181
            org.glassfish.grizzly.http.util.CharChunk r8 = r1.tmpNameC     // Catch:{ all -> 0x013e }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x013e }
            goto L_0x0182
        L_0x0181:
            r8 = r10
        L_0x0182:
            org.glassfish.grizzly.http.util.CharChunk r13 = r1.tmpValueC     // Catch:{ all -> 0x013e }
            int r13 = r13.getLength()     // Catch:{ all -> 0x013e }
            if (r13 <= 0) goto L_0x0190
            org.glassfish.grizzly.http.util.CharChunk r10 = r1.tmpValueC     // Catch:{ all -> 0x013e }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x013e }
        L_0x0190:
            java.util.logging.Level r13 = java.util.logging.Level.INFO     // Catch:{ all -> 0x013e }
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x013e }
            java.lang.String r2 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(r2, r8, r10)     // Catch:{ all -> 0x013e }
            r4.log(r13, r2)     // Catch:{ all -> 0x013e }
            java.util.logging.Level r2 = java.util.logging.Level.FINE     // Catch:{ all -> 0x013e }
            java.lang.String r13 = "Decoding stacktrace."
            r4.log(r2, r13, r0)     // Catch:{ all -> 0x013e }
        L_0x01a4:
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpNameC
            r0.recycle()
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpValueC
            r0.recycle()
        L_0x01af:
            if (r3 < r5) goto L_0x01cb
            r0 = r3
        L_0x01b2:
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r3 = java.util.logging.Level.FINEST
            boolean r3 = r2.isLoggable(r3)
            if (r3 != 0) goto L_0x01ca
            r4 = 1
            if (r6 <= r4) goto L_0x01ca
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)
            java.lang.String r3 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(r3)
            r2.info(r3)
        L_0x01ca:
            return
        L_0x01cb:
            r4 = 1
            r2 = r22
            r0 = r3
            r13 = r4
            r12 = r16
            r10 = 3
            r11 = 2
            r3 = r23
            r4 = r24
            goto L_0x0038
        L_0x01da:
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpNameC
            r2.recycle()
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpValueC
            r2.recycle()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.Parameters.processParameters(char[], int, int):void");
    }

    public void processParameters(DataChunk data) {
        processParameters(data, this.encoding);
    }

    public void processParameters(DataChunk data, Charset encoding2) {
        if (data != null && !data.isNull() && data.getLength() > 0) {
            try {
                if (data.getType() == DataChunk.Type.Buffer) {
                    BufferChunk bc = data.getBufferChunk();
                    processParameters(bc.getBuffer(), bc.getStart(), bc.getLength(), encoding2);
                    return;
                }
                if (data.getType() != DataChunk.Type.Chars) {
                    data.toChars(encoding2);
                }
                CharChunk cc = data.getCharChunk();
                processParameters(cc.getChars(), cc.getStart(), cc.getLength());
            } catch (CharConversionException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public String paramsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.paramHashValues.keySet()) {
            sb.append(s);
            sb.append('=');
            ArrayList<String> v = this.paramHashValues.get(s);
            int len = v.size();
            for (int i = 0; i < len; i++) {
                sb.append(v.get(i));
                sb.append(StringUtil.COMMA);
            }
            sb.append(10);
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007d, code lost:
        r8 = r11 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processParameters(java.lang.String r21) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            int r3 = r21.length()
            r0 = 0
            r4 = 0
            java.util.logging.Logger r5 = LOGGER
            java.util.logging.Level r6 = java.util.logging.Level.FINEST
            boolean r7 = r5.isLoggable(r6)
            if (r7 == 0) goto L_0x0019
            java.lang.String r7 = "Process parameters. String: {0}"
            r5.log(r6, r7, r2)
        L_0x0019:
            int r5 = r1.limit
            r6 = -1
            r7 = 1
            if (r5 <= r6) goto L_0x0032
            int r8 = r1.parameterCount
            if (r8 < r5) goto L_0x0032
            java.util.logging.Logger r6 = LOGGER
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r5 = org.glassfish.grizzly.localization.LogMessages.WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(r5)
            r6.warning(r5)
            goto L_0x018a
        L_0x0032:
            r5 = 0
            r8 = -1
            r9 = -1
            r10 = r0
            r11 = 61
            int r11 = r2.indexOf(r11, r10)
            r12 = 38
            int r13 = r2.indexOf(r12, r10)
            if (r13 != r6) goto L_0x0045
            r13 = r3
        L_0x0045:
            r14 = 3
            r16 = 0
            if (r13 == r6) goto L_0x0077
            if (r11 == r6) goto L_0x004e
            if (r11 <= r13) goto L_0x0077
        L_0x004e:
            r11 = r13
            r5 = 1
            r8 = r11
            r9 = r11
            java.util.logging.Logger r12 = LOGGER
            java.util.logging.Level r6 = java.util.logging.Level.FINEST
            boolean r18 = r12.isLoggable(r6)
            if (r18 == 0) goto L_0x0077
            java.lang.Object[] r15 = new java.lang.Object[r14]
            java.lang.Integer r19 = java.lang.Integer.valueOf(r10)
            r15[r16] = r19
            java.lang.Integer r19 = java.lang.Integer.valueOf(r11)
            r15[r7] = r19
            java.lang.String r19 = r2.substring(r10, r11)
            r18 = 2
            r15[r18] = r19
            java.lang.String r14 = "no equal {0} {1} {2}"
            r12.log(r6, r14, r15)
        L_0x0077:
            r6 = -1
            if (r11 != r6) goto L_0x007b
            r11 = r3
        L_0x007b:
            if (r5 != 0) goto L_0x008e
            int r8 = r11 + 1
            r6 = 38
            int r9 = r2.indexOf(r6, r8)
            r6 = -1
            if (r9 != r6) goto L_0x008e
            if (r8 >= r3) goto L_0x008c
            r6 = r3
            goto L_0x008d
        L_0x008c:
            r6 = r8
        L_0x008d:
            r9 = r6
        L_0x008e:
            int r6 = r9 + 1
            if (r11 > r10) goto L_0x0094
            goto L_0x0187
        L_0x0094:
            java.util.logging.Logger r0 = LOGGER
            java.util.logging.Level r12 = java.util.logging.Level.FINEST
            boolean r14 = r0.isLoggable(r12)
            if (r14 == 0) goto L_0x00c2
            r14 = 4
            java.lang.Object[] r14 = new java.lang.Object[r14]
            java.lang.Integer r15 = java.lang.Integer.valueOf(r10)
            r14[r16] = r15
            java.lang.Integer r15 = java.lang.Integer.valueOf(r11)
            r14[r7] = r15
            java.lang.Integer r15 = java.lang.Integer.valueOf(r8)
            r17 = 2
            r14[r17] = r15
            java.lang.Integer r15 = java.lang.Integer.valueOf(r9)
            r17 = 3
            r14[r17] = r15
            java.lang.String r15 = "XXX {0} {1} {2} {3}"
            r0.log(r12, r15, r14)
        L_0x00c2:
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpNameC     // Catch:{ Exception -> 0x011c }
            int r15 = r11 - r10
            r14.append((java.lang.String) r2, (int) r10, (int) r15)     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpValueC     // Catch:{ Exception -> 0x011c }
            int r15 = r9 - r8
            r14.append((java.lang.String) r2, (int) r8, (int) r15)     // Catch:{ Exception -> 0x011c }
            boolean r14 = r0.isLoggable(r12)     // Catch:{ Exception -> 0x011c }
            java.lang.String r15 = "{0}= {1}"
            if (r14 == 0) goto L_0x00e8
            r14 = 2
            java.lang.Object[] r7 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpNameC     // Catch:{ Exception -> 0x011c }
            r7[r16] = r14     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpValueC     // Catch:{ Exception -> 0x011c }
            r17 = 1
            r7[r17] = r14     // Catch:{ Exception -> 0x011c }
            r0.log(r12, r15, r7)     // Catch:{ Exception -> 0x011c }
        L_0x00e8:
            org.glassfish.grizzly.http.util.CharChunk r7 = r1.tmpNameC     // Catch:{ Exception -> 0x011c }
            r14 = 1
            org.glassfish.grizzly.http.util.URLDecoder.decode((org.glassfish.grizzly.http.util.CharChunk) r7, (boolean) r14)     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r7 = r1.tmpValueC     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.URLDecoder.decode((org.glassfish.grizzly.http.util.CharChunk) r7, (boolean) r14)     // Catch:{ Exception -> 0x011c }
            boolean r7 = r0.isLoggable(r12)     // Catch:{ Exception -> 0x011c }
            if (r7 == 0) goto L_0x0109
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpNameC     // Catch:{ Exception -> 0x011c }
            r7[r16] = r14     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpValueC     // Catch:{ Exception -> 0x011c }
            r16 = 1
            r7[r16] = r14     // Catch:{ Exception -> 0x011c }
            r0.log(r12, r15, r7)     // Catch:{ Exception -> 0x011c }
        L_0x0109:
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpNameC     // Catch:{ Exception -> 0x011c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x011c }
            org.glassfish.grizzly.http.util.CharChunk r7 = r1.tmpValueC     // Catch:{ Exception -> 0x011c }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x011c }
            r1.addParameter(r0, r7)     // Catch:{ Exception -> 0x011c }
            goto L_0x017c
        L_0x0119:
            r0 = move-exception
            goto L_0x01a8
        L_0x011c:
            r0 = move-exception
            int r4 = r4 + 1
            java.util.logging.Logger r7 = LOGGER     // Catch:{ all -> 0x0119 }
            java.util.logging.Level r12 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x0119 }
            boolean r14 = r7.isLoggable(r12)     // Catch:{ all -> 0x0119 }
            if (r14 == 0) goto L_0x013d
            org.glassfish.grizzly.http.util.BufferChunk r14 = r1.origName     // Catch:{ all -> 0x0119 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0119 }
            org.glassfish.grizzly.http.util.BufferChunk r15 = r1.origValue     // Catch:{ all -> 0x0119 }
            java.lang.String r15 = r15.toString()     // Catch:{ all -> 0x0119 }
            java.lang.String r14 = org.glassfish.grizzly.localization.LogMessages.FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(r14, r15)     // Catch:{ all -> 0x0119 }
            r7.log(r12, r14)     // Catch:{ all -> 0x0119 }
            goto L_0x017c
        L_0x013d:
            java.util.logging.Level r12 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0119 }
            boolean r12 = r7.isLoggable(r12)     // Catch:{ all -> 0x0119 }
            if (r12 == 0) goto L_0x017c
            r12 = 1
            if (r4 != r12) goto L_0x017c
            org.glassfish.grizzly.http.util.CharChunk r12 = r1.tmpNameC     // Catch:{ all -> 0x0119 }
            int r12 = r12.getLength()     // Catch:{ all -> 0x0119 }
            java.lang.String r14 = "unavailable"
            if (r12 <= 0) goto L_0x0159
            org.glassfish.grizzly.http.util.CharChunk r12 = r1.tmpNameC     // Catch:{ all -> 0x0119 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0119 }
            goto L_0x015a
        L_0x0159:
            r12 = r14
        L_0x015a:
            org.glassfish.grizzly.http.util.CharChunk r15 = r1.tmpValueC     // Catch:{ all -> 0x0119 }
            int r15 = r15.getLength()     // Catch:{ all -> 0x0119 }
            if (r15 <= 0) goto L_0x0168
            org.glassfish.grizzly.http.util.CharChunk r14 = r1.tmpValueC     // Catch:{ all -> 0x0119 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0119 }
        L_0x0168:
            java.util.logging.Level r15 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(r2, r12, r14)     // Catch:{ all -> 0x0119 }
            r7.log(r15, r2)     // Catch:{ all -> 0x0119 }
            java.util.logging.Level r2 = java.util.logging.Level.FINE     // Catch:{ all -> 0x0119 }
            java.lang.String r15 = "Decoding stacktrace."
            r7.log(r2, r15, r0)     // Catch:{ all -> 0x0119 }
        L_0x017c:
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpNameC
            r0.recycle()
            org.glassfish.grizzly.http.util.CharChunk r0 = r1.tmpValueC
            r0.recycle()
        L_0x0187:
            if (r6 < r3) goto L_0x01a3
            r0 = r6
        L_0x018a:
            java.util.logging.Logger r2 = LOGGER
            java.util.logging.Level r5 = java.util.logging.Level.FINEST
            boolean r5 = r2.isLoggable(r5)
            if (r5 != 0) goto L_0x01a2
            r5 = 1
            if (r4 <= r5) goto L_0x01a2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            java.lang.String r5 = org.glassfish.grizzly.localization.LogMessages.INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(r5)
            r2.info(r5)
        L_0x01a2:
            return
        L_0x01a3:
            r2 = r21
            r0 = r6
            goto L_0x0019
        L_0x01a8:
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpNameC
            r2.recycle()
            org.glassfish.grizzly.http.util.CharChunk r2 = r1.tmpValueC
            r2.recycle()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.util.Parameters.processParameters(java.lang.String):void");
    }
}
