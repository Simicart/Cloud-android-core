package com.simicart.core.catalog.product.options.customoption;

import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

/**
 * Created by MSI on 11/12/2015.
 */
public interface ManageCustomOptionDelegate {

    public void updatePrice(ValueCustomOptionEntity option, boolean isAdd);
}
