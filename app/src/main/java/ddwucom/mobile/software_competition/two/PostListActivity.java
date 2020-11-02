package ddwucom.mobile.software_competition.two;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import ddwucom.mobile.software_competition.R;

public class PostListActivity extends AppCompatActivity {

    final int READ_CODE = 100;

    private ListView postListView;
    private ArrayList<PostDTO> postList;
    private PostListAdapter adapter;
    private Spinner spinnerPostListCategory;

    private Intent intent;
    private String nickname;
    private String _id;

    private Button btnCreatePost;
    private Button btnAdmin;

    private EditText etSearchPost;
    private String searchStr;
    private String searchType;
    private String cate = "전체";

    private ProgressBar progressBar;
    private boolean lastItemVisibleFlag = false;    //리스트 스크롤이 최하단으로 이동했는지 체크
    private boolean mLockListView = false;          //데이터를 불러올 때 중복되지 않기위한 변수
    private int pageSize = 15;
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        postListView = findViewById(R.id.postListView);
        postList = new ArrayList();
        adapter = new PostListAdapter(this, R.layout.post_list_view, postList);
        postListView.setAdapter(adapter);

        nickname = getIntent().getStringExtra("nickname");

        spinnerPostListCategory = findViewById(R.id.spinnerPostListCategory);
        spinnerPostListCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cate = (String) parent.getItemAtPosition(position);
                pageNum = 0;
                postList.clear();
                getNewPostList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _id = String.valueOf(postList.get(position).get_id());
                intent = new Intent(PostListActivity.this, ReadPostActivity.class);
                intent.putExtra("_id", _id);
                intent.putExtra("post", postList.get(position));
                intent.putExtra("nickname", nickname);
                startActivityForResult(intent, READ_CODE);
            }
        });

        progressBar = findViewById(R.id.progressBarPostList);
        progressBar.setVisibility(View.INVISIBLE);

        //페이징 처리
        postListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
                // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
                // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
                // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
                    // 화면이 바닥에 닿을때 처리
                    // 로딩중을 알리는 프로그레스바를 보인다.
                    progressBar.setVisibility(View.VISIBLE);

                    getNewPostList();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
                // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
                // totalItemCount : 리스트 전체의 총 갯수
                // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });

        btnCreatePost = findViewById(R.id.btnCreatePost);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PostListActivity.this, CreatePostsActivity.class);
                intent.putExtra("postType", "createPost");
                intent.putExtra("nickname", nickname);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });

        btnAdmin = findViewById(R.id.btnAdmin);
        if (nickname.equals("admin")) {
            btnAdmin.setVisibility(View.VISIBLE);
        }
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PostListActivity.this, AdminActivity.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });
    }


    public void getNewPostList() {
        //리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록
        mLockListView = true;

        new Thread() {
            public void run() {
                Response.Listener<String> resposneListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (!response.equals("[]")) {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray ja = jsonArray.getJSONArray(i);
                                    PostDTO post = new PostDTO(Long.valueOf(ja.get(0).toString()), ja.get(1).toString(),
                                            ja.get(2).toString(), Integer.valueOf(ja.get(3).toString()), ja.get(4).toString(), ja.get(5).toString());
                                    postList.add(post);
                                }
                            } else {
                                Toast.makeText(PostListActivity.this, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
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
                PostRequest postRequest;
                postRequest = new PostRequest("20", cate, String.valueOf(pageSize), String.valueOf(pageNum), resposneListener, errorListener);

                postRequest.setShouldCache(false);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(postRequest);
            }
        }.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum++;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mLockListView = false;
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postList.clear();
        pageNum = 0;
        getNewPostList();
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_post, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTitleSearch :
                searchType = "title";
                break;
            case R.id.menuNicknameSearch :
                searchType = "nickname";
                break;
            case R.id.menuContentSearch :
                searchType = "body";
                break;
        }
        pageNum = 0;
        postList.clear();
        searchPost();
        return true;
    }

    public void searchPost() {
        mLockListView = true;
        final ConstraintLayout layout = (ConstraintLayout) View.inflate(PostListActivity.this, R.layout.dialog_search, null);
        new AlertDialog.Builder(PostListActivity.this)
                .setView(layout)
                .setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etSearchPost = layout.findViewById(R.id.etSearchPost);
                        searchStr = etSearchPost.getText().toString();
                        new Thread() {
                            public void run() {
                                Response.Listener<String> resposneListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            if (!response.equals("-1")) {
                                                if (!response.equals("[]")) {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONArray ja = jsonArray.getJSONArray(i);
                                                        PostDTO post = new PostDTO(Long.valueOf(ja.get(0).toString()), ja.get(1).toString(),
                                                                ja.get(2).toString(), Integer.valueOf(ja.get(3).toString()), ja.get(4).toString(), ja.get(5).toString());
//                                                        Post post = new Post(Long.valueOf(ja.get(0).toString()), ja.get(1).toString(),
//                                                                ja.get(2).toString(), Integer.valueOf(ja.get(3).toString()), ja.get(4).toString());
                                                        postList.add(post);
                                                    }
                                                } else if (postList.size() == 0) {
                                                    Toast.makeText(PostListActivity.this, "검색결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(PostListActivity.this, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();
                                                }
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
                                PostRequest postRequest;
                                Log.d("MAINCC", searchStr);
                                postRequest = new PostRequest("21", cate, searchStr, searchType, String.valueOf(pageNum), resposneListener, errorListener);

                                postRequest.setShouldCache(false);

                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(postRequest);
                            }
                        }.start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //   dialog.dismiss();
                                pageNum++;
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                mLockListView = false;
                            }
                        }, 500);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 500);
    }
}