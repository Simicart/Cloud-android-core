package com.simicart.core.catalog.product.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.simicart.core.adapter.ProductListAdapter;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.block.RelatedProductBlock;
import com.simicart.core.catalog.product.controller.RelatedProductController;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.controller.ProductListListenerController;

import java.util.ArrayList;

public class RelatedProductFragment extends SimiFragment {
	protected ArrayList<ProductEntity> mListProductRelated;
	protected RelatedProductBlock mBlock;
	protected RelatedProductController mController;
	protected ListView lv_relatedProduct;
	protected ProductListAdapter mAdapter;

	public void setListProductRelated(ArrayList<ProductEntity> mListProductRelated) {
		this.mListProductRelated = mListProductRelated;
	}

	public static RelatedProductFragment newInstance() {
		RelatedProductFragment fragment = new RelatedProductFragment();
		return fragment;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				Rconfig.getInstance().layout("core_information_related_product_layout"), container,
				false);
		Context context = getActivity();
//		mBlock = new RelatedProductBlock(view, context);
//		mBlock.initView();
//		if(mController == null){
//			mController = new RelatedProductController();
//			mController.setDelegate(mBlock);
//			mController.onStart();
//		}else{
//			mController.setDelegate(mBlock);
//			mController.onResume();
//		}

		lv_relatedProduct = (ListView) view.findViewById(Rconfig.getInstance()
				.id("lv_relatedProduct"));
		ColorDrawable sage = new ColorDrawable(Config.getInstance()
				.getLine_color());
		lv_relatedProduct.setDivider(sage);
		lv_relatedProduct.setDividerHeight(1);

		mAdapter = new ProductListAdapter(context, mListProductRelated);
		lv_relatedProduct.setAdapter(mAdapter);

		ProductListListenerController listner = new ProductListListenerController();
		listner.setProductList(mListProductRelated);

		lv_relatedProduct.setOnItemClickListener(listner
				.createTouchProductList());

		return view;
	}
}
