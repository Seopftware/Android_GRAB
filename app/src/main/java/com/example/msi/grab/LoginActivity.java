package com.example.msi.grab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.msi.grab.RegisterActivity.Email;
import static com.example.msi.grab.RegisterActivity.MyPREFERENCES;
import static com.example.msi.grab.RegisterActivity.Password;
import static com.example.msi.grab.RegisterActivity.Username;

public class LoginActivity extends AppCompatActivity {

    Button btnRegist;
    EditText etEmail;
    EditText etPassword;
    CheckBox autoLogin;
    SharedPreferences autosettings;
    SharedPreferences.Editor autoeditor;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressCloseHandler = new BackPressCloseHandler(this);

        btnRegist = (Button) findViewById(R.id.btnRegist);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);

        SharedPreferences pref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userInputEmail = pref.getString(Email, "");
        String userInputPassword = pref.getString(Password, "");

//        etEmail.setText(userInputEmail);

        autosettings = getSharedPreferences("settings", MODE_PRIVATE);
        autoeditor = autosettings.edit();

        if (autosettings.getBoolean("Auto_Login_enabled", false)) {
            etPassword.setText(userInputPassword);
            autoLogin.setChecked(true);
            Toast.makeText(getApplicationContext(), pref.getString(Username, "") + "님\n자동 로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class); // 홈 화면을 보여줄 인텐트 객체 생성
            startActivity(intent);
            finish();
        }

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String PW = etPassword.getText().toString();
                    autoeditor.putString("PW", PW);
                    System.out.println("PW" + etPassword.getText().toString());
                    autoeditor.putBoolean("Auto_Login_enabled", true);
                    autoeditor.commit();

                } else {
                    autoeditor.clear();
                    autoeditor.commit();
                    return;
                }
            }
        });


        // 호출
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                // 동시에 사용 가능
                // intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
            }
        });

    }

    // emil값 받아오는 부분 구현
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터 logcat 확인
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            etEmail.setText(data.getStringExtra("email")); // 이메일 값을 editText에 받아오기
        }
    }

    // LOG IN 버튼을 클릭할 때 발생하는 이벤트

    public void onClickedLogin(View v) {

        SharedPreferences pref = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        String userInputEmail = pref.getString(Email, "");
        String userInputPassword = pref.getString(Password, "");

        // 유효성 검사
        if (etEmail.length() <= 0) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }

        if (etPassword.length() <= 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }

        if (userInputEmail.equals(etEmail.getText().toString())) {
            if (userInputPassword.equals(etPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), pref.getString(Username, "") + "님\n로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class); // 홈 화면을 보여줄 인텐트 객체 생성
                startActivity(intent);
                finish();
            } else if (!userInputPassword.equals(etPassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (!userInputEmail.equals(etEmail.getText().toString())) {
            Toast.makeText(getApplicationContext(), "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
            return;

        }

    }
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "RegisterActiviy: OnStart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, "RegisterActiviy: onRestart 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveState();
//        Toast.makeText(this, "RegisterActiviy: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        restoreState();
//        Toast.makeText(this, "RegisterActiviy: onResume 호출됨 + restoreState() 실행됨", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(this, "RegisterActiviy: onStop 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "RegisterActiviy: OnDestroy 호출됨", Toast.LENGTH_SHORT).show();
//    }
//
//    // 값 저장하기
//    protected void saveState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor=pref.edit();
//        editor.putString("email", etEmail.getText().toString());
//        editor.commit(); // 값을 저장한 후에는 반드시 불러야 하는 함수
//    }
//
//
//    // 값 불러오기
//    protected void restoreState() {
//        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
//
//        if ((pref!=null) && (pref.contains(Email))) {
//            String email=pref.getString(Email, "");
//            etEmail.setText(email);
//
//        }
//
//    }
}
