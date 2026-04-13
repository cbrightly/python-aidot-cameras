package com.google.android.gms.measurement.internal;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzeh {
    public static final zzeg zzA = zza("measurement.upload.initial_upload_delay_time", 15000L, 15000L, zzbi.zza);
    public static final zzeg zzB = zza("measurement.upload.retry_time", 1800000L, 1800000L, zzbk.zza);
    public static final zzeg zzC = zza("measurement.upload.retry_count", 6, 6, zzbl.zza);
    public static final zzeg zzD = zza("measurement.upload.max_queue_time", 2419200000L, 2419200000L, zzbm.zza);
    public static final zzeg zzE = zza("measurement.lifetimevalue.max_currency_tracked", 4, 4, zzbo.zza);
    public static final zzeg zzF = zza("measurement.audience.filter_result_max_count", 200, 200, zzbp.zza);
    public static final zzeg zzG = zza("measurement.upload.max_public_user_properties", 25, 25, (zzed) null);
    public static final zzeg zzH = zza("measurement.upload.max_event_name_cardinality", 500, 500, (zzed) null);
    public static final zzeg zzI = zza("measurement.upload.max_public_event_params", 25, 25, (zzed) null);
    public static final zzeg zzJ;
    public static final zzeg zzK = zza("measurement.test.boolean_flag", false, false, zzbr.zza);
    public static final zzeg zzL = zza("measurement.test.string_flag", "---", "---", zzbs.zza);
    public static final zzeg zzM = zza("measurement.test.long_flag", -1L, -1L, zzbt.zza);
    public static final zzeg zzN = zza("measurement.test.int_flag", -2, -2, zzbv.zza);
    public static final zzeg zzO;
    public static final zzeg zzP = zza("measurement.experiment.max_ids", 50, 50, zzbx.zza);
    public static final zzeg zzQ = zza("measurement.upload.max_item_scoped_custom_parameters", 27, 27, zzby.zza);
    public static final zzeg zzR = zza("measurement.max_bundles_per_iteration", 100, 100, zzca.zza);
    public static final zzeg zzS = zza("measurement.sdk.attribution.cache.ttl", 604800000L, 604800000L, zzcb.zza);
    public static final zzeg zzT = zza("measurement.redaction.app_instance_id.ttl", 7200000L, 7200000L, zzcc.zza);
    public static final zzeg zzU = zza("measurement.collection.log_event_and_bundle_v2", true, true, zzcd.zza);
    public static final zzeg zzV = zza("measurement.quality.checksum", false, false, (zzed) null);
    public static final zzeg zzW = zza("measurement.audience.use_bundle_end_timestamp_for_non_sequence_property_filters", false, false, zzce.zza);
    public static final zzeg zzX = zza("measurement.audience.refresh_event_count_filters_timestamp", false, false, zzcg.zza);
    public static final zzeg zzY = zza("measurement.audience.use_bundle_timestamp_for_event_count_filters", false, false, zzch.zza);
    public static final zzeg zzZ = zza("measurement.sdk.collection.retrieve_deeplink_from_bow_2", true, true, zzci.zza);
    public static final zzeg zza = zza("measurement.ad_id_cache_time", 10000L, 10000L, zzbj.zza);
    public static final zzeg zzaA = zza("measurement.item_scoped_custom_parameters.service", false, false, zzdp.zza);
    public static final zzeg zzaB = zza("measurement.remove_app_background.client", false, false, zzdq.zza);
    public static final zzeg zzaC = zza("measurement.rb.attribution.service", false, false, zzdr.zza);
    public static final zzeg zzaD = zza("measurement.collection.client.log_target_api_version", true, true, zzds.zza);
    public static final zzeg zzaE = zza("measurement.collection.service.log_target_api_version", true, true, zzdt.zza);
    public static final zzeg zzaF = zza("measurement.client.deep_link_referrer_fix", true, true, zzdu.zza);
    public static final zzeg zzaG = zza("measurement.client.sessions.enable_fix_background_engagement", false, false, zzdw.zza);
    public static final zzeg zzaH = zza("measurement.link_sst_to_sid", false, false, zzdy.zza);
    /* access modifiers changed from: private */
    public static final List zzaI = Collections.synchronizedList(new ArrayList());
    private static final Set zzaJ = Collections.synchronizedSet(new HashSet());
    public static final zzeg zzaa = zza("measurement.sdk.collection.last_deep_link_referrer_campaign2", false, false, zzcj.zza);
    public static final zzeg zzab = zza("measurement.lifecycle.app_in_background_parameter", false, false, zzck.zza);
    public static final zzeg zzac = zza("measurement.integration.disable_firebase_instance_id", false, false, zzcm.zza);
    public static final zzeg zzad = zza("measurement.collection.service.update_with_analytics_fix", false, false, zzcn.zza);
    public static final zzeg zzae = zza("measurement.client.firebase_feature_rollout.v1.enable", true, true, zzco.zza);
    public static final zzeg zzaf = zza("measurement.client.sessions.check_on_reset_and_enable2", true, true, zzcp.zza);
    public static final zzeg zzag = zza("measurement.collection.synthetic_data_mitigation", false, false, zzcr.zza);
    public static final zzeg zzah = zza("measurement.service.storage_consent_support_version", 203600, 203600, zzcs.zza);
    public static final zzeg zzai = zza("measurement.client.click_identifier_control.dev", false, false, zzct.zza);
    public static final zzeg zzaj = zza("measurement.service.click_identifier_control", false, false, zzcu.zza);
    public static final zzeg zzak = zza("measurement.service.store_null_safelist", true, true, zzcv.zza);
    public static final zzeg zzal = zza("measurement.service.store_safelist", true, true, zzcw.zza);
    public static final zzeg zzam = zza("measurement.collection.enable_session_stitching_token.first_open_fix", true, true, zzcy.zza);
    public static final zzeg zzan = zza("measurement.collection.enable_session_stitching_token.client.dev", true, true, zzcz.zza);
    public static final zzeg zzao = zza("measurement.session_stitching_token_enabled", false, false, zzda.zza);
    public static final zzeg zzap = zza("measurement.sgtm.client.dev", false, false, zzdc.zza);
    public static final zzeg zzaq = zza("measurement.sgtm.service", false, false, zzdd.zza);
    public static final zzeg zzar = zza("measurement.redaction.retain_major_os_version", true, true, zzde.zza);
    public static final zzeg zzas = zza("measurement.redaction.scion_payload_generator", true, true, zzdf.zza);
    public static final zzeg zzat = zza("measurement.service.clear_global_params_on_uninstall", true, true, zzdg.zza);
    public static final zzeg zzau = zza("measurement.sessionid.enable_client_session_id", true, true, zzdh.zza);
    public static final zzeg zzav = zza("measurement.sfmc.client", true, true, zzdi.zza);
    public static final zzeg zzaw = zza("measurement.sfmc.service", true, true, zzdk.zza);
    public static final zzeg zzax = zza("measurement.gmscore_feature_tracking", true, true, zzdl.zza);
    public static final zzeg zzay = zza("measurement.fix_health_monitor_stack_trace", true, true, zzdn.zza);
    public static final zzeg zzaz = zza("measurement.item_scoped_custom_parameters.client", true, true, zzdo.zza);
    public static final zzeg zzb;
    public static final zzeg zzc;
    public static final zzeg zzd;
    public static final zzeg zze = zza("measurement.config.url_scheme", "https", "https", zzcl.zza);
    public static final zzeg zzf = zza("measurement.config.url_authority", "app-measurement.com", "app-measurement.com", zzcx.zza);
    public static final zzeg zzg = zza("measurement.upload.max_bundles", 100, 100, zzdj.zza);
    public static final zzeg zzh = zza("measurement.upload.max_batch_size", 65536, 65536, zzdv.zza);
    public static final zzeg zzi = zza("measurement.upload.max_bundle_size", 65536, 65536, zzdz.zza);
    public static final zzeg zzj = zza("measurement.upload.max_events_per_bundle", 1000, 1000, zzea.zza);
    public static final zzeg zzk = zza("measurement.upload.max_events_per_day", 100000, 100000, zzbu.zza);
    public static final zzeg zzl = zza("measurement.upload.max_error_events_per_day", 1000, 1000, zzcf.zza);
    public static final zzeg zzm;
    public static final zzeg zzn = zza("measurement.upload.max_conversions_per_day", 10000, 10000, zzdb.zza);
    public static final zzeg zzo = zza("measurement.upload.max_realtime_events_per_day", 10, 10, zzdm.zza);
    public static final zzeg zzp = zza("measurement.store.max_stored_events_per_app", 100000, 100000, zzdx.zza);
    public static final zzeg zzq = zza("measurement.upload.url", "https://app-measurement.com/a", "https://app-measurement.com/a", zzeb.zza);
    public static final zzeg zzr = zza("measurement.upload.backoff_period", 43200000L, 43200000L, zzec.zza);
    public static final zzeg zzs;
    public static final zzeg zzt;
    public static final zzeg zzu = zza("measurement.upload.realtime_upload_interval", 10000L, 10000L, zzbc.zza);
    public static final zzeg zzv = zza("measurement.upload.debug_upload_interval", 1000L, 1000L, zzbd.zza);
    public static final zzeg zzw = zza("measurement.upload.minimum_delay", 500L, 500L, zzbe.zza);
    public static final zzeg zzx = zza("measurement.alarm_manager.minimum_interval", 60000L, 60000L, zzbf.zza);
    public static final zzeg zzy;
    public static final zzeg zzz = zza("measurement.upload.refresh_blacklisted_config_interval", 604800000L, 604800000L, zzbh.zza);

    static {
        Long valueOf = Long.valueOf(CostTimeUtil.HOUR);
        zzb = zza("measurement.app_uninstalled_additional_ad_id_cache_time", valueOf, valueOf, zzbb.zza);
        Long valueOf2 = Long.valueOf(CostTimeUtil.DAY);
        zzc = zza("measurement.monitoring.sample_period_millis", valueOf2, valueOf2, zzbn.zza);
        zzd = zza("measurement.config.cache_time", valueOf2, valueOf, zzbz.zza);
        Integer valueOf3 = Integer.valueOf(SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH);
        zzm = zza("measurement.upload.max_public_events_per_day", valueOf3, valueOf3, zzcq.zza);
        zzs = zza("measurement.upload.window_interval", valueOf, valueOf, zzaz.zza);
        zzt = zza("measurement.upload.interval", valueOf, valueOf, zzba.zza);
        zzy = zza("measurement.upload.stale_data_deletion_interval", valueOf2, valueOf2, zzbg.zza);
        Long valueOf4 = Long.valueOf(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        zzJ = zza("measurement.service_client.idle_disconnect_millis", valueOf4, valueOf4, zzbq.zza);
        Double valueOf5 = Double.valueOf(-3.0d);
        zzO = zza("measurement.test.double_flag", valueOf5, valueOf5, zzbw.zza);
    }

    static zzeg zza(String str, Object obj, Object obj2, zzed zzed) {
        zzeg zzeg = new zzeg(str, obj, obj2, zzed, (zzef) null);
        zzaI.add(zzeg);
        return zzeg;
    }
}
