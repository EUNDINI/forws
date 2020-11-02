package ddwucom.mobile.software_competition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadPostActivity extends AppCompatActivity {

    private PostDTO post;
    private PostDTO post2;
    private Intent intent;
    private String _id;
    private String nickname;

    private TextView tvTitle;
    private TextView tvCreateDate;
    private TextView tvBody;
    private TextView tvNickname;
    private TextView tvViews;
    private TextView tvLikes;

    private Button btnLike;
    private Button btnShowComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        post2 = (PostDTO) getIntent().getSerializableExtra("post");
        _id = Long.toString(post2.get_id());
        nickname = getIntent().getStringExtra("nickname");

        tvTitle = findViewById(R.id.tvReadPostTitle);
        tvCreateDate = findViewById(R.id.tvReadPostCreateTime);
        tvBody = findViewById(R.id.tvReadPostBody);
        tvNickname = findViewById(R.id.tvReadPostNickname);
        tvViews = findViewById(R.id.tvReadPostViews);
        tvLikes = findViewById(R.id.tvReadPostLikes);

        readPost();

        viewsIncrease();

        btnShowComment = findViewById(R.id.btnShowComment);
        btnShowComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ReadPostActivity.this, CommentActivity.class);
                intent.putExtra("nickname", nickname);
                intent.putExtra("_id", _id);
                startActivity(intent);
            }
        });

        btnLike = findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likesIncrease();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //닉네임이 같으면 수정, 삭제 메뉴가 보이게
//        nickname = getIntent().getStringExtra("nickname");
        Log.d("MAINCC", post2.getNickname());
        if (nickname.equals(post2.getNickname())) {
            getMenuInflater().inflate(R.menu.menu_read_post, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuUpdatePost:
                intent = new Intent(ReadPostActivity.this, CreatePostsActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("postType", "update");
                intent.putExtra("nickname", nickname);
                startActivity(intent);
                finish();
                break;
            case R.id.menuDeletePost:
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ReadPostActivity.this);
                builder.setTitle("글을 삭제하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                PostRequest postRequest = new PostRequest("3", String.valueOf(post.get_id()), resposneListener, errorListener);
                                postRequest.setShouldCache(false);

                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(postRequest);

                            }
                        })
                        .show();
                break;
        }
        return true;
    }

    public void readPost() {
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("success");
                    if (result != null) {
                        tvTitle.setText(result.getString("title"));

                        if (result.getString("anonymous").equals("0"))
                            tvNickname.setText(result.getString("nickname"));
                        else
                            tvNickname.setText("익명");

                        tvCreateDate.setText(result.getString("createDate"));
                        tvBody.setText(result.getString("body"));
                        tvViews.setText(result.getString("views"));
                        tvLikes.setText(result.getString("likes"));

                        post = new PostDTO(Long.valueOf(_id), result.getString("title"), result.getString("nickname"), result.getInt("anonymous"),
                                result.getString("createDate"), result.getString("body"), result.getString("category"), result.getInt("views"), result.getInt("likes"));
                    } else {
                        Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.d("MAINCC", "1 " + e.toString());
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
        PostRequest postRequest = new PostRequest("0", _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    public void viewsIncrease(){
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success != null && !success.equals("-1") && !success.equals("error")) {
                        tvViews.setText(success);
                    } else {
                        Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.d("MAINCC", "1 " + e.toString());
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
        PostRequest postRequest = new PostRequest("10", _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    public void likesIncrease(){
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success != null && !success.equals("-1") && !success.equals("error")) {
                        tvLikes.setText(success);
                    } else {
                        Toast.makeText(getApplicationContext(), "실패!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    Log.d("MAINCC", "1 " + e.toString());
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
        PostRequest postRequest = new PostRequest("11", _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }
}