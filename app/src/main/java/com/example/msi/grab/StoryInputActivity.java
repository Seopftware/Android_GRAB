package com.example.msi.grab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import static com.example.msi.grab.ListViewAdapter.ListViewItemList;

public class StoryInputActivity extends AppCompatActivity {

    //    ImageView image
    EditText etTitle;
    EditText etSubTitle;
    EditText etSubject;

    // 카메라 앨범을 불러들이기 위한 맴버변수
    ImageView imageView;
    ImageButton ibtPicPlus;

    private static final int PICK_IMAGE=100;

    Bitmap imagepic;
    Bitmap resized;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_input);

        imageView=(ImageView) findViewById(R.id.imageView);
        ibtPicPlus=(ImageButton) findViewById(R.id.ibtPicPlus);
        etTitle=(EditText) findViewById(R.id.etTitle);
        etSubTitle=(EditText) findViewById(R.id.etSubtitle);
        etSubject=(EditText) findViewById(R.id.etSubject);


        System.out.println("스토리 입력 화면 입니다.");
        System.out.println("ListViewItemList.size()의 값을 ArrayList인 KeyList의 값에 담습니다.");

    }

    public void onClickedBack(View v) {
//        showMessage();            // 다이얼로그창 띄우기 (내용을 저장하지 않았습니다.\n돌아가시겠습니까?)
        finish();
    }

    public void onClickedSave(View v) {
        // Key 저장
        String Key=String.valueOf(ListViewItemList.size());
        System.out.println("Key:(String.valueOf(ListViewItemList.size()): "+Key+""); // 액티비티가 추가될 떄 마다 key의 값이 1씩 증가해야함.

        // 키값 영구 저장을 위한 SharedPreference
        SharedPreferences KeyPref=getSharedPreferences("KeyArray", MODE_PRIVATE);
        SharedPreferences.Editor KeyEditor=KeyPref.edit();
        KeyEditor.putString("KeyPref", Key);
        KeyEditor.commit();

        // Value 저장
        String title=etTitle.getText().toString();
        String subtitle=etSubTitle.getText().toString();
        String subject=etSubject.getText().toString();

        Log.i("StoryInputActivity", "String 변경전 imagepic값 : "+imageUri);
        String image=imageUri.toString();
        Log.i("StoryInputActivity", "String 변경후 imagepic값 : "+image);

        String Value=title+"_@#@_"+subtitle+"_@#@_"+subject+"_@#@_"+image;

        // 벨류값 영구 저장을 위한 SharedPreference
        SharedPreferences pref=getSharedPreferences("listview", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        editor.putString(Key,Value); // Key와 Value의 값이 제대로 담기고 있는지 확인 필요
        editor.commit();
        System.out.println("InputActivity Key: "+Key);
        System.out.println("InputActivity Value: "+Value);

        finish();

        }


    //================================================================================================
    // 갤러리 불러 들이기1
    protected void onClickedPicPlus(View v) {
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

    @Override
    protected void onActivityResult(int resultCode, int requestCode, Intent data) {
        super.onActivityResult(resultCode, requestCode, data);

        if(resultCode==PICK_IMAGE && requestCode==RESULT_OK) {

            imageUri=data.getData();
            imageView.setImageURI(imageUri);



//            BitmapFactory.Options bounds = new BitmapFactory.Options();
//            bounds.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(file, bounds);
//
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            Bitmap bm = BitmapFactory.decodeFile(file, opts);
//            ExifInterface exif = new ExifInterface(file);
//            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//            int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
//
//            int rotationAngle = 0;
//            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
//            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
//            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
//
//            Matrix matrix = new Matrix();
//            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);


//
//            // we are getting input stream, based on the URI of the image.
//            try {
//
//                InputStream inputStream=getContentResolver().openInputStream(imageUri);
//                // get a bitmap from the stream.
//                imagepic= BitmapFactory.decodeStream(inputStream);
//                // show the image to the user
//                resized=Bitmap.createScaledBitmap(imagepic, 120, 120, true); // 다운 사이징
//
//                imageView.setImageBitmap(resized);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                // show a message to the user indictating that the image is unavaiable.
//                Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
//            }
        }
    }



}

//
//    BitmapFactory.Options bounds = new BitmapFactory.Options();
//        bounds.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(file, bounds);
//
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                Bitmap bm = BitmapFactory.decodeFile(file, opts);
//                ExifInterface exif = new ExifInterface(file);
//                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//                int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
//
//                int rotationAngle = 0;
//                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
//                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
//                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
//
//                Matrix matrix = new Matrix();
//                matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);


    //================================================================================================


//
//    //==================================================
//    //  생명주기 적용
//    //===================================================
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Toast.makeText(this, "StoryInputActivity: OnStart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//
//        Toast.makeText(this, "StoryInputActivity: onRestart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveState();
//
//        Toast.makeText(this, "StoryInputActivity: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        restoreState();
//
//        Toast.makeText(this, "StoryInputActivity: onResume 호출됨 + restoreState() 실행됨", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        Toast.makeText(this, "StoryInputActivity: onStop 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Toast.makeText(this, "StoryInputActivity: OnDestroy 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    // 값 저장하기
//    protected void saveState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor=pref.edit();
//        editor.putString("title", etTitle.getText().toString());
//        editor.putString("subject", etSubject.getText().toString());
//        editor.commit(); // 값을 저장한 후에는 반드시 불러야 하는 함수
//    }
//
//
//    // 값 불러오기
//    protected void restoreState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//
//        if ((pref!=null) && (pref.contains("title")) || (pref.contains("subject")) ) {
//            String title=pref.getString("title", "");
//            etTitle.setText(title);
//
//            String subject=pref.getString("subject", ""); // 불러올 공간 / 기본값(값이 저장되어 있지 않을 때)
//            etSubject.setText(subject);
//
//        }
//
//    }



/*
    public void onBackPressed() {

    }

    private void showMessage() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this); // 대화상자를 만들기 위한 builder 객체 생성
        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String message = "예 버튼을 눌렸습니다.";
                        textView.setText(message);
                    }
                });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String message="취소 버튼을 눌렸습니다.";
                    textView.setText(message);

                }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String message="아니오 버튼을 눌렸습니다.";
                textView.setText(message);
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }
 */