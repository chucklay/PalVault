package me.chucklay.palvault;

import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import me.chucklay.palvault.Java.PalVaultData;
import me.chucklay.palvault.Java.VaultAdapter;
import me.chucklay.palvault.Java.VaultDBHelper;

public class MainList extends AppCompatActivity {

    private static final String TAG = "MAINLIST";

    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    VaultAdapter vaultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO display a spinner while waiting for the ListView to populate.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        setTitle(getString(R.string.title_activity_main_list));

        listView = (ListView) findViewById(R.id.main_list_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Open the database for reading
        VaultDBHelper vaultDBHelper = new VaultDBHelper(this);
        sqLiteDatabase = vaultDBHelper.getReadableDatabase();
        SQLiteCursor cursor = vaultDBHelper.getAll(sqLiteDatabase);
        Log.d(TAG, "Cursor created!");

        vaultAdapter = new VaultAdapter(this, cursor, 0);
        listView.setAdapter(vaultAdapter);
        Log.d(TAG, "Adapter set.");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Determine which vault to open, generate an intent, and launch the OpenVault
                //activity.
                String vaultToOpen = ((TextView) view.findViewById(R.id.vault_name))
                        .getText().toString();
                Intent toOpenVault = new Intent(getApplicationContext(), OpenVault.class);
                toOpenVault.putExtra(OpenVault.VAULT_ID, vaultToOpen);
                startActivity(toOpenVault);
            }
        });
        Toast mToast = Toast.makeText(this, "Hello, " + PalVaultData.getUsername(),
                Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vaultAdapter.notifyDataSetChanged();
    }
}
