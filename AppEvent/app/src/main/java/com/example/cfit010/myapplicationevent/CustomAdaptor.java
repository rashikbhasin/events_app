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
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewInfo = (TextView) listViewItem.findViewById(R.id.textViewInfo);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewVenue = (TextView) listViewItem.findViewById(R.id.textViewVenue);
        TextView textViewCity = (TextView) listViewItem.findViewById(R.id.textViewCity);

       // textViewId.setText(data.get(position).getId());
        textViewName.setText("EVENT NAME  : "+data.get(position).getName());
        textViewInfo.setText("EVENT INFO     : "+data.get(position).getInfo());
        textViewDate.setText("EVENT DATE    : "+data.get(position).getDate());
        textViewVenue.setText("EVENT VENUE : "+data.get(position).getVenue());
        textViewCity.setText("EVENT CITY      : "+data.get(position).getCity());


        return  listViewItem;
    }
}
