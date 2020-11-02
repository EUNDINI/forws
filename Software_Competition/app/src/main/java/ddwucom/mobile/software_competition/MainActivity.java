package ddwucom.mobile.software_competition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView location;
    String address;
    final static int SEARCH_ADDRESS_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.location);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locaOne:
                Gps gps = new Gps(this);
                address = gps.getGps();
                location.setText(address);
                break;
            case R.id.locaTwo:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_CODE);
                break;
            case R.id.one:
                intent = new Intent(this, MapsActivity2.class);
                if (address != null) {
                    intent.putExtra("address", address);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "주소를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.two :
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
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