package org.glassfish.tyrus.core.uri.internal;

import java.util.Comparator;

public final class PathPattern extends PatternWithGroups {
    public static final Comparator<PathPattern> COMPARATOR = new Comparator<PathPattern>() {
        public int compare(PathPattern o1, PathPattern o2) {
            return UriTemplate.COMPARATOR.compare(o1.template, o2.template);
        }
    };
    public static final PathPattern EMPTY_PATTERN = new PathPattern();
    public static final PathPattern END_OF_PATH_PATTERN = new PathPattern("", RightHandPath.capturingZeroSegments);
    public static final PathPattern OPEN_ROOT_PATH_PATTERN = new PathPattern("", RightHandPath.capturingZeroOrMoreSegments);
    /* access modifiers changed from: private */
    public final UriTemplate template;

    public enum RightHandPath {
        capturingZeroOrMoreSegments("(/.*)?"),
        capturingZeroSegments("(/)?");
        
        private final String regex;

        private RightHandPath(String regex2) {
            this.regex = regex2;
        }

        /* access modifiers changed from: private */
        public String getRegex() {
            return this.regex;
        }
    }

    public static PathPattern asClosed(PathPattern pattern) {
        return new PathPattern(pattern.getTemplate().getTemplate(), RightHandPath.capturingZeroSegments);
    }

    private PathPattern() {
        this.template = UriTemplate.EMPTY;
    }

    public PathPattern(String template2) {
        this(new PathTemplate(template2));
    }

    public PathPattern(PathTemplate template2) {
        super(postfixWithCapturingGroup(template2.getPattern().getRegex()), addIndexForRightHandPathCapturingGroup(template2.getNumberOfRegexGroups(), template2.getPattern().getGroupIndexes()));
        this.template = template2;
    }

    public PathPattern(String template2, RightHandPath rhpp) {
        this(new PathTemplate(template2), rhpp);
    }

    public PathPattern(PathTemplate template2, RightHandPath rhpp) {
        super(postfixWithCapturingGroup(template2.getPattern().getRegex(), rhpp), addIndexForRightHandPathCapturingGroup(template2.getNumberOfRegexGroups(), template2.getPattern().getGroupIndexes()));
        this.template = template2;
    }

    public UriTemplate getTemplate() {
        return this.template;
    }

    private static String postfixWithCapturingGroup(String regex) {
        return postfixWithCapturingGroup(regex, RightHandPath.capturingZeroOrMoreSegments);
    }

    private static String postfixWithCapturingGroup(String regex, RightHandPath rhpp) {
        if (regex.endsWith("/")) {
            regex = regex.substring(0, regex.length() - 1);
        }
        return regex + rhpp.getRegex();
    }

    private static int[] addIndexForRightHandPathCapturingGroup(int numberOfGroups, int[] indexes) {
        if (indexes.length == 0) {
            return indexes;
        }
        int[] cgIndexes = new int[(indexes.length + 1)];
        System.arraycopy(indexes, 0, cgIndexes, 0, indexes.length);
        cgIndexes[indexes.length] = numberOfGroups + 1;
        return cgIndexes;
    }
}
