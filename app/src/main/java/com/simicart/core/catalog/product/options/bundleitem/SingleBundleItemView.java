package com.simicart.core.catalog.product.options.bundleitem;

import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.Custom.singlechoice.SingleCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MSI on 11/12/2015.
 */
public class SingleBundleItemView extends SingleCustomOptionView {
    public SingleBundleItemView(CustomOptionEntity cacheOption) {
        super(cacheOption);
    }

    @Override
    public JSONObject getDataForCheckout() {
        try {
            JSONObject json = new JSONObject();

            // put id of custom option
            String id = mCustomOption.getID();
            json.put("bundle_id", id);

            // put id of value
            JSONArray array = new JSONArray();
            for (int i = 0; i < mListValueView.size(); i++) {
                ValueView valueView = mListValueView.get(i);
                if (valueView.isSelected()) {
                    ValueCustomOptionEntity valueEntity = valueView.getValue();
                    String id_value = valueEntity.getID();
                    array.put(id_value);
                }
            }
            json.put("value_id", array);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }







}
