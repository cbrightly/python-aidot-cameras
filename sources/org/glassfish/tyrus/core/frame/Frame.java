package org.glassfish.tyrus.core.frame;

public class Frame {
    private final boolean controlFrame;
    /* access modifiers changed from: private */
    public final boolean fin;
    /* access modifiers changed from: private */
    public final boolean mask;
    /* access modifiers changed from: private */
    public final Integer maskingKey;
    /* access modifiers changed from: private */
    public final byte opcode;
    /* access modifiers changed from: private */
    public final byte[] payloadData;
    /* access modifiers changed from: private */
    public final long payloadLength;
    /* access modifiers changed from: private */
    public final boolean rsv1;
    /* access modifiers changed from: private */
    public final boolean rsv2;
    /* access modifiers changed from: private */
    public final boolean rsv3;

    protected Frame(Frame frame) {
        this.fin = frame.fin;
        this.rsv1 = frame.rsv1;
        this.rsv2 = frame.rsv2;
        this.rsv3 = frame.rsv3;
        this.mask = frame.mask;
        byte b = frame.opcode;
        this.opcode = b;
        this.payloadLength = frame.payloadLength;
        this.maskingKey = frame.maskingKey;
        this.payloadData = frame.payloadData;
        this.controlFrame = (b & 8) == 8;
    }

    private Frame(boolean fin2, boolean rsv12, boolean rsv22, boolean rsv32, boolean mask2, byte opcode2, long payloadLength2, Integer maskingKey2, byte[] payloadData2) {
        this.fin = fin2;
        this.rsv1 = rsv12;
        this.rsv2 = rsv22;
        this.rsv3 = rsv32;
        this.mask = mask2;
        this.opcode = opcode2;
        this.payloadLength = payloadLength2;
        this.maskingKey = maskingKey2;
        this.payloadData = payloadData2;
        this.controlFrame = (opcode2 & 8) == 8;
    }

    public boolean isFin() {
        return this.fin;
    }

    public boolean isRsv1() {
        return this.rsv1;
    }

    public boolean isRsv2() {
        return this.rsv2;
    }

    public boolean isRsv3() {
        return this.rsv3;
    }

    public boolean isMask() {
        return this.mask;
    }

    public byte getOpcode() {
        return this.opcode;
    }

    public long getPayloadLength() {
        return this.payloadLength;
    }

    public Integer getMaskingKey() {
        return this.maskingKey;
    }

    public byte[] getPayloadData() {
        long j = this.payloadLength;
        byte[] tmp = new byte[((int) j)];
        System.arraycopy(this.payloadData, 0, tmp, 0, (int) j);
        return tmp;
    }

    public boolean isControlFrame() {
        return this.controlFrame;
    }

    public String toString() {
        return "Frame{" + "fin=" + this.fin + ", rsv1=" + this.rsv1 + ", rsv2=" + this.rsv2 + ", rsv3=" + this.rsv3 + ", mask=" + this.mask + ", opcode=" + this.opcode + ", payloadLength=" + this.payloadLength + ", maskingKey=" + this.maskingKey + '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Frame frame) {
        return new Builder(frame);
    }

    public static final class Builder {
        private boolean fin;
        private boolean mask;
        private Integer maskingKey = null;
        private byte opcode;
        private byte[] payloadData;
        private long payloadLength;
        private boolean rsv1;
        private boolean rsv2;
        private boolean rsv3;

        public Builder() {
        }

        public Builder(Frame frame) {
            this.fin = frame.fin;
            this.rsv1 = frame.rsv1;
            this.rsv2 = frame.rsv2;
            this.rsv3 = frame.rsv3;
            this.mask = frame.mask;
            this.opcode = frame.opcode;
            this.payloadLength = frame.payloadLength;
            this.maskingKey = frame.maskingKey;
            this.payloadData = frame.payloadData;
        }

        public Frame build() {
            return new Frame(this.fin, this.rsv1, this.rsv2, this.rsv3, this.mask, this.opcode, this.payloadLength, this.maskingKey, this.payloadData);
        }

        public Builder fin(boolean fin2) {
            this.fin = fin2;
            return this;
        }

        public Builder rsv1(boolean rsv12) {
            this.rsv1 = rsv12;
            return this;
        }

        public Builder rsv2(boolean rsv22) {
            this.rsv2 = rsv22;
            return this;
        }

        public Builder rsv3(boolean rsv32) {
            this.rsv3 = rsv32;
            return this;
        }

        public Builder mask(boolean mask2) {
            this.mask = mask2;
            return this;
        }

        public Builder opcode(byte opcode2) {
            this.opcode = (byte) (opcode2 & 15);
            return this;
        }

        public Builder payloadLength(long payloadLength2) {
            this.payloadLength = payloadLength2;
            return this;
        }

        public Builder maskingKey(Integer maskingKey2) {
            this.maskingKey = maskingKey2;
            return this;
        }

        public Builder payloadData(byte[] payloadData2) {
            this.payloadData = payloadData2;
            this.payloadLength = (long) payloadData2.length;
            return this;
        }
    }
}
