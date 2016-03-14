package me.chucklay.palvault;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.Manifest;

import javax.net.ssl.HttpsURLConnection;

import me.chucklay.palvault.Java.ImageAdapter;
import me.chucklay.palvault.Java.PalVaultData;
import me.chucklay.palvault.Java.VaultDBHelper;

public class OpenVault extends AppCompatActivity {

    public static final String VAULT_NAME = "vault_id";
    public static final String VAULT_ID = "vault_num_id";
    private static final String TAG = "open_vault";
    private static final int STORAGE_REQUEST = 1;
    private String vaultName;
    private String vaultId;
    private VaultDBHelper vaultDBHelper = new VaultDBHelper(this);
    private GridView imgGrid;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vault);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vaultName = getIntent().getStringExtra(VAULT_NAME);
        vaultId = getIntent().getStringExtra(VAULT_ID);

        imgGrid = (GridView) findViewById(R.id.vault_grid);

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

    private class CheckForUpdate extends AsyncTask<Void, Void, Object>{
        @Override
        protected Object doInBackground(Void... params) {
            //TODO check for updates and populate the girdview.

            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //SD Card is readable. Open the proper directory.
                File dir = new File(getApplicationContext().getExternalFilesDir(null), vaultId);
                //Get all of the image files in this directory
                List<File> images = new ArrayList<>();
                File[] files = dir.listFiles();
                for(File file : files){
                    if(file.getName().endsWith(".png")){
                        images.add(file);
                    }
                }

                try {
                    URL url = new URL("https://chucklay.me/pv/update/");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);

                    //TODO implement a token system to substitute for username/password.
                    String requests = "?token=" + PalVaultData.getUsername() + "&type=update-full";
                    conn.getOutputStream().write(requests.getBytes("UTF-8"));
                    conn.connect();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                adapter = new ImageAdapter(getApplicationContext(),
                        R.layout.content_vault_preview, (File[]) images.toArray());

                return adapter;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object result){
            if(result != null) {
                ImageAdapter adapter = (ImageAdapter) result;
                imgGrid.setAdapter(adapter);
            }
            else{
                Toast.makeText(getApplicationContext(), getText(R.string.open_error),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "CheckForUpdate returned null. This shouldn't happen in release version.");
            }
        }
    }
}
