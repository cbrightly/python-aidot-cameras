package com.alibaba.fastjson.asm;

public class Label {
    static final int FORWARD_REFERENCE_HANDLE_MASK = 268435455;
    static final int FORWARD_REFERENCE_TYPE_MASK = -268435456;
    static final int FORWARD_REFERENCE_TYPE_SHORT = 268435456;
    static final int FORWARD_REFERENCE_TYPE_WIDE = 536870912;
    int inputStackTop;
    Label next;
    int outputStackMax;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int status;
    Label successor;

    /* access modifiers changed from: package-private */
    public void put(MethodWriter owner, ByteVector out, int source, boolean wideOffset) {
        if ((this.status & 2) == 0) {
            if (wideOffset) {
                addReference(source, out.length, FORWARD_REFERENCE_TYPE_WIDE);
                out.putInt(-1);
                return;
            }
            addReference(source, out.length, 268435456);
            out.putShort(-1);
        } else if (wideOffset) {
            out.putInt(this.position - source);
        } else {
            out.putShort(this.position - source);
        }
    }

    private void addReference(int sourcePosition, int referencePosition, int referenceType) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        int i = this.referenceCount;
        int[] iArr = this.srcAndRefPositions;
        if (i >= iArr.length) {
            int[] a = new int[(iArr.length + 6)];
            System.arraycopy(iArr, 0, a, 0, iArr.length);
            this.srcAndRefPositions = a;
        }
        int[] a2 = this.srcAndRefPositions;
        int i2 = this.referenceCount;
        int i3 = i2 + 1;
        this.referenceCount = i3;
        a2[i2] = sourcePosition;
        this.referenceCount = i3 + 1;
        a2[i3] = referencePosition | referenceType;
    }

    /* access modifiers changed from: package-private */
    public void resolve(MethodWriter owner, int position2, byte[] data) {
        this.status |= 2;
        this.position = position2;
        int source = 0;
        while (source < this.referenceCount) {
            int[] iArr = this.srcAndRefPositions;
            int i = source + 1;
            int i2 = iArr[source];
            int i3 = i + 1;
            int reference = iArr[i];
            int handle = FORWARD_REFERENCE_HANDLE_MASK & reference;
            int offset = position2 - i2;
            if ((FORWARD_REFERENCE_TYPE_MASK & reference) == 268435456) {
                data[handle] = (byte) (offset >>> 8);
                data[handle + 1] = (byte) offset;
            } else {
                int handle2 = handle + 1;
                data[handle] = (byte) (offset >>> 24);
                int handle3 = handle2 + 1;
                data[handle2] = (byte) (offset >>> 16);
                data[handle3] = (byte) (offset >>> 8);
                data[handle3 + 1] = (byte) offset;
            }
            source = i3;
        }
    }
}
