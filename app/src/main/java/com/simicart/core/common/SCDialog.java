package com.simicart.core.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.Button;
import com.simicart.core.material.ButtonRectangle;

/**
 * Created by frank on 9/20/16.
 */
public class SCDialog {

    protected SCDialogCallBack mCallBack;
    protected Context mContext;
    protected String mMessage;
    protected String mTitle;
    protected int idView;
    protected View rootView;
    protected AlertDialog mDialog;
    protected AlertDialog.Builder mDialogBuilder;


    public SCDialog() {
        mContext = SimiManager.getIntance().getCurrentActivity();
        idView = Rconfig.getInstance().layout("demo_sc_dialog");
    }

    public void createDialog() {
        mDialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rootView = inflater.inflate(idView, null, false);
        createView();
        mDialogBuilder.setView(rootView);
        mDialog = mDialogBuilder.show();
    }

    protected void createView() {

        // title
        TextView tvTitle = (TextView) findView("tv_title");
        String title = "Message";
        if (Utils.validateString(mTitle)) {
            title = mTitle;
        }
        tvTitle.setText(title);

        // close dialog
        ImageButton btnClose = (ImageButton) findView("btn_close");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // content
        TextView tvContent = (TextView) findView("tv_body");
        if (Utils.validateString(mMessage)) {
            tvContent.setText(mMessage);
        } else {
            tvContent.setText("This feature can only be accessed with the fully synced version. Please contact SimiCart by email or chat with us");
        }

        // chat with SimiCart
        ButtonRectangle btnChat = (ButtonRectangle) findView("btn_chat");
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatWithSimiCart();
            }
        });

        // contact with SimiCart
        ButtonRectangle btnContact = (ButtonRectangle) findView("btn_contact");
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactWithSimiCart();
            }
        });

    }

    protected void chatWithSimiCart() {
        if (null != mCallBack) {
            mCallBack.onChatWithSimiCart();
        } else {

            dismiss();
        }

    }

    protected void contactWithSimiCart() {
        if (null != mCallBack) {
            mCallBack.onContactSimiCart();
        } else {
            Intent intentEmail = new Intent(Intent.ACTION_SEND);
            intentEmail.setData(Uri.parse("mailto:"));
            intentEmail.setType("message/rfc822");
            intentEmail.putExtra(Intent.EXTRA_EMAIL, "support@simicart.com");
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, Config.getInstance()
                    .getText("Your subject"));
            intentEmail.putExtra(Intent.EXTRA_TEXT,
                    Config.getInstance().getText("Enter your FeedBack"));
            mContext.startActivity(Intent.createChooser(intentEmail, Config
                    .getInstance().getText("Contact SimiCart...")));
            dismiss();
        }
    }

    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    public SCDialogCallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(SCDialogCallBack callBack) {
        mCallBack = callBack;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    protected View findView(String id) {
        int id_view = Rconfig.getInstance().id(id);
        return rootView.findViewById(id_view);
    }
}
