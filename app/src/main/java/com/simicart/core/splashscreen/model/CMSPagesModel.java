package com.simicart.core.splashscreen.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.splashscreen.entity.CMSPageEntity;

import java.util.ArrayList;

/**
 * Created by James Crabby on 12/3/2015.
 */
public class CMSPagesModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("pages");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("CMSPagesModel", mJSONResult.toString());
        CMSPageEntity cmsPageEntity = new CMSPageEntity();
        cmsPageEntity.setJSONObject(mJSONResult);
        cmsPageEntity.parse();

        ArrayList<SimiEntity> arrayEntity = new ArrayList<>();
        arrayEntity.add(cmsPageEntity);
        collection.setCollection(arrayEntity);
    }

}
