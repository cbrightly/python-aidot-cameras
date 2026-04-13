package org.glassfish.tyrus.core.uri.internal;

public class UriParser {
    private static final String ERROR_STATE = "The parser was not executed yet. Call the parse() method first.";
    private String authority;
    private CharacterIterator ci;
    private String fragment;
    private String host;
    private final String input;
    private boolean opaque;
    private boolean parserExecuted;
    private String path;
    private String port;
    private String query;
    private String scheme;
    private String ssp;
    private String userInfo;

    UriParser(String uri) {
        this.input = uri;
    }

    private String parseComponentWithIP(String delimiters, boolean mayEnd) {
        return parseComponent(delimiters, mayEnd, true);
    }

    private String parseComponent(String delimiters, boolean mayEnd) {
        return parseComponent(delimiters, mayEnd, false);
    }

    private String parseComponent(String delimiters, boolean mayEnd, boolean isIp) {
        int curlyBracketsCount = 0;
        int squareBracketsCount = 0;
        StringBuilder sb = new StringBuilder();
        boolean endOfInput = false;
        Character c = Character.valueOf(this.ci.current());
        while (!endOfInput) {
            if (c.charValue() == '{') {
                curlyBracketsCount++;
                sb.append(c);
            } else if (c.charValue() == '}') {
                curlyBracketsCount--;
                sb.append(c);
            } else if (isIp && c.charValue() == '[') {
                squareBracketsCount++;
                sb.append(c);
            } else if (isIp && c.charValue() == ']') {
                squareBracketsCount--;
                sb.append(c);
            } else if (delimiters == null || delimiters.indexOf(c.charValue()) < 0 || ((isIp && squareBracketsCount != 0) || curlyBracketsCount != 0)) {
                sb.append(c);
            } else if (sb.length() == 0) {
                return null;
            } else {
                return sb.toString();
            }
            endOfInput = !this.ci.hasNext();
            if (!endOfInput) {
                c = Character.valueOf(this.ci.next());
            }
        }
        if (!mayEnd) {
            throw new IllegalArgumentException("does not end by a delimiter '" + delimiters + "'");
        } else if (sb.length() == 0) {
            return null;
        } else {
            return sb.toString();
        }
    }

    public void parse() {
        this.parserExecuted = true;
        CharacterIterator characterIterator = new CharacterIterator(this.input);
        this.ci = characterIterator;
        if (!characterIterator.hasNext()) {
            this.path = "";
            this.ssp = "";
            return;
        }
        this.ci.next();
        String comp = parseComponent(":/?#", true);
        if (this.ci.hasNext()) {
            this.ssp = this.ci.getInput().substring(this.ci.pos() + 1);
        }
        this.opaque = false;
        if (this.ci.current() != ':') {
            this.ci.setPosition(0);
            if (this.ci.current() == '/') {
                parseHierarchicalUri();
            } else {
                parsePath();
            }
        } else if (comp != null) {
            this.scheme = comp;
            if (!this.ci.hasNext()) {
                this.path = "";
                this.ssp = "";
            } else if (this.ci.next() == '/') {
                parseHierarchicalUri();
            } else {
                this.opaque = true;
            }
        } else {
            throw new IllegalArgumentException(String.format("Expected scheme name at index %d: \"%s\"", new Object[]{Integer.valueOf(this.ci.pos()), this.input}));
        }
    }

    private void parseHierarchicalUri() {
        if (this.ci.hasNext() && this.ci.peek() == '/') {
            this.ci.next();
            this.ci.next();
            parseAuthority();
        }
        if (this.ci.hasNext()) {
            parsePath();
        } else if (this.ci.current() == '/') {
            this.path = "/";
        }
    }

    private void parseAuthority() {
        String comp;
        int start = this.ci.pos();
        String comp2 = parseComponentWithIP("@/?#", true);
        if (this.ci.current() == '@') {
            this.userInfo = comp2;
            if (this.ci.hasNext()) {
                this.ci.next();
                comp = parseComponentWithIP(":/?#", true);
            } else {
                return;
            }
        } else {
            this.ci.setPosition(start);
            comp = parseComponentWithIP("@:/?#", true);
        }
        this.host = comp;
        if (this.ci.current() == ':') {
            if (this.ci.hasNext()) {
                this.ci.next();
                this.port = parseComponent("/?#", true);
            } else {
                return;
            }
        }
        String substring = this.ci.getInput().substring(start, this.ci.pos());
        this.authority = substring;
        if (substring.length() == 0) {
            this.authority = null;
        }
    }

    private void parsePath() {
        this.path = parseComponent("?#", true);
        if (this.ci.current() == '?') {
            if (this.ci.hasNext()) {
                this.ci.next();
                this.query = parseComponent("#", true);
            } else {
                return;
            }
        }
        if (this.ci.current() == '#' && this.ci.hasNext()) {
            this.ci.next();
            this.fragment = parseComponent((String) null, true);
        }
    }

    public String getSsp() {
        if (this.parserExecuted) {
            return this.ssp;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getScheme() {
        if (this.parserExecuted) {
            return this.scheme;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getUserInfo() {
        if (this.parserExecuted) {
            return this.userInfo;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getHost() {
        if (this.parserExecuted) {
            return this.host;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getPort() {
        if (this.parserExecuted) {
            return this.port;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getQuery() {
        if (this.parserExecuted) {
            return this.query;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getPath() {
        if (this.parserExecuted) {
            return this.path;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getFragment() {
        if (this.parserExecuted) {
            return this.fragment;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public String getAuthority() {
        if (this.parserExecuted) {
            return this.authority;
        }
        throw new IllegalStateException(ERROR_STATE);
    }

    public boolean isOpaque() {
        if (this.parserExecuted) {
            return this.opaque;
        }
        throw new IllegalStateException(ERROR_STATE);
    }
}
