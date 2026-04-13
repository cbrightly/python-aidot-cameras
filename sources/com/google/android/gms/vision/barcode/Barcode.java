package com.google.android.gms.vision.barcode;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "BarcodeCreator")
@SafeParcelable.Reserved({1})
/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public class Barcode extends AbstractSafeParcelable {
    public static final int ALL_FORMATS = 0;
    public static final int AZTEC = 4096;
    public static final int CALENDAR_EVENT = 11;
    public static final int CODABAR = 8;
    public static final int CODE_128 = 1;
    public static final int CODE_39 = 2;
    public static final int CODE_93 = 4;
    public static final int CONTACT_INFO = 1;
    @RecentlyNonNull
    public static final Parcelable.Creator<Barcode> CREATOR = new zzb();
    public static final int DATA_MATRIX = 16;
    public static final int DRIVER_LICENSE = 12;
    public static final int EAN_13 = 32;
    public static final int EAN_8 = 64;
    public static final int EMAIL = 2;
    public static final int GEO = 10;
    public static final int ISBN = 3;
    public static final int ITF = 128;
    public static final int PDF417 = 2048;
    public static final int PHONE = 4;
    public static final int PRODUCT = 5;
    public static final int QR_CODE = 256;
    public static final int SMS = 6;
    public static final int TEXT = 7;
    public static final int UPC_A = 512;
    public static final int UPC_E = 1024;
    public static final int URL = 8;
    public static final int WIFI = 9;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 13)
    public CalendarEvent calendarEvent;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 14)
    public ContactInfo contactInfo;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 6)
    public Point[] cornerPoints;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 4)
    public String displayValue;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 15)
    public DriverLicense driverLicense;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 7)
    public Email email;
    @SafeParcelable.Field(id = 2)
    public int format;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 12)
    public GeoPoint geoPoint;
    @SafeParcelable.Field(id = 17)
    public boolean isRecognized;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 8)
    public Phone phone;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 16)
    public byte[] rawBytes;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 3)
    public String rawValue;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 9)
    public Sms sms;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 11)
    public UrlBookmark url;
    @SafeParcelable.Field(id = 5)
    public int valueFormat;
    @RecentlyNonNull
    @SafeParcelable.Field(id = 10)
    public WiFi wifi;

    @SafeParcelable.Class(creator = "AddressCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class Address extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<Address> CREATOR = new zza();
        public static final int HOME = 2;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String[] addressLines;
        @SafeParcelable.Field(id = 2)
        public int type;

        public Address() {
        }

        @SafeParcelable.Constructor
        public Address(@SafeParcelable.Param(id = 2) int i, @RecentlyNonNull @SafeParcelable.Param(id = 3) String[] strArr) {
            this.type = i;
            this.addressLines = strArr;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 2, this.type);
            SafeParcelWriter.writeStringArray(parcel, 3, this.addressLines, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "CalendarDateTimeCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class CalendarDateTime extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<CalendarDateTime> CREATOR = new zzd();
        @SafeParcelable.Field(id = 4)
        public int day;
        @SafeParcelable.Field(id = 5)
        public int hours;
        @SafeParcelable.Field(id = 8)
        public boolean isUtc;
        @SafeParcelable.Field(id = 6)
        public int minutes;
        @SafeParcelable.Field(id = 3)
        public int month;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 9)
        public String rawValue;
        @SafeParcelable.Field(id = 7)
        public int seconds;
        @SafeParcelable.Field(id = 2)
        public int year;

        public CalendarDateTime() {
        }

        @SafeParcelable.Constructor
        public CalendarDateTime(@SafeParcelable.Param(id = 2) int i, @SafeParcelable.Param(id = 3) int i2, @SafeParcelable.Param(id = 4) int i3, @SafeParcelable.Param(id = 5) int i4, @SafeParcelable.Param(id = 6) int i5, @SafeParcelable.Param(id = 7) int i6, @SafeParcelable.Param(id = 8) boolean z, @RecentlyNonNull @SafeParcelable.Param(id = 9) String str) {
            this.year = i;
            this.month = i2;
            this.day = i3;
            this.hours = i4;
            this.minutes = i5;
            this.seconds = i6;
            this.isUtc = z;
            this.rawValue = str;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 2, this.year);
            SafeParcelWriter.writeInt(parcel, 3, this.month);
            SafeParcelWriter.writeInt(parcel, 4, this.day);
            SafeParcelWriter.writeInt(parcel, 5, this.hours);
            SafeParcelWriter.writeInt(parcel, 6, this.minutes);
            SafeParcelWriter.writeInt(parcel, 7, this.seconds);
            SafeParcelWriter.writeBoolean(parcel, 8, this.isUtc);
            SafeParcelWriter.writeString(parcel, 9, this.rawValue, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "CalendarEventCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class CalendarEvent extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<CalendarEvent> CREATOR = new zzf();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String description;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 8)
        public CalendarDateTime end;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 4)
        public String location;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 5)
        public String organizer;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 7)
        public CalendarDateTime start;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 6)
        public String status;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String summary;

        public CalendarEvent() {
        }

        @SafeParcelable.Constructor
        public CalendarEvent(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str3, @RecentlyNonNull @SafeParcelable.Param(id = 5) String str4, @RecentlyNonNull @SafeParcelable.Param(id = 6) String str5, @RecentlyNonNull @SafeParcelable.Param(id = 7) CalendarDateTime calendarDateTime, @RecentlyNonNull @SafeParcelable.Param(id = 8) CalendarDateTime calendarDateTime2) {
            this.summary = str;
            this.description = str2;
            this.location = str3;
            this.organizer = str4;
            this.status = str5;
            this.start = calendarDateTime;
            this.end = calendarDateTime2;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.summary, false);
            SafeParcelWriter.writeString(parcel, 3, this.description, false);
            SafeParcelWriter.writeString(parcel, 4, this.location, false);
            SafeParcelWriter.writeString(parcel, 5, this.organizer, false);
            SafeParcelWriter.writeString(parcel, 6, this.status, false);
            SafeParcelWriter.writeParcelable(parcel, 7, this.start, i, false);
            SafeParcelWriter.writeParcelable(parcel, 8, this.end, i, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "ContactInfoCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class ContactInfo extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<ContactInfo> CREATOR = new zze();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 8)
        public Address[] addresses;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 6)
        public Email[] emails;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public PersonName name;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String organization;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 5)
        public Phone[] phones;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 4)
        public String title;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 7)
        public String[] urls;

        public ContactInfo() {
        }

        @SafeParcelable.Constructor
        public ContactInfo(@RecentlyNonNull @SafeParcelable.Param(id = 2) PersonName personName, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str2, @RecentlyNonNull @SafeParcelable.Param(id = 5) Phone[] phoneArr, @RecentlyNonNull @SafeParcelable.Param(id = 6) Email[] emailArr, @RecentlyNonNull @SafeParcelable.Param(id = 7) String[] strArr, @RecentlyNonNull @SafeParcelable.Param(id = 8) Address[] addressArr) {
            this.name = personName;
            this.organization = str;
            this.title = str2;
            this.phones = phoneArr;
            this.emails = emailArr;
            this.urls = strArr;
            this.addresses = addressArr;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeParcelable(parcel, 2, this.name, i, false);
            SafeParcelWriter.writeString(parcel, 3, this.organization, false);
            SafeParcelWriter.writeString(parcel, 4, this.title, false);
            SafeParcelWriter.writeTypedArray(parcel, 5, this.phones, i, false);
            SafeParcelWriter.writeTypedArray(parcel, 6, this.emails, i, false);
            SafeParcelWriter.writeStringArray(parcel, 7, this.urls, false);
            SafeParcelWriter.writeTypedArray(parcel, 8, this.addresses, i, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "DriverLicenseCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class DriverLicense extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<DriverLicense> CREATOR = new zzh();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 8)
        public String addressCity;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 9)
        public String addressState;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 7)
        public String addressStreet;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 10)
        public String addressZip;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 14)
        public String birthDate;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String documentType;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 13)
        public String expiryDate;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String firstName;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 6)
        public String gender;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 12)
        public String issueDate;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 15)
        public String issuingCountry;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 5)
        public String lastName;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 11)
        public String licenseNumber;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 4)
        public String middleName;

        public DriverLicense() {
        }

        @SafeParcelable.Constructor
        public DriverLicense(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str3, @RecentlyNonNull @SafeParcelable.Param(id = 5) String str4, @RecentlyNonNull @SafeParcelable.Param(id = 6) String str5, @RecentlyNonNull @SafeParcelable.Param(id = 7) String str6, @RecentlyNonNull @SafeParcelable.Param(id = 8) String str7, @RecentlyNonNull @SafeParcelable.Param(id = 9) String str8, @RecentlyNonNull @SafeParcelable.Param(id = 10) String str9, @RecentlyNonNull @SafeParcelable.Param(id = 11) String str10, @RecentlyNonNull @SafeParcelable.Param(id = 12) String str11, @RecentlyNonNull @SafeParcelable.Param(id = 13) String str12, @RecentlyNonNull @SafeParcelable.Param(id = 14) String str13, @RecentlyNonNull @SafeParcelable.Param(id = 15) String str14) {
            this.documentType = str;
            this.firstName = str2;
            this.middleName = str3;
            this.lastName = str4;
            this.gender = str5;
            this.addressStreet = str6;
            this.addressCity = str7;
            this.addressState = str8;
            this.addressZip = str9;
            this.licenseNumber = str10;
            this.issueDate = str11;
            this.expiryDate = str12;
            this.birthDate = str13;
            this.issuingCountry = str14;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.documentType, false);
            SafeParcelWriter.writeString(parcel, 3, this.firstName, false);
            SafeParcelWriter.writeString(parcel, 4, this.middleName, false);
            SafeParcelWriter.writeString(parcel, 5, this.lastName, false);
            SafeParcelWriter.writeString(parcel, 6, this.gender, false);
            SafeParcelWriter.writeString(parcel, 7, this.addressStreet, false);
            SafeParcelWriter.writeString(parcel, 8, this.addressCity, false);
            SafeParcelWriter.writeString(parcel, 9, this.addressState, false);
            SafeParcelWriter.writeString(parcel, 10, this.addressZip, false);
            SafeParcelWriter.writeString(parcel, 11, this.licenseNumber, false);
            SafeParcelWriter.writeString(parcel, 12, this.issueDate, false);
            SafeParcelWriter.writeString(parcel, 13, this.expiryDate, false);
            SafeParcelWriter.writeString(parcel, 14, this.birthDate, false);
            SafeParcelWriter.writeString(parcel, 15, this.issuingCountry, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "EmailCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class Email extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<Email> CREATOR = new zzg();
        public static final int HOME = 2;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String address;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 5)
        public String body;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 4)
        public String subject;
        @SafeParcelable.Field(id = 2)
        public int type;

        public Email() {
        }

        @SafeParcelable.Constructor
        public Email(@SafeParcelable.Param(id = 2) int i, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str2, @RecentlyNonNull @SafeParcelable.Param(id = 5) String str3) {
            this.type = i;
            this.address = str;
            this.subject = str2;
            this.body = str3;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 2, this.type);
            SafeParcelWriter.writeString(parcel, 3, this.address, false);
            SafeParcelWriter.writeString(parcel, 4, this.subject, false);
            SafeParcelWriter.writeString(parcel, 5, this.body, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "GeoPointCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class GeoPoint extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<GeoPoint> CREATOR = new zzj();
        @SafeParcelable.Field(id = 2)
        public double lat;
        @SafeParcelable.Field(id = 3)
        public double lng;

        public GeoPoint() {
        }

        @SafeParcelable.Constructor
        public GeoPoint(@SafeParcelable.Param(id = 2) double d, @SafeParcelable.Param(id = 3) double d2) {
            this.lat = d;
            this.lng = d2;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeDouble(parcel, 2, this.lat);
            SafeParcelWriter.writeDouble(parcel, 3, this.lng);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "PersonNameCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class PersonName extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<PersonName> CREATOR = new zzi();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 5)
        public String first;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String formattedName;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 7)
        public String last;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 6)
        public String middle;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 4)
        public String prefix;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String pronunciation;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 8)
        public String suffix;

        public PersonName() {
        }

        @SafeParcelable.Constructor
        public PersonName(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str3, @RecentlyNonNull @SafeParcelable.Param(id = 5) String str4, @RecentlyNonNull @SafeParcelable.Param(id = 6) String str5, @RecentlyNonNull @SafeParcelable.Param(id = 7) String str6, @RecentlyNonNull @SafeParcelable.Param(id = 8) String str7) {
            this.formattedName = str;
            this.pronunciation = str2;
            this.prefix = str3;
            this.first = str4;
            this.middle = str5;
            this.last = str6;
            this.suffix = str7;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.formattedName, false);
            SafeParcelWriter.writeString(parcel, 3, this.pronunciation, false);
            SafeParcelWriter.writeString(parcel, 4, this.prefix, false);
            SafeParcelWriter.writeString(parcel, 5, this.first, false);
            SafeParcelWriter.writeString(parcel, 6, this.middle, false);
            SafeParcelWriter.writeString(parcel, 7, this.last, false);
            SafeParcelWriter.writeString(parcel, 8, this.suffix, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "PhoneCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class Phone extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<Phone> CREATOR = new zzl();
        public static final int FAX = 3;
        public static final int HOME = 2;
        public static final int MOBILE = 4;
        public static final int UNKNOWN = 0;
        public static final int WORK = 1;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String number;
        @SafeParcelable.Field(id = 2)
        public int type;

        public Phone() {
        }

        @SafeParcelable.Constructor
        public Phone(@SafeParcelable.Param(id = 2) int i, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str) {
            this.type = i;
            this.number = str;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 2, this.type);
            SafeParcelWriter.writeString(parcel, 3, this.number, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "SmsCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class Sms extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<Sms> CREATOR = new zzk();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String message;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String phoneNumber;

        public Sms() {
        }

        @SafeParcelable.Constructor
        public Sms(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2) {
            this.message = str;
            this.phoneNumber = str2;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.message, false);
            SafeParcelWriter.writeString(parcel, 3, this.phoneNumber, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "UrlBookmarkCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class UrlBookmark extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<UrlBookmark> CREATOR = new zzn();
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String title;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String url;

        public UrlBookmark() {
        }

        @SafeParcelable.Constructor
        public UrlBookmark(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2) {
            this.title = str;
            this.url = str2;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.title, false);
            SafeParcelWriter.writeString(parcel, 3, this.url, false);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    @SafeParcelable.Class(creator = "WiFiCreator")
    @SafeParcelable.Reserved({1})
    /* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
    public static class WiFi extends AbstractSafeParcelable {
        @RecentlyNonNull
        public static final Parcelable.Creator<WiFi> CREATOR = new zzm();
        public static final int OPEN = 1;
        public static final int WEP = 3;
        public static final int WPA = 2;
        @SafeParcelable.Field(id = 4)
        public int encryptionType;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 3)
        public String password;
        @RecentlyNonNull
        @SafeParcelable.Field(id = 2)
        public String ssid;

        public WiFi() {
        }

        @SafeParcelable.Constructor
        public WiFi(@RecentlyNonNull @SafeParcelable.Param(id = 2) String str, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str2, @SafeParcelable.Param(id = 4) int i) {
            this.ssid = str;
            this.password = str2;
            this.encryptionType = i;
        }

        public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
            int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeString(parcel, 2, this.ssid, false);
            SafeParcelWriter.writeString(parcel, 3, this.password, false);
            SafeParcelWriter.writeInt(parcel, 4, this.encryptionType);
            SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
        }
    }

    public Barcode() {
    }

    @SafeParcelable.Constructor
    public Barcode(@SafeParcelable.Param(id = 2) int i, @RecentlyNonNull @SafeParcelable.Param(id = 3) String str, @RecentlyNonNull @SafeParcelable.Param(id = 4) String str2, @SafeParcelable.Param(id = 5) int i2, @RecentlyNonNull @SafeParcelable.Param(id = 6) Point[] pointArr, @RecentlyNonNull @SafeParcelable.Param(id = 7) Email email2, @RecentlyNonNull @SafeParcelable.Param(id = 8) Phone phone2, @RecentlyNonNull @SafeParcelable.Param(id = 9) Sms sms2, @RecentlyNonNull @SafeParcelable.Param(id = 10) WiFi wiFi, @RecentlyNonNull @SafeParcelable.Param(id = 11) UrlBookmark urlBookmark, @RecentlyNonNull @SafeParcelable.Param(id = 12) GeoPoint geoPoint2, @RecentlyNonNull @SafeParcelable.Param(id = 13) CalendarEvent calendarEvent2, @RecentlyNonNull @SafeParcelable.Param(id = 14) ContactInfo contactInfo2, @RecentlyNonNull @SafeParcelable.Param(id = 15) DriverLicense driverLicense2, @RecentlyNonNull @SafeParcelable.Param(id = 16) byte[] bArr, @SafeParcelable.Param(id = 17) boolean z) {
        this.format = i;
        this.rawValue = str;
        this.rawBytes = bArr;
        this.displayValue = str2;
        this.valueFormat = i2;
        this.cornerPoints = pointArr;
        this.isRecognized = z;
        this.email = email2;
        this.phone = phone2;
        this.sms = sms2;
        this.wifi = wiFi;
        this.url = urlBookmark;
        this.geoPoint = geoPoint2;
        this.calendarEvent = calendarEvent2;
        this.contactInfo = contactInfo2;
        this.driverLicense = driverLicense2;
    }

    public void writeToParcel(@RecentlyNonNull Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.format);
        SafeParcelWriter.writeString(parcel, 3, this.rawValue, false);
        SafeParcelWriter.writeString(parcel, 4, this.displayValue, false);
        SafeParcelWriter.writeInt(parcel, 5, this.valueFormat);
        SafeParcelWriter.writeTypedArray(parcel, 6, this.cornerPoints, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.email, i, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.phone, i, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.sms, i, false);
        SafeParcelWriter.writeParcelable(parcel, 10, this.wifi, i, false);
        SafeParcelWriter.writeParcelable(parcel, 11, this.url, i, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.geoPoint, i, false);
        SafeParcelWriter.writeParcelable(parcel, 13, this.calendarEvent, i, false);
        SafeParcelWriter.writeParcelable(parcel, 14, this.contactInfo, i, false);
        SafeParcelWriter.writeParcelable(parcel, 15, this.driverLicense, i, false);
        SafeParcelWriter.writeByteArray(parcel, 16, this.rawBytes, false);
        SafeParcelWriter.writeBoolean(parcel, 17, this.isRecognized);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @RecentlyNonNull
    public Rect getBoundingBox() {
        int i = Integer.MIN_VALUE;
        int i2 = Integer.MAX_VALUE;
        int i3 = 0;
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MAX_VALUE;
        while (true) {
            Point[] pointArr = this.cornerPoints;
            if (i3 >= pointArr.length) {
                return new Rect(i5, i2, i, i4);
            }
            Point point = pointArr[i3];
            i5 = Math.min(i5, point.x);
            i = Math.max(i, point.x);
            i2 = Math.min(i2, point.y);
            i4 = Math.max(i4, point.y);
            i3++;
        }
    }
}
