package ddwucom.mobile.software_competition;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreatePostsActivity extends AppCompatActivity {

    private Spinner spinnerCreatePostsCategory;
    private TextView tvCreatePostsTv;
    private Button btnCreatePostsBtn;
    private CheckBox checkBoxCreatePostsAnonymous;
    private EditText etCreatePostsTitle;
    private EditText etCreatePostsBody;

    private PostDTO post;
    private Intent intent;
    private String postType;

    private String _id;
    private String title;
    private String nickname;
    private String anonymous = "0";
    private String body;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_posts);

        postType = getIntent().getStringExtra("postType");
        nickname = getIntent().getStringExtra("nickname");

        spinnerCreatePostsCategory = findViewById(R.id.spinnerCreatePostsCategory);
        tvCreatePostsTv = findViewById(R.id.tvCreatePostsTv);
        btnCreatePostsBtn = findViewById(R.id.btnCreatePostsBtn);
        checkBoxCreatePostsAnonymous = findViewById(R.id.checkBoxCreatePostsAnonymous);
        etCreatePostsTitle = findViewById(R.id.etCreatePostsTitle);
        etCreatePostsBody = findViewById(R.id.etCreatePostsBody);

        switch (postType) {
            case "createPost":
                tvCreatePostsTv.setText("글 작성하기");
                btnCreatePostsBtn.setText("작성");
                btnCreatePostsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createPostListener();
                    }
                });
                break;
            case "createNotice":
                tvCreatePostsTv.setText("공지 작성하기");
                spinnerCreatePostsCategory.setVisibility(View.GONE);
                checkBoxCreatePostsAnonymous.setVisibility(View.GONE);
                btnCreatePostsBtn.setText("작성");
                category = "공지사항";
                btnCreatePostsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createPostListener();
                    }
                });
                break;
            case "update":
                post = (PostDTO) getIntent().getSerializableExtra("post");
                _id = Long.toString(post.get_id());
                tvCreatePostsTv.setText("글 수정하기");
                spinnerCreatePostsCategory.setSelection(getSpinnerIndex(spinnerCreatePostsCategory, post.getCategory()));
                if (post.getAnonymous() == 1) {
                    checkBoxCreatePostsAnonymous.setChecked(true);
                }
                etCreatePostsTitle.setText(post.getTitle());
                etCreatePostsBody.setText(post.getBody());
                btnCreatePostsBtn.setText("수정");
                btnCreatePostsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateListener();
                    }
                });
                break;
        }
    }

    private void createPostListener() {
        title = etCreatePostsTitle.getText().toString();
        body = etCreatePostsBody.getText().toString();
        if (category == null) {
            category = spinnerCreatePostsCategory.getSelectedItem().toString();
        }
        if (title.equals("") || body.equals("")) {
            Toast.makeText(CreatePostsActivity.this, "필수 항목을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkBoxCreatePostsAnonymous.isChecked()) {
            anonymous = "1";
        }

        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("MAINCC", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(CreatePostsActivity.this, PostListActivity.class);
                        intent.putExtra("nickname", nickname);
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
        PostRequest postRequest = new PostRequest("1", title, nickname, anonymous, body, category, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    private void updateListener() {
        title = etCreatePostsTitle.getText().toString();
        body = etCreatePostsBody.getText().toString();
        category = spinnerCreatePostsCategory.getSelectedItem().toString();
        if (title.equals("") || body.equals("")) {
            Toast.makeText(CreatePostsActivity.this, "필수 항목을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (checkBoxCreatePostsAnonymous.isChecked()) {
            anonymous = "1";
        }

        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (result != null && result.equals("1")) {
                        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
                        // 넘어갈 인텐트 정보와 클래스 입력
                        intent = new Intent(CreatePostsActivity.this, ReadPostActivity.class);
                        intent.putExtra("_id", _id);
                        intent.putExtra("post", post);
                        intent.putExtra("nickname", nickname);
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
        PostRequest postRequest = new PostRequest("2", _id, title, nickname, anonymous, body, category, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    private int getSpinnerIndex(Spinner spinner, String item) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return 0;
    }
}