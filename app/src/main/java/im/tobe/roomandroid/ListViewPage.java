package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class ListViewPage extends AppCompatActivity {
    private static final String TAG = "ListViewPage";
    private ArrayList<String> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_page);

        ListView listView = findViewById(R.id.listView);
        contactList = new ArrayList<>();

        ContactViewModel contactViewModel = new ViewModelProvider.AndroidViewModelFactory(ListViewPage.this.getApplication())
                .create(ContactViewModel.class);

        // Observe LiveData
        contactViewModel.getContacts().observe(this, (List<Contact> contacts) -> {
            for (Contact contact : contacts) {
                Log.d(TAG, "onCreate: " + contact.getName());
                contactList.add(contact.getName());
            }
        });

        // create arrayAdapter
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contactList
        );

        // add data to listView
        listView.setAdapter(arrayAdapter);

        // attach event listener to listView
        listView.setOnItemClickListener(this::onListViewItemClicked);
    }

    private void onListViewItemClicked(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClicked: " + contactList.get(i));
    }
}