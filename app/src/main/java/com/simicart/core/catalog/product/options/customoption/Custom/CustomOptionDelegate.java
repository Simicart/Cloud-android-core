package com.simicart.core.catalog.product.options.customoption.Custom;

import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

/**
 * Created by MSI on 08/12/2015.
 */
public interface CustomOptionDelegate {

    public void updateStateCacheOption(String id, boolean isSeletecd);

    public void updatePriceForHeader(String price);

    public void updatePriceParent(ValueCustomOptionEntity value, boolean operation);

    public void clearOther(String id);

}
