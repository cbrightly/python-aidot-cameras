package androidx.camera.core.impl.utils;

import android.location.Location;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Logger;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Exif {
    private static final ThreadLocal<SimpleDateFormat> DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
        }
    };
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy:MM:dd", Locale.US);
        }
    };
    public static final long INVALID_TIMESTAMP = -1;
    private static final String KILOMETERS_PER_HOUR = "K";
    private static final String KNOTS = "N";
    private static final String MILES_PER_HOUR = "M";
    private static final String TAG = Exif.class.getSimpleName();
    private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss", Locale.US);
        }
    };
    private final ExifInterface mExifInterface;
    private boolean mRemoveTimestamp = false;

    private Exif(ExifInterface exifInterface) {
        this.mExifInterface = exifInterface;
    }

    @NonNull
    public static Exif createFromFile(@NonNull File file) {
        return createFromFileString(file.toString());
    }

    @NonNull
    public static Exif createFromFileString(@NonNull String filePath) {
        return new Exif(new ExifInterface(filePath));
    }

    @NonNull
    public static Exif createFromInputStream(@NonNull InputStream is) {
        return new Exif(new ExifInterface(is));
    }

    private static String convertToExifDateTime(long timestamp) {
        return DATETIME_FORMAT.get().format(new Date(timestamp));
    }

    private static Date convertFromExifDateTime(String dateTime) {
        return DATETIME_FORMAT.get().parse(dateTime);
    }

    private static Date convertFromExifDate(String date) {
        return DATE_FORMAT.get().parse(date);
    }

    private static Date convertFromExifTime(String time) {
        return TIME_FORMAT.get().parse(time);
    }

    public void save() {
        if (!this.mRemoveTimestamp) {
            attachLastModifiedTimestamp();
        }
        this.mExifInterface.saveAttributes();
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "Exif{width=%s, height=%s, rotation=%d, isFlippedVertically=%s, isFlippedHorizontally=%s, location=%s, timestamp=%s, description=%s}", new Object[]{Integer.valueOf(getWidth()), Integer.valueOf(getHeight()), Integer.valueOf(getRotation()), Boolean.valueOf(isFlippedVertically()), Boolean.valueOf(isFlippedHorizontally()), getLocation(), Long.valueOf(getTimestamp()), getDescription()});
    }

    public int getOrientation() {
        return this.mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
    }

    public void setOrientation(int orientation) {
        this.mExifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(orientation));
    }

    public int getWidth() {
        return this.mExifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
    }

    public int getHeight() {
        return this.mExifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
    }

    @Nullable
    public String getDescription() {
        return this.mExifInterface.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);
    }

    public void setDescription(@Nullable String description) {
        this.mExifInterface.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, description);
    }

    public int getRotation() {
        switch (getOrientation()) {
            case 2:
                return 0;
            case 3:
                return 180;
            case 4:
                return 180;
            case 5:
                return SubsamplingScaleImageView.ORIENTATION_270;
            case 6:
                return 90;
            case 7:
                return 90;
            case 8:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                return 0;
        }
    }

    public boolean isFlippedVertically() {
        switch (getOrientation()) {
            case 2:
                return false;
            case 3:
                return false;
            case 4:
                return true;
            case 5:
                return true;
            case 6:
                return false;
            case 7:
                return true;
            case 8:
                return false;
            default:
                return false;
        }
    }

    public boolean isFlippedHorizontally() {
        switch (getOrientation()) {
            case 2:
                return true;
            case 3:
                return false;
            case 4:
                return false;
            case 5:
                return false;
            case 6:
                return false;
            case 7:
                return false;
            case 8:
                return false;
            default:
                return false;
        }
    }

    private void attachLastModifiedTimestamp() {
        long now = System.currentTimeMillis();
        String datetime = convertToExifDateTime(now);
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME, datetime);
        try {
            this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME, Long.toString(now - convertFromExifDateTime(datetime).getTime()));
        } catch (ParseException e) {
        }
    }

    public long getLastModifiedTimestamp() {
        long timestamp = parseTimestamp(this.mExifInterface.getAttribute(ExifInterface.TAG_DATETIME));
        if (timestamp == -1) {
            return -1;
        }
        String subSecs = this.mExifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME);
        if (subSecs == null) {
            return timestamp;
        }
        try {
            long sub = Long.parseLong(subSecs);
            while (sub > 1000) {
                sub /= 10;
            }
            return timestamp + sub;
        } catch (NumberFormatException e) {
            return timestamp;
        }
    }

    public long getTimestamp() {
        long timestamp = parseTimestamp(this.mExifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL));
        if (timestamp == -1) {
            return -1;
        }
        String subSecs = this.mExifInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL);
        if (subSecs == null) {
            return timestamp;
        }
        try {
            long sub = Long.parseLong(subSecs);
            while (sub > 1000) {
                sub /= 10;
            }
            return timestamp + sub;
        } catch (NumberFormatException e) {
            return timestamp;
        }
    }

    @Nullable
    public Location getLocation() {
        double speed;
        String provider = this.mExifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);
        double[] latlng = this.mExifInterface.getLatLong();
        double altitude = this.mExifInterface.getAltitude(0.0d);
        double speed2 = this.mExifInterface.getAttributeDouble(ExifInterface.TAG_GPS_SPEED, 0.0d);
        String speedRef = this.mExifInterface.getAttribute(ExifInterface.TAG_GPS_SPEED_REF);
        String speedRef2 = speedRef == null ? "K" : speedRef;
        long timestamp = parseTimestamp(this.mExifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP), this.mExifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));
        if (latlng == null) {
            return null;
        }
        if (provider == null) {
            provider = TAG;
        }
        Location location = new Location(provider);
        location.setLatitude(latlng[0]);
        location.setLongitude(latlng[1]);
        if (altitude != 0.0d) {
            location.setAltitude(altitude);
        }
        if (speed2 != 0.0d) {
            char c = 65535;
            switch (speedRef2.hashCode()) {
                case 75:
                    if (speedRef2.equals("K")) {
                        c = 2;
                        break;
                    }
                    break;
                case 77:
                    if (speedRef2.equals("M")) {
                        c = 0;
                        break;
                    }
                    break;
                case 78:
                    if (speedRef2.equals("N")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    speed = Speed.fromMilesPerHour(speed2).toMetersPerSecond();
                    break;
                case 1:
                    speed = Speed.fromKnots(speed2).toMetersPerSecond();
                    break;
                default:
                    speed = Speed.fromKilometersPerHour(speed2).toMetersPerSecond();
                    break;
            }
            location.setSpeed((float) speed);
        }
        if (timestamp != -1) {
            location.setTime(timestamp);
        }
        return location;
    }

    public void rotate(int degrees) {
        int orientation;
        if (degrees % 90 != 0) {
            Logger.w(TAG, String.format(Locale.US, "Can only rotate in right angles (eg. 0, 90, 180, 270). %d is unsupported.", new Object[]{Integer.valueOf(degrees)}));
            this.mExifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(0));
            return;
        }
        int degrees2 = degrees % 360;
        int orientation2 = getOrientation();
        while (degrees2 < 0) {
            degrees2 += 90;
            switch (orientation2) {
                case 2:
                    orientation2 = 5;
                    break;
                case 3:
                    orientation2 = 6;
                    break;
                case 4:
                    orientation2 = 7;
                    break;
                case 5:
                    orientation2 = 4;
                    break;
                case 6:
                    orientation2 = 1;
                    break;
                case 7:
                    orientation2 = 2;
                    break;
                case 8:
                    orientation2 = 6;
                    break;
                default:
                    orientation2 = 8;
                    break;
            }
        }
        while (degrees2 > 0) {
            degrees2 -= 90;
            switch (orientation2) {
                case 2:
                    orientation = 7;
                    break;
                case 3:
                    orientation = 8;
                    break;
                case 4:
                    orientation = 5;
                    break;
                case 5:
                    orientation = 2;
                    break;
                case 6:
                    orientation = 3;
                    break;
                case 7:
                    orientation = 4;
                    break;
                case 8:
                    orientation = 1;
                    break;
                default:
                    orientation = 6;
                    break;
            }
        }
        this.mExifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(orientation2));
    }

    public void flipVertically() {
        int orientation;
        switch (getOrientation()) {
            case 2:
                orientation = 3;
                break;
            case 3:
                orientation = 2;
                break;
            case 4:
                orientation = 1;
                break;
            case 5:
                orientation = 8;
                break;
            case 6:
                orientation = 7;
                break;
            case 7:
                orientation = 6;
                break;
            case 8:
                orientation = 5;
                break;
            default:
                orientation = 4;
                break;
        }
        this.mExifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(orientation));
    }

    public void flipHorizontally() {
        int orientation;
        switch (getOrientation()) {
            case 2:
                orientation = 1;
                break;
            case 3:
                orientation = 4;
                break;
            case 4:
                orientation = 3;
                break;
            case 5:
                orientation = 6;
                break;
            case 6:
                orientation = 5;
                break;
            case 7:
                orientation = 8;
                break;
            case 8:
                orientation = 7;
                break;
            default:
                orientation = 2;
                break;
        }
        this.mExifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(orientation));
    }

    public void attachTimestamp() {
        long now = System.currentTimeMillis();
        String datetime = convertToExifDateTime(now);
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, datetime);
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME_DIGITIZED, datetime);
        try {
            String subsec = Long.toString(now - convertFromExifDateTime(datetime).getTime());
            this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, subsec);
            this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, subsec);
        } catch (ParseException e) {
        }
        this.mRemoveTimestamp = false;
    }

    public void removeTimestamp() {
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME_ORIGINAL, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_DATETIME_DIGITIZED, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIGINAL, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_DIGITIZED, (String) null);
        this.mRemoveTimestamp = true;
    }

    public void attachLocation(@NonNull Location location) {
        this.mExifInterface.setGpsInfo(location);
    }

    public void removeLocation() {
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_SPEED, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_SPEED_REF, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, (String) null);
        this.mExifInterface.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP, (String) null);
    }

    private long parseTimestamp(@Nullable String date, @Nullable String time) {
        if (date == null && time == null) {
            return -1;
        }
        if (time == null) {
            try {
                return convertFromExifDate(date).getTime();
            } catch (ParseException e) {
                return -1;
            }
        } else if (date == null) {
            try {
                return convertFromExifTime(time).getTime();
            } catch (ParseException e2) {
                return -1;
            }
        } else {
            return parseTimestamp(date + " " + time);
        }
    }

    private long parseTimestamp(@Nullable String datetime) {
        if (datetime == null) {
            return -1;
        }
        try {
            return convertFromExifDateTime(datetime).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static final class Speed {
        private Speed() {
        }

        static Converter fromKilometersPerHour(double kph) {
            return new Converter(0.621371d * kph);
        }

        static Converter fromMetersPerSecond(double mps) {
            return new Converter(2.23694d * mps);
        }

        static Converter fromMilesPerHour(double mph) {
            return new Converter(mph);
        }

        static Converter fromKnots(double knots) {
            return new Converter(1.15078d * knots);
        }

        public static final class Converter {
            final double mMph;

            Converter(double mph) {
                this.mMph = mph;
            }

            /* access modifiers changed from: package-private */
            public double toKilometersPerHour() {
                return this.mMph / 0.621371d;
            }

            /* access modifiers changed from: package-private */
            public double toMilesPerHour() {
                return this.mMph;
            }

            /* access modifiers changed from: package-private */
            public double toKnots() {
                return this.mMph / 1.15078d;
            }

            /* access modifiers changed from: package-private */
            public double toMetersPerSecond() {
                return this.mMph / 2.23694d;
            }
        }
    }
}
