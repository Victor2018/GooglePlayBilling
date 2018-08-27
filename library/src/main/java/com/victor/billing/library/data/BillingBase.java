package com.victor.billing.library.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BillingBase.java
 * Author: Victor
 * Date: 2018/8/27 13:41
 * Description:
 * -----------------------------------------------------------------
 */
public class BillingBase {
    private Context context;

    BillingBase(Context context)
    {
        this.context = context;
    }

    Context getContext()
    {
        return context;
    }

    String getPreferencesBaseKey()
    {
        return getContext().getPackageName() + "_preferences";
    }

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    boolean saveString(String key, String value) {
        SharedPreferences sp = getPreferences();
        if (sp != null) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(key, value);
            spe.commit();
            return true;
        }
        return false;
    }

    String loadString(String key, String defValue) {
        SharedPreferences sp = getPreferences();
        if (sp != null) {
            return sp.getString(key, defValue);
        }
        return defValue;
    }

    boolean saveBoolean(String key, Boolean value) {
        SharedPreferences sp = getPreferences();
        if (sp != null) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putBoolean(key, value);
            spe.commit();
            return true;
        }
        return false;
    }

    boolean loadBoolean(String key, boolean defValue) {
        SharedPreferences sp = getPreferences();
        if (sp != null) {
            return sp.getBoolean(key, defValue);
        }
        return defValue;
    }
}
