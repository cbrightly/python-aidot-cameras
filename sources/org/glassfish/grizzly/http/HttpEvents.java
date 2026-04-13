package org.glassfish.grizzly.http;

import org.glassfish.grizzly.filterchain.FilterChainEvent;

public class HttpEvents {
    public static IncomingHttpUpgradeEvent createIncomingUpgradeEvent(HttpHeader httpHeader) {
        return new IncomingHttpUpgradeEvent(httpHeader);
    }

    public static OutgoingHttpUpgradeEvent createOutgoingUpgradeEvent(HttpHeader httpHeader) {
        return new OutgoingHttpUpgradeEvent(httpHeader);
    }

    public static ChangePacketInProgressEvent createChangePacketInProgressEvent(HttpHeader packet) {
        return new ChangePacketInProgressEvent(packet);
    }

    public static final class ChangePacketInProgressEvent implements FilterChainEvent {
        public static final Object TYPE = ChangePacketInProgressEvent.class.getName();
        private final HttpHeader httpHeader;

        private ChangePacketInProgressEvent(HttpHeader httpHeader2) {
            this.httpHeader = httpHeader2;
        }

        public HttpHeader getPacket() {
            return this.httpHeader;
        }

        public Object type() {
            return TYPE;
        }
    }

    public static final class ResponseCompleteEvent implements FilterChainEvent {
        public static final Object TYPE = ResponseCompleteEvent.class.getName();

        public Object type() {
            return TYPE;
        }
    }

    public static final class IncomingHttpUpgradeEvent extends HttpUpgradeEvent {
        public static final Object TYPE = IncomingHttpUpgradeEvent.class.getName();

        public /* bridge */ /* synthetic */ HttpHeader getHttpHeader() {
            return super.getHttpHeader();
        }

        private IncomingHttpUpgradeEvent(HttpHeader httpHeader) {
            super(httpHeader);
        }

        public Object type() {
            return TYPE;
        }
    }

    public static final class OutgoingHttpUpgradeEvent extends HttpUpgradeEvent {
        public static final Object TYPE = OutgoingHttpUpgradeEvent.class.getName();

        public /* bridge */ /* synthetic */ HttpHeader getHttpHeader() {
            return super.getHttpHeader();
        }

        private OutgoingHttpUpgradeEvent(HttpHeader httpHeader) {
            super(httpHeader);
        }

        public Object type() {
            return TYPE;
        }
    }

    public static abstract class HttpUpgradeEvent implements FilterChainEvent {
        private final HttpHeader httpHeader;

        private HttpUpgradeEvent(HttpHeader httpHeader2) {
            this.httpHeader = httpHeader2;
        }

        public HttpHeader getHttpHeader() {
            return this.httpHeader;
        }
    }
}
