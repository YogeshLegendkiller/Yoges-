package precisioninfomatics.servicefirst.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.vipulasri.timelineview.TimelineView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import precisioninfomatics.servicefirst.Activities.GeneralClaimUpdation;
import precisioninfomatics.servicefirst.Activities.Incident;
import precisioninfomatics.servicefirst.DAO.DeleteDAO;
import precisioninfomatics.servicefirst.DAO.GeneralClaimDAO;
import precisioninfomatics.servicefirst.Fragments.GeneralClaim;
import precisioninfomatics.servicefirst.HelperClass.ViewDateTimeFormat;
import precisioninfomatics.servicefirst.Model.ClaimFieldModel;
import precisioninfomatics.servicefirst.Model.DeleteModel;
import precisioninfomatics.servicefirst.Model.GeneralClaimModel;
import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.LocalVendorModel;
import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.Model.VisitModel;
import precisioninfomatics.servicefirst.Networks.DeleteRecords;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 04/04/2017.
 */

public class GeneralClaimAdapter  extends CursorRecyclerViewAdapter<GeneralClaimAdapter.MyViewHolder>   implements View.OnClickListener{
    private Context context;
    private OnItemClickListener onItemClickListener;


    public GeneralClaimAdapter(Context mContext, Cursor cursor) {
        super(cursor);
        this.context=mContext;
    }
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public   static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  from_date, to_date,fromloc,toloc,total;
        private SeekBar seekBar2,seekBar1,seekBar3;
        private RecyclerView mrecyclerview;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.total=(TextView)itemView.findViewById(R.id.total);
            this.mrecyclerview=(RecyclerView)itemView.findViewById(R.id.cost);
            this.fromloc=(TextView)itemView.findViewById(R.id.fromloc);
            this.toloc=(TextView)itemView.findViewById(R.id.toloc);
               this.from_date = (TextView) itemView.findViewById(R.id.start_time);
            this.to_date = (TextView) itemView.findViewById(R.id.end_time);
            seekBar1=(SeekBar)itemView.findViewById(R.id.seek1);
            seekBar3=(SeekBar)itemView.findViewById(R.id.seek3);
            seekBar2=(SeekBar)itemView.findViewById(R.id.seek2);
            seekBar1.setEnabled(false);
            seekBar2.setEnabled(false);
            seekBar3.setEnabled(false);
        }
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }
    @Override
    public GeneralClaimAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.generalclaimrecycleradapter, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GeneralClaimAdapter.MyViewHolder holder, final Cursor cursor) {
       holder.from_date.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy",cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_FromDate))));
       holder.to_date.setText(ViewDateTimeFormat.DateFormatter("yyyy-MM-dd'T'HH:mm:ss","dd-MM-yy",cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_ToDate))));
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ClaimFieldModel>>() {
        }.getType();
        List<ClaimFieldModel> claimcostlist = gson.fromJson(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_claimcost)), type);
        ClaimCostAdapter claimCostAdapter=new ClaimCostAdapter(claimcostlist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.mrecyclerview.setLayoutManager(layoutManager);
        holder.mrecyclerview.setAdapter(claimCostAdapter);
        holder.fromloc.setText(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_FromLoc)));
       holder.toloc.setText(cursor.getString(cursor.getColumnIndex(GeneralClaimModel.Gc_ToLoc)));
          holder.total.setText(context.getString(R.string.Rs) + " " +cursor.getString(cursor.getColumnIndex(GeneralClaimModel.GC_Total)));

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