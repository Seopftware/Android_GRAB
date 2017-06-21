package com.example.msi.grab;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class IntroActivity extends Activity {

    TextView textView;
    TextView textView2;
    TextView grabtext;
    ProgressBar progress;
    BackgroundTask task;
    int value;
    ImageView ivHeart;
    Animation scaleAnim, grabAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // xml과 java 소스 연결

        textView=(TextView) findViewById(R.id.textViewTitle);
        textView2= (TextView) findViewById(R.id.textView2);
        progress= (ProgressBar) findViewById(R.id.progressBar);

        ivHeart= (ImageView) findViewById(R.id.ivHeart);
        scaleAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
        ivHeart.setAnimation(scaleAnim);

        grabtext= (TextView) findViewById(R.id.grabtext);
        grabAnim=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.intro_grab);
        grabtext.setAnimation(grabAnim);


        task=new BackgroundTask();
        task.execute(100);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        Toast.makeText(getApplicationContext(), "onWindowFocusChanged : "+hasFocus, Toast.LENGTH_SHORT).show();

        if(hasFocus) {
            scaleAnim.start();
            grabAnim.start();
        } else {
            scaleAnim.reset();
            grabAnim.reset();

        }

    }

    /*
        새로운 Task 객체를 정의
         */
    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        // 초기화 단계에서 사용.
        // 값을 저장하기 위해 메인 액티비티에 정의한 value 변수의 값을 0ㅇ으로 초기화하고 프로그레스바의 값도 0으로 만들어줌

        protected void onPreExecute() {
            value=0;
            progress.setProgress(value);

        }

        // 주 작업을 실행하는데 사용.
        // while 구문을 이용해 value의 값을 하나씩 증가시키도록 함.
        protected Integer doInBackground(Integer... values) {
            while(isCancelled()==false) {
                value++;
                if (value>=100) {
                    break; // 100번 이상 반복시 반복문을 빠져나감
                } else {
                    publishProgress(value); // 중간 진행 상태를 UI에 업데이트하도록 만들기 위한 메소드 호출
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return value;
        }

        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0].intValue());
            textView.setText("Current Loading...  "+values[0].toString()+"%");

            if(values[0].intValue()==1) {
                textView2.setText("GRAB이 로딩을 시작합니다!!");
            } else if(values[0].intValue()==25) {
                textView2.setText("사랑하는 사람과의 잊고 싶지 않은 소중한 추억들..");
            } else if(values[0].intValue()==50) {
                textView2.setText("어떻게 하면 모두 놓치지 않을 수 있을까요?");
            } else if(values[0].intValue()==75) {
                textView2.setText("GRAB과 함께 소중한 추억을 GRAB하라!");
            } else if(values[0].intValue()==99) {
                textView2.setText("로딩이 완료 되었습니다.");
            }
        }

        protected void onPostExecute(Integer result) {
            progress.setProgress(0);
            textView.setText("Finished");
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

}


/*
package com.example.msi.grab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // xml과 java 소스 연결
    }

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // 3초 뒤에 다음화면(LogiActivity)으로 넘어가기 위해 Handler 사용
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    protected void onResume() {
        super.onResume();
        // 다시 화면에 들어왔을 때 예약 걸어주기
        handler.postDelayed(runnable, 1000); // 3초 뒤에 Runnable 객체 수행
    }

    protected void onPause() {
        super.onPause();
        // 화면을 벗어나면, handler에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(runnable); // 예약취소
    }
}

 */
