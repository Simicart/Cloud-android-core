package com.simicart.core.catalog.product.options.variants;

import java.util.ArrayList;

/**
 * Created by MSI on 08/12/2015.
 */
public interface ItemVariantAttributeDelegate {
//    public void onSend(String idItemVariantAtt, ArrayList<String> listIDVariant);
//
//    public void onReceive(String idItemVarAttSent, ArrayList<String> listIDVariantOfItemSent);

    public void clearSelected();

    public void onSelected();

    public void onEnable();

    public void clearEnable();

    public String getValue();
}
