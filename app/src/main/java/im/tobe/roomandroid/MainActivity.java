package im.tobe.roomandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private ContactViewModel contactViewModel;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(ContactViewModel.class);

        // Observe LiveData
        contactViewModel.getContacts().observe(this, contacts -> {
            StringBuilder builder = new StringBuilder();
            for (Contact contact : contacts) {
                Log.d(TAG, "onCreate: "+contact.getName());
                builder.append(" - ").append(contact.getName()).append(" ").append(contact.getOccupation());
            }

            text.setText(builder.toString());
        });

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                assert data != null;
                String name = data.getStringExtra(NewContact.REPLY_KEY_NAME);
                String occupation = data.getStringExtra(NewContact.REPLY_KEY_OCCUPATION);

                Log.d(TAG, "onActivityResult: "+name);

                assert name != null;
                Contact contact = new Contact(name, occupation);
                ContactViewModel.insert(contact);

            }
        }
    }
}