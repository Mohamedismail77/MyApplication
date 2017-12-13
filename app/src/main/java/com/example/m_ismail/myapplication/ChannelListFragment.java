package com.example.m_ismail.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by m-ismail on 13/12/17.
 */

public class ChannelListFragment extends Fragment {

    public ArrayList<ChannelDetails> channels = new ArrayList<>();
    public Callback mCallback;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ChannelDetails channel_1 = new ChannelDetails();
        ChannelDetails channel_2 = new ChannelDetails();
        ChannelDetails channel_3 = new ChannelDetails();

        channel_1.setmTitle("Apple TV");
        channel_1.setmChannelLink("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8");

        channel_2.setmTitle("Big Buck Bunny");
        channel_2.setmChannelLink("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov");

        channel_3.setmTitle("Cyber Tech Media");
        channel_3.setmChannelLink("http://www.cybertechmedia.com/samples/raycharles.wmv");

        channels.add(channel_1);
        channels.add(channel_2);
        channels.add(channel_3);
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.channel_list_fragment, container, false);

        ListAdapter adapter = new ListAdapter(getActivity(),R.layout.channel_list,channels);
        ListView mListView = (ListView) frameLayout.findViewById(R.id.channel_list);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getActivity(), channels.get(i).getmTitle(),Toast.LENGTH_LONG).show();
                mCallback.onItemSelected();

            }
        });

        mListView.setAdapter(adapter);

        return frameLayout;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (Callback) activity;
    }

    public interface Callback{

        public void onItemSelected();

    }



}
