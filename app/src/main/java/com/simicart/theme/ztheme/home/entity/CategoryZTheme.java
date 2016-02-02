package com.simicart.theme.ztheme.home.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.catalog.category.entity.Category;
import com.simicart.theme.ztheme.home.common.ConstantsZTheme;

public class CategoryZTheme extends Category {

	public static final int TYPE_CAT = 0;
	public static final int TYPE_SPOT = 1;

	int type = -1;
	protected String mTitle = "";
	protected String mPhoneImage;
	protected String mTabImage;
	ArrayList<CategoryZTheme> mCategories = new ArrayList<>();
	Category category = new Category();

	private String phone_image = "phone_image";
	private String tablet_image = "tablet_image";

	SpotProductZTheme spotProductZTheme;

	public ArrayList<CategoryZTheme> getmCategories() {
		Log.e("CategoryZTheme", "__" + mCategories.size());
//		try {
//			if (mCategories.size() == 0 && hasChild()) {
//				JSONArray array = getJSONObject().getJSONArray(
//						ConstantsZTheme.CHILD_CAT);
//				if (null != array && array.length() > 0) {
//					for (int i = 0; i < array.length(); i++) {
//						JSONObject object = array.getJSONObject(i);
//						CategoryZTheme category = new CategoryZTheme();
//						category.setJSONObject(object);
//						mCategories.add(category);
//					}
//				}
//			}
//		} catch (Exception e) {
//			Log.e(getClass().getName(), "");
//		}
		return mCategories;
	}

	public void setmCategories(ArrayList<CategoryZTheme> mCategories) {
		this.mCategories = mCategories;
	}

	public void setSpotProductZTheme(SpotProductZTheme spotProductZTheme) {
		this.spotProductZTheme = spotProductZTheme;
	}

	public SpotProductZTheme getSpotProductZTheme() {
		if (spotProductZTheme == null) {
			spotProductZTheme = new SpotProductZTheme();
			spotProductZTheme.setJSONObject(getJSONObject());
		}
		return spotProductZTheme;
	}

	public int getType() {
		if (type == -1) {
			if(spotProductZTheme != null)
				type = TYPE_SPOT;
			else
				type = TYPE_CAT;
		}
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		if (null == mTitle) {
			mTitle = getData(ConstantsZTheme.TITLE);
		}
//		if (mTitle.equals("null")) {
//			mTitle = "";
//		}
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getPhoneImage() {
		if(mPhoneImage == null){
			if(mJSON.has(phone_image)){
				try {
					JSONObject mTabObj = mJSON.getJSONObject(phone_image);
					if(mTabObj.has("url")){
						mPhoneImage = mTabObj.getString("url");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return mPhoneImage;
	}

	public void setPhoneImage(String mPhoneImage) {
		this.mPhoneImage = mPhoneImage;
	}

	public String getTabImage() {
		if(mTabImage == null){
			if(mJSON.has(tablet_image)){
				try {
					JSONObject mTabObj = mJSON.getJSONObject(tablet_image);
					if(mTabObj.has("url")){
						mTabImage = mTabObj.getString("url");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
		return mTabImage;
	}

	public void setTabImage(String mTabImage) {
		this.mTabImage = mTabImage;
	}
}
