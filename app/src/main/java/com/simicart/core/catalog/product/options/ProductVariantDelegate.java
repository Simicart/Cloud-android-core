package com.simicart.core.catalog.product.options;

import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;

/**
 * Created by MSI on 09/12/2015.
 */
public interface ProductVariantDelegate {

    public void onUpdateVariantSelected(VariantEntity variantEntity);

}
