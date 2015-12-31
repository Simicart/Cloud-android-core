package com.simicart.core.catalog.product.options.variants;

import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;

import java.util.ArrayList;

/**
 * Created by MSI on 09/12/2015.
 */
public interface VariantAttributeDelegate {

    public  void onSend(String idItemSend);

    public ArrayList<VariantEntity> onReceive( ArrayList<VariantEntity> listVariantAvaiable);

    public String getIDAttribute();
}
