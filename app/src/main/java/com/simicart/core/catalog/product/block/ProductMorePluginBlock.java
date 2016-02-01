package com.simicart.core.catalog.product.block;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;
import com.simicart.core.style.material.floatingactionbutton.FloatingActionButton;
import com.simicart.core.style.material.floatingactionbutton.FloatingActionsMenu;

public class ProductMorePluginBlock extends SimiBlock {
	protected ProductEntity mProduct;
	protected FloatingActionsMenu mMultipleActions;
	protected FloatingActionButton more_share;

	protected ArrayList<FloatingActionButton> mListButton;

	public ArrayList<FloatingActionButton> getListButton() {
		return mListButton;
	}

	public void setProduct(ProductEntity mProduct) {
		this.mProduct = mProduct;
	}

	public ProductMorePluginBlock(View view, Context context) {
		super(view, context);
	}

	public void setListenerMoreShare(OnClickListener onclick) {
		more_share.setOnClickListener(onclick);
	}

	@Override
	public void initView() {
		mListButton = new ArrayList<FloatingActionButton>();
		mMultipleActions = (FloatingActionsMenu) mView.findViewById(Rconfig
				.getInstance().id("more_plugins_action"));
		mMultipleActions.createButton(mContext, Config.getInstance()
				.getButton_background(), Config.getInstance()
				.getButton_background(), Config.getInstance()
				.getButton_text_color());
		more_share = new FloatingActionButton(mContext);
		more_share.setColorNormal(Color.parseColor("#FFFFFF"));
		more_share.setColorPressed(Color.parseColor("#f4f4f4"));
		more_share.setIcon(Rconfig.getInstance().drawable("ic_share"));
		mListButton.add(more_share);
		for (int i = 0; i < mListButton.size(); i++) {
			mMultipleActions.addButton(mListButton.get(i));
		}

		Intent intent = new Intent("com.simicart.core.catalog.product.block.ProductMorePluginBlock");
		SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
		blockEntity.setView(mView);
		blockEntity.setEntity(mProduct);
		blockEntity.setContext(mContext);
		blockEntity.setBlock(this);
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.ENTITY, blockEntity);
		intent.putExtra(Constants.DATA, bundle);
		LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);



	}
}
