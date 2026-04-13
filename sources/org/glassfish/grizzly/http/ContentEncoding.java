package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;

public interface ContentEncoding {
    ParsingResult decode(Connection connection, HttpContent httpContent);

    HttpContent encode(Connection connection, HttpContent httpContent);

    String[] getAliases();

    String getName();

    boolean wantDecode(HttpHeader httpHeader);

    boolean wantEncode(HttpHeader httpHeader);
}
