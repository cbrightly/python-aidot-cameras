package io.netty.buffer;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.glassfish.grizzly.http.server.util.MappingData;

public class CompositeByteBuf extends AbstractReferenceCountedByteBuf implements Iterable<ByteBuf> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Iterator<ByteBuf> EMPTY_ITERATOR = Collections.emptyList().iterator();
    private static final ByteBuffer EMPTY_NIO_BUFFER = Unpooled.EMPTY_BUFFER.nioBuffer();
    private final ByteBufAllocator alloc;
    /* access modifiers changed from: private */
    public final ComponentList components;
    private final boolean direct;
    private boolean freed;
    private final int maxNumComponents;

    static {
        Class<CompositeByteBuf> cls = CompositeByteBuf.class;
    }

    public CompositeByteBuf(ByteBufAllocator alloc2, boolean direct2, int maxNumComponents2) {
        super(Integer.MAX_VALUE);
        if (alloc2 != null) {
            this.alloc = alloc2;
            this.direct = direct2;
            this.maxNumComponents = maxNumComponents2;
            this.components = newList(maxNumComponents2);
            return;
        }
        throw new NullPointerException("alloc");
    }

    public CompositeByteBuf(ByteBufAllocator alloc2, boolean direct2, int maxNumComponents2, ByteBuf... buffers) {
        this(alloc2, direct2, maxNumComponents2, buffers, 0, buffers.length);
    }

    CompositeByteBuf(ByteBufAllocator alloc2, boolean direct2, int maxNumComponents2, ByteBuf[] buffers, int offset, int len) {
        super(Integer.MAX_VALUE);
        if (alloc2 == null) {
            throw new NullPointerException("alloc");
        } else if (maxNumComponents2 >= 2) {
            this.alloc = alloc2;
            this.direct = direct2;
            this.maxNumComponents = maxNumComponents2;
            this.components = newList(maxNumComponents2);
            addComponents0(false, 0, buffers, offset, len);
            consolidateIfNeeded();
            setIndex(0, capacity());
        } else {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents2 + " (expected: >= 2)");
        }
    }

    public CompositeByteBuf(ByteBufAllocator alloc2, boolean direct2, int maxNumComponents2, Iterable<ByteBuf> buffers) {
        super(Integer.MAX_VALUE);
        if (alloc2 == null) {
            throw new NullPointerException("alloc");
        } else if (maxNumComponents2 >= 2) {
            this.alloc = alloc2;
            this.direct = direct2;
            this.maxNumComponents = maxNumComponents2;
            this.components = newList(maxNumComponents2);
            addComponents0(false, 0, buffers);
            consolidateIfNeeded();
            setIndex(0, capacity());
        } else {
            throw new IllegalArgumentException("maxNumComponents: " + maxNumComponents2 + " (expected: >= 2)");
        }
    }

    private static ComponentList newList(int maxNumComponents2) {
        return new ComponentList(Math.min(16, maxNumComponents2));
    }

    CompositeByteBuf(ByteBufAllocator alloc2) {
        super(Integer.MAX_VALUE);
        this.alloc = alloc2;
        this.direct = false;
        this.maxNumComponents = 0;
        this.components = null;
    }

    public CompositeByteBuf addComponent(ByteBuf buffer) {
        return addComponent(false, buffer);
    }

    public CompositeByteBuf addComponents(ByteBuf... buffers) {
        return addComponents(false, buffers);
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> buffers) {
        return addComponents(false, buffers);
    }

    public CompositeByteBuf addComponent(int cIndex, ByteBuf buffer) {
        return addComponent(false, cIndex, buffer);
    }

    public CompositeByteBuf addComponent(boolean increaseWriterIndex, ByteBuf buffer) {
        ObjectUtil.checkNotNull(buffer, "buffer");
        addComponent0(increaseWriterIndex, this.components.size(), buffer);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean increaseWriterIndex, ByteBuf... buffers) {
        addComponents0(increaseWriterIndex, this.components.size(), buffers, 0, buffers.length);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean increaseWriterIndex, Iterable<ByteBuf> buffers) {
        addComponents0(increaseWriterIndex, this.components.size(), buffers);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponent(boolean increaseWriterIndex, int cIndex, ByteBuf buffer) {
        ObjectUtil.checkNotNull(buffer, "buffer");
        addComponent0(increaseWriterIndex, cIndex, buffer);
        consolidateIfNeeded();
        return this;
    }

    private int addComponent0(boolean increaseWriterIndex, int cIndex, ByteBuf buffer) {
        boolean wasAdded;
        if (buffer != null) {
            boolean wasAdded2 = false;
            try {
                checkComponentIndex(cIndex);
                int readableBytes = buffer.readableBytes();
                Component c = new Component(buffer.order(ByteOrder.BIG_ENDIAN).slice());
                if (cIndex == this.components.size()) {
                    wasAdded = this.components.add(c);
                    if (cIndex == 0) {
                        c.endOffset = readableBytes;
                    } else {
                        int i = ((Component) this.components.get(cIndex - 1)).endOffset;
                        c.offset = i;
                        c.endOffset = i + readableBytes;
                    }
                } else {
                    this.components.add(cIndex, c);
                    wasAdded = true;
                    if (readableBytes != 0) {
                        updateComponentOffsets(cIndex);
                    }
                }
                if (increaseWriterIndex) {
                    writerIndex(writerIndex() + buffer.readableBytes());
                }
                return cIndex;
            } finally {
                if (!wasAdded2) {
                    buffer.release();
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public CompositeByteBuf addComponents(int cIndex, ByteBuf... buffers) {
        addComponents0(false, cIndex, buffers, 0, buffers.length);
        consolidateIfNeeded();
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x003c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int addComponents0(boolean r6, int r7, io.netty.buffer.ByteBuf[] r8, int r9, int r10) {
        /*
            r5 = this;
            java.lang.String r0 = "buffers"
            io.netty.util.internal.ObjectUtil.checkNotNull(r8, r0)
            r0 = r9
            r5.checkComponentIndex(r7)     // Catch:{ all -> 0x0036 }
        L_0x0009:
            if (r0 >= r10) goto L_0x0026
            int r1 = r0 + 1
            r0 = r8[r0]     // Catch:{ all -> 0x0024 }
            if (r0 != 0) goto L_0x0013
            r0 = r1
            goto L_0x0026
        L_0x0013:
            int r2 = r5.addComponent0(r6, r7, r0)     // Catch:{ all -> 0x0024 }
            int r7 = r2 + 1
            io.netty.buffer.CompositeByteBuf$ComponentList r2 = r5.components     // Catch:{ all -> 0x0024 }
            int r2 = r2.size()     // Catch:{ all -> 0x0024 }
            if (r7 <= r2) goto L_0x0022
            r7 = r2
        L_0x0022:
            r0 = r1
            goto L_0x0009
        L_0x0024:
            r0 = move-exception
            goto L_0x003a
        L_0x0026:
        L_0x0027:
            if (r0 >= r10) goto L_0x0035
            r1 = r8[r0]
            if (r1 == 0) goto L_0x0032
            r1.release()     // Catch:{ all -> 0x0031 }
            goto L_0x0032
        L_0x0031:
            r2 = move-exception
        L_0x0032:
            int r0 = r0 + 1
            goto L_0x0027
        L_0x0035:
            return r7
        L_0x0036:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x003a:
            if (r1 >= r10) goto L_0x0048
            r2 = r8[r1]
            if (r2 == 0) goto L_0x0045
            r2.release()     // Catch:{ all -> 0x0044 }
            goto L_0x0045
        L_0x0044:
            r3 = move-exception
        L_0x0045:
            int r1 = r1 + 1
            goto L_0x003a
        L_0x0048:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.buffer.CompositeByteBuf.addComponents0(boolean, int, io.netty.buffer.ByteBuf[], int, int):int");
    }

    public CompositeByteBuf addComponents(int cIndex, Iterable<ByteBuf> buffers) {
        addComponents0(false, cIndex, buffers);
        consolidateIfNeeded();
        return this;
    }

    private int addComponents0(boolean increaseIndex, int cIndex, Iterable<ByteBuf> buffers) {
        if (buffers instanceof ByteBuf) {
            return addComponent0(increaseIndex, cIndex, (ByteBuf) buffers);
        }
        ObjectUtil.checkNotNull(buffers, "buffers");
        if (!(buffers instanceof Collection)) {
            List<ByteBuf> list = new ArrayList<>();
            try {
                for (ByteBuf b : buffers) {
                    list.add(b);
                }
                buffers = list;
                if (buffers != list) {
                    for (ByteBuf b2 : buffers) {
                        if (b2 != null) {
                            try {
                                b2.release();
                            } catch (Throwable th) {
                            }
                        }
                    }
                }
            } catch (Throwable th2) {
            }
        }
        Collection<ByteBuf> col = (Collection) buffers;
        return addComponents0(increaseIndex, cIndex, (ByteBuf[]) col.toArray(new ByteBuf[col.size()]), 0, col.size());
    }

    private void consolidateIfNeeded() {
        int numComponents = this.components.size();
        if (numComponents > this.maxNumComponents) {
            ByteBuf consolidated = allocBuffer(((Component) this.components.get(numComponents - 1)).endOffset);
            for (int i = 0; i < numComponents; i++) {
                Component c = (Component) this.components.get(i);
                consolidated.writeBytes(c.buf);
                c.freeIfNecessary();
            }
            Component c2 = new Component(consolidated);
            c2.endOffset = c2.length;
            this.components.clear();
            this.components.add(c2);
        }
    }

    private void checkComponentIndex(int cIndex) {
        ensureAccessible();
        if (cIndex < 0 || cIndex > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[]{Integer.valueOf(cIndex), Integer.valueOf(this.components.size())}));
        }
    }

    private void checkComponentIndex(int cIndex, int numComponents) {
        ensureAccessible();
        if (cIndex < 0 || cIndex + numComponents > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[]{Integer.valueOf(cIndex), Integer.valueOf(numComponents), Integer.valueOf(this.components.size())}));
        }
    }

    private void updateComponentOffsets(int cIndex) {
        int size = this.components.size();
        if (size > cIndex) {
            Component c = (Component) this.components.get(cIndex);
            if (cIndex == 0) {
                c.offset = 0;
                c.endOffset = c.length;
                cIndex++;
            }
            for (int i = cIndex; i < size; i++) {
                Component cur = (Component) this.components.get(i);
                int i2 = ((Component) this.components.get(i - 1)).endOffset;
                cur.offset = i2;
                cur.endOffset = i2 + cur.length;
            }
        }
    }

    public CompositeByteBuf removeComponent(int cIndex) {
        checkComponentIndex(cIndex);
        Component comp = (Component) this.components.remove(cIndex);
        comp.freeIfNecessary();
        if (comp.length > 0) {
            updateComponentOffsets(cIndex);
        }
        return this;
    }

    public CompositeByteBuf removeComponents(int cIndex, int numComponents) {
        checkComponentIndex(cIndex, numComponents);
        if (numComponents == 0) {
            return this;
        }
        int endIndex = cIndex + numComponents;
        boolean needsUpdate = false;
        for (int i = cIndex; i < endIndex; i++) {
            Component c = (Component) this.components.get(i);
            if (c.length > 0) {
                needsUpdate = true;
            }
            c.freeIfNecessary();
        }
        this.components.removeRange(cIndex, endIndex);
        if (needsUpdate) {
            updateComponentOffsets(cIndex);
        }
        return this;
    }

    public Iterator<ByteBuf> iterator() {
        ensureAccessible();
        if (this.components.isEmpty()) {
            return EMPTY_ITERATOR;
        }
        return new CompositeByteBufIterator();
    }

    public List<ByteBuf> decompose(int offset, int length) {
        checkIndex(offset, length);
        if (length == 0) {
            return Collections.emptyList();
        }
        int componentId = toComponentIndex(offset);
        List<ByteBuf> slice = new ArrayList<>(this.components.size());
        Component firstC = (Component) this.components.get(componentId);
        ByteBuf first = firstC.buf.duplicate();
        first.readerIndex(offset - firstC.offset);
        ByteBuf buf = first;
        int bytesToSlice = length;
        while (true) {
            int readableBytes = buf.readableBytes();
            if (bytesToSlice > readableBytes) {
                slice.add(buf);
                bytesToSlice -= readableBytes;
                componentId++;
                buf = ((Component) this.components.get(componentId)).buf.duplicate();
                if (bytesToSlice <= 0) {
                    break;
                }
            } else {
                buf.writerIndex(buf.readerIndex() + bytesToSlice);
                slice.add(buf);
                break;
            }
        }
        for (int i = 0; i < slice.size(); i++) {
            slice.set(i, slice.get(i).slice());
        }
        return slice;
    }

    public boolean isDirect() {
        int size = this.components.size();
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!((Component) this.components.get(i)).buf.isDirect()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasArray() {
        switch (this.components.size()) {
            case 0:
                return true;
            case 1:
                return ((Component) this.components.get(0)).buf.hasArray();
            default:
                return false;
        }
    }

    public byte[] array() {
        switch (this.components.size()) {
            case 0:
                return EmptyArrays.EMPTY_BYTES;
            case 1:
                return ((Component) this.components.get(0)).buf.array();
            default:
                throw new UnsupportedOperationException();
        }
    }

    public int arrayOffset() {
        switch (this.components.size()) {
            case 0:
                return 0;
            case 1:
                return ((Component) this.components.get(0)).buf.arrayOffset();
            default:
                throw new UnsupportedOperationException();
        }
    }

    public boolean hasMemoryAddress() {
        switch (this.components.size()) {
            case 0:
                return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
            case 1:
                return ((Component) this.components.get(0)).buf.hasMemoryAddress();
            default:
                return false;
        }
    }

    public long memoryAddress() {
        switch (this.components.size()) {
            case 0:
                return Unpooled.EMPTY_BUFFER.memoryAddress();
            case 1:
                return ((Component) this.components.get(0)).buf.memoryAddress();
            default:
                throw new UnsupportedOperationException();
        }
    }

    public int capacity() {
        int numComponents = this.components.size();
        if (numComponents == 0) {
            return 0;
        }
        return ((Component) this.components.get(numComponents - 1)).endOffset;
    }

    public CompositeByteBuf capacity(int newCapacity) {
        checkNewCapacity(newCapacity);
        int oldCapacity = capacity();
        if (newCapacity > oldCapacity) {
            int paddingLength = newCapacity - oldCapacity;
            if (this.components.size() < this.maxNumComponents) {
                ByteBuf padding = allocBuffer(paddingLength);
                padding.setIndex(0, paddingLength);
                addComponent0(false, this.components.size(), padding);
            } else {
                ByteBuf padding2 = allocBuffer(paddingLength);
                padding2.setIndex(0, paddingLength);
                addComponent0(false, this.components.size(), padding2);
                consolidateIfNeeded();
            }
        } else if (newCapacity < oldCapacity) {
            int bytesToTrim = oldCapacity - newCapacity;
            ComponentList componentList = this.components;
            ListIterator<Component> i = componentList.listIterator(componentList.size());
            while (true) {
                if (i.hasPrevious()) {
                    Component c = i.previous();
                    int i2 = c.length;
                    if (bytesToTrim < i2) {
                        Component newC = new Component(c.buf.slice(0, i2 - bytesToTrim));
                        int i3 = c.offset;
                        newC.offset = i3;
                        newC.endOffset = i3 + newC.length;
                        i.set(newC);
                        break;
                    }
                    bytesToTrim -= i2;
                    i.remove();
                } else {
                    break;
                }
            }
            if (readerIndex() > newCapacity) {
                setIndex(newCapacity, newCapacity);
            } else if (writerIndex() > newCapacity) {
                writerIndex(newCapacity);
            }
        }
        return this;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public int numComponents() {
        return this.components.size();
    }

    public int maxNumComponents() {
        return this.maxNumComponents;
    }

    public int toComponentIndex(int offset) {
        checkIndex(offset);
        int low = 0;
        int high = this.components.size();
        while (low <= high) {
            int mid = (low + high) >>> 1;
            Component c = (Component) this.components.get(mid);
            if (offset >= c.endOffset) {
                low = mid + 1;
            } else if (offset >= c.offset) {
                return mid;
            } else {
                high = mid - 1;
            }
        }
        throw new Error("should not reach here");
    }

    public int toByteIndex(int cIndex) {
        checkComponentIndex(cIndex);
        return ((Component) this.components.get(cIndex)).offset;
    }

    public byte getByte(int index) {
        return _getByte(index);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int index) {
        Component c = findComponent(index);
        return c.buf.getByte(index - c.offset);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int index) {
        Component c = findComponent(index);
        if (index + 2 <= c.endOffset) {
            return c.buf.getShort(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(index) & 255) << 8) | (_getByte(index + 1) & 255));
        }
        return (short) ((_getByte(index) & 255) | ((_getByte(index + 1) & 255) << 8));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int index) {
        Component c = findComponent(index);
        if (index + 3 <= c.endOffset) {
            return c.buf.getUnsignedMedium(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShort(index) & 65535) << 8) | (_getByte(index + 2) & 255);
        }
        return (_getShort(index) & 65535) | ((_getByte(index + 2) & 255) << MappingData.PATH);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int index) {
        Component c = findComponent(index);
        if (index + 4 <= c.endOffset) {
            return c.buf.getInt(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShort(index) & 65535) << 16) | (_getShort(index + 2) & 65535);
        }
        return (_getShort(index) & 65535) | ((_getShort(index + 2) & 65535) << 16);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int index) {
        Component c = findComponent(index);
        if (index + 8 <= c.endOffset) {
            return c.buf.getLong(index - c.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((((long) _getInt(index)) & 4294967295L) << 32) | (((long) _getInt(index + 4)) & 4294967295L);
        }
        return (((long) _getInt(index)) & 4294967295L) | ((4294967295L & ((long) _getInt(index + 4))) << 32);
    }

    public CompositeByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.length);
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
        return this;
    }

    /* JADX INFO: finally extract failed */
    public CompositeByteBuf getBytes(int index, ByteBuffer dst) {
        int limit = dst.limit();
        int length = dst.remaining();
        checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            try {
                Component c = (Component) this.components.get(i);
                ByteBuf s = c.buf;
                int adjustment = c.offset;
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                dst.limit(dst.position() + localLength);
                s.getBytes(index - adjustment, dst);
                index += localLength;
                length -= localLength;
                i++;
            } catch (Throwable th) {
                dst.limit(limit);
                throw th;
            }
        }
        dst.limit(limit);
        return this;
    }

    public CompositeByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        checkDstIndex(index, length, dstIndex, dst.capacity());
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) {
        if (nioBufferCount() == 1) {
            return out.write(internalNioBuffer(index, length));
        }
        long writtenBytes = out.write(nioBuffers(index, length));
        if (writtenBytes > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) writtenBytes;
    }

    public CompositeByteBuf getBytes(int index, OutputStream out, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, out, localLength);
            index += localLength;
            length -= localLength;
            i++;
        }
        return this;
    }

    public CompositeByteBuf setByte(int index, int value) {
        Component c = findComponent(index);
        c.buf.setByte(index - c.offset, value);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int index, int value) {
        setByte(index, value);
    }

    public CompositeByteBuf setShort(int index, int value) {
        return (CompositeByteBuf) super.setShort(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int index, int value) {
        Component c = findComponent(index);
        if (index + 2 <= c.endOffset) {
            c.buf.setShort(index - c.offset, value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setByte(index, (byte) (value >>> 8));
            _setByte(index + 1, (byte) value);
        } else {
            _setByte(index, (byte) value);
            _setByte(index + 1, (byte) (value >>> 8));
        }
    }

    public CompositeByteBuf setMedium(int index, int value) {
        return (CompositeByteBuf) super.setMedium(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int index, int value) {
        Component c = findComponent(index);
        if (index + 3 <= c.endOffset) {
            c.buf.setMedium(index - c.offset, value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(index, (short) (value >> 8));
            _setByte(index + 2, (byte) value);
        } else {
            _setShort(index, (short) value);
            _setByte(index + 2, (byte) (value >>> 16));
        }
    }

    public CompositeByteBuf setInt(int index, int value) {
        return (CompositeByteBuf) super.setInt(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int index, int value) {
        Component c = findComponent(index);
        if (index + 4 <= c.endOffset) {
            c.buf.setInt(index - c.offset, value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(index, (short) (value >>> 16));
            _setShort(index + 2, (short) value);
        } else {
            _setShort(index, (short) value);
            _setShort(index + 2, (short) (value >>> 16));
        }
    }

    public CompositeByteBuf setLong(int index, long value) {
        return (CompositeByteBuf) super.setLong(index, value);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int index, long value) {
        Component c = findComponent(index);
        if (index + 8 <= c.endOffset) {
            c.buf.setLong(index - c.offset, value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setInt(index, (int) (value >>> 32));
            _setInt(index + 4, (int) value);
        } else {
            _setInt(index, (int) value);
            _setInt(index + 4, (int) (value >>> 32));
        }
    }

    public CompositeByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.length);
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
            length -= localLength;
            i++;
        }
        return this;
    }

    /* JADX INFO: finally extract failed */
    public CompositeByteBuf setBytes(int index, ByteBuffer src) {
        int limit = src.limit();
        int length = src.remaining();
        checkIndex(index, length);
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            try {
                Component c = (Component) this.components.get(i);
                ByteBuf s = c.buf;
                int adjustment = c.offset;
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                src.limit(src.position() + localLength);
                s.setBytes(index - adjustment, src);
                index += localLength;
                length -= localLength;
                i++;
            } catch (Throwable th) {
                src.limit(limit);
                throw th;
            }
        }
        src.limit(limit);
        return this;
    }

    public CompositeByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        checkSrcIndex(index, length, srcIndex, src.capacity());
        if (length == 0) {
            return this;
        }
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
            length -= localLength;
            i++;
        }
        return this;
    }

    public int setBytes(int index, InputStream in, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return in.read(EmptyArrays.EMPTY_BYTES);
        }
        int i = toComponentIndex(index);
        int readBytes = 0;
        while (true) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            int localReadBytes = s.setBytes(index - adjustment, in, localLength);
            if (localReadBytes >= 0) {
                if (localReadBytes == localLength) {
                    index += localLength;
                    length -= localLength;
                    readBytes += localLength;
                    i++;
                    continue;
                } else {
                    index += localReadBytes;
                    length -= localReadBytes;
                    readBytes += localReadBytes;
                    continue;
                }
                if (length <= 0) {
                    break;
                }
            } else if (readBytes == 0) {
                return -1;
            }
        }
        return readBytes;
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return in.read(EMPTY_NIO_BUFFER);
        }
        int i = toComponentIndex(index);
        int readBytes = 0;
        while (true) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            int localReadBytes = s.setBytes(index - adjustment, in, localLength);
            if (localReadBytes != 0) {
                if (localReadBytes >= 0) {
                    if (localReadBytes == localLength) {
                        index += localLength;
                        length -= localLength;
                        readBytes += localLength;
                        i++;
                        continue;
                    } else {
                        index += localReadBytes;
                        length -= localReadBytes;
                        readBytes += localReadBytes;
                        continue;
                    }
                    if (length <= 0) {
                        break;
                    }
                } else if (readBytes == 0) {
                    return -1;
                }
            } else {
                break;
            }
        }
        return readBytes;
    }

    public ByteBuf copy(int index, int length) {
        checkIndex(index, length);
        ByteBuf dst = allocBuffer(length);
        if (length != 0) {
            copyTo(index, length, toComponentIndex(index), dst);
        }
        return dst;
    }

    private void copyTo(int index, int length, int componentId, ByteBuf dst) {
        int dstIndex = 0;
        int i = componentId;
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
        dst.writerIndex(dst.capacity());
    }

    public ByteBuf component(int cIndex) {
        return internalComponent(cIndex).duplicate();
    }

    public ByteBuf componentAtOffset(int offset) {
        return internalComponentAtOffset(offset).duplicate();
    }

    public ByteBuf internalComponent(int cIndex) {
        checkComponentIndex(cIndex);
        return ((Component) this.components.get(cIndex)).buf;
    }

    public ByteBuf internalComponentAtOffset(int offset) {
        return findComponent(offset).buf;
    }

    private Component findComponent(int offset) {
        checkIndex(offset);
        int low = 0;
        int high = this.components.size();
        while (low <= high) {
            int mid = (low + high) >>> 1;
            Component c = (Component) this.components.get(mid);
            if (offset >= c.endOffset) {
                low = mid + 1;
            } else if (offset < c.offset) {
                high = mid - 1;
            } else if (c.length != 0) {
                return c;
            } else {
                throw new AssertionError();
            }
        }
        throw new Error("should not reach here");
    }

    public int nioBufferCount() {
        switch (this.components.size()) {
            case 0:
                return 1;
            case 1:
                return ((Component) this.components.get(0)).buf.nioBufferCount();
            default:
                int count = 0;
                int componentsCount = this.components.size();
                for (int i = 0; i < componentsCount; i++) {
                    count += ((Component) this.components.get(i)).buf.nioBufferCount();
                }
                return count;
        }
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        switch (this.components.size()) {
            case 0:
                return EMPTY_NIO_BUFFER;
            case 1:
                return ((Component) this.components.get(0)).buf.internalNioBuffer(index, length);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public ByteBuffer nioBuffer(int index, int length) {
        checkIndex(index, length);
        switch (this.components.size()) {
            case 0:
                return EMPTY_NIO_BUFFER;
            case 1:
                if (((Component) this.components.get(0)).buf.nioBufferCount() == 1) {
                    return ((Component) this.components.get(0)).buf.nioBuffer(index, length);
                }
                break;
        }
        ByteBuffer merged = ByteBuffer.allocate(length).order(order());
        for (ByteBuffer buf : nioBuffers(index, length)) {
            merged.put(buf);
        }
        merged.flip();
        return merged;
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        checkIndex(index, length);
        if (length == 0) {
            return new ByteBuffer[]{EMPTY_NIO_BUFFER};
        }
        List<ByteBuffer> buffers = new ArrayList<>(this.components.size());
        int i = toComponentIndex(index);
        while (length > 0) {
            Component c = (Component) this.components.get(i);
            ByteBuf s = c.buf;
            int adjustment = c.offset;
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            switch (s.nioBufferCount()) {
                case 0:
                    throw new UnsupportedOperationException();
                case 1:
                    buffers.add(s.nioBuffer(index - adjustment, localLength));
                    break;
                default:
                    Collections.addAll(buffers, s.nioBuffers(index - adjustment, localLength));
                    break;
            }
            index += localLength;
            length -= localLength;
            i++;
        }
        return (ByteBuffer[]) buffers.toArray(new ByteBuffer[buffers.size()]);
    }

    public CompositeByteBuf consolidate() {
        ensureAccessible();
        int numComponents = numComponents();
        if (numComponents <= 1) {
            return this;
        }
        ByteBuf consolidated = allocBuffer(((Component) this.components.get(numComponents - 1)).endOffset);
        for (int i = 0; i < numComponents; i++) {
            Component c = (Component) this.components.get(i);
            consolidated.writeBytes(c.buf);
            c.freeIfNecessary();
        }
        this.components.clear();
        this.components.add(new Component(consolidated));
        updateComponentOffsets(0);
        return this;
    }

    public CompositeByteBuf consolidate(int cIndex, int numComponents) {
        checkComponentIndex(cIndex, numComponents);
        if (numComponents <= 1) {
            return this;
        }
        int endCIndex = cIndex + numComponents;
        ByteBuf consolidated = allocBuffer(((Component) this.components.get(endCIndex - 1)).endOffset - ((Component) this.components.get(cIndex)).offset);
        for (int i = cIndex; i < endCIndex; i++) {
            Component c = (Component) this.components.get(i);
            consolidated.writeBytes(c.buf);
            c.freeIfNecessary();
        }
        this.components.removeRange(cIndex + 1, endCIndex);
        this.components.set(cIndex, new Component(consolidated));
        updateComponentOffsets(cIndex);
        return this;
    }

    public CompositeByteBuf discardReadComponents() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            int size = this.components.size();
            for (int i = 0; i < size; i++) {
                ((Component) this.components.get(i)).freeIfNecessary();
            }
            this.components.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int firstComponentId = toComponentIndex(readerIndex);
        for (int i2 = 0; i2 < firstComponentId; i2++) {
            ((Component) this.components.get(i2)).freeIfNecessary();
        }
        this.components.removeRange(0, firstComponentId);
        int offset = ((Component) this.components.get(0)).offset;
        updateComponentOffsets(0);
        setIndex(readerIndex - offset, writerIndex - offset);
        adjustMarkers(offset);
        return this;
    }

    public CompositeByteBuf discardReadBytes() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            int size = this.components.size();
            for (int i = 0; i < size; i++) {
                ((Component) this.components.get(i)).freeIfNecessary();
            }
            this.components.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int firstComponentId = toComponentIndex(readerIndex);
        for (int i2 = 0; i2 < firstComponentId; i2++) {
            ((Component) this.components.get(i2)).freeIfNecessary();
        }
        Component c = (Component) this.components.get(firstComponentId);
        int adjustment = readerIndex - c.offset;
        int i3 = c.length;
        if (adjustment == i3) {
            firstComponentId++;
        } else {
            this.components.set(firstComponentId, new Component(c.buf.slice(adjustment, i3 - adjustment)));
        }
        this.components.removeRange(0, firstComponentId);
        updateComponentOffsets(0);
        setIndex(0, writerIndex - readerIndex);
        adjustMarkers(readerIndex);
        return this;
    }

    private ByteBuf allocBuffer(int capacity) {
        return this.direct ? alloc().directBuffer(capacity) : alloc().heapBuffer(capacity);
    }

    public String toString() {
        String result = super.toString();
        String result2 = result.substring(0, result.length() - 1);
        return result2 + ", components=" + this.components.size() + ')';
    }

    public static final class Component {
        final ByteBuf buf;
        int endOffset;
        final int length;
        int offset;

        Component(ByteBuf buf2) {
            this.buf = buf2;
            this.length = buf2.readableBytes();
        }

        /* access modifiers changed from: package-private */
        public void freeIfNecessary() {
            this.buf.release();
        }
    }

    public CompositeByteBuf readerIndex(int readerIndex) {
        return (CompositeByteBuf) super.readerIndex(readerIndex);
    }

    public CompositeByteBuf writerIndex(int writerIndex) {
        return (CompositeByteBuf) super.writerIndex(writerIndex);
    }

    public CompositeByteBuf setIndex(int readerIndex, int writerIndex) {
        return (CompositeByteBuf) super.setIndex(readerIndex, writerIndex);
    }

    public CompositeByteBuf clear() {
        return (CompositeByteBuf) super.clear();
    }

    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf) super.markReaderIndex();
    }

    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf) super.resetReaderIndex();
    }

    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf) super.markWriterIndex();
    }

    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf) super.resetWriterIndex();
    }

    public CompositeByteBuf ensureWritable(int minWritableBytes) {
        return (CompositeByteBuf) super.ensureWritable(minWritableBytes);
    }

    public CompositeByteBuf getBytes(int index, ByteBuf dst) {
        return (CompositeByteBuf) super.getBytes(index, dst);
    }

    public CompositeByteBuf getBytes(int index, ByteBuf dst, int length) {
        return (CompositeByteBuf) super.getBytes(index, dst, length);
    }

    public CompositeByteBuf getBytes(int index, byte[] dst) {
        return (CompositeByteBuf) super.getBytes(index, dst);
    }

    public CompositeByteBuf setBoolean(int index, boolean value) {
        return (CompositeByteBuf) super.setBoolean(index, value);
    }

    public CompositeByteBuf setChar(int index, int value) {
        return (CompositeByteBuf) super.setChar(index, value);
    }

    public CompositeByteBuf setFloat(int index, float value) {
        return (CompositeByteBuf) super.setFloat(index, value);
    }

    public CompositeByteBuf setDouble(int index, double value) {
        return (CompositeByteBuf) super.setDouble(index, value);
    }

    public CompositeByteBuf setBytes(int index, ByteBuf src) {
        return (CompositeByteBuf) super.setBytes(index, src);
    }

    public CompositeByteBuf setBytes(int index, ByteBuf src, int length) {
        return (CompositeByteBuf) super.setBytes(index, src, length);
    }

    public CompositeByteBuf setBytes(int index, byte[] src) {
        return (CompositeByteBuf) super.setBytes(index, src);
    }

    public CompositeByteBuf setZero(int index, int length) {
        return (CompositeByteBuf) super.setZero(index, length);
    }

    public CompositeByteBuf readBytes(ByteBuf dst) {
        return (CompositeByteBuf) super.readBytes(dst);
    }

    public CompositeByteBuf readBytes(ByteBuf dst, int length) {
        return (CompositeByteBuf) super.readBytes(dst, length);
    }

    public CompositeByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
        return (CompositeByteBuf) super.readBytes(dst, dstIndex, length);
    }

    public CompositeByteBuf readBytes(byte[] dst) {
        return (CompositeByteBuf) super.readBytes(dst);
    }

    public CompositeByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        return (CompositeByteBuf) super.readBytes(dst, dstIndex, length);
    }

    public CompositeByteBuf readBytes(ByteBuffer dst) {
        return (CompositeByteBuf) super.readBytes(dst);
    }

    public CompositeByteBuf readBytes(OutputStream out, int length) {
        return (CompositeByteBuf) super.readBytes(out, length);
    }

    public CompositeByteBuf skipBytes(int length) {
        return (CompositeByteBuf) super.skipBytes(length);
    }

    public CompositeByteBuf writeBoolean(boolean value) {
        return (CompositeByteBuf) super.writeBoolean(value);
    }

    public CompositeByteBuf writeByte(int value) {
        return (CompositeByteBuf) super.writeByte(value);
    }

    public CompositeByteBuf writeShort(int value) {
        return (CompositeByteBuf) super.writeShort(value);
    }

    public CompositeByteBuf writeMedium(int value) {
        return (CompositeByteBuf) super.writeMedium(value);
    }

    public CompositeByteBuf writeInt(int value) {
        return (CompositeByteBuf) super.writeInt(value);
    }

    public CompositeByteBuf writeLong(long value) {
        return (CompositeByteBuf) super.writeLong(value);
    }

    public CompositeByteBuf writeChar(int value) {
        return (CompositeByteBuf) super.writeChar(value);
    }

    public CompositeByteBuf writeFloat(float value) {
        return (CompositeByteBuf) super.writeFloat(value);
    }

    public CompositeByteBuf writeDouble(double value) {
        return (CompositeByteBuf) super.writeDouble(value);
    }

    public CompositeByteBuf writeBytes(ByteBuf src) {
        return (CompositeByteBuf) super.writeBytes(src);
    }

    public CompositeByteBuf writeBytes(ByteBuf src, int length) {
        return (CompositeByteBuf) super.writeBytes(src, length);
    }

    public CompositeByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
        return (CompositeByteBuf) super.writeBytes(src, srcIndex, length);
    }

    public CompositeByteBuf writeBytes(byte[] src) {
        return (CompositeByteBuf) super.writeBytes(src);
    }

    public CompositeByteBuf writeBytes(byte[] src, int srcIndex, int length) {
        return (CompositeByteBuf) super.writeBytes(src, srcIndex, length);
    }

    public CompositeByteBuf writeBytes(ByteBuffer src) {
        return (CompositeByteBuf) super.writeBytes(src);
    }

    public CompositeByteBuf writeZero(int length) {
        return (CompositeByteBuf) super.writeZero(length);
    }

    public CompositeByteBuf retain(int increment) {
        return (CompositeByteBuf) super.retain(increment);
    }

    public CompositeByteBuf retain() {
        return (CompositeByteBuf) super.retain();
    }

    public ByteBuffer[] nioBuffers() {
        return nioBuffers(readerIndex(), readableBytes());
    }

    public CompositeByteBuf discardSomeReadBytes() {
        return discardReadComponents();
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        if (!this.freed) {
            this.freed = true;
            int size = this.components.size();
            for (int i = 0; i < size; i++) {
                ((Component) this.components.get(i)).freeIfNecessary();
            }
        }
    }

    public ByteBuf unwrap() {
        return null;
    }

    public final class CompositeByteBufIterator implements Iterator<ByteBuf> {
        private int index;
        private final int size;

        private CompositeByteBufIterator() {
            this.size = CompositeByteBuf.this.components.size();
        }

        public boolean hasNext() {
            return this.size > this.index;
        }

        public ByteBuf next() {
            if (this.size != CompositeByteBuf.this.components.size()) {
                throw new ConcurrentModificationException();
            } else if (hasNext()) {
                try {
                    ComponentList access$100 = CompositeByteBuf.this.components;
                    int i = this.index;
                    this.index = i + 1;
                    return ((Component) access$100.get(i)).buf;
                } catch (IndexOutOfBoundsException e) {
                    throw new ConcurrentModificationException();
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-Only");
        }
    }

    public static final class ComponentList extends ArrayList<Component> {
        ComponentList(int initialCapacity) {
            super(initialCapacity);
        }

        public void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
        }
    }
}
