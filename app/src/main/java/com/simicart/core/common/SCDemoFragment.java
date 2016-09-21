package com.simicart.core.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;

/**
 * Created by frank on 9/20/16.
 */
public class SCDemoFragment extends SimiFragment {

    protected int idImage;
    protected String mTitle;
    protected String mMessage;
    protected SCDialogCallBack mCallBack;
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(Rconfig.getInstance().layout("demo_fragment"), container, false);
        ImageView imgDemo = (ImageView) rootView.findViewById(Rconfig.getInstance().id("img_demo"));
        imgDemo.setImageResource(idImage);
        createSCDialog();
        return rootView;
    }

    protected void createSCDialog() {
        SCDialog scDialog = new SCDialog();
        if (Utils.validateString(mTitle)) {
            scDialog.setTitle(mTitle);
        }

        if (Utils.validateString(mMessage)) {
            scDialog.setMessage(mMessage);
        }

        if (null != mCallBack) {
            scDialog.setCallBack(mCallBack);
        }

        scDialog.createDialog();
        scDialog.show();
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public SCDialogCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(SCDialogCallBack callBack) {
        mCallBack = callBack;
    }
}
