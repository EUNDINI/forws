package ddwucom.mobile.software_competition.one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.software_competition.two.LoginActivity;
import ddwucom.mobile.software_competition.three.NewsActivity;
import ddwucom.mobile.software_competition.R;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maps:
                intent = new Intent(this, LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.community :
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.policy :
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;

        }
    }

}