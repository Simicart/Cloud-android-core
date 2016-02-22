package com.simicart.core.catalog.product.options.variants;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantsAttributeEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by MSI on 08/12/2015.
 */
public class VariantAttributeView implements VariantAttributeDelegate {

    private VariantsAttributeEntity mAttribute;
    private Context mContext;
    private LinearLayout ll_variantAtt;
    private TextView tv_item_selected;
    private LinearLayout ll_body;
    private ManageVariantAttributeDelegate mDelegate;
    private ArrayList<VariantEntity> mListVariant;
    private ArrayList<ItemVariantAttributeView> mListItem;
    private boolean isUserSeleted;
    private boolean isAutoSelected;
    private String mCurrentValue = "";
    private ImageView imv_arr;

    public VariantAttributeView(VariantsAttributeEntity att, Context context, ArrayList<VariantEntity> listVariant, ManageVariantAttributeDelegate delegate) {
        mContext = context;
        mAttribute = att;
        mListVariant = listVariant;
        mDelegate = delegate;
        mListItem = new ArrayList<ItemVariantAttributeView>();
    }

    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ll_variantAtt = (LinearLayout) inflater.inflate(Rconfig.getInstance().layout("core_variant_attribute"), null);

        // header
        RelativeLayout rlt_header = (RelativeLayout) ll_variantAtt.findViewById(Rconfig.getInstance().id("rlt_header"));
        TextView tv_title = (TextView) ll_variantAtt.findViewById(Rconfig.getInstance().id("tv_title"));
        tv_item_selected = (TextView) ll_variantAtt.findViewById(Rconfig.getInstance().id("tv_item_selected"));
        imv_arr = (ImageView) ll_variantAtt.findViewById(Rconfig.getInstance().id("imv_arr"));
        String name = mAttribute.getName();
        Log.e("VariantAttributeView", "Name " + name);
        tv_title.setText(mAttribute.getName());
        imv_arr.setRotation(90);

        rlt_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateOption();
            }
        });

        // body
        ll_body = (LinearLayout) ll_variantAtt.findViewById(Rconfig.getInstance().id("ll_body"));
        ArrayList<String> listAtt = mAttribute.getValues();
        for (int i = 0; i < listAtt.size(); i++) {
            String title = listAtt.get(i);
            if (Utils.validateString(title)) {
                ItemVariantAttributeView itemView = new ItemVariantAttributeView(title, this, mContext);
                View view = itemView.createView();
                if (null != view) {
                    ll_body.addView(view);
                    mListItem.add(itemView);
                }
            }
        }
        return ll_variantAtt;
    }

    protected void animateOption() {
        if (ll_body.getVisibility() == View.VISIBLE) {
            Utils.collapse(ll_body);
            imv_arr.setRotation(0);
        } else {
            Utils.expand(ll_body);
            imv_arr.setRotation(90);
        }
    }


    @Override
    public void onSend(String valueSelected) {
        // goi ManageVariantAttribute send other delegate
        isUserSeleted = true;
        ArrayList<VariantEntity> variants = getListVariantAvaiable(valueSelected, mListVariant);
        String idAtt = mAttribute.getID();
        selecteAItem(valueSelected);
        mDelegate.onSend(valueSelected, variants, idAtt);

    }

    @Override
    public ArrayList<VariantEntity> onReceive(ArrayList<VariantEntity> listVariantAvaiable) {

        // kiem tra neu nguoi dung da chon 1 gia tri va gia tri nay phu hop voi list variant thi khong can chon nua

        ArrayList<String> listValue = new ArrayList<String>();
        ArrayList<VariantEntity> listVariant = new ArrayList<VariantEntity>();
        String idAtt = mAttribute.getID();
        for (int i = 0; i < listVariantAvaiable.size(); i++) {
            VariantEntity variant = listVariantAvaiable.get(i);
            if (variant.has(idAtt)) {
                String value = variant.getValue(idAtt);
                if (!checkValueInArray(value, listValue)) {
                    listValue.add(value);
                    listVariant.add(variant);
                }
            }
        }

        if (listValue.size() > 0) {
            if (isUserSeleted) {
                if (!checkValueInArray(mCurrentValue, listValue)) {
                    // gia tri ma nguoi dung da chon khong phu hop nen can chon lai mot gia tri khac
                    for (int i = 0; i < mListItem.size(); i++) {
                        ItemVariantAttributeView itemView = mListItem.get(i);
                        String valueItem = itemView.getValue();
                        for (int j = 0; j < listValue.size(); j++) {
                            String value = listValue.get(j);
                            if (value.equals(valueItem)) {
                                itemView.onEnable();
                            } else {
                                itemView.clearEnable();
                            }

                        }
                    }
                    String randdomItem = listValue.get(0);
                    isUserSeleted = false;
                    selecteAItem(randdomItem);
                    ArrayList<VariantEntity> listVariantResult = getListVariantAvaiable(randdomItem, listVariant);
                    return listVariantResult;
                }

            } else {
                // neu gia tri hien tai khong phai do nguoi dung chon thi se chon lai mot phan tu khac
                for (int i = 0; i < mListItem.size(); i++) {
                    ItemVariantAttributeView itemView = mListItem.get(i);
                    String valueItem = itemView.getValue();
                    for (int j = 0; j < listValue.size(); j++) {
                        String value = listValue.get(j);
                        if (value.equals(valueItem)) {
                            itemView.onEnable();
                        } else {
                            itemView.clearEnable();
                        }

                    }
                }
                String randdomItem = listValue.get(0);
                isUserSeleted = false;
                selecteAItem(randdomItem);
                ArrayList<VariantEntity> listVariantResult = getListVariantAvaiable(randdomItem, listVariant);
                return listVariantResult;
            }

        }

        return null;
    }

    protected void selecteAItem(String value) {
        mCurrentValue = value;
        for (int i = 0; i < mListItem.size(); i++) {
            ItemVariantAttributeView itemView = mListItem.get(i);
            if (value.equals(itemView.getValue())) {
                itemView.onSelected();
            } else {
                itemView.clearSelected();
            }
        }
    }

    @Override
    public String getIDAttribute() {
        return mAttribute.getID();
    }

    // can tim ra list id variant voi item duoc chon
    public ArrayList<VariantEntity> getListVariantAvaiable(String valueItemSent, ArrayList<VariantEntity> listVariant) {
        ArrayList<VariantEntity> listVariantAvaiable = new ArrayList<VariantEntity>();
        String id_attribute = mAttribute.getID();
        for (int i = 0; i < listVariant.size(); i++) {
            VariantEntity variant = listVariant.get(i);
            if (variant.has(id_attribute)) {
                String value_attribute = variant.getValue(id_attribute);
                if (valueItemSent.equals(value_attribute)) {
                    listVariantAvaiable.add(variant);
                }
            }

        }
        return listVariantAvaiable;
    }

    protected boolean checkValueInArray(String value, ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (value.equals(arrayList.get(i))) {
                return true;
            }
        }
        return false;

    }


}
