package androidx.exifinterface.media;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.InputDeviceCompat;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.compress.Checker;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.tutk.IOTC.IOTCAPIs;
import io.netty.util.internal.StringUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.glassfish.grizzly.http.server.util.MappingData;

public class ExifInterface {
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = 1;
    static final Charset ASCII;
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    static final short BYTE_ALIGN_II = 18761;
    static final short BYTE_ALIGN_MM = 19789;
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = 2;
    public static final short CONTRAST_NORMAL = 0;
    public static final short CONTRAST_SOFT = 1;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    private static final Pattern DATETIME_PRIMARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
    private static final Pattern DATETIME_SECONDARY_FORMAT_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
    private static final int DATETIME_VALUE_STRING_LENGTH = 19;
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final ExifTag[] EXIF_POINTER_TAGS = {new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 1), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 1)};
    static final ExifTag[][] EXIF_TAGS;
    public static final short EXPOSURE_MODE_AUTO = 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = 2;
    public static final short EXPOSURE_MODE_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_ACTION = 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = 4;
    public static final short FILE_SOURCE_DSC = 3;
    public static final short FILE_SOURCE_OTHER = 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = 1;
    public static final short FLAG_FLASH_FIRED = 1;
    public static final short FLAG_FLASH_MODE_AUTO = 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = 4;
    private static final List<Integer> FLIPPED_ROTATION_ORDER = Arrays.asList(new Integer[]{2, 7, 4, 5});
    public static final short FORMAT_CHUNKY = 1;
    public static final short FORMAT_PLANAR = 2;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = 1;
    public static final short GAIN_CONTROL_NONE = 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    private static final Pattern GPS_TIMESTAMP_PATTERN = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, 49};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, 112};
    static final byte[] IDENTIFIER_EXIF_APP1;
    private static final byte[] IDENTIFIER_XMP_APP1;
    private static final ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    static final int IFD_TYPE_PREVIEW = 5;
    static final int IFD_TYPE_PRIMARY = 0;
    static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_PNG = 13;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final int IMAGE_TYPE_WEBP = 14;
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_LENGTH_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4);
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, 4);
    static final byte[] JPEG_SIGNATURE = {MARKER, MARKER_SOI, MARKER};
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = 14;
    public static final short LIGHT_SOURCE_D50 = 23;
    public static final short LIGHT_SOURCE_D55 = 20;
    public static final short LIGHT_SOURCE_D65 = 21;
    public static final short LIGHT_SOURCE_D75 = 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = 9;
    public static final short LIGHT_SOURCE_FLASH = 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = 24;
    public static final short LIGHT_SOURCE_OTHER = 255;
    public static final short LIGHT_SOURCE_SHADE = 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = 3;
    public static final short LIGHT_SOURCE_UNKNOWN = 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    static final byte MARKER = -1;
    static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOI = -40;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    public static final short METERING_MODE_AVERAGE = 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = 2;
    public static final short METERING_MODE_MULTI_SPOT = 4;
    public static final short METERING_MODE_OTHER = 255;
    public static final short METERING_MODE_PARTIAL = 6;
    public static final short METERING_MODE_PATTERN = 5;
    public static final short METERING_MODE_SPOT = 3;
    public static final short METERING_MODE_UNKNOWN = 0;
    private static final Pattern NON_ZERO_TIME_PATTERN = Pattern.compile(".*[1-9].*");
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS;
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int PNG_CHUNK_CRC_BYTE_LENGTH = 4;
    private static final int PNG_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    private static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    private static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    private static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = 1;
    public static final short RENDERED_PROCESS_NORMAL = 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = 3;
    public static final short RESOLUTION_UNIT_INCHES = 2;
    private static final List<Integer> ROTATION_ORDER = Arrays.asList(new Integer[]{1, 6, 3, 8});
    private static final short RW2_SIGNATURE = 85;
    public static final short SATURATION_HIGH = 0;
    public static final short SATURATION_LOW = 0;
    public static final short SATURATION_NORMAL = 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = 3;
    public static final short SENSITIVITY_TYPE_REI = 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = 6;
    public static final short SENSITIVITY_TYPE_SOS = 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = 1;
    public static final short SENSOR_TYPE_ONE_CHIP = 2;
    public static final short SENSOR_TYPE_THREE_CHIP = 4;
    public static final short SENSOR_TYPE_TRILINEAR = 7;
    public static final short SENSOR_TYPE_TWO_CHIP = 3;
    public static final short SHARPNESS_HARD = 2;
    public static final short SHARPNESS_NORMAL = 0;
    public static final short SHARPNESS_SOFT = 1;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    static final byte START_CODE = 42;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = 0;
    private static final String TAG = "ExifInterface";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    @Deprecated
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CAMERA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";
    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, Base.kMatchMaxLen, 3);
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    private static final int WEBP_CHUNK_SIZE_BYTE_LENGTH = 4;
    private static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());
    private static final int WEBP_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    private static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
    private static final int WEBP_CHUNK_TYPE_VP8X_DEFAULT_LENGTH = 10;
    private static final int WEBP_FILE_SIZE_BYTE_LENGTH = 4;
    private static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    private static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    private static final byte WEBP_VP8L_SIGNATURE = 47;
    private static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, START_CODE};
    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;
    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = 0;
    public static final short WHITE_BALANCE_MANUAL = 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = 2;
    private static final HashMap<Integer, Integer> sExifPointerTagMap = new HashMap<>();
    private static final HashMap<Integer, ExifTag>[] sExifTagMapsForReading;
    private static final HashMap<String, ExifTag>[] sExifTagMapsForWriting;
    private static SimpleDateFormat sFormatterPrimary;
    private static SimpleDateFormat sFormatterSecondary;
    private static final HashSet<String> sTagSetForCompatibility = new HashSet<>(Arrays.asList(new String[]{TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP}));
    private boolean mAreThumbnailStripsConsecutive;
    private AssetManager.AssetInputStream mAssetInputStream;
    private final HashMap<String, ExifAttribute>[] mAttributes;
    private Set<Integer> mAttributesOffsets;
    private ByteOrder mExifByteOrder;
    private String mFilename;
    private boolean mHasThumbnail;
    private boolean mHasThumbnailStrips;
    private boolean mIsExifDataOnly;
    private int mMimeType;
    private boolean mModified;
    private int mOffsetToExifData;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;
    private boolean mXmpIsFromSeparateMarker;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExifStreamType {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static {
        ExifTag[] exifTagArr = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, CustomCameraView.BUTTON_STATE_ONLY_RECORDER, 3), new ExifTag(TAG_COMPRESSION, CustomCameraView.BUTTON_STATE_BOTH, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, SubsamplingScaleImageView.ORIENTATION_270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, Base.kNumLenSymbols, 2), new ExifTag(TAG_STRIP_OFFSETS, Base.kMatchMaxLen, 3, 4), new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, TypedValues.AttributesType.TYPE_PIVOT_TARGET, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag(TAG_RW2_ISO, 23, 3), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, 7), new ExifTag(TAG_XMP, 700, 1)};
        IFD_TIFF_TAGS = exifTagArr;
        ExifTag[] exifTagArr2 = {new ExifTag(TAG_EXPOSURE_TIME, 33434, 5), new ExifTag(TAG_F_NUMBER, 33437, 5), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag(TAG_SPECTRAL_SENSITIVITY, 34852, 2), new ExifTag(TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, 3), new ExifTag(TAG_OECF, 34856, 7), new ExifTag(TAG_SENSITIVITY_TYPE, 34864, 3), new ExifTag(TAG_STANDARD_OUTPUT_SENSITIVITY, 34865, 4), new ExifTag(TAG_RECOMMENDED_EXPOSURE_INDEX, 34866, 4), new ExifTag(TAG_ISO_SPEED, 34867, 4), new ExifTag(TAG_ISO_SPEED_LATITUDE_YYY, 34868, 4), new ExifTag(TAG_ISO_SPEED_LATITUDE_ZZZ, 34869, 4), new ExifTag(TAG_EXIF_VERSION, 36864, 2), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag(TAG_OFFSET_TIME, 36880, 2), new ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, 2), new ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, 2), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag(TAG_APERTURE_VALUE, 37378, 5), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, 5), new ExifTag(TAG_METERING_MODE, 37383, 3), new ExifTag(TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(TAG_FLASH, 37385, 3), new ExifTag(TAG_FOCAL_LENGTH, 37386, 5), new ExifTag(TAG_SUBJECT_AREA, 37396, 3), new ExifTag(TAG_MAKER_NOTE, 37500, 7), new ExifTag(TAG_USER_COMMENT, 37510, 7), new ExifTag(TAG_SUBSEC_TIME, 37520, 2), new ExifTag(TAG_SUBSEC_TIME_ORIGINAL, 37521, 2), new ExifTag(TAG_SUBSEC_TIME_DIGITIZED, 37522, 2), new ExifTag(TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag(TAG_COLOR_SPACE, 40961, 3), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag(TAG_RELATED_SOUND_FILE, 40964, 2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_FLASH_ENERGY, 41483, 5), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag(TAG_SUBJECT_LOCATION, 41492, 3), new ExifTag(TAG_EXPOSURE_INDEX, 41493, 5), new ExifTag(TAG_SENSING_METHOD, 41495, 3), new ExifTag(TAG_FILE_SOURCE, 41728, 7), new ExifTag(TAG_SCENE_TYPE, 41729, 7), new ExifTag(TAG_CFA_PATTERN, 41730, 7), new ExifTag(TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag(TAG_EXPOSURE_MODE, 41986, 3), new ExifTag(TAG_WHITE_BALANCE, 41987, 3), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag(TAG_GAIN_CONTROL, 41991, 3), new ExifTag(TAG_CONTRAST, 41992, 3), new ExifTag(TAG_SATURATION, 41993, 3), new ExifTag(TAG_SHARPNESS, 41994, 3), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, 2), new ExifTag("CameraOwnerName", 42032, 2), new ExifTag(TAG_BODY_SERIAL_NUMBER, 42033, 2), new ExifTag(TAG_LENS_SPECIFICATION, 42034, 5), new ExifTag(TAG_LENS_MAKE, 42035, 2), new ExifTag(TAG_LENS_MODEL, 42036, 2), new ExifTag(TAG_GAMMA, 42240, 5), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_EXIF_TAGS = exifTagArr2;
        ExifTag[] exifTagArr3 = {new ExifTag(TAG_GPS_VERSION_ID, 0, 1), new ExifTag(TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(TAG_GPS_LATITUDE, 2, 5, 10), new ExifTag(TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(TAG_GPS_LONGITUDE, 4, 5, 10), new ExifTag(TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(TAG_GPS_ALTITUDE, 6, 5), new ExifTag(TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(TAG_GPS_SATELLITES, 8, 2), new ExifTag(TAG_GPS_STATUS, 9, 2), new ExifTag(TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag(TAG_GPS_DOP, 11, 5), new ExifTag(TAG_GPS_SPEED_REF, 12, 2), new ExifTag(TAG_GPS_SPEED, 13, 5), new ExifTag(TAG_GPS_TRACK_REF, 14, 2), new ExifTag(TAG_GPS_TRACK, 15, 5), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag(TAG_GPS_MAP_DATUM, 18, 2), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag(TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(TAG_GPS_DEST_BEARING, 24, 5), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag(TAG_GPS_DATESTAMP, 29, 2), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, 3), new ExifTag(TAG_GPS_H_POSITIONING_ERROR, 31, 5)};
        IFD_GPS_TAGS = exifTagArr3;
        ExifTag[] exifTagArr4 = {new ExifTag(TAG_INTEROPERABILITY_INDEX, 1, 2)};
        IFD_INTEROPERABILITY_TAGS = exifTagArr4;
        ExifTag[] exifTagArr5 = {new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, CustomCameraView.BUTTON_STATE_ONLY_RECORDER, 3), new ExifTag(TAG_COMPRESSION, CustomCameraView.BUTTON_STATE_BOTH, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, SubsamplingScaleImageView.ORIENTATION_270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, Base.kNumLenSymbols, 2), new ExifTag(TAG_STRIP_OFFSETS, Base.kMatchMaxLen, 3, 4), new ExifTag(TAG_THUMBNAIL_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, TypedValues.AttributesType.TYPE_PIVOT_TARGET, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_THUMBNAIL_TAGS = exifTagArr5;
        ExifTag[] exifTagArr6 = {new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 4), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 4)};
        ORF_MAKER_NOTE_TAGS = exifTagArr6;
        ExifTag[] exifTagArr7 = {new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, CustomCameraView.BUTTON_STATE_ONLY_RECORDER, 4)};
        ORF_CAMERA_SETTINGS_TAGS = exifTagArr7;
        ExifTag[] exifTagArr8 = {new ExifTag(TAG_ORF_ASPECT_FRAME, 4371, 3)};
        ORF_IMAGE_PROCESSING_TAGS = exifTagArr8;
        ExifTag[] exifTagArr9 = {new ExifTag(TAG_COLOR_SPACE, 55, 3)};
        PEF_TAGS = exifTagArr9;
        ExifTag[][] exifTagArr10 = {exifTagArr, exifTagArr2, exifTagArr3, exifTagArr4, exifTagArr5, exifTagArr, exifTagArr6, exifTagArr7, exifTagArr8, exifTagArr9};
        EXIF_TAGS = exifTagArr10;
        sExifTagMapsForReading = new HashMap[exifTagArr10.length];
        sExifTagMapsForWriting = new HashMap[exifTagArr10.length];
        Charset forName = Charset.forName("US-ASCII");
        ASCII = forName;
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(forName);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(forName);
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", locale);
        sFormatterPrimary = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        sFormatterSecondary = simpleDateFormat2;
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
        int ifdType = 0;
        while (true) {
            ExifTag[][] exifTagArr11 = EXIF_TAGS;
            if (ifdType < exifTagArr11.length) {
                sExifTagMapsForReading[ifdType] = new HashMap<>();
                sExifTagMapsForWriting[ifdType] = new HashMap<>();
                for (ExifTag tag : exifTagArr11[ifdType]) {
                    sExifTagMapsForReading[ifdType].put(Integer.valueOf(tag.number), tag);
                    sExifTagMapsForWriting[ifdType].put(tag.name, tag);
                }
                ifdType++;
            } else {
                HashMap<Integer, Integer> hashMap = sExifPointerTagMap;
                ExifTag[] exifTagArr12 = EXIF_POINTER_TAGS;
                hashMap.put(Integer.valueOf(exifTagArr12[0].number), 5);
                hashMap.put(Integer.valueOf(exifTagArr12[1].number), 1);
                hashMap.put(Integer.valueOf(exifTagArr12[2].number), 2);
                hashMap.put(Integer.valueOf(exifTagArr12[3].number), 3);
                hashMap.put(Integer.valueOf(exifTagArr12[4].number), 7);
                hashMap.put(Integer.valueOf(exifTagArr12[5].number), 8);
                return;
            }
        }
    }

    public static class Rational {
        public final long denominator;
        public final long numerator;

        Rational(double value) {
            this((long) (10000.0d * value), 10000);
        }

        Rational(long numerator2, long denominator2) {
            if (denominator2 == 0) {
                this.numerator = 0;
                this.denominator = 1;
                return;
            }
            this.numerator = numerator2;
            this.denominator = denominator2;
        }

        public String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public double calculate() {
            return ((double) this.numerator) / ((double) this.denominator);
        }
    }

    public static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        ExifAttribute(int format2, int numberOfComponents2, byte[] bytes2) {
            this(format2, numberOfComponents2, -1, bytes2);
        }

        ExifAttribute(int format2, int numberOfComponents2, long bytesOffset2, byte[] bytes2) {
            this.format = format2;
            this.numberOfComponents = numberOfComponents2;
            this.bytesOffset = bytesOffset2;
            this.bytes = bytes2;
        }

        public static ExifAttribute createUShort(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * values.length)]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putShort((short) value);
            }
            return new ExifAttribute(3, values.length, buffer.array());
        }

        public static ExifAttribute createUShort(int value, ByteOrder byteOrder) {
            return createUShort(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createULong(long[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * values.length)]);
            buffer.order(byteOrder);
            for (long value : values) {
                buffer.putInt((int) value);
            }
            return new ExifAttribute(4, values.length, buffer.array());
        }

        public static ExifAttribute createULong(long value, ByteOrder byteOrder) {
            return createULong(new long[]{value}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * values.length)]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putInt(value);
            }
            return new ExifAttribute(9, values.length, buffer.array());
        }

        public static ExifAttribute createSLong(int value, ByteOrder byteOrder) {
            return createSLong(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createByte(String value) {
            if (value.length() != 1 || value.charAt(0) < '0' || value.charAt(0) > '1') {
                byte[] ascii = value.getBytes(ExifInterface.ASCII);
                return new ExifAttribute(1, ascii.length, ascii);
            }
            byte[] bytes2 = {(byte) (value.charAt(0) - '0')};
            return new ExifAttribute(1, bytes2.length, bytes2);
        }

        public static ExifAttribute createString(String value) {
            byte[] ascii = (value + 0).getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, ascii.length, ascii);
        }

        public static ExifAttribute createURational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * values.length)]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(5, values.length, buffer.array());
        }

        public static ExifAttribute createURational(Rational value, ByteOrder byteOrder) {
            return createURational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * values.length)]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(10, values.length, buffer.array());
        }

        public static ExifAttribute createSRational(Rational value, ByteOrder byteOrder) {
            return createSRational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[(ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * values.length)]);
            buffer.order(byteOrder);
            for (double value : values) {
                buffer.putDouble(value);
            }
            return new ExifAttribute(12, values.length, buffer.array());
        }

        public static ExifAttribute createDouble(double value, ByteOrder byteOrder) {
            return createDouble(new double[]{value}, byteOrder);
        }

        public String toString() {
            return "(" + ExifInterface.IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + ")";
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:153:0x01b6 A[SYNTHETIC, Splitter:B:153:0x01b6] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object getValue(java.nio.ByteOrder r12) {
            /*
                r11 = this;
                java.lang.String r0 = "IOException occurred while closing InputStream"
                java.lang.String r1 = "ExifInterface"
                r2 = 0
                r3 = 0
                androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream r4 = new androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ IOException -> 0x01ad }
                byte[] r5 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                r4.<init>((byte[]) r5)     // Catch:{ IOException -> 0x01ad }
                r2 = r4
                r2.setByteOrder(r12)     // Catch:{ IOException -> 0x01ad }
                int r4 = r11.format     // Catch:{ IOException -> 0x01ad }
                switch(r4) {
                    case 1: goto L_0x0166;
                    case 2: goto L_0x0116;
                    case 3: goto L_0x00f9;
                    case 4: goto L_0x00dc;
                    case 5: goto L_0x00b6;
                    case 6: goto L_0x0166;
                    case 7: goto L_0x0116;
                    case 8: goto L_0x0099;
                    case 9: goto L_0x007c;
                    case 10: goto L_0x0054;
                    case 11: goto L_0x0036;
                    case 12: goto L_0x0019;
                    default: goto L_0x0016;
                }     // Catch:{ IOException -> 0x01ad }
            L_0x0016:
                goto L_0x01a2
            L_0x0019:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                double[] r4 = new double[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x001e:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x002b
                double r6 = r2.readDouble()     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x001e
            L_0x002b:
                r2.close()     // Catch:{ IOException -> 0x0031 }
                goto L_0x0035
            L_0x0031:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x0035:
                return r4
            L_0x0036:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                double[] r4 = new double[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x003b:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x0049
                float r6 = r2.readFloat()     // Catch:{ IOException -> 0x01ad }
                double r6 = (double) r6     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x003b
            L_0x0049:
                r2.close()     // Catch:{ IOException -> 0x004f }
                goto L_0x0053
            L_0x004f:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x0053:
                return r4
            L_0x0054:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                androidx.exifinterface.media.ExifInterface$Rational[] r4 = new androidx.exifinterface.media.ExifInterface.Rational[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x0059:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x0071
                int r6 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
                long r6 = (long) r6     // Catch:{ IOException -> 0x01ad }
                int r8 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
                long r8 = (long) r8     // Catch:{ IOException -> 0x01ad }
                androidx.exifinterface.media.ExifInterface$Rational r10 = new androidx.exifinterface.media.ExifInterface$Rational     // Catch:{ IOException -> 0x01ad }
                r10.<init>(r6, r8)     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r10     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x0059
            L_0x0071:
                r2.close()     // Catch:{ IOException -> 0x0077 }
                goto L_0x007b
            L_0x0077:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x007b:
                return r4
            L_0x007c:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x0081:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x008e
                int r6 = r2.readInt()     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x0081
            L_0x008e:
                r2.close()     // Catch:{ IOException -> 0x0094 }
                goto L_0x0098
            L_0x0094:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x0098:
                return r4
            L_0x0099:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x009e:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x00ab
                short r6 = r2.readShort()     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x009e
            L_0x00ab:
                r2.close()     // Catch:{ IOException -> 0x00b1 }
                goto L_0x00b5
            L_0x00b1:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x00b5:
                return r4
            L_0x00b6:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                androidx.exifinterface.media.ExifInterface$Rational[] r4 = new androidx.exifinterface.media.ExifInterface.Rational[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x00bb:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x00d1
                long r6 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
                long r8 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
                androidx.exifinterface.media.ExifInterface$Rational r10 = new androidx.exifinterface.media.ExifInterface$Rational     // Catch:{ IOException -> 0x01ad }
                r10.<init>(r6, r8)     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r10     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x00bb
            L_0x00d1:
                r2.close()     // Catch:{ IOException -> 0x00d7 }
                goto L_0x00db
            L_0x00d7:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x00db:
                return r4
            L_0x00dc:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                long[] r4 = new long[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x00e1:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x00ee
                long r6 = r2.readUnsignedInt()     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x00e1
            L_0x00ee:
                r2.close()     // Catch:{ IOException -> 0x00f4 }
                goto L_0x00f8
            L_0x00f4:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x00f8:
                return r4
            L_0x00f9:
                int r4 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                int[] r4 = new int[r4]     // Catch:{ IOException -> 0x01ad }
                r5 = 0
            L_0x00fe:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r5 >= r6) goto L_0x010b
                int r6 = r2.readUnsignedShort()     // Catch:{ IOException -> 0x01ad }
                r4[r5] = r6     // Catch:{ IOException -> 0x01ad }
                int r5 = r5 + 1
                goto L_0x00fe
            L_0x010b:
                r2.close()     // Catch:{ IOException -> 0x0111 }
                goto L_0x0115
            L_0x0111:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x0115:
                return r4
            L_0x0116:
                r4 = 0
                int r5 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                byte[] r6 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x01ad }
                int r6 = r6.length     // Catch:{ IOException -> 0x01ad }
                if (r5 < r6) goto L_0x0136
                r5 = 1
                r6 = 0
            L_0x0120:
                byte[] r7 = androidx.exifinterface.media.ExifInterface.EXIF_ASCII_PREFIX     // Catch:{ IOException -> 0x01ad }
                int r8 = r7.length     // Catch:{ IOException -> 0x01ad }
                if (r6 >= r8) goto L_0x0132
                byte[] r8 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                byte r8 = r8[r6]     // Catch:{ IOException -> 0x01ad }
                byte r9 = r7[r6]     // Catch:{ IOException -> 0x01ad }
                if (r8 == r9) goto L_0x012f
                r5 = 0
                goto L_0x0132
            L_0x012f:
                int r6 = r6 + 1
                goto L_0x0120
            L_0x0132:
                if (r5 == 0) goto L_0x0136
                int r6 = r7.length     // Catch:{ IOException -> 0x01ad }
                r4 = r6
            L_0x0136:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01ad }
                r5.<init>()     // Catch:{ IOException -> 0x01ad }
            L_0x013b:
                int r6 = r11.numberOfComponents     // Catch:{ IOException -> 0x01ad }
                if (r4 >= r6) goto L_0x0158
                byte[] r6 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                byte r6 = r6[r4]     // Catch:{ IOException -> 0x01ad }
                if (r6 != 0) goto L_0x0146
                goto L_0x0158
            L_0x0146:
                r7 = 32
                if (r6 < r7) goto L_0x014f
                char r7 = (char) r6     // Catch:{ IOException -> 0x01ad }
                r5.append(r7)     // Catch:{ IOException -> 0x01ad }
                goto L_0x0154
            L_0x014f:
                r7 = 63
                r5.append(r7)     // Catch:{ IOException -> 0x01ad }
            L_0x0154:
                int r4 = r4 + 1
                goto L_0x013b
            L_0x0158:
                java.lang.String r3 = r5.toString()     // Catch:{ IOException -> 0x01ad }
                r2.close()     // Catch:{ IOException -> 0x0161 }
                goto L_0x0165
            L_0x0161:
                r6 = move-exception
                android.util.Log.e(r1, r0, r6)
            L_0x0165:
                return r3
            L_0x0166:
                byte[] r4 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                int r5 = r4.length     // Catch:{ IOException -> 0x01ad }
                r6 = 1
                if (r5 != r6) goto L_0x018f
                r5 = 0
                byte r7 = r4[r5]     // Catch:{ IOException -> 0x01ad }
                if (r7 < 0) goto L_0x018f
                byte r4 = r4[r5]     // Catch:{ IOException -> 0x01ad }
                if (r4 > r6) goto L_0x018f
                java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x01ad }
                char[] r6 = new char[r6]     // Catch:{ IOException -> 0x01ad }
                byte[] r7 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                byte r7 = r7[r5]     // Catch:{ IOException -> 0x01ad }
                int r7 = r7 + 48
                char r7 = (char) r7     // Catch:{ IOException -> 0x01ad }
                r6[r5] = r7     // Catch:{ IOException -> 0x01ad }
                r4.<init>(r6)     // Catch:{ IOException -> 0x01ad }
                r2.close()     // Catch:{ IOException -> 0x018a }
                goto L_0x018e
            L_0x018a:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x018e:
                return r4
            L_0x018f:
                java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x01ad }
                byte[] r5 = r11.bytes     // Catch:{ IOException -> 0x01ad }
                java.nio.charset.Charset r6 = androidx.exifinterface.media.ExifInterface.ASCII     // Catch:{ IOException -> 0x01ad }
                r4.<init>(r5, r6)     // Catch:{ IOException -> 0x01ad }
                r2.close()     // Catch:{ IOException -> 0x019d }
                goto L_0x01a1
            L_0x019d:
                r3 = move-exception
                android.util.Log.e(r1, r0, r3)
            L_0x01a1:
                return r4
            L_0x01a2:
                r2.close()     // Catch:{ IOException -> 0x01a6 }
                goto L_0x01aa
            L_0x01a6:
                r4 = move-exception
                android.util.Log.e(r1, r0, r4)
            L_0x01aa:
                return r3
            L_0x01ab:
                r3 = move-exception
                goto L_0x01bf
            L_0x01ad:
                r4 = move-exception
                java.lang.String r5 = "IOException occurred during reading a value"
                android.util.Log.w(r1, r5, r4)     // Catch:{ all -> 0x01ab }
                if (r2 == 0) goto L_0x01be
                r2.close()     // Catch:{ IOException -> 0x01ba }
                goto L_0x01be
            L_0x01ba:
                r5 = move-exception
                android.util.Log.e(r1, r0, r5)
            L_0x01be:
                return r3
            L_0x01bf:
                if (r2 == 0) goto L_0x01c9
                r2.close()     // Catch:{ IOException -> 0x01c5 }
                goto L_0x01c9
            L_0x01c5:
                r4 = move-exception
                android.util.Log.e(r1, r0, r4)
            L_0x01c9:
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.ExifAttribute.getValue(java.nio.ByteOrder):java.lang.Object");
        }

        public double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] array = (long[]) value;
                    if (array.length == 1) {
                        return (double) array[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] array2 = (int[]) value;
                    if (array2.length == 1) {
                        return (double) array2[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof double[]) {
                    double[] array3 = (double[]) value;
                    if (array3.length == 1) {
                        return array3[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof Rational[]) {
                    Rational[] array4 = (Rational[]) value;
                    if (array4.length == 1) {
                        return array4[0].calculate();
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                if (value instanceof long[]) {
                    long[] array = (long[]) value;
                    if (array.length == 1) {
                        return (int) array[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (value instanceof int[]) {
                    int[] array2 = (int[]) value;
                    if (array2.length == 1) {
                        return array2[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                for (int i = 0; i < array.length; i++) {
                    stringBuilder.append(array[i]);
                    if (i + 1 != array.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            } else if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                for (int i2 = 0; i2 < array2.length; i2++) {
                    stringBuilder.append(array2[i2]);
                    if (i2 + 1 != array2.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            } else if (value instanceof double[]) {
                double[] array3 = (double[]) value;
                for (int i3 = 0; i3 < array3.length; i3++) {
                    stringBuilder.append(array3[i3]);
                    if (i3 + 1 != array3.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            } else if (!(value instanceof Rational[])) {
                return null;
            } else {
                Rational[] array4 = (Rational[]) value;
                for (int i4 = 0; i4 < array4.length; i4++) {
                    stringBuilder.append(array4[i4].numerator);
                    stringBuilder.append('/');
                    stringBuilder.append(array4[i4].denominator);
                    if (i4 + 1 != array4.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            }
        }

        public int size() {
            return ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    public static class ExifTag {
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

    public ExifInterface(@NonNull File file) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (file != null) {
            initForFilename(file.getAbsolutePath());
            return;
        }
        throw new NullPointerException("file cannot be null");
    }

    public ExifInterface(@NonNull String filename) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (filename != null) {
            initForFilename(filename);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    public ExifInterface(@NonNull FileDescriptor fileDescriptor) {
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (fileDescriptor != null) {
            this.mAssetInputStream = null;
            this.mFilename = null;
            boolean isFdDuped = false;
            if (Build.VERSION.SDK_INT < 21 || !isSeekableFD(fileDescriptor)) {
                this.mSeekableFileDescriptor = null;
            } else {
                this.mSeekableFileDescriptor = fileDescriptor;
                try {
                    fileDescriptor = Os.dup(fileDescriptor);
                    isFdDuped = true;
                } catch (Exception e) {
                    throw new IOException("Failed to duplicate file descriptor", e);
                }
            }
            FileInputStream in = null;
            try {
                in = new FileInputStream(fileDescriptor);
                loadAttributes(in);
            } finally {
                closeQuietly(in);
                if (isFdDuped) {
                    closeFileDescriptor(fileDescriptor);
                }
            }
        } else {
            throw new NullPointerException("fileDescriptor cannot be null");
        }
    }

    public ExifInterface(@NonNull InputStream inputStream) {
        this(inputStream, 0);
    }

    public ExifInterface(@NonNull InputStream inputStream, int streamType) {
        InputStream inputStream2;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        this.mAttributes = new HashMap[exifTagArr.length];
        this.mAttributesOffsets = new HashSet(exifTagArr.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (inputStream != null) {
            this.mFilename = null;
            if (streamType == 1) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
                if (!isExifDataOnly(bufferedInputStream)) {
                    Log.w(TAG, "Given data does not follow the structure of an Exif-only data.");
                    return;
                }
                this.mIsExifDataOnly = true;
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
                inputStream2 = bufferedInputStream;
            } else if (inputStream instanceof AssetManager.AssetInputStream) {
                this.mAssetInputStream = (AssetManager.AssetInputStream) inputStream;
                this.mSeekableFileDescriptor = null;
                inputStream2 = inputStream;
            } else if (!(inputStream instanceof FileInputStream) || !isSeekableFD(((FileInputStream) inputStream).getFD())) {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
                inputStream2 = inputStream;
            } else {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = ((FileInputStream) inputStream).getFD();
                inputStream2 = inputStream;
            }
            loadAttributes(inputStream2);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }

    public static boolean isSupportedMimeType(@NonNull String mimeType) {
        if (mimeType != null) {
            String lowerCase = mimeType.toLowerCase(Locale.ROOT);
            char c = 65535;
            switch (lowerCase.hashCode()) {
                case -1875291391:
                    if (lowerCase.equals("image/x-fuji-raf")) {
                        c = 10;
                        break;
                    }
                    break;
                case -1635437028:
                    if (lowerCase.equals("image/x-samsung-srw")) {
                        c = 9;
                        break;
                    }
                    break;
                case -1594371159:
                    if (lowerCase.equals("image/x-sony-arw")) {
                        c = 5;
                        break;
                    }
                    break;
                case -1487464693:
                    if (lowerCase.equals(Checker.MIME_TYPE_HEIC)) {
                        c = 11;
                        break;
                    }
                    break;
                case -1487464690:
                    if (lowerCase.equals("image/heif")) {
                        c = 12;
                        break;
                    }
                    break;
                case -1487394660:
                    if (lowerCase.equals("image/jpeg")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1487018032:
                    if (lowerCase.equals("image/webp")) {
                        c = 14;
                        break;
                    }
                    break;
                case -1423313290:
                    if (lowerCase.equals("image/x-adobe-dng")) {
                        c = 1;
                        break;
                    }
                    break;
                case -985160897:
                    if (lowerCase.equals("image/x-panasonic-rw2")) {
                        c = 6;
                        break;
                    }
                    break;
                case -879258763:
                    if (lowerCase.equals("image/png")) {
                        c = StringUtil.CARRIAGE_RETURN;
                        break;
                    }
                    break;
                case -332763809:
                    if (lowerCase.equals("image/x-pentax-pef")) {
                        c = 8;
                        break;
                    }
                    break;
                case 1378106698:
                    if (lowerCase.equals("image/x-olympus-orf")) {
                        c = 7;
                        break;
                    }
                    break;
                case 2099152104:
                    if (lowerCase.equals("image/x-nikon-nef")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2099152524:
                    if (lowerCase.equals("image/x-nikon-nrw")) {
                        c = 4;
                        break;
                    }
                    break;
                case 2111234748:
                    if (lowerCase.equals("image/x-canon-cr2")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    return true;
                default:
                    return false;
            }
        } else {
            throw new NullPointerException("mimeType shouldn't be null");
        }
    }

    @Nullable
    private ExifAttribute getExifAttribute(@NonNull String tag) {
        if (tag != null) {
            if (TAG_ISO_SPEED_RATINGS.equals(tag)) {
                if (DEBUG) {
                    Log.d(TAG, "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
                }
                tag = TAG_PHOTOGRAPHIC_SENSITIVITY;
            }
            for (int i = 0; i < EXIF_TAGS.length; i++) {
                ExifAttribute value = this.mAttributes[i].get(tag);
                if (value != null) {
                    return value;
                }
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    @Nullable
    public String getAttribute(@NonNull String tag) {
        if (tag != null) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute == null) {
                return null;
            }
            if (!sTagSetForCompatibility.contains(tag)) {
                return attribute.getStringValue(this.mExifByteOrder);
            }
            if (tag.equals(TAG_GPS_TIMESTAMP)) {
                int i = attribute.format;
                if (i == 5 || i == 10) {
                    Rational[] array = (Rational[]) attribute.getValue(this.mExifByteOrder);
                    if (array == null || array.length != 3) {
                        Log.w(TAG, "Invalid GPS Timestamp array. array=" + Arrays.toString(array));
                        return null;
                    }
                    return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (((float) array[0].numerator) / ((float) array[0].denominator))), Integer.valueOf((int) (((float) array[1].numerator) / ((float) array[1].denominator))), Integer.valueOf((int) (((float) array[2].numerator) / ((float) array[2].denominator)))});
                }
                Log.w(TAG, "GPS Timestamp format is not rational. format=" + attribute.format);
                return null;
            }
            try {
                return Double.toString(attribute.getDoubleValue(this.mExifByteOrder));
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public int getAttributeInt(@NonNull String tag, int defaultValue) {
        if (tag != null) {
            ExifAttribute exifAttribute = getExifAttribute(tag);
            if (exifAttribute == null) {
                return defaultValue;
            }
            try {
                return exifAttribute.getIntValue(this.mExifByteOrder);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public double getAttributeDouble(@NonNull String tag, double defaultValue) {
        if (tag != null) {
            ExifAttribute exifAttribute = getExifAttribute(tag);
            if (exifAttribute == null) {
                return defaultValue;
            }
            try {
                return exifAttribute.getDoubleValue(this.mExifByteOrder);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public void setAttribute(@NonNull String tag, @Nullable String value) {
        String tag2;
        int i;
        int dataFormat;
        String str;
        String tag3 = tag;
        String value2 = value;
        if (tag3 != null) {
            if ((TAG_DATETIME.equals(tag3) || TAG_DATETIME_ORIGINAL.equals(tag3) || TAG_DATETIME_DIGITIZED.equals(tag3)) && value2 != null) {
                boolean isPrimaryFormat = DATETIME_PRIMARY_FORMAT_PATTERN.matcher(value2).find();
                boolean isSecondaryFormat = DATETIME_SECONDARY_FORMAT_PATTERN.matcher(value2).find();
                if (value.length() != 19 || (!isPrimaryFormat && !isSecondaryFormat)) {
                    Log.w(TAG, "Invalid value for " + tag3 + " : " + value2);
                    return;
                } else if (isSecondaryFormat) {
                    value2 = value2.replaceAll("-", ":");
                }
            }
            if (TAG_ISO_SPEED_RATINGS.equals(tag3)) {
                if (DEBUG) {
                    Log.d(TAG, "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
                }
                tag2 = TAG_PHOTOGRAPHIC_SENSITIVITY;
            } else {
                tag2 = tag3;
            }
            int i2 = 2;
            int i3 = 1;
            if (value2 != null && sTagSetForCompatibility.contains(tag2)) {
                if (tag2.equals(TAG_GPS_TIMESTAMP)) {
                    Matcher m = GPS_TIMESTAMP_PATTERN.matcher(value2);
                    if (!m.find()) {
                        Log.w(TAG, "Invalid value for " + tag2 + " : " + value2);
                        return;
                    }
                    value2 = Integer.parseInt(m.group(1)) + "/1," + Integer.parseInt(m.group(2)) + "/1," + Integer.parseInt(m.group(3)) + "/1";
                } else {
                    try {
                        value2 = new Rational(Double.parseDouble(value2)).toString();
                    } catch (NumberFormatException e) {
                        Log.w(TAG, "Invalid value for " + tag2 + " : " + value2);
                        return;
                    }
                }
            }
            int i4 = 0;
            while (i4 < EXIF_TAGS.length) {
                if (i4 != 4 || this.mHasThumbnail) {
                    ExifTag exifTag = sExifTagMapsForWriting[i4].get(tag2);
                    if (exifTag != null) {
                        if (value2 != null) {
                            Pair<Integer, Integer> guess = guessDataFormat(value2);
                            int i5 = -1;
                            if (exifTag.primaryFormat == ((Integer) guess.first).intValue() || exifTag.primaryFormat == ((Integer) guess.second).intValue()) {
                                dataFormat = exifTag.primaryFormat;
                            } else {
                                int i6 = exifTag.secondaryFormat;
                                if (i6 == -1 || !(i6 == ((Integer) guess.first).intValue() || exifTag.secondaryFormat == ((Integer) guess.second).intValue())) {
                                    int dataFormat2 = exifTag.primaryFormat;
                                    if (dataFormat2 == i3 || dataFormat2 == 7 || dataFormat2 == i2) {
                                        dataFormat = exifTag.primaryFormat;
                                    } else if (DEBUG) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Given tag (");
                                        sb.append(tag2);
                                        sb.append(") value didn't match with one of expected formats: ");
                                        String[] strArr = IFD_FORMAT_NAMES;
                                        sb.append(strArr[exifTag.primaryFormat]);
                                        String str2 = "";
                                        if (exifTag.secondaryFormat == -1) {
                                            str = str2;
                                        } else {
                                            str = ", " + strArr[exifTag.secondaryFormat];
                                        }
                                        sb.append(str);
                                        sb.append(" (guess: ");
                                        sb.append(strArr[((Integer) guess.first).intValue()]);
                                        if (((Integer) guess.second).intValue() != -1) {
                                            str2 = ", " + strArr[((Integer) guess.second).intValue()];
                                        }
                                        sb.append(str2);
                                        sb.append(")");
                                        Log.d(TAG, sb.toString());
                                        i = i3;
                                    } else {
                                        i = i3;
                                    }
                                } else {
                                    dataFormat = exifTag.secondaryFormat;
                                }
                            }
                            char c = 0;
                            String str3 = "/";
                            switch (dataFormat) {
                                case 1:
                                    i = i3;
                                    Pair<Integer, Integer> pair = guess;
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createByte(value2));
                                    break;
                                case 2:
                                case 7:
                                    i = i3;
                                    Pair<Integer, Integer> pair2 = guess;
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createString(value2));
                                    break;
                                case 3:
                                    i = i3;
                                    Pair<Integer, Integer> pair3 = guess;
                                    String[] values = value2.split(",", -1);
                                    int[] intArray = new int[values.length];
                                    for (int j = 0; j < values.length; j++) {
                                        intArray[j] = Integer.parseInt(values[j]);
                                    }
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createUShort(intArray, this.mExifByteOrder));
                                    break;
                                case 4:
                                    i = i3;
                                    Pair<Integer, Integer> pair4 = guess;
                                    String[] values2 = value2.split(",", -1);
                                    long[] longArray = new long[values2.length];
                                    for (int j2 = 0; j2 < values2.length; j2++) {
                                        longArray[j2] = Long.parseLong(values2[j2]);
                                    }
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createULong(longArray, this.mExifByteOrder));
                                    break;
                                case 5:
                                    String[] values3 = value2.split(",", -1);
                                    Rational[] rationalArray = new Rational[values3.length];
                                    int j3 = 0;
                                    while (j3 < values3.length) {
                                        String[] numbers = values3[j3].split(str3, -1);
                                        rationalArray[j3] = new Rational((long) Double.parseDouble(numbers[0]), (long) Double.parseDouble(numbers[1]));
                                        j3++;
                                        str3 = str3;
                                    }
                                    i = 1;
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createURational(rationalArray, this.mExifByteOrder));
                                    break;
                                case 9:
                                    String[] values4 = value2.split(",", -1);
                                    int[] intArray2 = new int[values4.length];
                                    for (int j4 = 0; j4 < values4.length; j4++) {
                                        intArray2[j4] = Integer.parseInt(values4[j4]);
                                    }
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createSLong(intArray2, this.mExifByteOrder));
                                    i = 1;
                                    break;
                                case 10:
                                    String[] values5 = value2.split(",", -1);
                                    Rational[] rationalArray2 = new Rational[values5.length];
                                    int j5 = 0;
                                    while (j5 < values5.length) {
                                        String[] numbers2 = values5[j5].split(str3, i5);
                                        String[] strArr2 = numbers2;
                                        rationalArray2[j5] = new Rational((long) Double.parseDouble(numbers2[c]), (long) Double.parseDouble(numbers2[i3]));
                                        j5++;
                                        guess = guess;
                                        i3 = 1;
                                        c = 0;
                                        i5 = -1;
                                    }
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createSRational(rationalArray2, this.mExifByteOrder));
                                    i = 1;
                                    break;
                                case 12:
                                    String[] values6 = value2.split(",", -1);
                                    double[] doubleArray = new double[values6.length];
                                    for (int j6 = 0; j6 < values6.length; j6++) {
                                        doubleArray[j6] = Double.parseDouble(values6[j6]);
                                    }
                                    this.mAttributes[i4].put(tag2, ExifAttribute.createDouble(doubleArray, this.mExifByteOrder));
                                    i = i3;
                                    break;
                                default:
                                    i = i3;
                                    Pair<Integer, Integer> pair5 = guess;
                                    if (!DEBUG) {
                                        break;
                                    } else {
                                        Log.d(TAG, "Data format isn't one of expected formats: " + dataFormat);
                                        break;
                                    }
                            }
                        } else {
                            this.mAttributes[i4].remove(tag2);
                            i = i3;
                        }
                    } else {
                        i = i3;
                    }
                } else {
                    i = i3;
                }
                i4++;
                i3 = i;
                i2 = 2;
            }
            return;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public void resetOrientation() {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int degree) {
        int resultOrientation;
        if (degree % 90 == 0) {
            int currentOrientation = getAttributeInt(TAG_ORIENTATION, 1);
            List<Integer> list = ROTATION_ORDER;
            int i = 0;
            if (list.contains(Integer.valueOf(currentOrientation))) {
                int newIndex = ((degree / 90) + list.indexOf(Integer.valueOf(currentOrientation))) % 4;
                if (newIndex < 0) {
                    i = 4;
                }
                resultOrientation = list.get(newIndex + i).intValue();
            } else {
                List<Integer> list2 = FLIPPED_ROTATION_ORDER;
                if (list2.contains(Integer.valueOf(currentOrientation))) {
                    int newIndex2 = ((degree / 90) + list2.indexOf(Integer.valueOf(currentOrientation))) % 4;
                    if (newIndex2 < 0) {
                        i = 4;
                    }
                    resultOrientation = list2.get(newIndex2 + i).intValue();
                } else {
                    resultOrientation = 0;
                }
            }
            setAttribute(TAG_ORIENTATION, Integer.toString(resultOrientation));
            return;
        }
        throw new IllegalArgumentException("degree should be a multiple of 90");
    }

    public void flipVertically() {
        int resultOrientation;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                resultOrientation = 4;
                break;
            case 2:
                resultOrientation = 3;
                break;
            case 3:
                resultOrientation = 2;
                break;
            case 4:
                resultOrientation = 1;
                break;
            case 5:
                resultOrientation = 8;
                break;
            case 6:
                resultOrientation = 7;
                break;
            case 7:
                resultOrientation = 6;
                break;
            case 8:
                resultOrientation = 5;
                break;
            default:
                resultOrientation = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(resultOrientation));
    }

    public void flipHorizontally() {
        int resultOrientation;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                resultOrientation = 2;
                break;
            case 2:
                resultOrientation = 1;
                break;
            case 3:
                resultOrientation = 4;
                break;
            case 4:
                resultOrientation = 3;
                break;
            case 5:
                resultOrientation = 6;
                break;
            case 6:
                resultOrientation = 5;
                break;
            case 7:
                resultOrientation = 8;
                break;
            case 8:
                resultOrientation = 7;
                break;
            default:
                resultOrientation = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(resultOrientation));
    }

    public boolean isFlipped() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 2:
            case 4:
            case 5:
            case 7:
                return true;
            default:
                return false;
        }
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 8:
                return SubsamplingScaleImageView.ORIENTATION_270;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    private void removeAttribute(String tag) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(tag);
        }
    }

    private void loadAttributes(@NonNull InputStream in) {
        if (in != null) {
            int i = 0;
            while (i < EXIF_TAGS.length) {
                try {
                    this.mAttributes[i] = new HashMap<>();
                    i++;
                } catch (IOException e) {
                    boolean z = DEBUG;
                    if (z) {
                        Log.w(TAG, "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    }
                    addDefaultValuesForCompatibility();
                    if (!z) {
                        return;
                    }
                } catch (Throwable th) {
                    addDefaultValuesForCompatibility();
                    if (DEBUG) {
                        printAttributes();
                    }
                    throw th;
                }
            }
            InputStream in2 = in;
            if (this.mIsExifDataOnly == 0) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in, 5000);
                this.mMimeType = getMimeType(bufferedInputStream);
                in2 = bufferedInputStream;
            }
            ByteOrderedDataInputStream inputStream = new ByteOrderedDataInputStream(in2);
            if (!this.mIsExifDataOnly) {
                switch (this.mMimeType) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 11:
                        getRawAttributes(inputStream);
                        break;
                    case 4:
                        getJpegAttributes(inputStream, 0, 0);
                        addDefaultValuesForCompatibility();
                        if (DEBUG) {
                            printAttributes();
                            return;
                        }
                        return;
                    case 7:
                        getOrfAttributes(inputStream);
                        break;
                    case 9:
                        getRafAttributes(inputStream);
                        addDefaultValuesForCompatibility();
                        if (DEBUG) {
                            printAttributes();
                            return;
                        }
                        return;
                    case 10:
                        getRw2Attributes(inputStream);
                        addDefaultValuesForCompatibility();
                        if (DEBUG) {
                            printAttributes();
                            return;
                        }
                        return;
                    case 12:
                        getHeifAttributes(inputStream);
                        break;
                    case 13:
                        getPngAttributes(inputStream);
                        addDefaultValuesForCompatibility();
                        if (DEBUG) {
                            printAttributes();
                            return;
                        }
                        return;
                    case 14:
                        getWebpAttributes(inputStream);
                        addDefaultValuesForCompatibility();
                        if (DEBUG) {
                            printAttributes();
                            return;
                        }
                        return;
                }
            } else {
                getStandaloneAttributes(inputStream);
            }
            inputStream.seek((long) this.mOffsetToExifData);
            setThumbnailData(inputStream);
            addDefaultValuesForCompatibility();
            if (!DEBUG) {
                return;
            }
            printAttributes();
            return;
        }
        throw new NullPointerException("inputstream shouldn't be null");
    }

    private static boolean isSeekableFD(FileDescriptor fd) {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        try {
            Os.lseek(fd, 0, OsConstants.SEEK_CUR);
            return true;
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "The file descriptor for the given input is not seekable");
            }
            return false;
        }
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            Log.d(TAG, "The size of tag group[" + i + "]: " + this.mAttributes[i].size());
            for (Map.Entry<String, ExifAttribute> entry : this.mAttributes[i].entrySet()) {
                ExifAttribute tagValue = entry.getValue();
                Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + tagValue.toString() + ", tagValue: '" + tagValue.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public void saveAttributes() {
        if (!isSupportedFormatForSavingAttributes()) {
            throw new IOException("ExifInterface only supports saving attributes on JPEG, PNG, or WebP formats.");
        } else if (this.mSeekableFileDescriptor == null && this.mFilename == null) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        } else {
            this.mModified = true;
            this.mThumbnailBytes = getThumbnail();
            FileInputStream in = null;
            try {
                File tempFile = File.createTempFile("temp", "tmp");
                if (this.mFilename != null) {
                    in = new FileInputStream(this.mFilename);
                } else if (Build.VERSION.SDK_INT >= 21) {
                    Os.lseek(this.mSeekableFileDescriptor, 0, OsConstants.SEEK_SET);
                    in = new FileInputStream(this.mSeekableFileDescriptor);
                }
                FileOutputStream out = new FileOutputStream(tempFile);
                copy(in, out);
                closeQuietly(in);
                closeQuietly(out);
                FileOutputStream out2 = null;
                boolean shouldKeepTempFile = false;
                try {
                    FileInputStream in2 = new FileInputStream(tempFile);
                    if (this.mFilename != null) {
                        out2 = new FileOutputStream(this.mFilename);
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        Os.lseek(this.mSeekableFileDescriptor, 0, OsConstants.SEEK_SET);
                        out2 = new FileOutputStream(this.mSeekableFileDescriptor);
                    }
                    BufferedInputStream bufferedIn = new BufferedInputStream(in2);
                    BufferedOutputStream bufferedOut = new BufferedOutputStream(out2);
                    int i = this.mMimeType;
                    if (i == 4) {
                        saveJpegAttributes(bufferedIn, bufferedOut);
                    } else if (i == 13) {
                        savePngAttributes(bufferedIn, bufferedOut);
                    } else if (i == 14) {
                        saveWebpAttributes(bufferedIn, bufferedOut);
                    }
                    closeQuietly(bufferedIn);
                    closeQuietly(bufferedOut);
                    if (0 == 0) {
                        tempFile.delete();
                    }
                    this.mThumbnailBytes = null;
                } catch (Exception exception) {
                    shouldKeepTempFile = true;
                    throw new IOException("Failed to save new file. Original file is stored in " + tempFile.getAbsolutePath(), exception);
                } catch (Exception e) {
                    FileInputStream in3 = new FileInputStream(tempFile);
                    if (this.mFilename != null) {
                        out2 = new FileOutputStream(this.mFilename);
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        Os.lseek(this.mSeekableFileDescriptor, 0, OsConstants.SEEK_SET);
                        out2 = new FileOutputStream(this.mSeekableFileDescriptor);
                    }
                    copy(in3, out2);
                    closeQuietly(in3);
                    closeQuietly(out2);
                    throw new IOException("Failed to save new file", e);
                } catch (Throwable th) {
                    closeQuietly((Closeable) null);
                    closeQuietly((Closeable) null);
                    if (!shouldKeepTempFile) {
                        tempFile.delete();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                throw new IOException("Failed to copy original file to temp file", e2);
            } catch (Throwable th2) {
                closeQuietly((Closeable) null);
                closeQuietly((Closeable) null);
                throw th2;
            }
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean hasAttribute(@NonNull String tag) {
        return getExifAttribute(tag) != null;
    }

    @Nullable
    public byte[] getThumbnail() {
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    @Nullable
    public byte[] getThumbnailBytes() {
        if (!this.mHasThumbnail) {
            return null;
        }
        byte[] bArr = this.mThumbnailBytes;
        if (bArr != null) {
            return bArr;
        }
        InputStream in = null;
        FileDescriptor newFileDescriptor = null;
        try {
            InputStream inputStream = this.mAssetInputStream;
            if (inputStream != null) {
                in = inputStream;
                if (in.markSupported()) {
                    in.reset();
                } else {
                    Log.d(TAG, "Cannot read thumbnail from inputstream without mark/reset support");
                    closeQuietly(in);
                    if (0 != 0) {
                        closeFileDescriptor((FileDescriptor) null);
                    }
                    return null;
                }
            } else if (this.mFilename != null) {
                in = new FileInputStream(this.mFilename);
            } else if (Build.VERSION.SDK_INT >= 21) {
                newFileDescriptor = Os.dup(this.mSeekableFileDescriptor);
                Os.lseek(newFileDescriptor, 0, OsConstants.SEEK_SET);
                in = new FileInputStream(newFileDescriptor);
            }
            if (in != null) {
                if (in.skip((long) (this.mThumbnailOffset + this.mOffsetToExifData)) == ((long) (this.mThumbnailOffset + this.mOffsetToExifData))) {
                    byte[] buffer = new byte[this.mThumbnailLength];
                    if (in.read(buffer) == this.mThumbnailLength) {
                        this.mThumbnailBytes = buffer;
                        closeQuietly(in);
                        if (newFileDescriptor != null) {
                            closeFileDescriptor(newFileDescriptor);
                        }
                        return buffer;
                    }
                    throw new IOException("Corrupted image");
                }
                throw new IOException("Corrupted image");
            }
            throw new FileNotFoundException();
        } catch (Exception e) {
            Log.d(TAG, "Encountered exception while getting thumbnail", e);
            closeQuietly(in);
            if (newFileDescriptor != null) {
                closeFileDescriptor(newFileDescriptor);
            }
            return null;
        } catch (Throwable th) {
            closeQuietly(in);
            if (newFileDescriptor != null) {
                closeFileDescriptor(newFileDescriptor);
            }
            throw th;
        }
    }

    @Nullable
    public Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (i == 1) {
            int[] rgbValues = new int[(this.mThumbnailBytes.length / 3)];
            for (int i2 = 0; i2 < rgbValues.length; i2++) {
                byte[] bArr = this.mThumbnailBytes;
                rgbValues[i2] = (bArr[i2 * 3] << MappingData.PATH) + 0 + (bArr[(i2 * 3) + 1] << 8) + bArr[(i2 * 3) + 2];
            }
            ExifAttribute imageLengthAttribute = this.mAttributes[4].get(TAG_IMAGE_LENGTH);
            ExifAttribute imageWidthAttribute = this.mAttributes[4].get(TAG_IMAGE_WIDTH);
            if (!(imageLengthAttribute == null || imageWidthAttribute == null)) {
                return Bitmap.createBitmap(rgbValues, imageWidthAttribute.getIntValue(this.mExifByteOrder), imageLengthAttribute.getIntValue(this.mExifByteOrder), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (!this.mHasThumbnail) {
            return false;
        }
        int i = this.mThumbnailCompression;
        if (i == 6 || i == 7) {
            return true;
        }
        return false;
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        } else if (!this.mHasThumbnail) {
            return null;
        } else {
            if (this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
                return null;
            }
            return new long[]{(long) (this.mThumbnailOffset + this.mOffsetToExifData), (long) this.mThumbnailLength};
        }
    }

    @Nullable
    public long[] getAttributeRange(@NonNull String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        } else if (!this.mModified) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute == null) {
                return null;
            }
            return new long[]{attribute.bytesOffset, (long) attribute.bytes.length};
        } else {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
    }

    @Nullable
    public byte[] getAttributeBytes(@NonNull String tag) {
        if (tag != null) {
            ExifAttribute attribute = getExifAttribute(tag);
            if (attribute != null) {
                return attribute.bytes;
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    @Deprecated
    public boolean getLatLong(float[] output) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        output[0] = (float) latLong[0];
        output[1] = (float) latLong[1];
        return true;
    }

    @Nullable
    public double[] getLatLong() {
        String latValue = getAttribute(TAG_GPS_LATITUDE);
        String latRef = getAttribute(TAG_GPS_LATITUDE_REF);
        String lngValue = getAttribute(TAG_GPS_LONGITUDE);
        String lngRef = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (latValue == null || latRef == null || lngValue == null || lngRef == null) {
            return null;
        }
        try {
            return new double[]{convertRationalLatLonToDouble(latValue, latRef), convertRationalLatLonToDouble(lngValue, lngRef)};
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Latitude/longitude values are not parsable. " + String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", new Object[]{latValue, latRef, lngValue, lngRef}));
            return null;
        }
    }

    public void setGpsInfo(Location location) {
        if (location != null) {
            setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
            setLatLong(location.getLatitude(), location.getLongitude());
            setAltitude(location.getAltitude());
            setAttribute(TAG_GPS_SPEED_REF, "K");
            setAttribute(TAG_GPS_SPEED, new Rational((double) ((location.getSpeed() * ((float) TimeUnit.HOURS.toSeconds(1))) / 1000.0f)).toString());
            String[] dateTime = sFormatterPrimary.format(new Date(location.getTime())).split("\\s+", -1);
            setAttribute(TAG_GPS_DATESTAMP, dateTime[0]);
            setAttribute(TAG_GPS_TIMESTAMP, dateTime[1]);
        }
    }

    public void setLatLong(double latitude, double longitude) {
        if (latitude < -90.0d || latitude > 90.0d || Double.isNaN(latitude)) {
            throw new IllegalArgumentException("Latitude value " + latitude + " is not valid.");
        } else if (longitude < -180.0d || longitude > 180.0d || Double.isNaN(longitude)) {
            throw new IllegalArgumentException("Longitude value " + longitude + " is not valid.");
        } else {
            setAttribute(TAG_GPS_LATITUDE_REF, latitude >= 0.0d ? "N" : LATITUDE_SOUTH);
            setAttribute(TAG_GPS_LATITUDE, convertDecimalDegree(Math.abs(latitude)));
            setAttribute(TAG_GPS_LONGITUDE_REF, longitude >= 0.0d ? LONGITUDE_EAST : LONGITUDE_WEST);
            setAttribute(TAG_GPS_LONGITUDE, convertDecimalDegree(Math.abs(longitude)));
        }
    }

    public double getAltitude(double defaultValue) {
        double altitude = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int i = -1;
        int ref = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (altitude < 0.0d || ref < 0) {
            return defaultValue;
        }
        if (ref != 1) {
            i = 1;
        }
        return ((double) i) * altitude;
    }

    public void setAltitude(double altitude) {
        String ref = altitude >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new Rational(Math.abs(altitude)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, ref);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDateTime(@NonNull Long timeStamp) {
        setAttribute(TAG_DATETIME, sFormatterPrimary.format(new Date(timeStamp.longValue())));
        setAttribute(TAG_SUBSEC_TIME, Long.toString(timeStamp.longValue() % 1000));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute(TAG_SUBSEC_TIME_DIGITIZED), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute(TAG_SUBSEC_TIME_ORIGINAL), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0055, code lost:
        if ("-".equals(r2) != false) goto L_0x0057;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Long parseDateTime(@androidx.annotation.Nullable java.lang.String r13, @androidx.annotation.Nullable java.lang.String r14, @androidx.annotation.Nullable java.lang.String r15) {
        /*
            r0 = 0
            if (r13 == 0) goto L_0x0095
            java.util.regex.Pattern r1 = NON_ZERO_TIME_PATTERN
            java.util.regex.Matcher r1 = r1.matcher(r13)
            boolean r1 = r1.matches()
            if (r1 != 0) goto L_0x0011
            goto L_0x0095
        L_0x0011:
            java.text.ParsePosition r1 = new java.text.ParsePosition
            r2 = 0
            r1.<init>(r2)
            java.text.SimpleDateFormat r3 = sFormatterPrimary     // Catch:{ IllegalArgumentException -> 0x0093 }
            java.util.Date r3 = r3.parse(r13, r1)     // Catch:{ IllegalArgumentException -> 0x0093 }
            if (r3 != 0) goto L_0x0029
            java.text.SimpleDateFormat r4 = sFormatterSecondary     // Catch:{ IllegalArgumentException -> 0x0093 }
            java.util.Date r4 = r4.parse(r13, r1)     // Catch:{ IllegalArgumentException -> 0x0093 }
            r3 = r4
            if (r3 != 0) goto L_0x0029
            return r0
        L_0x0029:
            long r4 = r3.getTime()     // Catch:{ IllegalArgumentException -> 0x0093 }
            if (r15 == 0) goto L_0x0079
            r6 = 1
            java.lang.String r2 = r15.substring(r2, r6)     // Catch:{ IllegalArgumentException -> 0x0093 }
            r7 = 3
            java.lang.String r8 = r15.substring(r6, r7)     // Catch:{ IllegalArgumentException -> 0x0093 }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ IllegalArgumentException -> 0x0093 }
            r9 = 6
            r10 = 4
            java.lang.String r9 = r15.substring(r10, r9)     // Catch:{ IllegalArgumentException -> 0x0093 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ IllegalArgumentException -> 0x0093 }
            java.lang.String r11 = "+"
            boolean r11 = r11.equals(r2)     // Catch:{ IllegalArgumentException -> 0x0093 }
            java.lang.String r12 = "-"
            if (r11 != 0) goto L_0x0057
            boolean r11 = r12.equals(r2)     // Catch:{ IllegalArgumentException -> 0x0093 }
            if (r11 == 0) goto L_0x0079
        L_0x0057:
            java.lang.String r11 = ":"
            java.lang.String r7 = r15.substring(r7, r10)     // Catch:{ IllegalArgumentException -> 0x0093 }
            boolean r7 = r11.equals(r7)     // Catch:{ IllegalArgumentException -> 0x0093 }
            if (r7 == 0) goto L_0x0079
            r7 = 14
            if (r8 > r7) goto L_0x0079
            int r7 = r8 * 60
            int r7 = r7 + r9
            int r7 = r7 * 60
            int r7 = r7 * 1000
            boolean r10 = r12.equals(r2)     // Catch:{ IllegalArgumentException -> 0x0093 }
            if (r10 == 0) goto L_0x0075
            goto L_0x0076
        L_0x0075:
            r6 = -1
        L_0x0076:
            int r7 = r7 * r6
            long r6 = (long) r7
            long r4 = r4 + r6
        L_0x0079:
            if (r14 == 0) goto L_0x008e
            long r6 = java.lang.Long.parseLong(r14)     // Catch:{ NumberFormatException -> 0x008d }
        L_0x007f:
            r8 = 1000(0x3e8, double:4.94E-321)
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x008b
            r8 = 10
            long r8 = r6 / r8
            r6 = r8
            goto L_0x007f
        L_0x008b:
            long r4 = r4 + r6
            goto L_0x008e
        L_0x008d:
            r2 = move-exception
        L_0x008e:
            java.lang.Long r0 = java.lang.Long.valueOf(r4)     // Catch:{ IllegalArgumentException -> 0x0093 }
            return r0
        L_0x0093:
            r2 = move-exception
            return r0
        L_0x0095:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.parseDateTime(java.lang.String, java.lang.String, java.lang.String):java.lang.Long");
    }

    @SuppressLint({"AutoBoxing"})
    @Nullable
    public Long getGpsDateTime() {
        String date = getAttribute(TAG_GPS_DATESTAMP);
        String time = getAttribute(TAG_GPS_TIMESTAMP);
        if (!(date == null || time == null)) {
            Pattern pattern = NON_ZERO_TIME_PATTERN;
            if (pattern.matcher(date).matches() || pattern.matcher(time).matches()) {
                String dateTimeString = date + ' ' + time;
                ParsePosition pos = new ParsePosition(0);
                try {
                    Date dateTime = sFormatterPrimary.parse(dateTimeString, pos);
                    if (dateTime == null && (dateTime = sFormatterSecondary.parse(dateTimeString, pos)) == null) {
                        return null;
                    }
                    return Long.valueOf(dateTime.getTime());
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private void initForFilename(String filename) {
        if (filename != null) {
            FileInputStream in = null;
            this.mAssetInputStream = null;
            this.mFilename = filename;
            try {
                in = new FileInputStream(filename);
                if (isSeekableFD(in.getFD())) {
                    this.mSeekableFileDescriptor = in.getFD();
                } else {
                    this.mSeekableFileDescriptor = null;
                }
                loadAttributes(in);
            } finally {
                closeQuietly(in);
            }
        } else {
            throw new NullPointerException("filename cannot be null");
        }
    }

    private static double convertRationalLatLonToDouble(String rationalString, String ref) {
        try {
            String[] parts = rationalString.split(",", -1);
            String[] pair = parts[0].split("/", -1);
            double degrees = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());
            String[] pair2 = parts[1].split("/", -1);
            double minutes = Double.parseDouble(pair2[0].trim()) / Double.parseDouble(pair2[1].trim());
            String[] pair3 = parts[2].split("/", -1);
            double result = (minutes / 60.0d) + degrees + ((Double.parseDouble(pair3[0].trim()) / Double.parseDouble(pair3[1].trim())) / 3600.0d);
            if (!ref.equals(LATITUDE_SOUTH)) {
                if (!ref.equals(LONGITUDE_WEST)) {
                    if (!ref.equals("N")) {
                        if (!ref.equals(LONGITUDE_EAST)) {
                            throw new IllegalArgumentException();
                        }
                    }
                    return result;
                }
            }
            return -result;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private String convertDecimalDegree(double decimalDegree) {
        long degrees = (long) decimalDegree;
        long minutes = (long) ((decimalDegree - ((double) degrees)) * 60.0d);
        long seconds = Math.round(((decimalDegree - ((double) degrees)) - (((double) minutes) / 60.0d)) * 3600.0d * 1.0E7d);
        return degrees + "/1," + minutes + "/1," + seconds + "/10000000";
    }

    private int getMimeType(BufferedInputStream in) {
        in.mark(5000);
        byte[] signatureCheckBytes = new byte[5000];
        in.read(signatureCheckBytes);
        in.reset();
        if (isJpegFormat(signatureCheckBytes)) {
            return 4;
        }
        if (isRafFormat(signatureCheckBytes)) {
            return 9;
        }
        if (isHeifFormat(signatureCheckBytes)) {
            return 12;
        }
        if (isOrfFormat(signatureCheckBytes)) {
            return 7;
        }
        if (isRw2Format(signatureCheckBytes)) {
            return 10;
        }
        if (isPngFormat(signatureCheckBytes)) {
            return 13;
        }
        if (isWebpFormat(signatureCheckBytes)) {
            return 14;
        }
        return 0;
    }

    private static boolean isJpegFormat(byte[] signatureCheckBytes) {
        int i = 0;
        while (true) {
            byte[] bArr = JPEG_SIGNATURE;
            if (i >= bArr.length) {
                return true;
            }
            if (signatureCheckBytes[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private boolean isRafFormat(byte[] signatureCheckBytes) {
        byte[] rafSignatureBytes = RAF_SIGNATURE.getBytes(Charset.defaultCharset());
        for (int i = 0; i < rafSignatureBytes.length; i++) {
            if (signatureCheckBytes[i] != rafSignatureBytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeifFormat(byte[] signatureCheckBytes) {
        byte[] bArr = signatureCheckBytes;
        ByteOrderedDataInputStream signatureInputStream = null;
        try {
            signatureInputStream = new ByteOrderedDataInputStream(bArr);
            long chunkSize = (long) signatureInputStream.readInt();
            byte[] chunkType = new byte[4];
            signatureInputStream.read(chunkType);
            if (!Arrays.equals(chunkType, HEIF_TYPE_FTYP)) {
                signatureInputStream.close();
                return false;
            }
            long chunkDataOffset = 8;
            if (chunkSize == 1) {
                chunkSize = signatureInputStream.readLong();
                if (chunkSize < 16) {
                    signatureInputStream.close();
                    return false;
                }
                chunkDataOffset = 8 + 8;
            }
            if (chunkSize > ((long) bArr.length)) {
                chunkSize = (long) bArr.length;
            }
            long chunkDataSize = chunkSize - chunkDataOffset;
            if (chunkDataSize < 8) {
                signatureInputStream.close();
                return false;
            }
            byte[] brand = new byte[4];
            boolean isMif1 = false;
            boolean isHeic = false;
            for (long i = 0; i < chunkDataSize / 4; i++) {
                if (signatureInputStream.read(brand) != brand.length) {
                    signatureInputStream.close();
                    return false;
                }
                if (i != 1) {
                    if (Arrays.equals(brand, HEIF_BRAND_MIF1)) {
                        isMif1 = true;
                    } else if (Arrays.equals(brand, HEIF_BRAND_HEIC)) {
                        isHeic = true;
                    }
                    if (isMif1 && isHeic) {
                        signatureInputStream.close();
                        return true;
                    }
                }
            }
            signatureInputStream.close();
            return false;
        } catch (Exception e) {
            if (DEBUG) {
                Log.d(TAG, "Exception parsing HEIF file type box.", e);
            }
            if (signatureInputStream == null) {
            }
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isOrfFormat(byte[] signatureCheckBytes) {
        ByteOrderedDataInputStream signatureInputStream = null;
        boolean z = false;
        try {
            ByteOrderedDataInputStream signatureInputStream2 = new ByteOrderedDataInputStream(signatureCheckBytes);
            ByteOrder readByteOrder = readByteOrder(signatureInputStream2);
            this.mExifByteOrder = readByteOrder;
            signatureInputStream2.setByteOrder(readByteOrder);
            short orfSignature = signatureInputStream2.readShort();
            if (orfSignature == 20306 || orfSignature == 21330) {
                z = true;
            }
            signatureInputStream2.close();
            return z;
        } catch (Exception e) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            return false;
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isRw2Format(byte[] signatureCheckBytes) {
        ByteOrderedDataInputStream signatureInputStream = null;
        boolean z = false;
        try {
            ByteOrderedDataInputStream signatureInputStream2 = new ByteOrderedDataInputStream(signatureCheckBytes);
            ByteOrder readByteOrder = readByteOrder(signatureInputStream2);
            this.mExifByteOrder = readByteOrder;
            signatureInputStream2.setByteOrder(readByteOrder);
            if (signatureInputStream2.readShort() == 85) {
                z = true;
            }
            signatureInputStream2.close();
            return z;
        } catch (Exception e) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            return false;
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isPngFormat(byte[] signatureCheckBytes) {
        int i = 0;
        while (true) {
            byte[] bArr = PNG_SIGNATURE;
            if (i >= bArr.length) {
                return true;
            }
            if (signatureCheckBytes[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private boolean isWebpFormat(byte[] signatureCheckBytes) {
        int i = 0;
        while (true) {
            byte[] bArr = WEBP_SIGNATURE_1;
            if (i >= bArr.length) {
                int i2 = 0;
                while (true) {
                    byte[] bArr2 = WEBP_SIGNATURE_2;
                    if (i2 >= bArr2.length) {
                        return true;
                    }
                    if (signatureCheckBytes[WEBP_SIGNATURE_1.length + i2 + 4] != bArr2[i2]) {
                        return false;
                    }
                    i2++;
                }
            } else if (signatureCheckBytes[i] != bArr[i]) {
                return false;
            } else {
                i++;
            }
        }
    }

    private static boolean isExifDataOnly(BufferedInputStream in) {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        in.mark(bArr.length);
        byte[] signatureCheckBytes = new byte[bArr.length];
        in.read(signatureCheckBytes);
        in.reset();
        int i = 0;
        while (true) {
            byte[] bArr2 = IDENTIFIER_EXIF_APP1;
            if (i >= bArr2.length) {
                return true;
            }
            if (signatureCheckBytes[i] != bArr2[i]) {
                return false;
            }
            i++;
        }
    }

    private void getJpegAttributes(ByteOrderedDataInputStream in, int offsetToJpeg, int imageType) {
        String str;
        int i;
        int bytesRead;
        ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        int i2 = imageType;
        boolean z = DEBUG;
        String str2 = TAG;
        if (z) {
            Log.d(str2, "getJpegAttributes starting with: " + byteOrderedDataInputStream);
        }
        char c = 0;
        byteOrderedDataInputStream.mark(0);
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        byte readByte = in.readByte();
        byte marker = readByte;
        byte b = MARKER;
        if (readByte == -1) {
            int i3 = 1;
            int bytesRead2 = 0 + 1;
            if (in.readByte() == -40) {
                int bytesRead3 = bytesRead2 + 1;
                while (true) {
                    byte marker2 = in.readByte();
                    if (marker2 == b) {
                        int bytesRead4 = bytesRead3 + 1;
                        byte marker3 = in.readByte();
                        boolean z2 = DEBUG;
                        if (z2) {
                            Log.d(str2, "Found JPEG segment indicator: " + Integer.toHexString(marker3 & MARKER));
                        }
                        int bytesRead5 = bytesRead4 + i3;
                        if (marker3 == -39 || marker3 == -38) {
                            byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
                        } else {
                            int length = in.readUnsignedShort() - 2;
                            int bytesRead6 = bytesRead5 + 2;
                            if (z2) {
                                Log.d(str2, "JPEG segment: " + Integer.toHexString(marker3 & MARKER) + " (length: " + (length + 2) + ")");
                            }
                            if (length >= 0) {
                                switch (marker3) {
                                    case IOTCAPIs.IOTC_ER_DEVICE_IS_SLEEP /*-64*/:
                                    case IOTCAPIs.IOTC_ER_NOT_SUPPORT /*-63*/:
                                    case IOTCAPIs.IOTC_ER_QUEUE_FULL /*-62*/:
                                    case IOTCAPIs.IOTC_ER_RESOURCE_ERROR /*-61*/:
                                    case IOTCAPIs.IOTC_ER_DEVICE_IS_BANNED /*-59*/:
                                    case IOTCAPIs.IOTC_ER_NOT_ENOUGH_MEMORY /*-58*/:
                                    case IOTCAPIs.IOTC_ER_STILL_IN_PROCESSING /*-57*/:
                                    case IOTCAPIs.IOTC_ER_NO_PATH_TO_WRITE_DATA /*-55*/:
                                    case IOTCAPIs.IOTC_ER_SERVER_NOT_SUPPORT /*-54*/:
                                    case IOTCAPIs.IOTC_ER_EXCEED_MAX_PACKET_SIZE /*-53*/:
                                    case IOTCAPIs.IOTC_ER_REMOTE_NOT_SUPPORTED /*-51*/:
                                    case IOTCAPIs.IOTC_ER_SESSION_CLOSED /*-50*/:
                                    case IOTCAPIs.IOTC_ER_BLOCKED_CALL /*-49*/:
                                        str = str2;
                                        i = i3;
                                        if (byteOrderedDataInputStream.skipBytes(i) == i) {
                                            this.mAttributes[i2].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong((long) in.readUnsignedShort(), this.mExifByteOrder));
                                            this.mAttributes[i2].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong((long) in.readUnsignedShort(), this.mExifByteOrder));
                                            length -= 5;
                                            break;
                                        } else {
                                            throw new IOException("Invalid SOFx");
                                        }
                                    case IOTCAPIs.IOTC_ER_SESSION_NO_FREE_CHANNEL /*-31*/:
                                        int start = bytesRead6;
                                        byte[] bytes = new byte[length];
                                        byteOrderedDataInputStream.readFully(bytes);
                                        int bytesRead7 = bytesRead6 + length;
                                        length = 0;
                                        byte[] bArr = IDENTIFIER_EXIF_APP1;
                                        if (startsWith(bytes, bArr)) {
                                            byte[] value = Arrays.copyOfRange(bytes, bArr.length, bytes.length);
                                            this.mOffsetToExifData = offsetToJpeg + start + bArr.length;
                                            readExifSegment(value, i2);
                                            setThumbnailData(new ByteOrderedDataInputStream(value));
                                            str = str2;
                                            bytesRead = bytesRead7;
                                            i = i3;
                                        } else {
                                            byte[] bArr2 = IDENTIFIER_XMP_APP1;
                                            if (startsWith(bytes, bArr2)) {
                                                int offset = bArr2.length + start;
                                                byte[] value2 = Arrays.copyOfRange(bytes, bArr2.length, bytes.length);
                                                if (getAttribute(TAG_XMP) == null) {
                                                    str = str2;
                                                    bytesRead = bytesRead7;
                                                    this.mAttributes[c].put(TAG_XMP, new ExifAttribute(1, value2.length, (long) offset, value2));
                                                    i = 1;
                                                    this.mXmpIsFromSeparateMarker = true;
                                                } else {
                                                    str = str2;
                                                    bytesRead = bytesRead7;
                                                    i = i3;
                                                }
                                            } else {
                                                str = str2;
                                                bytesRead = bytesRead7;
                                                i = i3;
                                            }
                                        }
                                        bytesRead6 = bytesRead;
                                        break;
                                    case -2:
                                        byte[] bytes2 = new byte[length];
                                        if (byteOrderedDataInputStream.read(bytes2) == length) {
                                            length = 0;
                                            if (getAttribute(TAG_USER_COMMENT) == null) {
                                                this.mAttributes[i3].put(TAG_USER_COMMENT, ExifAttribute.createString(new String(bytes2, ASCII)));
                                            }
                                            str = str2;
                                            i = i3;
                                            break;
                                        } else {
                                            throw new IOException("Invalid exif");
                                        }
                                    default:
                                        str = str2;
                                        i = i3;
                                        break;
                                }
                                if (length < 0) {
                                    throw new IOException("Invalid length");
                                } else if (byteOrderedDataInputStream.skipBytes(length) == length) {
                                    bytesRead3 = bytesRead6 + length;
                                    i3 = i;
                                    str2 = str;
                                    c = 0;
                                    b = MARKER;
                                } else {
                                    throw new IOException("Invalid JPEG segment");
                                }
                            } else {
                                throw new IOException("Invalid length");
                            }
                        }
                    } else {
                        throw new IOException("Invalid marker:" + Integer.toHexString(marker2 & MARKER));
                    }
                }
                byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
                return;
            }
            throw new IOException("Invalid marker: " + Integer.toHexString(marker & MARKER));
        }
        throw new IOException("Invalid marker: " + Integer.toHexString(marker & MARKER));
    }

    private void getRawAttributes(ByteOrderedDataInputStream in) {
        ExifAttribute makerNoteAttribute;
        parseTiffHeaders(in, in.available());
        readImageFileDirectory(in, 0);
        updateImageSizeValues(in, 0);
        updateImageSizeValues(in, 5);
        updateImageSizeValues(in, 4);
        validateImages();
        if (this.mMimeType == 8 && (makerNoteAttribute = this.mAttributes[1].get(TAG_MAKER_NOTE)) != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            makerNoteDataInputStream.seek(6);
            readImageFileDirectory(makerNoteDataInputStream, 9);
            ExifAttribute colorSpaceAttribute = this.mAttributes[9].get(TAG_COLOR_SPACE);
            if (colorSpaceAttribute != null) {
                this.mAttributes[1].put(TAG_COLOR_SPACE, colorSpaceAttribute);
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream in) {
        ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, "getRafAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.mark(0);
        byteOrderedDataInputStream.skipBytes(84);
        byte[] offsetToJpegBytes = new byte[4];
        byte[] jpegLengthBytes = new byte[4];
        byte[] cfaHeaderOffsetBytes = new byte[4];
        byteOrderedDataInputStream.read(offsetToJpegBytes);
        byteOrderedDataInputStream.read(jpegLengthBytes);
        byteOrderedDataInputStream.read(cfaHeaderOffsetBytes);
        int offsetToJpeg = ByteBuffer.wrap(offsetToJpegBytes).getInt();
        int jpegLength = ByteBuffer.wrap(jpegLengthBytes).getInt();
        int cfaHeaderOffset = ByteBuffer.wrap(cfaHeaderOffsetBytes).getInt();
        byte[] jpegBytes = new byte[jpegLength];
        byteOrderedDataInputStream.seek((long) offsetToJpeg);
        byteOrderedDataInputStream.read(jpegBytes);
        getJpegAttributes(new ByteOrderedDataInputStream(jpegBytes), offsetToJpeg, 5);
        byteOrderedDataInputStream.seek((long) cfaHeaderOffset);
        byteOrderedDataInputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        int numberOfDirectoryEntry = in.readInt();
        if (z) {
            Log.d(TAG, "numberOfDirectoryEntry: " + numberOfDirectoryEntry);
        }
        for (int i = 0; i < numberOfDirectoryEntry; i++) {
            int tagNumber = in.readUnsignedShort();
            int numberOfBytes = in.readUnsignedShort();
            if (tagNumber == TAG_RAF_IMAGE_SIZE.number) {
                int imageLength = in.readShort();
                byte[] bArr = cfaHeaderOffsetBytes;
                int imageWidth = in.readShort();
                byte[] bArr2 = offsetToJpegBytes;
                ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(imageLength, this.mExifByteOrder);
                byte[] bArr3 = jpegLengthBytes;
                ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(imageWidth, this.mExifByteOrder);
                int i2 = offsetToJpeg;
                int i3 = jpegLength;
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
                if (DEBUG) {
                    Log.d(TAG, "Updated to length: " + imageLength + ", width: " + imageWidth);
                    return;
                }
                return;
            }
            byte[] bArr4 = offsetToJpegBytes;
            byte[] bArr5 = jpegLengthBytes;
            int i4 = offsetToJpeg;
            int i5 = jpegLength;
            byteOrderedDataInputStream.skipBytes(numberOfBytes);
        }
    }

    private void getHeifAttributes(ByteOrderedDataInputStream in) {
        final ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                retriever.setDataSource(new MediaDataSource() {
                    long mPosition;

                    public void close() {
                    }

                    public int readAt(long position, byte[] buffer, int offset, int size) {
                        if (size == 0) {
                            return 0;
                        }
                        if (position < 0) {
                            return -1;
                        }
                        try {
                            long j = this.mPosition;
                            if (j != position) {
                                if (j >= 0 && position >= j + ((long) byteOrderedDataInputStream.available())) {
                                    return -1;
                                }
                                byteOrderedDataInputStream.seek(position);
                                this.mPosition = position;
                            }
                            if (size > byteOrderedDataInputStream.available()) {
                                size = byteOrderedDataInputStream.available();
                            }
                            int bytesRead = byteOrderedDataInputStream.read(buffer, offset, size);
                            if (bytesRead >= 0) {
                                this.mPosition += (long) bytesRead;
                                return bytesRead;
                            }
                        } catch (IOException e) {
                        }
                        this.mPosition = -1;
                        return -1;
                    }

                    public long getSize() {
                        return -1;
                    }
                });
            } else {
                FileDescriptor fileDescriptor = this.mSeekableFileDescriptor;
                if (fileDescriptor != null) {
                    retriever.setDataSource(fileDescriptor);
                } else {
                    String str = this.mFilename;
                    if (str != null) {
                        retriever.setDataSource(str);
                    } else {
                        retriever.release();
                        return;
                    }
                }
            }
            String exifOffsetStr = retriever.extractMetadata(33);
            String exifLengthStr = retriever.extractMetadata(34);
            String hasImage = retriever.extractMetadata(26);
            String hasVideo = retriever.extractMetadata(17);
            String width = null;
            String height = null;
            String rotation = null;
            Object obj = "yes";
            if ("yes".equals(hasImage)) {
                width = retriever.extractMetadata(29);
                height = retriever.extractMetadata(30);
                rotation = retriever.extractMetadata(31);
            } else if ("yes".equals(hasVideo)) {
                width = retriever.extractMetadata(18);
                height = retriever.extractMetadata(19);
                rotation = retriever.extractMetadata(24);
            }
            if (width != null) {
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createUShort(Integer.parseInt(width), this.mExifByteOrder));
            }
            if (height != null) {
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createUShort(Integer.parseInt(height), this.mExifByteOrder));
            }
            if (rotation != null) {
                int orientation = 1;
                switch (Integer.parseInt(rotation)) {
                    case 90:
                        orientation = 6;
                        break;
                    case 180:
                        orientation = 3;
                        break;
                    case SubsamplingScaleImageView.ORIENTATION_270 /*270*/:
                        orientation = 8;
                        break;
                }
                this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(orientation, this.mExifByteOrder));
            }
            if (exifOffsetStr != null && exifLengthStr != null) {
                int offset = Integer.parseInt(exifOffsetStr);
                int length = Integer.parseInt(exifLengthStr);
                if (length > 6) {
                    try {
                        byteOrderedDataInputStream.seek((long) offset);
                        byte[] identifier = new byte[6];
                        if (byteOrderedDataInputStream.read(identifier) == 6) {
                            int offset2 = offset + 6;
                            int length2 = length - 6;
                            if (Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                                byte[] bytes = new byte[length2];
                                if (byteOrderedDataInputStream.read(bytes) == length2) {
                                    this.mOffsetToExifData = offset2;
                                    readExifSegment(bytes, 0);
                                } else {
                                    byte[] bArr = identifier;
                                    throw new IOException("Can't read exif");
                                }
                            } else {
                                byte[] bArr2 = identifier;
                                throw new IOException("Invalid identifier");
                            }
                        } else {
                            byte[] bArr3 = identifier;
                            throw new IOException("Can't read identifier");
                        }
                    } catch (Throwable th) {
                        th = th;
                        retriever.release();
                        throw th;
                    }
                } else {
                    throw new IOException("Invalid exif length");
                }
            }
            if (DEBUG) {
                Log.d(TAG, "Heif meta: " + width + "x" + height + ", rotation " + rotation);
            }
            retriever.release();
        } catch (Throwable th2) {
            th = th2;
            retriever.release();
            throw th;
        }
    }

    private void getStandaloneAttributes(ByteOrderedDataInputStream in) {
        byte[] bArr = IDENTIFIER_EXIF_APP1;
        in.skipBytes(bArr.length);
        byte[] data = new byte[in.available()];
        in.readFully(data);
        this.mOffsetToExifData = bArr.length;
        readExifSegment(data, 0);
    }

    private void getOrfAttributes(ByteOrderedDataInputStream in) {
        getRawAttributes(in);
        ExifAttribute makerNoteAttribute = this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (makerNoteAttribute != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            byte[] bArr = ORF_MAKER_NOTE_HEADER_1;
            byte[] makerNoteHeader1Bytes = new byte[bArr.length];
            makerNoteDataInputStream.readFully(makerNoteHeader1Bytes);
            makerNoteDataInputStream.seek(0);
            byte[] bArr2 = ORF_MAKER_NOTE_HEADER_2;
            byte[] makerNoteHeader2Bytes = new byte[bArr2.length];
            makerNoteDataInputStream.readFully(makerNoteHeader2Bytes);
            if (Arrays.equals(makerNoteHeader1Bytes, bArr)) {
                makerNoteDataInputStream.seek(8);
            } else if (Arrays.equals(makerNoteHeader2Bytes, bArr2)) {
                makerNoteDataInputStream.seek(12);
            }
            readImageFileDirectory(makerNoteDataInputStream, 6);
            ExifAttribute imageStartAttribute = this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute imageLengthAttribute = this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (!(imageStartAttribute == null || imageLengthAttribute == null)) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, imageStartAttribute);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, imageLengthAttribute);
            }
            ExifAttribute aspectFrameAttribute = this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (aspectFrameAttribute != null) {
                int[] aspectFrameValues = (int[]) aspectFrameAttribute.getValue(this.mExifByteOrder);
                if (aspectFrameValues == null || aspectFrameValues.length != 4) {
                    Log.w(TAG, "Invalid aspect frame values. frame=" + Arrays.toString(aspectFrameValues));
                } else if (aspectFrameValues[2] > aspectFrameValues[0] && aspectFrameValues[3] > aspectFrameValues[1]) {
                    int primaryImageWidth = (aspectFrameValues[2] - aspectFrameValues[0]) + 1;
                    int primaryImageLength = (aspectFrameValues[3] - aspectFrameValues[1]) + 1;
                    if (primaryImageWidth < primaryImageLength) {
                        int primaryImageWidth2 = primaryImageWidth + primaryImageLength;
                        primaryImageLength = primaryImageWidth2 - primaryImageLength;
                        primaryImageWidth = primaryImageWidth2 - primaryImageLength;
                    }
                    ExifAttribute primaryImageWidthAttribute = ExifAttribute.createUShort(primaryImageWidth, this.mExifByteOrder);
                    ExifAttribute primaryImageLengthAttribute = ExifAttribute.createUShort(primaryImageLength, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, primaryImageWidthAttribute);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, primaryImageLengthAttribute);
                }
            }
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream in) {
        if (DEBUG) {
            Log.d(TAG, "getRw2Attributes starting with: " + in);
        }
        getRawAttributes(in);
        ExifAttribute jpgFromRawAttribute = this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW);
        if (jpgFromRawAttribute != null) {
            getJpegAttributes(new ByteOrderedDataInputStream(jpgFromRawAttribute.bytes), (int) jpgFromRawAttribute.bytesOffset, 5);
        }
        ExifAttribute rw2IsoAttribute = this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifIsoAttribute = this.mAttributes[1].get(TAG_PHOTOGRAPHIC_SENSITIVITY);
        if (rw2IsoAttribute != null && exifIsoAttribute == null) {
            this.mAttributes[1].put(TAG_PHOTOGRAPHIC_SENSITIVITY, rw2IsoAttribute);
        }
    }

    private void getPngAttributes(ByteOrderedDataInputStream in) {
        if (DEBUG) {
            Log.d(TAG, "getPngAttributes starting with: " + in);
        }
        in.mark(0);
        in.setByteOrder(ByteOrder.BIG_ENDIAN);
        byte[] bArr = PNG_SIGNATURE;
        in.skipBytes(bArr.length);
        int bytesRead = 0 + bArr.length;
        while (true) {
            try {
                int length = in.readInt();
                int bytesRead2 = bytesRead + 4;
                byte[] type = new byte[4];
                if (in.read(type) == type.length) {
                    int bytesRead3 = bytesRead2 + 4;
                    if (bytesRead3 == 16) {
                        if (!Arrays.equals(type, PNG_CHUNK_TYPE_IHDR)) {
                            throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                        }
                    }
                    if (!Arrays.equals(type, PNG_CHUNK_TYPE_IEND)) {
                        if (Arrays.equals(type, PNG_CHUNK_TYPE_EXIF)) {
                            byte[] data = new byte[length];
                            if (in.read(data) == length) {
                                int dataCrcValue = in.readInt();
                                CRC32 crc = new CRC32();
                                crc.update(type);
                                crc.update(data);
                                if (((int) crc.getValue()) == dataCrcValue) {
                                    this.mOffsetToExifData = bytesRead3;
                                    readExifSegment(data, 0);
                                    validateImages();
                                    setThumbnailData(new ByteOrderedDataInputStream(data));
                                    return;
                                }
                                throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + dataCrcValue + ", calculated CRC value: " + crc.getValue());
                            }
                            throw new IOException("Failed to read given length for given PNG chunk type: " + byteArrayToHexString(type));
                        }
                        in.skipBytes(length + 4);
                        bytesRead = bytesRead3 + length + 4;
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing PNG chunktype");
                }
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void getWebpAttributes(ByteOrderedDataInputStream in) {
        if (DEBUG) {
            Log.d(TAG, "getWebpAttributes starting with: " + in);
        }
        in.mark(0);
        in.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        in.skipBytes(WEBP_SIGNATURE_1.length);
        int fileSize = in.readInt() + 8;
        int bytesRead = 8 + in.skipBytes(WEBP_SIGNATURE_2.length);
        while (true) {
            try {
                byte[] code = new byte[4];
                if (in.read(code) == code.length) {
                    int chunkSize = in.readInt();
                    int bytesRead2 = bytesRead + 4 + 4;
                    if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, code)) {
                        byte[] payload = new byte[chunkSize];
                        if (in.read(payload) == chunkSize) {
                            this.mOffsetToExifData = bytesRead2;
                            readExifSegment(payload, 0);
                            setThumbnailData(new ByteOrderedDataInputStream(payload));
                            return;
                        }
                        throw new IOException("Failed to read given length for given PNG chunk type: " + byteArrayToHexString(code));
                    }
                    int chunkSize2 = chunkSize % 2 == 1 ? chunkSize + 1 : chunkSize;
                    if (bytesRead2 + chunkSize2 != fileSize) {
                        if (bytesRead2 + chunkSize2 <= fileSize) {
                            int skipped = in.skipBytes(chunkSize2);
                            if (skipped == chunkSize2) {
                                bytesRead = bytesRead2 + skipped;
                            } else {
                                throw new IOException("Encountered WebP file with invalid chunk size");
                            }
                        } else {
                            throw new IOException("Encountered WebP file with invalid chunk size");
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing WebP chunktype");
                }
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) {
        if (DEBUG) {
            Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() == -1) {
            dataOutputStream.writeByte(-1);
            if (dataInputStream.readByte() == -40) {
                dataOutputStream.writeByte(-40);
                ExifAttribute xmpAttribute = null;
                if (getAttribute(TAG_XMP) != null && this.mXmpIsFromSeparateMarker) {
                    xmpAttribute = this.mAttributes[0].remove(TAG_XMP);
                }
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-31);
                writeExifSegment(dataOutputStream);
                if (xmpAttribute != null) {
                    this.mAttributes[0].put(TAG_XMP, xmpAttribute);
                }
                byte[] bytes = new byte[4096];
                while (dataInputStream.readByte() == -1) {
                    byte marker = dataInputStream.readByte();
                    switch (marker) {
                        case IOTCAPIs.IOTC_ER_EXIT_LISTEN /*-39*/:
                        case IOTCAPIs.IOTC_ER_INVALID_MODE /*-38*/:
                            dataOutputStream.writeByte(-1);
                            dataOutputStream.writeByte(marker);
                            copy(dataInputStream, dataOutputStream);
                            return;
                        case IOTCAPIs.IOTC_ER_SESSION_NO_FREE_CHANNEL /*-31*/:
                            int length = dataInputStream.readUnsignedShort() - 2;
                            if (length >= 0) {
                                byte[] identifier = new byte[6];
                                if (length >= 6) {
                                    if (dataInputStream.read(identifier) != 6) {
                                        throw new IOException("Invalid exif");
                                    } else if (Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                                        if (dataInputStream.skipBytes(length - 6) == length - 6) {
                                            break;
                                        } else {
                                            throw new IOException("Invalid length");
                                        }
                                    }
                                }
                                dataOutputStream.writeByte(-1);
                                dataOutputStream.writeByte(marker);
                                dataOutputStream.writeUnsignedShort(length + 2);
                                if (length >= 6) {
                                    length -= 6;
                                    dataOutputStream.write(identifier);
                                }
                                while (length > 0) {
                                    int read = dataInputStream.read(bytes, 0, Math.min(length, bytes.length));
                                    int read2 = read;
                                    if (read < 0) {
                                        break;
                                    } else {
                                        dataOutputStream.write(bytes, 0, read2);
                                        length -= read2;
                                    }
                                }
                                break;
                            } else {
                                throw new IOException("Invalid length");
                            }
                        default:
                            dataOutputStream.writeByte(-1);
                            dataOutputStream.writeByte(marker);
                            int length2 = dataInputStream.readUnsignedShort();
                            dataOutputStream.writeUnsignedShort(length2);
                            int length3 = length2 - 2;
                            if (length3 >= 0) {
                                while (length3 > 0) {
                                    int read3 = dataInputStream.read(bytes, 0, Math.min(length3, bytes.length));
                                    int read4 = read3;
                                    if (read3 < 0) {
                                        break;
                                    } else {
                                        dataOutputStream.write(bytes, 0, read4);
                                        length3 -= read4;
                                    }
                                }
                                break;
                            } else {
                                throw new IOException("Invalid length");
                            }
                    }
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    /* JADX INFO: finally extract failed */
    private void savePngAttributes(InputStream inputStream, OutputStream outputStream) {
        if (DEBUG) {
            Log.d(TAG, "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
        ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(outputStream, byteOrder);
        byte[] bArr = PNG_SIGNATURE;
        copy(dataInputStream, dataOutputStream, bArr.length);
        int i = this.mOffsetToExifData;
        if (i == 0) {
            int ihdrChunkLength = dataInputStream.readInt();
            dataOutputStream.writeInt(ihdrChunkLength);
            copy(dataInputStream, dataOutputStream, ihdrChunkLength + 4 + 4);
        } else {
            copy(dataInputStream, dataOutputStream, ((i - bArr.length) - 4) - 4);
            dataInputStream.skipBytes(dataInputStream.readInt() + 4 + 4);
        }
        ByteArrayOutputStream exifByteArrayOutputStream = null;
        try {
            exifByteArrayOutputStream = new ByteArrayOutputStream();
            ByteOrderedDataOutputStream exifDataOutputStream = new ByteOrderedDataOutputStream(exifByteArrayOutputStream, byteOrder);
            writeExifSegment(exifDataOutputStream);
            byte[] exifBytes = ((ByteArrayOutputStream) exifDataOutputStream.mOutputStream).toByteArray();
            dataOutputStream.write(exifBytes);
            CRC32 crc = new CRC32();
            crc.update(exifBytes, 4, exifBytes.length - 4);
            dataOutputStream.writeInt((int) crc.getValue());
            closeQuietly(exifByteArrayOutputStream);
            copy(dataInputStream, dataOutputStream);
        } catch (Throwable th) {
            closeQuietly(exifByteArrayOutputStream);
            throw th;
        }
    }

    private void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) {
        InputStream inputStream2 = inputStream;
        OutputStream outputStream2 = outputStream;
        if (DEBUG) {
            Log.d(TAG, "saveWebpAttributes starting with (inputStream: " + inputStream2 + ", outputStream: " + outputStream2 + ")");
        }
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteOrderedDataInputStream totalInputStream = new ByteOrderedDataInputStream(inputStream2, byteOrder);
        ByteOrderedDataOutputStream totalOutputStream = new ByteOrderedDataOutputStream(outputStream2, byteOrder);
        byte[] bArr = WEBP_SIGNATURE_1;
        copy(totalInputStream, totalOutputStream, bArr.length);
        byte[] bArr2 = WEBP_SIGNATURE_2;
        totalInputStream.skipBytes(bArr2.length + 4);
        ByteArrayOutputStream nonHeaderByteArrayOutputStream = null;
        try {
            nonHeaderByteArrayOutputStream = new ByteArrayOutputStream();
            ByteOrderedDataOutputStream nonHeaderOutputStream = new ByteOrderedDataOutputStream(nonHeaderByteArrayOutputStream, byteOrder);
            int i = this.mOffsetToExifData;
            if (i != 0) {
                copy(totalInputStream, nonHeaderOutputStream, ((i - ((bArr.length + 4) + bArr2.length)) - 4) - 4);
                totalInputStream.skipBytes(4);
                totalInputStream.skipBytes(totalInputStream.readInt());
                writeExifSegment(nonHeaderOutputStream);
            } else {
                byte[] firstChunkType = new byte[4];
                if (totalInputStream.read(firstChunkType) == firstChunkType.length) {
                    byte[] bArr3 = WEBP_CHUNK_TYPE_VP8X;
                    boolean z = false;
                    if (Arrays.equals(firstChunkType, bArr3)) {
                        int size = totalInputStream.readInt();
                        byte[] data = new byte[(size % 2 == 1 ? size + 1 : size)];
                        totalInputStream.read(data);
                        data[0] = (byte) (8 | data[0]);
                        if (((data[0] >> 1) & 1) == 1) {
                            z = true;
                        }
                        boolean containsAnimation = z;
                        nonHeaderOutputStream.write(bArr3);
                        nonHeaderOutputStream.writeInt(size);
                        nonHeaderOutputStream.write(data);
                        if (containsAnimation) {
                            copyChunksUpToGivenChunkType(totalInputStream, nonHeaderOutputStream, WEBP_CHUNK_TYPE_ANIM, (byte[]) null);
                            while (true) {
                                byte[] type = new byte[4];
                                int read = inputStream2.read(type);
                                if (!Arrays.equals(type, WEBP_CHUNK_TYPE_ANMF)) {
                                    break;
                                }
                                copyWebPChunk(totalInputStream, nonHeaderOutputStream, type);
                            }
                            writeExifSegment(nonHeaderOutputStream);
                        } else {
                            copyChunksUpToGivenChunkType(totalInputStream, nonHeaderOutputStream, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                            writeExifSegment(nonHeaderOutputStream);
                        }
                    } else {
                        byte[] bArr4 = WEBP_CHUNK_TYPE_VP8;
                        if (Arrays.equals(firstChunkType, bArr4) || Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                            int size2 = totalInputStream.readInt();
                            int bytesToRead = size2;
                            if (size2 % 2 == 1) {
                                bytesToRead++;
                            }
                            int widthAndHeight = 0;
                            int width = 0;
                            int height = 0;
                            int alpha = 0;
                            byte[] vp8Frame = new byte[3];
                            if (Arrays.equals(firstChunkType, bArr4)) {
                                totalInputStream.read(vp8Frame);
                                byte[] vp8Signature = new byte[3];
                                if (totalInputStream.read(vp8Signature) != vp8Signature.length || !Arrays.equals(WEBP_VP8_SIGNATURE, vp8Signature)) {
                                    throw new IOException("Encountered error while checking VP8 signature");
                                }
                                widthAndHeight = totalInputStream.readInt();
                                width = (widthAndHeight << 18) >> 18;
                                height = (widthAndHeight << 2) >> 18;
                                bytesToRead -= (vp8Frame.length + vp8Signature.length) + 4;
                            } else if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                                if (totalInputStream.readByte() == 47) {
                                    widthAndHeight = totalInputStream.readInt();
                                    width = ((widthAndHeight << 18) >> 18) + 1;
                                    height = ((widthAndHeight << 4) >> 18) + 1;
                                    alpha = widthAndHeight & 8;
                                    bytesToRead -= 5;
                                } else {
                                    throw new IOException("Encountered error while checking VP8L signature");
                                }
                            }
                            nonHeaderOutputStream.write(bArr3);
                            nonHeaderOutputStream.writeInt(10);
                            byte[] data2 = new byte[10];
                            data2[0] = (byte) (data2[0] | 8);
                            data2[0] = (byte) (data2[0] | (alpha << 4));
                            int width2 = width - 1;
                            int height2 = height - 1;
                            data2[4] = (byte) width2;
                            data2[5] = (byte) (width2 >> 8);
                            data2[6] = (byte) (width2 >> 16);
                            data2[7] = (byte) height2;
                            data2[8] = (byte) (height2 >> 8);
                            data2[9] = (byte) (height2 >> 16);
                            nonHeaderOutputStream.write(data2);
                            nonHeaderOutputStream.write(firstChunkType);
                            nonHeaderOutputStream.writeInt(size2);
                            if (Arrays.equals(firstChunkType, bArr4)) {
                                nonHeaderOutputStream.write(vp8Frame);
                                nonHeaderOutputStream.write(WEBP_VP8_SIGNATURE);
                                nonHeaderOutputStream.writeInt(widthAndHeight);
                            } else if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                                nonHeaderOutputStream.write(47);
                                nonHeaderOutputStream.writeInt(widthAndHeight);
                            }
                            copy(totalInputStream, nonHeaderOutputStream, bytesToRead);
                            writeExifSegment(nonHeaderOutputStream);
                        }
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing WebP chunk type");
                }
            }
            copy(totalInputStream, nonHeaderOutputStream);
            int size3 = nonHeaderByteArrayOutputStream.size();
            byte[] bArr5 = WEBP_SIGNATURE_2;
            totalOutputStream.writeInt(size3 + bArr5.length);
            totalOutputStream.write(bArr5);
            nonHeaderByteArrayOutputStream.writeTo(totalOutputStream);
            closeQuietly(nonHeaderByteArrayOutputStream);
        } catch (Exception e) {
            throw new IOException("Failed to save WebP file", e);
        } catch (Throwable th) {
            closeQuietly(nonHeaderByteArrayOutputStream);
            throw th;
        }
    }

    private void copyChunksUpToGivenChunkType(ByteOrderedDataInputStream inputStream, ByteOrderedDataOutputStream outputStream, byte[] firstGivenType, byte[] secondGivenType) {
        String str;
        while (true) {
            byte[] type = new byte[4];
            if (inputStream.read(type) != type.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Encountered invalid length while copying WebP chunks up tochunk type ");
                Charset charset = ASCII;
                sb.append(new String(firstGivenType, charset));
                if (secondGivenType == null) {
                    str = "";
                } else {
                    str = " or " + new String(secondGivenType, charset);
                }
                sb.append(str);
                throw new IOException(sb.toString());
            }
            copyWebPChunk(inputStream, outputStream, type);
            if (Arrays.equals(type, firstGivenType)) {
                return;
            }
            if (secondGivenType != null && Arrays.equals(type, secondGivenType)) {
                return;
            }
        }
    }

    private void copyWebPChunk(ByteOrderedDataInputStream inputStream, ByteOrderedDataOutputStream outputStream, byte[] type) {
        int size = inputStream.readInt();
        outputStream.write(type);
        outputStream.writeInt(size);
        copy(inputStream, outputStream, size % 2 == 1 ? size + 1 : size);
    }

    private void readExifSegment(byte[] exifBytes, int imageType) {
        ByteOrderedDataInputStream dataInputStream = new ByteOrderedDataInputStream(exifBytes);
        parseTiffHeaders(dataInputStream, exifBytes.length);
        readImageFileDirectory(dataInputStream, imageType);
    }

    private void addDefaultValuesForCompatibility() {
        String valueOfDateTimeOriginal = getAttribute(TAG_DATETIME_ORIGINAL);
        if (valueOfDateTimeOriginal != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(valueOfDateTimeOriginal));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream dataInputStream) {
        short byteOrder = dataInputStream.readShort();
        switch (byteOrder) {
            case 18761:
                if (DEBUG) {
                    Log.d(TAG, "readExifSegment: Byte Align II");
                }
                return ByteOrder.LITTLE_ENDIAN;
            case 19789:
                if (DEBUG) {
                    Log.d(TAG, "readExifSegment: Byte Align MM");
                }
                return ByteOrder.BIG_ENDIAN;
            default:
                throw new IOException("Invalid byte order: " + Integer.toHexString(byteOrder));
        }
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream dataInputStream, int exifBytesLength) {
        ByteOrder readByteOrder = readByteOrder(dataInputStream);
        this.mExifByteOrder = readByteOrder;
        dataInputStream.setByteOrder(readByteOrder);
        int startCode = dataInputStream.readUnsignedShort();
        int i = this.mMimeType;
        if (i == 7 || i == 10 || startCode == 42) {
            int firstIfdOffset = dataInputStream.readInt();
            if (firstIfdOffset < 8 || firstIfdOffset >= exifBytesLength) {
                throw new IOException("Invalid first Ifd offset: " + firstIfdOffset);
            }
            int firstIfdOffset2 = firstIfdOffset - 8;
            if (firstIfdOffset2 > 0 && dataInputStream.skipBytes(firstIfdOffset2) != firstIfdOffset2) {
                throw new IOException("Couldn't jump to first Ifd: " + firstIfdOffset2);
            }
            return;
        }
        throw new IOException("Invalid start code: " + Integer.toHexString(startCode));
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x016e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readImageFileDirectory(androidx.exifinterface.media.ExifInterface.ByteOrderedDataInputStream r31, int r32) {
        /*
            r30 = this;
            r0 = r30
            r1 = r31
            r2 = r32
            java.util.Set<java.lang.Integer> r3 = r0.mAttributesOffsets
            int r4 = r1.mPosition
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.add(r4)
            int r3 = r1.mPosition
            r4 = 2
            int r3 = r3 + r4
            int r5 = r1.mLength
            if (r3 <= r5) goto L_0x001a
            return
        L_0x001a:
            short r3 = r31.readShort()
            boolean r5 = DEBUG
            java.lang.String r6 = "ExifInterface"
            if (r5 == 0) goto L_0x0038
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "numberOfDirectoryEntry: "
            r5.append(r7)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
        L_0x0038:
            int r5 = r1.mPosition
            int r7 = r3 * 12
            int r5 = r5 + r7
            int r7 = r1.mLength
            if (r5 > r7) goto L_0x0426
            if (r3 > 0) goto L_0x0047
            r26 = r3
            goto L_0x0428
        L_0x0047:
            r5 = 0
        L_0x0048:
            r9 = 0
            r10 = 5
            if (r5 >= r3) goto L_0x0397
            int r13 = r31.readUnsignedShort()
            int r14 = r31.readUnsignedShort()
            int r15 = r31.readInt()
            int r7 = r31.peek()
            long r7 = (long) r7
            r18 = 4
            long r7 = r7 + r18
            java.util.HashMap<java.lang.Integer, androidx.exifinterface.media.ExifInterface$ExifTag>[] r20 = sExifTagMapsForReading
            r12 = r20[r2]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r13)
            java.lang.Object r4 = r12.get(r4)
            androidx.exifinterface.media.ExifInterface$ExifTag r4 = (androidx.exifinterface.media.ExifInterface.ExifTag) r4
            boolean r12 = DEBUG
            r11 = 3
            if (r12 == 0) goto L_0x00a5
            java.lang.Object[] r10 = new java.lang.Object[r10]
            java.lang.Integer r23 = java.lang.Integer.valueOf(r32)
            r10[r9] = r23
            java.lang.Integer r23 = java.lang.Integer.valueOf(r13)
            r20 = 1
            r10[r20] = r23
            if (r4 == 0) goto L_0x0089
            java.lang.String r9 = r4.name
            goto L_0x008a
        L_0x0089:
            r9 = 0
        L_0x008a:
            r22 = 2
            r10[r22] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r14)
            r10[r11] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r15)
            r21 = 4
            r10[r21] = r9
            java.lang.String r9 = "ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d"
            java.lang.String r9 = java.lang.String.format(r9, r10)
            android.util.Log.d(r6, r9)
        L_0x00a5:
            r9 = 0
            r24 = 0
            if (r4 != 0) goto L_0x00cf
            if (r12 == 0) goto L_0x00c8
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r26 = r3
            java.lang.String r3 = "Skip the tag entry since tag number is not defined: "
            r11.append(r3)
            r11.append(r13)
            java.lang.String r3 = r11.toString()
            android.util.Log.d(r6, r3)
            r27 = r9
            r11 = r13
            goto L_0x0163
        L_0x00c8:
            r26 = r3
            r27 = r9
            r11 = r13
            goto L_0x0163
        L_0x00cf:
            r26 = r3
            if (r14 <= 0) goto L_0x014a
            int[] r3 = IFD_FORMAT_BYTES_PER_FORMAT
            int r11 = r3.length
            if (r14 < r11) goto L_0x00dd
            r27 = r9
            r11 = r13
            goto L_0x014d
        L_0x00dd:
            boolean r11 = r4.isFormatCompatible(r14)
            if (r11 != 0) goto L_0x010f
            if (r12 == 0) goto L_0x010b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r11 = "Skip the tag entry since data format ("
            r3.append(r11)
            java.lang.String[] r11 = IFD_FORMAT_NAMES
            r11 = r11[r14]
            r3.append(r11)
            java.lang.String r11 = ") is unexpected for tag: "
            r3.append(r11)
            java.lang.String r11 = r4.name
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
            r27 = r9
            r11 = r13
            goto L_0x0163
        L_0x010b:
            r27 = r9
            r11 = r13
            goto L_0x0163
        L_0x010f:
            r11 = 7
            if (r14 != r11) goto L_0x0114
            int r14 = r4.primaryFormat
        L_0x0114:
            r27 = r9
            long r9 = (long) r15
            r3 = r3[r14]
            r11 = r13
            r29 = r14
            long r13 = (long) r3
            long r9 = r9 * r13
            r13 = 0
            int r3 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r3 < 0) goto L_0x0131
            r13 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r3 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r3 <= 0) goto L_0x012c
            goto L_0x0131
        L_0x012c:
            r24 = 1
            r14 = r29
            goto L_0x0165
        L_0x0131:
            if (r12 == 0) goto L_0x0147
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r13 = "Skip the tag entry since the number of components is invalid: "
            r3.append(r13)
            r3.append(r15)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
        L_0x0147:
            r14 = r29
            goto L_0x0165
        L_0x014a:
            r27 = r9
            r11 = r13
        L_0x014d:
            if (r12 == 0) goto L_0x0163
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r9 = "Skip the tag entry since data format is invalid: "
            r3.append(r9)
            r3.append(r14)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
        L_0x0163:
            r9 = r27
        L_0x0165:
            if (r24 != 0) goto L_0x016e
            r1.seek(r7)
            r27 = r5
            goto L_0x038d
        L_0x016e:
            int r3 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            java.lang.String r13 = "Compression"
            if (r3 <= 0) goto L_0x022f
            int r3 = r31.readInt()
            if (r12 == 0) goto L_0x0194
            r27 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r28 = r14
            java.lang.String r14 = "seek to data offset: "
            r5.append(r14)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
            goto L_0x0198
        L_0x0194:
            r27 = r5
            r28 = r14
        L_0x0198:
            int r5 = r0.mMimeType
            r14 = 7
            if (r5 != r14) goto L_0x0202
            java.lang.String r5 = r4.name
            java.lang.String r14 = "MakerNote"
            boolean r5 = r14.equals(r5)
            if (r5 == 0) goto L_0x01ae
            r0.mOrfMakerNoteOffset = r3
            r25 = r4
            r18 = r15
            goto L_0x0206
        L_0x01ae:
            r5 = 6
            if (r2 != r5) goto L_0x01fd
            java.lang.String r14 = r4.name
            java.lang.String r5 = "ThumbnailImage"
            boolean r5 = r5.equals(r14)
            if (r5 == 0) goto L_0x01f8
            r0.mOrfThumbnailOffset = r3
            r0.mOrfThumbnailLength = r15
            java.nio.ByteOrder r5 = r0.mExifByteOrder
            r14 = 6
            androidx.exifinterface.media.ExifInterface$ExifAttribute r5 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createUShort((int) r14, (java.nio.ByteOrder) r5)
            int r14 = r0.mOrfThumbnailOffset
            r18 = r15
            long r14 = (long) r14
            java.nio.ByteOrder r2 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r2 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r14, (java.nio.ByteOrder) r2)
            int r14 = r0.mOrfThumbnailLength
            long r14 = (long) r14
            r25 = r4
            java.nio.ByteOrder r4 = r0.mExifByteOrder
            androidx.exifinterface.media.ExifInterface$ExifAttribute r4 = androidx.exifinterface.media.ExifInterface.ExifAttribute.createULong((long) r14, (java.nio.ByteOrder) r4)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r14 = r0.mAttributes
            r15 = 4
            r14 = r14[r15]
            r14.put(r13, r5)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r14 = r0.mAttributes
            r14 = r14[r15]
            java.lang.String r15 = "JPEGInterchangeFormat"
            r14.put(r15, r2)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r14 = r0.mAttributes
            r15 = 4
            r14 = r14[r15]
            java.lang.String r15 = "JPEGInterchangeFormatLength"
            r14.put(r15, r4)
            goto L_0x0206
        L_0x01f8:
            r25 = r4
            r18 = r15
            goto L_0x0206
        L_0x01fd:
            r25 = r4
            r18 = r15
            goto L_0x0206
        L_0x0202:
            r25 = r4
            r18 = r15
        L_0x0206:
            long r4 = (long) r3
            long r4 = r4 + r9
            int r2 = r1.mLength
            long r14 = (long) r2
            int r2 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r2 > 0) goto L_0x0214
            long r4 = (long) r3
            r1.seek(r4)
            goto L_0x0237
        L_0x0214:
            if (r12 == 0) goto L_0x022a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "Skip the tag entry since data offset is invalid: "
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r6, r2)
        L_0x022a:
            r1.seek(r7)
            goto L_0x038d
        L_0x022f:
            r25 = r4
            r27 = r5
            r28 = r14
            r18 = r15
        L_0x0237:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r2 = sExifPointerTagMap
            java.lang.Integer r3 = java.lang.Integer.valueOf(r11)
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            if (r12 == 0) goto L_0x0261
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "nextIfdType: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r4 = " byteCount: "
            r3.append(r4)
            r3.append(r9)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
        L_0x0261:
            if (r2 == 0) goto L_0x0310
            r3 = -1
            switch(r28) {
                case 3: goto L_0x027a;
                case 4: goto L_0x0275;
                case 8: goto L_0x026f;
                case 9: goto L_0x0269;
                case 13: goto L_0x0269;
                default: goto L_0x0268;
            }
        L_0x0268:
            goto L_0x0280
        L_0x0269:
            int r5 = r31.readInt()
            long r3 = (long) r5
            goto L_0x0280
        L_0x026f:
            short r5 = r31.readShort()
            long r3 = (long) r5
            goto L_0x0280
        L_0x0275:
            long r3 = r31.readUnsignedInt()
            goto L_0x0280
        L_0x027a:
            int r5 = r31.readUnsignedShort()
            long r3 = (long) r5
        L_0x0280:
            if (r12 == 0) goto L_0x029e
            r5 = 2
            java.lang.Object[] r13 = new java.lang.Object[r5]
            java.lang.Long r14 = java.lang.Long.valueOf(r3)
            r15 = 0
            r13[r15] = r14
            r14 = r25
            java.lang.String r15 = r14.name
            r19 = 1
            r13[r19] = r15
            java.lang.String r15 = "Offset: %d, tagName: %s"
            java.lang.String r13 = java.lang.String.format(r15, r13)
            android.util.Log.d(r6, r13)
            goto L_0x02a1
        L_0x029e:
            r14 = r25
            r5 = 2
        L_0x02a1:
            r15 = 0
            int r13 = (r3 > r15 ? 1 : (r3 == r15 ? 0 : -1))
            if (r13 <= 0) goto L_0x02f5
            int r13 = r1.mLength
            r22 = r6
            long r5 = (long) r13
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L_0x02f3
            java.util.Set<java.lang.Integer> r5 = r0.mAttributesOffsets
            int r6 = (int) r3
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            boolean r5 = r5.contains(r6)
            if (r5 != 0) goto L_0x02ca
            r1.seek(r3)
            int r5 = r2.intValue()
            r0.readImageFileDirectory(r1, r5)
            r6 = r22
            goto L_0x030b
        L_0x02ca:
            if (r12 == 0) goto L_0x02f0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Skip jump into the IFD since it has already been read: IfdType "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r6 = " (at "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r6 = ")"
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = r22
            android.util.Log.d(r6, r5)
            goto L_0x030b
        L_0x02f0:
            r6 = r22
            goto L_0x030b
        L_0x02f3:
            r6 = r22
        L_0x02f5:
            if (r12 == 0) goto L_0x030b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r12 = "Skip jump into the IFD since its offset is invalid: "
            r5.append(r12)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
        L_0x030b:
            r1.seek(r7)
            goto L_0x038d
        L_0x0310:
            r14 = r25
            int r3 = r31.peek()
            int r4 = r0.mOffsetToExifData
            int r3 = r3 + r4
            int r4 = (int) r9
            byte[] r4 = new byte[r4]
            r1.readFully(r4)
            androidx.exifinterface.media.ExifInterface$ExifAttribute r5 = new androidx.exifinterface.media.ExifInterface$ExifAttribute
            r21 = r9
            long r9 = (long) r3
            r12 = r18
            r15 = r5
            r16 = r28
            r17 = r12
            r18 = r9
            r20 = r4
            r15.<init>(r16, r17, r18, r20)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r9 = r0.mAttributes
            r9 = r9[r32]
            java.lang.String r10 = r14.name
            r9.put(r10, r5)
            java.lang.String r9 = r14.name
            java.lang.String r10 = "DNGVersion"
            boolean r9 = r10.equals(r9)
            if (r9 == 0) goto L_0x0348
            r9 = 3
            r0.mMimeType = r9
        L_0x0348:
            java.lang.String r9 = r14.name
            java.lang.String r10 = "Make"
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L_0x035c
            java.lang.String r9 = r14.name
            java.lang.String r10 = "Model"
            boolean r9 = r10.equals(r9)
            if (r9 == 0) goto L_0x036a
        L_0x035c:
            java.nio.ByteOrder r9 = r0.mExifByteOrder
            java.lang.String r9 = r5.getStringValue(r9)
            java.lang.String r10 = "PENTAX"
            boolean r9 = r9.contains(r10)
            if (r9 != 0) goto L_0x037d
        L_0x036a:
            java.lang.String r9 = r14.name
            boolean r9 = r13.equals(r9)
            if (r9 == 0) goto L_0x0381
            java.nio.ByteOrder r9 = r0.mExifByteOrder
            int r9 = r5.getIntValue(r9)
            r10 = 65535(0xffff, float:9.1834E-41)
            if (r9 != r10) goto L_0x0381
        L_0x037d:
            r9 = 8
            r0.mMimeType = r9
        L_0x0381:
            int r9 = r31.peek()
            long r9 = (long) r9
            int r9 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r9 == 0) goto L_0x038d
            r1.seek(r7)
        L_0x038d:
            int r5 = r27 + 1
            short r5 = (short) r5
            r2 = r32
            r3 = r26
            r4 = 2
            goto L_0x0048
        L_0x0397:
            r26 = r3
            r27 = r5
            int r2 = r31.peek()
            r3 = 4
            int r2 = r2 + r3
            int r3 = r1.mLength
            if (r2 > r3) goto L_0x0425
            int r2 = r31.readInt()
            boolean r3 = DEBUG
            if (r3 == 0) goto L_0x03c0
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            r7 = 0
            r4[r7] = r5
            java.lang.String r5 = "nextIfdOffset: %d"
            java.lang.String r4 = java.lang.String.format(r5, r4)
            android.util.Log.d(r6, r4)
        L_0x03c0:
            long r4 = (long) r2
            r7 = 0
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 <= 0) goto L_0x040f
            int r4 = r1.mLength
            if (r2 >= r4) goto L_0x040f
            java.util.Set<java.lang.Integer> r4 = r0.mAttributesOffsets
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            boolean r4 = r4.contains(r5)
            if (r4 != 0) goto L_0x03f8
            long r3 = (long) r2
            r1.seek(r3)
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r3 = r0.mAttributes
            r4 = 4
            r3 = r3[r4]
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x03ea
            r0.readImageFileDirectory(r1, r4)
            goto L_0x0425
        L_0x03ea:
            java.util.HashMap<java.lang.String, androidx.exifinterface.media.ExifInterface$ExifAttribute>[] r3 = r0.mAttributes
            r3 = r3[r10]
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x0425
            r0.readImageFileDirectory(r1, r10)
            goto L_0x0425
        L_0x03f8:
            if (r3 == 0) goto L_0x0425
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Stop reading file since re-reading an IFD may cause an infinite loop: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
            goto L_0x0425
        L_0x040f:
            if (r3 == 0) goto L_0x0425
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Stop reading file since a wrong offset may cause an infinite loop: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.d(r6, r3)
        L_0x0425:
            return
        L_0x0426:
            r26 = r3
        L_0x0428:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.readImageFileDirectory(androidx.exifinterface.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream in, int imageType) {
        ExifAttribute imageLengthAttribute = this.mAttributes[imageType].get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = this.mAttributes[imageType].get(TAG_IMAGE_WIDTH);
        if (imageLengthAttribute == null || imageWidthAttribute == null) {
            ExifAttribute jpegInterchangeFormatAttribute = this.mAttributes[imageType].get(TAG_JPEG_INTERCHANGE_FORMAT);
            ExifAttribute jpegInterchangeFormatLengthAttribute = this.mAttributes[imageType].get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            if (jpegInterchangeFormatAttribute != null && jpegInterchangeFormatLengthAttribute != null) {
                int jpegInterchangeFormat = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
                int jpegInterchangeFormatLength = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
                in.seek((long) jpegInterchangeFormat);
                byte[] jpegBytes = new byte[jpegInterchangeFormatLength];
                in.read(jpegBytes);
                getJpegAttributes(new ByteOrderedDataInputStream(jpegBytes), jpegInterchangeFormat, imageType);
            }
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream in) {
        HashMap thumbnailData = this.mAttributes[4];
        ExifAttribute compressionAttribute = thumbnailData.get(TAG_COMPRESSION);
        if (compressionAttribute != null) {
            int intValue = compressionAttribute.getIntValue(this.mExifByteOrder);
            this.mThumbnailCompression = intValue;
            switch (intValue) {
                case 1:
                case 7:
                    if (isSupportedDataType(thumbnailData)) {
                        handleThumbnailFromStrips(in, thumbnailData);
                        return;
                    }
                    return;
                case 6:
                    handleThumbnailFromJfif(in, thumbnailData);
                    return;
                default:
                    return;
            }
        } else {
            this.mThumbnailCompression = 6;
            handleThumbnailFromJfif(in, thumbnailData);
        }
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream in, HashMap thumbnailData) {
        ExifAttribute jpegInterchangeFormatAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute jpegInterchangeFormatLengthAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (jpegInterchangeFormatAttribute != null && jpegInterchangeFormatLengthAttribute != null) {
            int thumbnailOffset = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
            int thumbnailLength = jpegInterchangeFormatLengthAttribute.getIntValue(this.mExifByteOrder);
            if (this.mMimeType == 7) {
                thumbnailOffset += this.mOrfMakerNoteOffset;
            }
            int thumbnailLength2 = Math.min(thumbnailLength, in.getLength() - thumbnailOffset);
            if (thumbnailOffset > 0 && thumbnailLength2 > 0) {
                this.mHasThumbnail = true;
                if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                    byte[] thumbnailBytes = new byte[thumbnailLength2];
                    in.skip((long) thumbnailOffset);
                    in.read(thumbnailBytes);
                    this.mThumbnailBytes = thumbnailBytes;
                }
                this.mThumbnailOffset = thumbnailOffset;
                this.mThumbnailLength = thumbnailLength2;
            }
            if (DEBUG) {
                Log.d(TAG, "Setting thumbnail attributes with offset: " + thumbnailOffset + ", length: " + thumbnailLength2);
            }
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream in, HashMap thumbnailData) {
        long totalStripByteCount;
        ByteOrderedDataInputStream byteOrderedDataInputStream = in;
        HashMap hashMap = thumbnailData;
        ExifAttribute stripOffsetsAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        ExifAttribute stripByteCountsAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (stripOffsetsAttribute == null || stripByteCountsAttribute == null) {
            ExifAttribute exifAttribute = stripByteCountsAttribute;
            return;
        }
        long[] stripOffsets = convertToLongArray(stripOffsetsAttribute.getValue(this.mExifByteOrder));
        long[] stripByteCounts = convertToLongArray(stripByteCountsAttribute.getValue(this.mExifByteOrder));
        if (stripOffsets == null) {
            ExifAttribute exifAttribute2 = stripByteCountsAttribute;
        } else if (stripOffsets.length == 0) {
            ExifAttribute exifAttribute3 = stripOffsetsAttribute;
            ExifAttribute exifAttribute4 = stripByteCountsAttribute;
        } else {
            if (stripByteCounts == null) {
                ExifAttribute exifAttribute5 = stripByteCountsAttribute;
            } else if (stripByteCounts.length == 0) {
                ExifAttribute exifAttribute6 = stripOffsetsAttribute;
                ExifAttribute exifAttribute7 = stripByteCountsAttribute;
            } else if (stripOffsets.length != stripByteCounts.length) {
                Log.w(TAG, "stripOffsets and stripByteCounts should have same length.");
                return;
            } else {
                long totalStripByteCount2 = 0;
                for (long byteCount : stripByteCounts) {
                    totalStripByteCount2 += byteCount;
                }
                byte[] totalStripBytes = new byte[((int) totalStripByteCount2)];
                int bytesRead = 0;
                int bytesAdded = 0;
                int i = 1;
                this.mAreThumbnailStripsConsecutive = true;
                this.mHasThumbnailStrips = true;
                this.mHasThumbnail = true;
                int i2 = 0;
                while (i2 < stripOffsets.length) {
                    ExifAttribute stripOffsetsAttribute2 = stripOffsetsAttribute;
                    int stripOffset = (int) stripOffsets[i2];
                    ExifAttribute stripByteCountsAttribute2 = stripByteCountsAttribute;
                    int stripByteCount = (int) stripByteCounts[i2];
                    if (i2 < stripOffsets.length - i) {
                        totalStripByteCount = totalStripByteCount2;
                        if (((long) (stripOffset + stripByteCount)) != stripOffsets[i2 + 1]) {
                            this.mAreThumbnailStripsConsecutive = false;
                        }
                    } else {
                        totalStripByteCount = totalStripByteCount2;
                    }
                    int bytesToSkip = stripOffset - bytesRead;
                    if (bytesToSkip < 0) {
                        Log.d(TAG, "Invalid strip offset value");
                        return;
                    }
                    int i3 = i2;
                    if (byteOrderedDataInputStream.skip((long) bytesToSkip) != ((long) bytesToSkip)) {
                        Log.d(TAG, "Failed to skip " + bytesToSkip + " bytes.");
                        return;
                    }
                    int bytesRead2 = bytesRead + bytesToSkip;
                    byte[] stripBytes = new byte[stripByteCount];
                    if (byteOrderedDataInputStream.read(stripBytes) != stripByteCount) {
                        Log.d(TAG, "Failed to read " + stripByteCount + " bytes.");
                        return;
                    }
                    bytesRead = bytesRead2 + stripByteCount;
                    System.arraycopy(stripBytes, 0, totalStripBytes, bytesAdded, stripBytes.length);
                    bytesAdded += stripBytes.length;
                    i2 = i3 + 1;
                    HashMap hashMap2 = thumbnailData;
                    stripOffsetsAttribute = stripOffsetsAttribute2;
                    stripByteCountsAttribute = stripByteCountsAttribute2;
                    totalStripByteCount2 = totalStripByteCount;
                    i = 1;
                }
                ExifAttribute exifAttribute8 = stripByteCountsAttribute;
                long j = totalStripByteCount2;
                int i4 = i2;
                this.mThumbnailBytes = totalStripBytes;
                if (this.mAreThumbnailStripsConsecutive) {
                    this.mThumbnailOffset = (int) stripOffsets[0];
                    this.mThumbnailLength = totalStripBytes.length;
                    return;
                }
                return;
            }
            Log.w(TAG, "stripByteCounts should not be null or have zero length.");
            return;
        }
        Log.w(TAG, "stripOffsets should not be null or have zero length.");
    }

    private boolean isSupportedDataType(HashMap thumbnailData) {
        ExifAttribute photometricInterpretationAttribute;
        int photometricInterpretationValue;
        ExifAttribute bitsPerSampleAttribute = (ExifAttribute) thumbnailData.get(TAG_BITS_PER_SAMPLE);
        if (bitsPerSampleAttribute != null) {
            int[] bitsPerSampleValue = (int[]) bitsPerSampleAttribute.getValue(this.mExifByteOrder);
            int[] iArr = BITS_PER_SAMPLE_RGB;
            if (Arrays.equals(iArr, bitsPerSampleValue)) {
                return true;
            }
            if (this.mMimeType == 3 && (photometricInterpretationAttribute = (ExifAttribute) thumbnailData.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((photometricInterpretationValue = photometricInterpretationAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(bitsPerSampleValue, BITS_PER_SAMPLE_GREYSCALE_2)) || (photometricInterpretationValue == 6 && Arrays.equals(bitsPerSampleValue, iArr)))) {
                return true;
            }
        }
        if (!DEBUG) {
            return false;
        }
        Log.d(TAG, "Unsupported data type value");
        return false;
    }

    private boolean isThumbnail(HashMap map) {
        ExifAttribute imageLengthAttribute = (ExifAttribute) map.get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = (ExifAttribute) map.get(TAG_IMAGE_WIDTH);
        if (imageLengthAttribute == null || imageWidthAttribute == null) {
            return false;
        }
        int imageLengthValue = imageLengthAttribute.getIntValue(this.mExifByteOrder);
        int imageWidthValue = imageWidthAttribute.getIntValue(this.mExifByteOrder);
        if (imageLengthValue > 512 || imageWidthValue > 512) {
            return false;
        }
        return true;
    }

    private void validateImages() {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute pixelXDimAttribute = this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute pixelYDimAttribute = this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (!(pixelXDimAttribute == null || pixelYDimAttribute == null)) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, pixelXDimAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, pixelYDimAttribute);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            HashMap<String, ExifAttribute>[] hashMapArr = this.mAttributes;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap<>();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream in, int imageType) {
        ExifAttribute defaultCropSizeXAttribute;
        ExifAttribute defaultCropSizeYAttribute;
        ExifAttribute defaultCropSizeAttribute = this.mAttributes[imageType].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute topBorderAttribute = this.mAttributes[imageType].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute leftBorderAttribute = this.mAttributes[imageType].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute bottomBorderAttribute = this.mAttributes[imageType].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute rightBorderAttribute = this.mAttributes[imageType].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (defaultCropSizeAttribute != null) {
            if (defaultCropSizeAttribute.format == 5) {
                Rational[] defaultCropSizeValue = (Rational[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                if (defaultCropSizeValue == null || defaultCropSizeValue.length != 2) {
                    Log.w(TAG, "Invalid crop size values. cropSize=" + Arrays.toString(defaultCropSizeValue));
                    return;
                }
                defaultCropSizeXAttribute = ExifAttribute.createURational(defaultCropSizeValue[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createURational(defaultCropSizeValue[1], this.mExifByteOrder);
            } else {
                int[] defaultCropSizeValue2 = (int[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                if (defaultCropSizeValue2 == null || defaultCropSizeValue2.length != 2) {
                    Log.w(TAG, "Invalid crop size values. cropSize=" + Arrays.toString(defaultCropSizeValue2));
                    return;
                }
                defaultCropSizeXAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[1], this.mExifByteOrder);
            }
            this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, defaultCropSizeXAttribute);
            this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, defaultCropSizeYAttribute);
            ExifAttribute exifAttribute = defaultCropSizeAttribute;
        } else if (topBorderAttribute == null || leftBorderAttribute == null || bottomBorderAttribute == null || rightBorderAttribute == null) {
            retrieveJpegImageSize(in, imageType);
        } else {
            int topBorderValue = topBorderAttribute.getIntValue(this.mExifByteOrder);
            int bottomBorderValue = bottomBorderAttribute.getIntValue(this.mExifByteOrder);
            int rightBorderValue = rightBorderAttribute.getIntValue(this.mExifByteOrder);
            int leftBorderValue = leftBorderAttribute.getIntValue(this.mExifByteOrder);
            if (bottomBorderValue <= topBorderValue || rightBorderValue <= leftBorderValue) {
                return;
            }
            ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(bottomBorderValue - topBorderValue, this.mExifByteOrder);
            ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(rightBorderValue - leftBorderValue, this.mExifByteOrder);
            ExifAttribute exifAttribute2 = defaultCropSizeAttribute;
            this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
            this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
        }
    }

    private int writeExifSegment(ByteOrderedDataOutputStream dataOutputStream) {
        Iterator<Map.Entry<String, ExifAttribute>> it;
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = dataOutputStream;
        ExifTag[][] exifTagArr = EXIF_TAGS;
        int[] ifdOffsets = new int[exifTagArr.length];
        int[] ifdDataSizes = new int[exifTagArr.length];
        for (ExifTag tag : EXIF_POINTER_TAGS) {
            removeAttribute(tag.name);
        }
        removeAttribute(JPEG_INTERCHANGE_FORMAT_TAG.name);
        removeAttribute(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name);
        for (int ifdType = 0; ifdType < EXIF_TAGS.length; ifdType++) {
            for (Object obj : this.mAttributes[ifdType].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[ifdType].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        int i = 2;
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0, this.mExifByteOrder));
        }
        int i2 = 4;
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong(0, this.mExifByteOrder));
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name, ExifAttribute.createULong((long) this.mThumbnailLength, this.mExifByteOrder));
        }
        for (int i3 = 0; i3 < EXIF_TAGS.length; i3++) {
            int sum = 0;
            for (Map.Entry<String, ExifAttribute> entry2 : this.mAttributes[i3].entrySet()) {
                int size = entry2.getValue().size();
                if (size > 4) {
                    sum += size;
                }
            }
            ifdDataSizes[i3] = ifdDataSizes[i3] + sum;
        }
        int position = 8;
        for (int ifdType2 = 0; ifdType2 < EXIF_TAGS.length; ifdType2++) {
            if (!this.mAttributes[ifdType2].isEmpty()) {
                ifdOffsets[ifdType2] = position;
                position += (this.mAttributes[ifdType2].size() * 12) + 2 + 4 + ifdDataSizes[ifdType2];
            }
        }
        if (this.mHasThumbnail != 0) {
            int thumbnailOffset = position;
            this.mAttributes[4].put(JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong((long) thumbnailOffset, this.mExifByteOrder));
            this.mThumbnailOffset = thumbnailOffset;
            position += this.mThumbnailLength;
        }
        int totalSize = position;
        if (this.mMimeType == 4) {
            totalSize += 8;
        }
        if (DEBUG) {
            for (int i4 = 0; i4 < EXIF_TAGS.length; i4++) {
                Log.d(TAG, String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", new Object[]{Integer.valueOf(i4), Integer.valueOf(ifdOffsets[i4]), Integer.valueOf(this.mAttributes[i4].size()), Integer.valueOf(ifdDataSizes[i4]), Integer.valueOf(totalSize)}));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong((long) ifdOffsets[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong((long) ifdOffsets[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong((long) ifdOffsets[3], this.mExifByteOrder));
        }
        switch (this.mMimeType) {
            case 4:
                byteOrderedDataOutputStream.writeUnsignedShort(totalSize);
                byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
                break;
            case 13:
                byteOrderedDataOutputStream.writeInt(totalSize);
                byteOrderedDataOutputStream.write(PNG_CHUNK_TYPE_EXIF);
                break;
            case 14:
                byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
                byteOrderedDataOutputStream.writeInt(totalSize);
                break;
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        byteOrderedDataOutputStream.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8);
        int ifdType3 = 0;
        while (ifdType3 < EXIF_TAGS.length) {
            if (!this.mAttributes[ifdType3].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[ifdType3].size());
                int dataOffset = ifdOffsets[ifdType3] + i + (this.mAttributes[ifdType3].size() * 12) + i2;
                Iterator<Map.Entry<String, ExifAttribute>> it2 = this.mAttributes[ifdType3].entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry<String, ExifAttribute> entry3 = it2.next();
                    int tagNumber = sExifTagMapsForWriting[ifdType3].get(entry3.getKey()).number;
                    ExifAttribute attribute = entry3.getValue();
                    int size2 = attribute.size();
                    byteOrderedDataOutputStream.writeUnsignedShort(tagNumber);
                    byteOrderedDataOutputStream.writeUnsignedShort(attribute.format);
                    byteOrderedDataOutputStream.writeInt(attribute.numberOfComponents);
                    if (size2 > i2) {
                        it = it2;
                        byteOrderedDataOutputStream.writeUnsignedInt((long) dataOffset);
                        dataOffset += size2;
                    } else {
                        it = it2;
                        byteOrderedDataOutputStream.write(attribute.bytes);
                        if (size2 < 4) {
                            int i5 = size2;
                            for (int i6 = 4; i5 < i6; i6 = 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                i5++;
                            }
                        }
                    }
                    it2 = it;
                    i2 = 4;
                }
                if (ifdType3 != 0 || this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(0);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt((long) ifdOffsets[4]);
                }
                for (Map.Entry<String, ExifAttribute> entry4 : this.mAttributes[ifdType3].entrySet()) {
                    byte[] bArr = entry4.getValue().bytes;
                    if (bArr.length > 4) {
                        byteOrderedDataOutputStream.write(bArr, 0, bArr.length);
                    }
                }
            }
            ifdType3++;
            i = 2;
            i2 = 4;
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && totalSize % 2 == 1) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        return totalSize;
    }

    private static Pair<Integer, Integer> guessDataFormat(String entryValue) {
        if (entryValue.contains(",")) {
            String[] entryValues = entryValue.split(",", -1);
            Pair<Integer, Integer> dataFormat = guessDataFormat(entryValues[0]);
            if (((Integer) dataFormat.first).intValue() == 2) {
                return dataFormat;
            }
            for (int i = 1; i < entryValues.length; i++) {
                Pair<Integer, Integer> guessDataFormat = guessDataFormat(entryValues[i]);
                int first = -1;
                int second = -1;
                if (((Integer) guessDataFormat.first).equals(dataFormat.first) || ((Integer) guessDataFormat.second).equals(dataFormat.first)) {
                    first = ((Integer) dataFormat.first).intValue();
                }
                if (((Integer) dataFormat.second).intValue() != -1 && (((Integer) guessDataFormat.first).equals(dataFormat.second) || ((Integer) guessDataFormat.second).equals(dataFormat.second))) {
                    second = ((Integer) dataFormat.second).intValue();
                }
                if (first == -1 && second == -1) {
                    return new Pair<>(2, -1);
                }
                if (first == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(second), -1);
                } else if (second == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(first), -1);
                }
            }
            return dataFormat;
        } else if (entryValue.contains("/")) {
            String[] rationalNumber = entryValue.split("/", -1);
            if (rationalNumber.length == 2) {
                try {
                    long numerator = (long) Double.parseDouble(rationalNumber[0]);
                    long denominator = (long) Double.parseDouble(rationalNumber[1]);
                    if (numerator >= 0) {
                        if (denominator >= 0) {
                            if (numerator <= 2147483647L) {
                                if (denominator <= 2147483647L) {
                                    return new Pair<>(10, 5);
                                }
                            }
                            return new Pair<>(5, -1);
                        }
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException e) {
                }
            }
            return new Pair<>(2, -1);
        } else {
            try {
                Long longValue = Long.valueOf(Long.parseLong(entryValue));
                if (longValue.longValue() >= 0 && longValue.longValue() <= 65535) {
                    return new Pair<>(3, 4);
                }
                if (longValue.longValue() < 0) {
                    return new Pair<>(9, -1);
                }
                return new Pair<>(4, -1);
            } catch (NumberFormatException e2) {
                try {
                    Double.parseDouble(entryValue);
                    return new Pair<>(12, -1);
                } catch (NumberFormatException e3) {
                    return new Pair<>(2, -1);
                }
            }
        }
    }

    public static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        private ByteOrder mByteOrder;
        private DataInputStream mDataInputStream;
        final int mLength;
        int mPosition;

        public ByteOrderedDataInputStream(InputStream in) {
            this(in, ByteOrder.BIG_ENDIAN);
        }

        ByteOrderedDataInputStream(InputStream in, ByteOrder byteOrder) {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            DataInputStream dataInputStream = new DataInputStream(in);
            this.mDataInputStream = dataInputStream;
            int available = dataInputStream.available();
            this.mLength = available;
            this.mPosition = 0;
            this.mDataInputStream.mark(available);
            this.mByteOrder = byteOrder;
        }

        public ByteOrderedDataInputStream(byte[] bytes) {
            this((InputStream) new ByteArrayInputStream(bytes));
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long byteCount) {
            int i = this.mPosition;
            if (((long) i) > byteCount) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                byteCount -= (long) i;
            }
            if (skipBytes((int) byteCount) != ((int) byteCount)) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        public int available() {
            return this.mDataInputStream.available();
        }

        public int read() {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        public int read(byte[] b, int off, int len) {
            int bytesRead = this.mDataInputStream.read(b, off, len);
            this.mPosition += bytesRead;
            return bytesRead;
        }

        public int readUnsignedByte() {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        public String readLine() {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        public boolean readBoolean() {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        public char readChar() {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        public String readUTF() {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        public void readFully(byte[] buffer, int offset, int length) {
            int i = this.mPosition + length;
            this.mPosition = i;
            if (i > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(buffer, offset, length) != length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public void readFully(byte[] buffer) {
            int length = this.mPosition + buffer.length;
            this.mPosition = length;
            if (length > this.mLength) {
                throw new EOFException();
            } else if (this.mDataInputStream.read(buffer, 0, buffer.length) != buffer.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public byte readByte() {
            int i = this.mPosition + 1;
            this.mPosition = i;
            if (i <= this.mLength) {
                int ch = this.mDataInputStream.read();
                if (ch >= 0) {
                    return (byte) ch;
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public short readShort() {
            int i = this.mPosition + 2;
            this.mPosition = i;
            if (i <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                if ((ch1 | ch2) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (short) ((ch2 << 8) + ch1);
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (short) ((ch1 << 8) + ch2);
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public int readInt() {
            int i = this.mPosition + 4;
            this.mPosition = i;
            if (i <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                int ch3 = this.mDataInputStream.read();
                int ch4 = this.mDataInputStream.read();
                if ((ch1 | ch2 | ch3 | ch4) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public int skipBytes(int byteCount) {
            int totalSkip = Math.min(byteCount, this.mLength - this.mPosition);
            int skipped = 0;
            while (skipped < totalSkip) {
                skipped += this.mDataInputStream.skipBytes(totalSkip - skipped);
            }
            this.mPosition += skipped;
            return skipped;
        }

        public int readUnsignedShort() {
            int i = this.mPosition + 2;
            this.mPosition = i;
            if (i <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                if ((ch1 | ch2) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (ch2 << 8) + ch1;
                    }
                    if (byteOrder == BIG_ENDIAN) {
                        return (ch1 << 8) + ch2;
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public long readUnsignedInt() {
            return ((long) readInt()) & 4294967295L;
        }

        public long readLong() {
            int i = this.mPosition + 8;
            this.mPosition = i;
            if (i <= this.mLength) {
                int ch1 = this.mDataInputStream.read();
                int ch2 = this.mDataInputStream.read();
                int ch3 = this.mDataInputStream.read();
                int ch4 = this.mDataInputStream.read();
                int ch5 = this.mDataInputStream.read();
                int ch6 = this.mDataInputStream.read();
                int ch7 = this.mDataInputStream.read();
                int ch8 = this.mDataInputStream.read();
                if ((ch1 | ch2 | ch3 | ch4 | ch5 | ch6 | ch7 | ch8) >= 0) {
                    ByteOrder byteOrder = this.mByteOrder;
                    if (byteOrder == LITTLE_ENDIAN) {
                        return (((long) ch8) << 56) + (((long) ch7) << 48) + (((long) ch6) << 40) + (((long) ch5) << 32) + (((long) ch4) << 24) + (((long) ch3) << 16) + (((long) ch2) << 8) + ((long) ch1);
                    }
                    int ch22 = ch2;
                    if (byteOrder == BIG_ENDIAN) {
                        return (((long) ch1) << 56) + (((long) ch22) << 48) + (((long) ch3) << 40) + (((long) ch4) << 32) + (((long) ch5) << 24) + (((long) ch6) << 16) + (((long) ch7) << 8) + ((long) ch8);
                    }
                    throw new IOException("Invalid byte order: " + this.mByteOrder);
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public float readFloat() {
            return Float.intBitsToFloat(readInt());
        }

        public double readDouble() {
            return Double.longBitsToDouble(readLong());
        }

        public synchronized void mark(int readlimit) {
            this.mDataInputStream.mark(readlimit);
        }

        public int getLength() {
            return this.mLength;
        }
    }

    public static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private ByteOrder mByteOrder;
        final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream out, ByteOrder byteOrder) {
            super(out);
            this.mOutputStream = out;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void write(byte[] bytes) {
            this.mOutputStream.write(bytes);
        }

        public void write(byte[] bytes, int offset, int length) {
            this.mOutputStream.write(bytes, offset, length);
        }

        public void writeByte(int val) {
            this.mOutputStream.write(val);
        }

        public void writeShort(short val) {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeInt(int val) {
            ByteOrder byteOrder = this.mByteOrder;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 24) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 24) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeUnsignedShort(int val) {
            writeShort((short) val);
        }

        public void writeUnsignedInt(long val) {
            writeInt((int) val);
        }
    }

    private void swapBasedOnImageSize(int firstIfdType, int secondIfdType) {
        if (!this.mAttributes[firstIfdType].isEmpty() && !this.mAttributes[secondIfdType].isEmpty()) {
            ExifAttribute firstImageLengthAttribute = this.mAttributes[firstIfdType].get(TAG_IMAGE_LENGTH);
            ExifAttribute firstImageWidthAttribute = this.mAttributes[firstIfdType].get(TAG_IMAGE_WIDTH);
            ExifAttribute secondImageLengthAttribute = this.mAttributes[secondIfdType].get(TAG_IMAGE_LENGTH);
            ExifAttribute secondImageWidthAttribute = this.mAttributes[secondIfdType].get(TAG_IMAGE_WIDTH);
            if (firstImageLengthAttribute == null || firstImageWidthAttribute == null) {
                if (DEBUG) {
                    Log.d(TAG, "First image does not contain valid size information");
                }
            } else if (secondImageLengthAttribute != null && secondImageWidthAttribute != null) {
                int firstImageLengthValue = firstImageLengthAttribute.getIntValue(this.mExifByteOrder);
                int firstImageWidthValue = firstImageWidthAttribute.getIntValue(this.mExifByteOrder);
                int secondImageLengthValue = secondImageLengthAttribute.getIntValue(this.mExifByteOrder);
                int secondImageWidthValue = secondImageWidthAttribute.getIntValue(this.mExifByteOrder);
                if (firstImageLengthValue < secondImageLengthValue && firstImageWidthValue < secondImageWidthValue) {
                    HashMap<String, ExifAttribute>[] hashMapArr = this.mAttributes;
                    HashMap<String, ExifAttribute> tempMap = hashMapArr[firstIfdType];
                    hashMapArr[firstIfdType] = hashMapArr[secondIfdType];
                    hashMapArr[secondIfdType] = tempMap;
                }
            } else if (DEBUG != 0) {
                Log.d(TAG, "Second image does not contain valid size information");
            }
        } else if (DEBUG) {
            Log.d(TAG, "Cannot perform swap since only one image data exists");
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    private static void closeFileDescriptor(FileDescriptor fd) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                Os.close(fd);
            } catch (Exception e) {
                Log.e(TAG, "Error closing fd.");
            }
        } else {
            Log.e(TAG, "closeFileDescriptor is called in API < 21, which must be wrong.");
        }
    }

    private static int copy(InputStream in, OutputStream out) {
        int total = 0;
        byte[] buffer = new byte[8192];
        while (true) {
            int read = in.read(buffer);
            int c = read;
            if (read == -1) {
                return total;
            }
            total += c;
            out.write(buffer, 0, c);
        }
    }

    private static void copy(InputStream in, OutputStream out, int numBytes) {
        int remainder = numBytes;
        byte[] buffer = new byte[8192];
        while (remainder > 0) {
            int bytesToRead = Math.min(remainder, 8192);
            int bytesRead = in.read(buffer, 0, bytesToRead);
            if (bytesRead == bytesToRead) {
                remainder -= bytesRead;
                out.write(buffer, 0, bytesRead);
            } else {
                throw new IOException("Failed to copy the given amount of bytes from the inputstream to the output stream.");
            }
        }
    }

    private static long[] convertToLongArray(Object inputObj) {
        if (inputObj instanceof int[]) {
            int[] input = (int[]) inputObj;
            long[] result = new long[input.length];
            for (int i = 0; i < input.length; i++) {
                result[i] = (long) input[i];
            }
            return result;
        } else if (inputObj instanceof long[]) {
            return (long[]) inputObj;
        } else {
            return null;
        }
    }

    private static boolean startsWith(byte[] cur, byte[] val) {
        if (cur == null || val == null || cur.length < val.length) {
            return false;
        }
        for (int i = 0; i < val.length; i++) {
            if (cur[i] != val[i]) {
                return false;
            }
        }
        return true;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(bytes[i])}));
        }
        return sb.toString();
    }

    private boolean isSupportedFormatForSavingAttributes() {
        int i = this.mMimeType;
        if (i == 4 || i == 13 || i == 14) {
            return true;
        }
        return false;
    }
}
