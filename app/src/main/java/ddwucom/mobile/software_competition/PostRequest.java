package ddwucom.mobile.software_competition;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends StringRequest {
    private final static String URL = "http://forws.dothome.co.kr/post.php";
    private Map<String, String> map;

    public PostRequest(String type, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);
        map = new HashMap<>();
        map.put("type", type);
    }

    //read
    public PostRequest(String type, String postId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("_id", postId);
    }

    //list-찾기
    public PostRequest(String type, String category, String searchKey, String searchType, String currentPage, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("category", category);
        map.put("searchType",searchType);
        map.put("currentPage", currentPage);
        map.put("searchKey", searchKey);
    }

    //list-카테고리별
    public PostRequest(String type, String category, String size, String currentPage, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("category", category);
        map.put("size", size);
        map.put("currentPage", currentPage);
    }

    //insert
    public PostRequest(String type, String title, String nickname, String anonymous, String content, String category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);
        map = new HashMap<>();
        map.put("type", type);
        map.put("title", title);
        map.put("nickname", nickname);
        map.put("anonymous", anonymous);
        map.put("content", content);
        map.put("category", category);
    }

    //update
    public PostRequest(String type, String _id, String title, String nickname, String anonymous, String content, String category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST,URL, listener, errorListener);
        map = new HashMap<>();
        map.put("type", type);
        map.put("_id", _id);
        map.put("title", title);
        map.put("nickname", nickname);
        map.put("anonymous", anonymous);
        map.put("content", content);
        map.put("category", category);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}