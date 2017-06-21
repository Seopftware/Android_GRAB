package com.example.msi.grab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HomeActivity_Dday extends AppCompatActivity {

    private TextView tvToday;
    private Button btStartDate;
    private TextView tvStartDate;
    private Button btDday;

    // Today
    private int tYear;
    private int tMonth;
    private int tDay;

    // Dday
    private int dYear;
    private int dMonth;
    private int dDay;

    private long d;
    private long t;
    private long r;

    private int resultNumber = 0;
    static final int DATE_DIALOG_ID = 0;

    ImageView fingerImage;
    Animation rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__dday);

        tvToday = (TextView) findViewById(R.id.tvToday);
        btStartDate = (Button) findViewById(R.id.btStartDate);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        btDday = (Button) findViewById(R.id.btDday);

        fingerImage= (ImageView) findViewById(R.id.fingerImage);
        rotateAnim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate2);
        fingerImage.setAnimation(rotateAnim);
        rotateAnim.start();

        btStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        Calendar calendar = Calendar.getInstance(); // 현재 날짜를 불러옵니다.
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        SharedPreferences preferences=getSharedPreferences("startday", MODE_PRIVATE);

        Calendar dCalendar = Calendar.getInstance();
        dYear=preferences.getInt("dYear", tYear);
        dMonth=preferences.getInt("dMonth",tMonth);
        dDay=preferences.getInt("dDay", tDay);

        dCalendar.set(dYear, dMonth, dDay);

        t = calendar.getTimeInMillis(); // 오늘 날짜를 밀리타임으로 바꿈
        d = dCalendar.getTimeInMillis(); // 디데이 날짜를 밀리타임으로 바꿈
        r = (d - t) / (24 * 60 * 60 * 1000); // 디데이 날짜에서 온르 날짜를 뺀 값을 '일' 단위로 바꿈

        resultNumber = (int) r + 1; // D-day 이므로 하루 더해줌
        updateDisplay();
    } // onCreate end

    private void updateDisplay() {


        tvToday.setText(String.format("%d년 %d월 %d일", tYear, tMonth + 1, tDay));
        tvStartDate.setText(String.format("%d년 %d월 %d일", dYear, dMonth + 1, dDay));

        // 디데이 날짜가 오늘날짜보다 뒤에 오면 '-' , 앞에 오면 '+'를 붙인다.
        if (resultNumber >= 0) {
            btDday.setText(String.format("-%d days", resultNumber));
        } else {
            int absR = Math.abs(resultNumber);
            btDday.setText(String.format("+%d days", absR));
        }
    }

    DatePickerDialog.OnDateSetListener dDateSetListner = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dYear = year;
            dMonth = month;
            dDay = dayOfMonth;

            SharedPreference5s preferences=getSharedPreferences("startday", MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();

            editor.putInt("dYear", dYear);
            editor.putInt("dMonth", dMonth);
            editor.putInt("dDay", dDay);
            editor.commit();

            final Calendar dCalendar = Calendar.getInstance();
            dCalendar.set(dYear, dMonth, dDay);

            d = dCalendar.getTimeInMillis();
            r = (d - t) / (24 * 60 * 60 * 1000);
            resultNumber = (int) r;
            updateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DATE_DIALOG_ID) {
            return new DatePickerDialog(this, dDateSetListner, tYear, tMonth, tDay);
        }
        return null;
    }

    public void onCliclkedDdaySave(View v) {
        SharedPreferences pref=getSharedPreferences("D_day", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        // 디데이 날짜가 오늘날짜보다 뒤에 오면 '-' , 앞에 오면 '+'를 붙인다.
        if (resultNumber >= 0) {
            btDday.setText(String.format("-%d days", resultNumber));
            Log.i("HomeActivity_Dday", "resultNumber : " +resultNumber);
            editor.putString("Dday", "-"+resultNumber+" days");
            editor.commit();

        } else {
            int absR = Math.abs(resultNumber);
            btDday.setText(String.format("+%d days", absR));
            editor.putString("Dday", absR+" days");
            editor.commit();
        }

        Toast.makeText(getApplicationContext(), "Love days 설정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);



    }

    protected void onCliclkedDdayBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}


/*
    public void caldate(int myear, int mmonth, int mday) {

        try {
            Calendar today = Calendar.getInstance();
            Calendar dday = Calendar.getInstance();

            dday.set(myear, mmonth, mday); // D-day날짜 입력

            day = today.getTimeInMillis() / 86400000;
            tday = today.getTimeInMillis() / 86400000;
            count = tday - day; // 오늘 날짜에서 D-day 날짜를 빼주게 됨.

            Log.d("Dday", "day : " + day);
            Log.d("Dday", "tday : " + tday);
            Log.d("Dday", "count(tday-day) : " + count);

            ddaycount=(int) count + 1; /// 날짜는 하루 + 시켜줘야 함
            // 각각 날의 시간 값을 얻어온 다음
            // 1일의 값 (8640000 = 24시간 * 60분 * 60초 * 1000 (1초값))

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
