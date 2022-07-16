package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toRecyclerViewBtn = findViewById(R.id.toRecyclerViewBtn);
        toRecyclerViewBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RecyclerViewPage.class);
            startActivity(intent);
        });

        Button toListViewBtn = findViewById(R.id.toListViewBtn);
        toListViewBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListViewPage.class);
            startActivity(intent);
        });

    }
}