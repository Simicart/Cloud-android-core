package com.simicart.core.common.entity;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by Martial on 3/18/2016.
 */
public class IntentEntity implements Serializable {
    int requestCode;
    int resultCode;
    Intent intent;

    public IntentEntity(Intent intent, int requestCode, int resultCode) {
        this.intent = intent;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
