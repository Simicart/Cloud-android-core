package com.simicart.core.home.controller;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.DataLocal;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;

public class CategoryHomeListener {

	protected ArrayList<Category> listCategory;

	public void setListCategory(ArrayList<Category> listCategory) {
		this.listCategory = listCategory;
	}

	public OnItemClickListener createOnTouchCategory() {
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Category cate = listCategory.get(position);
				String idCat = cate.getCategoryID();
				String name = cate.getCategoryName();
				boolean hasChild = cate.hasChild();

				if (hasChild) {
					CategoryFragment fragment = CategoryFragment.newInstance(
							name, idCat);
					if(DataLocal.isTablet){
						CateSlideMenuFragment.getIntance().openMenu();
						CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(
								fragment);
					}else {
						SimiManager.getIntance().replaceFragment(fragment);
					}
					SimiManager.getIntance().hideKeyboard();
				} else {
					CategoryDetailFragment fragment = CategoryDetailFragment
							.newInstance();
					fragment.setCategoryId(idCat);
					fragment.setCategoryName(name);
					if (idCat.equals("-1")) {
						fragment.setUrlSearch("products");
					} else {
						fragment.setUrlSearch("categories");
					}
					SimiManager.getIntance().replaceFragment(fragment);
					SimiManager.getIntance().hideKeyboard();
				}
			}
		};
	}

}
