package com.google.android.gms.internal.measurement;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzol implements zzok {
    public static final zzib zzA;
    public static final zzib zzB;
    public static final zzib zzC;
    public static final zzib zzD;
    public static final zzib zzE;
    public static final zzib zzF;
    public static final zzib zzG;
    public static final zzib zzH;
    public static final zzib zzI;
    public static final zzib zzJ;
    public static final zzib zzK;
    public static final zzib zzL;
    public static final zzib zzM;
    public static final zzib zzN;
    public static final zzib zzO;
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;
    public static final zzib zze;
    public static final zzib zzf;
    public static final zzib zzg;
    public static final zzib zzh;
    public static final zzib zzi;
    public static final zzib zzj;
    public static final zzib zzk;
    public static final zzib zzl;
    public static final zzib zzm;
    public static final zzib zzn;
    public static final zzib zzo;
    public static final zzib zzp;
    public static final zzib zzq;
    public static final zzib zzr;
    public static final zzib zzs;
    public static final zzib zzt;
    public static final zzib zzu;
    public static final zzib zzv;
    public static final zzib zzw;
    public static final zzib zzx;
    public static final zzib zzy;
    public static final zzib zzz;

    static {
        zzhy zza2 = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zza2.zzd("measurement.ad_id_cache_time", 10000);
        zzb = zza2.zzd("measurement.app_uninstalled_additional_ad_id_cache_time", CostTimeUtil.HOUR);
        zzc = zza2.zzd("measurement.max_bundles_per_iteration", 100);
        zzd = zza2.zzd("measurement.config.cache_time", CostTimeUtil.DAY);
        zze = zza2.zze("measurement.log_tag", "FA");
        zzf = zza2.zze("measurement.config.url_authority", "app-measurement.com");
        zzg = zza2.zze("measurement.config.url_scheme", "https");
        zzh = zza2.zzd("measurement.upload.debug_upload_interval", 1000);
        zzi = zza2.zzd("measurement.lifetimevalue.max_currency_tracked", 4);
        zzj = zza2.zzd("measurement.store.max_stored_events_per_app", 100000);
        zzk = zza2.zzd("measurement.experiment.max_ids", 50);
        zzl = zza2.zzd("measurement.audience.filter_result_max_count", 200);
        zzm = zza2.zzd("measurement.upload.max_item_scoped_custom_parameters", 27);
        zzn = zza2.zzd("measurement.alarm_manager.minimum_interval", 60000);
        zzo = zza2.zzd("measurement.upload.minimum_delay", 500);
        zzp = zza2.zzd("measurement.monitoring.sample_period_millis", CostTimeUtil.DAY);
        zzq = zza2.zzd("measurement.upload.realtime_upload_interval", 10000);
        zzr = zza2.zzd("measurement.upload.refresh_blacklisted_config_interval", 604800000);
        zzs = zza2.zzd("measurement.config.cache_time.service", CostTimeUtil.HOUR);
        zzt = zza2.zzd("measurement.service_client.idle_disconnect_millis", KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        zzu = zza2.zze("measurement.log_tag.service", "FA-SVC");
        zzv = zza2.zzd("measurement.upload.stale_data_deletion_interval", CostTimeUtil.DAY);
        zzw = zza2.zzd("measurement.sdk.attribution.cache.ttl", 604800000);
        zzx = zza2.zzd("measurement.redaction.app_instance_id.ttl", 7200000);
        zzy = zza2.zzd("measurement.upload.backoff_period", 43200000);
        zzz = zza2.zzd("measurement.upload.initial_upload_delay_time", 15000);
        zzA = zza2.zzd("measurement.upload.interval", CostTimeUtil.HOUR);
        zzB = zza2.zzd("measurement.upload.max_bundle_size", 65536);
        zzC = zza2.zzd("measurement.upload.max_bundles", 100);
        zzD = zza2.zzd("measurement.upload.max_conversions_per_day", 500);
        zzE = zza2.zzd("measurement.upload.max_error_events_per_day", 1000);
        zzF = zza2.zzd("measurement.upload.max_events_per_bundle", 1000);
        zzG = zza2.zzd("measurement.upload.max_events_per_day", 100000);
        zzH = zza2.zzd("measurement.upload.max_public_events_per_day", 50000);
        zzI = zza2.zzd("measurement.upload.max_queue_time", 2419200000L);
        zzJ = zza2.zzd("measurement.upload.max_realtime_events_per_day", 10);
        zzK = zza2.zzd("measurement.upload.max_batch_size", 65536);
        zzL = zza2.zzd("measurement.upload.retry_count", 6);
        zzM = zza2.zzd("measurement.upload.retry_time", 1800000);
        zzN = zza2.zze("measurement.upload.url", "https://app-measurement.com/a");
        zzO = zza2.zzd("measurement.upload.window_interval", CostTimeUtil.HOUR);
    }

    public final long zzA() {
        return ((Long) zzF.zzb()).longValue();
    }

    public final long zzB() {
        return ((Long) zzG.zzb()).longValue();
    }

    public final long zzC() {
        return ((Long) zzH.zzb()).longValue();
    }

    public final long zzD() {
        return ((Long) zzI.zzb()).longValue();
    }

    public final long zzE() {
        return ((Long) zzJ.zzb()).longValue();
    }

    public final long zzF() {
        return ((Long) zzK.zzb()).longValue();
    }

    public final long zzG() {
        return ((Long) zzL.zzb()).longValue();
    }

    public final long zzH() {
        return ((Long) zzM.zzb()).longValue();
    }

    public final long zzI() {
        return ((Long) zzO.zzb()).longValue();
    }

    public final String zzJ() {
        return (String) zzf.zzb();
    }

    public final String zzK() {
        return (String) zzg.zzb();
    }

    public final String zzL() {
        return (String) zzN.zzb();
    }

    public final long zza() {
        return ((Long) zza.zzb()).longValue();
    }

    public final long zzb() {
        return ((Long) zzb.zzb()).longValue();
    }

    public final long zzc() {
        return ((Long) zzc.zzb()).longValue();
    }

    public final long zzd() {
        return ((Long) zzd.zzb()).longValue();
    }

    public final long zze() {
        return ((Long) zzh.zzb()).longValue();
    }

    public final long zzf() {
        return ((Long) zzi.zzb()).longValue();
    }

    public final long zzg() {
        return ((Long) zzj.zzb()).longValue();
    }

    public final long zzh() {
        return ((Long) zzk.zzb()).longValue();
    }

    public final long zzi() {
        return ((Long) zzl.zzb()).longValue();
    }

    public final long zzj() {
        return ((Long) zzm.zzb()).longValue();
    }

    public final long zzk() {
        return ((Long) zzn.zzb()).longValue();
    }

    public final long zzl() {
        return ((Long) zzo.zzb()).longValue();
    }

    public final long zzm() {
        return ((Long) zzp.zzb()).longValue();
    }

    public final long zzn() {
        return ((Long) zzq.zzb()).longValue();
    }

    public final long zzo() {
        return ((Long) zzr.zzb()).longValue();
    }

    public final long zzp() {
        return ((Long) zzt.zzb()).longValue();
    }

    public final long zzq() {
        return ((Long) zzv.zzb()).longValue();
    }

    public final long zzr() {
        return ((Long) zzw.zzb()).longValue();
    }

    public final long zzs() {
        return ((Long) zzx.zzb()).longValue();
    }

    public final long zzt() {
        return ((Long) zzy.zzb()).longValue();
    }

    public final long zzu() {
        return ((Long) zzz.zzb()).longValue();
    }

    public final long zzv() {
        return ((Long) zzA.zzb()).longValue();
    }

    public final long zzw() {
        return ((Long) zzB.zzb()).longValue();
    }

    public final long zzx() {
        return ((Long) zzC.zzb()).longValue();
    }

    public final long zzy() {
        return ((Long) zzD.zzb()).longValue();
    }

    public final long zzz() {
        return ((Long) zzE.zzb()).longValue();
    }
}
