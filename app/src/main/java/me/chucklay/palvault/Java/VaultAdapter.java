package me.chucklay.palvault.Java;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.chucklay.palvault.R;

/**
 * Created by Charlie on 1/9/2016.
 */
public class VaultAdapter extends CursorAdapter {

    private static final String TAG = "VAULTADAPTER";

    private LayoutInflater cursorInflater;

    public VaultAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(TAG, "New VaultAdapter created.");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(TAG, "inflating a new entry in the main list...");
        return cursorInflater.inflate(R.layout.vault_list_entry, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Set up TextViews.
        TextView vaultName = (TextView) view.findViewById(R.id.vault_name);
        TextView vaultDate = (TextView) view.findViewById(R.id.vault_timestamp);
        TextView unreadAlert = (TextView) view.findViewById(R.id.new_notification);

        //Get the strings to be placed in them.
        String title = cursor.getString(cursor.getColumnIndex(VaultContract.NewVaultInfo.USER_NAME));
        String date = cursor.getString(cursor.getColumnIndex(VaultContract.NewVaultInfo.LATEST_INTERACTION));
        boolean alertRequired;
        if(cursor.getInt(cursor.getColumnIndex(VaultContract.NewVaultInfo.REQUIRES_ALERT)) == 1){
            alertRequired = true;
        }
        else{
            alertRequired = false;
        }

        vaultName.setText(title);
        vaultDate.setText(date);
        if(alertRequired){
            unreadAlert.setText("!");
        }
        else{
            unreadAlert.setText("");
        }

        Log.d(TAG, "bindView complete.");
    }
}
