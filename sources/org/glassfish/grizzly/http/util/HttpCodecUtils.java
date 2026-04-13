package org.glassfish.grizzly.http.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.memory.MemoryManager;

public class HttpCodecUtils {
    private static final int[] DEC = HexUtils.getDecBytes();
    static final byte[] EMPTY_ARRAY = new byte[0];

    public static void parseHost(DataChunk hostDC, DataChunk serverNameDC, HttpRequestPacket request) {
        DataChunk dataChunk = serverNameDC;
        HttpRequestPacket httpRequestPacket = request;
        if (hostDC == null) {
            Connection connection = request.getConnection();
            httpRequestPacket.setServerPort(((InetSocketAddress) connection.getLocalAddress()).getPort());
            InetAddress localAddress = ((InetSocketAddress) connection.getLocalAddress()).getAddress();
            httpRequestPacket.setLocalHost(localAddress.getHostName());
            dataChunk.setString(localAddress.getHostName());
            return;
        }
        DataChunk.Type type = hostDC.getType();
        DataChunk.Type type2 = DataChunk.Type.Bytes;
        if (type == type2 || hostDC.getType() == DataChunk.Type.String) {
            int valueS = hostDC.getStart();
            int valueL = hostDC.getEnd() - valueS;
            int colonPos = -1;
            byte[] valueB = hostDC.getType() == type2 ? hostDC.getByteChunk().getBuffer() : hostDC.toString().getBytes();
            boolean ipv6 = valueB[valueS] == 91;
            boolean bracketClosed = false;
            int i = 0;
            while (true) {
                if (i >= valueL) {
                    break;
                }
                byte b = valueB[i + valueS];
                if (b == 93) {
                    bracketClosed = true;
                } else if (b == 58 && (!ipv6 || bracketClosed)) {
                    colonPos = i;
                }
                i++;
            }
            colonPos = i;
            if (colonPos < 0) {
                if (!request.isSecure()) {
                    httpRequestPacket.setServerPort(80);
                } else {
                    httpRequestPacket.setServerPort(443);
                }
                dataChunk.setBytes(valueB, valueS, valueS + valueL);
                return;
            }
            dataChunk.setBytes(valueB, valueS, valueS + colonPos);
            int port = 0;
            int mult = 1;
            int i2 = valueL - 1;
            while (i2 > colonPos) {
                int charValue = DEC[valueB[i2 + valueS]];
                if (charValue != -1) {
                    port += charValue * mult;
                    mult *= 10;
                    i2--;
                } else {
                    throw new IllegalStateException(String.format("Host header %s contained a non-decimal value in the port definition.", new Object[]{hostDC.toString()}));
                }
            }
            httpRequestPacket.setServerPort(port);
            return;
        }
        BufferChunk valueBC = hostDC.getBufferChunk();
        int valueS2 = valueBC.getStart();
        int valueL2 = valueBC.getEnd() - valueS2;
        int colonPos2 = -1;
        Buffer valueB2 = valueBC.getBuffer();
        boolean ipv62 = valueB2.get(valueS2) == 91;
        boolean bracketClosed2 = false;
        int i3 = 0;
        while (true) {
            if (i3 >= valueL2) {
                break;
            }
            byte b2 = valueB2.get(i3 + valueS2);
            if (b2 == 93) {
                bracketClosed2 = true;
            } else if (b2 == 58 && (!ipv62 || bracketClosed2)) {
                colonPos2 = i3;
            }
            i3++;
        }
        colonPos2 = i3;
        if (colonPos2 < 0) {
            if (!request.isSecure()) {
                httpRequestPacket.setServerPort(80);
            } else {
                httpRequestPacket.setServerPort(443);
            }
            dataChunk.setBuffer(valueB2, valueS2, valueS2 + valueL2);
            return;
        }
        dataChunk.setBuffer(valueB2, valueS2, valueS2 + colonPos2);
        int port2 = 0;
        int mult2 = 1;
        int i4 = valueL2 - 1;
        while (i4 > colonPos2) {
            int charValue2 = DEC[valueB2.get(i4 + valueS2)];
            if (charValue2 != -1) {
                port2 += charValue2 * mult2;
                mult2 *= 10;
                i4--;
            } else {
                throw new IllegalStateException(String.format("Host header %s contained a non-decimal value in the port definition.", new Object[]{hostDC.toString()}));
            }
        }
        httpRequestPacket.setServerPort(port2);
    }

    public static int checkEOL(HttpCodecFilter.HeaderParsingState parsingState, Buffer input) {
        byte b1;
        byte b2;
        int offset = parsingState.offset;
        int avail = input.limit() - offset;
        if (avail >= 2) {
            short s = input.getShort(offset);
            b1 = (byte) (s >>> 8);
            b2 = (byte) (s & 255);
        } else if (avail != 1) {
            return -2;
        } else {
            b1 = input.get(offset);
            b2 = -1;
        }
        return checkCRLF(parsingState, b1, b2);
    }

    public static int checkEOL(HttpCodecFilter.HeaderParsingState parsingState, byte[] input, int end) {
        byte b2;
        byte b1;
        int arrayOffs = parsingState.arrayOffset;
        int offset = parsingState.offset + arrayOffs;
        int avail = Math.min(parsingState.packetLimit + arrayOffs, end) - offset;
        if (avail >= 2) {
            b1 = input[offset];
            b2 = input[offset + 1];
        } else if (avail != 1) {
            return -2;
        } else {
            b1 = input[offset];
            b2 = -1;
        }
        return checkCRLF(parsingState, b1, b2);
    }

    public static boolean findEOL(HttpCodecFilter.HeaderParsingState state, Buffer input) {
        int offset = state.offset;
        int limit = Math.min(input.limit(), state.packetLimit);
        while (offset < limit) {
            byte b = input.get(offset);
            if (b == 13) {
                state.checkpoint = offset;
            } else if (b == 10) {
                if (state.checkpoint == -1) {
                    state.checkpoint = offset;
                }
                state.offset = offset + 1;
                return true;
            }
            offset++;
        }
        state.offset = offset;
        return false;
    }

    public static boolean findEOL(HttpCodecFilter.HeaderParsingState state, byte[] input, int end) {
        int arrayOffs = state.arrayOffset;
        int offset = state.offset + arrayOffs;
        int limit = Math.min(end, state.packetLimit + arrayOffs);
        while (offset < limit) {
            byte b = input[offset];
            if (b == 13) {
                state.checkpoint = offset - arrayOffs;
            } else if (b == 10) {
                if (state.checkpoint == -1) {
                    state.checkpoint = offset - arrayOffs;
                }
                state.offset = (offset + 1) - arrayOffs;
                return true;
            }
            offset++;
        }
        state.offset = offset - arrayOffs;
        return false;
    }

    public static int findSpace(Buffer input, int offset, int packetLimit) {
        int limit = Math.min(input.limit(), packetLimit);
        while (offset < limit) {
            if (isSpaceOrTab(input.get(offset))) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public static int findSpace(byte[] input, int offset, int end, int packetLimit) {
        int limit = Math.min(end, packetLimit);
        while (offset < limit) {
            if (isSpaceOrTab(input[offset])) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public static int skipSpaces(Buffer input, int offset, int packetLimit) {
        int limit = Math.min(input.limit(), packetLimit);
        while (offset < limit) {
            if (isNotSpaceAndTab(input.get(offset))) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public static int skipSpaces(byte[] input, int offset, int end, int packetLimit) {
        int limit = Math.min(end, packetLimit);
        while (offset < limit) {
            if (isNotSpaceAndTab(input[offset])) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public static int indexOf(Buffer input, int offset, byte b, int packetLimit) {
        int limit = Math.min(input.limit(), packetLimit);
        while (offset < limit) {
            if (input.get(offset) == b) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    public static Buffer getLongAsBuffer(MemoryManager memoryManager, long length) {
        Buffer b = memoryManager.allocate(20);
        b.allowBufferDispose(true);
        HttpUtils.longToBuffer(length, b);
        return b;
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] tempBuffer, DataChunk chunk) {
        if (chunk.isNull()) {
            return dstBuffer;
        }
        if (chunk.getType() == DataChunk.Type.Bytes) {
            ByteChunk byteChunk = chunk.getByteChunk();
            return put(memoryManager, dstBuffer, byteChunk.getBuffer(), byteChunk.getStart(), byteChunk.getLength());
        } else if (chunk.getType() != DataChunk.Type.Buffer) {
            return put(memoryManager, dstBuffer, tempBuffer, chunk.toString());
        } else {
            BufferChunk bc = chunk.getBufferChunk();
            int length = bc.getLength();
            Buffer dstBuffer2 = checkAndResizeIfNeeded(memoryManager, dstBuffer, length);
            dstBuffer2.put(bc.getBuffer(), bc.getStart(), length);
            return dstBuffer2;
        }
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] tempBuffer, String s) {
        int size = s.length();
        Buffer dstBuffer2 = checkAndResizeIfNeeded(memoryManager, dstBuffer, size);
        if (dstBuffer2.hasArray()) {
            byte[] array = dstBuffer2.array();
            int arrayOffs = dstBuffer2.arrayOffset();
            int pos = dstBuffer2.position() + arrayOffs;
            int i = 0;
            while (i < size) {
                byte b = (byte) s.charAt(i);
                int pos2 = pos + 1;
                array[pos] = isNonPrintableUsAscii(b) ? 32 : b;
                i++;
                pos = pos2;
            }
            dstBuffer2.position(pos - arrayOffs);
        } else {
            fastAsciiEncode(s, tempBuffer, dstBuffer2);
        }
        return dstBuffer2;
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] array) {
        return put(memoryManager, dstBuffer, array, 0, array.length);
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte[] array, int off, int len) {
        Buffer dstBuffer2 = checkAndResizeIfNeeded(memoryManager, dstBuffer, len);
        dstBuffer2.put(array, off, len);
        return dstBuffer2;
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, Buffer buffer) {
        Buffer dstBuffer2 = checkAndResizeIfNeeded(memoryManager, dstBuffer, buffer.remaining());
        dstBuffer2.put(buffer);
        return dstBuffer2;
    }

    public static Buffer put(MemoryManager memoryManager, Buffer dstBuffer, byte value) {
        if (!dstBuffer.hasRemaining()) {
            dstBuffer = resizeBuffer(memoryManager, dstBuffer, 1);
        }
        dstBuffer.put(value);
        return dstBuffer;
    }

    public static Buffer resizeBuffer(MemoryManager memoryManager, Buffer buffer, int grow) {
        return memoryManager.reallocate(buffer, Math.max(buffer.capacity() + grow, ((buffer.capacity() * 3) / 2) + 1));
    }

    public static boolean isNotSpaceAndTab(byte b) {
        return (b == 32 || b == 9) ? false : true;
    }

    public static boolean isSpaceOrTab(byte b) {
        return b == 32 || b == 9;
    }

    public static byte[] toCheckedByteArray(CharSequence s) {
        return toCheckedByteArray(s, new byte[s.length()], 0);
    }

    public static byte[] toCheckedByteArray(CharSequence s, byte[] dstArray, int arrayOffs) {
        if (dstArray != null) {
            int strLen = s.length();
            if (arrayOffs + strLen <= dstArray.length) {
                for (int i = 0; i < strLen; i++) {
                    int c = s.charAt(i);
                    dstArray[i + arrayOffs] = isNonPrintableUsAscii(c) ? 32 : (byte) c;
                }
                return dstArray;
            }
            throw new IllegalArgumentException("Not enough space in the array");
        }
        throw new NullPointerException();
    }

    public static boolean isNonPrintableUsAscii(int ub) {
        return (ub <= 31 && ub != 9) || ub >= 127;
    }

    private static void fastAsciiEncode(String s, byte[] tempBuffer, Buffer dstBuffer) {
        int totalLen = s.length();
        if (tempBuffer == null) {
            tempBuffer = new byte[totalLen];
        }
        int count = 0;
        while (count < totalLen) {
            int len = Math.min(totalLen - count, tempBuffer.length);
            for (int i = 0; i < len; i++) {
                int c = s.charAt(count);
                tempBuffer[i] = isNonPrintableUsAscii(c) ? 32 : (byte) c;
                count++;
            }
            dstBuffer.put(tempBuffer, 0, len);
        }
    }

    private static int checkCRLF(HttpCodecFilter.HeaderParsingState parsingState, byte b1, byte b2) {
        if (b1 == 13) {
            if (b2 == 10) {
                parsingState.offset += 2;
                return 0;
            } else if (b2 == -1) {
                return -2;
            }
        } else if (b1 == 10) {
            parsingState.offset++;
            return 0;
        }
        return -1;
    }

    private static Buffer checkAndResizeIfNeeded(MemoryManager memoryManager, Buffer dstBuffer, int length) {
        if (dstBuffer.remaining() < length) {
            return resizeBuffer(memoryManager, dstBuffer, length);
        }
        return dstBuffer;
    }
}
