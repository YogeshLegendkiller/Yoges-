package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.IncidentModel;
import precisioninfomatics.servicefirst.Model.PartModel;
import precisioninfomatics.servicefirst.Model.SpareModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 06-02-2017.
 */

public class Spare_Visit_cursorAdapter extends CursorRecyclerViewAdapter<Spare_Visit_cursorAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;

    public Spare_Visit_cursorAdapter( Cursor cursor) {
        super(cursor);
    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public   static  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partno;
        TextView status,advancereturn,issue;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.partno = (TextView) itemView.findViewById(R.id.partserialno);
            this.status=(TextView)itemView.findViewById(R.id.spare_status);
            this.advancereturn=(TextView)itemView.findViewById(R.id.advance_return);
            this.issue=(TextView)itemView.findViewById(R.id.issue);


        }
    }



    @Override
    public Spare_Visit_cursorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterview, parent, false);

        Spare_Visit_cursorAdapter.MyViewHolder myViewHolder = new Spare_Visit_cursorAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(Spare_Visit_cursorAdapter.MyViewHolder holder, Cursor cursor) {
        holder.partno.setText(cursor.getString(cursor.getColumnIndex(SpareModel.partno)));
        int id = cursor.getInt(cursor.getColumnIndex(SpareModel.advreturn));
       // holder.partno.setText(listobj.get(position).getPartno());
        holder.issue.setText("Issued :"+ " " +cursor.getString(cursor.getColumnIndex(SpareModel.issued)));
        if (id == 0) {
            holder.advancereturn.setText("Advance Return : No");
        } else {
            holder.advancereturn.setText("Advance Return : Yes");
        }
        holder.status.setText(cursor.getString(cursor.getColumnIndex(SpareModel.partstatus)));

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
