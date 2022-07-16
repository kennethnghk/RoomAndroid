package im.tobe.roomandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import im.tobe.roomandroid.adapter.RecyclerViewAdapter;
import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class RecyclerViewPage extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickedListener {
    private static final String TAG = "RecyclerViewPage";
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Contact>> contactList;
    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_page);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(RecyclerViewPage.this.getApplication())
                .create(ContactViewModel.class);

        // Observe LiveData
        contactViewModel.getContacts().observe(this, (List<Contact> contacts) -> {
            // set up adapter
            recyclerViewAdapter = new RecyclerViewAdapter(contacts, RecyclerViewPage.this, this);

            recyclerView.setAdapter(recyclerViewAdapter);
        });

        // display floating icon
        FloatingActionButton addBtn = findViewById(R.id.addContactBtn);
        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RecyclerViewPage.this, NewContact.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    public void onContactClicked(int position) {
        Log.d(TAG, "onContactClicked: position: "+position);
        Contact contact = Objects.requireNonNull(contactViewModel.getContacts().getValue()).get(position);
        Log.d(TAG, "onContactClicked: "+contact.getName());
    }

    // after create contact, come back here
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