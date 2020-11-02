package ddwucom.mobile.software_competition.one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ddwucom.mobile.software_competition.R;

public class LocationActivity extends Activity {
    private String address = "서울특별시 중구 명동 세종대로 110";
    private ImageButton search;
    private TextView location;
    private Button btnOk;
    private Button btnCancel;
    final int SEARCH_ADDRESS_CODE = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.location_dialog);
        getWindow().getAttributes().width = 1200;
        getWindow().getAttributes().height = 400;

        search = findViewById(R.id.searchLocation);
        location = findViewById(R.id.locationText);

        Gps gps = new Gps(this);

        if(gps.getGps() != null)
            address = gps.getGps();

        location.setText(address);

        search.setImageResource(R.drawable.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MapsActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_CODE);
            }
        });

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this, MapsActivity2.class);

                if(address == null)
                    Toast.makeText(LocationActivity.this, "주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                else{
                    intent.putExtra("address", address);
                    startActivity(intent);
                }
            }
        });
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SEARCH_ADDRESS_CODE :
                if (resultCode == RESULT_OK) {
                    address = data.getStringExtra("address");
                    location.setText(address);
                }
                break;
        }

    }
}
