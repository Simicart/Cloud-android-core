package com.simicart.plugins.braintree;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {

    private static final String SANDBOX_TOKENIZATION_KEY = "sandbox_tmxhyf7d_3yzdm69mrvcpy3z3";

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean useTokenizationKey(Context context) {
        return getPreferences(context).getBoolean("tokenization_key", false);
    }

    public static String getEnvironmentTokenizationKey(Context context) {
        return SANDBOX_TOKENIZATION_KEY;
    }
}
