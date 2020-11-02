package ddwucom.mobile.software_competition;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckRequest extends StringRequest {
    private final static String URL = "http://forws.dothome.co.kr/check.php";
    private Map<String, String> map;

    public CheckRequest(int type, String data, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,URL, listener, errorListener);
        map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put("data", data);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
