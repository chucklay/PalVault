package me.chucklay.palvault;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.security.Permission;
import java.util.List;
import java.util.Objects;
import java.util.jar.Manifest;

import me.chucklay.palvault.Java.VaultDBHelper;

public class OpenVault extends AppCompatActivity {

    public static final String VAULT_NAME = "vault_id";
    public static final String VAULT_ID = "vault_num_id";
    private static final String TAG = "open_vault";
    private static final int STORAGE_REQUEST = 1;
    private String vaultName;
    private String vaultId;
    private VaultDBHelper vaultDBHelper = new VaultDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO this activity may be useless. Look into photoPickerIntent.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vault);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vaultName = getIntent().getStringExtra(VAULT_NAME);
        vaultId = getIntent().getStringExtra(VAULT_ID);

        setTitle(vaultName);

        vaultDBHelper.modifyNotification(false, vaultName, vaultDBHelper.getWritableDatabase());

        Toast toast = Toast.makeText(getApplicationContext(), "Vault for " + vaultName,
                Toast.LENGTH_SHORT);
        toast.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Ask for permissions, if needed.
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission_group.STORAGE}, STORAGE_REQUEST);
        }

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission_group.STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
        }
        else{
            //We don't have permission to access files. Quit.
            //TODO Explain to the user that this app needs file permissions.
            Log.e(TAG, "ERROR: File storage permission denied.");
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            new CheckForUpdate().execute();
        }
    }

    private class CheckForUpdate extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            //TODO check for updates and populate the girdview.
            return null;
        }
    }
}
