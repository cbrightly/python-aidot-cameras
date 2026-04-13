package com.didichuxing.doraemonkit.kit.toolpanel;

import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.util.DokitUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ToolPanelUtil.kt */
public final class ToolPanelUtil {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    /* compiled from: ToolPanelUtil.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void jsonConfig2InnerKits(@NotNull String json) {
            String str = json;
            k.f(str, "json");
            Object e = com.blankj.utilcode.util.k.e(str, com.blankj.utilcode.util.k.h(KitGroupBean.class));
            k.b(e, "GsonUtils.fromJson(json,…itGroupBean::class.java))");
            for (KitGroupBean group : (List) e) {
                DokitConstant.GLOBAL_SYSTEM_KITS.put(group.getGroupId(), new ArrayList());
                for (KitBean kitBean : group.getKits()) {
                    try {
                        Object newInstance = Class.forName(kitBean.getAllClassName()).newInstance();
                        if (newInstance != null) {
                            AbstractKit kit = (AbstractKit) newInstance;
                            String string = DokitUtil.getString(kit.getName());
                            k.b(string, "DokitUtil.getString(kit.name)");
                            KitWrapItem kitWrapItem = new KitWrapItem(201, string, kitBean.getChecked(), group.getGroupId(), kit);
                            List list = DokitConstant.GLOBAL_SYSTEM_KITS.get(group.getGroupId());
                            if (list != null) {
                                list.add(kitWrapItem);
                            }
                            String str2 = json;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type com.didichuxing.doraemonkit.kit.AbstractKit");
                        }
                    } catch (Exception e2) {
                    }
                }
                String str3 = json;
            }
            for (String groupId : DokitConstant.GLOBAL_SYSTEM_KITS.keySet()) {
                LinkedHashMap<String, List<KitWrapItem>> linkedHashMap = DokitConstant.GLOBAL_KITS;
                List<KitWrapItem> list2 = DokitConstant.GLOBAL_SYSTEM_KITS.get(groupId);
                if (list2 == null) {
                    k.n();
                }
                k.b(list2, "DokitConstant.GLOBAL_SYSTEM_KITS[groupId]!!");
                linkedHashMap.put(groupId, list2);
            }
        }
    }
}
