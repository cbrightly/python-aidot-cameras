package com.blankj.utilcode.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* compiled from: ThrowableUtils */
public class c0 {
    private static final String a = System.getProperty("line.separator");

    public static String a(Throwable throwable) {
        List<Throwable> throwableList = new ArrayList<>();
        while (throwable != null && !throwableList.contains(throwable)) {
            throwableList.add(throwable);
            throwable = throwable.getCause();
        }
        int size = throwableList.size();
        List<String> frames = new ArrayList<>();
        List<String> nextTrace = b(throwableList.get(size - 1));
        int i = size;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            List<String> trace = nextTrace;
            if (i != 0) {
                nextTrace = b(throwableList.get(i - 1));
                c(trace, nextTrace);
            }
            if (i == size - 1) {
                frames.add(throwableList.get(i).toString());
            } else {
                frames.add(" Caused by: " + throwableList.get(i).toString());
            }
            frames.addAll(trace);
        }
        StringBuilder sb = new StringBuilder();
        for (String element : frames) {
            sb.append(element);
            sb.append(a);
        }
        return sb.toString();
    }

    private static List<String> b(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        StringTokenizer frames = new StringTokenizer(sw.toString(), a);
        List<String> list = new ArrayList<>();
        boolean traceStarted = false;
        while (frames.hasMoreTokens()) {
            String token = frames.nextToken();
            int at = token.indexOf("at");
            if (at != -1 && token.substring(0, at).trim().isEmpty()) {
                traceStarted = true;
                list.add(token);
            } else if (traceStarted) {
                break;
            }
        }
        return list;
    }

    private static void c(List<String> causeFrames, List<String> wrapperFrames) {
        int causeFrameIndex = causeFrames.size() - 1;
        int wrapperFrameIndex = wrapperFrames.size() - 1;
        while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
            if (causeFrames.get(causeFrameIndex).equals(wrapperFrames.get(wrapperFrameIndex))) {
                causeFrames.remove(causeFrameIndex);
            }
            causeFrameIndex--;
            wrapperFrameIndex--;
        }
    }
}
