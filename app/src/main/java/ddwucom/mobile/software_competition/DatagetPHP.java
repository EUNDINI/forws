package ddwucom.mobile.software_competition;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatagetPHP {
    private ArrayList<double[]> cctvList;
    private ArrayList<String[]> policeList;
    private Context context;
    private double lat, lon;
    private String url;

    DatagetPHP(Context context, double lat, double lon) {
        cctvList = new ArrayList<>();
        policeList = new ArrayList<>();
        this.context = context;
        this.lat = lat;
        this.lon = lon;
    }

    public void getCCTVData() {
        cctvList.clear();


        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("response");

                    if (!result.equals("-1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            double[] data = {obj.getDouble("lat"), obj.getDouble("lon")};

                            cctvList.add(data);
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
                return;
            }
        };

        url = "http://forws.dothome.co.kr/cctv.php";

        // Volley 로 회원양식 웹으로 전송
        DataRequest dataRequest = new DataRequest(lat, lon, url, resposneListener, errorListener);
        dataRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(dataRequest);


    }

    public void getPoliceData() {
        policeList.clear();
        Response.Listener<String> resposneListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("response");

                    if (!result.equals("-1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String[] data = {obj.getString("name"), obj.getString("lat"), obj.getString("lon")};

                            policeList.add(data);
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
                return;
            }
        };

        url = "http://forws.dothome.co.kr/police.php";

        // Volley 로 회원양식 웹으로 전송
        DataRequest dataRequest = new DataRequest(lat, lon, url, resposneListener, errorListener);
        dataRequest.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(dataRequest);

    }

    public ArrayList<double[]> getCctvList() {
        return cctvList;
    }

    public ArrayList<String[]> getPoliceList() {
        return policeList;
    }
}
