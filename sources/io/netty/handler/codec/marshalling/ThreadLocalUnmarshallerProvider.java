package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;
    private final FastThreadLocal<Unmarshaller> unmarshallers = new FastThreadLocal<>();

    public ThreadLocalUnmarshallerProvider(MarshallerFactory factory2, MarshallingConfiguration config2) {
        this.factory = factory2;
        this.config = config2;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) {
        Unmarshaller unmarshaller = this.unmarshallers.get();
        if (unmarshaller != null) {
            return unmarshaller;
        }
        Unmarshaller unmarshaller2 = this.factory.createUnmarshaller(this.config);
        this.unmarshallers.set(unmarshaller2);
        return unmarshaller2;
    }
}
