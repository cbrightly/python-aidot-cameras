package com.google.android.libraries.places.internal;

import android.text.TextUtils;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfv {
    private static final zzjt zza;

    static {
        zzjs zzjs = new zzjs();
        zzjs.zza(Place.Field.ADDRESS, "formatted_address");
        zzjs.zza(Place.Field.ADDRESS_COMPONENTS, "address_components");
        zzjs.zza(Place.Field.BUSINESS_STATUS, "business_status");
        zzjs.zza(Place.Field.CURBSIDE_PICKUP, "curbside_pickup");
        zzjs.zza(Place.Field.CURRENT_OPENING_HOURS, "current_opening_hours");
        zzjs.zza(Place.Field.DELIVERY, "delivery");
        zzjs.zza(Place.Field.DINE_IN, "dine_in");
        zzjs.zza(Place.Field.ICON_BACKGROUND_COLOR, "icon_background_color");
        zzjs.zza(Place.Field.ICON_URL, "icon_mask_base_uri");
        zzjs.zza(Place.Field.ID, "place_id");
        zzjs.zza(Place.Field.LAT_LNG, "geometry/location");
        zzjs.zza(Place.Field.NAME, "name");
        zzjs.zza(Place.Field.OPENING_HOURS, "opening_hours");
        zzjs.zza(Place.Field.PHONE_NUMBER, "international_phone_number");
        zzjs.zza(Place.Field.PHOTO_METADATAS, "photos");
        zzjs.zza(Place.Field.PLUS_CODE, PlaceTypes.PLUS_CODE);
        zzjs.zza(Place.Field.PRICE_LEVEL, "price_level");
        zzjs.zza(Place.Field.RATING, "rating");
        zzjs.zza(Place.Field.RESERVABLE, "reservable");
        zzjs.zza(Place.Field.SECONDARY_OPENING_HOURS, "secondary_opening_hours");
        zzjs.zza(Place.Field.SERVES_BEER, "serves_beer");
        zzjs.zza(Place.Field.SERVES_BREAKFAST, "serves_breakfast");
        zzjs.zza(Place.Field.SERVES_BRUNCH, "serves_brunch");
        zzjs.zza(Place.Field.SERVES_DINNER, "serves_dinner");
        zzjs.zza(Place.Field.SERVES_LUNCH, "serves_lunch");
        zzjs.zza(Place.Field.SERVES_VEGETARIAN_FOOD, "serves_vegetarian_food");
        zzjs.zza(Place.Field.SERVES_WINE, "serves_wine");
        zzjs.zza(Place.Field.TAKEOUT, "takeout");
        zzjs.zza(Place.Field.TYPES, "types");
        zzjs.zza(Place.Field.USER_RATINGS_TOTAL, "user_ratings_total");
        zzjs.zza(Place.Field.UTC_OFFSET, "utc_offset");
        zzjs.zza(Place.Field.VIEWPORT, "geometry/viewport");
        zzjs.zza(Place.Field.WEBSITE_URI, "website");
        zzjs.zza(Place.Field.WHEELCHAIR_ACCESSIBLE_ENTRANCE, "wheelchair_accessible_entrance");
        zza = zzjs.zzb();
    }

    public static String zza(List list) {
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) zza.get((Place.Field) it.next());
            if (!TextUtils.isEmpty(str)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static List zzb(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) zza.get((Place.Field) it.next());
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}
