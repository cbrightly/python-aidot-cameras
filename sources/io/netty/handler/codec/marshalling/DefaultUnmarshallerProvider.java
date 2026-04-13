package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public class DefaultUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;

    public DefaultUnmarshallerProvider(MarshallerFactory factory2, MarshallingConfiguration config2) {
        this.factory = factory2;
        this.config = config2;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) {
        return this.factory.createUnmarshaller(this.config);
    }
}
