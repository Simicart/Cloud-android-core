package com.simicart.core.event.block;

import android.content.Context;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;

import java.io.Serializable;

/**
 * Created by MSI on 28/01/2016.
 */
public class SimiEventBlockEntity implements Serializable {
    private SimiCollection simiCollection;
    private View view;
    private SimiBlock mBlock;
    private Context mContext;
    private SimiEntity mEntity;

    public SimiEntity getEntity() {
        return mEntity;
    }

    public void setEntity(SimiEntity mEntity) {
        this.mEntity = mEntity;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public SimiBlock getBlock() {
        return mBlock;
    }

    public void setBlock(SimiBlock mBlock) {
        this.mBlock = mBlock;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public SimiCollection getSimiCollection() {
        return simiCollection;
    }

    public void setSimiCollection(SimiCollection simiCollection) {
        this.simiCollection = simiCollection;
    }
}
