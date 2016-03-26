package com.simicart.theme.matrixtheme.home.block;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.matrixtheme.home.adapter.CategoryCustomAdapter;
import com.simicart.theme.matrixtheme.home.adapter.Theme1CustomScrollView;
import com.simicart.theme.matrixtheme.home.entity.OrderProduct;
import com.simicart.theme.matrixtheme.home.entity.Theme1SpotProduct;

public class SpotProductHomeTheme1Block extends SimiBlock {

    private Theme1CustomScrollView lv_CategoryBottom;

    public SpotProductHomeTheme1Block(View view, Context context) {
        super(view, context);
        lv_CategoryBottom = (Theme1CustomScrollView) mView.findViewById(Rconfig
                .getInstance().id("lv_category_bottom"));
    }


    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> entity = collection.getCollection();
        ArrayList<Theme1SpotProduct> categories = new ArrayList<Theme1SpotProduct>();
        if (null != entity && entity.size() > 0) {
            for (SimiEntity simiEntity : entity) {
                Theme1SpotProduct category = new Theme1SpotProduct();
                category.setJSONObject(simiEntity.getJSONObject());
                category.parse();
                categories.add(category);
            }
        } else {
            Theme1SpotProduct category = new Theme1SpotProduct();
            category.setID("fake");
            category.setKey("Feature Products");
            category.setName("Feature Products");
            categories.add(category);
        }
        if (categories.size() > 0) {
            setBannerCategoryBottom(categories);
        }
    }


    private void setBannerCategoryBottom(ArrayList<Theme1SpotProduct> categories) {
        if (categories == null) {
            return;
        }
        if (mContext != null) {
            CategoryCustomAdapter adapter = new CategoryCustomAdapter(mContext,
                    categories);
            lv_CategoryBottom.setAdapter(mContext, adapter);
        }

    }


}
