package com.tempo.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andrewcofano on 3/13/17.
 */

public class MemberListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mDataSource;

    public MemberListAdapter(android.content.Context context, List<String> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_member, parent, false);

        TextView userEmail =
                (TextView) rowView.findViewById(R.id.userEmailView);

        String user = (String) getItem(position);

        userEmail.setText(user);

        return rowView;
    }


    @Override
    public int getCount() {
        return mDataSource.size();
    }


    public void setmDataSource(List<String> mDataSource) {
        this.mDataSource = mDataSource;
    }



    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
