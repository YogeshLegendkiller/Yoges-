package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.DAO.SpareStatusDAO;
import precisioninfomatics.servicefirst.Model.SpareStatusModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 18/03/2017.
 */

public class SpareStatusAdapter extends CursorRecyclerViewAdapter<VisitStatusAdapter.MyViewHolder>  implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    public SpareStatusAdapter(Cursor cursor) {
        super( cursor);

    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView partno;
     ///   TextView brand,model;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.partno = (TextView) itemView.findViewById(R.id.partserialno);
        //    this.brand=(TextView)itemView.findViewById(R.id.advance_return);
        //    this.model=(TextView)itemView.findViewById(R.id.issue);
        }
    }
    @Override
    public VisitStatusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterview, parent, false);

        VisitStatusAdapter.MyViewHolder myViewHolder = new VisitStatusAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(VisitStatusAdapter.MyViewHolder holder, Cursor cursor) {
           holder.partno.setText(cursor.getString(cursor.getColumnIndex(SpareStatusModel.statusname)));
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

