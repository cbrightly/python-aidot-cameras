package smarthome.ui.navigationbar;

import android.text.TextUtils;
import com.leedarson.base.R$drawable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: TabEnum */
public enum j {
    Home("Home", R$drawable.ic_home_nor, R$drawable.ic_home_selected, "device.json"),
    Automations("Automations", R$drawable.ic_auto_nor, R$drawable.ic_auto_selected, "autamation.json"),
    Me("Aidotor", r19, r2, "me.json"),
    Community("Community", R$drawable.ic_community_nor, r13, "community.json"),
    Shop("Shop", R$drawable.ic_shop_nor, r13, "shop.json");
    
    public static ChangeQuickRedirect changeQuickRedirect;
    String icon;
    String lottieFile;
    int normalSrcId;
    int selectedSrcId;
    private String title;

    private j(String title2, int normalSrcId2, int selectedSrcId2, String jsonFile) {
        this.icon = "Default";
        this.title = title2;
        this.normalSrcId = normalSrcId2;
        this.selectedSrcId = selectedSrcId2;
        this.lottieFile = jsonFile;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void setIcon(String icon2) {
        if (!PatchProxy.proxy(new Object[]{icon2}, this, changeQuickRedirect, false, 14295, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.icon = icon2;
            if (!TextUtils.isEmpty(icon2) && !"Default".equals(icon2)) {
                this.lottieFile = icon2 + ".json";
            }
        }
    }

    public int getNormalSrcId() {
        return this.normalSrcId;
    }

    public void setNormalSrcId(int normalSrcId2) {
        this.normalSrcId = normalSrcId2;
    }

    public int getSelectedSrcId() {
        return this.selectedSrcId;
    }

    public void setSelectedSrcId(int selectedSrcId2) {
        this.selectedSrcId = selectedSrcId2;
    }

    public String getLottieFile() {
        return this.lottieFile;
    }
}
