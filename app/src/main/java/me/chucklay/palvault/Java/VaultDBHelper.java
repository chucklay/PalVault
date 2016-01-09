package me.chucklay.palvault.Java;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Charlie on 1/8/2016.
 *
 * A class that allows for easy access to a database containing information about the various
 * vaults.
 */
public class VaultDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "VAULT_DB";                                                   //Debug tag.

    //Constants used for operation.
    private static final String CREATE_QUERY = "CREATE TABLE " +
            VaultContract.NewVaultInfo.DATABASE_NAME + " (" + VaultContract.NewVaultInfo.USER_NAME +
            " TEXT PRIMARY KEY," + VaultContract.NewVaultInfo.PUBLIC_KEY + " TEXT," +
            VaultContract.NewVaultInfo.LATEST_INTERACTION + " TEXT);";
    private static final String UPGRADE_QUERY = "DROP TABLE IF EXISTS " +
            VaultContract.NewVaultInfo.DATABASE_NAME + ";";
    private static final String DATABASE_NAME = "VAULTS.DB";
    private static final int DATABASE_VERSION = 1;

    public VaultDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Beginning interaction with vault database...");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d(TAG, "Database successfully created.");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        At the moment, this simply recreates the database in its initial state.
        If this is actually needed, code to port the old database should be implemented.
         */
        if(oldVersion < newVersion){
            db.execSQL(UPGRADE_QUERY);
            Log.d(TAG, "Upgrading vault database from v." + oldVersion + " to v." + newVersion
                    + "...");
            onCreate(db);
        }
        else{
            Log.e(TAG, "onUpgrade called, but old version is not less than newVersion!");
        }
    }

    /**
     * Creates a new entry in the database representing a vault. Note that the latest interaction
     * time is assumed to be when this method is called.
     *
     * @param username The username of the other person involved with this vault.
     * @param key The public key of the other user.
     */
    public void insertVault(String username, String key, SQLiteDatabase db) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        ContentValues contentValues = new ContentValues();

        contentValues.put(VaultContract.NewVaultInfo.USER_NAME, username);
        contentValues.put(VaultContract.NewVaultInfo.PUBLIC_KEY, key);
        contentValues.put(VaultContract.NewVaultInfo.LATEST_INTERACTION, sdf.format(date));

        db.insert(VaultContract.NewVaultInfo.DATABASE_NAME, null, contentValues);
        Log.d(TAG, "New vault added to database.");
    }
}
