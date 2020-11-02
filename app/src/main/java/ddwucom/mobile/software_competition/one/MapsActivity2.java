package ddwucom.mobile.software_competition.one;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ddwucom.mobile.software_competition.R;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MarkerOptions mOptions;
    private String address;
    private EditText editText;
    private List<Address> addressList = null;
    private ArrayList<double[]> cctvList = null;
    private ArrayList<String[]> policeList = null;
    private ArrayList<CourierDTO> courierList = null;
    private ArrayList<GuardHouseDTO> guardHouseList = null;
    private double latitude;
    private double longitude;
    private Location my;
    private int count = 0;
    private XmlParser xmlParser;

    private Spinner spinner;
    private String placeType;
    private Intent intent;
    private int idx;
    private BitmapDescriptor icon;
    private TextView tvPlaceName;
    private TextView tvDetail;
    private ConstraintLayout simpleInfoLayout;
    private String addrStr;
    private Intent getIntent;
    private Address addr;
    private TextView tvCount;

    private DatagetPHP data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        my = new Location("my");

        tvCount = findViewById(R.id.tvCount);

        spinner = (Spinner) findViewById(R.id.serviceSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                data = new DatagetPHP(MapsActivity2.this, latitude, longitude);
                String service = (String) parent.getItemAtPosition(position);
                switch (service) {
                    case "CCTV":
                        if (cctvList == null) {
                            new Thread() {
                                public void run() {
                                    data.getCCTVData();
                                }
                            }.start();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cctvList = data.getCctvList();
                                    checkingCCTVData();

                                    tvCount.setText("1km 내의 CCTV의 개수는 " + Integer.toString(count) + "개 입니다.");
                                    tvCount.setVisibility(View.VISIBLE);
                                }
                            }, 800);
                        } else {
                            checkingCCTVData();

                            tvCount.setText("1km 내의 CCTV의 개수는 " + Integer.toString(count) + "개 입니다.");
                            tvCount.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "여성안심택배함":
                        checkingCourierData();
                        break;
                    case "여성안심지킴이집":
                        checkingGuardHouseData();
                        break;
                    case "경찰관서":
                        if (policeList == null) {
                            new Thread() {
                                public void run() {
                                    data.getPoliceData();
                                }
                            }.start();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    policeList = data.getPoliceList();
                                    checkingPoliceData();

                                    tvCount.setText("1km 내의 경찰서의 개수는 " + Integer.toString(count) + "개 입니다.");
                                    tvCount.setVisibility(View.VISIBLE);
                                }
                            }, 800);
                        } else {
                            checkingPoliceData();
                            tvCount.setText("1km 내의 경찰서의 개수는 " + Integer.toString(count) + "개 입니다.");
                            tvCount.setVisibility(View.VISIBLE);
                        }

                        break;
                }

                if (service.equals("여성안심택배함") || service.equals("여성안심지킴이집")) {
                    tvCount.setText("1km 내의 " + service + "의 개수는 " + Integer.toString(count) + "개 입니다.");
                    tvCount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        simpleInfoLayout = findViewById(R.id.simpleInfoLayout);
        tvPlaceName = findViewById(R.id.tvPlaceName);
        tvDetail = findViewById(R.id.tvDetail);
        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (placeType) {
                    case "courier":
                        intent = new Intent(MapsActivity2.this, CourierInfoActivity.class);
                        intent.putExtra("courier", courierList.get(idx));
                        startActivity(intent);
                        break;
                    case "guardHouse":
                        intent = new Intent(MapsActivity2.this, GuardHouseInfoActivity.class);
                        intent.putExtra("guardHouse", guardHouseList.get(idx));
                        startActivity(intent);
                        break;
                    case "police":
                        Toast.makeText(MapsActivity2.this, "상세보기를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bitmap bit = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.marker_blue_soft_more), 50, 50, false);
        icon = BitmapDescriptorFactory.fromBitmap(bit);
    }


    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        //위치 받아오기
        getIntent = getIntent();
        try {
            addrStr = getIntent.getStringExtra("address");
            addressList = geocoder.getFromLocationName(addrStr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //위치 표시
        addr = addressList.get(0);
        latitude = addr.getLatitude();
        longitude = addr.getLongitude();

        checkingMap(addr);

        this.mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));
                idx = Integer.parseInt(marker.getTitle().substring(marker.getTitle().lastIndexOf("/") + 1));
                switch (placeType) {
                    case "CCTV":
                        break;
                    case "courier":
                        tvPlaceName.setText(courierList.get(idx).getName());
                        simpleInfoLayout.setVisibility(View.VISIBLE);
                        break;
                    case "guardHouse":
                        tvPlaceName.setText(guardHouseList.get(idx).getName());
                        simpleInfoLayout.setVisibility(View.VISIBLE);
                        break;
                    case "police":
                        tvPlaceName.setText(policeList.get(idx)[0]);
                        simpleInfoLayout.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

    }

    public void checkingMap(Address addr) {
        latitude = addr.getLatitude();
        longitude = addr.getLongitude();

        LatLng point = new LatLng(latitude, longitude);

        mOptions = new MarkerOptions();
        mOptions.title("내 위치");
        mOptions.position(point);

        mMap.addMarker(mOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

        address = addr.getAddressLine(0);
    }

    public void checkingMap(double latitude, double longitude, String name) {
        LatLng point = new LatLng(latitude, longitude);

        MarkerOptions mOptions = new MarkerOptions();
        mOptions.position(point)
                .icon(icon);

        if (name != null) {
            mOptions.title(name);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
        mMap.addMarker(mOptions);
    }

    public void checkingCCTVData() {
        mMap.clear();
        checkingMap(addr);

        placeType = "CCTV";
        count = 0;
        for (int i = 0; i < cctvList.size(); i++) {
            count++;
            checkingMap(cctvList.get(i)[0], cctvList.get(i)[1], null);
        }
    }

    public void checkingPoliceData() {
        mMap.clear();
        checkingMap(addr);

        placeType = "police";

        count = 0;
        for (int i = 0; i < policeList.size(); i++) {
            String name = policeList.get(i)[0] + "/" + i;
            count++;
            checkingMap(Double.valueOf(policeList.get(i)[1]), Double.valueOf(policeList.get(i)[2]), name);
        }
    }

    public void checkingCourierData() {
        mMap.clear();
        checkingMap(addr);
        placeType = "courier";

        try {
            if (courierList == null) {
                xmlParser = new XmlParser(this);
                xmlParser.parseXMLCourier();
                courierList = xmlParser.getCourierList();
            }

            my.setLatitude(latitude);
            my.setLongitude(longitude);

            count = 0;
            for (int i = 0; i < courierList.size(); i++) {
                Location obj = new Location("data");

                String name = courierList.get(i).getName() + "/" + i;
                obj.setLatitude(courierList.get(i).getLat());
                obj.setLongitude(courierList.get(i).getLng());

                double distance = my.distanceTo(obj);

                if (distance <= 1000) {
                    count++;
                    checkingMap(courierList.get(i).getLat(), courierList.get(i).getLng(), name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkingGuardHouseData() {
        mMap.clear();
        checkingMap(addr);

        placeType = "guardHouse";
        try {
            if (guardHouseList == null) {
                xmlParser = new XmlParser(this);
                xmlParser.parseXMLGuardHouse();
                guardHouseList = xmlParser.getGuardHouseList();
            }

            my.setLatitude(latitude);
            my.setLongitude(longitude);

            count = 0;
            for (int i = 0; i < guardHouseList.size(); i++) {
                Location obj = new Location("data");

                String name = guardHouseList.get(i).getName() + "/" + i;
                obj.setLatitude(guardHouseList.get(i).getLat());
                obj.setLongitude(guardHouseList.get(i).getLng());

                double distance = my.distanceTo(obj);

                if (distance <= 1000) {
                    count++;
                    checkingMap(guardHouseList.get(i).getLat(), guardHouseList.get(i).getLng(), name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}