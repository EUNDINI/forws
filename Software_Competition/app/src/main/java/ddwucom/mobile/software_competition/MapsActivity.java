package ddwucom.mobile.software_competition;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MarkerOptions mOptions;
    private String address;
    private EditText editText;
    private TextView place;
    private ConstraintLayout place_info;
    private List<Address> addressList = null;
    private Button search;
    private Button ok;
    private double latitude;
    private double longitude;
    private Location my;
    private Intent intent;
    private BitmapDescriptor icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        editText = (EditText) findViewById(R.id.editText);
        search = (Button)findViewById(R.id.btn_search);
        place = (TextView) findViewById(R.id.place_ok);
        ok = (Button)findViewById(R.id.btn_ok) ;
        place_info = (ConstraintLayout)findViewById(R.id.place_info);
        my = new Location("my");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bitmap bit = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.marker_blue_soft_more), 50, 50, false);
        icon = BitmapDescriptorFactory.fromBitmap(bit);
    }

    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        Gps gps = new Gps(this);
        address = gps.getGps();

        if (address == null) {
            try {
                addressList = geocoder.getFromLocationName("서울 시청", 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                addressList = geocoder.getFromLocationName(address, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        checkingMap(addressList.get(0));
        search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                String str = editText.getText().toString();
                try {
                    addressList = geocoder.getFromLocationName(str, 1);
                    if (addressList.size() != 0) {
                        checkingMap(addressList.get(0));
                        place.setText(addressList.get(0).getAddressLine(0));
                        checkingMap(addressList.get(0));
                        place_info.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(MapsActivity.this, "잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                        editText.setText("");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    search.performClick();
                }

                return true;
            }
        });

        editText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText.setText(null);
                place_info.setVisibility(View.INVISIBLE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.putExtra("address", addressList.get(0).getAddressLine(0));
                setResult(RESULT_OK, intent);
                finish();
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

}
