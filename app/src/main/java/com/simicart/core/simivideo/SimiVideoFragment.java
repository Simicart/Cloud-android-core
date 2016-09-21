package com.simicart.core.simivideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by frank on 9/21/16.
 */

public class SimiVideoFragment extends SimiFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int idView = Rconfig.getInstance().layout("plugins_youtube_fragment_layout");
        rootView = inflater.inflate(idView, container, false);

        YoutubeEnity youtubeEnity = new YoutubeEnity("Simicart", "https://www.youtube.com/watch?v=AfgX7GB_Rkc");
        ArrayList<YoutubeEnity> entities = new ArrayList<>();
        entities.add(youtubeEnity);

        ListView mListView = (ListView) rootView.findViewById(Rconfig.getInstance().id(
                "youtube_list"));
        YoutubeAdapter mAdapter = new YoutubeAdapter(getActivity(), entities);
        mListView.setAdapter(mAdapter);


        return rootView;
    }
}
