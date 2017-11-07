package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import precisioninfomatics.servicefirst.Model.PartIssueModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 15/03/2017.
 */

public class SpareIssueAdapter  extends CursorRecyclerViewAdapter<SpareIssueAdapter.MyViewHolder> implements View.OnClickListener  {
    private precisioninfomatics.servicefirst.Adapters.OnItemClickListener onItemClickListener;


    public SpareIssueAdapter ( Cursor cursor){
        super( cursor);

    }
    @Override
    public SpareIssueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partissueadapter, parent, false);
        SpareIssueAdapter.MyViewHolder myViewHolder = new SpareIssueAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(SpareIssueAdapter.MyViewHolder holder, Cursor cursor) {
        holder.partno.setText(cursor.getString(cursor.getColumnIndex(PartIssueModel.issuedpartno)));
        holder.serialnumber.setText(cursor.getString(cursor.getColumnIndex(PartIssueModel.serialno)));
        holder.status.setText(cursor.getString(cursor.getColumnIndex(PartIssueModel.statusname)));
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partno;
        TextView status,serialnumber;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.partno = itemView.findViewById(R.id.partno);
            this.status= itemView.findViewById(R.id.status);
            this.serialnumber= itemView.findViewById(R.id.serialnumber);
        }
    }



}

