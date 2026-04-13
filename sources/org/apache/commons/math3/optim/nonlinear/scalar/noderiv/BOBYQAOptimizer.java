package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math3.optim.nonlinear.scalar.a;

public class BOBYQAOptimizer extends a {
    /* access modifiers changed from: private */
    public static String b(int n) {
        StackTraceElement e = new Throwable().getStackTrace()[n];
        return e.getMethodName() + " (at line " + e.getLineNumber() + ")";
    }

    public static class PathIsExploredException extends RuntimeException {
        private static final long serialVersionUID = 745350979634801853L;

        PathIsExploredException() {
            super("If this exception is thrown, just remove it from the code " + BOBYQAOptimizer.b(3));
        }
    }
}
