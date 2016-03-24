package com.simicart.core.catalog.product.block;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.OptionFragment;
import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantsAttributeEntity;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.ButtonRectangle;
import com.simicart.core.style.CirclePageIndicator;
import com.simicart.core.style.VerticalViewPager2;

public class ProductDetailParentBlock extends SimiBlock implements
        ProductDelegate {
    // protected LinearLayout ll_top;
    protected RelativeLayout rlt_top;
    protected LinearLayout ll_bottom;
    protected LinearLayout ll_more;
    protected ButtonRectangle btn_option;
    protected ButtonRectangle btn_addtocart;
    protected TextView tv_name_product;
    protected LinearLayout ll_price;
    protected ProductEntity mProduct;
    protected CirclePageIndicator mIndicator;
    protected OnClickListener onDoneOption;


    public ProductDetailParentBlock(View view, Context context) {
        super(view, context);

    }

    public void setOnDoneOption(OnClickListener onDoneOption) {
        this.onDoneOption = onDoneOption;
    }

    public void setAddToCartListener(OnClickListener listener) {
        btn_addtocart.setOnClickListener(listener);
    }

    public void setDetailListener(OnTouchListener listener) {

        ll_more.setOnTouchListener(listener);
    }

    public void setOptionListener(OnClickListener listener) {
        btn_option.setOnClickListener(listener);
    }

    @SuppressLint("NewApi")
    @Override
    public void initView() {
        rlt_top = (RelativeLayout) mView.findViewById(Rconfig.getInstance().id(
                "ll_top_product_detatil"));
        ll_bottom = (LinearLayout) mView.findViewById(Rconfig.getInstance().id(
                "ll_bottom_product_detail"));
        rlt_top.setBackgroundResource(Rconfig.getInstance().drawable(
                "core_backgroud_top_product_detail"));
        rlt_top.setBackgroundColor(Color.parseColor(Config.getInstance()
                .getSection_color()));
        rlt_top.getBackground().setAlpha(100);
        // details
        ll_more = (LinearLayout) mView.findViewById(Rconfig.getInstance().id(
                "ll_more"));
        ll_more.setVisibility(View.INVISIBLE);

        TextView tv_more = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("tv_more"));
        tv_more.setText(Config.getInstance().getText("More"));
        tv_more.setTextColor(Config.getInstance().getContent_color());
        ImageView img_icon_more = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("img_more"));
        Drawable icon = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("core_icon_more"));
        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_icon_more.setImageDrawable(icon);

		/* 23/11/2015 start Frank: fix bug display "More" length */
        ImageView img_seprate = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("img_seprate"));
        Drawable icon_img_seprate = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("core_background_right_border"));
        icon_img_seprate.setColorFilter(
                Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_seprate.setImageDrawable(icon_img_seprate);
        /* end Frank: fix bug display "More" length */

        // options
        btn_option = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("btn_option"));

        // add to cart
        btn_addtocart = (ButtonRectangle) mView.findViewById(Rconfig
                .getInstance().id("btn_addtocart"));

        // name product
        tv_name_product = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("tv_name_product"));
        // price
        ll_price = (LinearLayout) mView.findViewById(Rconfig.getInstance().id(
                "layout_price"));

        // indicator
        mIndicator = (CirclePageIndicator) mView.findViewById(Rconfig
                .getInstance().id("indicator"));
        mIndicator.setFillColor(Config.getInstance().getKey_color());
        if (DataLocal.isTablet) {
            mIndicator.setScaleX(1.5f);
            mIndicator.setScaleY(1.5f);
        }
        mIndicator.setOrientation(LinearLayout.VERTICAL);
        // = (RelativeLayout)
        // mView.findViewById(Rconfig.getInstance().id("rlt_overlay_left"));
        // rlt_right_overlay = (RelativeLayout)
        // mView.findViewById(Rconfig.getInstance().id("rlt_overlay_right"));
        // rlt_param_left.setMargins(0, Utils.getValueDp(50), 0, 0);
        // // rlt_param_right.setMargins(0, Utils.getValueDp(50), 0, 0);
        // rlt_rlt_left_overlayleft_overlay.setLayoutParams(rlt_param_left);
        // // rlt_right_overlay.setLayoutParams(rlt_param_right);
    }

    @Override
    public void drawView(SimiCollection collection) {

        if (null != collection) {
            mProduct = getProductFromCollection(collection);
            if (null != mProduct) {
                ll_bottom.setVisibility(View.VISIBLE);
                rlt_top.setVisibility(View.VISIBLE);

                showNameProduct();
                initButton();
                showAddToCart();
            }
        }
        ll_more.setVisibility(View.VISIBLE);
    }

    protected void showNameProduct() {
        if (null != mProduct) {
            String name_product = mProduct.getName();
            Log.e("DetailParentBlock", name_product);
            tv_name_product.setVisibility(View.VISIBLE);
            tv_name_product.setTextColor(Config.getInstance()
                    .getContent_color());
            if (Utils.validateString(name_product)) {
                tv_name_product.setText(name_product.trim());
            }
        }
    }

    protected void initButton() {
        Drawable bg_button = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("core_background_button"));
        if (!hasOption()) {
            bg_button.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
            btn_option.setText(Config.getInstance().getText("No Option"));
            btn_option.setTextColor(Color.parseColor("#FFFFFF"));
            btn_option.setClickable(false);
            btn_option.setVisibility(View.GONE);
            btn_option.setTextSize(Constants.SIZE_TEXT_BUTTON);
            btn_option.setBackgroundColor(Config.getInstance().getKey_color());
        } else {
            bg_button.setColorFilter(Config.getInstance().getKey_color(),
                    PorterDuff.Mode.SRC_ATOP);
            btn_option.setVisibility(View.VISIBLE);
            btn_option.setText(Config.getInstance().getText("Options"));
            btn_option.setTextColor(Color.parseColor("#FFFFFF"));
            btn_option.setClickable(true);
            btn_option.setTextSize(Constants.SIZE_TEXT_BUTTON);
            btn_option.setBackgroundColor(Config.getInstance().getKey_color());
        }
    }

    protected void showAddToCart() {
        Drawable bg_button = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("core_background_button"));
        bg_button.setColorFilter(Config.getInstance().getKey_color(),
                PorterDuff.Mode.SRC_ATOP);
        btn_addtocart.setText(Config.getInstance().getText("Add To Cart"));
        btn_addtocart.setTextColor(Color.parseColor("#FFFFFF"));
        btn_addtocart.setBackgroundColor(Config.getInstance()
                .getKey_color());
        btn_addtocart.setTextSize(Constants.SIZE_TEXT_BUTTON);
    }

    @Override
    public void onVisibleTopBottom(boolean isVisible) {
        if (isVisible) {
            if (rlt_top.getVisibility() == View.VISIBLE
                    && ll_bottom.getVisibility() == View.VISIBLE) {
                tv_name_product.setVisibility(View.GONE);
                rlt_top.setVisibility(View.GONE);
                // rlt_param_left.setMargins(0, 0, 0, 0);
                // rlt_param_right.setMargins(0, 0, 0, 0);
                // rlt_left_overlay.setLayoutParams(rlt_param_left);
                // rlt_right_overlay.setLayoutParams(rlt_param_right);
                ll_bottom.setVisibility(View.GONE);
            } else {
                tv_name_product.setVisibility(View.VISIBLE);
                rlt_top.setVisibility(View.VISIBLE);
                // rlt_param_left.setMargins(0, Utils.getValueDp(50), 0, 0);
                // rlt_param_right.setMargins(0, Utils.getValueDp(50), 0, 0);
                // rlt_left_overlay.setLayoutParams(rlt_param_left);
                // rlt_right_overlay.setLayoutParams(rlt_param_right);
                ll_bottom.setVisibility(View.VISIBLE);
                showNameProduct();
            }
        }

    }

    @Override
    public void onUpdatePriceView(View view) {
        if (null != view) {
            ll_price.removeAllViewsInLayout();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            if (DataLocal.isTablet) {
                params.gravity = Gravity.CENTER_HORIZONTAL;
            } else {
                params.gravity = Gravity.LEFT;
            }
            ll_price.addView(view, params);
        }
    }

    @Override
    public void onUpdateOptionView(View view) {
        if (null != view) {
            OptionFragment fragment = OptionFragment.newInstance(view,
                    onDoneOption);
            FragmentTransaction frt = SimiManager.getIntance().getManager()
                    .beginTransaction();
            frt.add(Rconfig.getInstance().id("container"), fragment);
            frt.addToBackStack(null);
            frt.commit();
        }

    }

    public boolean hasOption() {
        boolean has_option = false;
        ProductEntity productEntity = mProduct;
        if (productEntity == null) {
            return false;
        }
        ArrayList<CustomOptionEntity> listCustom = productEntity.getListCustom();
        if (null != listCustom && listCustom.size() > 0) {
            has_option = true;
        }

        ArrayList<VariantEntity> listVariant = productEntity.getVariants();
        ArrayList<VariantsAttributeEntity> listAttribute = productEntity.getVariantAttribute();
        if (null != listAttribute && listAttribute.size() > 0) {
            has_option = true;
        }


        ArrayList<CustomOptionEntity> listBundleItem = productEntity.getListBundleItem();
        if (null != listBundleItem && listBundleItem.size() > 0) {
            has_option = true;
        }

        ArrayList<ItemGroupEntity> listGroupItem = productEntity.getListItemGroup();
        if (null != listGroupItem && listGroupItem.size() > 0) {
            has_option = true;
        }


        return has_option;
    }

    protected ProductEntity getProductFromCollection(SimiCollection collection) {
        ProductEntity product = null;
        ArrayList<SimiEntity> entity = collection.getCollection();
        if (null != entity && entity.size() > 0) {
            product = (ProductEntity) entity.get(0);
        }
        return product;
    }

    @Override
    public String[] getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isShown() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateViewPager(VerticalViewPager2 viewpager) {
        if (null != mIndicator && null != viewpager
                && null != viewpager.getAdapter()) {
            mIndicator.setViewPager(viewpager);
            mIndicator.setCurrentItem(0);
        }
    }

    @Override
    public LinearLayout getLayoutMore() {
        return ll_more;
    }

}
