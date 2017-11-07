package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Activities.LocalVendor;
import precisioninfomatics.servicefirst.DAO.DeleteDAO;
import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.DAO.LocalVendorDAO;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Networks.DeleteRecords;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 02/03/2017.
 */

public class LocalVendorAdapter extends CursorRecyclerViewAdapter<LocalVendorAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    private Context mContext;
    public LocalVendorAdapter(Context mContext, Cursor cursor) {
        super( cursor);
        this.mContext = mContext;

    }
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public   static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView invoicenumber, invoicedate, sparecharge,servicecharge, resourcename,total;
        public MyViewHolder(final View itemView) {
            super(itemView);
            this.total= itemView.findViewById(R.id.total);
            this.servicecharge=itemView.findViewById(R.id.service_charge);
            this.invoicenumber = itemView.findViewById(R.id.invoicenumber);
            this.invoicedate = itemView.findViewById(R.id.invoicedate);
            this.sparecharge = itemView.findViewById(R.id.spare_charge);
            this.resourcename = itemView.findViewById(R.id.resource);
        }
    }
    @Override
    public LocalVendorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_vendor_layout, parent, false);

        LocalVendorAdapter.MyViewHolder myViewHolder = new LocalVendorAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(LocalVendorAdapter.MyViewHolder holder,final Cursor cursor) {
        try {
        holder.invoicenumber.setText(cursor.getString(cursor.getColumnIndex(LocalVendorModel.invoiceno)));
        holder.invoicedate.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy",cursor.getString(cursor.getColumnIndex(LocalVendorModel.invoicedate))));
        holder.servicecharge.setText(mContext.getString(R.string.Rs) + " " +cursor.getInt(cursor.getColumnIndex(LocalVendorModel.servicecharge)));
        holder.sparecharge.setText(mContext.getString(R.string.Rs) + " " +cursor.getInt(cursor.getColumnIndex(LocalVendorModel.sparecharge)));
        holder.resourcename.setText(cursor.getString(cursor.getColumnIndex(LocalVendorModel.Resourcename)));
        int total=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.servicecharge)) + cursor.getInt(cursor.getColumnIndex(LocalVendorModel.sparecharge));
        holder.total.setText(mContext.getString(R.string.Rs) + " " + total);
        }catch (Exception ex){
               ex.printStackTrace();
        }
        /*  holder.edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                alertDialog.setTitle("Confirm Delete...");

                alertDialog.setMessage("Are you sure you want delete this?");

                alertDialog.setIcon(R.drawable.ic_delete);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        int id=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.id));
                        int webid=cursor.getInt(cursor.getColumnIndex(LocalVendorModel.webid));
                        if(webid>0){
                            DeleteDAO deleteDAO=new DeleteDAO(mContext);
                            DeleteModel deleteModel=new DeleteModel();
                            deleteModel.setWebID(webid);
                            deleteModel.setFlag(2);
                            deleteDAO.InsertRecords(deleteModel);
///
                        }
                        LocalVendorDAO localVendorDAO=new LocalVendorDAO(mContext);
                        localVendorDAO.DeleteSingle(id);
                        DeleteRecords deleteRecords=new DeleteRecords();
                        deleteRecords.DeleteRecord(mContext);
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });*/
    }


    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            final Cursor cursor = this.getItem(position);
            this.onItemClickListener.onItemClicked(cursor);
        }


    }
}
