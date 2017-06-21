package com.example.msi.grab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import static com.example.msi.grab.R.anim.today_alpha;
import static com.example.msi.grab.RegisterActivity.Email;
import static com.example.msi.grab.RegisterActivity.MyPREFERENCES;
import static com.example.msi.grab.RegisterActivity.Username;

public class SettingsActivity extends AppCompatActivity {

    TextView tvUsername;
    TextView tvEmail;
    TextView tvNotice;
    ImageButton tbtNotice;
    ImageView imageView;
    Boolean running=true; // 공지사항 시작
    ArrayList<String> noticeList=new ArrayList<String>();
    Handler handler=new Handler();
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backPressCloseHandler=new BackPressCloseHandler(this);

        tvUsername=(TextView) findViewById(R.id.tvUsername);
        tvEmail=(TextView) findViewById(R.id.tvEmail);

        tbtNotice= (ImageButton) findViewById(R.id.tbtNotice);
        Animation notice= AnimationUtils.loadAnimation(getApplicationContext(), today_alpha);
        tbtNotice.setAnimation(notice);
        notice.start();

        imageView= (ImageView) findViewById(R.id.imageView);
        SharedPreferences prefer=getSharedPreferences("profileImage", MODE_PRIVATE);
        String profileimage=prefer.getString("image", "");
        Uri profileimageUri=Uri.parse(profileimage);
        imageView.setImageURI(profileimageUri);

        SharedPreferences pref=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String email=pref.getString(Email, "input your email address");
        String username=pref.getString(Username, "input your Name");

        tvEmail.setText(email);
        tvUsername.setText(username);

        tvNotice= (TextView) findViewById(R.id.tvNotice);
        startAnimation();


        // AdView와 AdRequest 메소드를 불러들인다.
        AdView adview=(AdView) findViewById(R.id.adView); // 레이아웃에서 adView를 찾고
        AdRequest adrequest=new AdRequest.Builder().build(); // adrequest를 만들고,
        adview.loadAd(adrequest); // 이를 사용하여 adview에 광고를 로드
    }

    public void onClickedHome(View v) {
        Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 만들어진 액티비티가 있으면 그걸 사용하고 아니면 새로 만들어라
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void onClickedStory(View v) {
        Intent intent=new Intent(getApplicationContext(), StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void onClickedCalender(View v) {
        Intent intent=new Intent(getApplicationContext(), CalenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void onClickedSettings(View v) {
        Intent intent=new Intent(getApplicationContext(), SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public void onClickedNotice(View v) {
        Intent intent=new Intent(getApplicationContext(), SettingsNoticeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onClickedContact(View v) {
        Intent intent=new Intent(getApplicationContext(), SettingsContactActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public void onClickedLogout(View v) {
        logout();
    }

    public void startAnimation() {
        SharedPreferences noticePref=getSharedPreferences("NOTICE", MODE_PRIVATE);
        String notice1=noticePref.getString("tvNotice1", "");
        String notice2=noticePref.getString("tvNotice2", "");
        String notice3=noticePref.getString("tvNotice3", "");
        noticeList.add(notice1);
        noticeList.add(notice2);
        noticeList.add(notice3);

        System.out.println("notice1 :"+notice1);
        System.out.println("notice2 :"+notice2);
        System.out.println("notice3 :"+notice3);

        NoticeThread thread=new NoticeThread();
        thread.start();

    }

    public void logout() {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃")
                .setMessage("정말로 로그아웃 하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences autosettings=getSharedPreferences("settings", MODE_PRIVATE);
                        SharedPreferences.Editor autoeditor=autosettings.edit();
                        autoeditor.putBoolean("Auto_Login_enabled", false);
                        autoeditor.commit();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    class NoticeThread extends Thread {
        public void run() {
            int index=0;

            while(running) {
                if(index>2) {
                    index=0;
                }
                final String string=noticeList.get(index);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvNotice.setText(string);
                    }
                });
                try {
                    Thread.sleep(3000);
                    index+=1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 생명주기
    protected void onResume() {
        super.onResume();
        running=true;

        NoticeThread thread= new NoticeThread();
        thread.start();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


}
