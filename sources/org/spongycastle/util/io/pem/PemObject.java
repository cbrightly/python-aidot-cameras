package org.spongycastle.util.io.pem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PemObject implements PemObjectGenerator {
    private static final List a = Collections.unmodifiableList(new ArrayList());
    private String b;
    private List c;
    private byte[] d;

    public PemObject(String type, byte[] content) {
        this(type, a, content);
    }

    public PemObject(String type, List headers, byte[] content) {
        this.b = type;
        this.c = Collections.unmodifiableList(headers);
        this.d = content;
    }

    public String d() {
        return this.b;
    }

    public List c() {
        return this.c;
    }

    public byte[] b() {
        return this.d;
    }

    public PemObject a() {
        return this;
    }
}
