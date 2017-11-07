package precisioninfomatics.servicefirst.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import precisioninfomatics.servicefirst.Model.TransportModeModel;
import precisioninfomatics.servicefirst.Model.VisitStatusModel;
import precisioninfomatics.servicefirst.R;

/**
 * Created by 4264 on 17-02-2017.
 */

public class TransportModeAdapter extends CursorRecyclerViewAdapter<TransportModeAdapter.MyViewHolder> implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;

    public TransportModeAdapter( Cursor cursor) {
        super(cursor);

    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView transportmode;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.transportmode = (TextView) itemView.findViewById(R.id.partserialno);
         }
    }

    @Override
    public TransportModeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterview, parent, false);

        TransportModeAdapter.MyViewHolder myViewHolder = new TransportModeAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(TransportModeAdapter.MyViewHolder holder, Cursor cursor) {
          holder.transportmode.setText(cursor.getString(cursor.getColumnIndex(TransportModeModel.name)));


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

