package org.glassfish.tyrus.core.uri.internal;

import org.glassfish.tyrus.core.uri.internal.UriComponent;

public final class PathTemplate extends UriTemplate {

    public static final class PathTemplateParser extends UriTemplateParser {
        public PathTemplateParser(String path) {
            super(path);
        }

        /* access modifiers changed from: protected */
        public String encodeLiteralCharacters(String literalCharacters) {
            return UriComponent.contextualEncode(literalCharacters, UriComponent.Type.PATH);
        }
    }

    public PathTemplate(String path) {
        super((UriTemplateParser) new PathTemplateParser(prefixWithSlash(path)));
    }

    private static String prefixWithSlash(String path) {
        if (!path.isEmpty() && path.charAt(0) == '/') {
            return path;
        }
        return "/" + path;
    }
}
