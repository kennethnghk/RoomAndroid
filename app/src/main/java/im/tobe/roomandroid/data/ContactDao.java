package im.tobe.roomandroid.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import im.tobe.roomandroid.model.Contact;

@Dao
public interface ContactDao {

    // telling the compiler it is insert action
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contact contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("SELECT * FROM contact ORDER BY id DESC")
    LiveData<List<Contact>> getAllContacts(); // return LiveData

    @Query("SELECT * FROM contact where id = :id")
    LiveData<Contact> get(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact delete);
}
