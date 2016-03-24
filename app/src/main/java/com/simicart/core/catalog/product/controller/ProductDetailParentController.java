package com.simicart.core.catalog.product.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.delegate.ProductDetailAdapterDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.InformationFragment;
import com.simicart.core.catalog.product.fragment.OptionFragment;
import com.simicart.core.catalog.product.model.ProductDetailModel;
import com.simicart.core.catalog.product.options.ManageOptionView;
import com.simicart.core.config.Constants;
import com.simicart.core.style.VerticalViewPager2;

import java.util.List;

@SuppressLint("DefaultLocale")
public class ProductDetailParentController extends ProductController {

    protected OnClickListener onTouchAddToCart;
    protected OnTouchListener onTouchDetails;
    protected OnClickListener onTouchOptions;
    protected OnClickListener onDoneClick;

    protected boolean fromScan = false;
    protected boolean hasOption = false;
    protected boolean statusTopBottom = true;
    protected ProductDetailAdapterDelegate mAdapterDelegate;
    private boolean checkOptionDerect = false;
    public static  boolean isShownOption;

    protected  View view;

    public void setAdapterDelegate(ProductDetailAdapterDelegate delegate) {
        mAdapterDelegate = delegate;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setHasOption(boolean hasOption) {
        this.hasOption = hasOption;
    }

    public void setFromScan(boolean fromScan) {
        this.fromScan = fromScan;
    }

    public OnClickListener getTouchAddToCart() {
        return onTouchAddToCart;
    }

    public OnClickListener getOnDoneClick() {
        return onDoneClick;
    }

    public OnTouchListener getTouchDetails() {
        return onTouchDetails;
    }

    public OnClickListener getTouchOptions() {
        return onTouchOptions;
    }

    public void setProductDelegate(ProductDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        initOnTouchListener();
    }

    @Override
    public void onResume() {
        isShownOption = false;
        mDelegate.updateView(mModel.getCollection());
        onUpdatePriceView();
        initBack();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initOnTouchListener() {

        initBack();

        onTouchAddToCart = new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkOptionDerect = false;
                onAddToCart();
            }
        };


        onTouchDetails = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // v.setBackgroundColor(Color.GRAY);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // onShowOptionView();
                        onShowDetail();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        // v.setBackgroundResource(Rconfig.getInstance().getIdDraw(
                        // "core_background_left_border"));
                    }
                    default:
                        break;
                }
                return true;
            }
        };

        onTouchOptions = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mProduct != null)
                    //mProduct.setAddedPriceDependent(false);
                    checkOptionDerect = true;
                onShowOption();
            }
        };

        onDoneClick = new OnClickListener() {

            @Override
            public void onClick(View v) {
                SimiManager.getIntance().getManager().popBackStack();
                onAddToCart();
            }
        };
    }

    public void initBack() {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        List<Fragment> list_fragmens = SimiManager.getIntance().getManager().getFragments();
                        if(list_fragmens.get(list_fragmens.size() - 1) instanceof OptionFragment) {
                            SimiManager.getIntance().getManager().popBackStack();
                            isShownOption = false;
                        } else if(fromScan == true && isShownOption == false) {
                            Intent intent = new Intent("com.simicart.leftmenu.mainactivity.onbackpress.backtoscan");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constants.ENTITY, "Scan Now");
                            intent.putExtra(Constants.DATA, bundle);
                            LocalBroadcastManager.getInstance(SimiManager.getIntance().getCurrentContext()).sendBroadcastSync(intent);
                            SimiManager.getIntance().getManager().popBackStack();

                            fromScan = false;
                        } else {
                            SimiManager.getIntance().backPreviousFragment();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }


    @SuppressLint("LongLogTag")
    protected void onShowOption() {
        if(!isShownOption) {
            isShownOption = true;
            onUpdatePriceView((ProductDetailModel)mModel);
            View view_option = onShowOptionView();
            mDelegate.onUpdateOptionView(view_option);
        }
    }


    public void onAddToCart() {
        SimiManager.getIntance().hideKeyboard();
        addtoCart();
    }


    protected void onShowDetail() {
        InformationFragment fragment = InformationFragment.newInstance();
        if (null != priceView) {
            Log.e("Parent Controller ","set Price View Basic");
            fragment.setPriceViewBasic(priceView);
        }
        fragment.setProduct(getProductFromCollection());
        SimiManager.getIntance().addPopupFragment(fragment);
    }

    @SuppressLint("LongLogTag")
    public void onUpdateTopBottom(ProductDetailModel model) {
        if (statusTopBottom) {
            mModel = model;

            onUpdatePriceView(model);
            mProduct = getProductFromCollection();
            mManageOptionView = new ManageOptionView(mProduct);
            mDelegate.updateView(model.getCollection());

//            Auto add to cart
//            if(fromScan == true && hasOption == false) {
//                onAddToCart();
//            } else if(fromScan == true && hasOption == true) {
//                onShowOption();
//            }
        }
    }

    protected void onUpdatePriceView(ProductDetailModel model) {
        ProductEntity product = getProductFromCollection();
        if (null != product) {
            View view = onShowPriceView(product);
            if (null != view) {
                mDelegate.onUpdatePriceView(view);
            }
        }
    }


    public void onVisibleTopBottom(boolean isVisible) {
        statusTopBottom = !statusTopBottom;
        mDelegate.onVisibleTopBottom(isVisible);

    }

    public void updateViewPager(VerticalViewPager2 viewpager) {
        mDelegate.updateViewPager(viewpager);
    }

}
