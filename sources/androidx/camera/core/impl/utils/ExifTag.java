package androidx.camera.core.impl.utils;

public class ExifTag {
    public final String name;
    public final int number;
    public final int primaryFormat;
    public final int secondaryFormat;

    ExifTag(String name2, int number2, int format) {
        this.name = name2;
        this.number = number2;
        this.primaryFormat = format;
        this.secondaryFormat = -1;
    }

    ExifTag(String name2, int number2, int primaryFormat2, int secondaryFormat2) {
        this.name = name2;
        this.number = number2;
        this.primaryFormat = primaryFormat2;
        this.secondaryFormat = secondaryFormat2;
    }

    /* access modifiers changed from: package-private */
    public boolean isFormatCompatible(int format) {
        int i;
        int i2 = this.primaryFormat;
        if (i2 == 7 || format == 7 || i2 == format || (i = this.secondaryFormat) == format) {
            return true;
        }
        if ((i2 == 4 || i == 4) && format == 3) {
            return true;
        }
        if ((i2 == 9 || i == 9) && format == 8) {
            return true;
        }
        if ((i2 == 12 || i == 12) && format == 11) {
            return true;
        }
        return false;
    }
}
