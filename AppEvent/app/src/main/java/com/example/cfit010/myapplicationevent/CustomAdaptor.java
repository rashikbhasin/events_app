package com.example.cfit010.myapplicationevent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdaptor extends BaseAdapter {


    ArrayList<DataModel> data;
    private static LayoutInflater inflater=null;

    public CustomAdaptor(Activity context, ArrayList<DataModel> list)
    {
        Activity mActivity = context;
        this.data = list;
        inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

          //  LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.program_list, null);
        //TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView name_head = (TextView) listViewItem.findViewById(R.id.name_head);
        TextView info_head = (TextView) listViewItem.findViewById(R.id.info_head);
        TextView date_head = (TextView) listViewItem.findViewById(R.id.date_head);
        TextView venue_head = (TextView) listViewItem.findViewById(R.id.venue_head);
        TextView city_head = (TextView) listViewItem.findViewById(R.id.city_head);

        TextView read_name = (TextView) listViewItem.findViewById(R.id.read_name);
        TextView read_info = (TextView) listViewItem.findViewById(R.id.read_info);
        TextView read_date = (TextView) listViewItem.findViewById(R.id.read_date);
        TextView read_venue = (TextView) listViewItem.findViewById(R.id.read_venue);
        TextView read_city = (TextView) listViewItem.findViewById(R.id.read_city);

        name_head.setText("Name:");
        info_head.setText("Info:");
        date_head.setText("Date:");
        venue_head.setText("Venue:");
        city_head.setText("City:");

       // textViewId.setText(data.get(position).getId());
        read_name.setText(data.get(position).getName());
        read_info.setText(data.get(position).getInfo());
        read_date.setText(data.get(position).getDate());
        read_venue.setText(data.get(position).getVenue());
        read_city.setText(data.get(position).getCity());


        return  listViewItem;
    }
}
