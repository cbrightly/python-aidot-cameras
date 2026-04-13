package io.netty.handler.codec.serialization;

import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

public class CompactObjectInputStream extends ObjectInputStream {
    private final ClassResolver classResolver;

    CompactObjectInputStream(InputStream in, ClassResolver classResolver2) {
        super(in);
        this.classResolver = classResolver2;
    }

    /* access modifiers changed from: protected */
    public void readStreamHeader() {
        int version = readByte() & 255;
        if (version != 5) {
            throw new StreamCorruptedException("Unsupported version: " + version);
        }
    }

    /* access modifiers changed from: protected */
    public ObjectStreamClass readClassDescriptor() {
        int type = read();
        if (type >= 0) {
            switch (type) {
                case 0:
                    return super.readClassDescriptor();
                case 1:
                    return ObjectStreamClass.lookupAny(this.classResolver.resolve(readUTF()));
                default:
                    throw new StreamCorruptedException("Unexpected class descriptor type: " + type);
            }
        } else {
            throw new EOFException();
        }
    }

    /* access modifiers changed from: protected */
    public Class<?> resolveClass(ObjectStreamClass desc) {
        try {
            return this.classResolver.resolve(desc.getName());
        } catch (ClassNotFoundException e) {
            return super.resolveClass(desc);
        }
    }
}
