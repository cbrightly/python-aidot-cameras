package com.google.android.datatransport.cct.internal;

import androidx.annotation.Nullable;
import com.google.android.datatransport.cct.internal.AndroidClientInfo;

public final class AutoValue_AndroidClientInfo extends AndroidClientInfo {
    private final String applicationBuild;
    private final String country;
    private final String device;
    private final String fingerprint;
    private final String hardware;
    private final String locale;
    private final String manufacturer;
    private final String mccMnc;
    private final String model;
    private final String osBuild;
    private final String product;
    private final Integer sdkVersion;

    private AutoValue_AndroidClientInfo(@Nullable Integer sdkVersion2, @Nullable String model2, @Nullable String hardware2, @Nullable String device2, @Nullable String product2, @Nullable String osBuild2, @Nullable String manufacturer2, @Nullable String fingerprint2, @Nullable String locale2, @Nullable String country2, @Nullable String mccMnc2, @Nullable String applicationBuild2) {
        this.sdkVersion = sdkVersion2;
        this.model = model2;
        this.hardware = hardware2;
        this.device = device2;
        this.product = product2;
        this.osBuild = osBuild2;
        this.manufacturer = manufacturer2;
        this.fingerprint = fingerprint2;
        this.locale = locale2;
        this.country = country2;
        this.mccMnc = mccMnc2;
        this.applicationBuild = applicationBuild2;
    }

    @Nullable
    public Integer getSdkVersion() {
        return this.sdkVersion;
    }

    @Nullable
    public String getModel() {
        return this.model;
    }

    @Nullable
    public String getHardware() {
        return this.hardware;
    }

    @Nullable
    public String getDevice() {
        return this.device;
    }

    @Nullable
    public String getProduct() {
        return this.product;
    }

    @Nullable
    public String getOsBuild() {
        return this.osBuild;
    }

    @Nullable
    public String getManufacturer() {
        return this.manufacturer;
    }

    @Nullable
    public String getFingerprint() {
        return this.fingerprint;
    }

    @Nullable
    public String getLocale() {
        return this.locale;
    }

    @Nullable
    public String getCountry() {
        return this.country;
    }

    @Nullable
    public String getMccMnc() {
        return this.mccMnc;
    }

    @Nullable
    public String getApplicationBuild() {
        return this.applicationBuild;
    }

    public String toString() {
        return "AndroidClientInfo{sdkVersion=" + this.sdkVersion + ", model=" + this.model + ", hardware=" + this.hardware + ", device=" + this.device + ", product=" + this.product + ", osBuild=" + this.osBuild + ", manufacturer=" + this.manufacturer + ", fingerprint=" + this.fingerprint + ", locale=" + this.locale + ", country=" + this.country + ", mccMnc=" + this.mccMnc + ", applicationBuild=" + this.applicationBuild + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AndroidClientInfo)) {
            return false;
        }
        AndroidClientInfo that = (AndroidClientInfo) o;
        Integer num = this.sdkVersion;
        if (num != null ? num.equals(that.getSdkVersion()) : that.getSdkVersion() == null) {
            String str = this.model;
            if (str != null ? str.equals(that.getModel()) : that.getModel() == null) {
                String str2 = this.hardware;
                if (str2 != null ? str2.equals(that.getHardware()) : that.getHardware() == null) {
                    String str3 = this.device;
                    if (str3 != null ? str3.equals(that.getDevice()) : that.getDevice() == null) {
                        String str4 = this.product;
                        if (str4 != null ? str4.equals(that.getProduct()) : that.getProduct() == null) {
                            String str5 = this.osBuild;
                            if (str5 != null ? str5.equals(that.getOsBuild()) : that.getOsBuild() == null) {
                                String str6 = this.manufacturer;
                                if (str6 != null ? str6.equals(that.getManufacturer()) : that.getManufacturer() == null) {
                                    String str7 = this.fingerprint;
                                    if (str7 != null ? str7.equals(that.getFingerprint()) : that.getFingerprint() == null) {
                                        String str8 = this.locale;
                                        if (str8 != null ? str8.equals(that.getLocale()) : that.getLocale() == null) {
                                            String str9 = this.country;
                                            if (str9 != null ? str9.equals(that.getCountry()) : that.getCountry() == null) {
                                                String str10 = this.mccMnc;
                                                if (str10 != null ? str10.equals(that.getMccMnc()) : that.getMccMnc() == null) {
                                                    String str11 = this.applicationBuild;
                                                    if (str11 == null) {
                                                        if (that.getApplicationBuild() == null) {
                                                            return true;
                                                        }
                                                    } else if (str11.equals(that.getApplicationBuild())) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1 * 1000003;
        Integer num = this.sdkVersion;
        int i = 0;
        int h$2 = (h$ ^ (num == null ? 0 : num.hashCode())) * 1000003;
        String str = this.model;
        int h$3 = (h$2 ^ (str == null ? 0 : str.hashCode())) * 1000003;
        String str2 = this.hardware;
        int h$4 = (h$3 ^ (str2 == null ? 0 : str2.hashCode())) * 1000003;
        String str3 = this.device;
        int h$5 = (h$4 ^ (str3 == null ? 0 : str3.hashCode())) * 1000003;
        String str4 = this.product;
        int h$6 = (h$5 ^ (str4 == null ? 0 : str4.hashCode())) * 1000003;
        String str5 = this.osBuild;
        int h$7 = (h$6 ^ (str5 == null ? 0 : str5.hashCode())) * 1000003;
        String str6 = this.manufacturer;
        int h$8 = (h$7 ^ (str6 == null ? 0 : str6.hashCode())) * 1000003;
        String str7 = this.fingerprint;
        int h$9 = (h$8 ^ (str7 == null ? 0 : str7.hashCode())) * 1000003;
        String str8 = this.locale;
        int h$10 = (h$9 ^ (str8 == null ? 0 : str8.hashCode())) * 1000003;
        String str9 = this.country;
        int h$11 = (h$10 ^ (str9 == null ? 0 : str9.hashCode())) * 1000003;
        String str10 = this.mccMnc;
        int h$12 = (h$11 ^ (str10 == null ? 0 : str10.hashCode())) * 1000003;
        String str11 = this.applicationBuild;
        if (str11 != null) {
            i = str11.hashCode();
        }
        return h$12 ^ i;
    }

    public static final class Builder extends AndroidClientInfo.Builder {
        private String applicationBuild;
        private String country;
        private String device;
        private String fingerprint;
        private String hardware;
        private String locale;
        private String manufacturer;
        private String mccMnc;
        private String model;
        private String osBuild;
        private String product;
        private Integer sdkVersion;

        Builder() {
        }

        public AndroidClientInfo.Builder setSdkVersion(@Nullable Integer sdkVersion2) {
            this.sdkVersion = sdkVersion2;
            return this;
        }

        public AndroidClientInfo.Builder setModel(@Nullable String model2) {
            this.model = model2;
            return this;
        }

        public AndroidClientInfo.Builder setHardware(@Nullable String hardware2) {
            this.hardware = hardware2;
            return this;
        }

        public AndroidClientInfo.Builder setDevice(@Nullable String device2) {
            this.device = device2;
            return this;
        }

        public AndroidClientInfo.Builder setProduct(@Nullable String product2) {
            this.product = product2;
            return this;
        }

        public AndroidClientInfo.Builder setOsBuild(@Nullable String osBuild2) {
            this.osBuild = osBuild2;
            return this;
        }

        public AndroidClientInfo.Builder setManufacturer(@Nullable String manufacturer2) {
            this.manufacturer = manufacturer2;
            return this;
        }

        public AndroidClientInfo.Builder setFingerprint(@Nullable String fingerprint2) {
            this.fingerprint = fingerprint2;
            return this;
        }

        public AndroidClientInfo.Builder setLocale(@Nullable String locale2) {
            this.locale = locale2;
            return this;
        }

        public AndroidClientInfo.Builder setCountry(@Nullable String country2) {
            this.country = country2;
            return this;
        }

        public AndroidClientInfo.Builder setMccMnc(@Nullable String mccMnc2) {
            this.mccMnc = mccMnc2;
            return this;
        }

        public AndroidClientInfo.Builder setApplicationBuild(@Nullable String applicationBuild2) {
            this.applicationBuild = applicationBuild2;
            return this;
        }

        public AndroidClientInfo build() {
            return new AutoValue_AndroidClientInfo(this.sdkVersion, this.model, this.hardware, this.device, this.product, this.osBuild, this.manufacturer, this.fingerprint, this.locale, this.country, this.mccMnc, this.applicationBuild);
        }
    }
}
