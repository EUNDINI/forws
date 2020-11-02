package ddwucom.mobile.software_competition.two;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ddwucom.mobile.software_competition.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText id, pwd, name, nickname, passwdcheck;
    private RadioButton woman;
    private Button send, cancel;
    Boolean checkMatch[] = {false, false, false};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = findViewById(R.id.userid);
        pwd = findViewById(R.id.userpasswd);
        name = findViewById(R.id.username);
        nickname = findViewById(R.id.usernickname);
        woman = findViewById(R.id.woman);
        send = findViewById(R.id.send);
        cancel = findViewById(R.id.send_cancel);
        passwdcheck = findViewById(R.id.passwdcheck);

        passwdcheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                String passwdck = passwdcheck.getText().toString();
                TextView same = findViewById(R.id.same);
                if(!hasFocus && !passwdck.equals("")) {
                    String check = pwd.getText().toString();
                    if(passwdck.equals(check)){
                        same.setText("일치합니다.");
                        checkMatch[1] = true;
                    }
                    else {
                        same.setText("불입치합니다.");
                        passwdcheck.setText("");
                        checkMatch[1] = false;
                    }

                    same.setVisibility(View.VISIBLE);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "이름를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (id.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "ID를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkMatch[0] == false) {
                    Toast.makeText(RegisterActivity.this, "ID 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                    return;

                } else if (pwd.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwdcheck.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkMatch[1] == false) {
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (nickname.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (checkMatch[2] == false) {
                    Toast.makeText(RegisterActivity.this, "닉네임 중복확인을 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    register();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("회원가입 취소");
                builder.setMessage("회원가입을 종료하시겠습니까?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                builder.show();
            }
        });
    }

    private void register() {
        String user_id = id.getText().toString();
        String user_pwd = pwd.getText().toString();
        String user_name = name.getText().toString();
        String user_nickname = nickname.getText().toString();
        String user_gender;

        if (woman.isChecked())
            user_gender = "1";
        else
            user_gender = "2";

        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "회원가입 실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "회원가입 처리시 에러발생!", Toast.LENGTH_SHORT).show();
                return;
            }
        };

        // Volley 로 회원양식 웹으로 전송
        RegisterRequest registerRequest = new RegisterRequest(user_id, user_pwd, user_name, user_nickname, user_gender, resposneListener, errorListener);
        registerRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(registerRequest);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_check:
                check(1, id.getText().toString());
                break;
            case R.id.nickname_check:
                check(2, nickname.getText().toString());
                break;
        }
    }

    private void check(final int type, final String data) {
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TextView resultCheck = null;
                if (type == 1) {
                    resultCheck = findViewById(R.id.duplication_id);
                } else if (type == 2) {
                    resultCheck = findViewById(R.id.duplication_nickname);
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        resultCheck.setText("사용가능합니다.");
                        resultCheck.setVisibility(View.VISIBLE);

                        if (type == 1)
                            checkMatch[0] = true;
                        else if (type == 2)
                            checkMatch[2] = true;
                    } else {
                        resultCheck.setText("사용이 불가능합니다. 다시 입력해주세요.");
                        resultCheck.setVisibility(View.VISIBLE);

                        if (type == 1) {
                            id.setText("");
                            checkMatch[0] = false;
                        }
                        else if (type == 2) {
                            nickname.setText("");
                            checkMatch[2] = false;
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MAINCC", error.toString());
                Toast.makeText(getApplicationContext(), "에러발생!", Toast.LENGTH_SHORT).show();
                return;
            }
        };

        // Volley 로 회원양식 웹으로 전송
        CheckRequest checkRequest = new CheckRequest(type, data, resposneListener, errorListener);
        checkRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(checkRequest);
    }
}
