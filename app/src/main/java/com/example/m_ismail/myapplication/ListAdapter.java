package com.example.m_ismail.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by m-ismail on 13/12/17.
 */

public class ListAdapter extends ArrayAdapter<ChannelDetails> {

    private Context mContext;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<ChannelDetails> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChannelDetails channel = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.channel_list, parent,false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(channel.getmTitle());

        return convertView;
    }
}
