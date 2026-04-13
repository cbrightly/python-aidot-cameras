package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.measurement.zzaa;
import com.google.android.gms.internal.measurement.zzek;
import com.google.android.gms.internal.measurement.zzem;
import com.google.android.gms.internal.measurement.zzer;
import com.google.android.gms.internal.measurement.zzet;
import com.google.android.gms.internal.measurement.zzey;
import com.google.android.gms.internal.measurement.zzfp;
import com.google.android.gms.internal.measurement.zzfr;
import com.google.android.gms.internal.measurement.zzfs;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzfw;
import com.google.android.gms.internal.measurement.zzfx;
import com.google.android.gms.internal.measurement.zzgb;
import com.google.android.gms.internal.measurement.zzgc;
import com.google.android.gms.internal.measurement.zzgd;
import com.google.android.gms.internal.measurement.zzgi;
import com.google.android.gms.internal.measurement.zzgk;
import com.google.android.gms.internal.measurement.zzgl;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzkn;
import com.google.android.gms.internal.measurement.zzmh;
import com.google.android.gms.internal.measurement.zzpw;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.maps.android.BuildConfig;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzli extends zzkt {
    zzli(zzlg zzlg) {
        super(zzlg);
    }

    @WorkerThread
    static final boolean zzA(zzaw zzaw, zzq zzq) {
        Preconditions.checkNotNull(zzaw);
        Preconditions.checkNotNull(zzq);
        return !TextUtils.isEmpty(zzq.zzb) || !TextUtils.isEmpty(zzq.zzq);
    }

    static final zzfx zzB(zzft zzft, String str) {
        for (zzfx zzfx : zzft.zzi()) {
            if (zzfx.zzg().equals(str)) {
                return zzfx;
            }
        }
        return null;
    }

    static final Object zzC(zzft zzft, String str) {
        zzfx zzB = zzB(zzft, str);
        if (zzB == null) {
            return null;
        }
        if (zzB.zzy()) {
            return zzB.zzh();
        }
        if (zzB.zzw()) {
            return Long.valueOf(zzB.zzd());
        }
        if (zzB.zzu()) {
            return Double.valueOf(zzB.zza());
        }
        if (zzB.zzc() <= 0) {
            return null;
        }
        List<zzfx> zzi = zzB.zzi();
        ArrayList arrayList = new ArrayList();
        for (zzfx zzfx : zzi) {
            if (zzfx != null) {
                Bundle bundle = new Bundle();
                for (zzfx zzfx2 : zzfx.zzi()) {
                    if (zzfx2.zzy()) {
                        bundle.putString(zzfx2.zzg(), zzfx2.zzh());
                    } else if (zzfx2.zzw()) {
                        bundle.putLong(zzfx2.zzg(), zzfx2.zzd());
                    } else if (zzfx2.zzu()) {
                        bundle.putDouble(zzfx2.zzg(), zzfx2.zza());
                    }
                }
                if (!bundle.isEmpty()) {
                    arrayList.add(bundle);
                }
            }
        }
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    private final void zzD(StringBuilder sb, int i, List list) {
        String str;
        String str2;
        Long l;
        if (list != null) {
            int i2 = i + 1;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                zzfx zzfx = (zzfx) it.next();
                if (zzfx != null) {
                    zzF(sb, i2);
                    sb.append("param {\n");
                    Double d = null;
                    if (zzfx.zzx()) {
                        str = this.zzt.zzj().zze(zzfx.zzg());
                    } else {
                        str = null;
                    }
                    zzI(sb, i2, "name", str);
                    if (zzfx.zzy()) {
                        str2 = zzfx.zzh();
                    } else {
                        str2 = null;
                    }
                    zzI(sb, i2, "string_value", str2);
                    if (zzfx.zzw()) {
                        l = Long.valueOf(zzfx.zzd());
                    } else {
                        l = null;
                    }
                    zzI(sb, i2, "int_value", l);
                    if (zzfx.zzu()) {
                        d = Double.valueOf(zzfx.zza());
                    }
                    zzI(sb, i2, "double_value", d);
                    if (zzfx.zzc() > 0) {
                        zzD(sb, i2, zzfx.zzi());
                    }
                    zzF(sb, i2);
                    sb.append("}\n");
                }
            }
        }
    }

    private final void zzE(StringBuilder sb, int i, zzem zzem) {
        String str;
        if (zzem != null) {
            zzF(sb, i);
            sb.append("filter {\n");
            if (zzem.zzh()) {
                zzI(sb, i, "complement", Boolean.valueOf(zzem.zzg()));
            }
            if (zzem.zzj()) {
                zzI(sb, i, "param_name", this.zzt.zzj().zze(zzem.zze()));
            }
            if (zzem.zzk()) {
                int i2 = i + 1;
                zzey zzd = zzem.zzd();
                if (zzd != null) {
                    zzF(sb, i2);
                    sb.append("string_filter {\n");
                    if (zzd.zzi()) {
                        switch (zzd.zzj()) {
                            case 1:
                                str = "UNKNOWN_MATCH_TYPE";
                                break;
                            case 2:
                                str = "REGEXP";
                                break;
                            case 3:
                                str = "BEGINS_WITH";
                                break;
                            case 4:
                                str = "ENDS_WITH";
                                break;
                            case 5:
                                str = "PARTIAL";
                                break;
                            case 6:
                                str = "EXACT";
                                break;
                            default:
                                str = "IN_LIST";
                                break;
                        }
                        zzI(sb, i2, "match_type", str);
                    }
                    if (zzd.zzh()) {
                        zzI(sb, i2, "expression", zzd.zzd());
                    }
                    if (zzd.zzg()) {
                        zzI(sb, i2, "case_sensitive", Boolean.valueOf(zzd.zzf()));
                    }
                    if (zzd.zza() > 0) {
                        zzF(sb, i2 + 1);
                        sb.append("expression_list {\n");
                        for (String append : zzd.zze()) {
                            zzF(sb, i2 + 2);
                            sb.append(append);
                            sb.append("\n");
                        }
                        sb.append("}\n");
                    }
                    zzF(sb, i2);
                    sb.append("}\n");
                }
            }
            if (zzem.zzi()) {
                zzJ(sb, i + 1, "number_filter", zzem.zzc());
            }
            zzF(sb, i);
            sb.append("}\n");
        }
    }

    private static final void zzF(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(JustifyTextView.TWO_CHINESE_BLANK);
        }
    }

    private static final String zzG(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("Dynamic ");
        }
        if (z2) {
            sb.append("Sequence ");
        }
        if (z3) {
            sb.append("Session-Scoped ");
        }
        return sb.toString();
    }

    private static final void zzH(StringBuilder sb, int i, String str, zzgi zzgi) {
        Integer num;
        Integer num2;
        if (zzgi != null) {
            zzF(sb, 3);
            sb.append(str);
            sb.append(" {\n");
            if (zzgi.zzb() != 0) {
                zzF(sb, 4);
                sb.append("results: ");
                int i2 = 0;
                for (Long l : zzgi.zzi()) {
                    int i3 = i2 + 1;
                    if (i2 != 0) {
                        sb.append(", ");
                    }
                    sb.append(l);
                    i2 = i3;
                }
                sb.append(10);
            }
            if (zzgi.zzd() != 0) {
                zzF(sb, 4);
                sb.append("status: ");
                int i4 = 0;
                for (Long l2 : zzgi.zzk()) {
                    int i5 = i4 + 1;
                    if (i4 != 0) {
                        sb.append(", ");
                    }
                    sb.append(l2);
                    i4 = i5;
                }
                sb.append(10);
            }
            if (zzgi.zza() != 0) {
                zzF(sb, 4);
                sb.append("dynamic_filter_timestamps: {");
                int i6 = 0;
                for (zzfr zzfr : zzgi.zzh()) {
                    int i7 = i6 + 1;
                    if (i6 != 0) {
                        sb.append(", ");
                    }
                    if (zzfr.zzh()) {
                        num2 = Integer.valueOf(zzfr.zza());
                    } else {
                        num2 = null;
                    }
                    sb.append(num2);
                    sb.append(":");
                    sb.append(zzfr.zzg() ? Long.valueOf(zzfr.zzb()) : null);
                    i6 = i7;
                }
                sb.append("}\n");
            }
            if (zzgi.zzc() != 0) {
                zzF(sb, 4);
                sb.append("sequence_filter_timestamps: {");
                int i8 = 0;
                for (zzgk zzgk : zzgi.zzj()) {
                    int i9 = i8 + 1;
                    if (i8 != 0) {
                        sb.append(", ");
                    }
                    if (zzgk.zzi()) {
                        num = Integer.valueOf(zzgk.zzb());
                    } else {
                        num = null;
                    }
                    sb.append(num);
                    sb.append(": [");
                    int i10 = 0;
                    for (Long longValue : zzgk.zzf()) {
                        long longValue2 = longValue.longValue();
                        int i11 = i10 + 1;
                        if (i10 != 0) {
                            sb.append(", ");
                        }
                        sb.append(longValue2);
                        i10 = i11;
                    }
                    sb.append("]");
                    i8 = i9;
                }
                sb.append("}\n");
            }
            zzF(sb, 3);
            sb.append("}\n");
        }
    }

    private static final void zzI(StringBuilder sb, int i, String str, Object obj) {
        if (obj != null) {
            zzF(sb, i + 1);
            sb.append(str);
            sb.append(": ");
            sb.append(obj);
            sb.append(10);
        }
    }

    private static final void zzJ(StringBuilder sb, int i, String str, zzer zzer) {
        String str2;
        if (zzer != null) {
            zzF(sb, i);
            sb.append(str);
            sb.append(" {\n");
            if (zzer.zzg()) {
                switch (zzer.zzm()) {
                    case 1:
                        str2 = "UNKNOWN_COMPARISON_TYPE";
                        break;
                    case 2:
                        str2 = "LESS_THAN";
                        break;
                    case 3:
                        str2 = "GREATER_THAN";
                        break;
                    case 4:
                        str2 = "EQUAL";
                        break;
                    default:
                        str2 = "BETWEEN";
                        break;
                }
                zzI(sb, i, "comparison_type", str2);
            }
            if (zzer.zzi()) {
                zzI(sb, i, "match_as_float", Boolean.valueOf(zzer.zzf()));
            }
            if (zzer.zzh()) {
                zzI(sb, i, "comparison_value", zzer.zzc());
            }
            if (zzer.zzk()) {
                zzI(sb, i, "min_comparison_value", zzer.zze());
            }
            if (zzer.zzj()) {
                zzI(sb, i, "max_comparison_value", zzer.zzd());
            }
            zzF(sb, i);
            sb.append("}\n");
        }
    }

    static int zza(zzgc zzgc, String str) {
        for (int i = 0; i < zzgc.zzb(); i++) {
            if (str.equals(zzgc.zzap(i).zzf())) {
                return i;
            }
        }
        return -1;
    }

    static zzmh zzl(zzmh zzmh, byte[] bArr) {
        zzkn zza = zzkn.zza();
        if (zza != null) {
            return zzmh.zzaz(bArr, zza);
        }
        return zzmh.zzay(bArr);
    }

    static List zzr(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            long j = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i * 64) + i2;
                if (i3 >= bitSet.length()) {
                    break;
                }
                if (bitSet.get(i3)) {
                    j |= 1 << i2;
                }
            }
            arrayList.add(Long.valueOf(j));
        }
        return arrayList;
    }

    static boolean zzv(List list, int i) {
        if (i >= list.size() * 64) {
            return false;
        }
        return ((1 << (i % 64)) & ((Long) list.get(i / 64)).longValue()) != 0;
    }

    static boolean zzx(String str) {
        return str != null && str.matches("([+-])?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    static final void zzz(zzfs zzfs, String str, Object obj) {
        List zzp = zzfs.zzp();
        int i = 0;
        while (true) {
            if (i >= zzp.size()) {
                i = -1;
                break;
            } else if (str.equals(((zzfx) zzp.get(i)).zzg())) {
                break;
            } else {
                i++;
            }
        }
        zzfw zze = zzfx.zze();
        zze.zzj(str);
        if (obj instanceof Long) {
            zze.zzi(((Long) obj).longValue());
        }
        if (i >= 0) {
            zzfs.zzj(i, zze);
        } else {
            zzfs.zze(zze);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzb() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final long zzd(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        this.zzt.zzv().zzg();
        MessageDigest zzF = zzlo.zzF();
        if (zzF != null) {
            return zzlo.zzp(zzF.digest(bArr));
        }
        this.zzt.zzaA().zzd().zza("Failed to get MD5");
        return 0;
    }

    /* access modifiers changed from: package-private */
    public final Bundle zzf(Map map, boolean z) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj == null) {
                bundle.putString(str, (String) null);
            } else if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (!(obj instanceof ArrayList)) {
                bundle.putString(str, obj.toString());
            } else if (z) {
                ArrayList arrayList = (ArrayList) obj;
                ArrayList arrayList2 = new ArrayList();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    arrayList2.add(zzf((Map) arrayList.get(i), false));
                }
                bundle.putParcelableArray(str, (Parcelable[]) arrayList2.toArray(new Parcelable[0]));
            }
        }
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public final Parcelable zzh(byte[] bArr, Parcelable.Creator creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            return (Parcelable) creator.createFromParcel(obtain);
        } catch (SafeParcelReader.ParseException e) {
            this.zzt.zzaA().zzd().zza("Failed to load parcelable from buffer");
            return null;
        } finally {
            obtain.recycle();
        }
    }

    /* access modifiers changed from: package-private */
    public final zzaw zzi(zzaa zzaa) {
        String str;
        String str2;
        Object obj;
        Bundle zzf = zzf(zzaa.zze(), true);
        if (!zzf.containsKey("_o") || (obj = zzf.get("_o")) == null) {
            str = "app";
        } else {
            str = obj.toString();
        }
        String zzb = zzhb.zzb(zzaa.zzd());
        if (zzb == null) {
            str2 = zzaa.zzd();
        } else {
            str2 = zzb;
        }
        return new zzaw(str2, new zzau(zzf), str, zzaa.zza());
    }

    /* access modifiers changed from: package-private */
    public final zzft zzj(zzar zzar) {
        zzfs zze = zzft.zze();
        zze.zzl(zzar.zze);
        zzat zzat = new zzat(zzar.zzf);
        while (zzat.hasNext()) {
            String zza = zzat.next();
            zzfw zze2 = zzfx.zze();
            zze2.zzj(zza);
            Object zzf = zzar.zzf.zzf(zza);
            Preconditions.checkNotNull(zzf);
            zzt(zze2, zzf);
            zze.zze(zze2);
        }
        return (zzft) zze.zzaD();
    }

    /* access modifiers changed from: package-private */
    public final String zzm(zzgb zzgb) {
        Long l;
        Long l2;
        Double d;
        if (zzgb == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        for (zzgd zzgd : zzgb.zzd()) {
            if (zzgd != null) {
                zzF(sb, 1);
                sb.append("bundle {\n");
                if (zzgd.zzbl()) {
                    zzI(sb, 1, "protocol_version", Integer.valueOf(zzgd.zzd()));
                }
                zzqr.zzc();
                if (this.zzt.zzf().zzs(zzgd.zzy(), zzeh.zzao) && zzgd.zzbo()) {
                    zzI(sb, 1, "session_stitching_token", zzgd.zzL());
                }
                zzI(sb, 1, "platform", zzgd.zzJ());
                if (zzgd.zzbh()) {
                    zzI(sb, 1, "gmp_version", Long.valueOf(zzgd.zzm()));
                }
                if (zzgd.zzbt()) {
                    zzI(sb, 1, "uploading_gmp_version", Long.valueOf(zzgd.zzs()));
                }
                if (zzgd.zzbf()) {
                    zzI(sb, 1, "dynamite_version", Long.valueOf(zzgd.zzj()));
                }
                if (zzgd.zzbc()) {
                    zzI(sb, 1, "config_version", Long.valueOf(zzgd.zzh()));
                }
                zzI(sb, 1, "gmp_app_id", zzgd.zzG());
                zzI(sb, 1, "admob_app_id", zzgd.zzx());
                zzI(sb, 1, "app_id", zzgd.zzy());
                zzI(sb, 1, SharePreferenceUtils.KEY_APP_VERSION, zzgd.zzB());
                if (zzgd.zzba()) {
                    zzI(sb, 1, "app_version_major", Integer.valueOf(zzgd.zza()));
                }
                zzI(sb, 1, "firebase_instance_id", zzgd.zzF());
                if (zzgd.zzbe()) {
                    zzI(sb, 1, "dev_cert_hash", Long.valueOf(zzgd.zzi()));
                }
                zzI(sb, 1, "app_store", zzgd.zzA());
                if (zzgd.zzbs()) {
                    zzI(sb, 1, "upload_timestamp_millis", Long.valueOf(zzgd.zzr()));
                }
                if (zzgd.zzbp()) {
                    zzI(sb, 1, "start_timestamp_millis", Long.valueOf(zzgd.zzp()));
                }
                if (zzgd.zzbg()) {
                    zzI(sb, 1, "end_timestamp_millis", Long.valueOf(zzgd.zzk()));
                }
                if (zzgd.zzbk()) {
                    zzI(sb, 1, "previous_bundle_start_timestamp_millis", Long.valueOf(zzgd.zzo()));
                }
                if (zzgd.zzbj()) {
                    zzI(sb, 1, "previous_bundle_end_timestamp_millis", Long.valueOf(zzgd.zzn()));
                }
                zzI(sb, 1, "app_instance_id", zzgd.zzz());
                zzI(sb, 1, "resettable_device_id", zzgd.zzK());
                zzI(sb, 1, "ds_id", zzgd.zzE());
                if (zzgd.zzbi()) {
                    zzI(sb, 1, "limited_ad_tracking", Boolean.valueOf(zzgd.zzaY()));
                }
                zzI(sb, 1, "os_version", zzgd.zzI());
                zzI(sb, 1, "device_model", zzgd.zzD());
                zzI(sb, 1, "user_default_language", zzgd.zzM());
                if (zzgd.zzbr()) {
                    zzI(sb, 1, "time_zone_offset_minutes", Integer.valueOf(zzgd.zzf()));
                }
                if (zzgd.zzbb()) {
                    zzI(sb, 1, "bundle_sequential_index", Integer.valueOf(zzgd.zzb()));
                }
                if (zzgd.zzbn()) {
                    zzI(sb, 1, "service_upload", Boolean.valueOf(zzgd.zzaZ()));
                }
                zzI(sb, 1, "health_monitor", zzgd.zzH());
                if (zzgd.zzbm()) {
                    zzI(sb, 1, IjkMediaPlayer.OnNativeInvokeListener.ARG_RETRY_COUNTER, Integer.valueOf(zzgd.zze()));
                }
                if (zzgd.zzbd()) {
                    zzI(sb, 1, "consent_signals", zzgd.zzC());
                }
                zzpw.zzc();
                if (this.zzt.zzf().zzs((String) null, zzeh.zzaE) && zzgd.zzbq()) {
                    zzI(sb, 1, "target_os_version", Long.valueOf(zzgd.zzq()));
                }
                List<zzgm> zzP = zzgd.zzP();
                if (zzP != null) {
                    for (zzgm zzgm : zzP) {
                        if (zzgm != null) {
                            zzF(sb, 2);
                            sb.append("user_property {\n");
                            if (zzgm.zzs()) {
                                l = Long.valueOf(zzgm.zzc());
                            } else {
                                l = null;
                            }
                            zzI(sb, 2, "set_timestamp_millis", l);
                            zzI(sb, 2, "name", this.zzt.zzj().zzf(zzgm.zzf()));
                            zzI(sb, 2, "string_value", zzgm.zzg());
                            if (zzgm.zzr()) {
                                l2 = Long.valueOf(zzgm.zzb());
                            } else {
                                l2 = null;
                            }
                            zzI(sb, 2, "int_value", l2);
                            if (zzgm.zzq()) {
                                d = Double.valueOf(zzgm.zza());
                            } else {
                                d = null;
                            }
                            zzI(sb, 2, "double_value", d);
                            zzF(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<zzfp> zzN = zzgd.zzN();
                if (zzN != null) {
                    for (zzfp zzfp : zzN) {
                        if (zzfp != null) {
                            zzF(sb, 2);
                            sb.append("audience_membership {\n");
                            if (zzfp.zzk()) {
                                zzI(sb, 2, "audience_id", Integer.valueOf(zzfp.zza()));
                            }
                            if (zzfp.zzm()) {
                                zzI(sb, 2, "new_audience", Boolean.valueOf(zzfp.zzj()));
                            }
                            zzH(sb, 2, "current_data", zzfp.zzd());
                            if (zzfp.zzn()) {
                                zzH(sb, 2, "previous_data", zzfp.zze());
                            }
                            zzF(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<zzft> zzO = zzgd.zzO();
                if (zzO != null) {
                    for (zzft zzft : zzO) {
                        if (zzft != null) {
                            zzF(sb, 2);
                            sb.append("event {\n");
                            zzI(sb, 2, "name", this.zzt.zzj().zzd(zzft.zzh()));
                            if (zzft.zzu()) {
                                zzI(sb, 2, "timestamp_millis", Long.valueOf(zzft.zzd()));
                            }
                            if (zzft.zzt()) {
                                zzI(sb, 2, "previous_timestamp_millis", Long.valueOf(zzft.zzc()));
                            }
                            if (zzft.zzs()) {
                                zzI(sb, 2, PictureConfig.EXTRA_DATA_COUNT, Integer.valueOf(zzft.zza()));
                            }
                            if (zzft.zzb() != 0) {
                                zzD(sb, 2, zzft.zzi());
                            }
                            zzF(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                zzF(sb, 1);
                sb.append("}\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final String zzo(zzek zzek) {
        if (zzek == null) {
            return BuildConfig.TRAVIS;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        if (zzek.zzp()) {
            zzI(sb, 0, "filter_id", Integer.valueOf(zzek.zzb()));
        }
        zzI(sb, 0, DbParams.KEY_CHANNEL_EVENT_NAME, this.zzt.zzj().zzd(zzek.zzg()));
        String zzG = zzG(zzek.zzk(), zzek.zzm(), zzek.zzn());
        if (!zzG.isEmpty()) {
            zzI(sb, 0, "filter_type", zzG);
        }
        if (zzek.zzo()) {
            zzJ(sb, 1, "event_count_filter", zzek.zzf());
        }
        if (zzek.zza() > 0) {
            sb.append("  filters {\n");
            for (zzem zzE : zzek.zzh()) {
                zzE(sb, 2, zzE);
            }
        }
        zzF(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final String zzp(zzet zzet) {
        if (zzet == null) {
            return BuildConfig.TRAVIS;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        if (zzet.zzj()) {
            zzI(sb, 0, "filter_id", Integer.valueOf(zzet.zza()));
        }
        zzI(sb, 0, "property_name", this.zzt.zzj().zzf(zzet.zze()));
        String zzG = zzG(zzet.zzg(), zzet.zzh(), zzet.zzi());
        if (!zzG.isEmpty()) {
            zzI(sb, 0, "filter_type", zzG);
        }
        zzE(sb, 1, zzet.zzb());
        sb.append("}\n");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final List zzq(List list, List list2) {
        ArrayList arrayList = new ArrayList(list);
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() < 0) {
                this.zzt.zzaA().zzk().zzb("Ignoring negative bit index to be cleared", num);
            } else {
                int intValue = num.intValue() / 64;
                if (intValue >= arrayList.size()) {
                    this.zzt.zzaA().zzk().zzc("Ignoring bit index greater than bitSet size", num, Integer.valueOf(arrayList.size()));
                } else {
                    arrayList.set(intValue, Long.valueOf(((Long) arrayList.get(intValue)).longValue() & (~(1 << (num.intValue() % 64)))));
                }
            }
        }
        int size = arrayList.size();
        int size2 = arrayList.size() - 1;
        while (size2 >= 0 && ((Long) arrayList.get(size2)).longValue() == 0) {
            size = size2;
            size2--;
        }
        return arrayList.subList(0, size);
    }

    /* access modifiers changed from: package-private */
    public final Map zzs(Bundle bundle, boolean z) {
        HashMap hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            boolean z2 = obj instanceof Parcelable[];
            if (z2 || (obj instanceof ArrayList) || (obj instanceof Bundle)) {
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    if (z2) {
                        for (Parcelable parcelable : (Parcelable[]) obj) {
                            if (parcelable instanceof Bundle) {
                                arrayList.add(zzs((Bundle) parcelable, false));
                            }
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList2 = (ArrayList) obj;
                        int size = arrayList2.size();
                        for (int i = 0; i < size; i++) {
                            Object obj2 = arrayList2.get(i);
                            if (obj2 instanceof Bundle) {
                                arrayList.add(zzs((Bundle) obj2, false));
                            }
                        }
                    } else if (obj instanceof Bundle) {
                        arrayList.add(zzs((Bundle) obj, false));
                    }
                    hashMap.put(str, arrayList);
                }
            } else if (obj != null) {
                hashMap.put(str, obj);
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public final void zzt(zzfw zzfw, Object obj) {
        Preconditions.checkNotNull(obj);
        zzfw.zzg();
        zzfw.zze();
        zzfw.zzd();
        zzfw.zzf();
        if (obj instanceof String) {
            zzfw.zzk((String) obj);
        } else if (obj instanceof Long) {
            zzfw.zzi(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            zzfw.zzh(((Double) obj).doubleValue());
        } else if (obj instanceof Bundle[]) {
            ArrayList arrayList = new ArrayList();
            for (Bundle bundle : (Bundle[]) obj) {
                if (bundle != null) {
                    zzfw zze = zzfx.zze();
                    for (String str : bundle.keySet()) {
                        zzfw zze2 = zzfx.zze();
                        zze2.zzj(str);
                        Object obj2 = bundle.get(str);
                        if (obj2 instanceof Long) {
                            zze2.zzi(((Long) obj2).longValue());
                        } else if (obj2 instanceof String) {
                            zze2.zzk((String) obj2);
                        } else if (obj2 instanceof Double) {
                            zze2.zzh(((Double) obj2).doubleValue());
                        }
                        zze.zzc(zze2);
                    }
                    if (zze.zza() > 0) {
                        arrayList.add((zzfx) zze.zzaD());
                    }
                }
            }
            zzfw.zzb(arrayList);
        } else {
            this.zzt.zzaA().zzd().zzb("Ignoring invalid (type) event param value", obj);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzu(zzgl zzgl, Object obj) {
        Preconditions.checkNotNull(obj);
        zzgl.zzc();
        zzgl.zzb();
        zzgl.zza();
        if (obj instanceof String) {
            zzgl.zzh((String) obj);
        } else if (obj instanceof Long) {
            zzgl.zze(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            zzgl.zzd(((Double) obj).doubleValue());
        } else {
            this.zzt.zzaA().zzd().zzb("Ignoring invalid (type) user attribute value", obj);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean zzw(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(this.zzt.zzax().currentTimeMillis() - j) > j2;
    }

    /* access modifiers changed from: package-private */
    public final byte[] zzy(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            this.zzt.zzaA().zzd().zzb("Failed to gzip content", e);
            throw e;
        }
    }
}
