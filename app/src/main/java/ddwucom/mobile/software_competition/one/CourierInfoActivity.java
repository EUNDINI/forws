package ddwucom.mobile.software_competition.one;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.software_competition.R;

public class CourierInfoActivity extends AppCompatActivity {

    Intent intent;
    CourierDTO courier;
    TextView tvCourierNewAddress;
    TextView tvCourierOldAddress;
    TextView tvCourierWeekdayStartTime;
    TextView tvCourierWeekdayEndTime;
    TextView tvCourierSaturdayStartTime;
    TextView tvCourierSaturdayEndTime;
    TextView tvCourierHolidayStartTime;
    TextView tvCourierHolidayEndTime;
    TextView tvCourierFreeUseTime;
    TextView tvCourierLateFee;
    TextView tvCourierLateFeeTime;
    TextView tvCourierInstruction;
    TextView tvCourierBox;
    TextView tvCourierServiceCenterTel;
    TextView tvCourierManagementAgencyTel;
    TextView tvCourierUpdateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_info);

        intent = getIntent();
        courier = (CourierDTO) intent.getSerializableExtra("courier");

        setTitle(courier.getName());

        tvCourierNewAddress = findViewById(R.id.tvCourierNewAddress);
        tvCourierOldAddress = findViewById(R.id.tvCourierOldAddress);
        tvCourierWeekdayStartTime = findViewById(R.id.tvCourierWeekdayStartTime);
        tvCourierWeekdayEndTime = findViewById(R.id.tvCourierWeekdayEndTime);
        tvCourierSaturdayStartTime = findViewById(R.id.tvCourierSaturdayStartTime);
        tvCourierSaturdayEndTime = findViewById(R.id.tvCourierSaturdayEndTime);
        tvCourierHolidayStartTime = findViewById(R.id.tvCourierHolidayStartTime);
        tvCourierHolidayEndTime = findViewById(R.id.tvCourierHolidayEndTime);
        tvCourierFreeUseTime = findViewById(R.id.tvCourierFreeUseTime);
        tvCourierLateFee = findViewById(R.id.tvCourierLateFee);
        tvCourierLateFeeTime = findViewById(R.id.tvCourierLateFeeTime);
        tvCourierInstruction = findViewById(R.id.tvCourierInstruction);
        tvCourierBox = findViewById(R.id.tvCourierBox);
        tvCourierServiceCenterTel = findViewById(R.id.tvCourierServiceCenterTel);
        tvCourierManagementAgencyTel = findViewById(R.id.tvCourierManagementAgencyTel);
        tvCourierUpdateDate = findViewById(R.id.tvCourierUpdateDate);

//        tvCourierName.setText(courier.getName());
        tvCourierNewAddress.setText(courier.getNewAddress());
        tvCourierOldAddress.setText(courier.getOldAddress());
        tvCourierWeekdayStartTime.setText(courier.getWeekdayStartTime());
        tvCourierWeekdayEndTime.setText(courier.getWeekdayEndTime());
        tvCourierSaturdayStartTime.setText(courier.getSaturdayStartTime());
        tvCourierSaturdayEndTime.setText(courier.getSaturdayEndTime());
        tvCourierHolidayStartTime.setText(courier.getHolidayStartTime());
        tvCourierHolidayEndTime.setText(courier.getHolidayEndTime());
        tvCourierFreeUseTime.setText(courier.getFreeUseTime());
        tvCourierLateFee.setText(courier.getLateFee());
        tvCourierLateFeeTime.setText(courier.getLateFeeTime());
        tvCourierInstruction.setText(courier.getInstruction());
        tvCourierBox.setText(courier.getBox());
        tvCourierServiceCenterTel.setText(courier.getServiceCenterTel());
        tvCourierManagementAgencyTel.setText(courier.getManagementAgencyTel());
        tvCourierUpdateDate.setText(courier.getUpdateDate());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
            finish();
        return true;
    }
}