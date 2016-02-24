package com.simicart.core.common.price;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;

/**
 * Created by MSI on 10/12/2015.
 */
public class TotalPriceView {

    protected Context mContext;
    protected QuoteEntity mQuote;

    public TotalPriceView(QuoteEntity quote) {
        mQuote = quote;
        mContext = SimiManager.getIntance().getCurrentContext();
    }


    public View createTotalView() {
        TableLayout tbl_price = new TableLayout(mContext);

        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.bottomMargin = Utils.getValueDp(3);
        params.topMargin = Utils.getValueDp(3);

        TableRow tbr_subTotal = (TableRow) getSubTotalView();
        if (null != tbr_subTotal) {
            tbl_price.addView(tbr_subTotal);
        }

        TableRow tbr_tax = (TableRow) getTaxAmountView();
        if (null != tbr_tax) {
            tbl_price.addView(tbr_tax);
        }

        TableRow tbr_discount = (TableRow) getDiscountAmountView();
        if(null != tbr_discount){
            tbl_price.addView(tbr_discount);
        }

        TableRow tbr_grandTotal = (TableRow) getGrandTotal();
        if (null != tbr_grandTotal) {
            tbl_price.addView(tbr_grandTotal);
        }

        return tbl_price;

    }

    protected View getSubTotalView() {

        float sub_total = mQuote.getSubTotal();


        if (sub_total == 0) {
            return null;
        }
        Log.e("TotalPriceView ", "get SubTotal View " + sub_total);

        String s_price = Config.getInstance().getPrice(sub_total);
        return createRowView("SubTotal", s_price);
    }

    protected View getTaxAmountView() {
        float tax_amount = mQuote.getTaxAmount();
        if (tax_amount == 0) {
            return null;
        }

        Log.e("TotalPriceView ", "get Tax Amount View " + tax_amount);

        String s_price = Config.getInstance().getPrice(tax_amount);
        return createRowView("Tax", s_price);
    }

    protected View getDiscountAmountView(){
        float discount_amount = mQuote.getDiscountAmount();
        if(discount_amount == 0){
            return null;
        }

        Log.e("TotalPriceView ", "get Discount Amount View " + discount_amount);
        String s_price = Config.getInstance().getPrice(discount_amount);
        return createRowView("Discount", s_price);
    }

    protected View getGrandTotal() {
        float grand_total = mQuote.getGrandTotal();
        if (grand_total == 0) {
            return null;
        }
        Log.e("TotalPriceView ", "get Grand Total View " + grand_total);
        String s_price = Config.getInstance().getPrice(grand_total);
        return createRowView("GrandTotal", s_price);
    }


    protected View createRowView(String label, String s_price) {
        TableRow tbr_price = new TableRow(mContext);
        String s_label = Config.getInstance().getText(label);
        String price = "<font color='" + Config.getInstance().getPrice_color() + "'>"
                + s_price + "</font>";

        TextView tb_label = (TextView) showViewLabel(s_label);
        TextView tv_price = (TextView) showViewPrice(price);

        tbr_price.addView(tb_label);

        tbr_price.addView(tv_price);

        return tbr_price;

    }

    protected View showViewLabel(String content) {
        TextView tv_label = new TextView(mContext);
        tv_label.setGravity(Gravity.RIGHT);
        tv_label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        String title = Html.fromHtml(content) + ": ";
        tv_label.setText(title);
        return tv_label;
    }

    protected  View showViewPrice(String content){
        TextView tv_price = new TextView(mContext);
        tv_price.setGravity(Gravity.RIGHT);
        tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_price.setText(Html.fromHtml(content));
        return tv_price;
    }
}
