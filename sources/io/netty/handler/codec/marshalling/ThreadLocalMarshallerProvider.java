package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

public class ThreadLocalMarshallerProvider implements MarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;
    private final FastThreadLocal<Marshaller> marshallers = new FastThreadLocal<>();

    public ThreadLocalMarshallerProvider(MarshallerFactory factory2, MarshallingConfiguration config2) {
        this.factory = factory2;
        this.config = config2;
    }

    public Marshaller getMarshaller(ChannelHandlerContext ctx) {
        Marshaller marshaller = this.marshallers.get();
        if (marshaller != null) {
            return marshaller;
        }
        Marshaller marshaller2 = this.factory.createMarshaller(this.config);
        this.marshallers.set(marshaller2);
        return marshaller2;
    }
}
