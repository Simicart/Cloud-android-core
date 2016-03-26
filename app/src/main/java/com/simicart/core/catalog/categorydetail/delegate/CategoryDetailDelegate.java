package com.simicart.core.catalog.categorydetail.delegate;

import java.util.ArrayList;

import android.content.Context;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.simicart.core.adapter.ProductListAdapter;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.categorydetail.adapter.GridViewCategoryDetailApdapter;

public interface CategoryDetailDelegate extends SimiDelegate {

	public void setQty(String qty);


	public void removeFooterView();

	public void addFooterView();


	public void setVisibilityMenuBotton(boolean temp);

	public String getTagSearch();
	public void setTagSearch(String tag_search);


	public void onChangeNumberColumnGrid( boolean is_zoom_out);


	public ArrayList<ProductEntity> getListProduct();

	public ArrayList<String> getListProductId();
	public void setCurrentPosition(int position);
	public void setCheckFilter(boolean filter);
	public void setIsLoadMore(boolean loadmore);
	public void showSort(boolean isCheck);

	public String onChangeTypeViewShow(boolean isList);


}
