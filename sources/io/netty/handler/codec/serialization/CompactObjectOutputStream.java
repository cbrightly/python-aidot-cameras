package io.netty.handler.codec.serialization;

import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

public class CompactObjectOutputStream extends ObjectOutputStream {
    static final int TYPE_FAT_DESCRIPTOR = 0;
    static final int TYPE_THIN_DESCRIPTOR = 1;

    CompactObjectOutputStream(OutputStream out) {
        super(out);
    }

    /* access modifiers changed from: protected */
    public void writeStreamHeader() {
        writeByte(5);
    }

    /* access modifiers changed from: protected */
    public void writeClassDescriptor(ObjectStreamClass desc) {
        Class<?> clazz = desc.forClass();
        if (clazz.isPrimitive() || clazz.isArray() || clazz.isInterface() || desc.getSerialVersionUID() == 0) {
            write(0);
            super.writeClassDescriptor(desc);
            return;
        }
        write(1);
        writeUTF(desc.getName());
    }
}
