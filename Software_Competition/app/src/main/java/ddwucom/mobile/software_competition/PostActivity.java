package ddwucom.mobile.software_competition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private String nickname;
    private Spinner category;
    private CheckBox anonymous;
    private Button send;
    private String type = "1";
    private Intent intent;

    /* intent로 넘겨받을 값, type, nickname, _id(update) */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.etCreatePostTitle);
        content = findViewById(R.id.etCreatePostBody);
        anonymous = findViewById(R.id.checkBoxAnonymous);
        category = findViewById(R.id.spinnerCreatePostCategory);
        send = findViewById(R.id.btnCreatePostCreate);

        intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        type = intent.getStringExtra("type");



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("1"))
                    createPost();
                else if(type.equals("2"));
                    updatePost();
            }
        });
    }

    public void createPost(){
        String createTitle = title.getText().toString();
        String createContent = content.getText().toString();
        String createCategory = category.getSelectedItem().toString();
        String createAnonymous = "0";

        if(anonymous.isChecked())
            createAnonymous = "1";

        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("MAINCC", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.d("MAINCC", e.toString());
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
        PostRequest postRequest = new PostRequest(type, createTitle, nickname, createAnonymous, createContent, createCategory, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    public void updatePost(){
        String updateTitle = title.getText().toString();
        String updateContent = content.getText().toString();
        String updateCategory = category.getSelectedItem().toString();
        String updateAnonymous = "0";
        String _id = getIntent().getStringExtra("_id");

        if(anonymous.isChecked())
            updateAnonymous = "1";

        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
                        // 넘어갈 인텐트 정보와 클래스 입력
                        Intent intent = new Intent(PostActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.d("MAINCC", e.toString());
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
        PostRequest postRequest = new PostRequest(type, _id, updateTitle, nickname, updateAnonymous, updateContent, updateCategory, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);

    }
}
