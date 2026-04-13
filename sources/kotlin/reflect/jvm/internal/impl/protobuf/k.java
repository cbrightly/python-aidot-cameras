package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;

/* compiled from: LazyFieldLite */
public class k {
    private d a;
    private f b;
    private volatile boolean c;
    protected volatile o d;

    public o c(o defaultInstance) {
        a(defaultInstance);
        return this.d;
    }

    public o d(o value) {
        o originalValue = this.d;
        this.d = value;
        this.a = null;
        this.c = true;
        return originalValue;
    }

    public int b() {
        if (this.c) {
            return this.d.getSerializedSize();
        }
        return this.a.size();
    }

    /* access modifiers changed from: protected */
    public void a(o defaultInstance) {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    try {
                        if (this.a != null) {
                            this.d = (o) defaultInstance.getParserForType().b(this.a, this.b);
                        } else {
                            this.d = defaultInstance;
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
