package com.google.android.gms.internal.wearable;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class zzl {
    private final ByteBuffer zzhb;

    private zzl(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private zzl(ByteBuffer byteBuffer) {
        this.zzhb = byteBuffer;
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static zzl zzb(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public static zzl zzb(byte[] bArr, int i, int i2) {
        return new zzl(bArr, 0, i2);
    }

    public final void zza(int i, float f) {
        zzf(i, 5);
        int floatToIntBits = Float.floatToIntBits(f);
        if (this.zzhb.remaining() >= 4) {
            this.zzhb.putInt(floatToIntBits);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public final void zza(int i, long j) {
        zzf(i, 0);
        zza(j);
    }

    public final void zzd(int i, int i2) {
        zzf(i, 0);
        if (i2 >= 0) {
            zzl(i2);
        } else {
            zza((long) i2);
        }
    }

    public final void zza(int i, String str) {
        zzf(i, 2);
        try {
            int zzm = zzm(str.length());
            if (zzm == zzm(str.length() * 3)) {
                int position = this.zzhb.position();
                if (this.zzhb.remaining() >= zzm) {
                    this.zzhb.position(position + zzm);
                    zza((CharSequence) str, this.zzhb);
                    int position2 = this.zzhb.position();
                    this.zzhb.position(position);
                    zzl((position2 - position) - zzm);
                    this.zzhb.position(position2);
                    return;
                }
                throw new zzm(position + zzm, this.zzhb.limit());
            }
            zzl(zza((CharSequence) str));
            zza((CharSequence) str, this.zzhb);
        } catch (BufferOverflowException e) {
            zzm zzm2 = new zzm(this.zzhb.position(), this.zzhb.limit());
            zzm2.initCause(e);
            throw zzm2;
        }
    }

    public final void zza(int i, zzt zzt) {
        zzf(i, 2);
        if (zzt.zzhl < 0) {
            zzt.zzx();
        }
        zzl(zzt.zzhl);
        zzt.zza(this);
    }

    private static int zza(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && charSequence.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = charSequence.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(charSequence, i2) >= 65536) {
                                i2++;
                            } else {
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2);
                                throw new IllegalArgumentException(sb.toString());
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        long j = ((long) i3) + IjkMediaMeta.AV_CH_WIDE_RIGHT;
        StringBuilder sb2 = new StringBuilder(54);
        sb2.append("UTF-8 length does not fit in int: ");
        sb2.append(j);
        throw new IllegalArgumentException(sb2.toString());
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        char charAt;
        if (!byteBuffer.isReadOnly()) {
            int i2 = 0;
            if (byteBuffer.hasArray()) {
                try {
                    byte[] array = byteBuffer.array();
                    int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
                    int remaining = byteBuffer.remaining();
                    int length = charSequence.length();
                    int i3 = remaining + arrayOffset;
                    while (i2 < length) {
                        int i4 = i2 + arrayOffset;
                        if (i4 >= i3 || (charAt = charSequence.charAt(i2)) >= 128) {
                            break;
                        }
                        array[i4] = (byte) charAt;
                        i2++;
                    }
                    if (i2 == length) {
                        i = arrayOffset + length;
                    } else {
                        i = arrayOffset + i2;
                        while (i2 < length) {
                            char charAt2 = charSequence.charAt(i2);
                            if (charAt2 < 128 && i < i3) {
                                array[i] = (byte) charAt2;
                                i++;
                            } else if (charAt2 < 2048 && i <= i3 - 2) {
                                int i5 = i + 1;
                                array[i] = (byte) ((charAt2 >>> 6) | 960);
                                i = i5 + 1;
                                array[i5] = (byte) ((charAt2 & '?') | 128);
                            } else if ((charAt2 < 55296 || 57343 < charAt2) && i <= i3 - 3) {
                                int i6 = i + 1;
                                array[i] = (byte) ((charAt2 >>> 12) | 480);
                                int i7 = i6 + 1;
                                array[i6] = (byte) (((charAt2 >>> 6) & 63) | 128);
                                array[i7] = (byte) ((charAt2 & '?') | 128);
                                i = i7 + 1;
                            } else if (i <= i3 - 4) {
                                int i8 = i2 + 1;
                                if (i8 != charSequence.length()) {
                                    char charAt3 = charSequence.charAt(i8);
                                    if (Character.isSurrogatePair(charAt2, charAt3)) {
                                        int codePoint = Character.toCodePoint(charAt2, charAt3);
                                        int i9 = i + 1;
                                        array[i] = (byte) ((codePoint >>> 18) | 240);
                                        int i10 = i9 + 1;
                                        array[i9] = (byte) (((codePoint >>> 12) & 63) | 128);
                                        int i11 = i10 + 1;
                                        array[i10] = (byte) (((codePoint >>> 6) & 63) | 128);
                                        i = i11 + 1;
                                        array[i11] = (byte) ((codePoint & 63) | 128);
                                        i2 = i8;
                                    } else {
                                        i2 = i8;
                                    }
                                }
                                StringBuilder sb = new StringBuilder(39);
                                sb.append("Unpaired surrogate at index ");
                                sb.append(i2 - 1);
                                throw new IllegalArgumentException(sb.toString());
                            } else {
                                StringBuilder sb2 = new StringBuilder(37);
                                sb2.append("Failed writing ");
                                sb2.append(charAt2);
                                sb2.append(" at index ");
                                sb2.append(i);
                                throw new ArrayIndexOutOfBoundsException(sb2.toString());
                            }
                            i2++;
                        }
                    }
                    byteBuffer.position(i - byteBuffer.arrayOffset());
                } catch (ArrayIndexOutOfBoundsException e) {
                    BufferOverflowException bufferOverflowException = new BufferOverflowException();
                    bufferOverflowException.initCause(e);
                    throw bufferOverflowException;
                }
            } else {
                int length2 = charSequence.length();
                while (i2 < length2) {
                    char charAt4 = charSequence.charAt(i2);
                    if (charAt4 < 128) {
                        byteBuffer.put((byte) charAt4);
                    } else if (charAt4 < 2048) {
                        byteBuffer.put((byte) ((charAt4 >>> 6) | 960));
                        byteBuffer.put((byte) ((charAt4 & '?') | 128));
                    } else if (charAt4 < 55296 || 57343 < charAt4) {
                        byteBuffer.put((byte) ((charAt4 >>> 12) | 480));
                        byteBuffer.put((byte) (((charAt4 >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((charAt4 & '?') | 128));
                    } else {
                        int i12 = i2 + 1;
                        if (i12 != charSequence.length()) {
                            char charAt5 = charSequence.charAt(i12);
                            if (Character.isSurrogatePair(charAt4, charAt5)) {
                                int codePoint2 = Character.toCodePoint(charAt4, charAt5);
                                byteBuffer.put((byte) ((codePoint2 >>> 18) | 240));
                                byteBuffer.put((byte) (((codePoint2 >>> 12) & 63) | 128));
                                byteBuffer.put((byte) (((codePoint2 >>> 6) & 63) | 128));
                                byteBuffer.put((byte) ((codePoint2 & 63) | 128));
                                i2 = i12;
                            } else {
                                i2 = i12;
                            }
                        }
                        StringBuilder sb3 = new StringBuilder(39);
                        sb3.append("Unpaired surrogate at index ");
                        sb3.append(i2 - 1);
                        throw new IllegalArgumentException(sb3.toString());
                    }
                    i2++;
                }
            }
        } else {
            throw new ReadOnlyBufferException();
        }
    }

    public static int zzb(int i, long j) {
        int i2;
        int zzk = zzk(i);
        if ((-128 & j) == 0) {
            i2 = 1;
        } else if ((-16384 & j) == 0) {
            i2 = 2;
        } else if ((-2097152 & j) == 0) {
            i2 = 3;
        } else if ((-268435456 & j) == 0) {
            i2 = 4;
        } else if ((-34359738368L & j) == 0) {
            i2 = 5;
        } else if ((-4398046511104L & j) == 0) {
            i2 = 6;
        } else if ((-562949953421312L & j) == 0) {
            i2 = 7;
        } else if ((-72057594037927936L & j) == 0) {
            i2 = 8;
        } else if ((j & Long.MIN_VALUE) == 0) {
            i2 = 9;
        } else {
            i2 = 10;
        }
        return zzk + i2;
    }

    public static int zze(int i, int i2) {
        return zzk(i) + zzi(i2);
    }

    public static int zzb(int i, String str) {
        return zzk(i) + zzg(str);
    }

    public static int zzb(int i, zzt zzt) {
        int zzk = zzk(i);
        int zzx = zzt.zzx();
        return zzk + zzm(zzx) + zzx;
    }

    public static int zzi(int i) {
        if (i >= 0) {
            return zzm(i);
        }
        return 10;
    }

    public static int zzg(String str) {
        int zza = zza((CharSequence) str);
        return zzm(zza) + zza;
    }

    public final void zzr() {
        if (this.zzhb.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", new Object[]{Integer.valueOf(this.zzhb.remaining())}));
        }
    }

    public final void zza(byte b) {
        if (this.zzhb.hasRemaining()) {
            this.zzhb.put(b);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    private final void zzj(int i) {
        byte b = (byte) i;
        if (this.zzhb.hasRemaining()) {
            this.zzhb.put(b);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public final void zzc(byte[] bArr) {
        int length = bArr.length;
        if (this.zzhb.remaining() >= length) {
            this.zzhb.put(bArr, 0, length);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public final void zzf(int i, int i2) {
        zzl((i << 3) | i2);
    }

    public static int zzk(int i) {
        return zzm(i << 3);
    }

    public final void zzl(int i) {
        while ((i & -128) != 0) {
            zzj((i & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            i >>>= 7;
        }
        zzj(i);
    }

    public static int zzm(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        if ((i & -268435456) == 0) {
            return 4;
        }
        return 5;
    }

    private final void zza(long j) {
        while ((-128 & j) != 0) {
            zzj((((int) j) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            j >>>= 7;
        }
        zzj((int) j);
    }

    public final void zzb(long j) {
        if (this.zzhb.remaining() >= 8) {
            this.zzhb.putLong(j);
            return;
        }
        throw new zzm(this.zzhb.position(), this.zzhb.limit());
    }

    public static int zzn(int i) {
        return (i >> 31) ^ (i << 1);
    }
}
