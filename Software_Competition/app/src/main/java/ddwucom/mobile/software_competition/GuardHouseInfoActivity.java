package ddwucom.mobile.software_competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuardHouseInfoActivity extends AppCompatActivity {

    Intent intent;
    GuardHouseDTO guardHouse;
    TextView tvGuardHouseName;
    TextView tvGuardHouseNewAddress;
    TextView tvGuardHouseOldAddress;
    TextView tvGuardHouseTel;
    TextView tvGuardHousePoliceOfficeName;
    TextView tvGuardHouseUpdateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardhouse_info);

        intent = getIntent();
        guardHouse = (GuardHouseDTO) intent.getSerializableExtra("guardHouse");

        setTitle(guardHouse.getName());

        tvGuardHouseNewAddress = findViewById(R.id.tvGuardHouseNewAddress);
        tvGuardHouseOldAddress = findViewById(R.id.tvGuardHouseOldAddress);
        tvGuardHouseTel = findViewById(R.id.tvGuardHouseTel);
        tvGuardHousePoliceOfficeName = findViewById(R.id.tvGuardHousePoliceOfficeName);
        tvGuardHouseUpdateDate = findViewById(R.id.tvGuardHouseUpdateDate);

//        tvGuardHouseName.setText(guardHouse.getName());
        tvGuardHouseNewAddress.setText(guardHouse.getNewAddress());
        tvGuardHouseOldAddress.setText(guardHouse.getOldAddress());
        tvGuardHouseTel.setText(guardHouse.getTel());
        tvGuardHousePoliceOfficeName.setText(guardHouse.getPoliceOfficeName());
        tvGuardHouseUpdateDate.setText(guardHouse.getUpdateDate());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
            finish();
        return true;
    }
}