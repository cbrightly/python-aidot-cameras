package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

public class DefaultMarshallerProvider implements MarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;

    public DefaultMarshallerProvider(MarshallerFactory factory2, MarshallingConfiguration config2) {
        this.factory = factory2;
        this.config = config2;
    }

    public Marshaller getMarshaller(ChannelHandlerContext ctx) {
        return this.factory.createMarshaller(this.config);
    }
}
