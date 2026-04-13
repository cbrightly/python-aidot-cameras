package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;

public final class AutoValue_Config_Option<T> extends Config.Option<T> {
    private final String id;
    private final Object token;
    private final Class<T> valueClass;

    AutoValue_Config_Option(String id2, Class<T> valueClass2, @Nullable Object token2) {
        if (id2 != null) {
            this.id = id2;
            if (valueClass2 != null) {
                this.valueClass = valueClass2;
                this.token = token2;
                return;
            }
            throw new NullPointerException("Null valueClass");
        }
        throw new NullPointerException("Null id");
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    @NonNull
    public Class<T> getValueClass() {
        return this.valueClass;
    }

    @Nullable
    public Object getToken() {
        return this.token;
    }

    public String toString() {
        return "Option{id=" + this.id + ", valueClass=" + this.valueClass + ", token=" + this.token + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Config.Option)) {
            return false;
        }
        Config.Option<?> that = (Config.Option) o;
        if (this.id.equals(that.getId()) && this.valueClass.equals(that.getValueClass())) {
            Object obj = this.token;
            if (obj == null) {
                if (that.getToken() == null) {
                    return true;
                }
            } else if (obj.equals(that.getToken())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = ((((1 * 1000003) ^ this.id.hashCode()) * 1000003) ^ this.valueClass.hashCode()) * 1000003;
        Object obj = this.token;
        return h$ ^ (obj == null ? 0 : obj.hashCode());
    }
}
