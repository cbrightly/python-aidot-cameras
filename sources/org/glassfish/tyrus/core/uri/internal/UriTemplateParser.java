package org.glassfish.tyrus.core.uri.internal;

import com.meituan.robust.Constants;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UriTemplateParser {
    static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final String[] HEX_TO_UPPERCASE_REGEX = initHexToUpperCaseRegex();
    private static final Set<Character> RESERVED_REGEX_CHARACTERS = initReserved();
    private static final Pattern TEMPLATE_VALUE_PATTERN = Pattern.compile("[^/]+?");
    private final List<Integer> groupCounts = new ArrayList();
    private int literalCharacters;
    private final StringBuffer literalCharactersBuffer = new StringBuffer();
    private final Map<String, Pattern> nameToPattern = new HashMap();
    private final List<String> names = new ArrayList();
    private final StringBuffer normalizedTemplate = new StringBuffer();
    private int numOfExplicitRegexes;
    private final Pattern pattern;
    private final StringBuffer regex;
    private int skipGroup;
    private final String template;

    private static Set<Character> initReserved() {
        char[] reserved = {'.', '^', '&', '!', '?', '-', ':', '<', '(', '[', '$', '=', ')', ']', StringUtil.COMMA, '>', '*', '+', '|'};
        Set<Character> s = new HashSet<>(reserved.length);
        for (char c : reserved) {
            s.add(Character.valueOf(c));
        }
        return s;
    }

    public UriTemplateParser(String template2) {
        StringBuffer stringBuffer = new StringBuffer();
        this.regex = stringBuffer;
        if (template2 == null || template2.isEmpty()) {
            throw new IllegalArgumentException("Template is null or has zero length");
        }
        this.template = template2;
        parse(new CharacterIterator(template2));
        try {
            this.pattern = Pattern.compile(stringBuffer.toString());
        } catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException("Invalid syntax for the template expression '" + this.regex + "'", ex);
        }
    }

    public final String getTemplate() {
        return this.template;
    }

    public final Pattern getPattern() {
        return this.pattern;
    }

    public final String getNormalizedTemplate() {
        return this.normalizedTemplate.toString();
    }

    public final Map<String, Pattern> getNameToPattern() {
        return this.nameToPattern;
    }

    public final List<String> getNames() {
        return this.names;
    }

    public final List<Integer> getGroupCounts() {
        return this.groupCounts;
    }

    public final int[] getGroupIndexes() {
        if (this.names.isEmpty()) {
            return EMPTY_INT_ARRAY;
        }
        int[] indexes = new int[this.names.size()];
        indexes[0] = this.groupCounts.get(0).intValue() + 0;
        for (int i = 1; i < indexes.length; i++) {
            indexes[i] = indexes[i - 1] + this.groupCounts.get(i).intValue();
        }
        return indexes;
    }

    public final int getNumberOfExplicitRegexes() {
        return this.numOfExplicitRegexes;
    }

    public final int getNumberOfRegexGroups() {
        if (this.groupCounts.isEmpty()) {
            return 0;
        }
        int[] groupIndex = getGroupIndexes();
        return groupIndex[groupIndex.length - 1] + this.skipGroup;
    }

    public final int getNumberOfLiteralCharacters() {
        return this.literalCharacters;
    }

    /* access modifiers changed from: protected */
    public String encodeLiteralCharacters(String characters) {
        return characters;
    }

    private void parse(CharacterIterator ci) {
        while (ci.hasNext()) {
            try {
                char c = ci.next();
                if (c == '{') {
                    processLiteralCharacters();
                    this.skipGroup = parseName(ci, this.skipGroup);
                } else {
                    this.literalCharactersBuffer.append(c);
                }
            } catch (NoSuchElementException ex) {
                throw new IllegalArgumentException("Invalid syntax for the template, \"" + this.template + "\". Check if a path parameter is terminated with a '}'.", ex);
            }
        }
        processLiteralCharacters();
    }

    private void processLiteralCharacters() {
        if (this.literalCharactersBuffer.length() > 0) {
            this.literalCharacters += this.literalCharactersBuffer.length();
            String s = encodeLiteralCharacters(this.literalCharactersBuffer.toString());
            this.normalizedTemplate.append(s);
            int i = 0;
            while (i < s.length()) {
                char c = s.charAt(i);
                if (RESERVED_REGEX_CHARACTERS.contains(Character.valueOf(c))) {
                    this.regex.append("\\");
                    this.regex.append(c);
                } else if (c == '%') {
                    char c1 = s.charAt(i + 1);
                    char c2 = s.charAt(i + 2);
                    if (UriComponent.isHexCharacter(c1) && UriComponent.isHexCharacter(c2)) {
                        StringBuffer stringBuffer = this.regex;
                        stringBuffer.append("%");
                        String[] strArr = HEX_TO_UPPERCASE_REGEX;
                        stringBuffer.append(strArr[c1]);
                        stringBuffer.append(strArr[c2]);
                        i += 2;
                    }
                } else {
                    this.regex.append(c);
                }
                i++;
            }
            this.literalCharactersBuffer.setLength(0);
        }
    }

    private static String[] initHexToUpperCaseRegex() {
        String[] table = new String[128];
        for (int i = 0; i < table.length; i++) {
            table[i] = String.valueOf((char) i);
        }
        for (char c = 'a'; c <= 'f'; c = (char) (c + 1)) {
            table[c] = Constants.ARRAY_TYPE + c + ((char) ((c - 'a') + 65)) + "]";
        }
        for (char c2 = 'A'; c2 <= 'F'; c2 = (char) (c2 + 1)) {
            table[c2] = Constants.ARRAY_TYPE + ((char) ((c2 - 'A') + 97)) + c2 + "]";
        }
        return table;
    }

    private int parseName(CharacterIterator ci, int skipGroup2) {
        char c;
        String nameRegexString;
        int skipGroup3;
        Pattern namePattern;
        char c2 = consumeWhiteSpace(ci);
        char paramType = 'p';
        StringBuilder nameBuffer = new StringBuilder();
        if (c2 == '?' || c2 == ';') {
            paramType = c2;
            c2 = ci.next();
        }
        if (Character.isLetterOrDigit(c2) || c2 == '_') {
            nameBuffer.append(c2);
            while (true) {
                c = ci.next();
                if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.') {
                    nameBuffer.append(c);
                } else if (c == ',' && paramType != 'p') {
                    nameBuffer.append(c);
                }
            }
            if (c == ':' && paramType == 'p') {
                nameRegexString = parseRegex(ci);
                char c3 = c;
            } else if (c == '}') {
                nameRegexString = "";
                char c4 = c;
            } else if (c == ' ') {
                char c5 = consumeWhiteSpace(ci);
                if (c5 == ':') {
                    nameRegexString = parseRegex(ci);
                    char c6 = c5;
                } else if (c5 == '}') {
                    nameRegexString = "";
                    char c7 = c5;
                } else {
                    throw new IllegalArgumentException("Illegal character '" + c5 + "' at position " + ci.pos() + " is not allowed after a name");
                }
            } else {
                throw new IllegalArgumentException("Illegal character '" + c + "' at position " + ci.pos() + " is not allowed as part of a name");
            }
            String name = nameBuffer.toString();
            if (paramType == '?' || paramType == ';') {
                String[] subNames = name.split(",\\s?");
                StringBuilder regexBuilder = new StringBuilder(paramType == '?' ? "\\?" : Constants.PACKNAME_END);
                String separator = paramType == '?' ? "\\&" : ";/\\?";
                boolean first = true;
                regexBuilder.append("(");
                for (String subName : subNames) {
                    regexBuilder.append("(&?");
                    regexBuilder.append(subName);
                    regexBuilder.append("(=([^");
                    regexBuilder.append(separator);
                    regexBuilder.append("]*))?");
                    regexBuilder.append(")");
                    if (!first) {
                        regexBuilder.append("|");
                    }
                    this.names.add(subName);
                    this.groupCounts.add(Integer.valueOf(first ? 5 : 3));
                    first = false;
                }
                try {
                    regexBuilder.append(")*");
                    name = paramType + name;
                    skipGroup3 = 1;
                    namePattern = Pattern.compile(regexBuilder.toString());
                } catch (PatternSyntaxException e) {
                    ex = e;
                    throw new IllegalArgumentException("Invalid syntax for the expression '" + nameRegexString + "' associated with the name '" + name + "'", ex);
                }
            } else {
                try {
                    this.names.add(name);
                    if (!nameRegexString.isEmpty()) {
                        this.numOfExplicitRegexes++;
                    }
                    if (nameRegexString.isEmpty()) {
                        namePattern = TEMPLATE_VALUE_PATTERN;
                    } else {
                        namePattern = Pattern.compile(nameRegexString);
                    }
                    if (!this.nameToPattern.containsKey(name)) {
                        this.nameToPattern.put(name, namePattern);
                    } else if (!this.nameToPattern.get(name).equals(namePattern)) {
                        throw new IllegalArgumentException("The name '" + name + "' is declared more than once with different regular expressions");
                    }
                    int g = namePattern.matcher("").groupCount();
                    this.groupCounts.add(Integer.valueOf(skipGroup2 + 1));
                    skipGroup3 = g;
                } catch (PatternSyntaxException e2) {
                    ex = e2;
                    int i = skipGroup2;
                    throw new IllegalArgumentException("Invalid syntax for the expression '" + nameRegexString + "' associated with the name '" + name + "'", ex);
                }
            }
            try {
                StringBuffer stringBuffer = this.regex;
                stringBuffer.append('(');
                stringBuffer.append(namePattern);
                stringBuffer.append(')');
                StringBuffer stringBuffer2 = this.normalizedTemplate;
                stringBuffer2.append('{');
                stringBuffer2.append(name);
                stringBuffer2.append('}');
                return skipGroup3;
            } catch (PatternSyntaxException e3) {
                ex = e3;
                int i2 = skipGroup3;
            }
        } else {
            throw new IllegalArgumentException("Illegal character '" + c2 + "' at position " + ci.pos() + " is not as the start of a name");
        }
    }

    private String parseRegex(CharacterIterator ci) {
        StringBuilder regexBuffer = new StringBuilder();
        int braceCount = 1;
        while (true) {
            char c = ci.next();
            if (c == '{') {
                braceCount++;
            } else if (c == '}' && braceCount - 1 == 0) {
                return regexBuffer.toString().trim();
            }
            regexBuffer.append(c);
        }
    }

    private char consumeWhiteSpace(CharacterIterator ci) {
        char c;
        do {
            c = ci.next();
        } while (Character.isWhitespace(c));
        return c;
    }
}
