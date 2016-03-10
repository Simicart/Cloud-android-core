package com.simicart.core.notification.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.simicart.MainActivity;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.GPSTracker;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.notification.GCMIntentService;
import com.simicart.core.notification.common.ServerUtilities;
import com.simicart.core.notification.entity.NotificationEntity;
import com.simicart.core.notification.fragment.WebviewFragment;
import com.simicart.core.notification.gcm.GCMRegistrar;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;

import java.io.IOException;

public class NotificationController {


    FragmentActivity mActivity;
    Context mContext;

    String regId;
    GoogleCloudMessaging gcm;

    AsyncTask<Void, Void, Void> mRegisterTask;

    public NotificationController(FragmentActivity activity) {
        this.mActivity = activity;
        this.mContext = MainActivity.context;

        gcm = GoogleCloudMessaging.getInstance(mContext);

        GPSTracker gps = new GPSTracker(activity);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
        }
    }

    public void registerNotification() {
        checkNotNull(Config.getInstance().getSenderId(), "SENDER_ID");
        GCMRegistrar.checkDevice(mContext);
        GCMRegistrar.checkManifest(mContext);

        regId = GCMRegistrar.getRegistrationId(mContext);
        // send deviceid to server
        if (regId.equals("")) {
            // Automatically registers application on startup.
            GCMRegistrar.register(mContext, Config.getInstance().getSenderId());
        } else {
            // Device is already registered on GCM, check server.
            if (!GCMRegistrar.isRegisteredOnServer(mContext)) {
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        ServerUtilities.register(mContext, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    "Please set the %1$s constant and recompile the app."
                            + name);
        }
    }

    public void showNotification(final Activity activity,
                                 final NotificationEntity notificationData) {

        Dialog alertboxDowload = new Dialog(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(
                Rconfig.getInstance().layout("core_notification_layout"), null);

        ImageView im_notification = (ImageView) view.findViewById(Rconfig
                .getInstance().id("im_notification"));
        TextView tv_notification = (TextView) view.findViewById(Rconfig
                .getInstance().id("tv_notification"));


        String url_image = notificationData.getImage();

        if (Utils.validateString(url_image)) {
            im_notification.setVisibility(View.VISIBLE);
            DrawableManager.fetchDrawableOnThread(url_image,
                    im_notification);
        } else {
            im_notification.setVisibility(View.GONE);
        }


        String mes = notificationData.getMessage();

        if (Utils.validateString(mes)) {
            tv_notification.setVisibility(View.VISIBLE);
            if (im_notification.getVisibility() == View.VISIBLE) {
                if (mes.length() > 253)
                    mes = mes.substring(0, 250) + "...";
            } else {
                if (mes.length() > 503)
                    mes = mes.substring(0, 500) + "...";
            }
            tv_notification.setText(mes);
        } else {
            tv_notification.setVisibility(View.GONE);
        }

        alertboxDowload.setContentView(view);
        alertboxDowload.setTitle(Config.getInstance().getText(
                notificationData.getTitle()));
        alertboxDowload.setCancelable(false);

        TextView tv_close = (TextView) view.findViewById(Rconfig.getInstance()
                .id("tv_close"));
        tv_close.setText(Config.getInstance().getText("CLOSE"));
        tv_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.finish();
                GCMIntentService.notificationData = null;
            }
        });
        TextView tv_show = (TextView) view.findViewById(Rconfig.getInstance()
                .id("tv_show"));
        tv_show.setText(Config.getInstance().getText("SHOW"));
        tv_show.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.finish();
                GCMIntentService.notificationData = null;
                openNotificationDetail(notificationData);
            }
        });

        alertboxDowload.show();
    }

    public void openNotificationSetting() {
        String message = "";
        if (DataLocal.enableNotification()) {
            DataLocal.saveNotificationSet(false);
            message = "Disable recieve notification";
        } else {
            DataLocal.saveNotificationSet(true);
            message = "Enable recieve notification";
        }

        SimiManager.getIntance().showToast(message);
    }

    protected void openNotificationDetail(NotificationEntity notificationData) {
        SimiFragment fragment = null;
        String type = notificationData.getType();
        if (type.equals("1")) {
            fragment = createProductFragment(notificationData);
        } else if (type.equals("2")) {

            fragment = createCateFragment(notificationData);

        } else {
            fragment = createWebFragment(notificationData);
        }

        if (fragment != null) {
            fragment = SimiManager.getIntance().eventFragment(fragment);
            FragmentTransaction fragmentTransaction = SimiManager.getIntance()
                    .getManager().beginTransaction();
            fragmentTransaction
                    .replace(Rconfig.getInstance().id("container"), fragment)
                    .addToBackStack(null).commitAllowingStateLoss();
        }
    }

    private SimiFragment createProductFragment(NotificationEntity notificationData) {
        ProductDetailParentFragment fragment = null;
        String product_id = notificationData.getProductID();
        if (Utils.validateString(product_id)) {
            fragment = ProductDetailParentFragment.newInstance();
            fragment
                    .setProductID(product_id);
        }

        return fragment;
    }

    private SimiFragment createCateFragment(NotificationEntity notificationData) {
        String category_id = notificationData.getCategoryID();
        String category_name = notificationData.getCategoryName();
        if (Utils.validateString(category_id)) {
            if (notificationData.getHasChild().equals("1")) {
                CategoryFragment fragment = null;
                if (DataLocal.isTablet) {
                    fragment = CategoryFragment.newInstance(category_name, category_id);
                    CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(fragment);
                    CateSlideMenuFragment.getIntance().openMenu();
                } else {
                    fragment = CategoryFragment.newInstance(category_name, category_id);
                }
                return fragment;
            } else {
                CategoryDetailFragment fragment = CategoryDetailFragment.newInstance();
                fragment.setUrlSearch("categories");
                fragment.setCategoryId(category_id);
                fragment.setCatName(category_name);
                return fragment;
            }
        }
        return null;
    }

    private SimiFragment createWebFragment(NotificationEntity notificationData) {
        String url = notificationData.getUrl();
        WebviewFragment fragment = null;
        if (Utils.validateString(url)) {
            if (url.contains("http")) {
                fragment = WebviewFragment.newInstance(url);
            } else {
                fragment = WebviewFragment.newInstance("http://" + url);
            }
        }

        return fragment;
    }


    public void destroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
    }
}
