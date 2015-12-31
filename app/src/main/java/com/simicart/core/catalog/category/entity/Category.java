package com.simicart.core.catalog.category.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category extends SimiEntity {
    /* ID of this object, It is not the ID of Category home */
    protected String mID;
    protected String mName;
    protected String mImage;
    protected ArrayList<String> mImages;
    protected boolean hasChild;
    protected String mCategoryID;
    protected int mPosition;
    protected boolean mStatus;

    private String _id = "_id";
    private String name = "name";
    private String image = "image";
    private String images = "images";
    private String url = "url";
    private String category_id = "category_id";
    private String has_children = "has_children";
    private String position = "position";
    private String status = "status";

    @Override
    public void parse() {
        if (null != mJSON) {
            // parse id of category widgets
            if (mJSON.has(_id)) {
                mID = getData(_id);
            }

            // parse name of category
            if (mJSON.has(name)) {
                mName = getData(name);
            }

            // parse image for category
            if (mJSON.has(image)) {
                try {
                    JSONObject js_image = mJSON.getJSONObject(image);
                    if (js_image.has(url)) {
                        mImage = js_image.getString(url);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(images)) {
                mImages = new ArrayList<>();
                try {
                    JSONArray arr_images = mJSON.getJSONArray(images);
                    for (int i=0;i<arr_images.length();i++) {
                        JSONObject obj = arr_images.getJSONObject(i);
                        if (obj.has(url)) {
                             mImages.add(obj.getString(url));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // parse id of category
            if (mJSON.has(category_id)) {
                mCategoryID = getData(category_id);
            }

            // parse to know whether this category has some children or not
            if (mJSON.has(has_children)) {
                String s_child = getData(has_children);
                if (Utils.validateString(s_child) && s_child.equals("true")) {
                    hasChild = true;
                }
            }

            // parse status of category
            if (mJSON.has(status)) {
                String s_status = getData(status);
                if (Utils.validateString(s_status) && s_status.equals("1")) {
                    mStatus = true;
                }
            }


        }
    }

    public String getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(String mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public boolean isEnable() {
        return mStatus;
    }

    public void setEnable(boolean status) {
        this.mStatus = status;
    }

    public boolean hasChild() {
        return hasChild;
    }

    public void setChild(boolean has_Child) {
        hasChild = has_Child;
    }

    public String getCategoryImage() {
        return this.mImage;
    }

    public void setCategoryImage(String category_image) {
        this.mImage = category_image;
    }

    public String getCategoryName() {
        return this.mName;
    }

    public void setCategoryName(String category_name) {
        this.mName = category_name;
    }

    public String getID() {
        return mID;
    }

    public void setID(String category_id) {
        this.mID = category_id;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void setmImages(ArrayList<String> mImages) {
        this.mImages = mImages;
    }
}
