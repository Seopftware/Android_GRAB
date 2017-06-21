package com.example.msi.grab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SettingsNoticeActivity extends AppCompatActivity {

    TextView tvNotice1;
    TextView tvNotice2;
    TextView tvNotice3;
    
// // TODO: 2017-04-16  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notice);

        tvNotice1= (TextView) findViewById(R.id.tvNotice1);
        tvNotice2= (TextView) findViewById(R.id.tvNotice2);
        tvNotice3= (TextView) findViewById(R.id.tvNotice3);

        String notice1=tvNotice1.getText().toString();
        String notice2=tvNotice2.getText().toString();
        String notice3=tvNotice3.getText().toString();


        SharedPreferences noticePref=getSharedPreferences("NOTICE", MODE_PRIVATE);
        SharedPreferences.Editor editor=noticePref.edit();
        editor.putString("tvNotice1", notice1);
        editor.putString("tvNotice2", notice2);
        editor.putString("tvNotice3", notice3);
        editor.commit();
        System.out.println("String notice1: "+notice1+"tvNotice : "+tvNotice1);
        System.out.println("String notice2: "+notice2+"tvNotice2 : "+tvNotice2);
        System.out.println("String notice3: "+notice3+"tvNotice3 : "+tvNotice3);


    }

    public void onClickedBack(View v) {
        finish();
    }
}
