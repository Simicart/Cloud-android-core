package com.simicart.core.base.network.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;

public class CoreResponse {
    protected String mStatus;
    protected String mMessage;
    protected String mData;
    protected JSONObject mJSON;
    protected SimiError mError;

    public void setSimiError(SimiError error) {
        mError = error;
    }

    public SimiError getSimiError() {
        return mError;
    }

    public void setData(String data) {
        mData = data;
    }

    public JSONObject getDataJSON() {
        return mJSON;
    }

    public void setDataJSON(JSONObject json) {
        mJSON = json;
    }

    public boolean parse() {

        if (null == mData) {
            mError.setMessage(Config.getInstance().getText("Some errors occured. Please try again later"));
            return false;
        }

        try {
            mJSON = new JSONObject(mData);
            Log.e("CoreRespone", mJSON.toString());
            if (mJSON.has("errors")) {
                mError = new SimiError();
                JSONObject jsError = mJSON.getJSONObject("errors");
                String code = jsError.getString("code");
                String message = jsError.getString("message");
                mError.setCodeError(code);
                mError.setMessage(message);
                return false;
            }
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getMessage() {
        if (mMessage == null) {
            mMessage = Config.getInstance().getText(
                    "Some errors occured. Please try again later");
        }
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
