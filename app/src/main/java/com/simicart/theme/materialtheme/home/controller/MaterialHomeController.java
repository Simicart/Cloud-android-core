package com.simicart.theme.materialtheme.home.controller;

import android.graphics.Color;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.core.home.model.SpotProductModel;

import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialHomeController extends SimiController {
    protected MaterialViewPager.Listener onClickTitleViewPager;
    protected ArrayList<SpotProductEntity> listSportProduct;

    protected SimiDelegate mDelegate;

    public MaterialViewPager.Listener getOnClickTitleViewPager() {
        return onClickTitleViewPager;
    }

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {
        requestGetAllSport();

        onClickTitleViewPager = new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorAndUrl(
                        Config.getInstance().getKey_color(),
                        listSportProduct.get(page).getPhoneImage());
            }
        };
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    protected void requestGetAllSport() {
        mDelegate.showLoading();
        mModel = new SpotProductModel();
        mModel.setDelegate(new ModelDelegate() {
                               @Override
                               public void onFail(SimiError error) {
                                   mDelegate.dismissLoading();
                                   if (error != null) {
                                       SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                                   }
                               }

                               @Override
                               public void onSuccess(SimiCollection collection) {
                                   mDelegate.dismissLoading();
                                   mDelegate.updateView(collection);
                                   ArrayList<SimiEntity> entity = collection.getCollection();
                                   if (null != entity && entity.size() > 0) {
                                       listSportProduct = new ArrayList<SpotProductEntity>();
                                       for (SimiEntity simiEntity : entity) {
                                           SpotProductEntity spotProductEntity = (SpotProductEntity) simiEntity;
                                           listSportProduct.add(spotProductEntity);
                                       }
                                   }
                               }
                           }
        );
        mModel.addDataParameter("order", "position");
        mModel.request();
    }
}
