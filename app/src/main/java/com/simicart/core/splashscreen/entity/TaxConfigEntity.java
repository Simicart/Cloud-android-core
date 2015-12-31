package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 10/12/2015.
 */
public class TaxConfigEntity extends SimiEntity {

    protected boolean isEnableTax;
    protected String mCalculTaxBaseOn;
    protected String mShippingTaxClass;
    protected boolean isRounding;
    protected String mAdditionalTaxClasses;
    protected boolean isTaxShop;
    protected boolean isTaxCart;
    protected String mPriceDisplaySuffix;
    protected String mDisplayTaxTotals;

    private String tax_enable = "tax_enable";
    private String calcul_tax_base_on = "calcul_tax_base_on";
    private String shipping_tax_class = "shipping_tax_class";
    private String rounding = "rounding";
    private String additional_tax_classes = "additional_tax_classes";
    private String display_prices_in_shop = "display_prices_in_shop";
    private String display_prices_during_cart = "display_prices_during_cart";
    private String price_display_suffix = "price_display_suffix";
    private String display_tax_totals = "display_tax_totals";

    @Override
    public void parse() {
        if (mJSON.has(tax_enable)) {
            String s_tax_enable = getData(tax_enable);
            if (Utils.validateString(s_tax_enable) && s_tax_enable.toUpperCase().equals("yes")) {
                isEnableTax = true;
            }
        }

        if (mJSON.has(calcul_tax_base_on)) {
            mCalculTaxBaseOn = getData(calcul_tax_base_on);
        }

        if (mJSON.has(shipping_tax_class)) {
            mShippingTaxClass = getData(shipping_tax_class);
        }

        if (mJSON.has(rounding)) {
            String s_rouding = getData(rounding);
            if (Utils.validateString(s_rouding) && s_rouding.toUpperCase().equals("yes")) {
                isRounding = true;
            }
        }

        if (mJSON.has(additional_tax_classes)) {
            mAdditionalTaxClasses = getData(additional_tax_classes);
        }

        if (mJSON.has(display_prices_in_shop)) {
            String in_shop = getData(display_prices_in_shop);
            if (Utils.validateString(in_shop) && in_shop.toUpperCase().equals("including tax")) ;
            {
                isTaxShop = true;
            }
        }

        if (mJSON.has(display_prices_during_cart)) {
            String s_checkout = getData(display_prices_during_cart);
            if (Utils.validateString(s_checkout) && s_checkout.toUpperCase().equals("including tax")) {
                isTaxCart = true;
            }
        }

        if (mJSON.has(price_display_suffix)) {
            mPriceDisplaySuffix = getData(price_display_suffix);
        }

        if (mJSON.has(display_tax_totals)) {
            mDisplayTaxTotals = getData(display_tax_totals);
        }

    }

    public boolean isEnableTax() {
        return isEnableTax;
    }

    public void setIsEnableTax(boolean isEnableTax) {
        this.isEnableTax = isEnableTax;
    }

    public String getCalculTaxBaseOn() {
        return mCalculTaxBaseOn;
    }

    public void setCalculTaxBaseOn(String mCalculTaxBaseOn) {
        this.mCalculTaxBaseOn = mCalculTaxBaseOn;
    }

    public String getShippingTaxClass() {
        return mShippingTaxClass;
    }

    public void setShippingTaxClass(String mShippingTaxClass) {
        this.mShippingTaxClass = mShippingTaxClass;
    }

    public boolean isRounding() {
        return isRounding;
    }

    public void setIsRounding(boolean isRounding) {
        this.isRounding = isRounding;
    }

    public String getAdditionalTaxClasses() {
        return mAdditionalTaxClasses;
    }

    public void setAdditionalTaxClasses(String mAdditionalTaxClasses) {
        this.mAdditionalTaxClasses = mAdditionalTaxClasses;
    }

    public boolean isTaxShop() {
        return isTaxShop;
    }

    public void setIsTaxShop(boolean isTaxShop) {
        this.isTaxShop = isTaxShop;
    }

    public boolean isTaxCart() {
        return isTaxCart;
    }

    public void setIsTaxCart(boolean isTaxCart) {
        this.isTaxCart = isTaxCart;
    }

    public String getPriceDisplaySuffix() {
        return mPriceDisplaySuffix;
    }

    public void setPriceDisplaySuffix(String mPriceDisplaySuffix) {
        this.mPriceDisplaySuffix = mPriceDisplaySuffix;
    }

    public String getDisplayTaxTotals() {
        return mDisplayTaxTotals;
    }

    public void setDisplayTaxTotals(String mDisplayTaxTotals) {
        this.mDisplayTaxTotals = mDisplayTaxTotals;
    }
}
