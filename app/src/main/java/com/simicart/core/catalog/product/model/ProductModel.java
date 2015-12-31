package com.simicart.core.catalog.product.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.config.Constants;

public class ProductModel extends SimiModel {

	public ProductModel() {
		super();
	}

	@Override
	protected void setUrlAction() {
		this.url_action = Constants.GET_PRODUCT_DETAIL;
	}

	public void paserData() {
		super.paserData();

	}
}
