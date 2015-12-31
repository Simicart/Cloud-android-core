package com.simicart.core.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.price.CategoryDetailPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.EventBlock;

public class ProductListAdapter extends BaseAdapter {

	protected ArrayList<ProductEntity> mProducts;
	protected Context mContext;

	public ProductListAdapter(Context context, ArrayList<ProductEntity> ProductList) {
		mContext = context;
		mProducts = ProductList;
	}

	public ArrayList<ProductEntity> getProductList() {
		return mProducts;
	}

	public void setProductList(ArrayList<ProductEntity> products) {
		mProducts = products;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ProductEntity product = (ProductEntity) getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext)
					.inflate(
							Rconfig.getInstance().layout(
									"core_item_list_search"), null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(Rconfig
					.getInstance().id("tv_productItemName"));
			holder.txtName
					.setTextColor(Config.getInstance().getContent_color());
			holder.imageView = (ImageView) convertView.findViewById(Rconfig
					.getInstance().id("iv_productItemImage"));
			holder.layoutStock = (LinearLayout) convertView
					.findViewById(Rconfig.getInstance().id("layout_stock"));
			holder.layoutStock.setBackgroundColor(Config.getInstance()
					.getOut_stock_background());
			ImageView ic_expand = (ImageView) convertView.findViewById(Rconfig
					.getInstance().id("ic_expand"));
			Drawable icon = mContext.getResources().getDrawable(
					Rconfig.getInstance().drawable("ic_extend"));
			icon.setColorFilter(Config.getInstance().getContent_color(),
					PorterDuff.Mode.SRC_ATOP);
			ic_expand.setImageDrawable(icon);
			// holder.tv_exclTax = (TextView) convertView.findViewById(Rconfig
			// .getInstance().id("tv_exclTax"));
			// holder.tv_inclTax = (TextView) convertView.findViewById(Rconfig
			// .getInstance().id("tv_inclTax"));
			holder.ll_price = (LinearLayout) convertView.findViewById(Rconfig
					.getInstance().id("ll_price"));

			// if (DataLocal.isLanguageRTL) {
			// holder.tv_exclTax.setGravity(Gravity.RIGHT);
			// holder.tv_inclTax.setGravity(Gravity.RIGHT);
			// }
			holder.txt_outstock = (TextView) convertView.findViewById(Rconfig
					.getInstance().id("txt_out_stock"));
			holder.txt_outstock.setTextColor(Config.getInstance()
					.getOut_stock_text());

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (DataLocal.isLanguageRTL) {
			holder.txtName.setGravity(Gravity.RIGHT);
		}

		holder.txtName.setText(product.getName());

		if (holder.imageView != null && product.getImages() != null) {
			if(product!= null && product.getImages().size() > 0) {
				DrawableManager.fetchDrawableOnThread(product.getImages().get(0),
						holder.imageView);
			}else{
				holder.imageView.setImageResource(Rconfig.getInstance().drawable("default_logo"));
			}
		}else{
			holder.imageView.setImageResource(Rconfig.getInstance().drawable("default_logo"));
		}

		//stock
//		if (product.isMangerStock() == true) {
			holder.layoutStock.setVisibility(View.GONE);
//		} else {
//			holder.layoutStock.setVisibility(View.VISIBLE);
//			holder.txt_outstock.setText(Config.getInstance().getText(
//					"Out Stock"));
//		}

		CategoryDetailPriceView price_view = new CategoryDetailPriceView(
				product);
		View view = price_view.createView();
		if (null != view) {
			holder.ll_price.removeAllViewsInLayout();
			holder.ll_price.addView(view);
		}

		RelativeLayout rl_product_list = (RelativeLayout) convertView
				.findViewById(Rconfig.getInstance().id("rel_product_list"));

		EventBlock eventBlock = new EventBlock();
		eventBlock.dispatchEvent("com.simicart.image.product.list",
				rl_product_list, product);
		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView txtName;
		LinearLayout layoutStock;
		TextView txt_outstock;
		LinearLayout ll_price;
	}

	@Override
	public int getCount() {
		return this.mProducts.size();
	}

	@Override
	public Object getItem(int position) {
		return mProducts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
