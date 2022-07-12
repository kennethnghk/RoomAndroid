package im.tobe.roomandroid.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import im.tobe.roomandroid.model.Contact;

@Dao
public interface ContactDao {

    // telling the compiler it is insert action
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("SELECT * FROM contact ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts(); // return LiveData
}
