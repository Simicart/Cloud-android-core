package com.simicart.core.home.controller;

import android.util.Log;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.model.ProductDetailModel;
import com.simicart.core.common.Utils;
import com.simicart.core.home.delegate.SpotProductDelegate;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.core.home.model.SpotProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpotProductController extends SimiController {
    protected SpotProductDelegate mDelegate;
    protected ArrayList<ProductEntity> mBestSellers;
    protected ArrayList<ProductEntity> mNewlyUpdated;
    protected ArrayList<ProductEntity> mRecentlyAdded;
    protected ArrayList<ProductEntity> mFeature;
    protected int spotLoadedCount;

    public SpotProductController() {
        mBestSellers = new ArrayList<ProductEntity>();
        mNewlyUpdated = new ArrayList<ProductEntity>();
        mRecentlyAdded = new ArrayList<ProductEntity>();
        mFeature = new ArrayList<ProductEntity>();
        spotLoadedCount = 0;
    }

    public void setDelegate(SpotProductDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onStart() {
        mDelegate.showLoading();
        requestBestSeller();
        requestNewlyUpdated();
        requestRecentlyAdded();
        requestFeature();
    }

    private void requestBestSeller() {
        ProductDetailModel bs_model = new ProductDetailModel();
        bs_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                spotLoadedCount++;
                Log.e("SpotLoadedCount", "++" + spotLoadedCount);
                if(spotLoadedCount == 4) {
                    mDelegate.dismissLoading();
                }
                JSONObject jsResult = collection.getJSON();
                if (jsResult.has("products")) {
                    try {
                        JSONArray array = jsResult.getJSONArray("products");
                        if (null != array && array.length() > 0) {
                            int length = array.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject js = array.getJSONObject(i);
                                ProductEntity product = new ProductEntity();
                                product.setJSONObject(js);
                                product.parse();
                                mBestSellers.add(product);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDelegate.onShowBestSeller(mBestSellers);
                }

            }
        });

        bs_model.addDataParameter("group-type", "best-sellers");
        bs_model.addLimitDataParameter("15");
        bs_model.request();
    }

    private void requestNewlyUpdated() {
        ProductDetailModel nl_model = new ProductDetailModel();
        nl_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                spotLoadedCount++;
                Log.e("SpotLoadedCount", "++" + spotLoadedCount);
                if(spotLoadedCount == 4) {
                    mDelegate.dismissLoading();
                }
                JSONObject jsResult = collection.getJSON();
                if (jsResult.has("products")) {
                    try {
                        JSONArray array = jsResult.getJSONArray("products");
                        if (null != array && array.length() > 0) {
                            int length = array.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject js = array.getJSONObject(i);
                                ProductEntity product = new ProductEntity();
                                product.setJSONObject(js);
                                product.parse();
                                mNewlyUpdated.add(product);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDelegate.onShowNewlyUpdated(mNewlyUpdated);
                }
            }
        });
        nl_model.addOrderDataParameter("updated_at");
        nl_model.sortDirDESC();
        nl_model.addOffsetDataParameter("0");
        nl_model.addLimitDataParameter("15");
        nl_model.request();
    }

    protected void requestRecentlyAdded() {
        ProductDetailModel ra_model = new ProductDetailModel();
        ra_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                spotLoadedCount++;
                Log.e("SpotLoadedCount", "++" + spotLoadedCount);
                if(spotLoadedCount == 4) {
                    mDelegate.dismissLoading();
                }
                JSONObject jsResult = collection.getJSON();
                if (jsResult.has("products")) {
                    try {
                        JSONArray array = jsResult.getJSONArray("products");
                        if (null != array && array.length() > 0) {
                            int length = array.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject js = array.getJSONObject(i);
                                ProductEntity product = new ProductEntity();
                                product.setJSONObject(js);
                                product.parse();
                                mRecentlyAdded.add(product);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDelegate.onShowRecentlyAdded(mRecentlyAdded);
                }
            }
        });
        ra_model.addOrderDataParameter("created_at");
        ra_model.sortDirDESC();
        ra_model.addOffsetDataParameter("0");
        ra_model.addLimitDataParameter("15");
        ra_model.request();
    }


    protected void requestFeature() {
        requestSpotProduct();
    }

    protected void requestSpotProduct() {
        SpotProductModel spModel = new SpotProductModel();
        spModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                spotLoadedCount++;
                Log.e("SpotLoadedCount", "++" + spotLoadedCount);
                if(spotLoadedCount == 4) {
                    mDelegate.dismissLoading();
                }
                ArrayList<SimiEntity> entities = collection.getCollection();
                String ids = getIDS(entities);
                if (Utils.validateString(ids)) {
                    Log.e("SpotProductController", "IDS " + ids);
                    getFeature(ids);
                }
            }
        });

        spModel.request();
    }

    protected String getIDS(ArrayList<SimiEntity> entities) {
        if (null != entities && entities.size() > 0) {
            for (int i = 0; i < entities.size(); i++) {
                SpotProductEntity spotProductEntity = (SpotProductEntity) entities.get(i);
                if (spotProductEntity.getType().equals("4")) {
                    Log.e("SpotproductController", "getIDs 001");
                    ArrayList<String> products = spotProductEntity.getProducts();
                    return arrayToString(products);
                }
            }
        }

        return null;
    }

    protected String arrayToString(ArrayList<String> array) {
        Log.e("SpotProductController", "arrToString");
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        if (null != array && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                String id = array.get(i);
                Log.e("SpotProductController", "arrToString " + id);
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

    protected void getFeature(String ids) {
        ProductDetailModel fModel = new ProductDetailModel();
        fModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                JSONObject jsResult = collection.getJSON();
                if (jsResult.has("products")) {
                    try {
                        JSONArray array = jsResult.getJSONArray("products");
                        if (null != array && array.length() > 0) {
                            int length = array.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject js = array.getJSONObject(i);
                                ProductEntity product = new ProductEntity();
                                product.setJSONObject(js);
                                product.parse();
                                mFeature.add(product);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDelegate.onShowFeature(mFeature);
                }
            }
        });
        fModel.addDataParameter("ids", ids);
        fModel.addLimitDataParameter("15");
        fModel.request();
    }


    @Override
    public void onResume() {
        mDelegate.onShowBestSeller(mBestSellers);
        mDelegate.onShowNewlyUpdated(mNewlyUpdated);
        mDelegate.onShowRecentlyAdded(mRecentlyAdded);
        mDelegate.onShowFeature(mFeature);
    }
}
