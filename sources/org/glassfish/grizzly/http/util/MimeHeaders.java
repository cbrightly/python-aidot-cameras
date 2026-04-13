package org.glassfish.grizzly.http.util;

import java.util.Arrays;
import java.util.Iterator;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.DataChunk;

public class MimeHeaders {
    public static final int DEFAULT_HEADER_SIZE = 8;
    private static final String[] INVALID_TRAILER_NAMES;
    public static final int MAX_NUM_HEADERS_DEFAULT = 100;
    public static final int MAX_NUM_HEADERS_UNBOUNDED = -1;
    public static DataChunk NOOP_CHUNK = new DataChunk.Immutable((DataChunk) null);
    private int count;
    private MimeHeaderField[] headers = new MimeHeaderField[8];
    protected int mark;
    private boolean marked;
    /* access modifiers changed from: private */
    public int maxNumHeaders = 100;
    private final Iterable<String> namesIterable = new Iterable<String>() {
        public Iterator<String> iterator() {
            return new NamesIterator(MimeHeaders.this, false);
        }
    };

    static {
        String[] strArr = {Header.CacheControl.getLowerCase(), Header.Expect.getLowerCase(), Header.Host.getLowerCase(), Header.MaxForwards.getLowerCase(), Header.Pragma.getLowerCase(), Header.Range.getLowerCase(), Header.TE.getLowerCase(), Header.SetCookie.getLowerCase(), Header.Authorization.getLowerCase(), Header.WWWAuthenticate.getLowerCase(), Header.ProxyAuthenticate.getLowerCase(), Header.ProxyAuthorization.getLowerCase(), Header.Age.getLowerCase(), Header.Date.getLowerCase(), Header.Location.getLowerCase(), Header.RetryAfter.getLowerCase(), Header.Vary.getLowerCase(), Header.Warnings.getLowerCase(), Header.IfMatch.getLowerCase(), Header.IfNoneMatch.getLowerCase(), Header.IfModifiedSince.getLowerCase(), Header.IfUnmodifiedSince.getLowerCase(), Header.IfRange.getLowerCase()};
        INVALID_TRAILER_NAMES = strArr;
        Arrays.sort(strArr);
    }

    public void mark() {
        if (!this.marked) {
            this.marked = true;
            this.mark = this.count;
        }
    }

    public void recycle() {
        clear();
    }

    public void clear() {
        for (int i = 0; i < this.count; i++) {
            this.headers[i].recycle();
        }
        this.count = 0;
        this.mark = 0;
        this.marked = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== MimeHeaders ===\n");
        for (int i = 0; i < this.count; i++) {
            sb.append(this.headers[i].nameB);
            sb.append(" = ");
            sb.append(this.headers[i].valueB);
            sb.append(10);
        }
        return sb.toString();
    }

    public void copyFrom(MimeHeaders source) {
        if (source != null && source.size() != 0) {
            this.maxNumHeaders = source.maxNumHeaders;
            int i = source.count;
            this.count = i;
            MimeHeaderField[] mimeHeaderFieldArr = this.headers;
            if (mimeHeaderFieldArr.length < i) {
                MimeHeaderField[] tmp = new MimeHeaderField[(i * 2)];
                System.arraycopy(mimeHeaderFieldArr, 0, tmp, 0, mimeHeaderFieldArr.length);
                this.headers = tmp;
            }
            int len = source.count;
            for (int i2 = 0; i2 < len; i2++) {
                MimeHeaderField sourceField = source.headers[i2];
                if (isValidName(sourceField.getName().toString())) {
                    MimeHeaderField f = this.headers[i2];
                    if (f == null) {
                        f = new MimeHeaderField();
                        this.headers[i2] = f;
                    }
                    DataChunk dataChunk = sourceField.nameB;
                    DataChunk.Type type = dataChunk.type;
                    DataChunk.Type type2 = DataChunk.Type.Buffer;
                    if (type == type2) {
                        copyBufferChunk(dataChunk, f.nameB);
                    } else {
                        f.nameB.set(dataChunk);
                    }
                    DataChunk dataChunk2 = sourceField.valueB;
                    if (dataChunk2.type == type2) {
                        copyBufferChunk(dataChunk2, f.valueB);
                    } else {
                        f.valueB.set(dataChunk2);
                    }
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private static void copyBufferChunk(DataChunk source, DataChunk dest) {
        BufferChunk bc = source.getBufferChunk();
        int l = bc.getLength();
        byte[] bytes = new byte[l];
        Buffer b = bc.getBuffer();
        int oldPos = b.position();
        try {
            b.position(bc.getStart());
            bc.getBuffer().get(bytes, 0, l);
            dest.setBytes(bytes);
            b.position(oldPos);
        } catch (Throwable th) {
            b.position(oldPos);
            throw th;
        }
    }

    public int size() {
        return this.count;
    }

    public int trailerSize() {
        if (this.marked) {
            return this.count - this.mark;
        }
        return 0;
    }

    public DataChunk getName(int n) {
        if (n < 0 || n >= this.count) {
            return null;
        }
        return this.headers[n].getName();
    }

    public DataChunk getValue(int n) {
        if (n < 0 || n >= this.count) {
            return null;
        }
        return this.headers[n].getValue();
    }

    public boolean isSerialized(int n) {
        if (n < 0 || n >= this.count) {
            return false;
        }
        return this.headers[n].isSerialized();
    }

    public boolean setSerialized(int n, boolean newValue) {
        if (n < 0 || n >= this.count) {
            return true;
        }
        MimeHeaderField field = this.headers[n];
        boolean value = field.isSerialized();
        field.setSerialized(newValue);
        return value;
    }

    public int indexOf(String name, int fromIndex) {
        for (int i = fromIndex; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public int indexOf(Header header, int fromIndex) {
        byte[] bytes = header.getLowerCaseBytes();
        for (int i = fromIndex; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Header header) {
        return indexOf(header, 0) >= 0;
    }

    public boolean contains(String header) {
        return indexOf(header, 0) >= 0;
    }

    public Iterable<String> names() {
        return this.namesIterable;
    }

    public Iterable<String> trailerNames() {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new NamesIterator(MimeHeaders.this, true);
            }
        };
    }

    public Iterable<String> values(final String name) {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new ValuesIterator(MimeHeaders.this, name, false);
            }
        };
    }

    public Iterable<String> values(Header name) {
        return values(name.toString());
    }

    public Iterable<String> trailerValues(final String name) {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new ValuesIterator(MimeHeaders.this, name, true);
            }
        };
    }

    public Iterable<String> trailerValues(Header name) {
        return trailerValues(name.toString());
    }

    private MimeHeaderField createHeader() {
        int i = this.maxNumHeaders;
        if (i < 0 || this.count != i) {
            MimeHeaderField[] mimeHeaderFieldArr = this.headers;
            int len = mimeHeaderFieldArr.length;
            int i2 = this.count;
            if (i2 >= len) {
                int newCount = i2 * 2;
                if (i >= 0 && newCount > i) {
                    newCount = this.maxNumHeaders;
                }
                MimeHeaderField[] tmp = new MimeHeaderField[newCount];
                System.arraycopy(mimeHeaderFieldArr, 0, tmp, 0, len);
                this.headers = tmp;
            }
            MimeHeaderField[] tmp2 = this.headers;
            int i3 = this.count;
            MimeHeaderField mimeHeaderField = tmp2[i3];
            MimeHeaderField mh = mimeHeaderField;
            if (mimeHeaderField == null) {
                MimeHeaderField mimeHeaderField2 = new MimeHeaderField();
                mh = mimeHeaderField2;
                tmp2[i3] = mimeHeaderField2;
            }
            this.count++;
            return mh;
        }
        throw new MaxHeaderCountExceededException();
    }

    public DataChunk addValue(String name) {
        if (!isValidName(name)) {
            return NOOP_CHUNK;
        }
        MimeHeaderField mh = createHeader();
        mh.getName().setString(name);
        return mh.getValue();
    }

    public DataChunk addValue(Header header) {
        if (!isValidName(header)) {
            return NOOP_CHUNK;
        }
        MimeHeaderField mh = createHeader();
        mh.getName().setBytes(header.toByteArray());
        return mh.getValue();
    }

    public DataChunk addValue(byte[] buffer, int startN, int len) {
        if (!isValidName(buffer)) {
            return NOOP_CHUNK;
        }
        MimeHeaderField mhf = createHeader();
        mhf.getName().setBytes(buffer, startN, startN + len);
        return mhf.getValue();
    }

    public DataChunk addValue(Buffer buffer, int startN, int len) {
        if (!isValidName(buffer)) {
            return NOOP_CHUNK;
        }
        MimeHeaderField mhf = createHeader();
        mhf.getName().setBuffer(buffer, startN, startN + len);
        return mhf.getValue();
    }

    public DataChunk setValue(String name) {
        if (!isValidName(name)) {
            return NOOP_CHUNK;
        }
        for (int i = 0; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCase(name)) {
                int j = i + 1;
                while (j < this.count) {
                    if (this.headers[j].getName().equalsIgnoreCase(name)) {
                        removeHeader(j);
                        j--;
                    }
                    j++;
                }
                return this.headers[i].getValue();
            }
        }
        MimeHeaderField mh = createHeader();
        mh.getName().setString(name);
        return mh.getValue();
    }

    public DataChunk setValue(Header header) {
        if (!isValidName(header)) {
            return NOOP_CHUNK;
        }
        byte[] bytes = header.getLowerCaseBytes();
        for (int i = 0; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
                int j = i + 1;
                while (j < this.count) {
                    if (this.headers[j].getName().equalsIgnoreCaseLowerCase(bytes)) {
                        removeHeader(j);
                        j--;
                    }
                    j++;
                }
                return this.headers[i].getValue();
            }
        }
        MimeHeaderField mh = createHeader();
        mh.getName().setBytes(header.toByteArray());
        return mh.getValue();
    }

    public DataChunk getValue(String name) {
        for (int i = 0; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCase(name)) {
                return this.headers[i].getValue();
            }
        }
        return null;
    }

    public DataChunk getValue(Header header) {
        byte[] bytes = header.getLowerCaseBytes();
        for (int i = 0; i < this.count; i++) {
            if (this.headers[i].getName().equalsIgnoreCaseLowerCase(bytes)) {
                return this.headers[i].getValue();
            }
        }
        return null;
    }

    public String getHeader(String name) {
        DataChunk mh = getValue(name);
        if (mh != null) {
            return mh.toString();
        }
        return null;
    }

    public String getHeader(Header header) {
        DataChunk mh = getValue(header);
        if (mh != null) {
            return mh.toString();
        }
        return null;
    }

    public void removeHeader(String name) {
        int i = 0;
        while (i < this.count) {
            if (this.headers[i].getName().equalsIgnoreCase(name)) {
                removeHeader(i);
                i--;
            }
            i++;
        }
    }

    public void removeHeader(Header header) {
        int i = 0;
        while (i < this.count) {
            if (this.headers[i].getName().equalsIgnoreCase(header.getBytes())) {
                removeHeader(i);
                i--;
            }
            i++;
        }
    }

    public void removeHeader(String name, String str) {
        int i = 0;
        while (i < this.count) {
            if (this.headers[i].getName().equalsIgnoreCase(name) && getValue(i) != null && getValue(i).toString() != null && getValue(i).toString().contains(str)) {
                removeHeader(i);
                i--;
            }
            i++;
        }
    }

    public void removeHeaderMatches(String name, String regex) {
        int i = 0;
        while (i < this.count) {
            if (this.headers[i].getName().equalsIgnoreCase(name) && getValue(i) != null && getValue(i).toString() != null && getValue(i).toString().matches(regex)) {
                removeHeader(i);
                i--;
            }
            i++;
        }
    }

    public void removeHeaderMatches(Header header, String regex) {
        int i = 0;
        while (i < this.count) {
            if (this.headers[i].getName().equalsIgnoreCaseLowerCase(header.getLowerCaseBytes()) && getValue(i) != null && getValue(i).toString() != null && getValue(i).toString().matches(regex)) {
                removeHeader(i);
                i--;
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public void removeHeader(int idx) {
        MimeHeaderField mh = this.headers[idx];
        mh.recycle();
        MimeHeaderField[] mimeHeaderFieldArr = this.headers;
        int i = this.count;
        mimeHeaderFieldArr[idx] = mimeHeaderFieldArr[i - 1];
        mimeHeaderFieldArr[i - 1] = mh;
        this.count = i - 1;
    }

    public void setMaxNumHeaders(int maxNumHeaders2) {
        this.maxNumHeaders = maxNumHeaders2;
    }

    public int getMaxNumHeaders() {
        return this.maxNumHeaders;
    }

    public class MaxHeaderCountExceededException extends IllegalStateException {
        public MaxHeaderCountExceededException() {
            super("Illegal attempt to exceed the configured maximum number of headers: " + MimeHeaders.this.maxNumHeaders);
        }
    }

    private boolean isValidName(String name) {
        return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.toLowerCase()) < 0;
    }

    private boolean isValidName(Header name) {
        return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.getLowerCase()) < 0;
    }

    private boolean isValidName(byte[] name) {
        return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, new String(name).toLowerCase()) < 0;
    }

    private boolean isValidName(Buffer name) {
        return !this.marked || Arrays.binarySearch(INVALID_TRAILER_NAMES, name.toStringContent().toLowerCase()) < 0;
    }
}
