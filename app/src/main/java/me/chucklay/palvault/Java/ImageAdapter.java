package me.chucklay.palvault.Java;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import me.chucklay.palvault.R;

/**
 * Created by crl50 on 2/24/2016.
 */
public class ImageAdapter extends ArrayAdapter {

    Context context;
    int layoutID;
    File[] data = null;

    private ImageView image;
    private TextView id;

    public ImageAdapter(Context context, int layoutID, File[] data){
        super(context, layoutID, data);
        this.layoutID = layoutID;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(layoutID, parent, false);

        image = (ImageView) v.findViewById(R.id.image_preview);
        id = (TextView) v.findViewById(R.id.img_id);

        Bitmap bmpimg = BitmapFactory.decodeFile(data[position].getAbsolutePath());
        image.setImageBitmap(bmpimg);
        id.setText(data[position].getName());

        return v;
    }
}
