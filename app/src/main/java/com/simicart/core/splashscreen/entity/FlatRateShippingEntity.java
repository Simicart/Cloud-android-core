package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 02/12/2015.
 */
public class FlatRateShippingEntity extends SimiEntity {
    protected boolean isEnable;
    protected String title;
    protected String description;
    protected float costPerOrder;
    protected String costAddTo;
    protected float cost;
    protected float handlingFee;
    protected String addOnRate;

    private String flat_rate_enable = "flat_rate_enable";
    private String flat_rate_title = "flat_rate_title";
    private String flat_rate_description = "flat_rate_description";
    private String flat_rate_cost_per_order = "flat_rate_cost_per_order";
    private String flat_rate_cost_add_to = "flat_rate_cost_add_to";
    private String flat_rate_cost = "flat_rate_cost";
    private String flat_rate_handling_fee = "flat_rate_handling_fee";
    private String flat_rate_add_on_rate = "flat_rate_add_on_rate";


    @Override
    public void parse() {


        if (null != mJSON) {
            // parse enable
            if (mJSON.has(flat_rate_enable)) {
                String enable = getData(flat_rate_enable);
                if (Utils.validateString(enable) && enable.equals("1")) {
                    isEnable = true;
                }
            }

            // parse title
            if (mJSON.has(flat_rate_title)) {
                title = getData(flat_rate_title);
            }

            // parse description
            if (mJSON.has(flat_rate_description)) {
                description = getData(flat_rate_description);
            }

            // parse cost per order
            if (mJSON.has(flat_rate_cost_per_order)) {
                String cost_per_order = getData(flat_rate_cost_per_order);
                if (Utils.validateString(cost_per_order)) {
                    costPerOrder = Float.parseFloat(cost_per_order);
                }
            }

            // parse cost_add_to
            if (mJSON.has(flat_rate_cost_add_to)) {
                costAddTo = getData(flat_rate_cost_add_to);
            }

            // parse rate cost
            if (mJSON.has(flat_rate_cost)) {
                String s_cost = getData(flat_rate_cost);
                if (Utils.validateString(s_cost)) {
                    cost = Float.parseFloat(s_cost);
                }
            }

            // parse handling fee
            if (mJSON.has(flat_rate_handling_fee)) {
                String s_handling_fee = getData(flat_rate_handling_fee);
                if (Utils.validateString(s_handling_fee)) {
                    handlingFee = Float.parseFloat(s_handling_fee);
                }
            }

            // parse add on rate
            if (mJSON.has(flat_rate_add_on_rate)) {
                addOnRate = getData(flat_rate_add_on_rate);
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

    public float getCostPerOrder() {
        return costPerOrder;
    }

    public void setCostPerOrder(float costPerOrder) {
        this.costPerOrder = costPerOrder;
    }

    public String getCostAddTo() {
        return costAddTo;
    }

    public void setCostAddTo(String costAddTo) {
        this.costAddTo = costAddTo;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(float handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getAddOnRate() {
        return addOnRate;
    }

    public void setAddOnRate(String addOnRate) {
        this.addOnRate = addOnRate;
    }
}
