package com.simicart.core.banner.block;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.simicart.core.banner.controller.BannerAnimation;
import com.simicart.core.banner.delegate.BannerDelegate;
import com.simicart.core.banner.entity.BannerEntity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class BannerBlock extends SimiBlock implements BannerDelegate {

    protected ViewFlipper mBannerFlipper;
    protected View mView;
    protected Context mContext;

    public BannerBlock(View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setRootView(View rootView) {
        mView = rootView;
    }

    @Override
    public void initView() {
        mBannerFlipper = (ViewFlipper) mView.findViewById(Rconfig.getInstance()
                .id("banner_slider"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> listBanner = collection.getCollection();
        if (listBanner == null || listBanner.size() == 0) {
            showBannersFake();
        } else {
            showBanners(listBanner);
        }
    }

    private void showBannersFake() {
        for (int i = 0; i < 3; i++) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout bannerView = (LinearLayout) inflater.inflate(Rconfig
                    .getInstance().layout("core_banner_home"), null, false);
            ImageView bannerImageView = (ImageView) bannerView
                    .findViewById(Rconfig.getInstance().id("image_banner_home"));
            if (DataLocal.isTablet) {
                bannerImageView.setScaleType(ScaleType.CENTER_CROP);
            }
            bannerImageView.setImageResource(Rconfig.getInstance().drawable(
                    "fake_banner"));
            bannerImageView.setScaleType(ScaleType.FIT_XY);
            mBannerFlipper.addView(bannerView);
        }
    }

    private void showBanners(ArrayList<SimiEntity> listBanner) {
        Log.e("BannerBlock", "showBanner" + listBanner.size());
        if (listBanner.size() == 1) {
            BannerAnimation bannerAnimation = new BannerAnimation(mView,
                    mBannerFlipper, false);
            bannerAnimation.isAnimation(false);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout bannerView = (LinearLayout) inflater.inflate(Rconfig
                    .getInstance().layout("core_banner_home"), null, false);
            ImageView bannerImageView = (ImageView) bannerView
                    .findViewById(Rconfig.getInstance().id("image_banner_home"));
            BannerEntity entity = (BannerEntity) listBanner.get(0);

            String imagePath = entity.getImage();
            Log.e("BannerBlock","imagePath " + imagePath);
            if (Utils.validateString(imagePath)) {
                DrawableManager.fetchDrawableOnThread(
                        imagePath, bannerImageView);
            }else{
                bannerImageView.setImageDrawable(null);
            }

            mBannerFlipper.addView(bannerView);
            bannerAnimation.onTouchEvent(entity, bannerView);
        } else {
            BannerAnimation bannerAnimation = new BannerAnimation(mView,
                    mBannerFlipper);
            for (int i = 0; i < listBanner.size(); i++) {
                BannerEntity bannerEntity = (BannerEntity) listBanner.get(i);
                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout bannerView = (LinearLayout) inflater.inflate(
                        Rconfig.getInstance().layout("core_banner_home"), null,
                        false);
                ImageView bannerImageView = (ImageView) bannerView
                        .findViewById(Rconfig.getInstance().id(
                                "image_banner_home"));
                if (DataLocal.isTablet) {
                    bannerImageView.setScaleType(ScaleType.CENTER_CROP);
                }
                String imagePath = bannerEntity.getImage();
                if (Utils.validateString(imagePath)) {
                    DrawableManager.fetchDrawableOnThread(
                            imagePath,
                            bannerImageView);
                }
                mBannerFlipper.addView(bannerView);
                bannerAnimation.onTouchEvent(bannerEntity, bannerView);
            }
        }
    }

}
