package com.simicart.core.catalog.product.entity.productEnity;

import com.simicart.core.base.model.entity.SimiEntity;

/**
 * Created by MSI on 07/12/2015.
 */
public class DimensionsProductEntity extends SimiEntity {
    protected String mWeight;
    protected String mLength;
    protected String mWidth;
    protected String mHeight;

    private String weight = "weight";
    private String length = "length";
    private String width = "width";
    private String height = "height";

    @Override
    public void parse() {
        // parse weight
        if (mJSON.has(weight)) {
            mWeight = getData(weight);
        }

        // parse length
        if (mJSON.has(length)) {
            mLength = getData(length);
        }

        // parse width
        if (mJSON.has(width)) {
            mWidth = getData(width);
        }

        // parse height
        if (mJSON.has(height)) {
            mHeight = getData(height);
        }
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
