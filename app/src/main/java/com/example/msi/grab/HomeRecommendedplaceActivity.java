package com.example.msi.grab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeRecommendedplaceActivity extends AppCompatActivity {

//    ImageView imageView;
//    ImageView imageView2;
//    ImageView imageView3;
//    ImageView imageView4;
//    ImageView imageView5;
//    int imageIndex=0; // frame 레이아웃 사용할 때 이용

    ImageView imageview;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recommendedplace);

        imageview=(ImageView) findViewById(R.id.imageView);

    }

    public void onClickedNext(View v) {
        i=0+i;

        if (i==0) {
            imageview.setImageResource(R.drawable.recommended1);
            i++;
        } else if (i==1) {
            imageview.setImageResource(R.drawable.recommended2);
            i++;
        } else if (i==2) {
            imageview.setImageResource(R.drawable.recommended3);
            i++;
        } else if (i==3) {
            imageview.setImageResource(R.drawable.recommended4);
            i++;
        } else if (i==4) {
            imageview.setImageResource(R.drawable.recommended5);
            i=0;
        }
    }

    public void onClickedBack(View v) {
        finish();
    }

    public void onClickedShare(View v) {
        Toast.makeText(this, "아직 구현 중인 기능입니다...^^", Toast.LENGTH_SHORT).show();

    }




}
