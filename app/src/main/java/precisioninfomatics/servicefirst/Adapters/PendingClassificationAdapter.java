package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.PendingClassificationModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 09-02-2017.
 */

public class PendingClassificationAdapter   extends CursorRecyclerViewAdapter<PendingClassificationAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    public PendingClassificationAdapter( Cursor cursor) {
        super( cursor);
    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


       @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterview, parent, false);

        PendingClassificationAdapter.MyViewHolder myViewHolder = new PendingClassificationAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partno;
        TextView brand,model;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.partno = (TextView) itemView.findViewById(R.id.partserialno);
            this.brand=(TextView)itemView.findViewById(R.id.advance_return);
            this.model=(TextView)itemView.findViewById(R.id.issue);
        }
    }
    @Override
    public void onBindViewHolder(PendingClassificationAdapter.MyViewHolder holder, Cursor cursor) {
        //   String partno=cursor.getString(cursor.getColumnIndex(PartModel.partno));
        // holder.brand.setText(cursor.getString(cursor.getColumnIndex(PartModel.brand)));
        //holder.model.setText(cursor.getString(cursor.getColumnIndex(PartModel.model)));
        holder.partno.setText(cursor.getString(cursor.getColumnIndex(PendingClassificationModel.text)));


    }




    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int   position = recyclerView.getChildLayoutPosition(v);
        if (position != RecyclerView.NO_POSITION) {
            final Cursor cursor = this.getItem(position);
            this.onItemClickListener.onItemClicked(cursor);
        }


    }

}

