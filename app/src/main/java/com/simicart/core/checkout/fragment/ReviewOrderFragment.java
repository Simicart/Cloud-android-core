package com.simicart.core.checkout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.block.ReviewOrderBlock;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.checkout.controller.ReviewOrderController;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.entity.MyAddress;

public class ReviewOrderFragment extends SimiFragment {

	protected ReviewOrderBlock mBlock;
	protected ReviewOrderController mController;
	protected MyAddress mBillingAddress;
	protected MyAddress mShippingAddress;
	protected int mAfterControll;
	protected QuoteEntity mQuote;

	public static ReviewOrderFragment newInstance() {
		ReviewOrderFragment fragment = new ReviewOrderFragment();
		fragment.setTargetFragment(fragment, ConfigCheckout.TARGET_REVIEWORDER);
		return fragment;
	}

	public void setAfterControll(int controll) {
		mAfterControll = controll;
	}

	public void setBilingAddress(MyAddress address) {
		mBillingAddress = address;
	}

	public void setShippingAddress(MyAddress address) {
		mShippingAddress = address;
	}

	public int getAftercontroll() {
		return mAfterControll;
	}

	public MyAddress getBillingAddress() {
		return mBillingAddress;
	}

	public MyAddress getShippingAddress() {
		return mShippingAddress;
	}

	public void setQuote(QuoteEntity mQuote) {
		this.mQuote = mQuote;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ConfigCheckout.checkPaymentMethod = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setScreenName("Review Order Screen");
		SimiManager.getIntance().showCartLayout(false);
		View view = inflater.inflate(
				Rconfig.getInstance().layout("core_review_order_layout"),
				container, false);
		if (DataLocal.isLanguageRTL) {
			view = inflater.inflate(
					Rconfig.getInstance().layout("rtl_review_order_layout"),
					container, false);
		}
		Context context = getActivity();
		mBlock = new ReviewOrderBlock(view, context);
		mBlock.initView();

		if (mController == null) {
			mController = new ReviewOrderController();
			mController.setDelegate(mBlock);
			mController.setBillingAddress(mBillingAddress);
			mController.setShippingAddress(mShippingAddress);
			mController.setAfterControll(mAfterControll);
			mController.onStart();
		} else {
			mController.setDelegate(mBlock);
			mController.onResume();
		}
		mBlock.setOnChoiceBillingAddress(mController
				.getOnChoiceBillingAddress());
		mBlock.setOnChoiceShippingAddress(mController
				.getOnChoiceShippingAddress());
		mBlock.setClickPlaceNow(mController.getOnPlaceNow());
		mBlock.setCouponCodeListener(mController.getCouponCodeListener());
		mBlock.setOnViewDetaiProduct(mController.getOnViewDetailProduct());
		mBlock.setCouponChange(mController.getCouponCodeChange());
		mBlock.setOnClickRemoveListener(mController.getOnRemoveCoupon());

		return view;
	}

	@Override
	public void onDestroy() {
		SimiManager.getIntance().showCartLayout(true);
		if (null != mController) {
			mController.onDestroy();
		}
		super.onDestroy();
	}

}
