package ddwucom.mobile.software_competition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button btnCreateNotice;
    Intent intent;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        nickname = getIntent().getStringExtra("nickname");

        btnCreateNotice = findViewById(R.id.btnCreateNotice);
        btnCreateNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, CreatePostsActivity.class);
                intent.putExtra("postType", "createNotice");
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });
    }
}