package retrofit2.converter.moshi;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.i;
import okhttp3.e0;
import okio.e;
import okio.f;
import retrofit2.h;

/* compiled from: MoshiResponseBodyConverter */
public final class c<T> implements h<e0, T> {
    private static final f a = f.decodeHex("EFBBBF");
    private final com.squareup.moshi.f<T> b;

    c(com.squareup.moshi.f<T> adapter) {
        this.b = adapter;
    }

    /* renamed from: a */
    public T convert(e0 value) {
        e source = value.source();
        try {
            f fVar = a;
            if (source.V(0, fVar)) {
                source.skip((long) fVar.size());
            }
            i reader = i.v(source);
            T result = this.b.b(reader);
            if (reader.x() == i.b.END_DOCUMENT) {
                return result;
            }
            throw new JsonDataException("JSON document was not fully consumed.");
        } finally {
            value.close();
        }
    }
}
