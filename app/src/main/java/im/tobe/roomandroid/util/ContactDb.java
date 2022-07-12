package im.tobe.roomandroid.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import im.tobe.roomandroid.data.ContactDao;
import im.tobe.roomandroid.model.Contact;

// for singleton DB instance
@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDb extends RoomDatabase {
    public abstract ContactDao contactDao();
    public static final int THREADS_COUNT = 4;

    private static volatile ContactDb INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREADS_COUNT);

    public static ContactDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactDb.class, "contact")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // run in background thread
            databaseWriteExecutor.execute(() -> {
                ContactDao contactDao = INSTANCE.contactDao();
                contactDao.deleteAll();

                Contact contact1 = new Contact("Peter", "Teacher");
                contactDao.insert(contact1);

                Contact contact2 = new Contact("MinChen", "Singer");
                contactDao.insert(contact2);
            });
        }
    };
}
