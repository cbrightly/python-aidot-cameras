package com.didichuxing.doraemonkit.kit.loginfo.util;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.loginfo.LogLine;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCriteria {
    public static final String PID_KEYWORD = "pid:";
    private static final Pattern PID_PATTERN = Pattern.compile("pid:(\\d+)", 2);
    public static final String TAG_KEYWORD = "tag:";
    private static final Pattern TAG_PATTERN = Pattern.compile("tag:(\"[^\"]+\"|\\S+)", 2);
    private int pid = -1;
    private String searchText;
    private int searchTextAsInt = -1;
    private String tag;

    public SearchCriteria(CharSequence inputQuery) {
        StringBuilder query = new StringBuilder(StringUtil.nullToEmpty(inputQuery));
        Matcher pidMatcher = PID_PATTERN.matcher(query);
        if (pidMatcher.find()) {
            try {
                this.pid = Integer.parseInt(pidMatcher.group(1));
                query.replace(pidMatcher.start(), pidMatcher.end(), "");
            } catch (NumberFormatException e) {
            }
        }
        Matcher tagMatcher = TAG_PATTERN.matcher(query);
        if (tagMatcher.find()) {
            String group = tagMatcher.group(1);
            this.tag = group;
            if (group.startsWith("\"") && this.tag.endsWith("\"")) {
                String str = this.tag;
                this.tag = str.substring(1, str.length() - 1);
            }
            query.replace(tagMatcher.start(), tagMatcher.end(), "");
        }
        String trim = query.toString().trim();
        this.searchText = trim;
        try {
            this.searchTextAsInt = Integer.parseInt(trim);
        } catch (NumberFormatException e2) {
        }
    }

    public boolean isEmpty() {
        return this.pid == -1 && TextUtils.isEmpty(this.tag) && TextUtils.isEmpty(this.searchText);
    }

    public boolean matches(LogLine logLine) {
        if (checkFoundPid(logLine) && checkFoundTag(logLine)) {
            return checkFoundText(logLine);
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r2.searchTextAsInt;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean checkFoundText(com.didichuxing.doraemonkit.kit.loginfo.LogLine r3) {
        /*
            r2 = this;
            java.lang.String r0 = r2.searchText
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x003a
            int r0 = r2.searchTextAsInt
            r1 = -1
            if (r0 == r1) goto L_0x0013
            int r1 = r3.getProcessId()
            if (r0 == r1) goto L_0x003a
        L_0x0013:
            java.lang.String r0 = r3.getTag()
            if (r0 == 0) goto L_0x0025
            java.lang.String r0 = r3.getTag()
            java.lang.String r1 = r2.searchText
            boolean r0 = com.didichuxing.doraemonkit.kit.loginfo.util.StringUtil.containsIgnoreCase(r0, r1)
            if (r0 != 0) goto L_0x003a
        L_0x0025:
            java.lang.String r0 = r3.getLogOutput()
            if (r0 == 0) goto L_0x0038
            java.lang.String r0 = r3.getLogOutput()
            java.lang.String r1 = r2.searchText
            boolean r0 = com.didichuxing.doraemonkit.kit.loginfo.util.StringUtil.containsIgnoreCase(r0, r1)
            if (r0 == 0) goto L_0x0038
            goto L_0x003a
        L_0x0038:
            r0 = 0
            goto L_0x003b
        L_0x003a:
            r0 = 1
        L_0x003b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.loginfo.util.SearchCriteria.checkFoundText(com.didichuxing.doraemonkit.kit.loginfo.LogLine):boolean");
    }

    private boolean checkFoundTag(LogLine logLine) {
        return TextUtils.isEmpty(this.tag) || (logLine.getTag() != null && StringUtil.containsIgnoreCase(logLine.getTag(), this.tag));
    }

    private boolean checkFoundPid(LogLine logLine) {
        return this.pid == -1 || logLine.getProcessId() == this.pid;
    }
}
