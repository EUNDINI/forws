package ddwucom.mobile.software_competition.two;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentRequest extends StringRequest {
    private final static String URL = "http://forws.dothome.co.kr/comment.php";
    private Map<String, String> map;

    //read - 1 & delete - 3
    public CommentRequest(String type, String id, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,URL, listener, errorListener);
        map = new HashMap<>();
        map.put("type", type);
        map.put("id", id);
    }

    //등록 - 2
    public CommentRequest(String type, String postId, String nickname, String anonymous, String commentBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("postId", postId);
        map.put("nickname", nickname);
        map.put("anonymous", anonymous);
        map.put("comment", commentBody);
    }

    // 수정 - 3
    public CommentRequest(String type, String _id, String commentBody, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("_id", _id);
        map.put("comment", commentBody);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}