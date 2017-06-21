package com.example.msi.grab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static android.content.Intent.ACTION_DIAL;
import static android.content.Intent.ACTION_VIEW;

public class SettingsContactActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_contact);

    }

    public void onClickedBack(View v) {
        finish();
    }

    public void onClickedCall(View v) {
        Intent intent=new Intent(ACTION_DIAL, Uri.parse("tel:010-4167-5164")); // 전화문의
        startActivity(intent);
    }

    public void onClickedHomepage(View v) {
        Intent intent=new Intent(ACTION_VIEW, Uri.parse("http://www.google.com")); // 홈페이지 문의
        startActivity(intent);
    }

}


/*
        switch (v.getId()) { // 홈페이지 문의
                case R.id.web:
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")); // Uri에 맞는 화면을 띄운다.
                startActivity(intent);
                break;

            case R.id.dial: // 전화 문의
                intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-4167-5164")); // 전화를 건다
                startActivity(intent);
                break;
 */