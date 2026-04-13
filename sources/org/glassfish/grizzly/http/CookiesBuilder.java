package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.CookieParserUtils;

public class CookiesBuilder {
    public static ClientCookiesBuilder client() {
        return client(false, false);
    }

    public static ClientCookiesBuilder client(boolean strictVersionOneCompliant) {
        return new ClientCookiesBuilder(strictVersionOneCompliant, false);
    }

    public static ClientCookiesBuilder client(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
        return new ClientCookiesBuilder(strictVersionOneCompliant, rfc6265Enabled);
    }

    public static ServerCookiesBuilder server() {
        return server(false, false);
    }

    public static ServerCookiesBuilder server(boolean strictVersionOneCompliant) {
        return new ServerCookiesBuilder(strictVersionOneCompliant, false);
    }

    public static ServerCookiesBuilder server(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
        return new ServerCookiesBuilder(strictVersionOneCompliant, rfc6265Enabled);
    }

    public static class ClientCookiesBuilder extends AbstractCookiesBuilder<ClientCookiesBuilder> {
        public ClientCookiesBuilder(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
            super(strictVersionOneCompliant, rfc6265Enabled);
        }

        public ClientCookiesBuilder parse(Buffer cookiesHeader) {
            return parse(cookiesHeader, cookiesHeader.position(), cookiesHeader.limit());
        }

        public ClientCookiesBuilder parse(Buffer cookiesHeader, int position, int limit) {
            CookieParserUtils.parseClientCookies(this.cookies, cookiesHeader, position, limit - position, this.strictVersionOneCompliant, this.rfc6265Enabled);
            return this;
        }

        public ClientCookiesBuilder parse(String cookiesHeader) {
            CookieParserUtils.parseClientCookies(this.cookies, cookiesHeader, this.strictVersionOneCompliant, this.rfc6265Enabled);
            return this;
        }
    }

    public static class ServerCookiesBuilder extends AbstractCookiesBuilder<ServerCookiesBuilder> {
        public ServerCookiesBuilder(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
            super(strictVersionOneCompliant, rfc6265Enabled);
        }

        public ServerCookiesBuilder parse(Buffer cookiesHeader) {
            return parse(cookiesHeader, cookiesHeader.position(), cookiesHeader.limit());
        }

        public ServerCookiesBuilder parse(Buffer cookiesHeader, int position, int limit) {
            CookieParserUtils.parseServerCookies(this.cookies, cookiesHeader, position, limit - position, this.strictVersionOneCompliant, this.rfc6265Enabled);
            return this;
        }

        public ServerCookiesBuilder parse(String cookiesHeader) {
            CookieParserUtils.parseServerCookies(this.cookies, cookiesHeader, this.strictVersionOneCompliant, this.rfc6265Enabled);
            return this;
        }
    }

    public static abstract class AbstractCookiesBuilder<E extends AbstractCookiesBuilder> {
        protected final Cookies cookies = new Cookies();
        protected final boolean rfc6265Enabled;
        protected final boolean strictVersionOneCompliant;

        public abstract E parse(String str);

        public abstract E parse(Buffer buffer);

        public abstract E parse(Buffer buffer, int i, int i2);

        public AbstractCookiesBuilder(boolean strictVersionOneCompliant2, boolean rfc6265Enabled2) {
            this.strictVersionOneCompliant = strictVersionOneCompliant2;
            this.rfc6265Enabled = rfc6265Enabled2;
        }

        public Cookies build() {
            return this.cookies;
        }
    }
}
