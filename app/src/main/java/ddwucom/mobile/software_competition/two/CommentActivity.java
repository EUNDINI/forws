package ddwucom.mobile.software_competition.two;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ddwucom.mobile.software_competition.R;

public class CommentActivity extends AppCompatActivity {

    CheckBox AnonymouscheckBox;
    EditText CommentEditText;
    Button buttonWrite;
    Button buttonUpdate;
    Button buttonDelete;

    String type = "0";
    String postId;
    public static String nickname;
    Intent intent;
    int anonymous;

    ArrayList<CommentDTO> myCommentDataArrayList;
    ListView listView;
    CommentAdapter myCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        AnonymouscheckBox = findViewById(R.id.AnonymouscheckBox);
        CommentEditText = findViewById(R.id.CommentEditText);
        buttonWrite = findViewById(R.id.buttonWrite);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        listView = findViewById(R.id.customCommentListView);
        myCommentDataArrayList = new ArrayList<CommentDTO>();

        myCommentAdapter = new CommentAdapter(this, R.layout.activity_comment_adapter_view, myCommentDataArrayList);
        listView.setAdapter(myCommentAdapter);

        intent = getIntent();
        postId = intent.getStringExtra("_id");
        nickname = intent.getStringExtra("nickname");
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCommentDataArrayList.clear();
        getList();
    }

    public void getList() {
        type = "0";
        myCommentDataArrayList.clear();
        new Thread() {
            public void run() {
                Response.Listener<String> resposneListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (!response.equals("-1")) {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray ja = jsonArray.getJSONArray(i);
                                    CommentDTO comment = new CommentDTO(Long.valueOf(ja.get(0).toString()), Long.valueOf(ja.get(1).toString()),
                                            ja.get(2).toString(), Integer.valueOf(ja.get(3).toString()), ja.get(4).toString(), ja.get(5).toString());

                                    myCommentDataArrayList.add(comment);
                                }
                            }
                        } catch (JSONException e) {
                            Log.d("MAINCC", "getList : " + e.toString());
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
                CommentRequest commentRequest;
                commentRequest = new CommentRequest(type, postId, resposneListener, errorListener);
                commentRequest.setShouldCache(false);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(commentRequest);
            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myCommentAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    public void onClick(View view) {
        final View v = view;
        switch (v.getId()) {
            case R.id.buttonWrite: // 작성
                createComment();
                break;
            case R.id.buttonUpdate: // 수정
                updateComment(v);
                break;
            case R.id.buttonDelete: // 삭제
                deleteComment(v);
                break;
        }

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getList();
            }
        }, 500);*/
    }

    public void createComment() {
        new Thread() {
            public void run() {
                type = "1";
                String commentBody = CommentEditText.getText().toString();

                // DB 연결하면 닉네임 / 익명 부분으로 나눠서 수정해야함
                if (AnonymouscheckBox.isChecked()) {
                    anonymous = 1;
                } else {
                    anonymous = 0;
                }

                Response.Listener<String> resposneListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");
                            if (result != null && result.equals("1")) {
                                Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
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
                CommentRequest commentRequest = new CommentRequest(type, postId, nickname, String.valueOf(anonymous), commentBody, resposneListener, errorListener);
                commentRequest.setShouldCache(false);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(commentRequest);
            }
        }.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getList();
            }
        }, 500);
    }

    public void updateComment(View v) {
        type = "2";
        final int UpdatePosition = listView.getPositionForView((View) v.getParent());
        final long id = myCommentDataArrayList.get(UpdatePosition).get_id();
        final String comment = myCommentDataArrayList.get(UpdatePosition).getCommentBody();

        AlertDialog.Builder UpdateBuilder = new AlertDialog.Builder(CommentActivity.this);
        UpdateBuilder.setTitle("댓글 수정");
        final EditText et = new EditText(CommentActivity.this);
        et.setText(comment);
        UpdateBuilder.setView(et);

        UpdateBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread() {
                    public void run() {
                        String comm = et.getText().toString();
                        Response.Listener<String> resposneListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("MAINCC", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String result = jsonObject.getString("success");
                                    if (result != null && result.equals("1")) {
                                        Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
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
                        CommentRequest commentRequest = new CommentRequest(type, String.valueOf(id), comm, resposneListener, errorListener);
                        commentRequest.setShouldCache(false);

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(commentRequest);
                    }}.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getList();
                    }
                }, 500);
            }
        });
        UpdateBuilder.setNegativeButton("취소", null);
        UpdateBuilder.show();

    }

    public void deleteComment(View v) {
        type = "3";
        final int position = listView.getPositionForView((View) v.getParent());
        final long _id = myCommentDataArrayList.get(position).get_id();

        AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
        builder.setMessage("댓글을 삭제하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread() {
                            public void run() {
                                Response.Listener<String> resposneListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.d("MAINCC", response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            String result = jsonObject.getString("success");
                                            if (result != null && result.equals("1")) {
                                                Toast.makeText(getApplicationContext(), "성공!", Toast.LENGTH_SHORT).show();
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
                                CommentRequest commentRequest = new CommentRequest(type, String.valueOf(_id), resposneListener, errorListener);
                                commentRequest.setShouldCache(false);

                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(commentRequest);
                            }
                        }.start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getList();
                            }
                        }, 500);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}