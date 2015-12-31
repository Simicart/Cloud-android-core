package com.simicart.core.catalog.product.options;

import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

/**
 * Created by MSI on 11/12/2015.
 */
public interface ManageOptionDelegate {

    public void updatePrice(ValueCustomOptionEntity option, boolean isAdd);

}
