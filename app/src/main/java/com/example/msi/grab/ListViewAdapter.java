package com.example.msi.grab;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 Adapter를 새롭게 구현할 때 안드로이드 SDK 에서 제공하는 Adapter 중 어떤 Adapter 클래스를 부모로 사용할 지 결정해야함
 구현하고자 하는 Adapter의 기능에 적합한 것을 선택하면 되는데 보통 ArrayAdapter 또는 BaseAdapter를 많이 사용
 */

public class ListViewAdapter  extends BaseAdapter {

    Context context;
    int layout;
    static ArrayList<ListViewItem> ListViewItemList=new ArrayList<>(); // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    LayoutInflater inf;
    private int lastPosition=-1;

    // ListViewAdapter의 생성자

    public ListViewAdapter(Context context, int layout, ArrayList<ListViewItem> ListViewItemList) {
        this.context=context;
        this.layout=layout;
        this.ListViewItemList=ListViewItemList;
        this.inf=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return ListViewItemList.size();
    }



    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
            return ListViewItemList.get(position);
        }


    // 지정한 위치(postion)에 있는 데이터와 관계된 아이템의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView=inf.inflate(R.layout.listview_item, parent, false); // listview_item을 View 객체로 만듬.
        }

        // 화면에 표시될 View(Layout이 inflate된)로 부터 위젯에 대한 참조 획득
        ImageView imageImageView=(ImageView) convertView.findViewById(R.id.imageView);
        TextView titleTextView=(TextView) convertView.findViewById(R.id.tvTitle);
        TextView subtitleTextView=(TextView) convertView.findViewById(R.id.tvSubTitle);

        // Data set(ListViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem=ListViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        imageImageView.setImageBitmap(listViewItem.getImageBitmap()); // 작동
        titleTextView.setText(listViewItem.getTitle());
        subtitleTextView.setText(listViewItem.getSubTitle());

        Animation animation= AnimationUtils.loadAnimation(this.context, (position>this.lastPosition)?R.anim.up_from_bottom:R.anim.down_from_top);
        convertView.startAnimation(animation);
        this.lastPosition=position;

        return convertView;
    }

//    public void addItem(String title, String subtitle, String subject, Uri image) {
//        ListViewItem item = new ListViewItem(title, subtitle, subject, image);
//
////        item.setTitle(title);
////        item.setSubtitle(subtitle);
////        item.setSubject(subject);
//
//        ListViewItemList.add(item);
//
//    }

    public void addItem(String title, String subtitle, String subject, Bitmap image) {
        ListViewItem item = new ListViewItem(title, subtitle, subject, image);

//        item.setTitle(title);
//        item.setSubtitle(subtitle);
//        item.setSubject(subject);

        ListViewItemList.add(item);

    }

}


/*
        // 아이템 데이터 추가를 위한 함수.
    public void addItem(Drawable image, String title, String subtitle) {

        ListViewItem item=new ListViewItem();

        item.setImage(image);
        item.setTitle(title);
        item.setSubTitle(subtitle);

        ListViewItemList.add(item);
    }



        @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "RegisterActiviy: OnStart 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "RegisterActiviy: onRestart 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        saveState();
        Toast.makeText(this, "RegisterActiviy: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        restoreState();
        Toast.makeText(this, "RegisterActiviy: onResume 호출됨 + restoreState() 실행됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "RegisterActiviy: onStop 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "RegisterActiviy: OnDestroy 호출됨", Toast.LENGTH_SHORT).show();
    }

    // 값 저장하기
    protected void saveState() {
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("email", etEmail.getText().toString());
        editor.putString("username", etUsername.getText().toString());
        editor.commit(); // 값을 저장한 후에는 반드시 불러야 하는 함수
    }


    // 값 불러오기
    protected void restoreState() {
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);

        if ((pref!=null) && (pref.contains("email")) || (pref.contains("username")) || (pref.contains("male"))) {
            String email=pref.getString("email", "");
            etEmail.setText(email);

            String username=pref.getString("username", ""); // 불러올 공간 / 기본값(값이 저장되어 있지 않을 때)
            etUsername.setText(username);
        }
 */