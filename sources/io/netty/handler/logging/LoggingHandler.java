package io.netty.handler.logging;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import okhttp3.internal.cache.d;

@ChannelHandler.Sharable
public class LoggingHandler extends ChannelDuplexHandler {
    private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
    protected final InternalLogLevel internalLevel;
    private final LogLevel level;
    protected final InternalLogger logger;

    public LoggingHandler() {
        this(DEFAULT_LEVEL);
    }

    public LoggingHandler(LogLevel level2) {
        if (level2 != null) {
            this.logger = InternalLoggerFactory.getInstance(getClass());
            this.level = level2;
            this.internalLevel = level2.toInternalLevel();
            return;
        }
        throw new NullPointerException(FirebaseAnalytics.Param.LEVEL);
    }

    public LoggingHandler(Class<?> clazz) {
        this(clazz, DEFAULT_LEVEL);
    }

    public LoggingHandler(Class<?> clazz, LogLevel level2) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        } else if (level2 != null) {
            this.logger = InternalLoggerFactory.getInstance(clazz);
            this.level = level2;
            this.internalLevel = level2.toInternalLevel();
        } else {
            throw new NullPointerException(FirebaseAnalytics.Param.LEVEL);
        }
    }

    public LoggingHandler(String name) {
        this(name, DEFAULT_LEVEL);
    }

    public LoggingHandler(String name, LogLevel level2) {
        if (name == null) {
            throw new NullPointerException("name");
        } else if (level2 != null) {
            this.logger = InternalLoggerFactory.getInstance(name);
            this.level = level2;
            this.internalLevel = level2.toInternalLevel();
        } else {
            throw new NullPointerException(FirebaseAnalytics.Param.LEVEL);
        }
    }

    public LogLevel level() {
        return this.level;
    }

    /* access modifiers changed from: protected */
    public String format(ChannelHandlerContext ctx, String message) {
        String chStr = ctx.channel().toString();
        StringBuilder sb = new StringBuilder(chStr.length() + message.length() + 1);
        sb.append(chStr);
        sb.append(' ');
        sb.append(message);
        return sb.toString();
    }

    public void channelRegistered(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "REGISTERED"));
        }
        super.channelRegistered(ctx);
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "UNREGISTERED"));
        }
        super.channelUnregistered(ctx);
    }

    public void channelActive(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "ACTIVE"));
        }
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "INACTIVE"));
        }
        super.channelInactive(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (this.logger.isEnabled(this.internalLevel)) {
            InternalLogger internalLogger = this.logger;
            InternalLogLevel internalLogLevel = this.internalLevel;
            internalLogger.log(internalLogLevel, format(ctx, "EXCEPTION: " + cause), cause);
        }
        super.exceptionCaught(ctx, cause);
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (this.logger.isEnabled(this.internalLevel)) {
            InternalLogger internalLogger = this.logger;
            InternalLogLevel internalLogLevel = this.internalLevel;
            internalLogger.log(internalLogLevel, format(ctx, "USER_EVENT: " + evt));
        }
        super.userEventTriggered(ctx, evt);
    }

    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) {
        if (this.logger.isEnabled(this.internalLevel)) {
            InternalLogger internalLogger = this.logger;
            InternalLogLevel internalLogLevel = this.internalLevel;
            internalLogger.log(internalLogLevel, format(ctx, "BIND(" + localAddress + ')'));
        }
        super.bind(ctx, localAddress, promise);
    }

    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        if (this.logger.isEnabled(this.internalLevel)) {
            InternalLogger internalLogger = this.logger;
            InternalLogLevel internalLogLevel = this.internalLevel;
            internalLogger.log(internalLogLevel, format(ctx, "CONNECT(" + remoteAddress + ", " + localAddress + ')'));
        }
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "DISCONNECT()"));
        }
        super.disconnect(ctx, promise);
    }

    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "CLOSE()"));
        }
        super.close(ctx, promise);
    }

    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "DEREGISTER()"));
        }
        super.deregister(ctx, promise);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "READ COMPLETE"));
        }
        ctx.fireChannelReadComplete();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logMessage(ctx, d.a2, msg);
        ctx.fireChannelRead(msg);
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        logMessage(ctx, "WRITE", msg);
        ctx.write(msg, promise);
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "WRITABILITY CHANGED"));
        }
        ctx.fireChannelWritabilityChanged();
    }

    public void flush(ChannelHandlerContext ctx) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, "FLUSH"));
        }
        ctx.flush();
    }

    private void logMessage(ChannelHandlerContext ctx, String eventName, Object msg) {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(ctx, formatMessage(eventName, msg)));
        }
    }

    /* access modifiers changed from: protected */
    public String formatMessage(String eventName, Object msg) {
        if (msg instanceof ByteBuf) {
            return formatByteBuf(eventName, (ByteBuf) msg);
        }
        if (msg instanceof ByteBufHolder) {
            return formatByteBufHolder(eventName, (ByteBufHolder) msg);
        }
        return formatNonByteBuf(eventName, msg);
    }

    /* access modifiers changed from: protected */
    public String formatByteBuf(String eventName, ByteBuf msg) {
        int length = msg.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(eventName.length() + 4);
            buf.append(eventName);
            buf.append(": 0B");
            return buf.toString();
        }
        StringBuilder buf2 = new StringBuilder(eventName.length() + 2 + 10 + 1 + 2 + (((length / 16) + (length % 15 == 0 ? 0 : 1) + 4) * 80));
        buf2.append(eventName);
        buf2.append(": ");
        buf2.append(length);
        buf2.append('B');
        buf2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(buf2, msg);
        return buf2.toString();
    }

    /* access modifiers changed from: protected */
    public String formatNonByteBuf(String eventName, Object msg) {
        return eventName + ": " + msg;
    }

    /* access modifiers changed from: protected */
    public String formatByteBufHolder(String eventName, ByteBufHolder msg) {
        String msgStr = msg.toString();
        ByteBuf content = msg.content();
        int length = content.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(eventName.length() + 2 + msgStr.length() + 4);
            buf.append(eventName);
            buf.append(", ");
            buf.append(msgStr);
            buf.append(", 0B");
            return buf.toString();
        }
        StringBuilder buf2 = new StringBuilder(eventName.length() + 2 + msgStr.length() + 2 + 10 + 1 + 2 + (((length / 16) + (length % 15 == 0 ? 0 : 1) + 4) * 80));
        buf2.append(eventName);
        buf2.append(": ");
        buf2.append(msgStr);
        buf2.append(", ");
        buf2.append(length);
        buf2.append('B');
        buf2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(buf2, content);
        return buf2.toString();
    }
}
