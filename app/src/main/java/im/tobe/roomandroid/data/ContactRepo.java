package im.tobe.roomandroid.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import im.tobe.roomandroid.model.Contact;
import im.tobe.roomandroid.util.ContactDb;

public class ContactRepo {
    private ContactDao contactDao;
    private LiveData<List<Contact>> contacts;

    public ContactRepo(Application application) {
        ContactDb db = ContactDb.getDatabase(application);
        contactDao = db.contactDao();

        contacts = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllData() {
        return contacts;
    }

    public void insert(Contact contact) {

        // always run in background thread, not pollute the main thread
        ContactDb.databaseWriteExecutor.execute(() -> {
            contactDao.insert(contact);
        });
    }
}
