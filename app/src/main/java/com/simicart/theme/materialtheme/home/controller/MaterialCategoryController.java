package com.simicart.theme.materialtheme.home.controller;

import android.util.Log;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.model.ProductDetailModel;
import com.simicart.core.common.Utils;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.theme.materialtheme.home.delegate.MaterialCategoryDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryController extends SimiController {
    protected  SpotProductEntity sportProduct;
    protected MaterialCategoryDelegate mDelegate;

    public void setSportProduct(SpotProductEntity sportProduct) {
        this.sportProduct = sportProduct;
    }

    public void setDelegate(MaterialCategoryDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {
        String type = sportProduct.getType();
        String limit = sportProduct.getLimit();
        if (type.equals("1")) {
            requestBestSeller(limit);
        } else if (type.equals("2")) {
            requestNewlyUpdated(limit);
        } else if (type.equals("3")) {
            requestRecentlyAdded(limit);
        } else if (type.equals("4")) {
            String ids = arrayToString(sportProduct.getProducts());
            if (Utils.validateString(ids)) {
                getFeature(ids, limit);
            }
        }
    }

    @Override
    public void onResume() {

    }

    private void requestBestSeller(String limit) {
        mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.updateView(collection);
            }
        });

        mModel.addDataParameter("group-type", "best-sellers");
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else {
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    private void requestNewlyUpdated(String limit) {
        mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.updateView(collection);
            }
        });
        mModel.addOrderDataParameter("updated_at");
        mModel.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else{
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    protected void requestRecentlyAdded(String limit) {
        mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.updateView(collection);
            }
        });
        mModel.addOrderDataParameter("created_at");
        mModel.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else{
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    protected String arrayToString(ArrayList<String> array) {
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        if (null != array && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                String id = array.get(i);
                if (isFirst) {
                    builder.append(id);
                    isFirst = false;
                } else {
                    builder.append("," + id);
                }
            }
        }
        Log.e("SpotProductController", "arrToString " + builder.toString());
        return builder.toString();
    }

    protected void getFeature(String ids, String limit) {
        mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.updateView(collection);
            }
        });
        mModel.addDataParameter("ids", ids);
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else {
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }
}
