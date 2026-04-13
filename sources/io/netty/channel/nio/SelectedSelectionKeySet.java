package io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

public final class SelectedSelectionKeySet extends AbstractSet<SelectionKey> {
    SelectionKey[] keys = new SelectionKey[1024];
    int size;

    SelectedSelectionKeySet() {
    }

    public boolean add(SelectionKey o) {
        if (o == null) {
            return false;
        }
        SelectionKey[] selectionKeyArr = this.keys;
        int i = this.size;
        int i2 = i + 1;
        this.size = i2;
        selectionKeyArr[i] = o;
        if (i2 != selectionKeyArr.length) {
            return true;
        }
        increaseCapacity();
        return true;
    }

    public int size() {
        return this.size;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        reset(0);
    }

    /* access modifiers changed from: package-private */
    public void reset(int start) {
        Arrays.fill(this.keys, start, this.size, (Object) null);
        this.size = 0;
    }

    private void increaseCapacity() {
        SelectionKey[] selectionKeyArr = this.keys;
        SelectionKey[] newKeys = new SelectionKey[(selectionKeyArr.length << 1)];
        System.arraycopy(selectionKeyArr, 0, newKeys, 0, this.size);
        this.keys = newKeys;
    }
}
