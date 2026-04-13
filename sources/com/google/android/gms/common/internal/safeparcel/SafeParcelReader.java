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
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class SafeParcelReader {

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static class ParseException extends RuntimeException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ParseException(@androidx.annotation.NonNull java.lang.String r3, @androidx.annotation.NonNull android.os.Parcel r4) {
            /*
                r2 = this;
                int r0 = r4.dataPosition()
                int r4 = r4.dataSize()
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                r1.append(r3)
                java.lang.String r3 = " Parcel: pos="
                r1.append(r3)
                r1.append(r0)
                java.lang.String r3 = " size="
                r1.append(r3)
                r1.append(r4)
                java.lang.String r3 = r1.toString()
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.safeparcel.SafeParcelReader.ParseException.<init>(java.lang.String, android.os.Parcel):void");
        }
    }

    private SafeParcelReader() {
    }

    @NonNull
    public static BigDecimal createBigDecimal(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        byte[] createByteArray = p.createByteArray();
        int readInt = p.readInt();
        p.setDataPosition(dataPosition + header2);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    @NonNull
    public static BigDecimal[] createBigDecimalArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[readInt];
        for (int i = 0; i < readInt; i++) {
            byte[] createByteArray = p.createByteArray();
            bigDecimalArr[i] = new BigDecimal(new BigInteger(createByteArray), p.readInt());
        }
        p.setDataPosition(dataPosition + header2);
        return bigDecimalArr;
    }

    @NonNull
    public static BigInteger createBigInteger(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        byte[] createByteArray = p.createByteArray();
        p.setDataPosition(dataPosition + header2);
        return new BigInteger(createByteArray);
    }

    @NonNull
    public static BigInteger[] createBigIntegerArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[readInt];
        for (int i = 0; i < readInt; i++) {
            bigIntegerArr[i] = new BigInteger(p.createByteArray());
        }
        p.setDataPosition(dataPosition + header2);
        return bigIntegerArr;
    }

    @NonNull
    public static boolean[] createBooleanArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        boolean[] createBooleanArray = p.createBooleanArray();
        p.setDataPosition(dataPosition + header2);
        return createBooleanArray;
    }

    @NonNull
    public static ArrayList<Boolean> createBooleanList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<Boolean> arrayList = new ArrayList<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Boolean.valueOf(p.readInt() != 0));
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static Bundle createBundle(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        Bundle readBundle = p.readBundle();
        p.setDataPosition(dataPosition + header2);
        return readBundle;
    }

    @NonNull
    public static byte[] createByteArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        byte[] createByteArray = p.createByteArray();
        p.setDataPosition(dataPosition + header2);
        return createByteArray;
    }

    @NonNull
    public static byte[][] createByteArrayArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        byte[][] bArr = new byte[readInt][];
        for (int i = 0; i < readInt; i++) {
            bArr[i] = p.createByteArray();
        }
        p.setDataPosition(dataPosition + header2);
        return bArr;
    }

    @NonNull
    public static SparseArray<byte[]> createByteArraySparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        SparseArray<byte[]> sparseArray = new SparseArray<>(readInt);
        for (int i = 0; i < readInt; i++) {
            sparseArray.append(p.readInt(), p.createByteArray());
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static char[] createCharArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        char[] createCharArray = p.createCharArray();
        p.setDataPosition(dataPosition + header2);
        return createCharArray;
    }

    @NonNull
    public static double[] createDoubleArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        double[] createDoubleArray = p.createDoubleArray();
        p.setDataPosition(dataPosition + header2);
        return createDoubleArray;
    }

    @NonNull
    public static ArrayList<Double> createDoubleList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<Double> arrayList = new ArrayList<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Double.valueOf(p.readDouble()));
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Double> createDoubleSparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseArray<Double> sparseArray = new SparseArray<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            sparseArray.append(p.readInt(), Double.valueOf(p.readDouble()));
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static float[] createFloatArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        float[] createFloatArray = p.createFloatArray();
        p.setDataPosition(dataPosition + header2);
        return createFloatArray;
    }

    @NonNull
    public static ArrayList<Float> createFloatList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<Float> arrayList = new ArrayList<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Float.valueOf(p.readFloat()));
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Float> createFloatSparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseArray<Float> sparseArray = new SparseArray<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            sparseArray.append(p.readInt(), Float.valueOf(p.readFloat()));
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static IBinder[] createIBinderArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        IBinder[] createBinderArray = p.createBinderArray();
        p.setDataPosition(dataPosition + header2);
        return createBinderArray;
    }

    @NonNull
    public static ArrayList<IBinder> createIBinderList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<IBinder> createBinderArrayList = p.createBinderArrayList();
        p.setDataPosition(dataPosition + header2);
        return createBinderArrayList;
    }

    @NonNull
    public static SparseArray<IBinder> createIBinderSparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        SparseArray<IBinder> sparseArray = new SparseArray<>(readInt);
        for (int i = 0; i < readInt; i++) {
            sparseArray.append(p.readInt(), p.readStrongBinder());
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static int[] createIntArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int[] createIntArray = p.createIntArray();
        p.setDataPosition(dataPosition + header2);
        return createIntArray;
    }

    @NonNull
    public static ArrayList<Integer> createIntegerList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Integer.valueOf(p.readInt()));
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static long[] createLongArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        long[] createLongArray = p.createLongArray();
        p.setDataPosition(dataPosition + header2);
        return createLongArray;
    }

    @NonNull
    public static ArrayList<Long> createLongList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<Long> arrayList = new ArrayList<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Long.valueOf(p.readLong()));
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static Parcel createParcel(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.appendFrom(p, dataPosition, header2);
        p.setDataPosition(dataPosition + header2);
        return obtain;
    }

    @NonNull
    public static Parcel[] createParcelArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        Parcel[] parcelArr = new Parcel[readInt];
        for (int i = 0; i < readInt; i++) {
            int readInt2 = p.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = p.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(p, dataPosition2, readInt2);
                parcelArr[i] = obtain;
                p.setDataPosition(dataPosition2 + readInt2);
            } else {
                parcelArr[i] = null;
            }
        }
        p.setDataPosition(dataPosition + header2);
        return parcelArr;
    }

    @NonNull
    public static ArrayList<Parcel> createParcelList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        ArrayList<Parcel> arrayList = new ArrayList<>();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = p.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = p.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(p, dataPosition2, readInt2);
                arrayList.add(obtain);
                p.setDataPosition(dataPosition2 + readInt2);
            } else {
                arrayList.add((Object) null);
            }
        }
        p.setDataPosition(dataPosition + header2);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Parcel> createParcelSparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        SparseArray<Parcel> sparseArray = new SparseArray<>();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = p.readInt();
            int readInt3 = p.readInt();
            if (readInt3 != 0) {
                int dataPosition2 = p.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(p, dataPosition2, readInt3);
                sparseArray.append(readInt2, obtain);
                p.setDataPosition(dataPosition2 + readInt3);
            } else {
                sparseArray.append(readInt2, (Object) null);
            }
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static <T extends Parcelable> T createParcelable(@NonNull Parcel p, int header, @NonNull Parcelable.Creator<T> creator) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        T t = (Parcelable) creator.createFromParcel(p);
        p.setDataPosition(dataPosition + header2);
        return t;
    }

    @NonNull
    public static SparseBooleanArray createSparseBooleanArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseBooleanArray readSparseBooleanArray = p.readSparseBooleanArray();
        p.setDataPosition(dataPosition + header2);
        return readSparseBooleanArray;
    }

    @NonNull
    public static SparseIntArray createSparseIntArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            sparseIntArray.append(p.readInt(), p.readInt());
        }
        p.setDataPosition(dataPosition + header2);
        return sparseIntArray;
    }

    @NonNull
    public static SparseLongArray createSparseLongArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseLongArray sparseLongArray = new SparseLongArray();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            sparseLongArray.append(p.readInt(), p.readLong());
        }
        p.setDataPosition(dataPosition + header2);
        return sparseLongArray;
    }

    @NonNull
    public static String createString(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        String readString = p.readString();
        p.setDataPosition(dataPosition + header2);
        return readString;
    }

    @NonNull
    public static String[] createStringArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        String[] createStringArray = p.createStringArray();
        p.setDataPosition(dataPosition + header2);
        return createStringArray;
    }

    @NonNull
    public static ArrayList<String> createStringList(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<String> createStringArrayList = p.createStringArrayList();
        p.setDataPosition(dataPosition + header2);
        return createStringArrayList;
    }

    @NonNull
    public static SparseArray<String> createStringSparseArray(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        SparseArray<String> sparseArray = new SparseArray<>();
        int readInt = p.readInt();
        for (int i = 0; i < readInt; i++) {
            sparseArray.append(p.readInt(), p.readString());
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    @NonNull
    public static <T> T[] createTypedArray(@NonNull Parcel p, int header, @NonNull Parcelable.Creator<T> c) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        T[] createTypedArray = p.createTypedArray(c);
        p.setDataPosition(dataPosition + header2);
        return createTypedArray;
    }

    @NonNull
    public static <T> ArrayList<T> createTypedList(@NonNull Parcel p, int header, @NonNull Parcelable.Creator<T> c) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        ArrayList<T> createTypedArrayList = p.createTypedArrayList(c);
        p.setDataPosition(dataPosition + header2);
        return createTypedArrayList;
    }

    @NonNull
    public static <T> SparseArray<T> createTypedSparseArray(@NonNull Parcel p, int header, @NonNull Parcelable.Creator<T> c) {
        T t;
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        int readInt = p.readInt();
        SparseArray<T> sparseArray = new SparseArray<>();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = p.readInt();
            if (p.readInt() != 0) {
                t = c.createFromParcel(p);
            } else {
                t = null;
            }
            sparseArray.append(readInt2, t);
        }
        p.setDataPosition(dataPosition + header2);
        return sparseArray;
    }

    public static void ensureAtEnd(@NonNull Parcel parcel, int end) {
        if (parcel.dataPosition() != end) {
            throw new ParseException("Overread allowed size end=" + end, parcel);
        }
    }

    public static int getFieldId(int i) {
        return (char) i;
    }

    public static boolean readBoolean(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return p.readInt() != 0;
    }

    @NonNull
    public static Boolean readBooleanObject(@NonNull Parcel p, int header) {
        int readSize = readSize(p, header);
        if (readSize == 0) {
            return null;
        }
        zza(p, header, readSize, 4);
        return Boolean.valueOf(p.readInt() != 0);
    }

    public static byte readByte(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return (byte) p.readInt();
    }

    public static char readChar(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return (char) p.readInt();
    }

    public static double readDouble(@NonNull Parcel p, int header) {
        zzb(p, header, 8);
        return p.readDouble();
    }

    @NonNull
    public static Double readDoubleObject(@NonNull Parcel p, int header) {
        int readSize = readSize(p, header);
        if (readSize == 0) {
            return null;
        }
        zza(p, header, readSize, 8);
        return Double.valueOf(p.readDouble());
    }

    public static float readFloat(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return p.readFloat();
    }

    @NonNull
    public static Float readFloatObject(@NonNull Parcel p, int header) {
        int readSize = readSize(p, header);
        if (readSize == 0) {
            return null;
        }
        zza(p, header, readSize, 4);
        return Float.valueOf(p.readFloat());
    }

    public static int readHeader(@NonNull Parcel p) {
        return p.readInt();
    }

    @NonNull
    public static IBinder readIBinder(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        IBinder readStrongBinder = p.readStrongBinder();
        p.setDataPosition(dataPosition + header2);
        return readStrongBinder;
    }

    public static int readInt(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return p.readInt();
    }

    @NonNull
    public static Integer readIntegerObject(@NonNull Parcel p, int header) {
        int readSize = readSize(p, header);
        if (readSize == 0) {
            return null;
        }
        zza(p, header, readSize, 4);
        return Integer.valueOf(p.readInt());
    }

    public static void readList(@NonNull Parcel p, int header, @NonNull List list, @NonNull ClassLoader loader) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 != 0) {
            p.readList(list, loader);
            p.setDataPosition(dataPosition + header2);
        }
    }

    public static long readLong(@NonNull Parcel p, int header) {
        zzb(p, header, 8);
        return p.readLong();
    }

    @NonNull
    public static Long readLongObject(@NonNull Parcel p, int header) {
        int readSize = readSize(p, header);
        if (readSize == 0) {
            return null;
        }
        zza(p, header, readSize, 8);
        return Long.valueOf(p.readLong());
    }

    @NonNull
    public static PendingIntent readPendingIntent(@NonNull Parcel p, int header) {
        int header2 = readSize(p, header);
        int dataPosition = p.dataPosition();
        if (header2 == 0) {
            return null;
        }
        PendingIntent readPendingIntentOrNullFromParcel = PendingIntent.readPendingIntentOrNullFromParcel(p);
        p.setDataPosition(dataPosition + header2);
        return readPendingIntentOrNullFromParcel;
    }

    public static short readShort(@NonNull Parcel p, int header) {
        zzb(p, header, 4);
        return (short) p.readInt();
    }

    public static int readSize(@NonNull Parcel p, int header) {
        return (header & SupportMenu.CATEGORY_MASK) != -65536 ? (char) (header >> 16) : p.readInt();
    }

    public static void skipUnknownField(@NonNull Parcel p, int header) {
        p.setDataPosition(p.dataPosition() + readSize(p, header));
    }

    public static int validateObjectHeader(@NonNull Parcel p) {
        int readHeader = readHeader(p);
        int readSize = readSize(p, readHeader);
        int fieldId = getFieldId(readHeader);
        int dataPosition = p.dataPosition();
        if (fieldId == 20293) {
            int i = readSize + dataPosition;
            if (i >= dataPosition && i <= p.dataSize()) {
                return i;
            }
            throw new ParseException("Size read is invalid start=" + dataPosition + " end=" + i, p);
        }
        throw new ParseException("Expected object header. Got 0x".concat(String.valueOf(Integer.toHexString(readHeader))), p);
    }

    private static void zza(Parcel parcel, int i, int i2, int i3) {
        if (i2 != i3) {
            String hexString = Integer.toHexString(i2);
            throw new ParseException("Expected size " + i3 + " got " + i2 + " (0x" + hexString + ")", parcel);
        }
    }

    private static void zzb(Parcel parcel, int i, int i2) {
        int readSize = readSize(parcel, i);
        if (readSize != i2) {
            String hexString = Integer.toHexString(readSize);
            throw new ParseException("Expected size " + i2 + " got " + readSize + " (0x" + hexString + ")", parcel);
        }
    }
}
