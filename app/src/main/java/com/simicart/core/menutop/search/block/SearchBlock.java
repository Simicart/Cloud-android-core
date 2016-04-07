package com.simicart.core.menutop.search.block;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.menutop.search.view.PredicateLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Martial on 4/6/2016.
 */
public class SearchBlock extends SimiBlock {

    TextView tv_recent_search_title, tv_trending_search_title;
    PredicateLayout ll_recent_search, ll_trending_search;
    Context context;

    List<String> list_recent_search;

    public SearchBlock(View view, Context context) {
        super(view, context);
        mView.setBackgroundColor(Config.getInstance().getKey_color());
        this.context = context;
    }

    @Override
    public void initView() {
        //super.initView();
        tv_recent_search_title = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_recent_search_title"));
        tv_trending_search_title = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_trending_search_title"));
        ll_recent_search = (PredicateLayout) mView.findViewById(Rconfig.getInstance().id("ll_recent_search"));
        ll_trending_search = (PredicateLayout) mView.findViewById(Rconfig.getInstance().id("ll_trending_search"));

        tv_recent_search_title.setText(Config.getInstance().getText("Recent searches").toUpperCase());
        tv_trending_search_title.setText(Config.getInstance().getText("Trending searches").toUpperCase());

        if(DataLocal.getRecentSearch() == null) {
            tv_recent_search_title.setVisibility(View.GONE);
            ll_recent_search.setVisibility(View.GONE);
        } else {
            list_recent_search = DataLocal.getRecentSearch();
            tv_recent_search_title.setVisibility(View.VISIBLE);
            ll_recent_search.setVisibility(View.VISIBLE);
            initRecentSearch();
        }
    }

    @Override
    public void drawView(SimiCollection collection) {

    }

    public void initRecentSearch() {
        for(int i=0;i<list_recent_search.size();i++) {
            String item = list_recent_search.get(i);

            final TextView search_item = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 10, 0);
            search_item.setText(item);
            search_item.setTextSize(14);
            search_item.setSingleLine(true);

            CardView card = new CardView(mContext);
            card.setLayoutParams(params);
            card.setElevation(4);
            card.setRadius(25);
            card.setContentPadding(15, 15, 15, 15);
            card.setUseCompatPadding(true);

            card.addView(search_item);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimiManager.getIntance().openSearchScreen(search_item.getText().toString());
                    SimiManager.getIntance().onUpdateSearchView(false);
                }
            });

            ll_recent_search.addView(card);
        }
    }

}
