package ddwucom.mobile.software_competition;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ContentActivity extends AppCompatActivity {
    private PostDTO post;
    private String _id;
    private String type;
    private TextView title;
    private TextView nickname;
    private TextView content;
    private TextView createDate;
    private TextView views;
    private TextView likes;
    private Button btnLike;
    private Button btnShowComment;
    private String nick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        title = findViewById(R.id.tvReadPostTitle);
        nickname = findViewById(R.id.tvReadPostNickname);
        content = findViewById(R.id.tvReadPostBody);
        createDate = findViewById(R.id.tvReadPostCreateTime);
        views = findViewById(R.id.tvReadPostViews);
        likes = findViewById(R.id.tvReadPostLikes);
        btnLike = findViewById(R.id.btnLike);
        btnShowComment = findViewById(R.id.btnShowComment);
        Intent intent = getIntent();
        _id = intent.getStringExtra("_id");
        nick = intent.getStringExtra("nickname");

        readPost();

        //views 증가
        viewsIncrease();

        btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //likes 증가
                likesIncrease();
            }
        });

        btnShowComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ContentActivity.this, CommentActivity.class);
                it.putExtra("_id", _id);
                it.putExtra("nickname", nick);
                startActivity(it);
            }
        });
    }

    public void readPost() {
        type = "0";
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("success");
                    if (result != null) {
                        title.setText(result.getString("title"));

                        if (result.getString("anonymous").equals("0"))
                            nickname.setText(result.getString("nickname"));
                        else
                            nickname.setText("익명");


                        createDate.setText(result.getString("createDate"));
                        content.setText(result.getString("body"));
                        views.setText(result.getString("views"));
                        likes.setText(result.getString("likes"));

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
        PostRequest postRequest = new PostRequest(type, _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);

    }

    public void viewsIncrease() {
        type = "10";
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success != null && !success.equals("-1") && !success.equals("error")) {
                        views.setText(success);
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
        PostRequest postRequest = new PostRequest(type, _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    public void likesIncrease() {
        type = "11";
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success != null && !success.equals("-1") && !success.equals("error")) {
                        likes.setText(success);
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
        PostRequest postRequest = new PostRequest(type, _id, resposneListener, errorListener);
        postRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_post, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuUpdatePost:
                type = "2";
                intent = new Intent(ContentActivity.this, PostActivity.class);
                intent.putExtra("post", post);
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
                break;
            case R.id.menuDeletePost:
                type = "3";
                AlertDialog.Builder builder = new AlertDialog.Builder(ContentActivity.this);
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
                                PostRequest postRequest = new PostRequest(type, String.valueOf(post.get_id()), resposneListener, errorListener);
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
}
