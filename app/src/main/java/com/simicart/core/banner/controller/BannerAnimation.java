package com.simicart.core.banner.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.URLUtil;
import android.widget.ViewFlipper;

import com.simicart.core.banner.entity.BannerEntity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.entity.CategoryEntity;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.category.model.CategoryModel;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;

@SuppressLint("ClickableViewAccessibility")
public class BannerAnimation {
    View parent_view;
    float lastX;
    ViewFlipper mBannerFlipper;
    Context context;
    protected boolean isAnimation = true;
    private String TYPE_PRODUCT = "1";
    private String TYPE_CATEGORY = "2";
    private String TYPE_WEB = "3";

    public void isAnimation(boolean isAni) {
        isAnimation = isAni;
    }

    public BannerAnimation(View view, ViewFlipper mBannerFlipper) {
        this.parent_view = view;
        this.mBannerFlipper = mBannerFlipper;
        this.mBannerFlipper.setFlipInterval(4500);
        this.context = view.getContext();
        if (isAnimation) {
            this.mBannerFlipper.setInAnimation(view.getContext(), Rconfig
                    .getInstance().getId("in_from_right", "anim"));
            this.mBannerFlipper.setOutAnimation(view.getContext(), Rconfig
                    .getInstance().getId("out_to_left", "anim"));
            this.mBannerFlipper.startFlipping();
        }
    }

    public BannerAnimation(View view, ViewFlipper mBannerFlipper, boolean isAn) {
        this.parent_view = view;
        this.mBannerFlipper = mBannerFlipper;
        this.mBannerFlipper.setFlipInterval(10500);
        this.context = view.getContext();
        isAnimation = isAn;
        if (isAnimation) {
            this.mBannerFlipper.setInAnimation(view.getContext(), Rconfig
                    .getInstance().getId("in_from_right", "anim"));
            this.mBannerFlipper.setOutAnimation(view.getContext(), Rconfig
                    .getInstance().getId("out_to_left", "anim"));
            this.mBannerFlipper.startFlipping();
        }
    }

    public void onTouchEvent(BannerEntity banner, View bannerView) {
        final BannerEntity banner_ad = banner;
        final GestureDetector myGestureListener = new GestureDetector(bannerView.getContext(), new CustomGestureListener());
        bannerView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return myGestureListener.onTouchEvent(event);
            }
        });

        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickBanner(banner_ad);
            }
        });
    }

    private void processClickBanner(BannerEntity banner_ad) {
        String type = banner_ad.getType();
        if (null != type) {
            if (type.equals(TYPE_PRODUCT)) {
                String product_id = banner_ad.getProductId();
                openProductScreen(product_id);
            } else if (type.equals(TYPE_CATEGORY)) {
                String category_id = banner_ad.getCategoryId();
                if (Utils.validateString(category_id)) {
                    requestGetDetailCategory(category_id);
                }
            } else {
                String url = banner_ad.getUrl();
                openWebView(url);
            }
        }
    }

    private void openProductScreen(String product_id) {
        if (Utils.validateString(product_id)) {
            ProductDetailParentFragment fragment = ProductDetailParentFragment
                    .newInstance();
            fragment.setProductID(product_id);
            SimiManager.getIntance().addFragment(
                    fragment);
        }
    }


    private void openWebView(String url) {
        if (Utils.validateString(url) && URLUtil.isValidUrl(url)) {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            } catch (Exception e) {
            }
        }
    }

    private class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean usedThisFling = false;

            // Is it horizontal?
            if (Math.abs(velocityX) >= 2.0 * Math.abs(velocityY)) {
                if (velocityX < 0.0) {
                    mBannerFlipper.setOutAnimation(null);

                    mBannerFlipper.setInAnimation(null);

                    mBannerFlipper.setInAnimation(
                            parent_view.getContext(),
                            Rconfig.getInstance().getId(
                                    "in_from_right", "anim"));
                    mBannerFlipper.setOutAnimation(
                            parent_view.getContext(),
                            Rconfig.getInstance().getId("out_to_left",
                                    "anim"));
                    mBannerFlipper.showPrevious();
                } else {
                    mBannerFlipper.setOutAnimation(null);
                    mBannerFlipper.setInAnimation(null);

                    mBannerFlipper.setInAnimation(
                            parent_view.getContext(),
                            Rconfig.getInstance().getId("in_from_left",
                                    "anim"));
                    mBannerFlipper.setOutAnimation(
                            parent_view.getContext(),
                            Rconfig.getInstance().getId("out_to_right",
                                    "anim"));
                    mBannerFlipper.showNext();
                }
                usedThisFling = true;
            }
            return usedThisFling;
        }
    }

    private void requestGetDetailCategory(String categoryID) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null && collection.getCollection().size() > 0) {
                    CategoryEntity categoryEntity = (CategoryEntity) collection.getCollection().get(0);
                    if (categoryEntity.isHasChild()) {
                        openCategoryScreen(categoryEntity);
                    } else {
                        openCategoryDetail(categoryEntity);
                    }
                }
            }
        });
        categoryModel.addDataExtendURL(categoryID);
        categoryModel.request();
    }

    private void openCategoryScreen(CategoryEntity categoryEntity) {
        String cate_name = categoryEntity.getName();
        String cate_id = categoryEntity.getId();
        if (DataLocal.isTablet) {
            CategoryFragment fragment = CategoryFragment.newInstance(cate_name, cate_id);
            CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(fragment);
            CateSlideMenuFragment.getIntance().openMenu();
        } else {
            CategoryFragment fragment = CategoryFragment.newInstance(cate_name, cate_id);
            SimiManager.getIntance().addFragment(fragment);
        }
    }

    private void openCategoryDetail(CategoryEntity categoryEntity) {

        String cate_id = categoryEntity.getId();
        String cate_name = categoryEntity.getName();

        CategoryDetailFragment fragment = CategoryDetailFragment
                .newInstance();
        fragment.setUrlSearch("categories");
        fragment.setCategoryId(cate_id);
        fragment.setCatName(cate_name);
        if (DataLocal.isTablet) {
            fragment.setTag_search(TagSearch.TAG_GRIDVIEW);
        }
        SimiManager.getIntance().addFragment(fragment);
    }


}
