package ddwucom.mobile.software_competition.two;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
        private final static String URL = "http://forws.dothome.co.kr/login.php";
        private Map<String,String> map;

        public LoginRequest(String id, String pwd, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST,URL, listener, errorListener);

            map = new HashMap<>();
            map.put("id", id);
            map.put("pwd", pwd);

        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
}
