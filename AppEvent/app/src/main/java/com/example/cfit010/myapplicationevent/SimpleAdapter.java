package com.example.cfit010.myapplicationevent;

/**
 * Created by cfit004 on 25/7/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SimpleAdapter extends ArrayAdapter<Events> {
    private List<Events> itemList;
    private Context context;

    public SimpleAdapter(List<Events> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public Events getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        Events c = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.name);
        TextView id = (TextView) v.findViewById(R.id.id1);
        text.setText(c.getName());
        id.setText(c.getId());

        return v;

    }

    public List<Events> getItemList() {
        return itemList;
    }

    public void setItemList(List<Events> itemList) {
        this.itemList = itemList;
    }


}