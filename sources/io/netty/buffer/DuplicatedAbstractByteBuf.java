package io.netty.buffer;

public final class DuplicatedAbstractByteBuf extends DuplicatedByteBuf {
    DuplicatedAbstractByteBuf(AbstractByteBuf buffer) {
        super(buffer);
    }

    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf) super.unwrap();
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        return unwrap()._getByte(index);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        return unwrap()._getShort(index);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        return unwrap()._getUnsignedMedium(index);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        return unwrap()._getInt(index);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        return unwrap()._getLong(index);
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        unwrap()._setByte(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        unwrap()._setShort(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        unwrap()._setMedium(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        unwrap()._setInt(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        unwrap()._setLong(index, value);
    }
}
