package com.simicart.theme.matrixtheme.home.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.theme.ztheme.home.common.ConstantsZTheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by James Crabby on 12/14/2015.
 */
public class Theme1SpotProduct extends SimiEntity {

    protected String mID;
    protected String mName;
    protected ArrayList<String> mImages;
    protected String mKey;
    protected ArrayList<String> mProducts;
    protected int mType;

    protected String id = "_id";
    protected String name = "name";
    protected String imagesPhone = "phone_images";
    protected String imagesTablet = "tablet_images";
    protected String products = "products";
    protected String type = "type";

    @Override
    public void parse() {
        if(mJSON.has("_id")) {
            try {
                mID = mJSON.getString("_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("name")) {
            try {
                mName = mJSON.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(!DataLocal.isTablet) {
            if(mJSON.has("phone_images")) {
                mImages = new ArrayList<>();
                try {
                    JSONArray arr = mJSON.getJSONArray(imagesPhone);
                    if(arr != null && arr.length() > 0) {
                        for(int i=0;i<arr.length();i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            if(obj.has("url")) {
                                mImages.add(obj.getString("url"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(mJSON.has("tablet_image")) {
                mImages = new ArrayList<>();
                try {
                    JSONArray arr = mJSON.getJSONArray(imagesPhone);
                    if(arr != null && arr.length() > 0) {
                        for(int i=0;i<arr.length();i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            if(obj.has("url")) {
                                mImages.add(obj.getString("url"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(mJSON.has("products")) {
            mProducts = new ArrayList<String>();
            try {
                JSONArray array = mJSON.getJSONArray("products");
                if (null != array && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        String js = array.getString(i);
                        mProducts.add(js.toString());
                    }
                }
            } catch (JSONException e) {
                Log.e("Theme1SpotProduct", "Parse products Exception " + e.getMessage());
            }
        }

        if(mJSON.has(type)){
            String typeValue = getData(type);
            if(Utils.validateString(typeValue)){
                try {
                    mType = Integer.parseInt(typeValue);
                }catch (Exception e){

                }
            }
        }
    }

    public String getID() {
        if (null == mID) {
            //mID = getData(ConstantsZTheme.SPOT_ID);
            if(mJSON.has("_id")) {
                try {
                    mID = mJSON.getString("_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        if (null == mName) {
            //mName = getData(ConstantsZTheme.SPOT_NAME);
            if(mJSON.has("name")) {
                try {
                    mName = mJSON.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void setmImages(ArrayList<String> mImages) {
        this.mImages = mImages;
    }

    public String getKey() {
        if (null == mKey) {
            mKey = getData(ConstantsZTheme.SPOT_KEY);
        }
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public ArrayList<String> getProducts() {
        return mProducts;
    }

    public void setProducts(ArrayList<String> mProducts) {
        this.mProducts = mProducts;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }
}
