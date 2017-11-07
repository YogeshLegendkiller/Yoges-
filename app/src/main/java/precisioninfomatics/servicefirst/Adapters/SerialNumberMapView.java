package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.File;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.SerialNumberMapModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 20/06/2017.
 */

public class SerialNumberMapView extends CursorRecyclerViewAdapter<SerialNumberMapView.SerialNumberMapViewViewHolder>   {
    public SerialNumberMapView( Cursor cursor) {
        super(cursor);

    }

    @Override
    public SerialNumberMapView.SerialNumberMapViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serialnumberview, parent, false);
        return new SerialNumberMapView.SerialNumberMapViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SerialNumberMapView.SerialNumberMapViewViewHolder holder, Cursor cursor) {
              holder.Shiptoserialnumber.setText(cursor.getString(cursor.getColumnIndex(SerialNumberMapModel.shiptoserialno)));
              holder.SerialNumber.setText(cursor.getString(cursor.getColumnIndex(SerialNumberMapModel.serialno)));
    }

    static class SerialNumberMapViewViewHolder extends RecyclerView.ViewHolder {
        TextView SerialNumber,
                Shiptoserialnumber;



        private SerialNumberMapViewViewHolder(View itemView) {
            super(itemView);
            this.SerialNumber=(TextView)itemView.findViewById(R.id.serialnumber);
            this.Shiptoserialnumber=(TextView)itemView.findViewById(R.id.shiptoserialno);

        }
    }
}


