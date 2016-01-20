package com.simicart.core.common.price;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.splashscreen.entity.BaseCurrencyEntity;
import com.simicart.core.splashscreen.entity.ConfigEntity;
import com.simicart.core.splashscreen.entity.CurrencyEntity;
import com.simicart.core.splashscreen.entity.FormatConfigEntity;
import com.simicart.core.splashscreen.entity.TaxConfigEntity;

/**
 * Created by MSI on 10/12/2015.
 */
public class ProductDetailPriceView {

    private int DYNAMIC = 0;
    private int FIXED = 0;

    protected ProductEntity mProductEntity;
    protected Context mContext;
    // if this variable is true, price of product detail and product list will contain tax
    protected boolean hasTaxProduct;
    protected LinearLayout ll_price;
    protected TextView tv_first;
    protected TextView tv_second;
    protected VariantEntity mVariant;
    protected boolean isBundleProduct = false;

    private float mPrice = -1;
    private float mSalePrice = -1;
    private float mPriceTax = -1;
    private float mSalePriceTax = -1;


    public ProductDetailPriceView(ProductEntity product) {
        mProductEntity = product;
        mContext = SimiManager.getIntance().getCurrentContext();
        hasTaxProduct = Config.getInstance().isTaxShop();
        String type_product = product.getType();
        if (type_product.equals("bundle")) {
            isBundleProduct = true;
        }
    }

    public View getView() {
        return ll_price;
    }


    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ll_price = (LinearLayout) inflater.inflate(Rconfig.getInstance().layout("core_price_layout"), null);
        tv_first = (TextView) ll_price.findViewById(Rconfig.getInstance().id("tv_fist_price"));
        tv_second = (TextView) ll_price.findViewById(Rconfig.getInstance().id("tv_second_price"));
        if (hasTaxProduct) {
            mPriceTax = mProductEntity.getPriceIncludeTax();
            mSalePriceTax = mProductEntity.getPriceSaleIncludeTax();
            Log.e("ProductDetailPriveView ", " Create View " + " Price Tax " + mPriceTax + " Sale Price Tax " + mSalePriceTax);
            createPriceWithTax();
        } else {
            mPrice = mProductEntity.getPrice();
            mSalePrice = mProductEntity.getSalePrice();

            Log.e("ProductDetailPriveView ", " Create View " + "Price " + mPrice + " Sale Price ");

            createPriceWithoutTax();
        }

        Log.e("ProductDetailPriveView ", " Create View " + "Price " + mPrice + " Sale Price " + mSalePrice + " Price Tax " + mPriceTax + " Sale Price Tax " + mSalePriceTax);


        return ll_price;
    }

    protected void createPriceWithoutTax() {
        if (mPrice == mSalePrice) {
            tv_second.setVisibility(View.GONE);
            if (mPrice >= 0) {
                String sPrice = getPrice(mPrice);
                if (Utils.validateString(sPrice)) {
                    tv_first.setText(sPrice);
                }
            } else {
                tv_first.setVisibility(View.GONE);
            }
        } else {
            if (mSalePrice < 0) {
                tv_second.setVisibility(View.GONE);
                if (mPrice >= 0) {
                    String content_price = getPrice(mPrice);
                    tv_first.setText(content_price);
                } else {
                    tv_first.setVisibility(View.GONE);
                }
            } else {

                String content_salePrice = getPrice(mSalePrice);

                Log.e("ProductDetailPriceView ", "---> Create Price Without Tax 001" + content_salePrice);
                tv_second.setText(content_salePrice);


                if (mPrice >= 0) {
                    tv_first.setPaintFlags(tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    String content_price = getPrice(mPrice);
                    Log.e("ProductDetailPriceView ", "---> Create Price Without Tax 002" + content_price);
                    tv_first.setText(content_price);
                } else {
                    tv_first.setVisibility(View.GONE);
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    protected void createPriceWithTax() {

        Log.e("Product Detail Price View ", "Price Tax " + mPriceTax + " Sale Price Tax " + mSalePriceTax);

        if (mPriceTax == mSalePriceTax) {
            if (mPriceTax < 0) {
                Log.e("Product Detail Price View ", "createPriceWithTax CALL  createPriceWithoutTax");
                mPrice = mProductEntity.getPrice();
                mSalePrice = mProductEntity.getSalePrice();
                createPriceWithoutTax();
            } else {
                tv_second.setVisibility(View.GONE);
                if (mPriceTax >= 0) {
                    String content_priceTax = getPrice(mPriceTax);
                    tv_first.setText(content_priceTax);
                } else {
                    tv_first.setVisibility(View.GONE);
                }
            }
        } else {
            if (mSalePriceTax < 0) {
                tv_second.setVisibility(View.GONE);
                if (mPriceTax >= 0) {
                    String content_priceTax = getPrice(mPriceTax);
                    tv_first.setText(content_priceTax);
                } else {
                    tv_first.setVisibility(View.GONE);
                }
            } else {
                Log.e("ProductDetailPriceView ", "show Price Tax and Sale Price Tax");
                if (mSalePriceTax >= 0) {
                    String content_salePriceTax = getPrice(mSalePriceTax);
                    Log.e("ProductDetailPriceView ", "CONTENT SALE PRICE TAX " + content_salePriceTax);
                    tv_second.setVisibility(View.VISIBLE);
                    tv_second.setText(content_salePriceTax);
                } else {
                    tv_second.setVisibility(View.GONE);
                }
                if (mPriceTax >= 0) {
                    tv_first.setPaintFlags(tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    String content_priceTax = getPrice(mPriceTax);
                    Log.e("ProductDetailPriceView ", "CONTENT PRICE TAX " + content_priceTax);
                    tv_first.setText(content_priceTax);
                } else {
                    tv_first.setVisibility(View.GONE);
                }
            }
        }
    }

    protected String getPrice(float price) {
        return Config.getInstance().getPrice(price);
    }


    public View updatePriceWithVariant(VariantEntity variant, boolean isAdd) {

        mVariant = variant;

        if (hasTaxProduct) {
            updatePriceVariantWithTax();
        } else {
            updatePriceVariantWithoutTax();
        }

        return ll_price;
    }

    public void updatePriceVariantWithTax() {
        mPriceTax = mVariant.getPriceTax();
        mSalePriceTax = mVariant.getSalePriceTax();

        Log.e("ProductDetailPriceView ", "update Price Variant " + mPriceTax + " : " + mSalePriceTax);

        if (mPriceTax == mSalePriceTax) {
            if (mPriceTax == -1) {
                updatePriceVariantWithoutTax();
            }

        }

        createPriceWithTax();
    }

    public void updatePriceVariantWithoutTax() {
        mPrice = mVariant.getPrice();
        mSalePrice = mVariant.getSalePrice();

        Log.e("ProductDetailPriceView ", "update Price without Variant " + mPrice + " : " + mSalePrice);

        createPriceWithoutTax();
    }


    public View updatePriceWithCustomOption(ValueCustomOptionEntity entity, boolean isAdd) {

        Log.e("ProductDetailPriceView ", "update Price Custom Option " + isBundleProduct);

        if (isBundleProduct) {
            return updatePriceForBundle(entity, isAdd);
        }

        float price = entity.getPrice();
        float taxPrice = entity.getTaxPrice();
        Log.e("ProductDetailPriceView ", "update Price Custom Option " + price + " : " + taxPrice);
        Log.e("ProductDetailPriceView ", "Price " + mPrice + "Sale Price " + mSalePrice + "Price Tax " + mPriceTax + "Sale PRice Tax " + mSalePriceTax);


        calculPrice(price, taxPrice, isAdd);
        if (hasTaxProduct) {
            createPriceWithTax();
        } else {
            createPriceWithoutTax();
        }
        return ll_price;
    }

    private View updatePriceForBundle(ValueCustomOptionEntity entity, boolean isAdd) {
        float price = entity.getPrice();
        float salePrice = entity.getSalePrice();
        float taxPrice = entity.getTaxPrice();
        float taxSalePrice = entity.getSaleTaxPrice();

        Log.e("ProductDetailPriceView ", "Price For Bundle " + price + " : " + salePrice + " : " + taxPrice + " : " + taxSalePrice);

        int price_type = mProductEntity.getSalePriceType();
        if (price_type == DYNAMIC) {
            // dynamic: price of product = total of price of item
            calPriceBundleDynamic(price, salePrice, taxPrice, taxSalePrice, isAdd);
        } else {
            // fixed: price of product = price of product + price of item
            calPriceBundleFixed(price, salePrice, taxPrice, taxSalePrice, isAdd);
        }
        if (hasTaxProduct) {
            createPriceWithTax();
        } else {
            createPriceWithoutTax();
        }
        return ll_price;
    }

    private void calPriceBundleDynamic(float price, float salePrice, float taxPrice, float taxSalePrice, boolean isAdd) {
        if (taxSalePrice > 0) {
            if (isAdd) {
                mSalePriceTax = mSalePriceTax + taxSalePrice;
            } else {
                mSalePriceTax = mSalePriceTax - taxSalePrice;
            }
            mPrice = -1;
            mSalePrice = -1;
            mPriceTax = -1;
        } else if (taxPrice > 0) {
            if (isAdd) {
                mPriceTax = mPriceTax + taxPrice;
            } else {
                mPriceTax = mPriceTax - taxPrice;
            }
            mPrice = -1;
            mSalePrice = -1;
            mSalePriceTax = -1;
        } else if (salePrice > 0) {
            if (isAdd) {
                mSalePrice = mSalePrice + salePrice;
            } else {
                mSalePrice = mSalePrice - salePrice;
            }
            mPrice = -1;
            mSalePrice = -1;
            mSalePriceTax = -1;
        } else if (price > 0) {
            if (isAdd) {
                mPrice = mPrice + price;
            } else {
                mPrice = mPrice - price;
            }
            mSalePrice = -1;
            mSalePriceTax = -1;
            mPriceTax = -1;
        }
    }

    private void calPriceBundleFixed(float price, float salePrice, float taxPrice, float taxSalePrice, boolean isAdd) {
        if (mSalePriceTax >= 0 || mPriceTax >= 0) {
            calPriceBundleFixedWithTax(taxPrice, taxSalePrice, isAdd);
        } else {
            calPriceBundleFixedWithoutTax(price, salePrice, isAdd);
        }

    }

    private void calPriceBundleFixedWithTax(float taxPrice, float taxSalePrice, boolean isAdd) {
        if (taxSalePrice >= 0) {
            if (isAdd) {
                mSalePriceTax = mSalePriceTax + taxSalePrice;
                mPriceTax = mPriceTax + taxSalePrice;
            } else {
                mSalePriceTax = mSalePriceTax - taxSalePrice;
                mPriceTax = mPriceTax - taxSalePrice;
            }
        } else if (taxPrice >= 0) {
            if (isAdd) {
                mSalePriceTax = mSalePriceTax + taxPrice;
                mPriceTax = mPriceTax + taxPrice;
            } else {
                mSalePriceTax = mSalePriceTax - taxPrice;
                mPriceTax = mPriceTax - taxPrice;
            }
        }
    }

    private void calPriceBundleFixedWithoutTax(float price, float salePrice, boolean isAdd) {
        if (salePrice >= 0) {
            if (isAdd) {
                mSalePrice = mSalePrice + salePrice;
                mPrice = mPrice + salePrice;
            } else {
                mSalePrice = mSalePrice - salePrice;
                mPrice = mPrice - salePrice;
            }
        } else if (price >= 0) {
            if (isAdd) {
                mSalePrice = mSalePrice + price;
                mPrice = mPrice + price;
            } else {
                mSalePrice = mSalePrice - price;
                mPrice = mPrice - price;
            }
        }

    }


    public View updatePriceWithItemGroup(ItemGroupEntity entity, boolean isAdd) {
        float price = entity.getPrice();
        float salePrice = entity.getSalePrice();
        float taxPrice = entity.getPriceTax();
        float taxSalePrice = entity.getSalePriceTax();

        Log.e("ProductDetailPriceView ", "update Price Item Group " + price + " : " + salePrice + ":" + taxPrice + ":" + taxSalePrice);


        if (taxPrice >= 0 || taxSalePrice >= 0) {
            calculPrice(taxPrice, taxSalePrice, isAdd);
        } else {
            calculPrice(price, salePrice, isAdd);
        }

        if (hasTaxProduct) {
            createPriceWithTax();
        } else {
            createPriceWithoutTax();
        }

        Log.e("ProductDetailPriceView ", "AFTER -->" + "Price " + mPrice + "Sale Price " + mSalePrice + "Price Tax " + mPriceTax + "Sale PRice Tax " + mSalePriceTax);
        return ll_price;
    }

    protected void calculPrice(float price, float taxPrice, boolean isAdd) {
        if (isAdd) {
            addPrice(price, taxPrice);
        } else {
            subPrice(price, taxPrice);
        }
    }

    private void addPrice(float price, float taxPrice) {
        if (mPrice >= 0) {
            if (mSalePrice >= 0) {
                mSalePrice = mSalePrice + price;
            } else {
                mPrice = mPrice + price;
            }
        } else if (mSalePrice >= 0) {
            mSalePrice = mSalePrice + price;
        }

        if (mPriceTax >= 0) {
            if (mSalePriceTax >= 0) {
                if (taxPrice >= 0) {
                    mSalePriceTax = mSalePriceTax + taxPrice;
                } else {
                    mSalePriceTax = mSalePriceTax + price;
                }
            } else {
                if (taxPrice >= 0) {
                    mPriceTax = mPriceTax + taxPrice;
                } else {
                    mPriceTax = mPriceTax + price;
                }
            }
        } else if (mSalePriceTax >= 0) {
            if (taxPrice >= 0) {
                mSalePriceTax = mSalePriceTax + taxPrice;
            } else {
                mSalePriceTax = mSalePriceTax + price;
            }
        }
    }

    private void subPrice(float price, float taxPrice) {
        if (mPrice >= 0) {
            if (mSalePrice >= 0) {
                mSalePrice = mSalePrice - price;
            } else {
                mPrice = mPrice - price;
            }
        } else if (mSalePrice >= 0) {
            mSalePrice = mSalePrice - price;
        }

        if (mPriceTax >= 0) {
            if (mSalePriceTax >= 0) {
                if (taxPrice >= 0) {
                    mSalePriceTax = mSalePriceTax - taxPrice;
                } else {
                    mSalePriceTax = mSalePriceTax - price;
                }
            } else {
                if (taxPrice >= 0) {
                    mPriceTax = mPriceTax - taxPrice;
                } else {
                    mPriceTax = mPriceTax - price;
                }
            }
        } else if (mSalePriceTax >= 0) {
            if (taxPrice >= 0) {
                mSalePriceTax = mSalePriceTax - taxPrice;
            } else {
                mSalePriceTax = mSalePriceTax - price;
            }
        }

        if (mPrice < -1) {
            mPrice = -1;
        }

        if (mSalePrice < -1) {
            mSalePrice = -1;
        }

        if (mPriceTax < -1) {
            mPriceTax = -1;
        }

        if (mSalePriceTax < -1) {
            mSalePriceTax = -1;
        }

    }


}
