package com.simicart.theme.materialtheme.checkout.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.CouponCodeEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.CouponCodeModel;
import com.simicart.core.checkout.model.RemoveCouponCodeModel;
import com.simicart.core.checkout.model.ReviewOrderModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialViewOrderDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialViewOrderManagerDelegate;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialViewOrderController extends SimiController {
    protected MaterialViewOrderDelegate mDelegate;
    protected MaterialViewOrderManagerDelegate mViewOrderManagerDelegate;
    protected TextView.OnEditorActionListener onCouponCodeListener;
    protected TextWatcher onCouponCodeChange;
    protected View.OnClickListener onRemoveCoupon;
    private boolean checkCouponCode = false;
    protected String couponCode = "";
    private int SHOW_REMOVE_COUPONCODE = 0;
    private int HIDEN_REMOVE_COUPONCODE = 1;

    public void setDelegate(MaterialViewOrderDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setViewOrderManagerDelegate(MaterialViewOrderManagerDelegate mViewOrderManagerDelegate) {
        this.mViewOrderManagerDelegate = mViewOrderManagerDelegate;
    }

    public TextView.OnEditorActionListener getOnCouponCodeListener() {
        return onCouponCodeListener;
    }

    public TextWatcher getOnCouponCodeChange() {
        return onCouponCodeChange;
    }

    public View.OnClickListener getOnRemoveCoupon() {
        return onRemoveCoupon;
    }

    @Override
    public void onStart() {
        actionCouponCode();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    public void getOrderInformation() {
        mDelegate.showDialogLoading();
        mModel = new ReviewOrderModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity entity = (QuoteEntity) collection.getCollection().get(0);
                    mDelegate.showCouponCode(entity);
                    ArrayList<CouponCodeEntity> listCouponCode = entity.getCouponCode();
                    if (listCouponCode != null && listCouponCode.size() > 0) {
                        CouponCodeEntity couponCodeEntity = listCouponCode.get(0);
                        couponCode = couponCodeEntity.getCode();
                        checkCouponCode = true;
                    }
                    mViewOrderManagerDelegate.showViewOrder();
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        mModel.request();
    }

    private void actionCouponCode() {
        onCouponCodeListener = new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String code = v.getText().toString().trim();
                    setCouponCode(code);
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(true);
                    Utils.hideKeyboard(v);
                }
                return false;
            }
        };

        onCouponCodeChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().equals("")) {
                    mDelegate.showHidenRemoveCoupon(SHOW_REMOVE_COUPONCODE);
                    couponCode = s.toString().trim();
                } else {
                    mDelegate.showHidenRemoveCoupon(HIDEN_REMOVE_COUPONCODE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        onRemoveCoupon = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCouponCode == true) {
                    if (!couponCode.equals("")) {
                        removeCouponCode(couponCode);
                    }
                } else {
                    mDelegate.removeTextCouponCode();
                }
            }
        };
    }

    protected void setCouponCode(String code) {
        final CouponCodeModel model = new CouponCodeModel();
        mDelegate.showDialogLoading();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was applied", "Ok");
                checkCouponCode = true;
                mDelegate.updateView(collection);
            }
        });
        model.addDataExtendURL("apply");

        model.addDataBody("coupon_code", code);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            model.addDataBody("quote_id", Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            model.addDataBody("quote_id", DataLocal.getQuoteCustomerNotSigin());
        }

        model.request();
    }

    protected void removeCouponCode(String couponCode) {
        mDelegate.showDialogLoading();
        RemoveCouponCodeModel removeCouponCodeModel = new RemoveCouponCodeModel();
        removeCouponCodeModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                SimiManager.getIntance().showNotify(null, "Couponcode was removed", "Ok");
                checkCouponCode = false;
                mDelegate.removeTextCouponCode();
                mDelegate.updateView(collection);
            }
        });
        removeCouponCodeModel.addDataExtendURL("remove");
        removeCouponCodeModel.addDataBody("coupon_code", couponCode);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            removeCouponCodeModel.addDataBody("quote_id", Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            removeCouponCodeModel.addDataBody("quote_id", DataLocal.getQuoteCustomerNotSigin());
        }
        removeCouponCodeModel.request();
    }
}
