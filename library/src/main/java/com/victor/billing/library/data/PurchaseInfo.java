package com.victor.billing.library.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PurchaseInfo.java
 * Author: Victor
 * Date: 2018/8/27 13:45
 * Description:
 * -----------------------------------------------------------------
 */
public class PurchaseInfo implements Parcelable {
    private static final String LOG_TAG = "iabv3.purchaseInfo";

    public final String responseData;
    public final String signature;
    public final PurchaseData purchaseData;

    public PurchaseInfo(String responseData, String signature) {
        this.responseData = responseData;
        this.signature = signature;
        this.purchaseData = parseResponseData();
    }

    /**
     * @deprecated dont call it directly, use {@see purchaseData}} instead.
     */
    @Deprecated
    public PurchaseData parseResponseData() {
        try {
            JSONObject json = new JSONObject(responseData);
            PurchaseData data = new PurchaseData();
            data.orderId = json.optString("orderId");
            data.packageName = json.optString("packageName");
            data.productId = json.optString("productId");
            long purchaseTimeMillis = json.optLong("purchaseTime", 0);
            data.purchaseTime = purchaseTimeMillis != 0 ? new Date(purchaseTimeMillis) : null;
            data.purchaseState = PurchaseState.values()[json.optInt("purchaseState", 1)];
            data.developerPayload = json.optString("developerPayload");
            data.purchaseToken = json.getString("purchaseToken");
            data.autoRenewing = json.optBoolean("autoRenewing");
            return data;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed to parse response data", e);
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.responseData);
        dest.writeString(this.signature);
    }

    protected PurchaseInfo(Parcel in) {
        this.responseData = in.readString();
        this.signature = in.readString();
        this.purchaseData = parseResponseData();
    }

    public static final Parcelable.Creator<PurchaseInfo> CREATOR =
        new Parcelable.Creator<PurchaseInfo>() {
            public PurchaseInfo createFromParcel(Parcel source) {
                return new PurchaseInfo(source);
            }
            public PurchaseInfo[] newArray(int size) {
                return new PurchaseInfo[size];
            }
        };
}
