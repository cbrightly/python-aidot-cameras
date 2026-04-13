package io.netty.channel;

import io.netty.util.ReferenceCounted;
import java.nio.channels.WritableByteChannel;

public interface FileRegion extends ReferenceCounted {
    long count();

    long position();

    long transferTo(WritableByteChannel writableByteChannel, long j);

    long transfered();
}
