package com.simicart.plugins.barcode;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.common.entity.IntentEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.slidemenu.SlideMenuData;
import com.simicart.core.slidemenu.entity.ItemNavigation;
import com.simicart.core.slidemenu.entity.ItemNavigation.TypeItem;

public class ScanCode {

    public final String QR_BAR_CODE = "Scan Now";

    Context mContext;
    ArrayList<ItemNavigation> mItems;
    private ScanCodeModel mModel;

    public ScanCode() {
        mContext = SimiManager.getIntance().getCurrentActivity();

        // register event: add navigation item to slide menu
        IntentFilter addItemFilter = new IntentFilter("com.simicart.add.navigation.more.qrbarcode");
        BroadcastReceiver addItemReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SlideMenuData entity = (SlideMenuData) bundle.getSerializable(Constants.ENTITY);
                mItems = entity.getItemNavigations();
                ItemNavigation item = new ItemNavigation();
                item.setType(TypeItem.NORMAL);
                item.setName(Config.getInstance().getText(
                        QR_BAR_CODE));
                int id_icon = Rconfig.getInstance().drawable("ic_barcode");
                Drawable icon = mContext.getResources().getDrawable(id_icon);
                icon.setColorFilter(Color.parseColor("#ffffff"),
                        PorterDuff.Mode.SRC_ATOP);
                item.setIcon(icon);
                mItems.add(item);
                entity.setItemNavigations(mItems);
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(addItemReceiver, addItemFilter);

        // register event: click item from left menu
        IntentFilter onClickItemFilter = new IntentFilter("com.simicart.leftmenu.slidemenucontroller.onnavigate.clickitem");
        BroadcastReceiver onClickItemReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                String leftItemName = (String) bundle.getSerializable(Constants.ENTITY);
                clickItemLeftMenuCode(leftItemName);
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(onClickItemReceiver, onClickItemFilter);

        // register event: on scan result
        IntentFilter onScanResultFilter = new IntentFilter("com.simicart.leftmenu.mainactivity.onactivityresult.resultbarcode");
        BroadcastReceiver onScanResultReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                IntentEntity entity = (IntentEntity) bundle.getSerializable(Constants.ENTITY);
                int requestCode = entity.getRequestCode();
                int resultCode = entity.getResultCode();
                Intent data = entity.getIntent();

                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    if (result.getContents() == null) {
                        for (Fragment fragment : SimiManager.getIntance()
                                .getManager().getFragments()) {
                            if (fragment == null) {
                                break;
                            } else {
                                if (fragment instanceof ProductDetailParentFragment) {
                                    ((ProductDetailParentFragment) fragment).setIsFromScan(false);
                                }
                            }
                        }
                    } else {
                        checkResultBarcode(result.getContents());
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(onScanResultReceiver, onScanResultFilter);

        // register event: on back to scan
        IntentFilter onBackFromDetailFilter = new IntentFilter("com.simicart.leftmenu.mainactivity.onbackpress.backtoscan");
        BroadcastReceiver onBackFromDetailtReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                String item_name = (String) bundle.getSerializable(Constants.ENTITY);
                clickItemLeftMenuCode(item_name);
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(onBackFromDetailtReceiver, onBackFromDetailFilter);
    }

    private void checkResultBarcode(String code) {
        mModel = new ScanCodeModel();
        final ProgressDialog pd_loading = ProgressDialog
                .show(SimiManager.getIntance().getCurrentActivity(), null,
                        null, true, false);
        pd_loading.setContentView(Rconfig.getInstance().layout(
                "core_base_loading"));
        pd_loading.setCanceledOnTouchOutside(false);
        pd_loading.setCancelable(false);
        pd_loading.show();
        mModel.setDelegate(new ModelDelegate() {

            @Override
            public void onFail(SimiError error) {
                pd_loading.dismiss();
                SimiManager.getIntance().showToast(
                        "Result products is empty");
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                pd_loading.dismiss();
                ArrayList<String> listID = new ArrayList<String>();

                ProductEntity product = null;
                ArrayList<SimiEntity> entity = mModel.getCollection().getCollection();
                if (null != entity && entity.size() > 0) {
                    product = (ProductEntity) entity.get(0);
                }
                String product_id = product.getID();

                if (!Utils.validateString(product_id)) {
                    SimiManager.getIntance().showToast(
                            "Result products is empty");
                    return;
                }

                listID.add(product_id);
                ProductDetailParentFragment fragment = ProductDetailParentFragment.newInstance();
                fragment.setListIDProduct(listID);
                fragment.setProductID(product_id);
                fragment.setIsFromScan(true);
                SimiManager.getIntance().replaceFragment(fragment);

            }
        });
        mModel.addDataExtendURL("barcode");
        mModel.addDataExtendURL(code);
        mModel.request();

    }

    private void clickItemLeftMenuCode(String nameItem) {
        if (nameItem.equals(QR_BAR_CODE)) {
            new IntentIntegrator(MainActivity.context).initiateScan();
        }
    }


}
