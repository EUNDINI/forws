package ddwucom.mobile.software_competition;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class DataRequest extends StringRequest {
    private Map<String, String> map;

    public DataRequest(double lat, double lon, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);

        map = new HashMap<>();
        map.put("lat", String.valueOf(lat));
        map.put("lon", String.valueOf(lon));
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
