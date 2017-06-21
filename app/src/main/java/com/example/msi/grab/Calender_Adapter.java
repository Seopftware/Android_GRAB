package com.example.msi.grab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MSI on 2017-04-24.
 */

public class Calender_Adapter extends BaseAdapter {

    private ArrayList<Calender_Item> calenderItemList=new ArrayList<Calender_Item>();

    public Calender_Adapter() {

    }

    @Override
    public int getCount() {
        return calenderItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return calenderItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final Context context=parent.getContext();

        if(convertView==null) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.calender_item, parent, false);
        }

        TextView calenderTitle= (TextView) convertView.findViewById(R.id.textViewTitle);
        ImageView calenderImage= (ImageView) convertView.findViewById(R.id.imageView);
        TextView calenderLocation= (TextView) convertView.findViewById(R.id.textViewLocation);

        Calender_Item calenderItem=calenderItemList.get(position);

        calenderTitle.setText(calenderItem.getCalenderTitle());
        calenderImage.setImageResource(calenderItem.getCalenderImage());
        calenderLocation.setText(calenderItem.getCalenderLocation());

        return convertView;
    }

    public void addCalenderItem(String title, int image, String location, String notes) {
        Calender_Item item = new Calender_Item();

        item.setCalenderTitle(title);
        item.setCalenderIamge(image);
        item.setCalenderLocation(location);
        item.setCalenderNotes(notes);

        calenderItemList.add(item);

    }

    public void clearAllItem() {

        calenderItemList.clear();
    }

    public void removeItem(int position) {
        calenderItemList.remove(position);
    }
}
