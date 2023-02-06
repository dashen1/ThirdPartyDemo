package com.example.database;

import android.app.Application;
import android.content.Context;

import com.example.database.greendao.DaoMaster;
import com.example.database.greendao.DaoSession;
import com.example.database.greendao.DownloadFileDao;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private DownloadFileDao downloadFileDao;
    private static App mApp;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // regular SQLite database
        ExampleOpenHelper helper = new ExampleOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // ExampleOpenHelper helper = new ExampleOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static class ExampleOpenHelper extends DaoMaster.OpenHelper {

        public ExampleOpenHelper(Context context, String name) {
            super(context, name);
        }

        @Override
        public void onCreate(Database db) {
            super.onCreate(db);

            // Insert some example data.
            // INSERT INTO NOTE (_id, DATE, TEXT) VALUES(1, 0, 'Example Note')
            db.execSQL("INSERT INTO " + DownloadFileDao.TABLENAME + " (" +
                    DownloadFileDao.Properties.Id.columnName + ", " +
                    DownloadFileDao.Properties.Start.columnName + ", " +
                    DownloadFileDao.Properties.End.columnName + ", " +
                    DownloadFileDao.Properties.Process.columnName +
                    ") VALUES(1, 0, 1024, 512)");
        }
    }
}
