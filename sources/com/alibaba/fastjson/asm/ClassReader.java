package com.alibaba.fastjson.asm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.glassfish.grizzly.http.server.util.MappingData;

public class ClassReader {
    public final byte[] b;
    public final int header;
    private final int[] items;
    private final int maxStringLength;
    private boolean readAnnotations;
    private final String[] strings;

    public ClassReader(InputStream is, boolean readAnnotations2) {
        int size;
        this.readAnnotations = readAnnotations2;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            int len = is.read(buf);
            if (len == -1) {
                break;
            } else if (len > 0) {
                out.write(buf, 0, len);
            }
        }
        is.close();
        this.b = out.toByteArray();
        int[] iArr = new int[readUnsignedShort(8)];
        this.items = iArr;
        int n = iArr.length;
        this.strings = new String[n];
        int max = 0;
        int index = 10;
        int i = 1;
        while (i < n) {
            this.items[i] = index + 1;
            switch (this.b[index]) {
                case 1:
                    size = readUnsignedShort(index + 1) + 3;
                    if (size <= max) {
                        break;
                    } else {
                        max = size;
                        break;
                    }
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                case 18:
                    size = 5;
                    break;
                case 5:
                case 6:
                    size = 9;
                    i++;
                    break;
                case 15:
                    size = 4;
                    break;
                default:
                    size = 3;
                    break;
            }
            index += size;
            i++;
        }
        this.maxStringLength = max;
        this.header = index;
    }

    public void accept(TypeCollector classVisitor) {
        char[] c = new char[this.maxStringLength];
        int anns = 0;
        if (this.readAnnotations) {
            int u = getAttributes();
            int i = readUnsignedShort(u);
            while (true) {
                if (i <= 0) {
                    break;
                } else if ("RuntimeVisibleAnnotations".equals(readUTF8(u + 2, c))) {
                    anns = u + 8;
                    break;
                } else {
                    u += readInt(u + 4) + 6;
                    i--;
                }
            }
        }
        int u2 = this.header;
        int len = readUnsignedShort(u2 + 6);
        int u3 = u2 + 8;
        for (int i2 = 0; i2 < len; i2++) {
            u3 += 2;
        }
        int v = u3;
        int v2 = v + 2;
        for (int i3 = readUnsignedShort(v); i3 > 0; i3--) {
            v2 += 8;
            for (int j = readUnsignedShort(v2 + 6); j > 0; j--) {
                v2 += readInt(v2 + 2) + 6;
            }
        }
        int v3 = v2 + 2;
        for (int i4 = readUnsignedShort(v2); i4 > 0; i4--) {
            v3 += 8;
            for (int j2 = readUnsignedShort(v3 + 6); j2 > 0; j2--) {
                v3 += readInt(v3 + 2) + 6;
            }
        }
        int v4 = v3 + 2;
        for (int i5 = readUnsignedShort(v3); i5 > 0; i5--) {
            v4 += readInt(v4 + 2) + 6;
        }
        if (anns != 0) {
            int v5 = anns + 2;
            for (int i6 = readUnsignedShort(anns); i6 > 0; i6--) {
                classVisitor.visitAnnotation(readUTF8(v5, c));
            }
        }
        int u4 = u3 + 2;
        for (int i7 = readUnsignedShort(u3); i7 > 0; i7--) {
            u4 += 8;
            for (int j3 = readUnsignedShort(u4 + 6); j3 > 0; j3--) {
                u4 += readInt(u4 + 2) + 6;
            }
        }
        int u5 = u4 + 2;
        for (int i8 = readUnsignedShort(u4); i8 > 0; i8--) {
            u5 = readMethod(classVisitor, c, u5);
        }
    }

    private int getAttributes() {
        int i = this.header;
        int u = i + 8 + (readUnsignedShort(i + 6) * 2);
        for (int i2 = readUnsignedShort(u); i2 > 0; i2--) {
            for (int j = readUnsignedShort(u + 8); j > 0; j--) {
                u += readInt(u + 12) + 6;
            }
            u += 8;
        }
        int u2 = u + 2;
        for (int i3 = readUnsignedShort(u2); i3 > 0; i3--) {
            for (int j2 = readUnsignedShort(u2 + 8); j2 > 0; j2--) {
                u2 += readInt(u2 + 12) + 6;
            }
            u2 += 8;
        }
        return u2 + 2;
    }

    private int readMethod(TypeCollector classVisitor, char[] c, int u) {
        char[] cArr = c;
        int i = u;
        int access = readUnsignedShort(i);
        String name = readUTF8(i + 2, cArr);
        String desc = readUTF8(i + 4, cArr);
        int v = 0;
        int u2 = i + 8;
        for (int j = readUnsignedShort(i + 6); j > 0; j--) {
            String attrName = readUTF8(u2, cArr);
            int attrSize = readInt(u2 + 2);
            int u3 = u2 + 6;
            if (attrName.equals("Code")) {
                v = u3;
            }
            u2 = u3 + attrSize;
        }
        if (0 != 0) {
            int w = 0 + 2;
            for (int j2 = 0; j2 < readUnsignedShort(w); j2++) {
                w += 2;
            }
        }
        MethodCollector mv = classVisitor.visitMethod(access, name, desc);
        if (mv == null || v == 0) {
            String str = name;
            String str2 = desc;
        } else {
            int v2 = v + 8;
            int codeStart = v2;
            int v3 = v2 + readInt(v + 4);
            int v4 = v3 + 2;
            for (int j3 = readUnsignedShort(v3); j3 > 0; j3--) {
                v4 += 8;
            }
            int varTable = 0;
            int varTypeTable = 0;
            int j4 = readUnsignedShort(v4);
            int v5 = v4 + 2;
            while (j4 > 0) {
                int access2 = access;
                String attrName2 = readUTF8(v5, cArr);
                String name2 = name;
                if (attrName2.equals("LocalVariableTable")) {
                    varTable = v5 + 6;
                } else if (attrName2.equals("LocalVariableTypeTable")) {
                    varTypeTable = v5 + 6;
                }
                v5 += readInt(v5 + 2) + 6;
                j4--;
                access = access2;
                name = name2;
            }
            String str3 = name;
            int v6 = codeStart;
            if (varTable != 0) {
                if (varTypeTable != 0) {
                    int k = readUnsignedShort(varTypeTable) * 3;
                    int w2 = varTypeTable + 2;
                    int[] typeTable = new int[k];
                    while (k > 0) {
                        int k2 = k - 1;
                        typeTable[k2] = w2 + 6;
                        int k3 = k2 - 1;
                        typeTable[k3] = readUnsignedShort(w2 + 8);
                        k = k3 - 1;
                        typeTable[k] = readUnsignedShort(w2);
                        w2 += 10;
                        desc = desc;
                    }
                    int i2 = w2;
                }
                int w3 = varTable + 2;
                for (int k4 = readUnsignedShort(varTable); k4 > 0; k4--) {
                    mv.visitLocalVariable(readUTF8(w3 + 4, cArr), readUnsignedShort(w3 + 8));
                    w3 += 10;
                }
            }
        }
        return u2;
    }

    private int readUnsignedShort(int index) {
        byte[] b2 = this.b;
        return ((b2[index] & 255) << 8) | (b2[index + 1] & 255);
    }

    private int readInt(int index) {
        byte[] b2 = this.b;
        return ((b2[index] & 255) << 24) | ((b2[index + 1] & 255) << MappingData.PATH) | ((b2[index + 2] & 255) << 8) | (b2[index + 3] & 255);
    }

    private String readUTF8(int index, char[] buf) {
        int item = readUnsignedShort(index);
        String[] strArr = this.strings;
        String s = strArr[item];
        if (s != null) {
            return s;
        }
        int index2 = this.items[item];
        String readUTF = readUTF(index2 + 2, readUnsignedShort(index2), buf);
        strArr[item] = readUTF;
        return readUTF;
    }

    private String readUTF(int c, int utfLen, char[] buf) {
        int endIndex = c + utfLen;
        byte[] b2 = this.b;
        int st = 0;
        int st2 = 0;
        char cc = 0;
        while (c < endIndex) {
            int index = c + 1;
            byte c2 = b2[c];
            switch (st2) {
                case 0:
                    int c3 = c2 & 255;
                    if (c3 >= 128) {
                        if (c3 < 224 && c3 > 191) {
                            cc = (char) (c3 & 31);
                            st2 = 1;
                            break;
                        } else {
                            cc = (char) (c3 & 15);
                            st2 = 2;
                            break;
                        }
                    } else {
                        buf[st] = (char) c3;
                        st++;
                        break;
                    }
                    break;
                case 1:
                    buf[st] = (char) ((cc << 6) | (c2 & 63));
                    st2 = 0;
                    st++;
                    break;
                case 2:
                    cc = (char) ((cc << 6) | (c2 & 63));
                    st2 = 1;
                    break;
            }
            c = index;
        }
        return new String(buf, 0, st);
    }
}
