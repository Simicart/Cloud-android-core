package com.simicart.core.customer.block;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.adapter.OrderHisAdapter;
import com.simicart.core.customer.delegate.OrderHistoryDelegate;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.customer.entity.OrderHistory;

public class OrderHistoryBlock extends SimiBlock implements
        OrderHistoryDelegate {

    protected ListView listview_order_history;
    protected OrderHisAdapter mAdapter;
    protected View mLoadMore;

    public OrderHistoryBlock(View view, Context context) {
        super(view, context);
    }

    public void setItemClicker(OnItemClickListener clicker) {
        listview_order_history.setOnItemClickListener(clicker);
    }

    public void setScrollListener(OnScrollListener listener) {
        listview_order_history.setOnScrollListener(listener);
    }



    @Override
    public void initView() {
        listview_order_history = (ListView) mView.findViewById(Rconfig
                .getInstance().id("list_order"));
        ColorDrawable sage = new ColorDrawable(Config.getInstance()
                .getLine_color());
        listview_order_history.setDivider(sage);
        listview_order_history.setDividerHeight(1);
    }

    @Override
    public void drawView(SimiCollection collection) {
        OrderHistory entity = (OrderHistory) collection.getCollection().get(0);
        if (entity != null) {
            ArrayList<OrderHisDetail> orderHisDetails = new ArrayList<>();
            orderHisDetails = entity.getmOrders();
            if (orderHisDetails.size() > 0) {
               showListOrder(orderHisDetails);
            } else {
                showNotifyEmpty();
            }

        } else {
            showNotifyEmpty();
        }
    }

    private void showListOrder(ArrayList<OrderHisDetail> orderHisDetails)
    {
        if (null == mAdapter) {
            mAdapter = new OrderHisAdapter(mContext, orderHisDetails);
            listview_order_history.setAdapter(mAdapter);
        } else {
            mAdapter.setOrderHis(orderHisDetails);
            mAdapter.notifyDataSetChanged();
        }
    }

    protected void showNotifyEmpty() {
        TextView tv_empty = new TextView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_empty.setGravity(Gravity.CENTER);
        tv_empty.setText(Config.getInstance().getText("Order history is empty"));
        tv_empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tv_empty.setTextColor(Config.getInstance().getContent_color());
        ((LinearLayout) mView).addView(tv_empty, params);
    }



    @SuppressLint("InflateParams")
    @Override
    public void addFooterView() {
        LayoutInflater inflater = (LayoutInflater) listview_order_history
                .getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLoadMore = inflater.inflate(
                Rconfig.getInstance().layout("core_loading_list"), null, false);
        removeFooterView();
        listview_order_history.post(new Runnable() {
            @Override
            public void run() {
                listview_order_history.addFooterView(mLoadMore);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    int lastViewedPosition = listview_order_history
                            .getFirstVisiblePosition();
                    View v = listview_order_history.getChildAt(0);
                    int topOffset = (v == null) ? 0 : v.getTop();

                    listview_order_history.setAdapter(mAdapter);
                    listview_order_history.setSelectionFromTop(
                            lastViewedPosition, topOffset);
                }
            }
        });

    }

    @Override
    public void removeFooterView() {
        listview_order_history.post(new Runnable() {
            @Override
            public void run() {
                while (listview_order_history.getFooterViewsCount() > 0) {
                    listview_order_history.removeFooterView(mLoadMore);
                }
            }
        });
    }
}
