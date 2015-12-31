package com.simicart.core.catalog.categorydetail.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.config.Constants;

public class SearchModelList extends SimiModel {



	@Override
	protected void setUrlAction() {
		url_action = Constants.SEARCH_PRODUCTS;
	}
}
