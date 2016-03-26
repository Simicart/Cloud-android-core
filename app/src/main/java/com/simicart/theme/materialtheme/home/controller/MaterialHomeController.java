package com.simicart.theme.materialtheme.home.controller;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.simicart.R;
import com.simicart.core.base.controller.SimiController;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialHomeController extends SimiController {
    protected  MaterialViewPager.Listener onClickTitleViewPager;

    public MaterialViewPager.Listener getOnClickTitleViewPager() {
        return onClickTitleViewPager;
    }

    @Override
    public void onStart() {
        onClickTitleViewPager = new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }
                return null;
            }
        };
    }

    @Override
    public void onResume() {

    }
}
