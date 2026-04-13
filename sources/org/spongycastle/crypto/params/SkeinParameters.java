package org.spongycastle.crypto.params;

import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.util.Integers;

public class SkeinParameters implements CipherParameters {
    private Hashtable c;

    public SkeinParameters() {
        this(new Hashtable());
    }

    private SkeinParameters(Hashtable parameters) {
        this.c = parameters;
    }

    public Hashtable b() {
        return this.c;
    }

    public byte[] a() {
        return (byte[]) this.c.get(Integers.b(0));
    }

    public static class Builder {
        private Hashtable a = new Hashtable();

        public Builder() {
        }

        public Builder(Hashtable paramsMap) {
            Enumeration keys = paramsMap.keys();
            while (keys.hasMoreElements()) {
                Integer key = (Integer) keys.nextElement();
                this.a.put(key, paramsMap.get(key));
            }
        }

        public Builder b(int type, byte[] value) {
            if (value == null) {
                throw new IllegalArgumentException("Parameter value must not be null.");
            } else if (type != 0 && (type <= 4 || type >= 63 || type == 48)) {
                throw new IllegalArgumentException("Parameter types must be in the range 0,5..47,49..62.");
            } else if (type != 4) {
                this.a.put(Integers.b(type), value);
                return this;
            } else {
                throw new IllegalArgumentException("Parameter type 4 is reserved for internal use.");
            }
        }

        public Builder c(byte[] key) {
            return b(0, key);
        }

        public SkeinParameters a() {
            return new SkeinParameters(this.a);
        }
    }
}
