package com.simicart.core.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.delegate.CartAdapterDelegate;
import com.simicart.core.checkout.entity.Option;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

@SuppressLint({"DefaultLocale", "ClickableViewAccessibility", "ViewHolder"})
public class CartListAdapter extends BaseAdapter {

    protected ArrayList<ProductEntity> mCarts;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected CartAdapterDelegate mDelegate;

    public CartListAdapter(Context context, ArrayList<ProductEntity> CartList) {
        this.mContext = context;
        this.mCarts = CartList;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDelegate(CartAdapterDelegate delegate) {
        mDelegate = delegate;
    }

    public ArrayList<ProductEntity> getCartList() {
        return this.mCarts;
    }

    public void setListCart(ArrayList<ProductEntity> carts) {
        mCarts = carts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (DataLocal.isLanguageRTL) {
            convertView = mInflater.inflate(
                    Rconfig.getInstance().layout("rtl_item_cart_layout"), null);
        } else {
            convertView = mInflater
                    .inflate(
                            Rconfig.getInstance().layout(
                                    "core_item_cart_layout"), null);
        }
        convertView.setBackgroundColor(Config.getInstance().getApp_backrground());

        final ProductEntity cart = (ProductEntity) getItem(position);

        // name
        TextView tv_name = (TextView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_name"));
        tv_name.setTextColor(Config.getInstance().getContent_color());
        tv_name.setText(cart.getName());

        // id
        TextView tv_id = (TextView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_id"));
        tv_id.setTextColor(Config.getInstance().getContent_color());
        tv_id.setText("" + cart.getID());

        // image
        ImageView img_item = (ImageView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_image"));
        if (cart.getImages() != null && cart.getImages().size() > 0) {
            String img = cart.getImages().get(0);
            DrawableManager.fetchDrawableOnThread(img, img_item);
        } else {
            img_item.setImageResource(Rconfig.getInstance().drawable("default_logo"));
        }

        // quantity
        TextView tv_quantity = (TextView) convertView.findViewById(Rconfig
                .getInstance().id("tv_quantity"));
        tv_quantity.setTextColor(Config.getInstance().getContent_color());
        tv_quantity.setText(Config.getInstance().getText("Quantity"));
        RelativeLayout rl_quanity = (RelativeLayout) convertView
                .findViewById(Rconfig.getInstance().id("rl_item_cart_quantity"));

        TextView tv_Qty = (TextView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_qty"));
        tv_Qty.setTextColor(Config.getInstance().getContent_color());
        tv_Qty.setText("" + cart.getQty());
        if (null != mDelegate) {
            rl_quanity.setOnClickListener(mDelegate.getClickQtyItem(position,
                    cart.getQty()));
        }

        // delete item
        ImageView img_delete = (ImageView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_del"));
        Drawable icon = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_delete"));
        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_delete.setImageDrawable(icon);

        if (null != mDelegate) {
            ArrayList<String> listID = new ArrayList<String>();
            for (int i = 0; i < mCarts.size(); i++) {
                listID.add(mCarts.get(i).getProductID());
            }
            img_delete
                    .setOnTouchListener(mDelegate.getOnTouchListener(position));
            img_item.setOnClickListener(mDelegate.getClickItemCartListener(
                    position, listID));
        }


        // price
        TextView tv_price = (TextView) convertView.findViewById(Rconfig
                .getInstance().id("item_cart_price"));
        tv_price.setText(Config.getInstance().getPrice(
                cart.getPrice()));
        tv_price.setTextColor(Color.parseColor(Config.getInstance()
                .getPrice_color()));

        // stock
        LinearLayout ll_stock = (LinearLayout) convertView.findViewById(Rconfig
                .getInstance().id("ll_stock"));
        ll_stock.setBackgroundColor(Config.getInstance().getKey_color());
        ll_stock.setVisibility(View.GONE);

        // if (DataLocal.isTablet) {
        View line_cart_bottom = (View) convertView.findViewById(Rconfig
                .getInstance().id("line_cart_bottom"));
        line_cart_bottom.setBackgroundColor(Config.getInstance()
                .getLine_color());
        // }

        return convertView;
    }

    @Override
    public int getCount() {
        return this.mCarts.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarts.get(position);
    }

    @Override
    public long getItemId(int psosition) {
        return 0;
    }

    protected void displayOptions(TextView tv_options, ArrayList<Option> options) {
        int total = 0;
        if (options != null) {
            total = options.size();
        }
        if (total > 0) {
            String html = "<dl>";
            for (int i = 0; i < total; i++) {
                Option option = options.get(i);

                // if (option.getOption_price().equals("0")
                // || option.getOption_price().equals("")
                // || option.getOption_price() == null) {
                if (i > 1) {
                    if (i == 2) {
                        html += "........";
                    }
                } else {
                    html += "<b>";
                    html += option.getOption_title();
                    html += "</b>";
                    html += "<br/>";
                    html += option.getOption_value();
                    html += "<br/>";
                }
                // } else {
                // String option_price = option.getOption_price();
                // if (i > 1) {
                // if (i == 2) {
                // html += "........";
                // }
                // } else {
                // html += "<b>";
                // html += option.getOption_title();
                // html += "</b>";
                // html += "<br/>";
                // html += Config.getInstance().getPrice(option_price);
                // html += "<br/>";
                // }
                // }
            }
            html += "</dl>";
            tv_options.setText(Html.fromHtml(html));
        } else {
            tv_options.setText("");
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0);
            lp.weight = 3;
            tv_options.setLayoutParams(lp);
        }
    }
}
