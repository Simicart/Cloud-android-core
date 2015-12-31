package com.simicart.core.catalog.product.controller;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.delegate.ProductDetailAdapterDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.InformationFragment;
import com.simicart.core.catalog.product.model.ProductDetailModel;
import com.simicart.core.catalog.product.options.ManageOptionView;
import com.simicart.core.style.VerticalViewPager2;

@SuppressLint("DefaultLocale")
public class ProductDetailParentController extends ProductController {

    protected OnClickListener onTouchAddToCart;
    protected OnTouchListener onTouchDetails;
    protected OnClickListener onTouchOptions;
    protected OnClickListener onDoneClick;

    protected boolean statusTopBottom = true;
    protected ProductDetailAdapterDelegate mAdapterDelegate;
    private boolean checkOptionDerect = false;

    public void setAdapterDelegate(ProductDetailAdapterDelegate delegate) {
        mAdapterDelegate = delegate;
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
        mDelegate.updateView(mModel.getCollection());
        onUpdatePriceView();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initOnTouchListener() {

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


    @SuppressLint("LongLogTag")
    protected void onShowOption() {
        Log.e("ProductDeatilParentController", "onShowOption");
        View view_option = onShowOptionView();
        mDelegate.onUpdateOptionView(view_option);
    }


    protected void onAddToCart() {
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
