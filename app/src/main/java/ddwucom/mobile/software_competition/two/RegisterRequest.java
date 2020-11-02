package ddwucom.mobile.software_competition.two;

import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private final static String URL = "http://forws.dothome.co.kr/signup.php";
    private Map<String, String> map;

    public RegisterRequest(String id, String pwd, String name, String nickname, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,URL, listener, errorListener);

        map = new HashMap<>();
        map.put("name", name);
        map.put("id", id);
        map.put("pwd", pwd);
        map.put("nickname", nickname);
    }


    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
