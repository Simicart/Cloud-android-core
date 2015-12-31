package com.simicart.core.catalog.product.options.customoption.value.text;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;

/**
 * Created by MSI on 08/12/2015.
 */
public class ValueText extends ValueView {

    private boolean ischeckedOptionText;

    public ValueText(ValueCustomOptionEntity value, Context context, CustomOptionDelegate delegate) {
        super(value, context, delegate);
    }

    @Override
    public View createView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText edt_value = new EditText(mContext);
        edt_value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        edt_value.setLayoutParams(param);
        edt_value.setPadding(Utils.getValueDp(5), Utils.getValueDp(10),
                Utils.getValueDp(5), Utils.getValueDp(10));
        edt_value.setHint(Config.getInstance().getText("Text"));
        edt_value.setHintTextColor(Color.GRAY);
        edt_value.setTextColor(Color.parseColor("#000000"));
        edt_value.setBackgroundResource(0);

        String firstText = mValue.getContentText();
        if (Utils.validateString(firstText)) {
            edt_value.setText(firstText);
            ischeckedOptionText = true;
            selectOption(true);
        }

        edt_value.addTextChangedListener(new TextWatcher() {
            boolean isAddPrice = true;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                String textOption = edt_value.getText().toString();
                if (textOption.length() != 0) {
                    mValue.setContentText(edt_value.getText().toString());
                    mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                            mValue.getPrice()));
                    updateView(true);
                } else {
                    mValue.setContentText("");
                    mDelegate.updatePriceForHeader("");
                    isAddPrice = true;
                    ischeckedOptionText = false;
                    updateView(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return edt_value;
    }

    @Override
    public boolean isComplete() {
        String content = mValue.getContentText();
        return Utils.validateString(content);
    }
}