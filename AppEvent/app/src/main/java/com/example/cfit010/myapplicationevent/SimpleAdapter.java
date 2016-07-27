package com.example.cfit010.myapplicationevent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
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
        String date="";
        Events c = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.name);
        TextView event_date = (TextView) v.findViewById(R.id.event_date);
        TextView event_status = (TextView) v.findViewById(R.id.event_status);
        text.setText(c.getName());

        String str=new String(c.getDate());
        String yyyy=str.substring(0, 4);
        int year=Integer.parseInt(str.substring(0, 4));
        // String mm1=str.substring(5, 7);
        int mm=Integer.parseInt(str.substring(5, 7));
        String dd=str.substring(8, 10);
        int dd1=Integer.parseInt(str.substring(8, 10));
        String[] month={"JAN","FEB","MAR","APR","MAY","JUNE","JULY","AUG","SEPT","OCT","NOV","DEC"};

        date=dd+", "+month[mm-1]+", "+yyyy;
        event_date.setText(date);
        String startDateString = c.getDate();
        int mYear,mMonth,mDay;
        Calendar c1=Calendar.getInstance();
        mYear=c1.get(Calendar.YEAR);
        mMonth=c1.get(Calendar.MONTH)+1;
        mDay=c1.get(Calendar.DAY_OF_MONTH);


        if(mYear>year)
            event_status.setText("Completed");
        else if (mYear >= year && mMonth>mm)
            event_status.setText("Completed");
        else if (mYear >= year && mMonth >= mm && mDay>dd1)
            event_status.setText("Completed");
        else if (mYear == year && mMonth == mm && mDay == dd1)
            event_status.setText("Ongoing");
        else
            event_status.setText("Upcoming");

        return v;

    }

    public List<Events> getItemList() {
        return itemList;
    }

    public void setItemList(List<Events> itemList) {
        this.itemList = itemList;
    }


}