package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import im.tobe.roomandroid.adapter.RecyclerViewAdapter;
import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class RecyclerViewPage extends AppCompatActivity {
    private static final String TAG = "RecyclerViewPage";
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
            recyclerViewAdapter = new RecyclerViewAdapter(contacts, RecyclerViewPage.this);

            recyclerView.setAdapter(recyclerViewAdapter);
        });


    }
}