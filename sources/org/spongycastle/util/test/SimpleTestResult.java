package org.spongycastle.util.test;

import org.spongycastle.util.Strings;

public class SimpleTestResult implements TestResult {
    private static final String a = Strings.d();
    private String b;

    public String toString() {
        return this.b;
    }
}
