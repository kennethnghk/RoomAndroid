package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class NewContact extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private EditText nameInput;
    private EditText occupationInput;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this.getApplication())
                .create(ContactViewModel.class);

        nameInput = findViewById(R.id.nameInput);
        occupationInput = findViewById(R.id.occupationInput);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(nameInput.getText()) && !TextUtils.isEmpty(occupationInput.getText())) {
                Contact contact = new Contact(nameInput.getText().toString(), occupationInput.getText().toString());

                ContactViewModel.insert(contact);

            } else {
                Toast.makeText(this, R.string.empty_prompt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}