package com.example.msi.grab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import static android.content.Intent.ACTION_VIEW;
import static com.example.msi.grab.Calender_Input.CALENDERVALUE;

public class CalenderActivity extends AppCompatActivity {

    GridView monthView; // 월별 캘린더 뷰 객체
    MonthAdapter monthViewAdapter; // 월별 캘린더 어댑터
    TextView monthText; // 월을 표시하는 텍스트 뷰
    int curYear; // 현재 년
    int curMonth; // 현재 월
    TextView tvDateShow;
    BackPressCloseHandler backPressCloseHandler;

    ImageButton bearBtn;
    Animation bearAnim;
    int index=0;

    ListView listView;
    Calender_Adapter adapter;

    String calenderKey;
    public static final int CALENDER_INPUT = 123;
    int calenderItemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        bearBtn= (ImageButton) findViewById(R.id.bearBtn);
        bearAnim= AnimationUtils.loadAnimation(this, R.anim.bear_translate);
        bearBtn.setAnimation(bearAnim);
        bearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                index = 0 + index;

                switch (index) {
                    case 0:
                        Intent intent=new Intent(ACTION_VIEW, Uri.parse("http://teamnova.co.kr/schedule.php") );
                        startActivity(intent);
                        index++;
                        break;
                    case 1:
                        Intent intent1=new Intent(ACTION_VIEW, Uri.parse("http://www.google.com") );
                        startActivity(intent1);
                        index++;
                        break;
                    case 2:
                        Intent intent2=new Intent(ACTION_VIEW, Uri.parse("http://www.naver.com") );
                        startActivity(intent2);
                        index=0;
                        break;

                }

            }
        });

        backPressCloseHandler=new BackPressCloseHandler(this);

        // 월별 캘린더 뷰 객체 참조
        monthView = (GridView) findViewById(R.id.monthView);
        monthViewAdapter = new MonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        tvDateShow= (TextView) findViewById(R.id.tvDateShow);

        // 리스너 설정
        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                adapter.clearAllItem();

                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
                String dayShow=String.valueOf(day);
                Log.d("MainActivity", "Selected :" + day);

                tvDateShow.setText("Date: 선택하신 날짜는 "+curYear+ "년 "+(curMonth+1)+"월 "+dayShow+"일 입니다.");
                calenderKey=String.valueOf(curYear)+String.valueOf((curMonth+1))+dayShow;

                Log.i("CalenderActivity", "calenderKey : "+calenderKey);
                Log.i("CalenderActivity", "curYear : "+String.valueOf(curYear));
                Log.i("CalenderActivity", "(curMonth+1) : "+String.valueOf((curMonth+1)));
                Log.i("CalenderActivity", "dayShow : "+dayShow);


                SharedPreferences prefs=getSharedPreferences(CALENDERVALUE, MODE_PRIVATE);
                String calenderEvent=prefs.getString(calenderKey, "fail");
                String[] split=calenderEvent.split("_@#@_");
                Log.e("CalenderActivity", "CalenderEvent" + calenderEvent);

                if (!calenderEvent.equals("fail")) {
                    try {
                        String calenderTitle=split[0];
                        String calenderImageStr=split[1];
                        String calenderLocation=split[2];
                        String calenderNotes=split[3];

                        Log.e("CalenderActivity", "calenderTitle" + split[0]);
                        Log.e("CalenderActivity", "calenderImage" + split[1]);
                        Log.e("CalenderActivity", "calenderLocation" + split[2]);

                        int calenderImageResource= Integer.parseInt(calenderImageStr);
                        adapter.addCalenderItem(calenderTitle, calenderImageResource, calenderLocation, calenderNotes);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if(calenderEvent.equals("fail")) {
                    adapter.clearAllItem();
                    adapter.notifyDataSetChanged();

                }

            }
        });

        monthView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence[] items=new CharSequence[] { "일정 추가하기" };
                AlertDialog.Builder dialog=new AlertDialog.Builder(CalenderActivity.this);
                dialog.setTitle("MENU");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(), Calender_Input.class);
                        intent.putExtra("CalenderKey", calenderKey);
                        startActivity(intent);
                        Log.d("CalenderActivity", "intent.putExtra(\"CalenderKey\", calenderKey) - calenderKey값 : "+calenderKey);
                    }
                });
                dialog.show();
                return false;
            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        ImageButton monthPrevious=(ImageButton) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        ImageButton monthNext = (ImageButton) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        adapter=new Calender_Adapter();
        listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Calender_Item calenderItem=(Calender_Item) parent.getItemAtPosition(position);
//                calenderItemPos=position;
//
//                Bundle extras = new Bundle();
//                extras.putString("title", calenderItem.getCalenderTitle());
//                extras.putInt("image", calenderItem.getCalenderImage());
//                extras.putString("location", calenderItem.getCalenderLocation());
//                extras.putString("notes", calenderItem.getCalenderNotes());
//                extras.putInt("position", calenderItemPos);
//
//                Intent intent = new Intent(getApplicationContext(), Calender_View.class);
//                intent.putExtras(extras);
//                startActivity(intent);
//
//                // 수정화면으로 해당 postion값 보내기
//                Intent sendposition = new Intent(getApplicationContext(), Calender_Edit.class);
//                sendposition.putExtra("position", calenderItemPos);
//
//
//
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items=new CharSequence[] { "삭제하기" };
                AlertDialog.Builder dialog=new AlertDialog.Builder(CalenderActivity.this);
                dialog.setTitle("MENU");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        if (items[which].equals("삭제하기")) {

//                            Bundle extras = new Bundle();
//                            extras.putString("title", calenderItem.getCalenderTitle());
//                            extras.putInt("image", calenderItem.getCalenderImage());
//                            extras.putString("location", calenderItem.getCalenderLocation());
//                            extras.putString("notes", calenderItem.getCalenderNotes());
//                            extras.putInt("position", calenderItemPos);
//
//                            Intent intent = new Intent(getApplicationContext(), Calender_View.class);
//                            intent.putExtras(extras);
//                            startActivity(intent);
//
//                            // 수정화면으로 해당 postion값 보내기
//                            Intent sendposition = new Intent(getApplicationContext(), Calender_Edit.class);
//                            sendposition.putExtra("position", calenderItemPos);

                            adapter.removeItem(position);

                            SharedPreferences prefs = getSharedPreferences(CALENDERVALUE, MODE_PRIVATE);
                            SharedPreferences.Editor editors = prefs.edit();
                            editors.remove(calenderKey);
                            editors.commit();
                            adapter.notifyDataSetChanged();
                        }

//                        } else if (items[which].equals("삭제하기")) {
//
//                            adapter.removeItem(position);
//
//                            SharedPreferences prefs=getSharedPreferences(CALENDERVALUE, MODE_PRIVATE);
//                            SharedPreferences.Editor editors=prefs.edit();
//                            editors.remove(calenderKey);
//                            editors.commit();
//                            adapter.notifyDataSetChanged();
//                        }
                    }

                });
                            dialog.show();
                            return false;
                        }
                    });
    }

    private void setMonthText() {
        curYear=monthViewAdapter.getCurYear();
        curMonth=monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 "+(curMonth+1)+"월");
    }

    public void onClickedHome(View v) {
        Intent intent = new Intent(CalenderActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 만들어진 액티비티가 있으면 그걸 사용하고 아니면 새로 만들어라
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void onClickedStory(View v) {
        Intent intent = new Intent(CalenderActivity.this, StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);


    }

    public void onClickedCalender(View v) {
        Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    public void onClickedSettings(View v) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_out_left);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            bearAnim.start();
        } else {
            bearAnim.reset();
        }
    }

}


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // setResult를 통해 받아온 요청번호, 상태, 데이터 logcat 확인
//        Log.d("RESULT", requestCode + "");
//        Log.d("RESULT", resultCode + "");
//        Log.d("RESULT", data + "");
//
//        if (requestCode == CALENDER_INPUT && resultCode == RESULT_OK) {
//
//        }
//    }
