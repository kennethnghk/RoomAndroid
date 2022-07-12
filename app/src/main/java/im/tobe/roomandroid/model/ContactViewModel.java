package im.tobe.roomandroid.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import im.tobe.roomandroid.data.ContactRepo;

public class ContactViewModel extends AndroidViewModel {

    public static ContactRepo repo;
    public final LiveData<List<Contact>> contacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repo = new ContactRepo(application);
        contacts = repo.getAllData();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public static void insert(Contact contact) {
        repo.insert(contact);
    }
}
