package com.simicart.core.catalog.product.delegate;

import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;

/**
 * Created by MSI on 11/12/2015.
 */
public interface OptionProductDelegate {

    public void updatePriceWithVariant(VariantEntity variant, boolean isAdd);

    public void updatePriceWithCustomOption(ValueCustomOptionEntity entity, boolean isAdd);

    public void updatePriceWithItemGroup(ItemGroupEntity entity, boolean isAdd);

}
