package com.example.msi.grab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StoryPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_password);
    }

    public void onClickedConfirm(View v) {
        Intent intent=new Intent(getApplicationContext(),StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public void onClickedCancel(View v) {
        finish();   // 액티비티 닫기
    }

}
