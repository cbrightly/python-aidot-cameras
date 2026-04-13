package org.glassfish.grizzly.http.server.accesslog;

import com.amazonaws.kinesisvideo.producer.Time;
import com.google.android.gms.common.internal.ImagesContract;
import io.netty.util.internal.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.Cookie;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.MimeHeaders;

public class ApacheLogFormat implements AccessLogFormat {
    public static final ApacheLogFormat AGENT = new ApacheLogFormat(AGENT_FORMAT);
    public static final String AGENT_FORMAT = "%{User-agent}i";
    public static final ApacheLogFormat AGENT_UTC;
    public static final ApacheLogFormat COMBINED = new ApacheLogFormat(COMBINED_FORMAT);
    public static final String COMBINED_FORMAT = "%h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"";
    public static final ApacheLogFormat COMBINED_UTC;
    public static final ApacheLogFormat COMMON = new ApacheLogFormat(COMMON_FORMAT);
    public static final String COMMON_FORMAT = "%h - %u %t \"%r\" %s %b";
    public static final ApacheLogFormat COMMON_UTC;
    private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
    public static final ApacheLogFormat REFERER = new ApacheLogFormat(REFERER_FORMAT);
    public static final String REFERER_FORMAT = "%{Referer}i -> %U";
    public static final ApacheLogFormat REFERER_UTC;
    private static final TimeZone UTC;
    public static final ApacheLogFormat VHOST_COMBINED = new ApacheLogFormat(VHOST_COMBINED_FORMAT);
    public static final String VHOST_COMBINED_FORMAT = "%v %h - %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-agent}i\"";
    public static final ApacheLogFormat VHOST_COMBINED_UTC;
    public static final ApacheLogFormat VHOST_COMMON = new ApacheLogFormat(VHOST_COMMON_FORMAT);
    public static final String VHOST_COMMON_FORMAT = "%v %h - %u %t \"%r\" %s %b";
    public static final ApacheLogFormat VHOST_COMMON_UTC;
    private final List<Field> fields;
    private final TimeZone timeZone;

    static {
        TimeZone timeZone2 = TimeZone.getTimeZone("UTC");
        UTC = timeZone2;
        COMMON_UTC = new ApacheLogFormat(timeZone2, COMMON_FORMAT);
        COMBINED_UTC = new ApacheLogFormat(timeZone2, COMBINED_FORMAT);
        VHOST_COMMON_UTC = new ApacheLogFormat(timeZone2, VHOST_COMMON_FORMAT);
        VHOST_COMBINED_UTC = new ApacheLogFormat(timeZone2, VHOST_COMBINED_FORMAT);
        REFERER_UTC = new ApacheLogFormat(timeZone2, REFERER_FORMAT);
        AGENT_UTC = new ApacheLogFormat(timeZone2, AGENT_FORMAT);
    }

    public ApacheLogFormat(String format) {
        this(TimeZone.getDefault(), format);
    }

    public ApacheLogFormat(TimeZone timeZone2, String format) {
        if (timeZone2 != null) {
            this.fields = new ArrayList();
            this.timeZone = timeZone2;
            parse(format);
            return;
        }
        throw new NullPointerException("Null time zone");
    }

    public String format(Response response, Date timeStamp, long responseNanos) {
        StringBuilder builder = new StringBuilder();
        Request request = response.getRequest();
        for (Field format : this.fields) {
            try {
                format.format(builder, request, response, timeStamp, responseNanos);
            } catch (Exception exception) {
                LOGGER.log(Level.WARNING, "Exception formatting access log entry", exception);
                builder.append('-');
            }
        }
        return builder.toString();
    }

    /* access modifiers changed from: package-private */
    public String unsafeFormat(Response response, Date timeStamp, long responseNanos) {
        StringBuilder builder = new StringBuilder();
        Request request = response.getRequest();
        for (Field field : this.fields) {
            field.format(builder, request, response, timeStamp, responseNanos);
        }
        return builder.toString();
    }

    public String getFormat() {
        StringBuilder builder = new StringBuilder();
        for (Field field : this.fields) {
            builder.append(field.toString());
        }
        return builder.toString();
    }

    private void parse(String format) {
        int x = 0;
        while (x < format.length()) {
            switch (format.charAt(x)) {
                case '%':
                    x = parseFormat(format, (String) null, x);
                    break;
                case '\\':
                    x = parseEscape(format, x);
                    break;
                default:
                    addLiteral(format.charAt(x));
                    break;
            }
            x++;
        }
    }

    private int parseFormat(String format, String parameter, int position) {
        int position2 = position + 1;
        if (position2 < format.length()) {
            char field = format.charAt(position2);
            if (parameter != null) {
                switch (field) {
                    case 'C':
                    case 'T':
                    case 'h':
                    case 'i':
                    case 'o':
                    case 'p':
                    case 't':
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported parameter \"" + parameter + "\" for field '" + field + "' in [" + format + "] at character " + position2);
                }
            }
            switch (field) {
                case '%':
                    addLiteral('%');
                    break;
                case 'A':
                    this.fields.add(new LocalAddressField());
                    break;
                case 'B':
                    this.fields.add(new ResponseSizeField(true));
                    break;
                case 'C':
                    this.fields.add(new RequestCookieField(parameter));
                    break;
                case 'D':
                    this.fields.add(new ResponseTimeField("micro", format, position2));
                    break;
                case 'H':
                    this.fields.add(new RequestProtocolField());
                    break;
                case 'T':
                    this.fields.add(new ResponseTimeField(parameter, format, position2));
                    break;
                case 'U':
                    this.fields.add(new RequestURIField());
                    break;
                case 'a':
                    this.fields.add(new RemoteAddressField());
                    break;
                case 'b':
                    this.fields.add(new ResponseSizeField(false));
                    break;
                case 'h':
                    this.fields.add(parseLocal(parameter, false, field, format, position2) ? new LocalHostField() : new RemoteHostField());
                    break;
                case 'i':
                    this.fields.add(new RequestHeaderField(parameter));
                    break;
                case 'm':
                    this.fields.add(new RequestMethodField());
                    break;
                case 'o':
                    this.fields.add(new ResponseHeaderField(parameter));
                    break;
                case 'p':
                    this.fields.add(parseLocal(parameter, true, field, format, position2) ? new LocalPortField() : new RemotePortField());
                    break;
                case 'q':
                    this.fields.add(new RequestQueryField());
                    break;
                case 'r':
                    this.fields.add(new RequestMethodField());
                    addLiteral(' ');
                    this.fields.add(new RequestURIField());
                    this.fields.add(new RequestQueryField());
                    addLiteral(' ');
                    this.fields.add(new RequestProtocolField());
                    break;
                case 's':
                    this.fields.add(new ResponseStatusField());
                    break;
                case 't':
                    this.fields.add(new RequestTimeField(parameter, this.timeZone));
                    break;
                case 'u':
                    this.fields.add(new RequestUserField());
                    break;
                case 'v':
                    this.fields.add(new ServerNameField());
                    break;
                case '{':
                    return parseParameter(format, position2);
                default:
                    throw new IllegalArgumentException("Unsupported field '" + field + "' in [" + format + "] at character " + position2);
            }
            return position2;
        }
        throw new IllegalArgumentException("Unterminated field declaration in [" + format + "] at character " + position2);
    }

    private boolean parseLocal(String parameter, boolean defaultValue, char field, String format, int position) {
        if (parameter == null) {
            return defaultValue;
        }
        String p = parameter.trim().toLowerCase();
        if (p.equals(ImagesContract.LOCAL)) {
            return true;
        }
        if (p.equals("remote")) {
            return false;
        }
        throw new IllegalArgumentException("Unsupported parameter \"" + parameter + "\" for field '" + field + "' in [" + format + "] at character " + position);
    }

    private int parseParameter(String format, int position) {
        int position2 = position + 1;
        if (position2 < format.length()) {
            int end = format.indexOf(125, position2);
            if (end == position2) {
                return parseFormat(format, (String) null, end);
            }
            if (end > position2) {
                return parseFormat(format, format.substring(position2, end), end);
            }
        }
        throw new IllegalArgumentException("Unterminated format parameter in [" + format + "] at character " + position2);
    }

    private int parseEscape(String format, int position) {
        int position2 = position + 1;
        if (position2 < format.length()) {
            char escaped = format.charAt(position2);
            switch (escaped) {
                case 'b':
                    addLiteral(8);
                    break;
                case 'f':
                    addLiteral(12);
                    break;
                case 'n':
                    addLiteral(10);
                    break;
                case 'r':
                    addLiteral(StringUtil.CARRIAGE_RETURN);
                    break;
                case 't':
                    addLiteral(9);
                    break;
                default:
                    addLiteral(escaped);
                    break;
            }
            return position2;
        }
        throw new IllegalArgumentException("Unterminated escape sequence in [" + format + "] at character " + position2);
    }

    private void addLiteral(char c) {
        if (!this.fields.isEmpty()) {
            List<Field> list = this.fields;
            Field last = list.get(list.size() - 1);
            if (last instanceof LiteralField) {
                ((LiteralField) last).append(c);
                return;
            }
        }
        this.fields.add(new LiteralField(c));
    }

    public static abstract class Field {
        /* access modifiers changed from: package-private */
        public abstract StringBuilder format(StringBuilder sb, Request request, Response response, Date date, long j);

        public abstract String toString();

        private Field() {
        }

        /* synthetic */ Field(AnonymousClass1 x0) {
            this();
        }
    }

    public static abstract class AbstractField extends Field {
        private final char format;
        private final String parameter;

        protected AbstractField(char format2) {
            this(format2, (String) null);
        }

        protected AbstractField(char format2, String parameter2) {
            super((AnonymousClass1) null);
            this.format = format2;
            this.parameter = parameter2;
        }

        public final String toString() {
            StringBuilder builder = new StringBuilder().append('%');
            if (this.parameter != null) {
                builder.append('{');
                builder.append(this.parameter);
                builder.append('}');
            }
            builder.append(this.format);
            return builder.toString();
        }
    }

    public static abstract class HeaderField extends AbstractField {
        final String name;

        HeaderField(char format, String name2) {
            super(format, name2.trim().toLowerCase());
            this.name = name2.trim().toLowerCase();
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, MimeHeaders headers) {
            Iterator<String> iterator = headers.values(this.name).iterator();
            if (iterator.hasNext()) {
                builder.append(iterator.next());
            }
            while (iterator.hasNext()) {
                builder.append("; ");
                builder.append(iterator.next());
            }
            return builder;
        }
    }

    public static class LiteralField extends Field {
        final StringBuilder contents;

        LiteralField(char character) {
            super((AnonymousClass1) null);
            StringBuilder sb = new StringBuilder();
            sb.append(character);
            this.contents = sb;
        }

        /* access modifiers changed from: package-private */
        public void append(char character) {
            this.contents.append(character);
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            builder.append(this.contents);
            return builder;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < this.contents.length(); x++) {
                char character = this.contents.charAt(x);
                switch (character) {
                    case '%':
                        builder.append('%');
                        break;
                    case 'b':
                    case 'f':
                    case 'n':
                    case 'r':
                    case 't':
                        builder.append('\\');
                        break;
                }
                builder.append(character);
            }
            return builder.toString();
        }
    }

    public static class ServerNameField extends AbstractField {
        ServerNameField() {
            super('v');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String name = request.getServerName();
            builder.append(name == null ? "-" : name);
            return builder;
        }
    }

    public static class LocalHostField extends AbstractField {
        LocalHostField() {
            super('h', ImagesContract.LOCAL);
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String host = request.getLocalName();
            builder.append(host == null ? "-" : host);
            return builder;
        }
    }

    public static class LocalAddressField extends AbstractField {
        LocalAddressField() {
            super('A');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String address = request.getLocalAddr();
            builder.append(address == null ? "-" : address);
            return builder;
        }
    }

    public static class LocalPortField extends AbstractField {
        LocalPortField() {
            super('p');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            int port = request.getLocalPort();
            builder.append(port < 1 ? "-" : Integer.valueOf(port));
            return builder;
        }
    }

    public static class RemoteHostField extends AbstractField {
        RemoteHostField() {
            super('h');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String host = request.getRemoteHost();
            builder.append(host == null ? "-" : host);
            return builder;
        }
    }

    public static class RemoteAddressField extends AbstractField {
        RemoteAddressField() {
            super('a');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String address = request.getRemoteAddr();
            builder.append(address == null ? "-" : address);
            return builder;
        }
    }

    public static class RemotePortField extends AbstractField {
        RemotePortField() {
            super('p', "remote");
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            int port = request.getRemotePort();
            builder.append(port < 1 ? "-" : Integer.valueOf(port));
            return builder;
        }
    }

    public static class RequestTimeField extends Field {
        private static final String DEFAULT_PATTERN = "[yyyy/MMM/dd:HH:mm:ss Z]";
        private final String format;
        private final String pattern;
        private final SimpleDateFormatThreadLocal simpleDateFormat;
        private final TimeZone timeZone;

        RequestTimeField(String format2, TimeZone zone) {
            super((AnonymousClass1) null);
            this.format = format2;
            if (format2 == null) {
                this.pattern = DEFAULT_PATTERN;
                this.timeZone = zone;
            } else {
                int pos = format2.lastIndexOf(64);
                if (pos < 0 || (pos > 0 && format2.charAt(pos - 1) == '@')) {
                    this.pattern = format2.replace("@@", "@");
                    this.timeZone = zone;
                } else if (pos == 0) {
                    this.pattern = DEFAULT_PATTERN;
                    this.timeZone = TimeZone.getTimeZone(format2.substring(1));
                } else {
                    this.pattern = format2.substring(0, pos).replace("@@", "@");
                    this.timeZone = TimeZone.getTimeZone(format2.substring(pos + 1));
                }
            }
            this.simpleDateFormat = new SimpleDateFormatThreadLocal(this.pattern);
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            if (timeStamp == null) {
                builder.append('-');
                return builder;
            }
            SimpleDateFormat format2 = (SimpleDateFormat) this.simpleDateFormat.get();
            format2.setTimeZone(this.timeZone);
            builder.append(format2.format(timeStamp));
            return builder;
        }

        public String toString() {
            if (this.format == null) {
                return "%t";
            }
            return "%{" + this.format + "}t";
        }
    }

    public static class RequestMethodField extends AbstractField {
        RequestMethodField() {
            super('m');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            Method method = request.getMethod();
            builder.append(method == null ? "-" : method.toString());
            return builder;
        }
    }

    public static class RequestUserField extends AbstractField {
        RequestUserField() {
            super('u');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String user = request.getRemoteUser();
            builder.append(user == null ? "-" : user);
            return builder;
        }
    }

    public static class RequestURIField extends AbstractField {
        RequestURIField() {
            super('U');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String uri = request.getRequestURI();
            builder.append(uri == null ? "-" : uri);
            return builder;
        }
    }

    public static class RequestQueryField extends AbstractField {
        RequestQueryField() {
            super('q');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            String query = request.getQueryString();
            if (query != null) {
                builder.append('?');
                builder.append(query);
            }
            return builder;
        }
    }

    public static class RequestProtocolField extends AbstractField {
        RequestProtocolField() {
            super('H');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            Protocol protocol = request.getProtocol();
            if (protocol == null) {
                builder.append("-");
                return builder;
            }
            switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$Protocol[protocol.ordinal()]) {
                case 1:
                    builder.append("HTTP/0.9");
                    return builder;
                case 2:
                    builder.append(Constants.HTTP_10);
                    return builder;
                case 3:
                    builder.append(Constants.HTTP_11);
                    return builder;
                default:
                    builder.append("-");
                    return builder;
            }
        }
    }

    /* renamed from: org.glassfish.grizzly.http.server.accesslog.ApacheLogFormat$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$Protocol;

        static {
            int[] iArr = new int[Protocol.values().length];
            $SwitchMap$org$glassfish$grizzly$http$Protocol = iArr;
            try {
                iArr[Protocol.HTTP_0_9.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$Protocol[Protocol.HTTP_1_0.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$http$Protocol[Protocol.HTTP_1_1.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static class RequestHeaderField extends HeaderField {
        RequestHeaderField(String name) {
            super('i', name);
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            return format(builder, request.getRequest().getHeaders());
        }
    }

    public static class RequestCookieField extends AbstractField {
        final String name;

        RequestCookieField(String name2) {
            super('C', name2.trim().toLowerCase());
            this.name = name2.trim().toLowerCase();
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (this.name.equals(cookie.getName().toLowerCase())) {
                        builder.append(cookie.getValue());
                        return builder;
                    }
                }
            }
            return builder;
        }
    }

    public static class ResponseStatusField extends AbstractField {
        ResponseStatusField() {
            super('s');
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            int status = response.getStatus();
            if (status < 10) {
                builder.append('0');
            }
            if (status < 100) {
                builder.append('0');
            }
            builder.append(status);
            return builder;
        }
    }

    public static class ResponseSizeField extends AbstractField {
        final String zero;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        ResponseSizeField(boolean zero2) {
            super(zero2 ? 'B' : 'b');
            this.zero = zero2 ? "0" : "-";
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            long size = response.getContentLengthLong();
            builder.append(size < 1 ? this.zero : Long.toString(size));
            return builder;
        }
    }

    public static class ResponseTimeField extends Field {
        private final long scale;

        ResponseTimeField(String unit, String format, int position) {
            super((AnonymousClass1) null);
            if (unit == null) {
                this.scale = 1000000000;
                return;
            }
            String s = unit.trim().toLowerCase();
            if (s.equals("n") || s.equals("nano") || s.equals("nanos") || s.equals("nanosec") || s.equals("nanosecs") || s.equals("nanosecond") || s.equals("nanoseconds")) {
                this.scale = 1;
            } else if (s.equals("micro") || s.equals("micros") || s.equals("microsec") || s.equals("microsecs") || s.equals("microsecond") || s.equals("microseconds")) {
                this.scale = 1000;
            } else if (s.equals("m") || s.equals("milli") || s.equals("millis") || s.equals("millisec") || s.equals("millisecs") || s.equals("millisecond") || s.equals("milliseconds")) {
                this.scale = Time.NANOS_IN_A_MILLISECOND;
            } else if (s.equals("s") || s.equals("sec") || s.equals("secs") || s.equals("second") || s.equals("seconds")) {
                this.scale = 1000000000;
            } else {
                throw new IllegalArgumentException("Unsupported time unit \"" + unit + "\" for field 'T' in [" + format + "] at character " + position);
            }
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            if (responseNanos < 0) {
                builder.append('-');
                return builder;
            }
            builder.append(responseNanos / this.scale);
            return builder;
        }

        public String toString() {
            StringBuilder string = new StringBuilder().append('%');
            long j = this.scale;
            if (j == 1) {
                string.append("{n}T");
            } else if (j == 1000) {
                string.append('D');
            } else if (j == Time.NANOS_IN_A_MILLISECOND) {
                string.append("{m}T");
            } else if (j == 1000000000) {
                string.append('T');
            } else {
                string.append('{');
                string.append(this.scale);
                string.append("}T");
            }
            return string.toString();
        }
    }

    public static class ResponseHeaderField extends HeaderField {
        ResponseHeaderField(String name) {
            super('o', name);
        }

        /* access modifiers changed from: package-private */
        public StringBuilder format(StringBuilder builder, Request request, Response response, Date timeStamp, long responseNanos) {
            return format(builder, response.getResponse().getHeaders());
        }
    }
}
