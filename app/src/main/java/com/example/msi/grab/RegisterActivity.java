package com.example.msi.grab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    Button btnDone;
    Button btnCancel;

    public static final String MyPREFERENCES="MyPrefs";
    public static final String Email="EmailKey";
    public static final String Password="PasswordKey";
    public static final String Username="UserName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername=(EditText) findViewById(R.id.etUsername);
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPassword);
        etPasswordConfirm=(EditText) findViewById(R.id.etPasswordConfirm);
        btnDone=(Button) findViewById(R.id.btnDone);
        btnCancel=(Button) findViewById(R.id.btnCancel);

        //비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password=etPassword.getText().toString();
                String confirm=etPasswordConfirm.getText().toString();

                if ( password.equals(confirm) ) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);

                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {

            SharedPreferences pref=getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor=pref.edit();

            String emailcheck=pref.getString(Email, "");

            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().length()==0) {
                    Toast.makeText(RegisterActivity.this, "UserName을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                    return;
                }

                //이메일 입력 확인
                if(etEmail.getText().toString().length()==0) {
                    Toast.makeText(RegisterActivity.this, "E-mail을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                if(EmailValid(etEmail.getText().toString())==false) {
                    Toast.makeText(RegisterActivity.this, "올바른 E-mail 형식이 아닙니다.\nex)abcd@abc.nova.com", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                Log.i("Register", "저장되어 있는 값 :"+emailcheck);
                Log.i("Register", "etEmail.getText() 입력값 :"+etEmail.getText().toString());

                if(emailcheck.equals(etEmail.getText().toString())) {
                    etEmail.setError("이미 존재하는 아이디가 있습니다.");
                    return;
                }

                if(etPassword.getText().toString().length()==0) {
                    Toast.makeText(RegisterActivity.this, "Password를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                if(PasswordValid(etPassword.getText().toString())==false) {
                    Toast.makeText(RegisterActivity.this, "비밀번호는 '특수문자'를 포함하여 '여덟 글자 이상'을 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                if(etPasswordConfirm.getText().toString().length()==0) {
                    Toast.makeText(RegisterActivity.this, "PasswordConfirm를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;

                }

                if(!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }



                editor.putString(Email, etEmail.getText().toString());
                editor.putString(Password, etPassword.getText().toString());
                editor.putString(Username, etUsername.getText().toString());
                editor.commit();

                // 회원가입한 이메일 값 보내기
                Intent result=new Intent();
                result.putExtra("email", etEmail.getText().toString());

                // 자신을 호출한 Activity로 데이터를 보냄
                setResult(RESULT_OK, result);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    // 정규식을 이용한 이메일 형식 체크
    private boolean EmailValid(String email) {
        String mail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(mail);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    // 정규식을 이용한 비밀번호 형식 체크
    private boolean PasswordValid(String password) {
        String password1 ="^[a-zA-Z0-9!@.#$%^&*?_~]{8,16}$";
        Pattern pass =Pattern.compile(password1);
        Matcher word= pass.matcher(password);
        return word.matches();

    }

}


/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this, "onSaveInstanceState 호출됨", Toast.LENGTH_SHORT).show();

        outState.putString("TEXT", inPutText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "onRestoreInstanceState 호출됨", Toast.LENGTH_SHORT).show();

        etEmail.setText(savedInstanceState.getString("TEXT"));
    }




    생명주기



    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "RegisterActiviy: OnStart 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "RegisterActiviy: onRestart 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        saveState();
        Toast.makeText(this, "RegisterActiviy: onPause 호출됨 + saveState() 실행됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        restoreState();
        Toast.makeText(this, "RegisterActiviy: onResume 호출됨 + restoreState() 실행됨", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "RegisterActiviy: onStop 호출됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "RegisterActiviy: OnDestroy 호출됨", Toast.LENGTH_SHORT).show();
    }

    // 값 저장하기
    protected void saveState() {
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("email", etEmail.getText().toString());
        editor.putString("username", etUsername.getText().toString());
        editor.commit(); // 값을 저장한 후에는 반드시 불러야 하는 함수
    }


    // 값 불러오기
    protected void restoreState() {
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);

        if ((pref!=null) && (pref.contains("email")) || (pref.contains("username")) || (pref.contains("male"))) {
            String email=pref.getString("email", "");
            etEmail.setText(email);

            String username=pref.getString("username", ""); // 불러올 공간 / 기본값(값이 저장되어 있지 않을 때)
            etUsername.setText(username);
        }

    }
 */
