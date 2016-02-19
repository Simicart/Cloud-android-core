package com.simicart.core.catalog.product.entity.productEnity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.catalog.product.options.groupitem.ItemGroupEntity;
import com.simicart.core.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ProductEntity extends SimiEntity {
    protected String mID;
    protected String mSku;
    protected String mName;
    protected String mType;
    protected String mQrcode;
    protected double mPrice = -1;
    protected double mSalePrice = -1;
    protected String mDescription;
    protected String mShortDescription;
    protected boolean mVisibility;
    protected boolean isStatus;
    protected boolean mVirtual;
    protected String mCreateAt;
    protected String mUpdateAt;
    protected String mTaxClass;
    protected String mAttributeSet;
    protected ArrayList<Attributes> mAttibutes;
    protected String mCustomOption;
    protected String mClientId;
    protected String mSeqNo;
    protected ArrayList<String> mImages;
    protected DimensionsProductEntity mDimensions;
    protected JSONArray mCategoryIds;
    protected ArrayList<ProductEntity> mRelatedProduct;
    protected ArrayList<String> mReviews;
    protected String mDetailRate;
    protected int mSalePriceType;
    protected String mGroupItems;
    protected String mBundleItems;
    protected double mPriceIncludeTax = -1;
    protected String mDownload;
    protected String mTitle;
    protected int mPurchasedSeparately;
    protected ArrayList<String> mUserCanDownload;
    protected double mPriceSaleIncludeTax = -1;
    protected boolean mMangerStock;
    protected String mProductID;

    protected ArrayList<VariantEntity> mVariants;
    protected ArrayList<VariantsAttributeEntity> mVariantAttribute;
    protected ArrayList<CustomOptionEntity> mListCustom;
    protected ArrayList<CustomOptionEntity> mListBundleItem;
    protected ArrayList<ItemGroupEntity> mListItemGroup;

    protected String mVariantID;
    protected int mQtyOrdered;
    protected float mBasePrice;
    protected float mTaxPercent;
    protected String mTaxAmount;
    protected String mBasetaxAmount;
    protected String mRowTotal;
    protected String mBaseRowTotal;
    protected String mWareHouseID;
    protected ArrayList<String> mCustomsDetail;
    protected String mProductType;
    // cart
    protected int mQty;


    private String _id = "_id";
    private String sku = "sku";
    private String name = "name";
    private String type = "type";
    private String qrcode = "qrcode";
    private String price = "price";
    private String sale_price = "sale_price";
    private String description = "description";
    private String short_description = "short_description";
    private String visibility = "visibility";
    private String status = "status";
    private String virtual = "virtual";
    private String created_at = "created_at";
    private String updated_at = "updated_at";
    private String tax_class = "tax_class";
    private String manage_stock = "manage_stock";
    private String attribute_set = "attribute_set";
    private String attributes = "attributes";
    private String custom_option = "custom_option";
    private String client_id = "client_id";
    private String seq_no = "seq_no";
    private String images = "images";
    private String dimensions = "dimensions";
    private String related_products = "related_products";
    private String category_ids = "category_ids";
    private String detailRate = "detailRate";
    private String sale_price_type = "sale_price_type";
    private String price_include_tax = "price_include_tax";
    private String price_sale_include_tax = "sale_price_include_tax";
    private String variant_id = "variant_id";
    private String qty_ordered = "qty_ordered";
    private String base_price = "base_price";
    private String tax_percent = "tax_percent";
    private String tax_amount = "tax_amount";
    private String base_tax_amount = "base_tax_amount";
    private String row_total = "row_total";
    private String base_row_total = "base_row_total";
    private String warehouse_id = "warehouse_id";
    private String customs_detail = "customs_detail";
    private String product_type = "product_type";
    //cart
    private String qty = "qty";
    private String product_id = "product_id";
    private String variants_attribute = "variants_attribute";
    private String variants = "variants";
    private String customs = "customs";
    private String bundle_items = "bundle_items";
    private String group_items = "group_items";


    @Override
    public void parse() {
        if (mJSON != null) {

            if (mJSON.has(images)) {
                mImages = new ArrayList<String>();
                try {
                    JSONArray array = mJSON.getJSONArray(images);
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            if (json.has("url")) {
                                String url = json.getString("url");
                                if (Utils.validateString(url)) {
                                    mImages.add(url);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                }
            }

            if (mJSON.has(_id)) {
                mID = getData(_id);
            }

            if (mJSON.has(sku)) {
                mSku = getData(sku);
            }

            if (mJSON.has(name)) {
                mName = getData(name);
            }

            if (mJSON.has(type)) {
                mType = getData(type);
            }

            if (mJSON.has(qrcode)) {
                mQrcode = getData(qrcode);
            }

            if (mJSON.has(price)) {
                String priceValue = getData(price);
                if (Utils.validateString(priceValue)) {
                    mPrice = Double.parseDouble(priceValue);
                }
            }

            if (mJSON.has(sale_price)) {
                String saleValue = getData(sale_price);
                if (Utils.validateString(saleValue)) {
                    mSalePrice = Double.parseDouble(saleValue);
                }
            }

            if (mJSON.has(sale_price_type)) {
                String s_sale_price_type = getData(sale_price_type);
                if (Utils.validateString(s_sale_price_type)) {
                    mSalePriceType = Integer.parseInt(s_sale_price_type);
                }
            }


            if (mJSON.has(description)) {
                mDescription = getData(description);
            }

            if (mJSON.has(short_description)) {
                mShortDescription = getData(short_description);
            }

            if (mJSON.has(visibility)) {
                String s_visibility = getData(visibility);
                if (Utils.validateString(s_visibility) && s_visibility.equals("1")) {
                    mVisibility = true;
                }
            }

            if (mJSON.has(status)) {
                String statusValue = getData(status);
                if (Utils.validateString(statusValue) && statusValue.equals("1")) {
                    isStatus = true;
                }
            }

            if (mJSON.has(virtual)) {
                String s_virtual = getData(virtual);
                if (Utils.validateString(s_virtual) && s_virtual.equals("1")) {
                    mVirtual = true;
                }
            }


            if (mJSON.has(dimensions)) {

                try {
                    JSONObject json = mJSON.getJSONObject(dimensions);
                    mDimensions = new DimensionsProductEntity();
                    mDimensions.setJSONObject(json);
                    mDimensions.parse();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (mJSON.has(category_ids)) {
                try {
                    mCategoryIds = mJSON.getJSONArray(category_ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(related_products)) {
                try {
                    JSONArray array = mJSON.getJSONArray(related_products);
                    mRelatedProduct = new ArrayList<ProductEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            ProductEntity product = new ProductEntity();
                            product.setJSONObject(json);
                            product.parse();
                            mRelatedProduct.add(product);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(detailRate)) {
                detailRate = getData(detailRate);
            }

            if (mJSON.has(sale_price_type)) {
                try {
                    mSalePriceType = mJSON.getInt(sale_price_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(price_include_tax)) {
                String s_price_include_tax = getData(price_include_tax);
                if (Utils.validateString(s_price_include_tax)) {
                    try {
                        mPriceIncludeTax = Float.parseFloat(s_price_include_tax);
                    } catch (Exception e) {

                    }
                }
            }

            if (mJSON.has(price_sale_include_tax)) {
                String s_price_sale_include_tax = getData(price_sale_include_tax);
                if (Utils.validateString(s_price_sale_include_tax)) {
                    try {
                        mPriceSaleIncludeTax = Float.parseFloat(s_price_sale_include_tax);
                    } catch (Exception e) {

                    }
                }
            }

            if (mJSON.has(variants)) {
                try {
                    JSONArray array = mJSON.getJSONArray(variants);
                    mVariants = new ArrayList<VariantEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            VariantEntity variantEntity = new VariantEntity();
                            variantEntity.setJSONObject(json);
                            variantEntity.parse();
                            mVariants.add(variantEntity);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


            if (mJSON.has(attributes)) {
                mAttibutes = new ArrayList<Attributes>();
                try {
                    JSONObject attrbutesObj = mJSON.getJSONObject(attributes);
                    Iterator<?> iter = attrbutesObj.keys();
                    while (iter.hasNext()) {
                        String key = (String) iter.next();
                        String title = attrbutesObj.getString(key);
                        Attributes attributes = new Attributes();
                        attributes.setID(key);
                        attributes.setTitle(title);
                        mAttibutes.add(attributes);

                    }
                } catch (JSONException e) {
                    mAttibutes = null;
                }

            }

            if (mJSON.has(attribute_set)) {
                mAttributeSet = getData(attribute_set);
            }


            if (mJSON.has(variants_attribute)) {
                try {
                    JSONArray array = mJSON.getJSONArray(variants_attribute);
                    mVariantAttribute = new ArrayList<VariantsAttributeEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            VariantsAttributeEntity attributeEntity = new VariantsAttributeEntity();
                            attributeEntity.setJSONObject(json);
                            attributeEntity.parse();
                            mVariantAttribute.add(attributeEntity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(variant_id)) {
                mVariantID = getData(variant_id);
            }

            if (mJSON.has(customs)) {
                try {
                    JSONArray array = mJSON.getJSONArray(customs);
                    mListCustom = new ArrayList<CustomOptionEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            CustomOptionEntity customOptionEntity = new CustomOptionEntity();
                            customOptionEntity.setJSONObject(json);
                            customOptionEntity.parse();
                            mListCustom.add(customOptionEntity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(qty_ordered)) {
                String qtyOrdered = getData(qty_ordered);
                if (Utils.validateString(qtyOrdered)) {
                    mQtyOrdered = Integer.parseInt(qtyOrdered);
                }
            }

            if (mJSON.has(base_price)) {
                String basePrice = getData(base_price);
                if (Utils.validateString(basePrice)) {
                    mBasePrice = Float.parseFloat(basePrice);
                }
            }

            if (mJSON.has(tax_percent)) {
                String taxPercent = getData(tax_percent);
                if (Utils.validateString(taxPercent)) {
                    mTaxPercent = Float.parseFloat(taxPercent);
                }
            }

            if (mJSON.has(tax_amount)) {
                mTaxAmount = getData(tax_amount);
            }

            if (mJSON.has(base_tax_amount)) {
                mBasetaxAmount = getData(base_tax_amount);
            }

            if (mJSON.has(row_total)) {
                mRowTotal = getData(row_total);
            }

            if (mJSON.has(base_row_total)) {
                mBaseRowTotal = getData(base_row_total);
            }

            if (mJSON.has(warehouse_id)) {
                mWareHouseID = getData(warehouse_id);
            }

            if (mJSON.has(customs_detail)) {
                mCustomsDetail = getArrayData(customs_detail);
            }

            if (mJSON.has(product_type)) {
                mProductType = getData(product_type);
            }

            //cart
            if (mJSON.has(qty)) {
                String qtyValue = getData(qty);
                if (Utils.validateString(qtyValue)) {
                    mQty = Integer.parseInt(qtyValue);
                }
            }

            if (mJSON.has(product_id)) {
                mProductID = getData(product_id);
            }

            if (mJSON.has(bundle_items)) {
                try {
                    JSONArray array = mJSON.getJSONArray(bundle_items);
                    mListBundleItem = new ArrayList<CustomOptionEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            CustomOptionEntity bundleItem = new CustomOptionEntity();
                            bundleItem.setJSONObject(json);
                            bundleItem.parse();
                            mListBundleItem.add(bundleItem);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(group_items)) {
                try {
                    JSONArray array = mJSON.getJSONArray(group_items);
                    mListItemGroup = new ArrayList<ItemGroupEntity>();
                    if (null != array && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = array.getJSONObject(i);
                            ItemGroupEntity itemGroup = new ItemGroupEntity();
                            itemGroup.setJSONObject(json);
                            itemGroup.parse();
                            mListItemGroup.add(itemGroup);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(manage_stock)) {
                String managerStockValue = getData(manage_stock);
                if (Utils.validateString(managerStockValue)) {
                    try {
                        int numberStock = Integer.parseInt(managerStockValue);
                        if (numberStock > 0) {
                            mMangerStock = true;
                        } else {
                            mMangerStock = false;
                        }
                    } catch (Exception e) {
                        mMangerStock = false;
                    }
                }
            }
        }
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        mImages = images;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getSku() {
        return mSku;
    }

    public void setSku(String mSku) {
        this.mSku = mSku;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getQrcode() {
        return mQrcode;
    }

    public void setQrcode(String mQrcode) {
        this.mQrcode = mQrcode;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getSalePrice() {
        return mSalePrice;
    }

    public void setSalePrice(double mSalePrice) {
        this.mSalePrice = mSalePrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public boolean isVisibility() {
        return mVisibility;
    }

    public void setVisibility(boolean mVisibility) {
        this.mVisibility = mVisibility;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    public boolean isVirtual() {
        return mVirtual;
    }

    public void setVirtual(boolean mVirtual) {
        this.mVirtual = mVirtual;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String mCreateAt) {
        this.mCreateAt = mCreateAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setUpdateAt(String mUpdateAt) {
        this.mUpdateAt = mUpdateAt;
    }

    public String getTaxClass() {
        return mTaxClass;
    }

    public void setTaxClass(String mTaxClass) {
        this.mTaxClass = mTaxClass;
    }


    public String getAttributeSet() {
        return mAttributeSet;
    }

    public void setAttributeSet(String mAttributeSet) {
        this.mAttributeSet = mAttributeSet;
    }

    public String getCustomOption() {
        return mCustomOption;
    }

    public void setCustomOption(String mCustomOption) {
        this.mCustomOption = mCustomOption;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String mClientId) {
        this.mClientId = mClientId;
    }

    public String getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(String mSeqNo) {
        this.mSeqNo = mSeqNo;
    }


    public ArrayList<VariantEntity> getVariants() {
        return mVariants;
    }

    public void setVariants(ArrayList<VariantEntity> mVariants) {
        this.mVariants = mVariants;
    }

    public ArrayList<VariantsAttributeEntity> getVariantAttribute() {
        return mVariantAttribute;
    }

    public void setVariantAttribute
            (ArrayList<VariantsAttributeEntity> mVariantAttribute) {
        this.mVariantAttribute = mVariantAttribute;
    }

    public ArrayList<CustomOptionEntity> getListCustom() {
        return mListCustom;
    }

    public void setListCustom(ArrayList<CustomOptionEntity> mListCustom) {
        this.mListCustom = mListCustom;
    }


    public int getQty() {
        return mQty;
    }

    public void setQty(int mQty) {
        this.mQty = mQty;
    }

    public ArrayList<Attributes> getAttibutes() {
        return mAttibutes;
    }

    public void setAttibutes(ArrayList<Attributes> mAttibutes) {
        this.mAttibutes = mAttibutes;
    }

    public ArrayList<ProductEntity> getRelatedProduct() {
        return mRelatedProduct;
    }

    public void setRelatedProduct(ArrayList<ProductEntity> mRelatedProduct) {
        this.mRelatedProduct = mRelatedProduct;
    }


    public double getPriceSaleIncludeTax() {
        return mPriceSaleIncludeTax;
    }

    public void setPriceSaleIncludeTax(double mPriceSaleIncludeTax) {
        this.mPriceSaleIncludeTax = mPriceSaleIncludeTax;
    }

    public double getPriceIncludeTax() {
        return mPriceIncludeTax;
    }

    public void setPriceIncludeTax(double mPriceIncludeTax) {
        this.mPriceIncludeTax = mPriceIncludeTax;
    }

    public ArrayList<CustomOptionEntity> getListBundleItem() {
        return mListBundleItem;
    }

    public void setListBundleItem(ArrayList<CustomOptionEntity> list) {
        this.mListBundleItem = list;
    }

    public ArrayList<ItemGroupEntity> getListItemGroup() {
        return mListItemGroup;
    }

    public void setListItemGroup(ArrayList<ItemGroupEntity> items) {
        mListItemGroup = items;
    }

    public int getmQtyOrdered() {
        return mQtyOrdered;
    }

    public void setmQtyOrdered(int mQtyOrdered) {
        this.mQtyOrdered = mQtyOrdered;
    }

    public boolean isMangerStock() {
        return mMangerStock;
    }

    public void setMangerStock(boolean mMangerStock) {
        this.mMangerStock = mMangerStock;
    }

    public String getProductID() {
        return mProductID;
    }

    public void setProductID(String mProductID) {
        this.mProductID = mProductID;
    }

    public int getSalePriceType() {
        return mSalePriceType;
    }

}
