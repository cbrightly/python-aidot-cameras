package org.glassfish.tyrus.core.uri.internal;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.tyrus.core.uri.internal.UriComponent;

public class UriTemplate {
    public static final Comparator<UriTemplate> COMPARATOR = new Comparator<UriTemplate>() {
        public int compare(UriTemplate o1, UriTemplate o2) {
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            UriTemplate uriTemplate = UriTemplate.EMPTY;
            if (o1 == uriTemplate && o2 == uriTemplate) {
                return 0;
            }
            if (o1 == uriTemplate) {
                return 1;
            }
            if (o2 == uriTemplate) {
                return -1;
            }
            int i = o2.getNumberOfExplicitCharacters() - o1.getNumberOfExplicitCharacters();
            if (i != 0) {
                return i;
            }
            int i2 = o2.getNumberOfTemplateVariables() - o1.getNumberOfTemplateVariables();
            if (i2 != 0) {
                return i2;
            }
            int i3 = o2.getNumberOfExplicitRegexes() - o1.getNumberOfExplicitRegexes();
            if (i3 != 0) {
                return i3;
            }
            return o2.pattern.getRegex().compareTo(o1.pattern.getRegex());
        }
    };
    public static final UriTemplate EMPTY = new UriTemplate();
    private static final String[] EMPTY_VALUES = new String[0];
    private static final Pattern TEMPLATE_NAMES_PATTERN = Pattern.compile("\\{([\\w\\?;][-\\w\\.,]*)\\}");
    private final boolean endsWithSlash;
    private final String normalizedTemplate;
    private final int numOfCharacters;
    private final int numOfExplicitRegexes;
    private final int numOfRegexGroups;
    /* access modifiers changed from: private */
    public final PatternWithGroups pattern;
    private final String template;
    private final List<String> templateVariables;

    public interface TemplateValueStrategy {
        String valueFor(String str, String str2);
    }

    private UriTemplate() {
        this.normalizedTemplate = "";
        this.template = "";
        this.pattern = PatternWithGroups.EMPTY;
        this.endsWithSlash = false;
        this.templateVariables = Collections.emptyList();
        this.numOfRegexGroups = 0;
        this.numOfCharacters = 0;
        this.numOfExplicitRegexes = 0;
    }

    public UriTemplate(String template2) {
        this(new UriTemplateParser(template2));
    }

    protected UriTemplate(UriTemplateParser templateParser) {
        String template2 = templateParser.getTemplate();
        this.template = template2;
        this.normalizedTemplate = templateParser.getNormalizedTemplate();
        this.pattern = initUriPattern(templateParser);
        this.numOfExplicitRegexes = templateParser.getNumberOfExplicitRegexes();
        this.numOfRegexGroups = templateParser.getNumberOfRegexGroups();
        this.numOfCharacters = templateParser.getNumberOfLiteralCharacters();
        this.endsWithSlash = template2.charAt(template2.length() - 1) != '/' ? false : true;
        this.templateVariables = Collections.unmodifiableList(templateParser.getNames());
    }

    private static PatternWithGroups initUriPattern(UriTemplateParser templateParser) {
        return new PatternWithGroups(templateParser.getPattern(), templateParser.getGroupIndexes());
    }

    public static URI resolve(URI baseUri, String refUri) {
        return resolve(baseUri, URI.create(refUri));
    }

    public static URI resolve(URI baseUri, URI refUri) {
        if (baseUri == null) {
            throw new NullPointerException("Input base URI parameter must not be null.");
        } else if (refUri != null) {
            String refString = refUri.toString();
            if (refString.isEmpty()) {
                refUri = URI.create("#");
            } else if (refString.startsWith("?")) {
                String baseString = baseUri.toString();
                int qIndex = baseString.indexOf(63);
                String baseString2 = qIndex > -1 ? baseString.substring(0, qIndex) : baseString;
                return URI.create(baseString2 + refString);
            }
            URI result = baseUri.resolve(refUri);
            if (refString.isEmpty()) {
                String resolvedString = result.toString();
                result = URI.create(resolvedString.substring(0, resolvedString.indexOf(35)));
            }
            return normalize(result);
        } else {
            throw new NullPointerException("Input reference URI parameter must not be null.");
        }
    }

    public static URI normalize(String uri) {
        return normalize(URI.create(uri));
    }

    public static URI normalize(URI uri) {
        if (uri != null) {
            String path = uri.getPath();
            if (path == null || path.isEmpty() || !path.contains("/.")) {
                return uri;
            }
            String[] segments = path.split("/");
            Deque<String> resolvedSegments = new ArrayDeque<>(segments.length);
            for (String segment : segments) {
                if (!segment.isEmpty() && !".".equals(segment)) {
                    if ("..".equals(segment)) {
                        resolvedSegments.pollLast();
                    } else {
                        resolvedSegments.offer(segment);
                    }
                }
            }
            StringBuilder pathBuilder = new StringBuilder();
            for (String segment2 : resolvedSegments) {
                pathBuilder.append('/');
                pathBuilder.append(segment2);
            }
            return URI.create(createURIWithStringValues(uri.getScheme(), uri.getAuthority(), (String) null, (String) null, (String) null, pathBuilder.toString(), uri.getQuery(), uri.getFragment(), EMPTY_VALUES, false, false));
        }
        throw new NullPointerException("Input reference URI parameter must not be null.");
    }

    public static URI relativize(URI baseUri, URI refUri) {
        if (baseUri == null) {
            throw new NullPointerException("Input base URI parameter must not be null.");
        } else if (refUri != null) {
            return normalize(baseUri.relativize(refUri));
        } else {
            throw new NullPointerException("Input reference URI parameter must not be null.");
        }
    }

    public final String getTemplate() {
        return this.template;
    }

    public final PatternWithGroups getPattern() {
        return this.pattern;
    }

    public final boolean endsWithSlash() {
        return this.endsWithSlash;
    }

    public final List<String> getTemplateVariables() {
        return this.templateVariables;
    }

    public final boolean isTemplateVariablePresent(String name) {
        for (String s : this.templateVariables) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public final int getNumberOfExplicitRegexes() {
        return this.numOfExplicitRegexes;
    }

    public final int getNumberOfRegexGroups() {
        return this.numOfRegexGroups;
    }

    public final int getNumberOfExplicitCharacters() {
        return this.numOfCharacters;
    }

    public final int getNumberOfTemplateVariables() {
        return this.templateVariables.size();
    }

    public final boolean match(CharSequence uri, Map<String, String> templateVariableToValue) {
        if (templateVariableToValue != null) {
            return this.pattern.match(uri, this.templateVariables, templateVariableToValue);
        }
        throw new IllegalArgumentException();
    }

    public final boolean match(CharSequence uri, List<String> groupValues) {
        if (groupValues != null) {
            return this.pattern.match(uri, groupValues);
        }
        throw new IllegalArgumentException();
    }

    public final String createURI(final Map<String, String> values) {
        StringBuilder sb = new StringBuilder();
        resolveTemplate(this.normalizedTemplate, sb, new TemplateValueStrategy() {
            public String valueFor(String templateVariable, String matchedGroup) {
                return (String) values.get(templateVariable);
            }
        });
        return sb.toString();
    }

    public final String createURI(String... values) {
        return createURI(values, 0, values.length);
    }

    public final String createURI(String[] values, int offset, int length) {
        TemplateValueStrategy ns = new TemplateValueStrategy(length, offset, values) {
            private final int lengthPlusOffset;
            private final Map<String, String> mapValues = new HashMap();
            private int v;
            final /* synthetic */ int val$length;
            final /* synthetic */ int val$offset;
            final /* synthetic */ String[] val$values;

            {
                this.val$length = r2;
                this.val$offset = r3;
                this.val$values = r4;
                this.lengthPlusOffset = r2 + r3;
                this.v = r3;
            }

            public String valueFor(String templateVariable, String matchedGroup) {
                int i;
                String tValue = this.mapValues.get(templateVariable);
                if (tValue == null && (i = this.v) < this.lengthPlusOffset) {
                    String[] strArr = this.val$values;
                    this.v = i + 1;
                    tValue = strArr[i];
                    if (tValue != null) {
                        this.mapValues.put(templateVariable, tValue);
                    }
                }
                return tValue;
            }
        };
        StringBuilder sb = new StringBuilder();
        resolveTemplate(this.normalizedTemplate, sb, ns);
        return sb.toString();
    }

    private static void resolveTemplate(String normalizedTemplate2, StringBuilder builder, TemplateValueStrategy valueStrategy) {
        String emptyValueAssignment;
        char separator;
        char prefix;
        String str = normalizedTemplate2;
        StringBuilder sb = builder;
        TemplateValueStrategy templateValueStrategy = valueStrategy;
        Matcher m = TEMPLATE_NAMES_PATTERN.matcher(str);
        int i = 0;
        while (m.find()) {
            sb.append(str, i, m.start());
            String variableName = m.group(1);
            int i2 = 0;
            char firstChar = variableName.charAt(0);
            if (firstChar == '?' || firstChar == ';') {
                if (firstChar == '?') {
                    prefix = '?';
                    separator = '&';
                    emptyValueAssignment = "=";
                } else {
                    prefix = ';';
                    separator = ';';
                    emptyValueAssignment = "";
                }
                int index = builder.length();
                String[] variables = variableName.substring(1).split(", ?");
                int length = variables.length;
                while (i2 < length) {
                    String variable = variables[i2];
                    try {
                        String value = templateValueStrategy.valueFor(variable, m.group());
                        if (value != null) {
                            if (index != builder.length()) {
                                sb.append(separator);
                            }
                            sb.append(variable);
                            if (value.isEmpty()) {
                                sb.append(emptyValueAssignment);
                            } else {
                                sb.append('=');
                                sb.append(value);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                    }
                    i2++;
                    templateValueStrategy = valueStrategy;
                }
                if (index != builder.length() && (index == 0 || sb.charAt(index - 1) != prefix)) {
                    sb.insert(index, prefix);
                }
            } else {
                String value2 = templateValueStrategy.valueFor(variableName, m.group());
                if (value2 != null) {
                    sb.append(value2);
                }
            }
            i = m.end();
            templateValueStrategy = valueStrategy;
        }
        sb.append(str, i, normalizedTemplate2.length());
    }

    public final String toString() {
        return this.pattern.toString();
    }

    public final int hashCode() {
        return this.pattern.hashCode();
    }

    public final boolean equals(Object o) {
        if (o instanceof UriTemplate) {
            return this.pattern.equals(((UriTemplate) o).pattern);
        }
        return false;
    }

    public static String createURI(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Map<String, ?> values, boolean encode, boolean encodeSlashInPath) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, ?> e : values.entrySet()) {
            if (e.getValue() != null) {
                hashMap.put(e.getKey(), e.getValue().toString());
            }
        }
        return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, (Map<String, ?>) hashMap, encode, encodeSlashInPath);
    }

    public static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Map<String, ?> values, boolean encode, boolean encodeSlashInPath) {
        return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, EMPTY_VALUES, encode, encodeSlashInPath, values);
    }

    public static String createURI(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Object[] values, boolean encode, boolean encodeSlashInPath) {
        Object[] objArr = values;
        String[] stringValues = new String[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            if (objArr[i] != null) {
                stringValues[i] = objArr[i].toString();
            }
        }
        return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, stringValues, encode, encodeSlashInPath);
    }

    public static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, String[] values, boolean encode, boolean encodeSlashInPath) {
        return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, values, encode, encodeSlashInPath, new HashMap<>());
    }

    private static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, String[] values, boolean encode, boolean encodeSlashInPath, Map<String, ?> mapValues) {
        boolean hasAuthority;
        int offset;
        int offset2;
        String str = path;
        StringBuilder sb = new StringBuilder();
        int offset3 = 0;
        if (scheme != null) {
            offset3 = createUriComponent(UriComponent.Type.SCHEME, scheme, values, 0, false, mapValues, sb);
            sb.append(':');
        }
        if (notEmpty(userInfo) || notEmpty(host) || notEmpty(port)) {
            sb.append("//");
            if (notEmpty(userInfo)) {
                offset3 = createUriComponent(UriComponent.Type.USER_INFO, userInfo, values, offset3, encode, mapValues, sb);
                sb.append('@');
            }
            if (notEmpty(host)) {
                offset3 = createUriComponent(UriComponent.Type.HOST, host, values, offset3, encode, mapValues, sb);
            }
            if (notEmpty(port)) {
                sb.append(':');
                offset = createUriComponent(UriComponent.Type.PORT, port, values, offset3, false, mapValues, sb);
                hasAuthority = true;
            } else {
                offset = offset3;
                hasAuthority = true;
            }
        } else if (notEmpty(authority)) {
            sb.append("//");
            offset = createUriComponent(UriComponent.Type.AUTHORITY, authority, values, offset3, encode, mapValues, sb);
            hasAuthority = true;
        } else {
            offset = offset3;
            hasAuthority = false;
        }
        if (notEmpty(path) != 0 || notEmpty(query) || notEmpty(fragment)) {
            if (hasAuthority && (str == null || path.isEmpty() || str.charAt(0) != '/')) {
                sb.append('/');
            }
            if (notEmpty(path)) {
                offset2 = createUriComponent(encodeSlashInPath ? UriComponent.Type.PATH_SEGMENT : UriComponent.Type.PATH, path, values, offset, encode, mapValues, sb);
            } else {
                offset2 = offset;
            }
            if (notEmpty(query)) {
                sb.append('?');
                offset2 = createUriComponent(UriComponent.Type.QUERY_PARAM, query, values, offset2, encode, mapValues, sb);
            }
            if (notEmpty(fragment)) {
                sb.append('#');
                createUriComponent(UriComponent.Type.FRAGMENT, fragment, values, offset2, encode, mapValues, sb);
            }
            int i = offset2;
        }
        return sb.toString();
    }

    private static boolean notEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    private static int createUriComponent(UriComponent.Type componentType, String template2, String[] values, int valueOffset, boolean encode, Map<String, ?> _mapValues, StringBuilder b) {
        Map<String, ?> map = _mapValues;
        if (template2.indexOf(123) == -1) {
            b.append(template2);
            return valueOffset;
        }
        String template3 = new UriTemplateParser(template2).getNormalizedTemplate();
        AnonymousClass1ValuesFromArrayStrategy cs = new TemplateValueStrategy(valueOffset, map, values, encode, componentType) {
            /* access modifiers changed from: private */
            public int offset;
            final /* synthetic */ UriComponent.Type val$componentType;
            final /* synthetic */ boolean val$encode;
            final /* synthetic */ Map val$mapValues;
            final /* synthetic */ int val$valueOffset;
            final /* synthetic */ String[] val$values;

            {
                this.val$valueOffset = r1;
                this.val$mapValues = r2;
                this.val$values = r3;
                this.val$encode = r4;
                this.val$componentType = r5;
                this.offset = r1;
            }

            public String valueFor(String templateVariable, String matchedGroup) {
                Object value = this.val$mapValues.get(templateVariable);
                if (value == null) {
                    int i = this.offset;
                    Object[] objArr = this.val$values;
                    if (i < objArr.length) {
                        this.offset = i + 1;
                        value = objArr[i];
                        this.val$mapValues.put(templateVariable, value);
                    }
                }
                if (value == null) {
                    throw new IllegalArgumentException(String.format("The template variable '%s' has no value", new Object[]{templateVariable}));
                } else if (this.val$encode) {
                    return UriComponent.encode(value.toString(), this.val$componentType);
                } else {
                    return UriComponent.contextualEncode(value.toString(), this.val$componentType);
                }
            }
        };
        resolveTemplate(template3, b, cs);
        return cs.offset;
    }

    public static String resolveTemplateValues(final UriComponent.Type type, String template2, final boolean encode, Map<String, ?> _mapValues) {
        if (template2 == null || template2.isEmpty() || template2.indexOf(123) == -1) {
            return template2;
        }
        final Map<String, ?> map = _mapValues;
        String template3 = new UriTemplateParser(template2).getNormalizedTemplate();
        StringBuilder sb = new StringBuilder();
        resolveTemplate(template3, sb, new TemplateValueStrategy() {
            public String valueFor(String templateVariable, String matchedGroup) {
                Object value;
                Object value2 = map.get(templateVariable);
                if (value2 != null) {
                    if (encode) {
                        value = UriComponent.encode(value2.toString(), type);
                    } else {
                        value = UriComponent.contextualEncode(value2.toString(), type);
                    }
                    return value.toString();
                } else if (!map.containsKey(templateVariable)) {
                    return matchedGroup;
                } else {
                    throw new IllegalArgumentException(String.format("The value associated of the template value map for key '%s' is 'null'.", new Object[]{templateVariable}));
                }
            }
        });
        return sb.toString();
    }
}
