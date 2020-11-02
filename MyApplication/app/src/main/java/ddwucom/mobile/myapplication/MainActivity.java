package ddwucom.mobile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<double[]> cctvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connect task = new Connect();
        task.execute("http://forws.dothome.co.kr/cctv.php");
        cctvList = task.getCctv();
        //Log.d("FFFF", String.valueOf(cctvList.size()));
    }

    public class Connect extends AsyncTask<String, String, String> {

        private ArrayList<double[]> cctvList = new ArrayList<>();

        @Override
        protected String doInBackground(String... params) {
            try {
                //연결 url 설정
                URL url = new URL(params[0]);

                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //연결되었으면
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    //연결된 코드가 리턴되면
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;

                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }

                        bufferedReader.close();
                        parsing(sb.toString().trim());
                     //   Log.d("FFFF", sb.toString());
                    }

                    conn.disconnect();

                }
            } catch (Exception e) {
                Log.d("FFFF", "1 " + e.toString());
            }
            return null;
        }


        public void parsing(String data) {

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("response"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    double[] cctvData = {json.getDouble("lat"), json.getDouble("lon")};
                    cctvList.add(cctvData);
                }

                Log.d("FFFF", String.valueOf(cctvList.size()));
            } catch (Exception e) {
                Log.d("FFFF", "2 "+ e.toString());
            }
        }

        public ArrayList<double[]> getCctv() {
            return cctvList;
        }
    }
}