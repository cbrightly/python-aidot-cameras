package org.apache.commons.fileupload;

import com.yanzhenjie.andserver.util.h;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.util.b;
import org.apache.commons.fileupload.util.c;
import org.apache.commons.fileupload.util.d;

public abstract class FileUploadBase {
    /* access modifiers changed from: private */
    public long a = -1;
    /* access modifiers changed from: private */
    public long b = -1;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public h d;

    public abstract b h();

    public static final boolean p(i ctx) {
        String contentType = ctx.getContentType();
        if (contentType != null && contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/")) {
            return true;
        }
        return false;
    }

    public long o() {
        return this.a;
    }

    public void w(long sizeMax) {
        this.a = sizeMax;
    }

    public long k() {
        return this.b;
    }

    public void u(long fileSizeMax) {
        this.b = fileSizeMax;
    }

    public String l() {
        return this.c;
    }

    public void v(String encoding) {
        this.c = encoding;
    }

    public e m(i ctx) {
        try {
            return new a(this, ctx);
        } catch (FileUploadIOException e) {
            throw ((FileUploadException) e.getCause());
        }
    }

    public List<a> t(i ctx) {
        List<FileItem> items = new ArrayList<>();
        try {
            e iter = m(ctx);
            b fac = h();
            if (fac != null) {
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    a fileItem = fac.a(item.b(), item.getContentType(), item.c(), ((a.b) item).c);
                    items.add(fileItem);
                    d.b(item.f(), fileItem.getOutputStream(), true);
                    fileItem.e(item.getHeaders());
                }
                if (1 == 0) {
                    Iterator<FileItem> it = items.iterator();
                    while (it.hasNext()) {
                        try {
                            ((a) it.next()).delete();
                        } catch (Exception e) {
                        }
                    }
                }
                return items;
            }
            throw new NullPointerException("No FileItemFactory has been set.");
        } catch (FileUploadIOException e2) {
            throw ((FileUploadException) e2.getCause());
        } catch (IOException e3) {
            throw new IOFileUploadException(String.format("Processing of %s request failed. %s", new Object[]{h.MULTIPART_FORM_DATA_VALUE, e3.getMessage()}), e3);
        } catch (FileUploadIOException e4) {
            throw ((FileUploadException) e4.getCause());
        } catch (IOException e5) {
            try {
                throw new FileUploadException(e5.getMessage(), e5);
            } catch (Throwable th) {
                if (0 == 0) {
                    Iterator<FileItem> it2 = items.iterator();
                    while (it2.hasNext()) {
                        try {
                            ((a) it2.next()).delete();
                        } catch (Exception e6) {
                        }
                    }
                }
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public byte[] e(String contentType) {
        g parser = new g();
        parser.j(true);
        String boundaryStr = parser.e(contentType, new char[]{';', StringUtil.COMMA}).get("boundary");
        if (boundaryStr == null) {
            return null;
        }
        try {
            return boundaryStr.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return boundaryStr.getBytes();
        }
    }

    /* access modifiers changed from: protected */
    public String j(c headers) {
        return i(headers.getHeader("Content-disposition"));
    }

    private String i(String pContentDisposition) {
        if (pContentDisposition == null) {
            return null;
        }
        String cdl = pContentDisposition.toLowerCase(Locale.ENGLISH);
        if (!cdl.startsWith("form-data") && !cdl.startsWith("attachment")) {
            return null;
        }
        g parser = new g();
        parser.j(true);
        Map<String, String> params = parser.d(pContentDisposition, ';');
        if (!params.containsKey("filename")) {
            return null;
        }
        String fileName = params.get("filename");
        if (fileName != null) {
            return fileName.trim();
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public String g(c headers) {
        return f(headers.getHeader("Content-disposition"));
    }

    private String f(String pContentDisposition) {
        if (pContentDisposition == null || !pContentDisposition.toLowerCase(Locale.ENGLISH).startsWith("form-data")) {
            return null;
        }
        g parser = new g();
        parser.j(true);
        String fieldName = parser.d(pContentDisposition, ';').get("name");
        if (fieldName != null) {
            return fieldName.trim();
        }
        return fieldName;
    }

    /* access modifiers changed from: protected */
    public c n(String headerPart) {
        int len = headerPart.length();
        b headers = q();
        int start = 0;
        while (true) {
            int end = r(headerPart, start);
            if (start == end) {
                return headers;
            }
            StringBuilder header = new StringBuilder(headerPart.substring(start, end));
            start = end + 2;
            while (start < len) {
                int nonWs = start;
                while (nonWs < len) {
                    char c2 = headerPart.charAt(nonWs);
                    if (c2 != ' ' && c2 != 9) {
                        break;
                    }
                    nonWs++;
                }
                if (nonWs == start) {
                    break;
                }
                int end2 = r(headerPart, nonWs);
                header.append(" ");
                header.append(headerPart.substring(nonWs, end2));
                start = end2 + 2;
            }
            s(headers, header.toString());
        }
    }

    /* access modifiers changed from: protected */
    public b q() {
        return new b();
    }

    private int r(String headerPart, int end) {
        int index = end;
        while (true) {
            int offset = headerPart.indexOf(13, index);
            if (offset != -1 && offset + 1 < headerPart.length()) {
                if (headerPart.charAt(offset + 1) == 10) {
                    return offset;
                }
                index = offset + 1;
            }
        }
        throw new IllegalStateException("Expected headers to be terminated by an empty line.");
    }

    private void s(b headers, String header) {
        int colonOffset = header.indexOf(58);
        if (colonOffset != -1) {
            headers.addHeader(header.substring(0, colonOffset).trim(), header.substring(header.indexOf(58) + 1).trim());
        }
    }

    public class a implements e {
        /* access modifiers changed from: private */
        public final MultipartStream a;
        private final MultipartStream.b b;
        private final byte[] c;
        private b d;
        private String e;
        private boolean f;
        private boolean g;
        private boolean h;
        final /* synthetic */ FileUploadBase i;

        public class b implements FileItemStream {
            private final String a;
            /* access modifiers changed from: private */
            public final String b;
            /* access modifiers changed from: private */
            public final String c;
            private final boolean d;
            private final InputStream e;
            private boolean f;
            private c g;
            final /* synthetic */ a h;

            b(a this$1, String pName, String pFieldName, String pContentType, boolean pFormField, long pContentLength) {
                InputStream istream;
                a aVar = this$1;
                String str = pName;
                String str2 = pFieldName;
                this.h = aVar;
                this.c = str;
                this.b = str2;
                this.a = pContentType;
                this.d = pFormField;
                if (aVar.i.b == -1 || pContentLength == -1 || pContentLength <= aVar.i.b) {
                    InputStream itemStream = this$1.a.o();
                    InputStream istream2 = itemStream;
                    if (aVar.i.b != -1) {
                        istream = new C0482a(istream2, aVar.i.b, this$1, itemStream);
                    } else {
                        istream = istream2;
                    }
                    this.e = istream;
                    return;
                }
                long j = pContentLength;
                FileSizeLimitExceededException e2 = new FileSizeLimitExceededException(String.format("The field %s exceeds its maximum permitted size of %s bytes.", new Object[]{str2, Long.valueOf(aVar.i.b)}), j, aVar.i.b);
                e2.setFileName(str);
                e2.setFieldName(str2);
                throw new FileUploadIOException(e2);
            }

            /* renamed from: org.apache.commons.fileupload.FileUploadBase$a$b$a  reason: collision with other inner class name */
            public class C0482a extends c {
                final /* synthetic */ a q;
                final /* synthetic */ MultipartStream.a x;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0482a(InputStream inputStream, long pSizeMax, a aVar, MultipartStream.a aVar2) {
                    super(inputStream, pSizeMax);
                    this.q = aVar;
                    this.x = aVar2;
                }

                /* access modifiers changed from: protected */
                public void c(long pSizeMax, long pCount) {
                    this.x.a(true);
                    FileSizeLimitExceededException fileSizeLimitExceededException = new FileSizeLimitExceededException(String.format("The field %s exceeds its maximum permitted size of %s bytes.", new Object[]{b.this.b, Long.valueOf(pSizeMax)}), pCount, pSizeMax);
                    fileSizeLimitExceededException.setFieldName(b.this.b);
                    fileSizeLimitExceededException.setFileName(b.this.c);
                    throw new FileUploadIOException(fileSizeLimitExceededException);
                }
            }

            public String getContentType() {
                return this.a;
            }

            public String b() {
                return this.b;
            }

            public boolean c() {
                return this.d;
            }

            public InputStream f() {
                if (this.f) {
                    throw new IllegalStateException("The stream was already opened.");
                } else if (!((org.apache.commons.fileupload.util.a) this.e).isClosed()) {
                    return this.e;
                } else {
                    throw new FileItemStream.ItemSkippedException();
                }
            }

            /* access modifiers changed from: package-private */
            public void j() {
                this.e.close();
            }

            public c getHeaders() {
                return this.g;
            }

            public void e(c pHeaders) {
                this.g = pHeaders;
            }
        }

        a(FileUploadBase fileUploadBase, i ctx) {
            long requestSize;
            InputStream input;
            FileUploadBase fileUploadBase2 = fileUploadBase;
            this.i = fileUploadBase2;
            if (ctx != null) {
                String contentType = ctx.getContentType();
                if (contentType == null || !contentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/")) {
                    throw new InvalidContentTypeException(String.format("the request doesn't contain a %s or %s stream, content type header is %s", new Object[]{h.MULTIPART_FORM_DATA_VALUE, "multipart/mixed", contentType}));
                }
                long requestSize2 = j.class.isAssignableFrom(ctx.getClass()) ? ((j) ctx).a() : (long) ctx.getContentLength();
                if (fileUploadBase.a < 0) {
                    requestSize = requestSize2;
                    input = ctx.getInputStream();
                } else if (requestSize2 == -1 || requestSize2 <= fileUploadBase.a) {
                    requestSize = requestSize2;
                    input = new C0481a(ctx.getInputStream(), fileUploadBase.a, fileUploadBase);
                } else {
                    throw new SizeLimitExceededException(String.format("the request was rejected because its size (%s) exceeds the configured maximum (%s)", new Object[]{Long.valueOf(requestSize2), Long.valueOf(fileUploadBase.a)}), requestSize2, fileUploadBase.a);
                }
                String charEncoding = fileUploadBase.c;
                charEncoding = charEncoding == null ? ctx.b() : charEncoding;
                byte[] e2 = fileUploadBase2.e(contentType);
                this.c = e2;
                if (e2 != null) {
                    MultipartStream.b bVar = new MultipartStream.b(fileUploadBase.d, requestSize);
                    this.b = bVar;
                    try {
                        MultipartStream multipartStream = new MultipartStream(input, e2, bVar);
                        this.a = multipartStream;
                        multipartStream.u(charEncoding);
                        this.f = true;
                        b();
                    } catch (IllegalArgumentException iae) {
                        org.apache.commons.io.d.b(input);
                        throw new InvalidContentTypeException(String.format("The boundary specified in the %s header is too long", new Object[]{"Content-type"}), iae);
                    }
                } else {
                    org.apache.commons.io.d.b(input);
                    throw new FileUploadException("the request was rejected because no multipart boundary was found");
                }
            } else {
                throw new NullPointerException("ctx parameter");
            }
        }

        /* renamed from: org.apache.commons.fileupload.FileUploadBase$a$a  reason: collision with other inner class name */
        public class C0481a extends c {
            final /* synthetic */ FileUploadBase q;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0481a(InputStream inputStream, long pSizeMax, FileUploadBase fileUploadBase) {
                super(inputStream, pSizeMax);
                this.q = fileUploadBase;
            }

            /* access modifiers changed from: protected */
            public void c(long pSizeMax, long pCount) {
                throw new FileUploadIOException(new SizeLimitExceededException(String.format("the request was rejected because its size (%s) exceeds the configured maximum (%s)", new Object[]{Long.valueOf(pCount), Long.valueOf(pSizeMax)}), pCount, pSizeMax));
            }
        }

        private boolean b() {
            boolean nextPart;
            c headers;
            String fieldName;
            if (this.h) {
                return false;
            }
            b bVar = this.d;
            if (bVar != null) {
                bVar.j();
                this.d = null;
            }
            while (true) {
                if (this.f) {
                    nextPart = this.a.v();
                } else {
                    nextPart = this.a.q();
                }
                if (nextPart) {
                    headers = this.i.n(this.a.s());
                    if (this.e == null) {
                        fieldName = this.i.g(headers);
                        if (fieldName != null) {
                            String subContentType = headers.getHeader("Content-type");
                            if (subContentType == null || !subContentType.toLowerCase(Locale.ENGLISH).startsWith("multipart/mixed")) {
                                String fileName = this.i.j(headers);
                            } else {
                                this.e = fieldName;
                                this.a.t(this.i.e(subContentType));
                                this.f = true;
                            }
                        }
                    } else {
                        String fileName2 = this.i.j(headers);
                        if (fileName2 != null) {
                            b bVar2 = new b(this, fileName2, this.e, headers.getHeader("Content-type"), false, c(headers));
                            this.d = bVar2;
                            bVar2.e(headers);
                            this.b.b();
                            this.g = true;
                            return true;
                        }
                    }
                    this.a.m();
                } else if (this.e == null) {
                    this.h = true;
                    return false;
                } else {
                    this.a.t(this.c);
                    this.e = null;
                }
            }
            String fileName3 = this.i.j(headers);
            b bVar3 = new b(this, fileName3, fieldName, headers.getHeader("Content-type"), fileName3 == null, c(headers));
            this.d = bVar3;
            bVar3.e(headers);
            this.b.b();
            this.g = true;
            return true;
        }

        private long c(c pHeaders) {
            try {
                return Long.parseLong(pHeaders.getHeader("Content-length"));
            } catch (Exception e2) {
                return -1;
            }
        }

        public boolean hasNext() {
            if (this.h) {
                return false;
            }
            if (this.g) {
                return true;
            }
            try {
                return b();
            } catch (FileUploadIOException e2) {
                throw ((FileUploadException) e2.getCause());
            }
        }

        public FileItemStream next() {
            if (this.h || (!this.g && !hasNext())) {
                throw new NoSuchElementException();
            }
            this.g = false;
            return this.d;
        }
    }

    public static class FileUploadIOException extends IOException {
        private static final long serialVersionUID = -7047616958165584154L;
        private final FileUploadException cause;

        public FileUploadIOException(FileUploadException pCause) {
            this.cause = pCause;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }

    public static class InvalidContentTypeException extends FileUploadException {
        private static final long serialVersionUID = -9073026332015646668L;

        public InvalidContentTypeException() {
        }

        public InvalidContentTypeException(String message) {
            super(message);
        }

        public InvalidContentTypeException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class IOFileUploadException extends FileUploadException {
        private static final long serialVersionUID = 1749796615868477269L;
        private final IOException cause;

        public IOFileUploadException(String pMsg, IOException pException) {
            super(pMsg);
            this.cause = pException;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }

    public static abstract class SizeException extends FileUploadException {
        private static final long serialVersionUID = -8776225574705254126L;
        private final long actual;
        private final long permitted;

        protected SizeException(String message, long actual2, long permitted2) {
            super(message);
            this.actual = actual2;
            this.permitted = permitted2;
        }

        public long getActualSize() {
            return this.actual;
        }

        public long getPermittedSize() {
            return this.permitted;
        }
    }

    @Deprecated
    public static class UnknownSizeException extends FileUploadException {
        private static final long serialVersionUID = 7062279004812015273L;

        public UnknownSizeException() {
        }

        public UnknownSizeException(String message) {
            super(message);
        }
    }

    public static class SizeLimitExceededException extends SizeException {
        private static final long serialVersionUID = -2474893167098052828L;

        public /* bridge */ /* synthetic */ long getActualSize() {
            return super.getActualSize();
        }

        public /* bridge */ /* synthetic */ long getPermittedSize() {
            return super.getPermittedSize();
        }

        @Deprecated
        public SizeLimitExceededException() {
            this((String) null, 0, 0);
        }

        @Deprecated
        public SizeLimitExceededException(String message) {
            this(message, 0, 0);
        }

        public SizeLimitExceededException(String message, long actual, long permitted) {
            super(message, actual, permitted);
        }
    }

    public static class FileSizeLimitExceededException extends SizeException {
        private static final long serialVersionUID = 8150776562029630058L;
        private String fieldName;
        private String fileName;

        public /* bridge */ /* synthetic */ long getActualSize() {
            return super.getActualSize();
        }

        public /* bridge */ /* synthetic */ long getPermittedSize() {
            return super.getPermittedSize();
        }

        public FileSizeLimitExceededException(String message, long actual, long permitted) {
            super(message, actual, permitted);
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String pFileName) {
            this.fileName = pFileName;
        }

        public String getFieldName() {
            return this.fieldName;
        }

        public void setFieldName(String pFieldName) {
            this.fieldName = pFieldName;
        }
    }
}
