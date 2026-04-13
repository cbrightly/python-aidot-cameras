package io.netty.buffer;

public final class SlicedAbstractByteBuf extends SlicedByteBuf {
    SlicedAbstractByteBuf(AbstractByteBuf buffer, int index, int length) {
        super(buffer, index, length);
    }

    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf) super.unwrap();
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return unwrap()._getByte(idx(index));
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return unwrap()._getShort(idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return unwrap()._getUnsignedMedium(idx(index));
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return unwrap()._getInt(idx(index));
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return unwrap()._getLong(idx(index));
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        unwrap()._setByte(idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        unwrap()._setShort(idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        unwrap()._setMedium(idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        unwrap()._setInt(idx(index), value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        unwrap()._setLong(idx(index), value);
    }
}
