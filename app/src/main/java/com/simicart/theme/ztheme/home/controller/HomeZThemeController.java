package com.simicart.theme.ztheme.home.controller;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.home.model.CategoryWidgetModel;
import com.simicart.core.home.model.SpotProductModel;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;
import com.simicart.theme.ztheme.home.delegate.HomeZThemeDelegate;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;
import com.simicart.theme.ztheme.home.entity.SpotProductZTheme;
import com.simicart.theme.ztheme.home.model.ChildCategoryZThemeModel;

public class HomeZThemeController extends SimiController {
	protected HomeZThemeDelegate mDelegate;
	protected OnGroupClickListener mGroupExpand;
	protected OnChildClickListener mChildClick;
	ArrayList<Category> mCategories;
	ArrayList<CategoryZTheme> mListChild;

	@Override
	public void onStart() {
		onAction();
		mCategories = new ArrayList<>();
		getCategoryWidget();
	}

	protected void getCategoryWidget() {
		mModel = new CategoryWidgetModel();
		mDelegate.showLoading();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {

			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				ArrayList<SimiEntity> entity = mModel.getCollection()
						.getCollection();
				if (null != entity && entity.size() > 0) {
					for (SimiEntity simiEntity : entity) {
						//Category category = (Category) simiEntity;
						CategoryZTheme cat = new CategoryZTheme();
						cat.setJSONObject(simiEntity.getJSONObject());
						cat.parse();
						if (cat == null)
							Log.e("abc", "null");
						if (cat.hasChild()) {
							mListChild = new ArrayList<CategoryZTheme>();
							getGroupChild(cat);
							cat.setmCategories(mListChild);
						}
						mDelegate.updateData(cat);
						mCategories.add(cat);
					}
				}
				//mDelegate.updateView(model.getCollection());
				getSpotProduct();
			}
		});
		mModel.addDataParameter("order", "position");
		mModel.request();
	}

	protected void getSpotProduct() {
		final SpotProductModel model = new SpotProductModel();
//		mDelegate.showLoading();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {

			}

			@Override
			public void onSuccess(SimiCollection collection) {
//				mDelegate.dismissLoading();
				ArrayList<SimiEntity> entity = model.getCollection()
						.getCollection();
				if (null != entity && entity.size() > 0) {
					for (SimiEntity simiEntity : entity) {
						SpotProductZTheme spot = new SpotProductZTheme();
						spot.setJSONObject(simiEntity.getJSONObject());
						spot.parse();
						CategoryZTheme cat = new CategoryZTheme();
						cat.setSpotProductZTheme(spot);
						mDelegate.updateData(cat);
						mCategories.add(cat);
					}
				}
				//mDelegate.updateView(model.getCollection());

			}
		});
		model.addDataParameter("order","position");
		model.request();
	}

	protected void getGroupChild(CategoryZTheme categoryZTheme) {
		final ChildCategoryZThemeModel model = new ChildCategoryZThemeModel();
//		mDelegate.showLoading();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {

			}

			@Override
			public void onSuccess(SimiCollection collection) {
				ArrayList<SimiEntity> entity = model.getCollection()
						.getCollection();
				if (null != entity && entity.size() > 0) {
					for (SimiEntity simiEntity : entity) {
						CategoryZTheme cat = new CategoryZTheme();
						cat.setJSONObject(simiEntity.getJSONObject());
						cat.parse();
						if (cat == null)
							Log.e("abc", "null");
						mListChild.add(cat);
					}
				}
				//mDelegate.updateView(model.getCollection());

			}
		});
		model.addFilterDataParameter("parent", categoryZTheme.getCategoryID());
		model.request();
	}

	protected void onAction() {
		mGroupExpand = new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				switch (((CategoryZTheme)mCategories.get(groupPosition)).getType()) {
				case CategoryZTheme.TYPE_CAT:
					if (!mCategories.get(groupPosition).hasChild()) {
						selectCat(mCategories.get(groupPosition), false);
					}
					break;
				case CategoryZTheme.TYPE_SPOT:
					selectSpot(((CategoryZTheme) mCategories.get(groupPosition))
							.getSpotProductZTheme());
					break;
				default:
					break;
				}
				return false;
			}
		};

		mChildClick = new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				CategoryZTheme categoryZTheme = new CategoryZTheme();
				categoryZTheme = ((CategoryZTheme) mCategories.get(groupPosition)).getmCategories()
						.get(childPosition);
				selectCat(categoryZTheme, true);
				return true;
			}
		};

	}

	protected void selectSpot(SpotProductZTheme spotProductZTheme) {
		CategoryDetailFragment fragment = CategoryDetailFragment
				.newInstance();
		fragment.setCategoryId(spotProductZTheme.getID());
		fragment.setCategoryName(spotProductZTheme.getName());
		fragment.setUrlSearch("products");
		fragment.setTypeSport(spotProductZTheme.getType());
		if(spotProductZTheme.getProducts() != null && spotProductZTheme.getProducts().size() > 0){
			String ids = arrayToString(spotProductZTheme.getProducts());
			if(Utils.validateString(ids)){
				fragment.setListIdsFeature(ids);
			}
		}
		SimiManager.getIntance().replaceFragment(fragment);
		SimiManager.getIntance().hideKeyboard();
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
		Log.e("CateogryCustomAdapter", "arrToString " + builder.toString());
		return builder.toString();
	}

	protected void selectCat(Category category, boolean isChild) {
		if (category.hasChild()) {
			CategoryFragment fragment = CategoryFragment.newInstance(category.getCategoryName(),
					category.getCategoryID());
			if (DataLocal.isTablet) {
				CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(
						fragment);
				CateSlideMenuFragment.getIntance().openMenu();
			} else {
				SimiManager.getIntance().replaceFragment(fragment);
			}
		} else {
			String catID = "";
			if(isChild == false)
				catID = category.getCategoryID();
			else
				catID = category.getID();
			CategoryDetailFragment fragment = CategoryDetailFragment
					.newInstance();
			fragment.setCategoryId(catID);
			fragment.setCategoryName(category.getCategoryName());
			Log.e("CategoryID", "++" + catID);
			if (catID.equals("-1")) {
				fragment.setUrlSearch("products");
			} else {
				fragment.setUrlSearch("categories");
			}
			SimiManager.getIntance().replaceFragment(fragment);
			SimiManager.getIntance().hideKeyboard();
		}

	}

	public OnGroupClickListener getmGroupExpand() {
		return mGroupExpand;
	}

	public OnChildClickListener getmChildClick() {
		return mChildClick;
	}

	@Override
	public void onResume() {
		mDelegate.updateView(mModel.getCollection());
	}

	public void setDelegate(HomeZThemeDelegate mDelegate) {
		this.mDelegate = mDelegate;
	}

}
