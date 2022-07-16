package im.tobe.roomandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import im.tobe.roomandroid.adapter.RecyclerViewAdapter;
import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.model.ContactViewModel;

public class NewContact extends AppCompatActivity {
    public static final String REPLY_KEY_NAME = "name";
    public static final String REPLY_KEY_OCCUPATION = "occupation";
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

        saveBtn.setOnClickListener(this::onSaveBtnClicked);
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