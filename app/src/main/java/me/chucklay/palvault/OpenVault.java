package me.chucklay.palvault;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import me.chucklay.palvault.Java.VaultDBHelper;

public class OpenVault extends AppCompatActivity {

    public static final String VAULT_ID = "vault_id";
    private static final String TAG = "open_vault";
    private String vaultName;
    private VaultDBHelper vaultDBHelper = new VaultDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vault);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vaultName = getIntent().getStringExtra(VAULT_ID);

        setTitle(vaultName);

        vaultDBHelper.modifyNotification(false, vaultName, vaultDBHelper.getWritableDatabase());

        Toast toast = Toast.makeText(getApplicationContext(), "Vault for " + vaultName,
                Toast.LENGTH_SHORT);
        toast.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
