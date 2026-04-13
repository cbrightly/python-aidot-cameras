package org.glassfish.tyrus.core.uri.internal;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternWithGroups {
    public static final PatternWithGroups EMPTY = new PatternWithGroups();
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final EmptyStringMatchResult EMPTY_STRING_MATCH_RESULT = new EmptyStringMatchResult();
    /* access modifiers changed from: private */
    public final int[] groupIndexes;
    private final String regex;
    private final Pattern regexPattern;

    protected PatternWithGroups() {
        this.regex = "";
        this.regexPattern = null;
        this.groupIndexes = EMPTY_INT_ARRAY;
    }

    public PatternWithGroups(String regex2) {
        this(regex2, EMPTY_INT_ARRAY);
    }

    public PatternWithGroups(String regex2, int[] groupIndexes2) {
        this(compile(regex2), groupIndexes2);
    }

    private static Pattern compile(String regex2) {
        if (regex2 == null || regex2.isEmpty()) {
            return null;
        }
        return Pattern.compile(regex2);
    }

    public PatternWithGroups(Pattern regexPattern2) {
        this(regexPattern2, EMPTY_INT_ARRAY);
    }

    public PatternWithGroups(Pattern regexPattern2, int[] groupIndexes2) {
        if (regexPattern2 != null) {
            this.regex = regexPattern2.toString();
            this.regexPattern = regexPattern2;
            this.groupIndexes = (int[]) groupIndexes2.clone();
            return;
        }
        throw new IllegalArgumentException();
    }

    public final String getRegex() {
        return this.regex;
    }

    public final int[] getGroupIndexes() {
        return (int[]) this.groupIndexes.clone();
    }

    public static final class EmptyStringMatchResult implements MatchResult {
        private EmptyStringMatchResult() {
        }

        public int start() {
            return 0;
        }

        public int start(int group) {
            if (group == 0) {
                return start();
            }
            throw new IndexOutOfBoundsException();
        }

        public int end() {
            return 0;
        }

        public int end(int group) {
            if (group == 0) {
                return end();
            }
            throw new IndexOutOfBoundsException();
        }

        public String group() {
            return "";
        }

        public String group(int group) {
            if (group == 0) {
                return group();
            }
            throw new IndexOutOfBoundsException();
        }

        public int groupCount() {
            return 0;
        }
    }

    public final class GroupIndexMatchResult implements MatchResult {
        private final MatchResult result;

        GroupIndexMatchResult(MatchResult r) {
            this.result = r;
        }

        public int start() {
            return this.result.start();
        }

        public int start(int group) {
            if (group <= groupCount()) {
                MatchResult matchResult = this.result;
                return group > 0 ? matchResult.start(PatternWithGroups.this.groupIndexes[group - 1]) : matchResult.start();
            }
            throw new IndexOutOfBoundsException();
        }

        public int end() {
            return this.result.end();
        }

        public int end(int group) {
            if (group <= groupCount()) {
                MatchResult matchResult = this.result;
                return group > 0 ? matchResult.end(PatternWithGroups.this.groupIndexes[group - 1]) : matchResult.end();
            }
            throw new IndexOutOfBoundsException();
        }

        public String group() {
            return this.result.group();
        }

        public String group(int group) {
            if (group <= groupCount()) {
                MatchResult matchResult = this.result;
                return group > 0 ? matchResult.group(PatternWithGroups.this.groupIndexes[group - 1]) : matchResult.group();
            }
            throw new IndexOutOfBoundsException();
        }

        public int groupCount() {
            return PatternWithGroups.this.groupIndexes.length;
        }
    }

    public final MatchResult match(CharSequence cs) {
        if (cs != null) {
            Pattern pattern = this.regexPattern;
            if (pattern == null) {
                return null;
            }
            Matcher m = pattern.matcher(cs);
            if (!m.matches()) {
                return null;
            }
            if (cs.length() == 0) {
                return EMPTY_STRING_MATCH_RESULT;
            }
            return this.groupIndexes.length > 0 ? new GroupIndexMatchResult(m) : m;
        } else if (this.regexPattern == null) {
            return EMPTY_STRING_MATCH_RESULT;
        } else {
            return null;
        }
    }

    public final boolean match(CharSequence cs, List<String> groupValues) {
        if (groupValues == null) {
            throw new IllegalArgumentException();
        } else if (cs != null && cs.length() != 0) {
            Pattern pattern = this.regexPattern;
            if (pattern == null) {
                return false;
            }
            Matcher m = pattern.matcher(cs);
            if (!m.matches()) {
                return false;
            }
            groupValues.clear();
            if (this.groupIndexes.length > 0) {
                int i = 0;
                while (true) {
                    int[] iArr = this.groupIndexes;
                    if (i >= iArr.length) {
                        break;
                    }
                    groupValues.add(m.group(iArr[i]));
                    i++;
                }
            } else {
                for (int i2 = 1; i2 <= m.groupCount(); i2++) {
                    groupValues.add(m.group(i2));
                }
            }
            return true;
        } else if (this.regexPattern == null) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean match(CharSequence cs, List<String> groupNames, Map<String, String> groupValues) {
        if (groupValues == null) {
            throw new IllegalArgumentException();
        } else if (cs != null && cs.length() != 0) {
            Pattern pattern = this.regexPattern;
            if (pattern == null) {
                return false;
            }
            Matcher m = pattern.matcher(cs);
            if (!m.matches()) {
                return false;
            }
            groupValues.clear();
            for (int i = 0; i < groupNames.size(); i++) {
                String name = groupNames.get(i);
                int[] iArr = this.groupIndexes;
                String currentValue = m.group(iArr.length > 0 ? iArr[i] : i + 1);
                String previousValue = groupValues.get(name);
                if (previousValue != null && !previousValue.equals(currentValue)) {
                    return false;
                }
                groupValues.put(name, currentValue);
            }
            return true;
        } else if (this.regexPattern == null) {
            return true;
        } else {
            return false;
        }
    }

    public final int hashCode() {
        return this.regex.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        String str = this.regex;
        String str2 = ((PatternWithGroups) obj).regex;
        if (str == str2) {
            return true;
        }
        if (str == null || !str.equals(str2)) {
            return false;
        }
        return true;
    }

    public final String toString() {
        return this.regex;
    }
}
