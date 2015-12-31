package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 02/12/2015.
 */
public class SaleConfigEntity extends SimiEntity {
    protected boolean isEnable;
    protected String salesTax;
    protected String purchaseTax;
    protected String warhouse;
    protected String paymentTerm;

    private String prices_entered_with_tax = "prices_entered_with_tax";
    private String default_sales_tax = "default_sales_tax";
    private String default_purchase_tax = "default_purchase_tax";
    private String default_warehouse = "default_warehouse";
    private String payment_term = "payment_term";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(prices_entered_with_tax)){
                String enable = getData(prices_entered_with_tax);
                if(Utils.validateString(enable) && enable.equals("yes")){
                    isEnable = true;
                }
            }

            if(mJSON.has(default_sales_tax)){
                salesTax = getData(default_sales_tax);
            }

            if(mJSON.has(default_purchase_tax)){
                purchaseTax = getData(default_purchase_tax);
            }

            if(mJSON.has(default_warehouse)){
                warhouse = getData(default_warehouse);
            }

            if(mJSON.has(payment_term)){
                paymentTerm = getData(payment_term);
            }
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public String getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(String purchaseTax) {
        this.purchaseTax = purchaseTax;
    }

    public String getWarhouse() {
        return warhouse;
    }

    public void setWarhouse(String warhouse) {
        this.warhouse = warhouse;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }
}
