package precisioninfomatics.servicefirst.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.ResourceModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 29-11-2016.
 */

public class ResourceViewAdapter extends CursorRecyclerViewAdapter<ResourceViewAdapter.ResourceViewHolder>  {
    private Context mcontext;
     public ResourceViewAdapter(Context context, Cursor cursor) {
        super(cursor);
         this.mcontext=context;
    }


    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resourceview, parent, false);
        return new ResourceViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ResourceViewHolder holder, Cursor cursor) {
        holder.phonnenumber.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.telephone)));
        holder.employeename.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.resourcename)));
        holder.status.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.resourcestatus)));
        String assigned_datetime=ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy hh:mm a",cursor.getString(cursor.getColumnIndex(ResourceModel.assignedate)));
        holder.assigneddate.setText(assigned_datetime);
        holder.designation.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.designation)));
        Picasso.with(mcontext).load(new File(cursor.getString(cursor.getColumnIndex(ResourceModel.localpath)))).resize(70,70).into(holder.imageView);
        holder.visitstatus.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.status)));
       if(cursor.getString(cursor.getColumnIndex(ResourceModel.instruction)).equals(""))
       {
           holder.insutruction.setText("-");
       } else{
           holder.insutruction.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.instruction)));
        }

        holder.email.setText(cursor.getString(cursor.getColumnIndex(ResourceModel.email)));
    }

    static   class ResourceViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
       private TextView employeename
        , phonnenumber ,status,designation,insutruction
        ,assigneddate
        ,email,visitstatus;
        ResourceViewHolder(View itemView) {
            super(itemView);
            this.designation= itemView.findViewById(R.id.designation);
            this.assigneddate= itemView.findViewById(R.id.assigneddatetime);
            this.employeename= itemView.findViewById(R.id.employeename);
            this.phonnenumber= itemView.findViewById(R.id.telephone);
            this.insutruction= itemView.findViewById(R.id.instruction);
            this.imageView= itemView.findViewById(R.id.image);
            this.status= itemView.findViewById(R.id.status);
            this.visitstatus= itemView.findViewById(R.id.visitstatus);
            this.email= itemView.findViewById(R.id.email);
        }

    }
}
