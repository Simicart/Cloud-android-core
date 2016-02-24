package com.simicart.core.banner.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.URLUtil;
import android.widget.ViewFlipper;

import com.simicart.core.banner.entity.BannerEntity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.entity.CategoryEntity;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.category.model.CategoryModel;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;

@SuppressLint("ClickableViewAccessibility")
public class BannerAnimation {
	View parent_view;
	float lastX;
	ViewFlipper mBannerFlipper;
	Context context;
	protected boolean isAnimation = true;
	private String TYPE_PRODUCT = "1";
	private String TYPE_CATEGORY = "2";
	private String TYPE_WEB = "3";

	public void isAnimation(boolean isAni) {
		isAnimation = isAni;
	}

	public BannerAnimation(View view, ViewFlipper mBannerFlipper) {
		this.parent_view = view;
		this.mBannerFlipper = mBannerFlipper;
		this.mBannerFlipper.setFlipInterval(4500);
		this.context = view.getContext();
		if (isAnimation) {
			this.mBannerFlipper.setInAnimation(view.getContext(), Rconfig
					.getInstance().getId("in_from_right", "anim"));
			this.mBannerFlipper.setOutAnimation(view.getContext(), Rconfig
					.getInstance().getId("out_to_left", "anim"));
			this.mBannerFlipper.startFlipping();
		}
	}

	public BannerAnimation(View view, ViewFlipper mBannerFlipper, boolean isAn) {
		this.parent_view = view;
		this.mBannerFlipper = mBannerFlipper;
		this.mBannerFlipper.setFlipInterval(10500);
		this.context = view.getContext();
		isAnimation = isAn;
		if (isAnimation) {
			this.mBannerFlipper.setInAnimation(view.getContext(), Rconfig
					.getInstance().getId("in_from_right", "anim"));
			this.mBannerFlipper.setOutAnimation(view.getContext(), Rconfig
					.getInstance().getId("out_to_left", "anim"));
			this.mBannerFlipper.startFlipping();
		}
	}

	public void onTouchEvent(BannerEntity banner, View bannerView) {
		final BannerEntity banner_ad = banner;
//		bannerView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent touchevent) {
//				switch (touchevent.getAction()) {
//					case MotionEvent.ACTION_DOWN: {
//						lastX = touchevent.getX();
//						break;
//					}
//
//					case MotionEvent.ACTION_UP: {
//						SimiManager.getIntance().hideKeyboard();
//						float currentX = touchevent.getX();
//						if (lastX == currentX) {
//							// dispatch event for sent google analytic
//	//						EventController dispacth = new EventController();
////						dispacth.dispatchEvent(
////								"com.simicart.banner.touchEvent",
////								banner_ad.getUrl());
//							// end dispatch
//							SimiFragment fragment = null;
//							if (banner_ad.getType() != null) {
//								if (banner_ad.getType().equals(TYPE_PRODUCT)) {
//									if (banner_ad.getProductId() != null
//											&& !banner_ad.getProductId().equals("")
//											&& !banner_ad.getProductId()
//											.toLowerCase().equals("null")) {
//										fragment = ProductDetailParentFragment
//												.newInstance();
//										((ProductDetailParentFragment) fragment)
//												.setProductID(banner_ad
//														.getProductId());
//										SimiManager.getIntance().addFragment(
//												fragment);
//									}
//								} else if (banner_ad.getType()
//										.equals(TYPE_CATEGORY)) {
//									if (banner_ad.getCategoryId() != null
//											&& !banner_ad.getCategoryId()
//											.equals("")
//											&& !banner_ad.getCategoryId()
//											.toLowerCase().equals("null")) {
//										if (banner_ad.getHasChild() != null
//												&& !banner_ad.getHasChild().equals(
//												"")
//												&& !banner_ad.getHasChild()
//												.toLowerCase()
//												.equals("null")) {
//											if (banner_ad.getHasChild().equals("1")) {
//												if (DataLocal.isTablet) {
//													fragment = CategoryFragment.newInstance(
//															banner_ad
//																	.getCategoryName(),
//															banner_ad
//																	.getCategoryId());
//													CateSlideMenuFragment
//															.getIntance()
//															.replaceFragmentCategoryMenu(
//																	fragment);
//													CateSlideMenuFragment
//															.getIntance()
//															.openMenu();
//												} else {
//													fragment = CategoryFragment.newInstance(
//															banner_ad
//																	.getCategoryName(),
//															banner_ad
//																	.getCategoryId());
//													SimiManager.getIntance()
//															.addFragment(fragment);
//												}
//											} else {
//												fragment = CategoryDetailFragment
//														.newInstance();
//												((CategoryDetailFragment) fragment)
//														.setCategoryId(banner_ad
//																.getCategoryId());
//												((CategoryDetailFragment) fragment).setUrlSearch(ConstantsSearch.url_category);
//												if (DataLocal.isTablet) {
//													((CategoryDetailFragment) fragment)
//															.setTag_search(TagSearch.TAG_GRIDVIEW);
//												}
//												SimiManager.getIntance()
//														.addFragment(fragment);
//											}
//										}
//									}
//								} else if (banner_ad.getType().equals(TYPE_WEB)) {
//									if (banner_ad.getUrl() != null
//											&& !banner_ad.getUrl().equals("null")
//											&& !banner_ad.getUrl().equals("")
//											&& URLUtil.isValidUrl(banner_ad
//											.getUrl())) {
//										try {
//											Intent browserIntent = new Intent(
//													Intent.ACTION_VIEW, Uri
//													.parse(banner_ad
//															.getUrl()));
//											context.startActivity(browserIntent);
//										} catch (Exception e) {
//										}
//									}
//								} else {
//									if (banner_ad.getUrl() != null
//											&& !banner_ad.getUrl().equals("null")
//											&& !banner_ad.getUrl().equals("")
//											&& URLUtil.isValidUrl(banner_ad
//											.getUrl())) {
//										try {
//											Intent browserIntent = new Intent(
//													Intent.ACTION_VIEW, Uri
//													.parse(banner_ad
//															.getUrl()));
//											context.startActivity(browserIntent);
//										} catch (Exception e) {
//										}
//									}
//								}
//							}
//							break;
//						}
//						if (isAnimation) {
//							if (lastX < currentX) {
//								mBannerFlipper.setOutAnimation(null);
//								mBannerFlipper.setInAnimation(null);
//								mBannerFlipper.setInAnimation(
//										v.getContext(),
//										Rconfig.getInstance().getId("in_from_left",
//												"anim"));
//								mBannerFlipper.setOutAnimation(
//										v.getContext(),
//										Rconfig.getInstance().getId("out_to_right",
//												"anim"));
//								mBannerFlipper.showNext();
//							}
//							if (lastX > currentX) {
//								mBannerFlipper.setOutAnimation(null);
//								mBannerFlipper.setInAnimation(null);
//								mBannerFlipper.setInAnimation(
//										v.getContext(),
//										Rconfig.getInstance().getId(
//												"in_from_right", "anim"));
//								mBannerFlipper.setOutAnimation(
//										v.getContext(),
//										Rconfig.getInstance().getId("out_to_left",
//												"anim"));
//								mBannerFlipper.showPrevious();
//							}
//						}
//						break;
//					}
//				}
//				return true;
//			}
//		});

		final GestureDetector myGestureListener = new GestureDetector(bannerView.getContext(), new CustomGestureListener());
		bannerView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return myGestureListener.onTouchEvent(event);
			}
		});

		bannerView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimiFragment fragment = null;
				if (banner_ad.getType() != null) {
					if (banner_ad.getType().equals(TYPE_PRODUCT)) {
						if (banner_ad.getProductId() != null
								&& !banner_ad.getProductId().equals("")
								&& !banner_ad.getProductId()
								.toLowerCase().equals("null")) {
							fragment = ProductDetailParentFragment
									.newInstance();
							((ProductDetailParentFragment) fragment)
									.setProductID(banner_ad
											.getProductId());
							SimiManager.getIntance().addFragment(
									fragment);
						}
					} else if (banner_ad.getType()
							.equals(TYPE_CATEGORY)) {
						if (banner_ad.getCategoryId() != null
								&& !banner_ad.getCategoryId()
								.equals("")
								&& !banner_ad.getCategoryId()
								.toLowerCase().equals("null")) {
							requestGetDetailCategory(banner_ad.getCategoryId());
						}
					} else if (banner_ad.getType().equals(TYPE_WEB)) {
						if (banner_ad.getUrl() != null
								&& !banner_ad.getUrl().equals("null")
								&& !banner_ad.getUrl().equals("")
								&& URLUtil.isValidUrl(banner_ad
								.getUrl())) {
							try {
								Intent browserIntent = new Intent(
										Intent.ACTION_VIEW, Uri
										.parse(banner_ad
												.getUrl()));
								context.startActivity(browserIntent);
							} catch (Exception e) {
							}
						}
					} else {
						if (banner_ad.getUrl() != null
								&& !banner_ad.getUrl().equals("null")
								&& !banner_ad.getUrl().equals("")
								&& URLUtil.isValidUrl(banner_ad
								.getUrl())) {
							try {
								Intent browserIntent = new Intent(
										Intent.ACTION_VIEW, Uri
										.parse(banner_ad
												.getUrl()));
								context.startActivity(browserIntent);
							} catch (Exception e) {
							}
						}
					}
				}
			}
		});
	}

	private class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			boolean usedThisFling = false;

			// Is it horizontal?
			if (Math.abs(velocityX) >= 2.0*Math.abs(velocityY)) {
				if (velocityX < 0.0){
					mBannerFlipper.setOutAnimation(null);

					mBannerFlipper.setInAnimation(null);

					mBannerFlipper.setInAnimation(
							parent_view.getContext(),
							Rconfig.getInstance().getId(
									"in_from_right", "anim"));
					mBannerFlipper.setOutAnimation(
							parent_view.getContext(),
							Rconfig.getInstance().getId("out_to_left",
									"anim"));
					mBannerFlipper.showPrevious();
				}
				else {
					mBannerFlipper.setOutAnimation(null);
					mBannerFlipper.setInAnimation(null);

					mBannerFlipper.setInAnimation(
							parent_view.getContext(),
							Rconfig.getInstance().getId("in_from_left",
									"anim"));
					mBannerFlipper.setOutAnimation(
							parent_view.getContext(),
							Rconfig.getInstance().getId("out_to_right",
									"anim"));
					mBannerFlipper.showNext();
				}
				usedThisFling = true;
			}
			return usedThisFling;
		}
	}

	private void requestGetDetailCategory(String categoryID){
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null){
					SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				SimiFragment fragment = null;
				if(collection != null && collection.getCollection().size() > 0){
					CategoryEntity categoryEntity = (CategoryEntity) collection.getCollection().get(0);
						if (categoryEntity.isHasChild()) {
							if (DataLocal.isTablet) {
								fragment = CategoryFragment.newInstance(
										categoryEntity
												.getName(),
										categoryEntity
												.getId());
								CateSlideMenuFragment
										.getIntance()
										.replaceFragmentCategoryMenu(
												fragment);
								CateSlideMenuFragment
										.getIntance()
										.openMenu();
							} else {
								fragment = CategoryFragment.newInstance(
										categoryEntity
												.getName(),
										categoryEntity
												.getId());
								SimiManager.getIntance()
										.addFragment(fragment);
							}
						} else {
							fragment = CategoryDetailFragment
									.newInstance();
							((CategoryDetailFragment) fragment).setUrlSearch("categories");
							((CategoryDetailFragment) fragment)
									.setCategoryId(categoryEntity
											.getId());
							((CategoryDetailFragment) fragment).setCatName(categoryEntity.getName());
							if (DataLocal.isTablet) {
								((CategoryDetailFragment) fragment)
										.setTag_search(TagSearch.TAG_GRIDVIEW);
							}
							SimiManager.getIntance()
									.addFragment(fragment);
						}
					}
				}
		});
		categoryModel.addDataExtendURL(categoryID);
		categoryModel.request();
	}
}
