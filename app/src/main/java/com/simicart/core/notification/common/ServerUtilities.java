package com.simicart.core.notification.common;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;

import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.core.notification.gcm.GCMRegistrar;
import com.simicart.core.notification.model.RegisterIDModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

    public static boolean register(final Context context, final String regId) {
        RegisterIDModel model = new RegisterIDModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        });
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", 3);
            obj.put("token", regId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.setJSONBody(obj);
        model.request();

        return false;
    }





    public static void unregister(Context context, String regId) {
        // TODO Auto-generated method stub
    }

}
