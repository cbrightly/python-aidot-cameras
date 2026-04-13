package com.squareup.moshi.adapters;

import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.o;
import java.util.Date;

/* compiled from: Rfc3339DateJsonAdapter */
public final class c extends f<Date> {
    /* renamed from: k */
    public synchronized Date b(i reader) {
        if (reader.x() == i.b.NULL) {
            return (Date) reader.t();
        }
        return a.e(reader.u());
    }

    /* renamed from: l */
    public synchronized void i(o writer, Date value) {
        if (value == null) {
            writer.s();
        } else {
            writer.W(a.b(value));
        }
    }
}
