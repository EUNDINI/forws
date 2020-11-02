package ddwucom.mobile.software_competition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    boolean searchFlag;

    TextView text;
    XmlPullParser xpp;
    String key = "M3jbEBnTMm7j5cH%2FdgW7FO2NHoeOQT%2BN5ygiNGwWScuUSOsJeTf1UZU%2FEX9PBOE47tDj5QH6EQ2GidOVhvcU1A%3D%3D";
    String data;
    String queryUrl;
    String pageNo;
    String str;
    String numOfRows;

    String regDt;
    String thumbUrl;
    String title;
    String viewUrl;

    ArrayList<NewsDTO> policyNewsDataArrayList;
    ListView listView;
    NewsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        searchFlag = false;
        pageNo = "1";
        str = "";
        numOfRows = "10";
        policyNewsDataArrayList = new ArrayList<NewsDTO>();
        text = findViewById(R.id.editText);

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        myAdapter = new NewsAdapter(this, R.layout.activity_news_adapter_view, policyNewsDataArrayList);
        listView = findViewById(R.id.customListView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(policyNewsDataArrayList.get(position).getViewUrl()));
                startActivity(intent);
            }
        });
    }

    String getXmlData() {
        int count = 1;
        StringBuffer buffer = new StringBuffer();

        str = text.getText().toString(); //EditText에 작성된 Text얻어오기

        queryUrl = "http://apis.data.go.kr/1383000/mogefNew/nwEnwSelectList?"
                +"pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "&ServiceKey=" + key;

        try{
            URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); //xml파싱을 위한
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("item")) ; // 첫번째 검색결과
                        else if(tag.equals("regDt")){
                            buffer.append("regDt : ");
                            xpp.next();
                            regDt = xpp.getText();
                            buffer.append(regDt); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("thumbUrl")){
                            buffer.append("thumbUrl : ");
                            xpp.next();
                            thumbUrl = xpp.getText();
                            buffer.append(thumbUrl); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("title")){
                            buffer.append("title :");
                            xpp.next();
                            title = xpp.getText();
                            buffer.append(title); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("viewUrl")){
                            buffer.append("viewUrl :");
                            xpp.next();
                            viewUrl = xpp.getText();
                            buffer.append(viewUrl); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("item")) {
                            if (searchFlag == true) {
                                if (title.toLowerCase().contains(str)) {
                                    policyNewsDataArrayList.add( new NewsDTO(count++, regDt, thumbUrl, title, viewUrl));
                                }
                            }
                            else {
                                policyNewsDataArrayList.add( new NewsDTO(count++, regDt, thumbUrl, title, viewUrl));
                            }
                        }
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return buffer.toString(); //StringBuffer 문자열 객체 반환

    } //getXmlData method


    public void onClick(View v) {
        searchFlag = false;
        numOfRows = "10";
        switch (v.getId()) {
            case R.id.buttonOne:
                policyNewsDataArrayList.clear();
                pageNo = "1";
                break;
            case R.id.buttonFront:
                if (pageNo.equals("1")) {
                    break;
                }
                else {
                    policyNewsDataArrayList.clear();
                    pageNo = Integer.toString(Integer.parseInt(pageNo) - 1);
                }
                break;
            case R.id.buttonTwo:
                policyNewsDataArrayList.clear();
                pageNo = "2";
                break;
            case R.id.buttonThree:
                policyNewsDataArrayList.clear();
                pageNo = "3";
                break;
            case R.id.buttonFour:
                policyNewsDataArrayList.clear();
                pageNo = "4";
                break;
            case R.id.buttonBack:
                policyNewsDataArrayList.clear();
                pageNo = Integer.toString(Integer.parseInt(pageNo) + 1);
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        myAdapter.notifyDataSetChanged();
    } // onClick method

    public void onSearchClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSearch:
                policyNewsDataArrayList.clear();
                numOfRows = "100";
                searchFlag = true;
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getXmlData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        myAdapter.notifyDataSetChanged();
    } // onSearchClick method
}
