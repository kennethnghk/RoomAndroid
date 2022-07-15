package im.tobe.roomandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private static final int RECYCLED_VIEW_PAGE_REQUEST_CODE = 2;
    private ContactViewModel contactViewModel;
    private ListView listView;
    private ArrayList<String> contactList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        contactList = new ArrayList<>();

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(ContactViewModel.class);

        // Observe LiveData
        contactViewModel.getContacts().observe(this, (List<Contact> contacts) -> {
            for (Contact contact : contacts) {
                Log.d(TAG, "onCreate: " + contact.getName());
                contactList.add(contact.getName());
            }
        });

        // create arrayAdapter
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contactList
        );

        // add data to listView
        listView.setAdapter(arrayAdapter);

        // attach event listener to listView
        listView.setOnItemClickListener(this::onListViewItemClicked);

        // display floating icon
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });

        Button toRecyclerViewBtn = findViewById(R.id.toRecyclerViewBtn);
        toRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewPage.class);
                startActivityForResult(intent, RECYCLED_VIEW_PAGE_REQUEST_CODE);

            }
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

    private void onListViewItemClicked(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClicked: " + contactList.get(i));
    }
}