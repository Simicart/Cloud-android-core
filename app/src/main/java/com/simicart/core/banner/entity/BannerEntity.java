package com.simicart.core.banner.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerEntity extends SimiEntity {
    protected String mImagePath;
    protected String mURL;
    protected String mType;
    protected String mCategoryId;
    protected String mCategoryName;
    protected String hasChild;
    protected String mProductId;

    private String type = "type";
    private String product_id = "product_id";
    private String url = "url";
    private String image = "image";
    private String category_id = "category_id";
    private String has_child = "has_child";

    public String getProductId() {
        return this.mProductId;
    }

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getHasChild() {
        return this.hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getUrl() {
        return this.mURL;
    }

    public void setUrl(String url) {
        this.mURL = url;
    }

    public String getImage() {
        return this.mImagePath;
    }

    public void setImage(String image_path) {
        this.mImagePath = image_path;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getCategoryName() {
        return this.mCategoryName;
    }

    public void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getCategoryId() {
        return this.mCategoryId;
    }

    public void setCategoryId(String mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    @Override
    public void parse() {
        if (null != mJSON) {
            // parse type of banner
            if (mJSON.has(type)) {
                type = getData(type);
            }

            // parse product_id of banner
            if (mJSON.has(product_id)) {
                product_id = getData(product_id);
            }

            // parse url of banner
            if (mJSON.has(url)) {
                mURL = getData(url);
            }

            // parse image of banner
            if (mJSON.has(image)) {
                Log.e("BannerEntity","parse image 001");
                try {
                    JSONObject js_image = mJSON.getJSONObject(image);
                    Log.e("BannerEntity","parse image 002 "+ js_image.toString());
                    if (js_image.has(url)) {
                        Log.e("BannerEntity","parse image 003");
                        mImagePath = js_image.getString(url);
                        Log.e("BannerEntity","parse image 004" + mImagePath);
                    }
                } catch (JSONException e) {
                    Log.e("BannerEntity", e.getMessage());
                }
            }

            // parse category_id of banner
            if (mJSON.has(category_id)) {
                category_id = getData(category_id);
            }

            // parse has_child of banner
            if (mJSON.has(has_child)) {
                has_child = getData(has_child);
            }
        }
    }
}
