package org.apache.commons.fileupload;

import androidx.work.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.util.d;

public class MultipartStream {
    protected static final byte[] a = {13, 10, 13, 10};
    protected static final byte[] b = {13, 10};
    protected static final byte[] c = {45, 45};
    protected static final byte[] d = {13, 10, 45, 45};
    /* access modifiers changed from: private */
    public final InputStream e;
    private int f;
    /* access modifiers changed from: private */
    public final int g;
    private final byte[] h;
    private final int[] i;
    /* access modifiers changed from: private */
    public final int j;
    /* access modifiers changed from: private */
    public final byte[] k;
    /* access modifiers changed from: private */
    public int l;
    /* access modifiers changed from: private */
    public int m;
    private String n;
    /* access modifiers changed from: private */
    public final b o;

    static /* synthetic */ int e(MultipartStream x0) {
        int i2 = x0.l;
        x0.l = i2 + 1;
        return i2;
    }

    public static class b {
        private final h a;
        private final long b;
        private long c;
        private int d;

        b(h pListener, long pContentLength) {
            this.a = pListener;
            this.b = pContentLength;
        }

        /* access modifiers changed from: package-private */
        public void a(int pBytes) {
            this.c += (long) pBytes;
            c();
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.d++;
            c();
        }

        private void c() {
            h hVar = this.a;
            if (hVar != null) {
                hVar.a(this.c, this.b, this.d);
            }
        }
    }

    public MultipartStream(InputStream input, byte[] boundary, int bufSize, b pNotifier) {
        if (boundary != null) {
            int length = boundary.length;
            byte[] bArr = d;
            int length2 = length + bArr.length;
            this.f = length2;
            if (bufSize >= length2 + 1) {
                this.e = input;
                int max = Math.max(bufSize, length2 * 2);
                this.j = max;
                this.k = new byte[max];
                this.o = pNotifier;
                int i2 = this.f;
                byte[] bArr2 = new byte[i2];
                this.h = bArr2;
                this.i = new int[(i2 + 1)];
                this.g = bArr2.length;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                System.arraycopy(boundary, 0, bArr2, bArr.length, boundary.length);
                l();
                this.l = 0;
                this.m = 0;
                return;
            }
            throw new IllegalArgumentException("The buffer size specified for the MultipartStream is too small");
        }
        throw new IllegalArgumentException("boundary may not be null");
    }

    MultipartStream(InputStream input, byte[] boundary, b pNotifier) {
        this(input, boundary, 4096, pNotifier);
    }

    public void u(String encoding) {
        this.n = encoding;
    }

    public byte r() {
        if (this.l == this.m) {
            this.l = 0;
            int read = this.e.read(this.k, 0, this.j);
            this.m = read;
            if (read != -1) {
                b bVar = this.o;
                if (bVar != null) {
                    bVar.a(read);
                }
            } else {
                throw new IOException("No more data is available");
            }
        }
        byte[] bArr = this.k;
        int i2 = this.l;
        this.l = i2 + 1;
        return bArr[i2];
    }

    public boolean q() {
        byte[] marker = new byte[2];
        this.l += this.f;
        try {
            marker[0] = r();
            if (marker[0] == 10) {
                return true;
            }
            marker[1] = r();
            if (k(marker, c, 2)) {
                return false;
            }
            if (k(marker, b, 2)) {
                return true;
            }
            throw new MalformedStreamException("Unexpected characters follow a boundary");
        } catch (FileUploadBase.FileUploadIOException e2) {
            throw e2;
        } catch (IOException e3) {
            throw new MalformedStreamException("Stream ended unexpectedly");
        }
    }

    public void t(byte[] boundary) {
        int length = boundary.length;
        int i2 = this.f;
        byte[] bArr = d;
        if (length == i2 - bArr.length) {
            System.arraycopy(boundary, 0, this.h, bArr.length, boundary.length);
            l();
            return;
        }
        throw new IllegalBoundaryException("The length of a boundary token cannot be changed");
    }

    private void l() {
        int position = 2;
        int candidate = 0;
        int[] iArr = this.i;
        iArr[0] = -1;
        iArr[1] = 0;
        while (position <= this.f) {
            byte[] bArr = this.h;
            if (bArr[position - 1] == bArr[candidate]) {
                this.i[position] = candidate + 1;
                candidate++;
                position++;
            } else if (candidate > 0) {
                candidate = this.i[candidate];
            } else {
                this.i[position] = 0;
                position++;
            }
        }
    }

    public String s() {
        int i2 = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int size = 0;
        while (true) {
            byte[] bArr = a;
            if (i2 < bArr.length) {
                try {
                    byte b2 = r();
                    size++;
                    if (size <= 10240) {
                        if (b2 == bArr[i2]) {
                            i2++;
                        } else {
                            i2 = 0;
                        }
                        baos.write(b2);
                    } else {
                        throw new MalformedStreamException(String.format("Header section has more than %s bytes (maybe it is not properly terminated)", new Object[]{Integer.valueOf(Data.MAX_DATA_BYTES)}));
                    }
                } catch (FileUploadBase.FileUploadIOException e2) {
                    throw e2;
                } catch (IOException e3) {
                    throw new MalformedStreamException("Stream ended unexpectedly");
                }
            } else {
                String str = this.n;
                if (str == null) {
                    return baos.toString();
                }
                try {
                    return baos.toString(str);
                } catch (UnsupportedEncodingException e4) {
                    return baos.toString();
                }
            }
        }
    }

    public int p(OutputStream output) {
        return (int) d.b(o(), output, false);
    }

    /* access modifiers changed from: package-private */
    public a o() {
        return new a();
    }

    public int m() {
        return p((OutputStream) null);
    }

    public boolean v() {
        byte[] bArr = this.h;
        System.arraycopy(bArr, 2, bArr, 0, bArr.length - 2);
        this.f = this.h.length - 2;
        l();
        try {
            m();
            return q();
        } catch (MalformedStreamException e2) {
            return false;
        } finally {
            byte[] bArr2 = this.h;
            System.arraycopy(bArr2, 0, bArr2, 2, bArr2.length - 2);
            byte[] bArr3 = this.h;
            this.f = bArr3.length;
            bArr3[0] = 13;
            bArr3[1] = 10;
            l();
        }
    }

    public static boolean k(byte[] a2, byte[] b2, int count) {
        for (int i2 = 0; i2 < count; i2++) {
            if (a2[i2] != b2[i2]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public int n() {
        int bufferPos = this.l;
        int tablePos = 0;
        while (bufferPos < this.m) {
            while (tablePos >= 0 && this.k[bufferPos] != this.h[tablePos]) {
                tablePos = this.i[tablePos];
            }
            bufferPos++;
            tablePos++;
            int i2 = this.f;
            if (tablePos == i2) {
                return bufferPos - i2;
            }
        }
        return -1;
    }

    public static class MalformedStreamException extends IOException {
        private static final long serialVersionUID = 6466926458059796677L;

        public MalformedStreamException() {
        }

        public MalformedStreamException(String message) {
            super(message);
        }
    }

    public static class IllegalBoundaryException extends IOException {
        private static final long serialVersionUID = -161533165102632918L;

        public IllegalBoundaryException() {
        }

        public IllegalBoundaryException(String message) {
            super(message);
        }
    }

    public class a extends InputStream implements org.apache.commons.fileupload.util.a {
        private long c;
        private int d;
        private int f;
        private boolean q;

        a() {
            c();
        }

        private void c() {
            int n = MultipartStream.this.n();
            this.f = n;
            if (n != -1) {
                return;
            }
            if (MultipartStream.this.m - MultipartStream.this.l > MultipartStream.this.g) {
                this.d = MultipartStream.this.g;
            } else {
                this.d = MultipartStream.this.m - MultipartStream.this.l;
            }
        }

        public int available() {
            int i = this.f;
            if (i == -1) {
                return (MultipartStream.this.m - MultipartStream.this.l) - this.d;
            }
            return i - MultipartStream.this.l;
        }

        public int read() {
            if (this.q) {
                throw new FileItemStream.ItemSkippedException();
            } else if (available() == 0 && g() == 0) {
                return -1;
            } else {
                this.c++;
                byte b = MultipartStream.this.k[MultipartStream.e(MultipartStream.this)];
                if (b >= 0) {
                    return b;
                }
                return b + 256;
            }
        }

        public int read(byte[] b, int off, int len) {
            if (this.q) {
                throw new FileItemStream.ItemSkippedException();
            } else if (len == 0) {
                return 0;
            } else {
                int res = available();
                if (res == 0 && (res = g()) == 0) {
                    return -1;
                }
                int res2 = Math.min(res, len);
                System.arraycopy(MultipartStream.this.k, MultipartStream.this.l, b, off, res2);
                MultipartStream multipartStream = MultipartStream.this;
                int unused = multipartStream.l = multipartStream.l + res2;
                this.c += (long) res2;
                return res2;
            }
        }

        public void close() {
            a(false);
        }

        public void a(boolean pCloseUnderlying) {
            if (!this.q) {
                if (!pCloseUnderlying) {
                    while (true) {
                        int av = available();
                        if (av == 0 && (av = g()) == 0) {
                            break;
                        }
                        skip((long) av);
                    }
                } else {
                    this.q = true;
                    MultipartStream.this.e.close();
                }
                this.q = true;
            }
        }

        public long skip(long bytes) {
            if (!this.q) {
                int av = available();
                if (av == 0 && (av = g()) == 0) {
                    return 0;
                }
                long res = Math.min((long) av, bytes);
                MultipartStream multipartStream = MultipartStream.this;
                int unused = multipartStream.l = (int) (((long) multipartStream.l) + res);
                return res;
            }
            throw new FileItemStream.ItemSkippedException();
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0093 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:6:0x0068  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int g() {
            /*
                r7 = this;
                int r0 = r7.f
                r1 = 0
                r2 = -1
                if (r0 == r2) goto L_0x0007
                return r1
            L_0x0007:
                long r3 = r7.c
                org.apache.commons.fileupload.MultipartStream r0 = org.apache.commons.fileupload.MultipartStream.this
                int r0 = r0.m
                org.apache.commons.fileupload.MultipartStream r5 = org.apache.commons.fileupload.MultipartStream.this
                int r5 = r5.l
                int r0 = r0 - r5
                int r5 = r7.d
                int r0 = r0 - r5
                long r5 = (long) r0
                long r3 = r3 + r5
                r7.c = r3
                org.apache.commons.fileupload.MultipartStream r0 = org.apache.commons.fileupload.MultipartStream.this
                byte[] r0 = r0.k
                org.apache.commons.fileupload.MultipartStream r3 = org.apache.commons.fileupload.MultipartStream.this
                int r3 = r3.m
                int r4 = r7.d
                int r3 = r3 - r4
                org.apache.commons.fileupload.MultipartStream r4 = org.apache.commons.fileupload.MultipartStream.this
                byte[] r4 = r4.k
                int r5 = r7.d
                java.lang.System.arraycopy(r0, r3, r4, r1, r5)
                org.apache.commons.fileupload.MultipartStream r0 = org.apache.commons.fileupload.MultipartStream.this
                int unused = r0.l = r1
                org.apache.commons.fileupload.MultipartStream r0 = org.apache.commons.fileupload.MultipartStream.this
                int r1 = r7.d
                int unused = r0.m = r1
            L_0x0043:
                org.apache.commons.fileupload.MultipartStream r0 = org.apache.commons.fileupload.MultipartStream.this
                java.io.InputStream r0 = r0.e
                org.apache.commons.fileupload.MultipartStream r1 = org.apache.commons.fileupload.MultipartStream.this
                byte[] r1 = r1.k
                org.apache.commons.fileupload.MultipartStream r3 = org.apache.commons.fileupload.MultipartStream.this
                int r3 = r3.m
                org.apache.commons.fileupload.MultipartStream r4 = org.apache.commons.fileupload.MultipartStream.this
                int r4 = r4.j
                org.apache.commons.fileupload.MultipartStream r5 = org.apache.commons.fileupload.MultipartStream.this
                int r5 = r5.m
                int r4 = r4 - r5
                int r0 = r0.read(r1, r3, r4)
                if (r0 == r2) goto L_0x0093
                org.apache.commons.fileupload.MultipartStream r1 = org.apache.commons.fileupload.MultipartStream.this
                org.apache.commons.fileupload.MultipartStream$b r1 = r1.o
                if (r1 == 0) goto L_0x0079
                org.apache.commons.fileupload.MultipartStream r1 = org.apache.commons.fileupload.MultipartStream.this
                org.apache.commons.fileupload.MultipartStream$b r1 = r1.o
                r1.a(r0)
            L_0x0079:
                org.apache.commons.fileupload.MultipartStream r1 = org.apache.commons.fileupload.MultipartStream.this
                int r3 = r1.m
                int r3 = r3 + r0
                int unused = r1.m = r3
                r7.c()
                int r1 = r7.available()
                if (r1 > 0) goto L_0x0092
                int r3 = r7.f
                if (r3 == r2) goto L_0x0091
                goto L_0x0092
            L_0x0091:
                goto L_0x0043
            L_0x0092:
                return r1
            L_0x0093:
                java.lang.String r1 = "Stream ended unexpectedly"
                org.apache.commons.fileupload.MultipartStream$MalformedStreamException r2 = new org.apache.commons.fileupload.MultipartStream$MalformedStreamException
                java.lang.String r3 = "Stream ended unexpectedly"
                r2.<init>(r3)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.fileupload.MultipartStream.a.g():int");
        }

        public boolean isClosed() {
            return this.q;
        }
    }
}
