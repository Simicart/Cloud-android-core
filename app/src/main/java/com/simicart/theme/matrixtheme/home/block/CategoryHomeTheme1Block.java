package com.simicart.theme.matrixtheme.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;
import com.simicart.theme.matrixtheme.home.common.BannerUpDown;
import com.simicart.theme.matrixtheme.home.delegate.CategoryHomeTheme1Delegate;

public class CategoryHomeTheme1Block extends SimiBlock implements
		CategoryHomeTheme1Delegate {

	private TextView tv_Category1;
	private TextView tv_Category2;
	private TextView tv_Category3;
	private TextView tv_Category4;
	private Button bt_Category1;
	private Button bt_Category2;
	private Button bt_Category3;
	private Button bt_Category4;
	private TextView tv_viewmore1;
	private TextView tv_viewmore2;
	private TextView tv_viewmore3;
	private TextView tv_viewmore4;

	public CategoryHomeTheme1Block(View view, Context context) {
		super(view, context);
	}

	@Override
	public void initView() {
		tv_Category1 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_category1"));
		tv_Category2 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_category2"));
		tv_Category3 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_category3"));
		bt_Category1 = (Button) mView.findViewById(Rconfig.getInstance().id(
				"bt_category1"));
		bt_Category2 = (Button) mView.findViewById(Rconfig.getInstance().id(
				"bt_category2"));
		bt_Category3 = (Button) mView.findViewById(Rconfig.getInstance().id(
				"bt_category3"));
		bt_Category4 = (Button) mView.findViewById(Rconfig.getInstance().id(
				"bt_category4"));
		tv_Category4 = (TextView) mView.findViewById(Rconfig
				.getInstance().id("tv_category4"));
		tv_viewmore4 = (TextView) mView.findViewById(Rconfig
				.getInstance().id("tv_viewmore4"));
		tv_viewmore4.setText(Config.getInstance().getText("View now"));
		tv_viewmore1 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_viewmore1"));
		tv_viewmore2 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_viewmore2"));
		tv_viewmore3 = (TextView) mView.findViewById(Rconfig.getInstance().id(
				"tv_viewmore3"));
		if (!DataLocal.isTablet) {
			tv_viewmore4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv_viewmore1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv_viewmore2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv_viewmore3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		}
		tv_viewmore1.setText(Config.getInstance().getText("View more") + ">>");
		tv_viewmore2.setText(Config.getInstance().getText("View more") + ">>");
		tv_viewmore3.setText(Config.getInstance().getText("View more") + ">>");
		tv_viewmore1.setTextColor(Color.parseColor("#4E4E4E"));
		tv_viewmore2.setTextColor(Color.parseColor("#4E4E4E"));
		tv_viewmore3.setTextColor(Color.parseColor("#4E4E4E"));
		tv_viewmore1.setVisibility(View.GONE);
		tv_viewmore2.setVisibility(View.GONE);
		tv_viewmore3.setVisibility(View.GONE);
		tv_viewmore4.setVisibility(View.GONE);
		tv_Category1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		tv_Category2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		tv_Category3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		tv_Category4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		tv_Category1.setText(Config.getInstance().getText("No category")
				.toUpperCase());
		tv_Category2.setText(Config.getInstance().getText("No category")
				.toUpperCase());
		tv_Category3.setText(Config.getInstance().getText("No category")
				.toUpperCase());
		tv_Category4.setText(Config.getInstance().getText("No category")
				.toUpperCase());
	}

	private void setCategory1View(final Category category) {
		if (category == null) {
			tv_Category1.setText("Category 1");
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category1"));
			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setImageResource(Rconfig.getInstance().drawable(
					"theme1_fake_category1"));
			imageView.setScaleType(ScaleType.FIT_XY);
			viewFlipper.addView(imageView);
			return;
		}

		tv_viewmore1.setVisibility(View.VISIBLE);

		String name = category.getCategoryName();
		if (null != name) {
			name = name.toUpperCase();
			tv_Category1.setText(name);
			if (DataLocal.isTablet) {
				tv_Category1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			} else {
				tv_Category1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			tv_Category1.setTextColor(Color.parseColor("#000000"));
		}

		ArrayList<String> urlImages = category.getmImages();
		if (null == urlImages || urlImages.size() == 0
				|| (urlImages.size() == 1)) {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category1"));
			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			if (urlImages == null) {
				imageView.setImageResource(Rconfig.getInstance().drawable(
						"default_logo"));
			} else {
				DrawableManager.fetchDrawableOnThread(urlImages.get(0),
						imageView);
			}
			viewFlipper.addView(imageView);
		} else {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category1"));

			BannerUpDown bannerUpDown = new BannerUpDown(mContext, viewFlipper,
					4500);

			for (int i = 0; i < urlImages.size(); i++) {
				String url = urlImages.get(i);
				LayoutInflater inflater = (LayoutInflater) mView.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout bannerView = (LinearLayout) inflater.inflate(
						Rconfig.getInstance().layout("theme1_banner_category"),
						null, false);
				ImageView imageView = (ImageView) bannerView
						.findViewById(Rconfig.getInstance().id(
								"image_banner_category"));

				DrawableManager.fetchDrawableOnThread(url, imageView);
				viewFlipper.addView(bannerView);
				bannerUpDown.onTouchEvent(url, bannerView);
			}
		}

		bt_Category1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewCategory(category);

			}
		});
	}

	private void setCategory2View(final Category category) {
		if (category == null) {
			tv_Category2.setText("Category 2");
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category2"));
			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setImageResource(Rconfig.getInstance().drawable(
					"theme1_fake_category2"));
			imageView.setScaleType(ScaleType.FIT_XY);
			viewFlipper.addView(imageView);
			return;
		}

		tv_viewmore2.setVisibility(View.VISIBLE);

		String name = category.getCategoryName();
		if (null != name) {
			name = name.toUpperCase();
			tv_Category2.setText(name);
			if (DataLocal.isTablet) {
				tv_Category2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			} else {
				tv_Category2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			tv_Category2.setTextColor(Color.parseColor("#000000"));
		}

		ArrayList<String> urlImages = category.getmImages();
		if (null == urlImages || urlImages.size() == 0
				|| (urlImages.size() == 1)) {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category2"));

			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			if (urlImages == null) {
				imageView.setImageResource(Rconfig.getInstance().drawable(
						"default_logo"));
			} else {
				DrawableManager.fetchDrawableOnThread(urlImages.get(0),
						imageView);
			}
			viewFlipper.addView(imageView);
		} else {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category2"));

			BannerUpDown bannerUpDown = new BannerUpDown(mView.getContext(),
					viewFlipper, 4700);

			for (int i = 0; i < urlImages.size(); i++) {
				String url = urlImages.get(i);
				LayoutInflater inflater = (LayoutInflater) mView.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout bannerView = (LinearLayout) inflater.inflate(
						Rconfig.getInstance().layout("theme1_banner_category"),
						null, false);
				ImageView imageView = (ImageView) bannerView
						.findViewById(Rconfig.getInstance().id(
								"image_banner_category"));

				DrawableManager.fetchDrawableOnThread(url, imageView);
				viewFlipper.addView(bannerView);
				bannerUpDown.onTouchEvent(url, bannerView);
			}
		}

		bt_Category2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewCategory(category);
			}
		});
	}

	private void setCategory3View(final Category category) {
		if (category == null) {
			tv_Category3.setText("Category 3");
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category3"));
			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setImageResource(Rconfig.getInstance().drawable(
					"theme1_fake_category2"));
			imageView.setScaleType(ScaleType.FIT_XY);
			viewFlipper.addView(imageView);
			return;
		}

		tv_viewmore3.setVisibility(View.VISIBLE);

		String name = category.getCategoryName();
		if (null != name) {
			name = name.toUpperCase();
			tv_Category3.setText(name);
			if (DataLocal.isTablet) {
				tv_Category3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			} else {
				tv_Category3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			tv_Category3.setTextColor(Color.parseColor("#000000"));
		}

		ArrayList<String> urlImages = category.getmImages();
		if (null == urlImages || urlImages.size() == 0
				|| (urlImages.size() == 1)) {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category3"));

			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			if (urlImages == null) {
				imageView.setImageResource(Rconfig.getInstance().drawable(
						"default_logo"));
			} else {
				DrawableManager.fetchDrawableOnThread(urlImages.get(0),
						imageView);
			}
			viewFlipper.addView(imageView);
		} else {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category3"));

			BannerUpDown bannerUpDown = new BannerUpDown(mView.getContext(),
					viewFlipper, 4900);

			for (int i = 0; i < urlImages.size(); i++) {
				String url = urlImages.get(i);
				LayoutInflater inflater = (LayoutInflater) mView.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout bannerView = (LinearLayout) inflater.inflate(
						Rconfig.getInstance().layout("theme1_banner_category"),
						null, false);
				ImageView imageView = (ImageView) bannerView
						.findViewById(Rconfig.getInstance().id(
								"image_banner_category"));

				DrawableManager.fetchDrawableOnThread(url, imageView);
				viewFlipper.addView(bannerView);
				bannerUpDown.onTouchEvent(url, bannerView);
			}
		}

		bt_Category3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewCategory(category);
			}
		});
	}

	private void setCategory4View(final Category category) {
		if (category == null) {
			tv_Category4.setText("Category 4");
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category4"));
			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			imageView.setImageResource(Rconfig.getInstance().drawable(
					"theme1_fake_category2"));
			imageView.setScaleType(ScaleType.FIT_XY);
			viewFlipper.addView(imageView);
			return;
		}

		tv_viewmore4.setVisibility(View.VISIBLE);

		String name = category.getCategoryName();
		if (null != name) {
			name = name.toUpperCase();
			tv_Category4.setText(name);
			if (DataLocal.isTablet) {
				tv_Category4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			} else {
				tv_Category4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			}
			tv_Category4.setTextColor(Color.parseColor("#000000"));
		}

		ArrayList<String> urlImages = category.getmImages();
		if (null == urlImages || urlImages.size() == 0
				|| (urlImages.size() == 1)) {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category4"));

			ImageView imageView = new ImageView(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			imageView.setLayoutParams(lp);
			if (urlImages == null) {
				imageView.setImageResource(Rconfig.getInstance().drawable(
						"default_logo"));
			} else {
				DrawableManager.fetchDrawableOnThread(urlImages.get(0),
						imageView);
			}
			viewFlipper.addView(imageView);
		} else {
			ViewFlipper viewFlipper = (ViewFlipper) mView.findViewById(Rconfig
					.getInstance().id("vfp_category4"));

			BannerUpDown bannerUpDown = new BannerUpDown(mView.getContext(),
					viewFlipper, 5000);

			for (int i = 0; i < urlImages.size(); i++) {
				String url = urlImages.get(i);
				LayoutInflater inflater = (LayoutInflater) mView.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout bannerView = (LinearLayout) inflater.inflate(
						Rconfig.getInstance().layout("theme1_banner_category"),
						null, false);
				ImageView imageView = (ImageView) bannerView
						.findViewById(Rconfig.getInstance().id(
								"image_banner_category"));

				DrawableManager.fetchDrawableOnThread(url, imageView);
				viewFlipper.addView(bannerView);
				bannerUpDown.onTouchEvent(url, bannerView);
			}
		}

		bt_Category4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewCategory(category);
			}
		});

	}

	private void viewCategory(final Category category) {
		if (category.hasChild()) {
			CategoryFragment fragment = CategoryFragment.newInstance(category.getCategoryName(),
					category.getCategoryID());
			SimiManager.getIntance().replaceFragment(fragment);
			SimiManager.getIntance().hideKeyboard();
		} else {
			CategoryDetailFragment fragment = CategoryDetailFragment
					.newInstance();
			fragment.setCategoryId(category.getCategoryID());
			fragment.setCategoryName(category.getCategoryName());
			if (category.getCategoryID().equals("-1")) {
				fragment.setUrlSearch("products");
			} else {
				fragment.setUrlSearch("categories");
			}
			SimiManager.getIntance().replaceFragment(fragment);
			SimiManager.getIntance().hideKeyboard();
		}
	}

	private void viewAllCategory() {
		if (DataLocal.isTablet) {
			CategoryFragment fr_Category = CategoryFragment.newInstance(Config
					.getInstance().getText("all categories"), "-1");
			CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(
					fr_Category);
			CateSlideMenuFragment.getIntance().openMenu();
		} else {
			CategoryFragment fr_Category = CategoryFragment.newInstance(Config
					.getInstance().getText("all categories"), "-1");
			SimiManager.getIntance().replacePopupFragment(fr_Category);
		}
	}

	@Override
	public void drawView(SimiCollection collection) {
		ArrayList<SimiEntity> entity = collection.getCollection();
		Category category1 = null;
		Category category2 = null;
		Category category3 = null;
		Category category4 = null;
		ArrayList<Category> categories = new ArrayList<Category>(4);
		if (null != entity && entity.size() > 0) {
			for (SimiEntity simiEntity : entity) {
				Category category = (Category) simiEntity;
				categories.add(category);
			}

			int size = categories.size();
			if (size > 0) {
				category1 = categories.get(0);
				if (size > 1) {
					category2 = categories.get(1);
					if (size > 2) {
						category3 = categories.get(2);
						if (size > 3) {
							category4 = categories.get(3);
						}
					}
				}
			}
		}
		setCategory1View(category1);
		setCategory2View(category2);
		setCategory3View(category3);
		setCategory4View(category4);
	}
}
