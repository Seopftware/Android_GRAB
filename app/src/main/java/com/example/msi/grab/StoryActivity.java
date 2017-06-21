package com.example.msi.grab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

import static com.example.msi.grab.ListViewAdapter.ListViewItemList;

public class StoryActivity extends AppCompatActivity {

    // ListView 적용을 위한 멤버변수 선언
    ListViewAdapter adapter;
    ListView listview;
    int itemposition;

    String title;
    String subtitle;
    String subject;
    String key;

    int getCheckedItemCount;

    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        backPressCloseHandler = new BackPressCloseHandler(this);
        //=================================================================
        // 리스트뷰
        // adapter 생성
        // ListView 참조 및 Adapter 달기
        adapter = new ListViewAdapter(getApplicationContext(), R.layout.listview_item, ListViewItemList);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        //=================================================================

//        Log.e("StoryActivity", "itemposition"+itemposition);

        // 한번만 클릭했을 때 (추억을 구체화시켜주는 액티비티 띄우기)
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                ListViewItem listViewItem = (ListViewItem) parent.getItemAtPosition(position);
                itemposition = position;

                Bundle extras = new Bundle();
                extras.putString("title", listViewItem.getTitle());
                extras.putString("subtitle", listViewItem.getSubTitle());
                extras.putString("subject", listViewItem.getSubject());

//                // image 보내기
//                Uri imageUri = listViewItem.getImageUri();
//                String imageString = imageUri.toString();
//                Log.i("StoryActivity", "imageString (값이 있나 없나 확인) :" + imageString);
//                extras.putString("image", imageString); // intent에 값을 담아서 보낼 때 String으로. 값을 꺼내고 난 후 이미지 변경할 때는 Uri로

                Bitmap imageBitmap=listViewItem.getImageBitmap();
                String imageString=BitMapToString(imageBitmap);
                extras.putString("image", imageString);

                // position 보내기
                extras.putInt("position", itemposition);

                Intent intent = new Intent(getApplicationContext(), StoryViewActivity.class);
                intent.putExtras(extras);
                startActivity(intent);

                // 수정화면으로 해당 postion값 보내기
                Intent sendposition = new Intent(getApplicationContext(), Story_InputEditActivity.class);
                sendposition.putExtra("position", itemposition);

            }

        });

        // 길게 클릭했을 때 (삭제하기)
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(StoryActivity.this);
                dialog.setTitle("삭제");
                dialog.setMessage("추억을 정말로 삭제하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Todo's 이 둘의 차이점은 뭘까?? Checked?
//                        ListViewItemList.remove(listview.getCheckedItemPosition());
                        ListViewItemList.remove(position);

                        Log.e("StoryActivity", "삭제 전 ListViewItemList 갯수 : " + ListViewItemList.size());

                        SharedPreferences pref = getSharedPreferences("listview", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove(String.valueOf(position));
                        editor.commit();

                        Log.e("StoryActivity", "삭제 후 ListViewItemList 갯수 : ");
                        // 삭제가 일어난 한 칸씩 땡기고 다시 재정렬
                        for (int i = position; i < ListViewItemList.size(); i++) {
                            SharedPreferences removePref = getSharedPreferences("listview", MODE_PRIVATE);

                        }


                        adapter.notifyDataSetChanged();

                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;
            }
        });

        Log.e("StoryActivity", "itemposition" + itemposition);
        Log.e("StoryActivity", "getCheckedItemCount" + getCheckedItemCount);


    }

    public void onClickedHome(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 만들어진 액티비티가 있으면 그걸 사용하고 아니면 새로 만들어라
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

    }

    public void onClickedStory(View v) {
        Intent intent = new Intent(getApplicationContext(), StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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

    public void onClickedStoryPlus(View v) {
        Intent intent = new Intent(getApplicationContext(), StoryInputActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            title = data.getStringExtra("title");
            subtitle = data.getStringExtra("subtitle");
            subject = data.getStringExtra("subject");
        }
    }

    //====================================================================================================
    //  생명주기 적용
    //====================================================================================================
    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(this, "StoryInputActivity: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restorePreference();
        //restore,remove,edit
//        Toast.makeText(this, "StoryActiviy: onResume 호출됨 + adapter.notifyDataSetChanged() 실행됨", Toast.LENGTH_LONG).show();
    }

    public void restorePreference() {
        ListViewItemList.clear();

        Log.e("StoryActivity", "Adapter.getCount() : " + adapter.getCount());

        SharedPreferences KeyPref = getSharedPreferences("KeyArray", MODE_PRIVATE);
        String KeyList = KeyPref.getString("KeyPref", String.valueOf(0));
        Log.e("StoryActivity", "KeyList(Keypref.getString(KeyPref):" + KeyList);

        int k = Integer.parseInt(KeyList) + 1;

        Log.e("StoryActivity", "k(Integer.parseInt(KeyList) : " + k);


        for (int i = 0; i < k; i++) { // 0~리스트 뷰의 크기 만큼 반복

            Log.e("StoryActivity", "반복횟수 (Integer.parseInt(KeyList): " + Integer.valueOf(KeyList));

            SharedPreferences pref = getSharedPreferences("listview", MODE_PRIVATE); // Value 값을 꺼내기 위한 SharedPreference
            String Key = String.valueOf(i); // 벨류값을 꺼내기 위한 키 값 (0~item.size())
            Log.e("StoryActivity", "Key (String.valueOf(i)): " + String.valueOf(i));
            String Value = pref.getString(Key, ""); // Key의 값에 해당하는 Value를 꺼낸다.
            String[] split = Value.split("_@#@_"); // Key값에 들어있는 벨류를 꺼내 split() 함수로 분해한다.
            Log.e("StoryActivity", "Value" + Value);

            if (key != null) ;
            {
                try {
                    String title = split[0];
                    String subtitle = split[1];
                    String subject = split[2];
                    String image = split[3]; // 여기서 꺼낸 String image를 Bitmap 형태로 다시 바꿔줘야 한다.

                    Log.e("StoryActivity", "title: " + title);
                    Log.e("StoryActivity", "subtitle: " + subtitle);
                    Log.e("StoryActivity", "subject: " + subject);
                    Log.e("StoryActivity", "image: " + image);

                    Uri imageuri = Uri.parse(image);
//                    Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inSampleSize=4;
                    Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                    Bitmap resized=Bitmap.createScaledBitmap(bm, 200,200, true);



                    adapter.addItem(title, subtitle, subject, resized);
                    adapter.notifyDataSetChanged();

                    Log.e("StoryActivity", "ListViewItemList 크기(size) (1이 증가해야함): " + ListViewItemList.size());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

//

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();

    }

    public String BitMapToString(Bitmap bitmap) {

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

}






//    //==================================================
//    //  생명주기 적용
//    //===================================================
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Toast.makeText(this, "StoryActiviy: OnStart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//
//        Toast.makeText(this, "StoryActiviy: onRestart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveState();
//
//        Toast.makeText(this, "StoryActiviy: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        restoreState();
//
//        Toast.makeText(this, "StoryActiviy: onResume 호출됨 + restoreState() 실행됨", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        Toast.makeText(this, "StoryActiviy: onStop 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Toast.makeText(this, "StoryActiviy: OnDestroy 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    // 값 저장하기
//    protected void saveState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor=pref.edit();
//        editor.putString("title", tvTitle.getText().toString());
//        editor.putString("subject", tvSubject.getText().toString());
//        editor.commit(); // 값을 저장한 후에는 반드시 불러야 하는 함수
//    }
//
//    // 값 초기화하기
//    protected void clearState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor=pref.edit();
//        editor.putString("title", tvTitle.getText().toString());
//        editor.putString("subject", tvSubject.getText().toString());
//        editor.clear();
//        editor.commit();
//    }
//
//
//
//    // 값 불러오기
//    protected void restoreState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//
//        if ((pref!=null) && (pref.contains("title")) || (pref.contains("subject")) ) {
//            String title=pref.getString("title", "");
//            tvTitle.setText(title);
//
//            String subject=pref.getString("subject", ""); // 불러올 공간 / 기본값(값이 저장되어 있지 않을 때)
//            tvSubject.setText(subject);
//
//        }
//
//    }
//  //TODO's 다이얼로그창
//    private void showMessage() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this); // 대화상자를 만들기 위한 builder 객체 생성
//        builder.setTitle("안내");
//        builder.setMessage("이전에 저장되어있는 파일이 있습니다.\n불러들이시겠습니까?");
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//
//        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Toast.makeText(getBaseContext(), "예를 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(getApplicationContext(), StoryInputActivity.class);
//                startActivityForResult(intent, 999);
//            }
//        });
//
//        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Toast.makeText(getBaseContext(), "취소를 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Toast.makeText(getBaseContext(), "아니요를 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(getApplicationContext(), StoryInputActivity.class);
//                clearState();
//                startActivityForResult(intent, 999);
//
//            }
//        });
//
//        AlertDialog dialog=builder.create();
//        dialog.show();
//
//    }
