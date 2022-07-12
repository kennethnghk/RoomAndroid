package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(ContactViewModel.class);

        // Observe LiveData
        contactViewModel.getContacts().observe(this, contacts -> {
            for (Contact contact : contacts) {
                Log.d(TAG, "onCreate: "+contact.getName());
            }
        });
    }
}