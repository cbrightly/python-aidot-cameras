package com.leedarson.serviceimpl.bodyfat.model;

import com.google.gson.Gson;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONObject;

public class BodyFatDataBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public float BMI = 100.0f;
    public int BMR = 10;
    public float bodyFat = 45.0f;
    public transient int bodyType = 0;
    public float boneMass = 10.0f;
    public float fatContent;
    public float moisture = 10.0f;
    public float muscle = 10.0f;
    public float muscleContent;
    public int obesityLevel = 0;
    public int physicalAge = 10;
    public float protein = 10.0f;
    public float proteinContent;
    public float skeletalMuscle = 10.0f;
    public float standardWeight;
    public float subcutaneousFat = 30.0f;
    public float visceralFat = 10.0f;
    public float waterContent;

    public void calNewParams(float weightKg, int sex, int height) {
        this.muscleContent = (this.muscle * weightKg) / 100.0f;
        this.fatContent = (this.bodyFat * weightKg) / 100.0f;
        this.waterContent = (this.moisture * weightKg) / 100.0f;
        this.proteinContent = (this.protein * weightKg) / 100.0f;
        if (sex == 1) {
            this.standardWeight = ((float) ((height * height) * 22)) / 10000.0f;
        } else {
            this.standardWeight = (((float) (height * height)) * 20.6f) / 10000.0f;
        }
        float f = this.BMI;
        if (((double) f) < 18.5d) {
            this.obesityLevel = 1;
        } else if (f < 25.0f) {
            this.obesityLevel = 2;
        } else if (f < 27.0f) {
            this.obesityLevel = 3;
        } else if (f < 30.0f) {
            this.obesityLevel = 4;
        } else if (f < 35.0f) {
            this.obesityLevel = 5;
        } else if (f < 40.0f) {
            this.obesityLevel = 6;
        } else {
            this.obesityLevel = 7;
        }
    }

    public JSONObject toJSON() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6931, new Class[0], JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        return new JSONObject(new Gson().toJson((Object) this));
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6932, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"BMI\":" + this.BMI + ",\"bodyFat\":" + this.bodyFat + ",\"subcutaneousFat\":" + this.subcutaneousFat + ",\"visceralFat\":" + this.visceralFat + ",\"muscle\":" + this.muscle + ",\"BMR\":" + this.BMR + ",\"boneMass\":" + this.boneMass + ",\"moisture\":" + this.moisture + ",\"physicalAge\":" + this.physicalAge + ",\"protein\":" + this.protein + ",\"skeletalMuscle\":" + this.skeletalMuscle + ",\"muscleContent\":" + this.muscleContent + ",\"fatContent\":" + this.fatContent + ",\"waterContent\":" + this.waterContent + ",\"proteinContent\":" + this.proteinContent + ",\"standardWeight\":" + this.standardWeight + ",\"obesityLevel\":" + this.obesityLevel + ",\"bodyType\":" + this.bodyType + '}';
    }
}
