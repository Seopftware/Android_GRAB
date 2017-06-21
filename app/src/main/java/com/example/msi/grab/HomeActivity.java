package com.example.msi.grab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ImageButton ibtHeart;
    int i = 0;

    TextView tvMe;

    public static final String NickNamePref = "NickNamePrefs";
    public static final String NickNameMe = "MyNickNameKey";
    private static final int PICK_IMAGE = 100;
    private static final int PROFILE_IMAGE = 555;

    Uri imageUri;


    ImageView imageView, imageViewBoy;
    ArrayList<Drawable> drawbleList = new ArrayList<Drawable>();
    Handler handler = new Handler();
    Button btnRecommendedplace;
    Animation today_alpha; // 버튼 반짝거림 애니메이션
    private BackPressCloseHandler backPressCloseHandler;

    TextView tvDday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 오늘 뭐 하지? 반짝 거림
        btnRecommendedplace= (Button) findViewById(R.id.btnRecommendedplace);
        today_alpha= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.today_alpha);
        btnRecommendedplace.setAnimation(today_alpha);
        today_alpha.start();


        // D-day 기능 설정
        tvDday = (TextView) findViewById(R.id.tvDday);
        tvDday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity_Dday.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });

        SharedPreferences prefer=getSharedPreferences("D_day", MODE_PRIVATE);
        tvDday.setText(prefer.getString("Dday", "Love days 를 설정하세요."));

        // 종료 함수 객체화
        backPressCloseHandler = new BackPressCloseHandler(this);

        ibtHeart = (ImageButton) findViewById(R.id.ibtHeart);
        tvMe = (TextView) findViewById(R.id.tvMe);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items=new CharSequence[] {"Choose from Gallery"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(HomeActivity.this);
                dialog.setTitle("MENU");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent galleryIntent=new Intent(Intent.ACTION_PICK);

                        // where do we want to find the data?
                        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        String pictureDiretoryPath=pictureDirectory.getPath();

                        // finally, get a URI representation
                        Uri data=Uri.parse(pictureDiretoryPath);

                        // set the data and type. Get all image types.
                        galleryIntent.setDataAndType(data, "image/*");

                        // we will invoke this activity, and get something back from it.
                        startActivityForResult(galleryIntent, PICK_IMAGE);
                    }
                });
                dialog.show();
                return false;
            }

        });

        // 프로필 화면 사진 바꾸기
        imageViewBoy= (ImageView) findViewById(R.id.imageViewBoy);
        imageViewBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items=new CharSequence[] {"Choose from Gallery"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(HomeActivity.this);
                dialog.setTitle("MENU");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent galleryIntent=new Intent(Intent.ACTION_PICK);

                        // where do we want to find the data?
                        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        String pictureDiretoryPath=pictureDirectory.getPath();

                        // finally, get a URI representation
                        Uri data=Uri.parse(pictureDiretoryPath);

                        // set the data and type. Get all image types.
                        galleryIntent.setDataAndType(data, "image/*");

                        // we will invoke this activity, and get something back from it.
                        startActivityForResult(galleryIntent, PROFILE_IMAGE);
                    }
                });
                dialog.show();
            }
        });

        // TextView에 값을 저장시킴
        SharedPreferences pref = getSharedPreferences(NickNamePref, MODE_PRIVATE);
        tvMe.setText(pref.getString(NickNameMe, "Me"));

        //======================================================================================
        //닉네임 변경
        tvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText 값을 입력받는 다이얼로그창 구현
                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setTitle("애칭 입력창");
                dialog.setMessage("당신의 애칭은 무엇인가요?\n한 글자 이상 입력하세요.");

                final EditText etDialogMe = new EditText(HomeActivity.this);
                dialog.setView(etDialogMe);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String inputDialogMe = etDialogMe.getText().toString();
                        SharedPreferences pref = getSharedPreferences(NickNamePref, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(NickNameMe, inputDialogMe);

                        if (!inputDialogMe.equals("")) {
                            editor.commit();

                        } else if (inputDialogMe.equals("")) {
                            Toast.makeText(HomeActivity.this, "애칭은 한 글자 이상 입력하세요~^^", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String outputDialogMe = pref.getString(NickNameMe, "Me");
                        tvMe.setText(outputDialogMe);

                    }
                });
                dialog.show();
            }
        });

    }

    public void onClickedHome(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 만들어진 액티비티가 있으면 그걸 사용하고 아니면 새로 만들어라
        startActivity(intent);

    }

    public void onClickedStory(View v) {
        Intent intent = new Intent(getApplicationContext(), StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_out_left);

    }

    public void onClickedCalender(View v) {
        Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_out_left);


    }

    public void onClickedSettings(View v) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_out_left);

    }

    public void onClickedRecommendedplace(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeRecommandedMenu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    //======================================================================================
    //클릭할 때마다 현재 사랑 상태를 표현
    public void onClickedHeart(View v) {
        i = 0 + i;

        if (i == 0) {
            ibtHeart.setImageResource(R.drawable.hearts);
            Toast.makeText(this, "열렬한 사랑중이에요~!", Toast.LENGTH_SHORT).show();
            i++;
        } else if (i == 1) {
            ibtHeart.setImageResource(R.drawable.hearts2);
            Toast.makeText(this, "놀러가고 싶어요~!", Toast.LENGTH_SHORT).show();
            i++;
        } else if (i == 2) {
            ibtHeart.setImageResource(R.drawable.hearts3);
            Toast.makeText(this, "휴식이 필요해요~!", Toast.LENGTH_SHORT).show();
            i++;
        } else if (i == 3) {
            ibtHeart.setImageResource(R.drawable.hearts4);
            Toast.makeText(this, "싸우지 맙시다.", Toast.LENGTH_SHORT).show();
            i = 0;
        }
    }
    //======================================================================================

    //======================================================================================

    public void startAnimation() {
        Resources res = getResources();

        drawbleList.add(res.getDrawable(R.drawable.pic1));
        drawbleList.add(res.getDrawable(R.drawable.pic3));
        drawbleList.add(res.getDrawable(R.drawable.pic4));

        AnimThread thread = new AnimThread();
        thread.start();
    }

    class AnimThread extends Thread {
        public void run() {
            int index = 0;
            for (int i = 0; i < 100; i++) { // while 문 사용해서 무한반복으로 바꿀 수 있을 듯.
                final Drawable drawable = drawbleList.get(index);
                index += 1;
                if (index > 2) {
                    index = 0;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 처음 백버튼을 누른 시간(start)과 두번째 백버튼을 누른 시간(end)의 차를 계산한다.
    // m_isPressedBackButton를 플래그 변수로 사용하여 적절한 로직이 구현되도록 유도한다.
    // 하드웨어 백버튼

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 requestCode, resultCode, data 확인
        Log.d("HomeActivity", "requestCode : " + requestCode);
        Log.d("HomeActivity", "resultCode : " + resultCode);
        Log.d("HomeActivity", "data : " + data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Toast.makeText(HomeActivity.this, "사진 변경이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            imageUri = data.getData();

            SharedPreferences prefer=getSharedPreferences("homeImage", MODE_PRIVATE);
            SharedPreferences.Editor editorsimage=prefer.edit();
            editorsimage.putString("image", String.valueOf(imageUri));
            Log.i("HomeActivity", "imageUri :" +imageUri);
            editorsimage.commit();

        }

        if (resultCode == RESULT_OK && requestCode == PROFILE_IMAGE) {
            Toast.makeText(HomeActivity.this, "사진 변경이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            imageUri = data.getData();

            SharedPreferences prefer=getSharedPreferences("profileImage", MODE_PRIVATE);
            SharedPreferences.Editor proeditorimage=prefer.edit();
            proeditorimage.putString("image", String.valueOf(imageUri));
            Log.i("HomeActivity", "Profile imageUri :" +imageUri);
            proeditorimage.commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefers=getSharedPreferences("homeImage", MODE_PRIVATE);
        String homeimage= prefers.getString("image", "");
        Uri homeimageUri= Uri.parse(homeimage);
        imageView.setImageURI(homeimageUri);

        SharedPreferences prefer=getSharedPreferences("profileImage", MODE_PRIVATE);
        String profileimage=prefer.getString("image", "");
        Uri profileimageUri=Uri.parse(profileimage);
        imageViewBoy.setImageURI(profileimageUri);

    }

}


/*
//        tvLover.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //EditText 값을 입력받는 다이얼로그창 구현
//                AlertDialog.Builder dialog=new AlertDialog.Builder(HomeActivity.this);
//                dialog.setTitle("애칭 입력창");
//                dialog.setMessage("상대방의 애칭은 무엇인가요?\n한 글자 이상 입력하세요.");
//
//                //다이얼로그 창에 EditText 창 구현
//                final EditText etDialogLover=new EditText(HomeActivity.this);
//                dialog.setView(etDialogLover);
//                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String inputDialogLover=etDialogLover.getText().toString();
//                        SharedPreferences pref=getSharedPreferences(NickNamePref, MODE_PRIVATE);
//                        SharedPreferences.Editor editor=pref.edit();
//                        editor.putString(NickNameLover, inputDialogLover);
//
//                        if(!inputDialogLover.equals("")) {
//                            editor.commit();
//                        } else if(inputDialogLover.equals("")) {
//                            Toast.makeText(HomeActivity.this, "애칭은 한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        tvLover.setText(pref.getString(NickNameLover, "Lover"));
//                    }
//                });
//
//                dialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                dialog.show();
//            }
//        });
 */