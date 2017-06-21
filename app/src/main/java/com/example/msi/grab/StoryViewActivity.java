package com.example.msi.grab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tvTitle;
    TextView tvSubTitle;
    TextView tvSubject;

    ImageButton ibtBack;
    ImageButton ibtEdit;

    String title;
    String subtitle;
    String subject;
    int itemposition;
    String imageString;
    Uri imageUri;
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_view);

        imageView=(ImageView) findViewById(R.id.imageView);
        tvTitle=(TextView) findViewById(R.id.tvTitle);
        tvSubTitle=(TextView) findViewById(R.id.tvSubTitle);
        tvSubject=(TextView) findViewById(R.id.tvSubject);

        ibtBack=(ImageButton) findViewById(R.id.ibtBack);
        ibtEdit=(ImageButton) findViewById(R.id.ibtEdit);


        itemposition=getIntent().getIntExtra("position", 0);

        title=getIntent().getStringExtra("title");
        tvTitle.setText(title);

        subtitle=getIntent().getStringExtra("subtitle");
        tvSubTitle.setText(subtitle);

        subject=getIntent().getStringExtra("subject");
        tvSubject.setText(subject);

        imageString=getIntent().getStringExtra("image");
        Log.i("StoryViewActiviy", "imageString=getIntent().getStringExtra(\"image\") : "+getIntent().getStringExtra("image"));
        imageBitmap=StringToBitMap(imageString);
        Log.i("StoryViewActiviy", "imageUri : "+imageUri);
        imageView.setImageBitmap(imageBitmap);

    }

    public void onClickedBack(View v) {
        finish();
    }

    public void onClickedEdit(View v) {

        Bundle extras=new Bundle();
        extras.putString("title", title);
        extras.putString("subtitle", subtitle);
        extras.putString("subject", subject);
        extras.putInt("position", itemposition);
        extras.putString("image", imageString);

        Intent intent = new Intent(getApplicationContext(), Story_InputEditActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
        finish();

    }

    public Bitmap StringToBitMap(String encodedString) {
        try{
            byte[] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
