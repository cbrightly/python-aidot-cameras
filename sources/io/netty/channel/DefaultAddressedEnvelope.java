package io.netty.channel;

import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

public class DefaultAddressedEnvelope<M, A extends SocketAddress> implements AddressedEnvelope<M, A> {
    private final M message;
    private final A recipient;
    private final A sender;

    public DefaultAddressedEnvelope(M message2, A recipient2, A sender2) {
        if (message2 != null) {
            this.message = message2;
            this.sender = sender2;
            this.recipient = recipient2;
            return;
        }
        throw new NullPointerException("message");
    }

    public DefaultAddressedEnvelope(M message2, A recipient2) {
        this(message2, recipient2, (A) null);
    }

    public M content() {
        return this.message;
    }

    public A sender() {
        return this.sender;
    }

    public A recipient() {
        return this.recipient;
    }

    public int refCnt() {
        M m = this.message;
        if (m instanceof ReferenceCounted) {
            return ((ReferenceCounted) m).refCnt();
        }
        return 1;
    }

    public AddressedEnvelope<M, A> retain() {
        ReferenceCountUtil.retain(this.message);
        return this;
    }

    public AddressedEnvelope<M, A> retain(int increment) {
        ReferenceCountUtil.retain(this.message, increment);
        return this;
    }

    public boolean release() {
        return ReferenceCountUtil.release(this.message);
    }

    public boolean release(int decrement) {
        return ReferenceCountUtil.release(this.message, decrement);
    }

    public String toString() {
        if (this.sender != null) {
            return StringUtil.simpleClassName((Object) this) + '(' + this.sender + " => " + this.recipient + ", " + this.message + ')';
        }
        return StringUtil.simpleClassName((Object) this) + "(=> " + this.recipient + ", " + this.message + ')';
    }
}
