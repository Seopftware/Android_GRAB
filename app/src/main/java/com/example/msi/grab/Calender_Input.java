package com.example.msi.grab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Calender_Input extends AppCompatActivity {

    EditText etTitle; // Calender에서 볼 수 있음
    ImageView imageCategory; // Calender에서 볼 수 있음
    TextView tvCategory;
    EditText etLocation; // Calender에서 볼 수 있음
    TextView tvAlert;
    TextView etNotes;

    Integer resource;

    String calenderKey;

    public static final String CALENDERKEY="Calender_Key";
    public static final String CALENDERVALUE="Calender_Value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenderinput);

        etTitle= (EditText) findViewById(R.id.etTitle);
        imageCategory= (ImageView) findViewById(R.id.imageCategory);
        tvCategory= (TextView) findViewById(R.id.tvCategory);
        etLocation= (EditText) findViewById(R.id.etLocation);
        etNotes= (TextView) findViewById(R.id.etNotes);

        calenderKey=getIntent().getStringExtra("CalenderKey");



        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items=new CharSequence[]{"(1) Date","(2) Study","(3) Travel", "(4) Other"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(Calender_Input.this);
                dialog.setTitle("Category Information");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (items[which].equals("(1) Date")) {
                            tvCategory.setText(items[which]);
                            imageCategory.setImageResource(R.drawable.date);
                            imageCategory.setTag(R.drawable.date);

                        } else if(items[which].equals("(2) Study")) {
                            tvCategory.setText(items[which]);
                            imageCategory.setImageResource(R.drawable.study);
                            imageCategory.setTag(R.drawable.study);

                        } else if(items[which].equals("(3) Travel")) {
                            tvCategory.setText(items[which]);
                            imageCategory.setImageResource(R.drawable.travel);
                            imageCategory.setTag(R.drawable.travel);

                        } else if(items[which].equals("(4) Other")) {
                            tvCategory.setText(items[which]);
                            imageCategory.setImageResource(R.drawable.other);
                            imageCategory.setTag(R.drawable.other);

                        }

                        resource = (Integer) imageCategory.getTag();
                        Log.i("Calender_Input", "items[which]: "+items[which]);
                        Log.i("Calender_Input", "resource : "+resource);


                    }

                });
                dialog.show();
            }
        });


    }

    public void onClickedCalenderSave(View v) {

        SharedPreferences pref=getSharedPreferences(CALENDERKEY, MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("Key", calenderKey);
        Log.d("Calender_Input", "pref.getString(Key, fail) : "+pref.getString("Key", "fail"));
        editor.commit();

        SharedPreferences prefs=getSharedPreferences(CALENDERVALUE, MODE_PRIVATE);
        SharedPreferences.Editor editors=prefs.edit();

        String title=etTitle.getText().toString();
        String categoryImage= String.valueOf(resource);
        String location=etLocation.getText().toString();
        String notes=etNotes.getText().toString();

        String calenderValue=title + "_@#@_" + categoryImage + "_@#@_" + location + "_@#@_" + notes;
        editors.putString(pref.getString("Key", "fail"), calenderValue);
        Log.d("Calender_Input","title :"+title+"categoryImage : "+categoryImage+"location: "+location+"CalenderValue:"+calenderValue);
        editors.commit();

        Intent intent=new Intent(getApplicationContext(), CalenderActivity.class);
        startActivity(intent);

    }

    public void onClickedCalenderBack(View v) {
        finish();
    }

}


//        Intent intent=new Intent();
//        intent.putExtra("Title", etTitle.getText().toString());
//        intent.putExtra("CategoryImage", resource);
//        intent.putExtra("CategoryText", tvCategory.getText().toString());
//        intent.putExtra("Location", etLocation.getText().toString());
//        intent.putExtra("AlertMessage", tvAlert.getText().toString());
//        intent.putExtra("Notes", etNotes.getText().toString());
//        setResult(RESULT_OK, intent);