package com.google.android.gms.common.internal.safeparcel;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class SafeParcelWriter {
    private SafeParcelWriter() {
    }

    public static int beginObjectHeader(@NonNull Parcel p) {
        return zza(p, 20293);
    }

    public static void finishObjectHeader(@NonNull Parcel p, int start) {
        zzb(p, start);
    }

    public static void writeBigDecimal(@NonNull Parcel p, int id, @NonNull BigDecimal val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeByteArray(val.unscaledValue().toByteArray());
            p.writeInt(val.scale());
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBigDecimalArray(@NonNull Parcel p, int id, @NonNull BigDecimal[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int length = val.length;
            p.writeInt(length);
            for (int i = 0; i < length; i++) {
                p.writeByteArray(val[i].unscaledValue().toByteArray());
                p.writeInt(val[i].scale());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBigInteger(@NonNull Parcel p, int id, @NonNull BigInteger val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeByteArray(val.toByteArray());
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBigIntegerArray(@NonNull Parcel p, int id, @NonNull BigInteger[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeInt(r5);
            for (BigInteger byteArray : val) {
                p.writeByteArray(byteArray.toByteArray());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBoolean(@NonNull Parcel p, int id, boolean val) {
        zzc(p, id, 4);
        p.writeInt(val);
    }

    public static void writeBooleanArray(@NonNull Parcel p, int id, @NonNull boolean[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeBooleanArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBooleanList(@NonNull Parcel p, int id, @NonNull List<Boolean> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.get(i).booleanValue() ? 1 : 0);
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBooleanObject(@NonNull Parcel p, int id, @NonNull Boolean val, boolean writeNull) {
        if (val != null) {
            zzc(p, id, 4);
            p.writeInt((int) val.booleanValue());
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeBundle(@NonNull Parcel p, int id, @NonNull Bundle val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeBundle(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeByte(@NonNull Parcel p, int id, byte val) {
        zzc(p, id, 4);
        p.writeInt(val);
    }

    public static void writeByteArray(@NonNull Parcel p, int id, @NonNull byte[] buf, boolean writeNull) {
        if (buf != null) {
            int zza = zza(p, id);
            p.writeByteArray(buf);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeByteArrayArray(@NonNull Parcel p, int id, @NonNull byte[][] buf, boolean writeNull) {
        if (buf != null) {
            int zza = zza(p, id);
            p.writeInt(r5);
            for (byte[] writeByteArray : buf) {
                p.writeByteArray(writeByteArray);
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeByteArraySparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<byte[]> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeByteArray(val.valueAt(i));
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeChar(@NonNull Parcel p, int id, char val) {
        zzc(p, id, 4);
        p.writeInt(val);
    }

    public static void writeCharArray(@NonNull Parcel p, int id, @NonNull char[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeCharArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeDouble(@NonNull Parcel p, int id, double val) {
        zzc(p, id, 8);
        p.writeDouble(val);
    }

    public static void writeDoubleArray(@NonNull Parcel p, int id, @NonNull double[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeDoubleArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeDoubleList(@NonNull Parcel p, int id, @NonNull List<Double> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeDouble(val.get(i).doubleValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeDoubleObject(@NonNull Parcel p, int id, @NonNull Double val, boolean writeNull) {
        if (val != null) {
            zzc(p, id, 8);
            p.writeDouble(val.doubleValue());
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeDoubleSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<Double> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeDouble(val.valueAt(i).doubleValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeFloat(@NonNull Parcel p, int id, float val) {
        zzc(p, id, 4);
        p.writeFloat(val);
    }

    public static void writeFloatArray(@NonNull Parcel p, int id, @NonNull float[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeFloatArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeFloatList(@NonNull Parcel p, int id, @NonNull List<Float> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeFloat(val.get(i).floatValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeFloatObject(@NonNull Parcel p, int id, @NonNull Float val, boolean writeNull) {
        if (val != null) {
            zzc(p, id, 4);
            p.writeFloat(val.floatValue());
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeFloatSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<Float> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeFloat(val.valueAt(i).floatValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIBinder(@NonNull Parcel p, int id, @NonNull IBinder val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeStrongBinder(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIBinderArray(@NonNull Parcel p, int id, @NonNull IBinder[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeBinderArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIBinderList(@NonNull Parcel p, int id, @NonNull List<IBinder> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeBinderList(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIBinderSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<IBinder> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeStrongBinder(val.valueAt(i));
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeInt(@NonNull Parcel p, int id, int val) {
        zzc(p, id, 4);
        p.writeInt(val);
    }

    public static void writeIntArray(@NonNull Parcel p, int id, @NonNull int[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeIntArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIntegerList(@NonNull Parcel p, int id, @NonNull List<Integer> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.get(i).intValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeIntegerObject(@NonNull Parcel p, int id, @NonNull Integer val, boolean writeNull) {
        if (val != null) {
            zzc(p, id, 4);
            p.writeInt(val.intValue());
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeList(@NonNull Parcel p, int id, @NonNull List list, boolean writeNull) {
        if (list != null) {
            int zza = zza(p, id);
            p.writeList(list);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeLong(@NonNull Parcel p, int id, long val) {
        zzc(p, id, 8);
        p.writeLong(val);
    }

    public static void writeLongArray(@NonNull Parcel p, int id, @NonNull long[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeLongArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeLongList(@NonNull Parcel p, int id, @NonNull List<Long> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeLong(val.get(i).longValue());
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeLongObject(@NonNull Parcel p, int id, @NonNull Long val, boolean writeNull) {
        if (val != null) {
            zzc(p, id, 8);
            p.writeLong(val.longValue());
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeParcel(@NonNull Parcel p, int id, @NonNull Parcel val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.appendFrom(val, 0, val.dataSize());
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeParcelArray(@NonNull Parcel p, int id, @NonNull Parcel[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeInt(r7);
            for (Parcel parcel : val) {
                if (parcel != null) {
                    p.writeInt(parcel.dataSize());
                    p.appendFrom(parcel, 0, parcel.dataSize());
                } else {
                    p.writeInt(0);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeParcelList(@NonNull Parcel p, int id, @NonNull List<Parcel> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                Parcel parcel = val.get(i);
                if (parcel != null) {
                    p.writeInt(parcel.dataSize());
                    p.appendFrom(parcel, 0, parcel.dataSize());
                } else {
                    p.writeInt(0);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeParcelSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<Parcel> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                Parcel valueAt = val.valueAt(i);
                if (valueAt != null) {
                    p.writeInt(valueAt.dataSize());
                    p.appendFrom(valueAt, 0, valueAt.dataSize());
                } else {
                    p.writeInt(0);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeParcelable(@NonNull Parcel p, int id, @NonNull Parcelable val, int parcelableFlags, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            val.writeToParcel(p, parcelableFlags);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writePendingIntent(@NonNull Parcel p, int id, @NonNull PendingIntent val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            PendingIntent.writePendingIntentOrNullToParcel(val, p);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeShort(@NonNull Parcel p, int id, short val) {
        zzc(p, id, 4);
        p.writeInt(val);
    }

    public static void writeSparseBooleanArray(@NonNull Parcel p, int id, @NonNull SparseBooleanArray val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeSparseBooleanArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeSparseIntArray(@NonNull Parcel p, int id, @NonNull SparseIntArray val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeInt(val.valueAt(i));
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeSparseLongArray(@NonNull Parcel p, int id, @NonNull SparseLongArray val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeLong(val.valueAt(i));
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeString(@NonNull Parcel p, int id, @NonNull String val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeString(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeStringArray(@NonNull Parcel p, int id, @NonNull String[] val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeStringArray(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeStringList(@NonNull Parcel p, int id, @NonNull List<String> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeStringList(val);
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static void writeStringSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<String> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                p.writeString(val.valueAt(i));
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static <T extends Parcelable> void writeTypedArray(@NonNull Parcel p, int id, @NonNull T[] val, int parcelableFlags, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            p.writeInt(r7);
            for (T t : val) {
                if (t == null) {
                    p.writeInt(0);
                } else {
                    zzd(p, t, parcelableFlags);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static <T extends Parcelable> void writeTypedList(@NonNull Parcel p, int id, @NonNull List<T> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                Parcelable parcelable = (Parcelable) val.get(i);
                if (parcelable == null) {
                    p.writeInt(0);
                } else {
                    zzd(p, parcelable, 0);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    public static <T extends Parcelable> void writeTypedSparseArray(@NonNull Parcel p, int id, @NonNull SparseArray<T> val, boolean writeNull) {
        if (val != null) {
            int zza = zza(p, id);
            int size = val.size();
            p.writeInt(size);
            for (int i = 0; i < size; i++) {
                p.writeInt(val.keyAt(i));
                Parcelable parcelable = (Parcelable) val.valueAt(i);
                if (parcelable == null) {
                    p.writeInt(0);
                } else {
                    zzd(p, parcelable, 0);
                }
            }
            zzb(p, zza);
        } else if (writeNull) {
            zzc(p, id, 0);
        }
    }

    private static int zza(Parcel parcel, int i) {
        parcel.writeInt(i | SupportMenu.CATEGORY_MASK);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzb(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(i - 4);
        parcel.writeInt(dataPosition - i);
        parcel.setDataPosition(dataPosition);
    }

    private static void zzc(Parcel parcel, int i, int i2) {
        parcel.writeInt(i | (i2 << 16));
    }

    private static void zzd(Parcel parcel, Parcelable parcelable, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        parcelable.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }
}
