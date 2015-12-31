package com.simicart.core.catalog.product.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.block.TechSpecsBlok;
import com.simicart.core.catalog.product.controller.TechSpecsController;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class TechSpecsFragment extends SimiFragment {
	protected TechSpecsBlok mBlock;
	protected TechSpecsController mController;

	protected ArrayList<Attributes> mAttributes;

	public static TechSpecsFragment newInstance() {
		TechSpecsFragment fragment = new TechSpecsFragment();
		return fragment;
	}

	public void setAttributes(ArrayList<Attributes> attributes) {
		mAttributes = attributes;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout(
						"core_information_description_layout"), container,
				false);
		Context context = getActivity();
		mBlock = new TechSpecsBlok(rootView, context);
		mBlock.initView();
		if(mController == null){
			mController = new TechSpecsController();
			mController.setAttributes(mAttributes);
			mController.setDelegate(mBlock);
			mController.onStart();
		}else{
			mController.setAttributes(mAttributes);
			mController.setDelegate(mBlock);
			mController.onResume();
		}
		rootView.setBackgroundColor(Config.getInstance().getApp_backrground());

		return rootView;
	}
}
