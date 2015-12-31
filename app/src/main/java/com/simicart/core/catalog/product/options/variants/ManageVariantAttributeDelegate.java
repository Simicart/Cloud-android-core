package com.simicart.core.catalog.product.options.variants;

import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;

import java.util.ArrayList;

/**
 * Created by MSI on 09/12/2015.
 */
public interface ManageVariantAttributeDelegate {

    public void onSend(String valueAttOfItemSend, ArrayList<VariantEntity> listIDVariantAvaiable,String idItemSend);
}
