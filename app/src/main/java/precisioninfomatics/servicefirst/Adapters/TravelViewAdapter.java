package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.File;

import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.IncidentViewModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TravelViewAdapter extends CursorRecyclerViewAdapter<TravelViewAdapter.IncidentViewHolder> {

    public TravelViewAdapter(Cursor cursor) {
        super(cursor);


    }

    @Override
    public TravelViewAdapter.IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travelviewadapter, parent, false);
        return new TravelViewAdapter.IncidentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TravelViewAdapter.IncidentViewHolder holder, Cursor cursor) {
        holder.Checkin.setText(ViewDateTimeFormat.DateView(cursor.getString(cursor.getColumnIndex(VisitModel.checkin)),1));
        holder.Checkout.setText(ViewDateTimeFormat.DateView(cursor.getString(cursor.getColumnIndex(VisitModel.checkout)),1));
    //    int is_map_exist = cursor.getInt(cursor.getColumnIndex(VisitModel.maporothr));
        holder.TransportMode.setText(cursor.getString(cursor.getColumnIndex(VisitModel.transportmode)));
       /* if (is_map_exist == 1 || is_map_exist == 2) {
            holder.Attachment.setText("Show Path");
            final String filepath = cursor.getString(cursor.getColumnIndex(VisitModel.docpath));
//            Log.d("filepat",filepath);
            if(filepath!=null&&!filepath.contains("null")) {
                holder.Map.setVisibility(View.VISIBLE);
                Log.d("filepath",filepath);
                holder.Map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File file = new File(filepath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "image/jpeg");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });
            }
            else {
                holder.Map.setVisibility(View.GONE);
                holder.Attachment.setText("-");
            }
        } else {
            holder.Map.setVisibility(View.GONE);

        }*/
        holder.FromLoc.setText(cursor.getString(cursor.getColumnIndex(VisitModel.statadrs)));
        holder.ToLoc.setText(cursor.getString(cursor.getColumnIndex(VisitModel.endadrs)));
        holder.Kilometer.setText(cursor.getString(cursor.getColumnIndex(VisitModel.km)));
        holder.Amount.setText(cursor.getString(cursor.getColumnIndex(VisitModel.t_amnt)));


    }


  /* private   String getMimeType(@NonNull File file) {
        String type = null;
        final String url = file.toString();
        final String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        if (type == null) {
            type = "image/*";
        }
        return type;
    }
    */
    static class IncidentViewHolder extends RecyclerView.ViewHolder {
        TextView Checkin, Checkout, Kilometer, Amount, TransportMode,FromLoc,ToLoc;

        private IncidentViewHolder(View itemView) {
            super(itemView);
            this.Checkin= itemView.findViewById(R.id.checkin);
            this.Checkout= itemView.findViewById(R.id.checkout);
            this.Kilometer= itemView.findViewById(R.id.kilometer);
            //this.Map= itemView.findViewById(R.id.map_view);
            this.FromLoc= itemView.findViewById(R.id.fromloc);
            this.ToLoc= itemView.findViewById(R.id.toloc);
          //  this.Attachment= itemView.findViewById(R.id.attachment);
            this.Amount= itemView.findViewById(R.id.amount);
            this.TransportMode= itemView.findViewById(R.id.transportmode);
        }
    }
}
