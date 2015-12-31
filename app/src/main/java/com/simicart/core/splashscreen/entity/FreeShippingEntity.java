package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 02/12/2015.
 */
public class FreeShippingEntity extends SimiEntity {
    protected boolean isEnable;
    protected String title;
    protected String description;

    private String free_shipping_enable = "free_shipping_enable";
    private String free_shipping_title = "free_shipping_title";
    private String free_shipping_description = "free_shipping_description";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.equals(free_shipping_enable)){
                String enable = getData(free_shipping_enable);
                if(Utils.validateString(enable) && enable.equals("1")){
                    isEnable = true;
                }
            }

            if(mJSON.equals(free_shipping_title)){
                title = getData(free_shipping_title);
            }

            if(mJSON.has(free_shipping_description)){
                description = getData(free_shipping_description);
            }
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
