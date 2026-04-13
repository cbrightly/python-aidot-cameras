package io.netty.util.internal.logging;

import com.google.maps.android.BuildConfig;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public final class MessageFormatter {
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    static FormattingTuple format(String messagePattern, Object arg) {
        return arrayFormat(messagePattern, new Object[]{arg});
    }

    static FormattingTuple format(String messagePattern, Object argA, Object argB) {
        return arrayFormat(messagePattern, new Object[]{argA, argB});
    }

    static FormattingTuple arrayFormat(String messagePattern, Object[] argArray) {
        String str = messagePattern;
        Object[] objArr = argArray;
        Throwable th = null;
        if (objArr == null || objArr.length == 0) {
            return new FormattingTuple(str, (Throwable) null);
        }
        boolean z = true;
        int lastArrIdx = objArr.length - 1;
        Object lastEntry = objArr[lastArrIdx];
        Throwable throwable = lastEntry instanceof Throwable ? (Throwable) lastEntry : null;
        if (str == null) {
            return new FormattingTuple((String) null, throwable);
        }
        int j = str.indexOf(DELIM_STR);
        if (j == -1) {
            return new FormattingTuple(str, throwable);
        }
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int L = 0;
        while (true) {
            boolean z2 = false;
            boolean notEscaped = (j == 0 || str.charAt(j + -1) != '\\') ? z : false;
            if (notEscaped) {
                sbuf.append(str, i, j);
            } else {
                sbuf.append(str, i, j - 1);
                if (j >= 2 && str.charAt(j - 2) == '\\') {
                    z2 = true;
                }
                notEscaped = z2;
            }
            i = j + 2;
            if (notEscaped) {
                deeplyAppendParameter(sbuf, objArr[L], (Set<Object[]>) null);
                L++;
                if (L > lastArrIdx) {
                    break;
                }
            } else {
                sbuf.append(DELIM_STR);
            }
            j = str.indexOf(DELIM_STR, i);
            if (j == -1) {
                break;
            }
            z = true;
        }
        sbuf.append(str, i, messagePattern.length());
        String sb = sbuf.toString();
        if (L <= lastArrIdx) {
            th = throwable;
        }
        return new FormattingTuple(sb, th);
    }

    private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Set<Object[]> seenSet) {
        if (o == null) {
            sbuf.append(BuildConfig.TRAVIS);
            return;
        }
        Class<?> objClass = o.getClass();
        if (objClass.isArray()) {
            sbuf.append('[');
            if (objClass == boolean[].class) {
                booleanArrayAppend(sbuf, (boolean[]) o);
            } else if (objClass == byte[].class) {
                byteArrayAppend(sbuf, (byte[]) o);
            } else if (objClass == char[].class) {
                charArrayAppend(sbuf, (char[]) o);
            } else if (objClass == short[].class) {
                shortArrayAppend(sbuf, (short[]) o);
            } else if (objClass == int[].class) {
                intArrayAppend(sbuf, (int[]) o);
            } else if (objClass == long[].class) {
                longArrayAppend(sbuf, (long[]) o);
            } else if (objClass == float[].class) {
                floatArrayAppend(sbuf, (float[]) o);
            } else if (objClass == double[].class) {
                doubleArrayAppend(sbuf, (double[]) o);
            } else {
                objectArrayAppend(sbuf, (Object[]) o, seenSet);
            }
            sbuf.append(']');
        } else if (!Number.class.isAssignableFrom(objClass)) {
            safeObjectAppend(sbuf, o);
        } else if (objClass == Long.class) {
            sbuf.append(((Long) o).longValue());
        } else if (objClass == Integer.class || objClass == Short.class || objClass == Byte.class) {
            sbuf.append(((Number) o).intValue());
        } else if (objClass == Double.class) {
            sbuf.append(((Double) o).doubleValue());
        } else if (objClass == Float.class) {
            sbuf.append(((Float) o).floatValue());
        } else {
            safeObjectAppend(sbuf, o);
        }
    }

    private static void safeObjectAppend(StringBuilder sbuf, Object o) {
        try {
            sbuf.append(o.toString());
        } catch (Throwable t) {
            PrintStream printStream = System.err;
            printStream.println("SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + ']');
            t.printStackTrace();
            sbuf.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Set<Object[]> seenSet) {
        if (a.length != 0) {
            if (seenSet == null) {
                seenSet = new HashSet<>(a.length);
            }
            if (seenSet.add(a)) {
                deeplyAppendParameter(sbuf, a[0], seenSet);
                for (int i = 1; i < a.length; i++) {
                    sbuf.append(", ");
                    deeplyAppendParameter(sbuf, a[i], seenSet);
                }
                seenSet.remove(a);
                return;
            }
            sbuf.append("...");
        }
    }

    private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void charArrayAppend(StringBuilder sbuf, char[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void intArrayAppend(StringBuilder sbuf, int[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void longArrayAppend(StringBuilder sbuf, long[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
        if (a.length != 0) {
            sbuf.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                sbuf.append(", ");
                sbuf.append(a[i]);
            }
        }
    }

    private MessageFormatter() {
    }
}
