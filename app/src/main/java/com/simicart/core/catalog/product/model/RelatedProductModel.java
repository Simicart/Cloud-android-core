package com.simicart.core.catalog.product.model;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.config.Constants;

public class RelatedProductModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		url_action = Constants.GET_RELATED_PRODUCTS;
	}



}
