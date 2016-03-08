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
import java.util.HashMap;

public class SpotProductController extends SimiController {
    protected SpotProductDelegate mDelegate;
    protected ArrayList<ProductEntity> mBestSellers;
    protected ArrayList<ProductEntity> mNewlyUpdated;
    protected ArrayList<ProductEntity> mRecentlyAdded;
    protected ArrayList<ProductEntity> mFeature;
    protected ArrayList<SpotProductEntity> listSportProduct;
    protected HashMap<Integer, ArrayList<ProductEntity>> mListHashCustom;

    public SpotProductController() {
        mBestSellers = new ArrayList<ProductEntity>();
        mNewlyUpdated = new ArrayList<ProductEntity>();
        mRecentlyAdded = new ArrayList<ProductEntity>();
        mListHashCustom = new HashMap<Integer, ArrayList<ProductEntity>>();
    }

    public void setDelegate(SpotProductDelegate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public void onStart() {
        mDelegate.showLoadingSpot();
        requestGetAllSport();
    }

    protected void requestGetAllSport() {
        final SpotProductModel spAllModel = new SpotProductModel();
        spAllModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.createViewSport(spAllModel.getTotalSport());
                if (collection != null && collection.getCollection().size() > 0) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    listSportProduct = new ArrayList<SpotProductEntity>();
                    if (null != entity && entity.size() > 0) {
                        int i = -1;
                        for (SimiEntity simiEntity : entity) {
                            i++;
                            SpotProductEntity spotProductEntity = new SpotProductEntity();
                            spotProductEntity = (SpotProductEntity) simiEntity;
                            if (spotProductEntity.getType().equals("1")) {
                                requestBestSeller(spotProductEntity.getName(), i, spotProductEntity.getLimit());
                            } else if (spotProductEntity.getType().equals("2")) {
                                requestNewlyUpdated(spotProductEntity.getName(), i, spotProductEntity.getLimit());
                            } else if (spotProductEntity.getType().equals("3")) {
                                requestRecentlyAdded(spotProductEntity.getName(), i, spotProductEntity.getLimit());
                            } else if (spotProductEntity.getType().equals("4")) {
                                String ids = arrayToString(spotProductEntity.getProducts());
                                if (Utils.validateString(ids)) {
                                    getFeature(ids, spotProductEntity.getName(), i, spotProductEntity.getLimit());
                                }
                            }
                            listSportProduct.add(spotProductEntity);
                        }
                    }
                }
            }
        });
        spAllModel.addDataParameter("order", "position");
        spAllModel.request();
    }

    private void requestBestSeller(final String title, final int postion, String limit) {
        ProductDetailModel bs_model = new ProductDetailModel();
        bs_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoadingSpot();
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
                    mDelegate.onShowBestSeller(postion, title, mBestSellers);
                }

            }
        });

        bs_model.addDataParameter("group-type", "best-sellers");
        if(Utils.validateString(limit) && !limit.equals("0")){
            bs_model.addLimitDataParameter(limit);
        }else {
            bs_model.addLimitDataParameter("15");
        }
        bs_model.request();
    }

    private void requestNewlyUpdated(final String title, final int postion, String limit) {
        ProductDetailModel nl_model = new ProductDetailModel();
        nl_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoadingSpot();
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
                    mDelegate.onShowNewlyUpdated(postion, title, mNewlyUpdated);
                }
            }
        });
        nl_model.addOrderDataParameter("updated_at");
        nl_model.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            nl_model.addLimitDataParameter(limit);
        }else{
            nl_model.addLimitDataParameter("15");
        }
        nl_model.request();
    }

    protected void requestRecentlyAdded(final String title, final int postion, String limit) {
        ProductDetailModel ra_model = new ProductDetailModel();
        ra_model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoadingSpot();
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
                    mDelegate.onShowRecentlyAdded(postion, title, mRecentlyAdded);
                }
            }
        });
        ra_model.addOrderDataParameter("created_at");
        ra_model.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            ra_model.addLimitDataParameter(limit);
        }else{
            ra_model.addLimitDataParameter("15");
        }
        ra_model.request();
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

    protected void getFeature(String ids, final String title, final int postion, String limit) {
        ProductDetailModel fModel = new ProductDetailModel();
        fModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoadingSpot();
                JSONObject jsResult = collection.getJSON();
                mFeature = new ArrayList<ProductEntity>();
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
                    mListHashCustom.put(postion, mFeature);
                    mDelegate.onShowFeature(postion, title, mFeature);
                }
            }
        });
        fModel.addDataParameter("ids", ids);
        if(Utils.validateString(limit) && !limit.equals("0")){
            fModel.addLimitDataParameter(limit);
        }else {
            fModel.addLimitDataParameter("15");
        }
        fModel.request();
    }


    @Override
    public void onResume() {
        if (listSportProduct != null && listSportProduct.size() > 0) {
            mDelegate.dismissLoadingSpot();
            mDelegate.createViewSport(listSportProduct.size());
            for (int i = 0; i < listSportProduct.size(); i++) {
                SpotProductEntity spotProductEntity = listSportProduct.get(i);
                if (spotProductEntity.getType().equals("1")) {
                    mDelegate.onShowBestSeller(i, spotProductEntity.getName(), mBestSellers);
                } else if (spotProductEntity.getType().equals("2")) {
                    mDelegate.onShowNewlyUpdated(i, spotProductEntity.getName(), mNewlyUpdated);
                } else if (spotProductEntity.getType().equals("3")) {
                    mDelegate.onShowRecentlyAdded(i, spotProductEntity.getName(), mRecentlyAdded);
                } else if (spotProductEntity.getType().equals("4")) {
                    mDelegate.onShowFeature(i, spotProductEntity.getName(), mListHashCustom.get(i));
                }
            }
        }
    }
}
