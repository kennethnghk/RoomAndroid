package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import im.tobe.roomandroid.adapter.RecyclerViewAdapter;
import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class NewContact extends AppCompatActivity {
    public static final String REPLY_KEY_NAME = "name";
    public static final String REPLY_KEY_OCCUPATION = "occupation";
    private EditText nameInput;
    private EditText occupationInput;
    private int contactId = 0;
    private Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        ContactViewModel contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this.getApplication())
                .create(ContactViewModel.class);

        nameInput = findViewById(R.id.nameInput);
        occupationInput = findViewById(R.id.occupationInput);

        if (getIntent().hasExtra(RecyclerViewPage.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(RecyclerViewPage.CONTACT_ID, 0);
            contactViewModel.get(contactId).observe(this, contact -> {
                if (contact != null) {
                    nameInput.setText(contact.getName());
                    occupationInput.setText(contact.getOccupation());
                }
            });
            isEdit = true;
        }

        // save button
        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this::onSaveBtnClicked);

        // update button
        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this::onUpdateBtnClicked);
    }

    private void onUpdateBtnClicked(View view) {
        String name = nameInput.getText().toString().trim();
        String occupation = occupationInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
            Snackbar.make(nameInput, R.string.empty_prompt, Snackbar.LENGTH_SHORT).show();
        } else {
            Contact contact = new Contact();
            contact.setId(contactId);
            contact.setName(name);
            contact.setOccupation(occupation);

            ContactViewModel.update(contact);
            finish();
        }
    }

    private void onSaveBtnClicked(View view) {
        Intent replyIntent = new Intent();

        if (!TextUtils.isEmpty(nameInput.getText()) && !TextUtils.isEmpty(occupationInput.getText())) {
            String name = nameInput.getText().toString();
            String occupation = occupationInput.getText().toString();

            replyIntent.putExtra(REPLY_KEY_NAME, name);
            replyIntent.putExtra(REPLY_KEY_OCCUPATION, occupation);
            setResult(RESULT_OK, replyIntent);

        } else {
            Toast.makeText(this, R.string.empty_prompt, Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, replyIntent);
        }

        finish();
    }
}