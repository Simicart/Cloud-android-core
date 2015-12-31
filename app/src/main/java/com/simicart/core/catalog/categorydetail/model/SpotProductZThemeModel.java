package com.simicart.core.catalog.categorydetail.model;

import com.simicart.core.base.model.SimiModel;

public class SpotProductZThemeModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		url_action = "ztheme/api/get_spot_products";
	}



}
