package com.example.msi.grab;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class HomeRecommandedMenu extends AppCompatActivity {

    ImageView swingImage, waterImage, waterImage2, skyImage, fingerImage;
    Animation shakeAnimation, dropAnimation, flowAnimation, rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recommanded_menu);

        // swingImage에 애니메이션 객체 설정
        swingImage= (ImageView) findViewById(R.id.swingImage);
        shakeAnimation= AnimationUtils.loadAnimation(this, R.anim.androboy_shake);
        swingImage.setAnimation(shakeAnimation);

        // waterImage에 애니메이션 객체 설정
        waterImage= (ImageView) findViewById(R.id.waterImage);
        dropAnimation=AnimationUtils.loadAnimation(this, R.anim.andrboy_drop);
        waterImage.setAnimation(dropAnimation);

        waterImage2= (ImageView) findViewById(R.id.waterImage2);
        dropAnimation=AnimationUtils.loadAnimation(this, R.anim.andrboy_drop1);
        waterImage2.setAnimation(dropAnimation);

        // sky 이미지에 애니메이션 객체 설정
        skyImage= (ImageView) findViewById(R.id.skyImage);
        flowAnimation=AnimationUtils.loadAnimation(this, R.anim.androboy_flow);
        skyImage.setAnimation(flowAnimation);

//        fingerImage= (ImageView) findViewById(R.id.fingerImage);
//        rotateAnimation=AnimationUtils.loadAnimation(this, R.anim.translate);
//        fingerImage.setAnimation(rotateAnimation);


        Resources res=getResources();
        Bitmap bitmap= BitmapFactory.decodeResource(res, R.drawable.sky_background);

        int bitmapWidth=bitmap.getWidth();
        int bitmapHeight=bitmap.getHeight();

        ViewGroup.LayoutParams params=skyImage.getLayoutParams();
        params.width=bitmapWidth;
        params.height=bitmapHeight;

        skyImage.setImageBitmap(bitmap);
        flowAnimation.setAnimationListener(new AnimationAdapter());

    }

    public void onClickedBack(View v) {
        finish();
    }

    public void onClickedRecommandedplace(View v) {
        Intent intent=new Intent(getApplicationContext(), HomeRecommendedplaceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

    }

    private class AnimationAdapter implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        Toast.makeText(getApplicationContext(), "onWindowFocusChanged : "+hasFocus, Toast.LENGTH_SHORT).show();

        if(hasFocus) {
            shakeAnimation.start();
            dropAnimation.start();
            flowAnimation.start();
        } else {
            shakeAnimation.reset();
            dropAnimation.reset();
            flowAnimation.reset();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}
